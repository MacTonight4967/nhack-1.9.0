/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.utils.EntityUtil;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class Tracers
/*     */   extends Mod
/*     */ {
/*  38 */   public static Setting lineWidth = new Setting(Mode.DOUBLE, "LineWidth", Double.valueOf(0.5D), new String[] { "Width of the rendered lines" });
/*  39 */   public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", Integer.valueOf(150), new String[] { "How bright the tracers are as alpha" });
/*  40 */   public static Setting players = new Setting(Mode.BOOLEAN, "Players", Boolean.valueOf(true), new String[0]);
/*  41 */   public static Setting friends = new Setting(Mode.BOOLEAN, "Friends", Boolean.valueOf(true), new String[0]);
/*  42 */   public static Setting animals = new Setting(Mode.BOOLEAN, "Animals", Boolean.valueOf(false), new String[0]);
/*  43 */   public static Setting monsters = new Setting(Mode.BOOLEAN, "Monsters", Boolean.valueOf(false), new String[0]);
/*  44 */   public static Setting vehicles = new Setting(Mode.BOOLEAN, "Vehicles", Boolean.valueOf(false), new String[0]);
/*  45 */   public static Setting items = new Setting(Mode.BOOLEAN, "Items", Boolean.valueOf(false), new String[0]);
/*  46 */   public static Setting others = new Setting(Mode.BOOLEAN, "Others", Boolean.valueOf(false), new String[0]);
/*     */ 
/*     */   
/*  49 */   public Tracers() { super(Group.RENDER, "Tracers", new String[] { "Draws line to entities" }); }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender(RenderWorldLastEvent e) {
/*  54 */     if (mc.func_175598_ae() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  59 */       if (shouldRenderTracer(entity)) {
/*  60 */         Color c = getColor(entity);
/*  61 */         Vec3d entityPos = interpolateEntity(entity, e.getPartialTicks()).func_178786_a((mc.func_175598_ae()).field_78725_b, (mc.func_175598_ae()).field_78726_c, (mc.func_175598_ae()).field_78723_d);
/*  62 */         entityPos = entityPos.func_72441_c(0.0D, (entity.field_70131_O / 2.0F), 0.0D);
/*  63 */         renderTracer(entityPos, c, (float)lineWidth.doubleValue(), e.getPartialTicks());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderTracer(Vec3d pos, Color c, float lineWidth, float partialTicks) {
/*  69 */     boolean bobbing = mc.field_71474_y.field_74336_f;
/*  70 */     mc.field_71474_y.field_74336_f = false;
/*  71 */     mc.field_71460_t.func_78479_a(partialTicks, 0);
/*     */     
/*  73 */     Vec3d pointer = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_175622_Z.field_70125_A))).func_178785_b(-((float)Math.toRadians(mc.field_175622_Z.field_70177_z)));
/*     */     
/*  75 */     GlStateManager.func_179094_E();
/*  76 */     GlStateManager.func_179090_x();
/*  77 */     GlStateManager.func_179147_l();
/*  78 */     GlStateManager.func_179118_c();
/*  79 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  80 */     GlStateManager.func_179103_j(7425);
/*  81 */     GL11.glLineWidth(lineWidth);
/*  82 */     GL11.glEnable(2848);
/*  83 */     GL11.glHint(3154, 4354);
/*  84 */     GlStateManager.func_179097_i();
/*  85 */     GL11.glEnable(34383);
/*  86 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  87 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  88 */     bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/*  89 */     bufferbuilder.func_181662_b(pointer.field_72450_a, pointer.field_72448_b + mc.field_71439_g.func_70047_e(), pointer.field_72449_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), alpha.intValue()).func_181675_d();
/*  90 */     bufferbuilder.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), alpha.intValue()).func_181675_d();
/*  91 */     tessellator.func_78381_a();
/*  92 */     GlStateManager.func_179103_j(7424);
/*  93 */     GL11.glDisable(2848);
/*  94 */     GlStateManager.func_179126_j();
/*  95 */     GL11.glDisable(34383);
/*  96 */     GlStateManager.func_179084_k();
/*  97 */     GlStateManager.func_179141_d();
/*  98 */     GlStateManager.func_179098_w();
/*  99 */     GlStateManager.func_179121_F();
/*     */     
/* 101 */     mc.field_71474_y.field_74336_f = bobbing;
/* 102 */     mc.field_71460_t.func_78479_a(partialTicks, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean shouldRenderTracer(Entity e) {
/* 107 */     if (!Freecam.isToggled && e.equals(mc.field_71439_g)) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     if (Freecam.isToggled && e.equals(mc.field_175622_Z)) {
/* 112 */       return false;
/*     */     }
/*     */     
/* 115 */     if (e instanceof net.minecraft.entity.player.EntityPlayer) {
/* 116 */       if (Friends.isFriend(e)) {
/* 117 */         return friends.booleanValue();
/*     */       }
/* 119 */       return players.booleanValue();
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e)) {
/* 124 */       return monsters.booleanValue();
/*     */     }
/*     */     
/* 127 */     if (EntityUtil.isPassive(e)) {
/* 128 */       return animals.booleanValue();
/*     */     }
/*     */     
/* 131 */     if (e instanceof net.minecraft.entity.item.EntityBoat || e instanceof net.minecraft.entity.item.EntityMinecart) {
/* 132 */       return vehicles.booleanValue();
/*     */     }
/*     */     
/* 135 */     if (e instanceof net.minecraft.entity.item.EntityItem) {
/* 136 */       return items.booleanValue();
/*     */     }
/*     */     
/* 139 */     return others.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color getColor(Entity e) {
/* 144 */     if (e instanceof net.minecraft.entity.player.EntityPlayer && Friends.isFriend(e)) {
/* 145 */       return Color.CYAN;
/*     */     }
/*     */     
/* 148 */     float distance = e.func_70032_d(mc.field_71439_g);
/* 149 */     if (distance <= 6.0F)
/* 150 */       return Color.RED; 
/* 151 */     if (distance <= 23.0F)
/* 152 */       return Color.ORANGE; 
/* 153 */     if (distance <= 45.0F) {
/* 154 */       return Color.YELLOW;
/*     */     }
/* 156 */     return Color.GREEN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public static Vec3d interpolateEntity(Entity entity, float time) { return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time); }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Tracers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */