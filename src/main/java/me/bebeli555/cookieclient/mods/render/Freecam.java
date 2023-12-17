/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStateEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.BaritoneUtil;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Freecam
/*     */   extends Mod
/*     */ {
/*     */   public static boolean isToggled;
/*     */   public static EntityPlayer camera;
/*     */   private boolean clicked;
/*  29 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Integer.valueOf(1), new String[] { "How fast to move" }); @EventHandler private Listener<PacketEvent> packetEvent; @EventHandler
/*  30 */   private Listener<PlayerUpdateMoveStateEvent> onKeyPress; public static Setting baritoneMiddleClick = new Setting(Mode.BOOLEAN, "BaritoneMiddleClick", Boolean.valueOf(true), new String[] { "Middleclick blocks while in freecam", "To make baritone walk there" }); public void onEnabled() { this.clicked = false; isToggled = true; if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*     */       disable(); return;
/*     */     }  Mod.EVENT_BUS.subscribe(this); MinecraftForge.EVENT_BUS.register(this); mc.field_175612_E = false; camera = new EntityOtherPlayerMP(mc.field_71441_e, mc.func_110432_I().func_148256_e()); camera.func_82149_j(mc.field_71439_g); camera.field_70126_B = mc.field_71439_g.field_70177_z; camera.field_70759_as = mc.field_71439_g.field_70759_as; camera.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by); mc.field_71441_e.func_73027_a(-100, camera);
/*  33 */     mc.field_175622_Z = camera; } public Freecam() { super(Group.RENDER, "Freecam", new String[] { "Allows you to fly out of your body" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.packetEvent = new Listener(event -> {
/*     */           
/*     */           try {
/* 137 */             if (event.packet instanceof CPacketUseEntity) {
/* 138 */               CPacketUseEntity packet = (CPacketUseEntity)event.packet;
/*     */               
/* 140 */               if (packet.func_149564_a(mc.field_71441_e).equals(mc.field_71439_g)) {
/* 141 */                 event.cancel();
/*     */               }
/*     */             } 
/* 144 */           } catch (NullPointerException nullPointerException) {}
/*     */         }new java.util.function.Predicate[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.onKeyPress = new Listener(event -> {
/*     */           
/* 169 */           mc.field_71439_g.field_71158_b.field_192832_b = 0.0F;
/* 170 */           mc.field_71439_g.field_71158_b.field_78902_a = 0.0F;
/* 171 */           mc.field_71439_g.field_71158_b.field_78901_c = false;
/* 172 */           mc.field_71439_g.field_71158_b.field_78899_d = false;
/* 173 */           mc.field_71439_g.func_70031_b(false);
/*     */           
/* 175 */           event.cancel();
/*     */         }new java.util.function.Predicate[0]); }
/*     */   
/*     */   public static double[] getRotationFromVec(Vec3d vec) {
/* 179 */     double xz = Math.sqrt(vec.field_72450_a * vec.field_72450_a + vec.field_72449_c * vec.field_72449_c);
/* 180 */     double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.field_72449_c, vec.field_72450_a)) - 90.0D);
/* 181 */     double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.field_72448_b, xz)));
/* 182 */     return new double[] { yaw, pitch }; } public void onDisabled() { isToggled = false; Mod.EVENT_BUS.unsubscribe(this); MinecraftForge.EVENT_BUS.unregister(this); mc.field_175612_E = true; if (mc.field_71439_g != null && mc.field_71441_e != null && mc.field_175622_Z != null) {
/*     */       mc.field_71439_g.field_70702_br = 0.0F; mc.field_71439_g.field_191988_bg = 0.0F; mc.field_71441_e.func_72900_e(camera);
/*     */       mc.field_175622_Z = mc.field_71439_g;
/*     */     }  }
/* 186 */   public static double normalizeAngle(double angle) { angle %= 360.0D;
/*     */     
/* 188 */     if (angle >= 180.0D) {
/* 189 */       angle -= 360.0D;
/*     */     }
/*     */     
/* 192 */     if (angle < -180.0D) {
/* 193 */       angle += 360.0D;
/*     */     }
/*     */     
/* 196 */     return angle; }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(LivingEvent.LivingUpdateEvent e) {
/*     */     if (!e.getEntity().equals(camera) || mc.field_71462_r != null)
/*     */       return; 
/*     */     if (camera == null) {
/*     */       disable();
/*     */       return;
/*     */     } 
/*     */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/*     */       camera.field_70181_x = speed.doubleValue();
/*     */     } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/*     */       camera.field_70181_x = -speed.doubleValue();
/*     */     } else {
/*     */       camera.field_70181_x = 0.0D;
/*     */     } 
/*     */     if (mc.field_71474_y.field_74351_w.func_151470_d()) {
/*     */       camera.field_191988_bg = 1.0F;
/*     */     } else if (mc.field_71474_y.field_74368_y.func_151470_d()) {
/*     */       camera.field_191988_bg = -1.0F;
/*     */     } else {
/*     */       camera.field_191988_bg = 0.0F;
/*     */     } 
/*     */     if (mc.field_71474_y.field_74370_x.func_151470_d()) {
/*     */       camera.field_70702_br = -1.0F;
/*     */     } else if (mc.field_71474_y.field_74366_z.func_151470_d()) {
/*     */       camera.field_70702_br = 1.0F;
/*     */     } else {
/*     */       camera.field_70702_br = 0.0F;
/*     */     } 
/*     */     if (camera.field_70702_br != 0.0F || camera.field_191988_bg != 0.0F) {
/*     */       double yawRad = Math.toRadians(camera.field_70177_z - getRotationFromVec(new Vec3d(camera.field_70702_br, 0.0D, camera.field_191988_bg))[0]);
/*     */       camera.field_70159_w = -Math.sin(yawRad) * speed.doubleValue();
/*     */       camera.field_70179_y = Math.cos(yawRad) * speed.doubleValue();
/*     */       if (mc.field_71474_y.field_151444_V.func_151470_d()) {
/*     */         camera.func_70031_b(true);
/*     */         camera.field_70159_w *= 1.5D;
/*     */         camera.field_70179_y *= 1.5D;
/*     */       } else {
/*     */         camera.func_70031_b(false);
/*     */       } 
/*     */     } else {
/*     */       camera.field_70159_w = 0.0D;
/*     */       camera.field_70179_y = 0.0D;
/*     */     } 
/*     */     camera.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
/*     */     camera.field_70145_X = true;
/*     */     camera.field_70759_as = camera.field_70177_z;
/*     */     camera.func_70091_d(MoverType.SELF, camera.field_70159_w, camera.field_70181_x, camera.field_70179_y);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMouseInput(InputEvent.MouseInputEvent e) {
/*     */     if (Mouse.isButtonDown(2) && baritoneMiddleClick.booleanValue()) {
/*     */       if (this.clicked)
/*     */         return; 
/*     */       this.clicked = true;
/*     */       if (mc.field_71476_x == null || mc.field_71476_x.func_178782_a() == null)
/*     */         return; 
/*     */       BaritoneUtil.walkTo(mc.field_71476_x.func_178782_a().func_177982_a(0, 1, 0), false);
/*     */     } else {
/*     */       this.clicked = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Freecam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */