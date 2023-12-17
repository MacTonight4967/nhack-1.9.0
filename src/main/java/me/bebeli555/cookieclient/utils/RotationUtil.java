/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
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
/*     */ public class RotationUtil
/*     */   extends Mod
/*     */ {
/*     */   public static boolean isRotateSpoofing;
/*     */   
/*     */   public RotationUtil() {
/*  81 */     this.onPacket = new Listener(event -> {
/*     */           
/*  83 */           if (event.packet instanceof CPacketPlayer.Rotation) {
/*  84 */             CPacketPlayer.Rotation packet = (CPacketPlayer.Rotation)event.packet;
/*  85 */             packet.field_149476_e = yaw;
/*  86 */             packet.field_149473_f = pitch;
/*  87 */           } else if (event.packet instanceof CPacketPlayer.PositionRotation) {
/*  88 */             CPacketPlayer.PositionRotation packet = (CPacketPlayer.PositionRotation)event.packet;
/*  89 */             packet.field_149476_e = yaw;
/*  90 */             packet.field_149473_f = pitch;
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  94 */     this.onMotionUpdate = new Listener(event -> 
/*     */         
/*  96 */         Mod.mc.field_71439_g.field_70759_as = yaw, new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   public static RotationUtil rotationUtil = new RotationUtil();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/* 103 */     if (yawMinusCount < 10) {
/* 104 */       yawMinusCount++;
/* 105 */       mc.field_71439_g.field_70177_z = (float)(mc.field_71439_g.field_70177_z - 0.005D);
/* 106 */     } else if (yawPlusCount < 10) {
/* 107 */       yawPlusCount++;
/* 108 */       mc.field_71439_g.field_70177_z = (float)(mc.field_71439_g.field_70177_z + 0.005D);
/*     */     } else {
/* 110 */       yawMinusCount = 0;
/* 111 */       yawPlusCount = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float yaw;
/*     */   public static float pitch;
/*     */   private static int yawPlusCount;
/*     */   private static int yawMinusCount;
/*     */   @EventHandler
/*     */   private Listener<PacketEvent> onPacket;
/*     */   @EventHandler
/*     */   private Listener<PlayerMotionUpdateEvent> onMotionUpdate;
/*     */   
/*     */   public static void rotate(Vec3d vec, boolean sendPacket) {
/*     */     float[] rotations = getRotations(vec);
/*     */     if (sendPacket)
/*     */       mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.field_71439_g.field_70122_E)); 
/*     */     mc.field_71439_g.field_70177_z = rotations[0];
/*     */     mc.field_71439_g.field_70125_A = rotations[1];
/*     */   }
/*     */   
/*     */   public static void rotateSpoof(Vec3d vec) {
/*     */     float[] rotations = getRotations(vec);
/*     */     yaw = rotations[0];
/*     */     pitch = rotations[1];
/*     */     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(yaw, pitch, mc.field_71439_g.field_70122_E));
/*     */     isRotateSpoofing = true;
/*     */     Mod.EVENT_BUS.subscribe(rotationUtil);
/*     */     MinecraftForge.EVENT_BUS.register(rotationUtil);
/*     */   }
/*     */   
/*     */   public static void rotateSpoofNoPacket(Vec3d vec) {
/*     */     float[] rotations = getRotations(vec);
/*     */     yaw = rotations[0];
/*     */     pitch = rotations[1];
/*     */     isRotateSpoofing = true;
/*     */     Mod.EVENT_BUS.subscribe(rotationUtil);
/*     */     MinecraftForge.EVENT_BUS.register(rotationUtil);
/*     */   }
/*     */   
/*     */   public static void stopRotating() {
/*     */     isRotateSpoofing = false;
/*     */     Mod.EVENT_BUS.unsubscribe(rotationUtil);
/*     */     MinecraftForge.EVENT_BUS.unregister(rotationUtil);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Vec3d vec) {
/*     */     Vec3d eyesPos = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */     double diffX = vec.field_72450_a - eyesPos.field_72450_a;
/*     */     double diffY = vec.field_72448_b - eyesPos.field_72448_b;
/*     */     double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
/*     */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/*     */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*     */     return new float[] { mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A) };
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */