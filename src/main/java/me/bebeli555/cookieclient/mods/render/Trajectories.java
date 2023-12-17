/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class Trajectories
/*     */   extends Mod
/*     */ {
/*  44 */   private Queue<Vec3d> flightPoint = new ConcurrentLinkedQueue();
/*     */   
/*  46 */   public static Setting width = new Setting(Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "How thicc the lines are" });
/*  47 */   public static Setting red = new Setting(Mode.INTEGER, "Red", Integer.valueOf(66), new String[] { "Rbg color" });
/*  48 */   public static Setting green = new Setting(Mode.INTEGER, "Green", Integer.valueOf(245), new String[] { "Rbg color" });
/*  49 */   public static Setting blue = new Setting(Mode.INTEGER, "Blue", Integer.valueOf(218), new String[] { "Rbg color" });
/*  50 */   public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", Integer.valueOf(255), new String[] { "Rbg color" });
/*     */ 
/*     */   
/*  53 */   public Trajectories() { super(Group.RENDER, "Trajectories", new String[] { "Renders where projectiles will land when shot", "Like enderpearls, arrows etc" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderWorld(float partialTicks) {
/*  58 */     ThrowableType throwingType = getTypeFromCurrentItem(mc.field_71439_g);
/*  59 */     if (throwingType == ThrowableType.NONE) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     FlightPath flightPath = new FlightPath(mc.field_71439_g, throwingType);
/*  64 */     while (!flightPath.isCollided()) {
/*  65 */       flightPath.onUpdate();
/*     */       
/*  67 */       this.flightPoint.offer(new Vec3d((FlightPath.access$000(flightPath)).field_72450_a - (mc.func_175598_ae()).field_78730_l, (FlightPath.access$000(flightPath)).field_72448_b - (mc.func_175598_ae()).field_78731_m, 
/*  68 */             (FlightPath.access$000(flightPath)).field_72449_c - (mc.func_175598_ae()).field_78728_n));
/*     */     } 
/*     */     
/*  71 */     boolean bobbing = mc.field_71474_y.field_74336_f;
/*  72 */     mc.field_71474_y.field_74336_f = false;
/*  73 */     mc.field_71460_t.func_78479_a(partialTicks, 0);
/*  74 */     GlStateManager.func_179094_E();
/*  75 */     GlStateManager.func_179090_x();
/*  76 */     GlStateManager.func_179147_l();
/*  77 */     GlStateManager.func_179118_c();
/*  78 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  79 */     GlStateManager.func_179103_j(7425);
/*  80 */     GL11.glLineWidth((float)width.doubleValue());
/*  81 */     GL11.glEnable(2848);
/*  82 */     GL11.glHint(3154, 4354);
/*  83 */     GlStateManager.func_179097_i();
/*  84 */     GL11.glEnable(34383);
/*  85 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  86 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/*  88 */     while (!this.flightPoint.isEmpty()) {
/*  89 */       bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*  90 */       Vec3d head = (Vec3d)this.flightPoint.poll();
/*  91 */       bufferbuilder.func_181662_b(head.field_72450_a, head.field_72448_b, head.field_72449_c).func_181666_a(red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F).func_181675_d();
/*     */       
/*  93 */       if (this.flightPoint.peek() != null) {
/*  94 */         Vec3d point = (Vec3d)this.flightPoint.peek();
/*  95 */         bufferbuilder.func_181662_b(point.field_72450_a, point.field_72448_b, point.field_72449_c).func_181666_a(red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F).func_181675_d();
/*     */       } 
/*     */       
/*  98 */       tessellator.func_78381_a();
/*     */     } 
/*     */     
/* 101 */     GlStateManager.func_179103_j(7424);
/* 102 */     GL11.glDisable(2848);
/* 103 */     GlStateManager.func_179126_j();
/* 104 */     GL11.glDisable(34383);
/* 105 */     GlStateManager.func_179084_k();
/* 106 */     GlStateManager.func_179141_d();
/* 107 */     GlStateManager.func_179098_w();
/* 108 */     GlStateManager.func_179121_F();
/*     */     
/* 110 */     mc.field_71474_y.field_74336_f = bobbing;
/* 111 */     mc.field_71460_t.func_78479_a(partialTicks, 0);
/*     */     
/* 113 */     if (FlightPath.access$100(flightPath)) {
/* 114 */       RayTraceResult hit = FlightPath.access$200(flightPath);
/* 115 */       AxisAlignedBB bb = null;
/*     */       
/* 117 */       if (hit == null) {
/*     */         return;
/*     */       }
/*     */       
/* 121 */       if (hit.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 122 */         BlockPos blockpos = hit.func_178782_a();
/* 123 */         IBlockState iblockstate = mc.field_71441_e.func_180495_p(blockpos);
/*     */         
/* 125 */         if (iblockstate.func_185904_a() != Material.field_151579_a && mc.field_71441_e.func_175723_af().func_177746_a(blockpos)) {
/* 126 */           Vec3d interp = Tracers.interpolateEntity(mc.field_71439_g, mc.func_184121_ak());
/* 127 */           bb = iblockstate.func_185918_c(mc.field_71441_e, blockpos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c);
/*     */         } 
/* 129 */       } else if (hit.field_72313_a == RayTraceResult.Type.ENTITY && hit.field_72308_g != null) {
/* 130 */         AxisAlignedBB entityBB = hit.field_72308_g.func_174813_aQ();
/* 131 */         if (entityBB != null)
/*     */         {
/* 133 */           bb = new AxisAlignedBB(entityBB.field_72340_a - (mc.func_175598_ae()).field_78730_l, entityBB.field_72338_b - (mc.func_175598_ae()).field_78731_m, entityBB.field_72339_c - (mc.func_175598_ae()).field_78728_n, entityBB.field_72336_d - (mc.func_175598_ae()).field_78730_l, entityBB.field_72337_e - (mc.func_175598_ae()).field_78731_m, entityBB.field_72334_f - (mc.func_175598_ae()).field_78728_n);
/*     */         }
/*     */       } 
/*     */       
/* 137 */       if (bb != null) {
/* 138 */         RenderUtil.drawBoundingBox(bb, (float)width.doubleValue(), red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ThrowableType getTypeFromCurrentItem(EntityPlayerSP player) {
/* 144 */     if (player.func_184614_ca() == null) {
/* 145 */       return ThrowableType.NONE;
/*     */     }
/*     */     
/* 148 */     ItemStack itemStack = player.func_184586_b(EnumHand.MAIN_HAND);
/* 149 */     switch (Item.func_150891_b(itemStack.func_77973_b())) {
/*     */       case 261:
/* 151 */         if (player.func_184587_cr())
/* 152 */           return ThrowableType.ARROW; 
/*     */         break;
/*     */       case 346:
/* 155 */         return ThrowableType.FISHING_ROD;
/*     */       case 438:
/*     */       case 441:
/* 158 */         return ThrowableType.POTION;
/*     */       case 384:
/* 160 */         return ThrowableType.EXPERIENCE;
/*     */       case 332:
/*     */       case 344:
/*     */       case 368:
/* 164 */         return ThrowableType.NORMAL;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 169 */     return ThrowableType.NONE;
/*     */   }
/*     */   
/*     */   public static class FlightPath {
/*     */     private EntityPlayerSP shooter;
/*     */     private Vec3d position;
/*     */     private Vec3d motion;
/*     */     private float yaw;
/*     */     private float pitch;
/*     */     private AxisAlignedBB boundingBox;
/*     */     private boolean collided;
/*     */     private RayTraceResult target;
/*     */     private Trajectories.ThrowableType throwableType;
/*     */     
/*     */     FlightPath(EntityPlayerSP player, Trajectories.ThrowableType throwableType) {
/* 184 */       this.shooter = player;
/* 185 */       this.throwableType = throwableType;
/* 186 */       setLocationAndAngles(this.shooter.field_70165_t, this.shooter.field_70163_u + this.shooter.func_70047_e(), this.shooter.field_70161_v, this.shooter.field_70177_z, this.shooter.field_70125_A);
/* 187 */       Vec3d startingOffset = new Vec3d((MathHelper.func_76134_b(this.yaw / 180.0F * 3.1415927F) * 0.16F), 0.1D, (MathHelper.func_76126_a(this.yaw / 180.0F * 3.1415927F) * 0.16F));
/* 188 */       this.position = this.position.func_178788_d(startingOffset);
/* 189 */       setPosition(this.position);
/* 190 */       this
/* 191 */         .motion = new Vec3d((-MathHelper.func_76126_a(this.yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.pitch / 180.0F * 3.1415927F)), -MathHelper.func_76126_a(this.pitch / 180.0F * 3.1415927F), (MathHelper.func_76134_b(this.yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.pitch / 180.0F * 3.1415927F)));
/* 192 */       setThrowableHeading(this.motion, getInitialVelocity());
/*     */     }
/*     */     
/*     */     public void onUpdate() {
/* 196 */       Vec3d prediction = this.position.func_178787_e(this.motion);
/* 197 */       RayTraceResult blockCollision = this.shooter.func_130014_f_().func_147447_a(this.position, prediction, (this.throwableType == Trajectories.ThrowableType.FISHING_ROD), !collidesWithNoBoundingBox(), false);
/*     */       
/* 199 */       if (blockCollision != null) {
/* 200 */         prediction = blockCollision.field_72307_f;
/*     */       }
/*     */       
/* 203 */       onCollideWithEntity(prediction, blockCollision);
/*     */       
/* 205 */       if (this.target != null) {
/* 206 */         this.collided = true;
/* 207 */         setPosition(this.target.field_72307_f);
/*     */         
/*     */         return;
/*     */       } 
/* 211 */       if (this.position.field_72448_b <= 0.0D) {
/* 212 */         this.collided = true;
/*     */         
/*     */         return;
/*     */       } 
/* 216 */       this.position = this.position.func_178787_e(this.motion);
/* 217 */       float motionModifier = 0.99F;
/* 218 */       if (this.shooter.func_130014_f_().func_72875_a(this.boundingBox, Material.field_151586_h)) {
/* 219 */         motionModifier = (this.throwableType == Trajectories.ThrowableType.ARROW) ? 0.6F : 0.8F;
/*     */       }
/*     */       
/* 222 */       if (this.throwableType == Trajectories.ThrowableType.FISHING_ROD) {
/* 223 */         motionModifier = 0.92F;
/*     */       }
/*     */       
/* 226 */       this.motion = new Vec3d(this.motion.field_72450_a * motionModifier, this.motion.field_72448_b * motionModifier, this.motion.field_72449_c * motionModifier);
/* 227 */       this.motion = this.motion.func_178786_a(0.0D, getGravityVelocity(), 0.0D);
/* 228 */       setPosition(this.position);
/*     */     }
/*     */     
/*     */     private boolean collidesWithNoBoundingBox() {
/* 232 */       switch (Trajectories.null.$SwitchMap$me$bebeli555$cookieclient$mods$render$Trajectories$ThrowableType[this.throwableType.ordinal()]) {
/*     */         case 1:
/*     */         case 2:
/* 235 */           return true;
/*     */       } 
/* 237 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private void onCollideWithEntity(Vec3d prediction, RayTraceResult blockCollision) {
/* 242 */       Entity collidingEntity = null;
/* 243 */       RayTraceResult collidingPosition = null;
/*     */       
/* 245 */       double currentDistance = 0.0D;
/* 246 */       List<Entity> collisionEntities = (Minecraft.func_71410_x()).field_71441_e.func_72839_b(this.shooter, this.boundingBox
/* 247 */           .func_72321_a(this.motion.field_72450_a, this.motion.field_72448_b, this.motion.field_72449_c).func_72314_b(1.0D, 1.0D, 1.0D));
/*     */       
/* 249 */       for (Entity entity : collisionEntities) {
/* 250 */         if (!entity.func_70067_L()) {
/*     */           continue;
/*     */         }
/*     */         
/* 254 */         float collisionSize = entity.func_70111_Y();
/* 255 */         AxisAlignedBB expandedBox = entity.func_174813_aQ().func_72321_a(collisionSize, collisionSize, collisionSize);
/* 256 */         RayTraceResult objectPosition = expandedBox.func_72327_a(this.position, prediction);
/*     */         
/* 258 */         if (objectPosition != null) {
/* 259 */           double distanceTo = this.position.func_72438_d(objectPosition.field_72307_f);
/*     */           
/* 261 */           if (distanceTo < currentDistance || currentDistance == 0.0D) {
/* 262 */             collidingEntity = entity;
/* 263 */             collidingPosition = objectPosition;
/* 264 */             currentDistance = distanceTo;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 269 */       if (collidingEntity != null) {
/* 270 */         this.target = new RayTraceResult(collidingEntity, collidingPosition.field_72307_f);
/*     */       } else {
/* 272 */         this.target = blockCollision;
/*     */       } 
/*     */     } private float getInitialVelocity() {
/*     */       float velocity;
/*     */       int useDuration;
/* 277 */       switch (Trajectories.null.$SwitchMap$me$bebeli555$cookieclient$mods$render$Trajectories$ThrowableType[this.throwableType.ordinal()]) {
/*     */         case 3:
/* 279 */           useDuration = this.shooter.func_184586_b(EnumHand.MAIN_HAND).func_77973_b().func_77626_a(this.shooter.func_184586_b(EnumHand.MAIN_HAND)) - this.shooter.func_184605_cv();
/* 280 */           velocity = useDuration / 20.0F;
/* 281 */           velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;
/* 282 */           if (velocity > 1.0F) {
/* 283 */             velocity = 1.0F;
/*     */           }
/*     */           
/* 286 */           return velocity * 2.0F * this.throwableType.getVelocity();
/*     */       } 
/* 288 */       return this.throwableType.getVelocity();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 293 */     private float getGravityVelocity() { return this.throwableType.getGravity(); }
/*     */ 
/*     */     
/*     */     private void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 297 */       this.position = new Vec3d(x, y, z);
/* 298 */       this.yaw = yaw;
/* 299 */       this.pitch = pitch;
/*     */     }
/*     */     
/*     */     private void setPosition(Vec3d position) {
/* 303 */       this.position = new Vec3d(position.field_72450_a, position.field_72448_b, position.field_72449_c);
/* 304 */       double entitySize = ((this.throwableType == Trajectories.ThrowableType.ARROW) ? 0.5D : 0.25D) / 2.0D;
/* 305 */       this.boundingBox = new AxisAlignedBB(position.field_72450_a - entitySize, position.field_72448_b - entitySize, position.field_72449_c - entitySize, position.field_72450_a + entitySize, position.field_72448_b + entitySize, position.field_72449_c + entitySize);
/*     */     }
/*     */     
/*     */     private void setThrowableHeading(Vec3d motion, float velocity) {
/* 309 */       this.motion = new Vec3d(motion.field_72450_a / (float)motion.func_72433_c(), motion.field_72448_b / (float)motion.func_72433_c(), motion.field_72449_c / (float)motion.func_72433_c());
/* 310 */       this.motion = new Vec3d(motion.field_72450_a * velocity, motion.field_72448_b * velocity, motion.field_72449_c * velocity);
/*     */     }
/*     */ 
/*     */     
/* 314 */     public boolean isCollided() { return this.collided; }
/*     */ 
/*     */ 
/*     */     
/* 318 */     public RayTraceResult getCollidingTarget() { return this.target; }
/*     */   }
/*     */   
/*     */   public enum ThrowableType
/*     */   {
/* 323 */     NONE(0.0F, 0.0F),
/* 324 */     ARROW(1.5F, 0.05F),
/* 325 */     POTION(0.5F, 0.05F),
/* 326 */     EXPERIENCE(0.7F, 0.07F),
/* 327 */     FISHING_ROD(1.5F, 0.04F),
/* 328 */     NORMAL(1.5F, 0.03F);
/*     */     
/*     */     private float velocity;
/*     */     private float gravity;
/*     */     
/*     */     ThrowableType(float velocity, float gravity) {
/* 334 */       this.velocity = velocity;
/* 335 */       this.gravity = gravity;
/*     */     }
/*     */ 
/*     */     
/* 339 */     public float getVelocity() { return this.velocity; }
/*     */ 
/*     */ 
/*     */     
/* 343 */     public float getGravity() { return this.gravity; }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Trajectories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */