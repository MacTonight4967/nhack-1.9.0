/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.hud.components.LagNotifierComponent;
/*    */ 
/*    */ public class Timer
/*    */   extends Mod {
/* 14 */   public static Setting tps = new Setting(Mode.DOUBLE, "TPS", Integer.valueOf(20), new String[] { "The clientsided tps", "Lower = slower. Higher = faster" });
/* 15 */   public static Setting sync = new Setting(Mode.BOOLEAN, "Sync", Boolean.valueOf(false), new String[] { "Sync client-sided tps with server tps" });
/*    */ 
/*    */   
/* 18 */   public Timer() { super(Group.WORLD, "Timer", new String[] { "Changes the client-sided TPS" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostInit() {
/* 23 */     tps.addValueChangedListener(new Setting.ValueChangedListener(this, false) {
/*    */           public void valueChanged() {
/* 25 */             if (Timer.tps.doubleValue() < 1.0D) {
/* 26 */               Timer.this.sendMessage("You cant put the timer speed to lower than 1 or the game will break", false);
/* 27 */               cancel();
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void onDisabled() { mc.field_71428_T.field_194149_e = 50.0F; }
/*    */   
/*    */   @EventHandler
/* 38 */   private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener(event -> {
/*    */         
/* 40 */         if (sync.booleanValue()) {
/* 41 */           mc.field_71428_T.field_194149_e = (int)(1000.0D / LagNotifierComponent.getTps());
/*    */         } else {
/* 43 */           mc.field_71428_T.field_194149_e = (int)(1000.0D / tps.doubleValue());
/*    */         } 
/*    */       }new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */