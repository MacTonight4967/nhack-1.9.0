/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ 
/*    */ public class Flight extends Mod {
/* 12 */   private static float defaultFlySpeed = -1.0F;
/*    */   
/* 14 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Double.valueOf(0.05D), new String[] { "Fly speed. Vanilla speed = 0.05" });
/*    */ 
/*    */   
/* 17 */   public Flight() { super(Group.MOVEMENT, "Flight", new String[] { "Allows you to fly" }); }
/*    */   
/*    */   @EventHandler
/* 20 */   private Listener<PlayerMotionUpdateEvent> onMotion = new Listener(event -> {
/*    */         
/* 22 */         if (defaultFlySpeed == -1.0F) {
/* 23 */           defaultFlySpeed = mc.field_71439_g.field_71075_bZ.func_75093_a();
/*    */         }
/*    */         
/* 26 */         mc.field_71439_g.field_71075_bZ.field_75100_b = true;
/* 27 */         mc.field_71439_g.field_71075_bZ.func_75092_a((float)speed.doubleValue());
/*    */       }new java.util.function.Predicate[0]);
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 32 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/* 33 */     mc.field_71439_g.field_71075_bZ.func_75092_a(defaultFlySpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */