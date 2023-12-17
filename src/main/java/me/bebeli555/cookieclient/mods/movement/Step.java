/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class Step extends Mod {
/* 16 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Double.valueOf(0.25D), new String[] { "How fast it goes forward" });
/*    */   
/*    */   public Step() {
/* 19 */     super(Group.MOVEMENT, "Step", new String[] { "Allows you to walk up blocks like stairs" });
/*    */ 
/*    */     
/* 22 */     this.onMotionUpdate = new Listener(event -> {
/*    */           
/* 24 */           if (mc.field_71439_g.field_70123_F && mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70143_R == 0.0F && !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.field_71158_b.field_78901_c) {
/* 25 */             AxisAlignedBB box = mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 0.05D, 0.0D).func_186662_g(0.05D);
/* 26 */             if (!mc.field_71441_e.func_184144_a(mc.field_71439_g, box.func_72317_d(0.0D, 1.0D, 0.0D)).isEmpty()) {
/*    */               return;
/*    */             }
/*    */             
/* 30 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.41999998688698D, mc.field_71439_g.field_70161_v, true));
/* 31 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.7531999805211997D, mc.field_71439_g.field_70161_v, true));
/* 32 */             mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.7531999805211997D, mc.field_71439_g.field_70161_v);
/*    */             
/* 34 */             double yawRad = Math.toRadians(mc.field_71439_g.field_70177_z - Freecam.getRotationFromVec(new Vec3d(-mc.field_71439_g.field_70702_br, 0.0D, mc.field_71439_g.field_191988_bg))[0]);
/* 35 */             if (EntitySpeed.isInputting()) {
/* 36 */               mc.field_71439_g.field_70159_w = -Math.sin(yawRad) * speed.doubleValue();
/* 37 */               mc.field_71439_g.field_70179_y = Math.cos(yawRad) * speed.doubleValue();
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerMotionUpdateEvent> onMotionUpdate;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */