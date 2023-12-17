/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*     */ import me.bebeli555.cookieclient.utils.GLUProjection;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.PlayerEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ public class Waypoints
/*     */   extends Mod
/*     */ {
/*     */   public static boolean loaded;
/*  35 */   public static ArrayList<Waypoint> waypoints = new ArrayList();
/*  36 */   private static ArrayList<String> onlinePlayers = new ArrayList();
/*  37 */   private static ArrayList<NamePos> loadedPlayers = new ArrayList();
/*  38 */   private static Timer timer = new Timer();
/*     */   
/*  40 */   public static Setting logoutSpots = new Setting(Mode.BOOLEAN, "LogoutSpots", Boolean.valueOf(true), new String[] { "Sets a waypoint when someone logs off" });
/*  41 */   public static Setting deathSpots = new Setting(Mode.BOOLEAN, "DeathSpots", Boolean.valueOf(true), new String[] { "Sets a waypoint to ur death location" });
/*  42 */   public static Setting tracers = new Setting(Mode.BOOLEAN, "Tracers", Boolean.valueOf(false), new String[] { "Renders tracers to the waypoints" });
/*  43 */   public static Setting tracersAlpha = new Setting(tracers, Mode.INTEGER, "Alpha", Integer.valueOf(200), new String[] { "Rbg" });
/*  44 */   public static Setting tracersWidth = new Setting(tracers, Mode.DOUBLE, "Width", Double.valueOf(1.5D), new String[] { "Line width" });
/*  45 */   public static Setting boundingBox = new Setting(Mode.BOOLEAN, "BoundingBox", Boolean.valueOf(true), new String[] { "Renders a box around the waypoint" });
/*  46 */   public static Setting size = new Setting(boundingBox, Mode.DOUBLE, "Size", Double.valueOf(0.65D), new String[] { "Size in width of rendered box" });
/*  47 */   public static Setting ySize = new Setting(boundingBox, Mode.DOUBLE, "YSize", Integer.valueOf(2), new String[] { "YSize of the box" });
/*  48 */   public static Setting width = new Setting(boundingBox, Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "How thicc the lines are" });
/*  49 */   public static Setting red = new Setting(boundingBox, Mode.INTEGER, "Red", Integer.valueOf(66), new String[] { "Rbg color" });
/*  50 */   public static Setting green = new Setting(boundingBox, Mode.INTEGER, "Green", Integer.valueOf(245), new String[] { "Rbg color" });
/*  51 */   public static Setting blue = new Setting(boundingBox, Mode.INTEGER, "Blue", Integer.valueOf(218), new String[] { "Rbg color" });
/*  52 */   public static Setting alpha = new Setting(boundingBox, Mode.INTEGER, "Alpha", Integer.valueOf(180), new String[] { "Rbg color" });
/*  53 */   public static Setting name = new Setting(Mode.BOOLEAN, "Name", Boolean.valueOf(true), new String[] { "Renders the name above the box" });
/*     */ 
/*     */   
/*  56 */   public Waypoints() { super(Group.RENDER, "Waypoints", new String[] { "Renders waypoints", "You can add a waypoint with "waypoint add name dimension"", "And delete one with "waypoint remove name"", "Example: "waypoint add test Overworld" Default dimension is the one ur in", "Also you can specify the coords like "waypoint add name x y z dimension"", "Also "waypoint list" will give u a list of waypoints for current server", "The waypoints are per server" }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  62 */     if (!loaded) {
/*  63 */       loaded = true;
/*     */       
/*     */       try {
/*  66 */         File file = new File(Settings.path + "/Waypoints.txt");
/*  67 */         if (file.exists()) {
/*  68 */           Scanner s = new Scanner(file);
/*  69 */           while (s.hasNextLine()) {
/*  70 */             String line = s.nextLine();
/*  71 */             if (!line.isEmpty()) {
/*     */               try {
/*  73 */                 String[] split = line.split(",");
/*  74 */                 Vec3d pos = new Vec3d(Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
/*  75 */                 Waypoint waypoint = new Waypoint(split[0], pos);
/*  76 */                 waypoint.server = split[4];
/*  77 */                 waypoint.originalDimension = Dimension.getDimensionFromId(Integer.parseInt(split[5]));
/*  78 */                 waypoints.add(waypoint);
/*  79 */               } catch (Exception e) {
/*  80 */                 System.out.println("CookieClient - Something is wrong with ur Waypoints.txt file. Couldnt load all waypoints");
/*  81 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/*  86 */           s.close();
/*     */         } 
/*  88 */       } catch (Exception e) {
/*  89 */         System.out.println("CookieClient - Error loading Waypoints from file");
/*  90 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  97 */     ArrayList<Waypoint> temp = new ArrayList<Waypoint>();
/*  98 */     temp.addAll(waypoints);
/*     */     
/* 100 */     for (Waypoint waypoint : temp) {
/* 101 */       if (waypoint.isTemp) {
/* 102 */         waypoints.remove(waypoint);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(float partialTicks) {
/* 109 */     for (Waypoint waypoint : waypoints) {
/* 110 */       if (!waypoint.server.equals(PlayerUtil.getServerIp()) || (waypoint.originalDimension == Dimension.END && Dimension.getCurrentDimension() != Dimension.END)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 115 */       if (boundingBox.booleanValue() && 
/* 116 */         waypoint.getPos().func_72438_d(mc.field_175622_Z.func_174791_d()) < 1000.0D) {
/* 117 */         RenderUtil.drawBoundingBox(RenderUtil.getBB(waypoint.getPos(), size.doubleValue(), ySize.doubleValue()), (float)width.doubleValue(), red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 122 */       if (tracers.booleanValue()) {
/* 123 */         Vec3d vec = (new Vec3d((waypoint.getPos()).field_72450_a, (waypoint.getPos()).field_72448_b, (waypoint.getPos()).field_72449_c)).func_178786_a((mc.func_175598_ae()).field_78725_b, (mc.func_175598_ae()).field_78726_c, (mc.func_175598_ae()).field_78723_d);
/* 124 */         Tracers.renderTracer(vec, new Color(82, 81, 79, tracersAlpha.intValue()), (float)tracersWidth.doubleValue(), partialTicks);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (name.booleanValue() && (mc.func_175598_ae()).field_78733_k != null && mc.field_175622_Z.func_174791_d().func_72438_d(waypoint.getPos()) <= 15.0D) {
/* 130 */         double scale = 0.03D;
/* 131 */         double posX = (waypoint.getPos()).field_72450_a + (0.0F * partialTicks) - (mc.func_175598_ae()).field_78725_b + size.doubleValue() / 2.0D;
/* 132 */         double posY = (waypoint.getPos()).field_72448_b + (0.0F * partialTicks) - (mc.func_175598_ae()).field_78726_c + ySize.doubleValue() + 0.5D;
/* 133 */         double posZ = (waypoint.getPos()).field_72449_c + (0.0F * partialTicks) - (mc.func_175598_ae()).field_78723_d + size.doubleValue() / 2.0D;
/* 134 */         GlStateManager.func_179094_E();
/* 135 */         GlStateManager.func_179137_b(posX, posY, posZ);
/* 136 */         GlStateManager.func_179114_b(-(mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
/* 137 */         GlStateManager.func_179114_b((((mc.func_175598_ae()).field_78733_k.field_74320_O == 2) ? -1 : 1) * (mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
/* 138 */         GlStateManager.func_179139_a(-scale, -scale, -scale);
/* 139 */         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 140 */         GlStateManager.func_179097_i();
/*     */         
/* 142 */         mc.field_71466_p.func_175063_a(ChatFormatting.GRAY + waypoint.name, (-mc.field_71466_p.func_78256_a(waypoint.name) / 2), 0.0F, -1);
/*     */         
/* 144 */         GlStateManager.func_179126_j();
/* 145 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
/* 152 */     if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
/* 153 */       GlStateManager.func_179094_E();
/* 154 */       float scale = 0.35F + 110.0F / mc.field_71474_y.field_74334_X - 1.0F;
/* 155 */       GlStateManager.func_179152_a(scale, scale, scale);
/*     */       
/* 157 */       for (Waypoint waypoint : waypoints) {
/* 158 */         if (mc.field_175622_Z.func_174791_d().func_72438_d(waypoint.getPos()) <= 15.0D || 
/* 159 */           !waypoint.server.equals(PlayerUtil.getServerIp()) || (waypoint.originalDimension == Dimension.END && Dimension.getCurrentDimension() != Dimension.END)) {
/*     */           continue;
/*     */         }
/*     */         
/* 163 */         float[] bounds = GLUProjection.convertBounds(waypoint.getPos().func_72441_c(size.doubleValue() / 2.0D, ySize.doubleValue() + 0.25D, size.doubleValue() / 2.0D), event.getPartialTicks(), event.getResolution().func_78326_a(), event.getResolution().func_78328_b());
/* 164 */         if (bounds != null) {
/* 165 */           for (int i = 0; i < bounds.length; i++) {
/* 166 */             bounds[i] = bounds[i] / scale;
/*     */           }
/*     */           
/* 169 */           String text = ChatFormatting.GRAY + waypoint.name + " " + (int)waypoint.getPos().func_72438_d(mc.field_175622_Z.func_174791_d()) + "m";
/* 170 */           mc.field_71466_p.func_175063_a(text, bounds[0] + (bounds[2] - bounds[0]) / 2.0F - (mc.field_71466_p.func_78256_a(text) / 2), bounds[1] + bounds[3] - bounds[1] - 8.0F - 1.0F, -1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 175 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/* 182 */   public void onLogout(PlayerEvent.PlayerLoggedOutEvent e) { onDisabled(); }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/* 187 */     if (logoutSpots.booleanValue() && mc.field_71439_g != null && mc.field_71439_g.field_70173_aa > 20 && !mc.field_71439_g.field_70128_L && timer.hasPassed(500)) {
/* 188 */       timer.reset();
/* 189 */       onlinePlayers.clear();
/* 190 */       for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/* 191 */         onlinePlayers.add(player.func_178845_a().getName());
/*     */       }
/*     */       
/* 194 */       for (NamePos player : loadedPlayers) {
/* 195 */         if (!onlinePlayers.contains(player.name)) {
/* 196 */           ArrayList<Waypoint> temp = new ArrayList<Waypoint>();
/* 197 */           temp.addAll(waypoints);
/* 198 */           for (Waypoint waypoint : temp) {
/* 199 */             if (waypoint.logoutName.equals(player.name)) {
/* 200 */               waypoints.remove(waypoint);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 205 */           Waypoint waypoint = new Waypoint(player.name + "'s logout spot", player.pos, true);
/* 206 */           waypoint.isTemp = true;
/* 207 */           waypoint.logoutName = player.name;
/* 208 */           waypoint.server = PlayerUtil.getServerIp();
/* 209 */           waypoints.add(waypoint);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 214 */       ArrayList<Waypoint> remove = new ArrayList<Waypoint>();
/* 215 */       for (Waypoint waypoint : waypoints) {
/* 216 */         if (waypoint.isTemp && !waypoint.logoutName.isEmpty() && onlinePlayers.contains(waypoint.logoutName)) {
/* 217 */           remove.add(waypoint);
/*     */         }
/*     */       } 
/* 220 */       waypoints.removeAll(remove);
/*     */       
/* 222 */       loadedPlayers.clear();
/* 223 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 224 */         if (player.func_145782_y() != -100) {
/* 225 */           loadedPlayers.add(new NamePos(player.func_70005_c_(), new Vec3d(player.field_70165_t, player.field_70163_u, player.field_70161_v)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDeath(LivingDeathEvent event) {
/* 233 */     if (mc.field_71439_g != null && event.getEntity().equals(mc.field_71439_g) && deathSpots.booleanValue()) {
/* 234 */       Waypoint waypoint = new Waypoint("Death spot", new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v));
/* 235 */       waypoint.isTemp = true;
/* 236 */       waypoint.server = PlayerUtil.getServerIp();
/* 237 */       waypoints.add(waypoint);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addWaypoint(Waypoint waypoint) {
/* 243 */     waypoints.add(waypoint);
/* 244 */     updateFile();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeWaypoint(Waypoint waypoint) {
/* 249 */     waypoints.remove(waypoint);
/* 250 */     updateFile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateFile() {
/*     */     try {
/* 257 */       file = new File(Settings.path + "/Waypoints.txt");
/* 258 */       file.delete();
/* 259 */       file.createNewFile();
/*     */       
/* 261 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/* 262 */       for (Waypoint waypoint : waypoints) {
/* 263 */         if (!waypoint.isTemp) {
/* 264 */           bw.write(waypoint.name + "," + (waypoint.getPos()).field_72450_a + "," + (waypoint.getPos()).field_72448_b + "," + (waypoint.getPos()).field_72449_c + "," + waypoint.server + "," + waypoint.originalDimension.id);
/* 265 */           bw.newLine();
/*     */         } 
/*     */       } 
/*     */       
/* 269 */       bw.close();
/* 270 */     } catch (Exception e) {
/* 271 */       System.out.println("CookieClient - Error updating Waypoints file");
/* 272 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Waypoint {
/*     */     private Vec3d originalPos;
/*     */     public Waypoints.Dimension originalDimension;
/*     */     public String name;
/* 280 */     public String logoutName = "";
/* 281 */     public String server = "";
/*     */     
/*     */     public boolean isTemp;
/*     */     
/*     */     public Waypoint(String name, Vec3d pos) {
/* 286 */       this.originalPos = pos.func_72441_c(-Waypoints.size.doubleValue() / 2.0D, 0.0D, -Waypoints.size.doubleValue() / 2.0D);
/* 287 */       this.originalDimension = Waypoints.Dimension.getCurrentDimension();
/* 288 */       this.name = name;
/*     */     }
/*     */     
/*     */     public Vec3d getPos() {
/* 292 */       if (Waypoints.Dimension.getCurrentDimension() == Waypoints.Dimension.NETHER && this.originalDimension == Waypoints.Dimension.OVERWORLD)
/* 293 */         return new Vec3d(this.originalPos.field_72450_a / 8.0D, this.originalPos.field_72448_b, this.originalPos.field_72449_c / 8.0D); 
/* 294 */       if (Waypoints.Dimension.getCurrentDimension() == Waypoints.Dimension.OVERWORLD && this.originalDimension == Waypoints.Dimension.NETHER) {
/* 295 */         return new Vec3d(this.originalPos.field_72450_a * 8.0D, this.originalPos.field_72448_b, this.originalPos.field_72449_c * 8.0D);
/*     */       }
/* 297 */       return this.originalPos;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 302 */     public Vec3d getOriginalPos() { return this.originalPos; }
/*     */ 
/*     */     
/*     */     public Waypoint(String name, Vec3d pos, boolean isTemp) {
/* 306 */       this(name, pos);
/* 307 */       this.isTemp = isTemp;
/*     */     }
/*     */     
/*     */     public static Waypoint getWaypointFromName(String name) {
/* 311 */       for (Waypoint waypoint : Waypoints.waypoints) {
/* 312 */         if (waypoint.name.equals(name)) {
/* 313 */           return waypoint;
/*     */         }
/*     */       } 
/*     */       
/* 317 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Dimension {
/* 322 */     OVERWORLD(false, "Overworld"),
/* 323 */     NETHER(-1, "Nether"),
/* 324 */     END(true, "End");
/*     */     public int id;
/*     */     public String name;
/*     */     
/*     */     Dimension(int id, String name) {
/* 329 */       this.id = id;
/* 330 */       this.name = name;
/*     */     }
/*     */     
/*     */     public static Dimension getCurrentDimension() {
/* 334 */       if (Mod.mc.field_175622_Z == null) {
/* 335 */         return OVERWORLD;
/*     */       }
/*     */       
/* 338 */       if (Mod.mc.field_175622_Z.field_71093_bK == -1)
/* 339 */         return NETHER; 
/* 340 */       if (Mod.mc.field_175622_Z.field_71093_bK == 0) {
/* 341 */         return OVERWORLD;
/*     */       }
/*     */       
/* 344 */       return END;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Dimension getDimensionFromId(int id) {
/* 349 */       for (Dimension dimension : values()) {
/* 350 */         if (dimension.id == id) {
/* 351 */           return dimension;
/*     */         }
/*     */       } 
/*     */       
/* 355 */       return OVERWORLD;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NamePos {
/*     */     public String name;
/*     */     public Vec3d pos;
/*     */     
/*     */     public NamePos(String name, Vec3d pos) {
/* 364 */       this.name = name;
/* 365 */       this.pos = pos;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Waypoints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */