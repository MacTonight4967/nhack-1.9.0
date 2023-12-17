/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMoveEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class LiquidSpeed extends Mod {
/* 15 */   public static Setting speedAdd = new Setting(Mode.DOUBLE, "SpeedAdd", Double.valueOf(0.18D), new String[] { "How much speed to add", "Higher = faster" });
/* 16 */   public static Setting upSpeed = new Setting(Mode.DOUBLE, "UpSpeed", Double.valueOf(0.1D), new String[] { "How fast it goes up when pressing jump key", "This is added to the vanilla speed" });
/* 17 */   public static Setting downSpeed = new Setting(Mode.DOUBLE, "DownSpeed", Double.valueOf(0.1D), new String[] { "How fast it goes down when pressing sneak key", "This is added to the vanilla speed" });
/*    */   
/*    */   public LiquidSpeed() {
/* 20 */     super(Group.MOVEMENT, "LiquidSpeed", new String[] { "Move faster in water/lava" });
/*    */ 
/*    */     
/* 23 */     this.onMove = new Listener(event -> {
/*    */           
/* 25 */           if (mc.field_71439_g == null || (getBlock(getPlayerPos()) != Blocks.field_150355_j && getBlock(getPlayerPos()) != Blocks.field_150353_l)) {
/*    */             return;
/*    */           }
/*    */           
/* 29 */           if (EntitySpeed.isInputting()) {
/* 30 */             double yawRad = Math.toRadians(mc.field_71439_g.field_70177_z - Freecam.getRotationFromVec(new Vec3d(-mc.field_71439_g.field_70702_br, 0.0D, mc.field_71439_g.field_191988_bg))[0]);
/* 31 */             event.x += -Math.sin(yawRad) * speedAdd.doubleValue();
/* 32 */             event.z += Math.cos(yawRad) * speedAdd.doubleValue();
/*    */           } 
/*    */           
/* 35 */           if (mc.field_71474_y.field_74314_A.func_151470_d()) event.y += upSpeed.doubleValue(); 
/* 36 */           if (mc.field_71474_y.field_74311_E.func_151470_d()) event.y -= downSpeed.doubleValue();
/*    */           
/* 38 */           event.cancel();
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerMoveEvent> onMove;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\LiquidSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */