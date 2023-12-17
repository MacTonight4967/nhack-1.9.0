/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDamageBlockEvent2;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ 
/*    */ public class SpeedMine extends Mod {
/* 13 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Double.valueOf(0.1D), new String[] { "How much more damage it does to the block", "Higher = more" });
/* 14 */   public static Setting startDelay = new Setting(Mode.INTEGER, "StartDelay", Integer.valueOf(0), new String[] { "How long the start delay will be", "0 = no delay" });
/*    */ 
/*    */   
/* 17 */   public SpeedMine() { super(Group.WORLD, "SpeedMine", new String[] { "Mine blocks faster" }); }
/*    */   
/*    */   @EventHandler
/* 20 */   private Listener<PlayerDamageBlockEvent2> onDamageBlock = new Listener(event -> 
/*    */       
/* 22 */       mc.field_71442_b.field_78770_f = (float)(mc.field_71442_b.field_78770_f + speed.doubleValue() / 10.0D), new java.util.function.Predicate[0]);
/*    */   
/*    */   @EventHandler
/* 25 */   private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener(event -> 
/*    */       
/* 27 */       mc.field_71442_b.field_78781_i = startDelay.intValue(), new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\SpeedMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */