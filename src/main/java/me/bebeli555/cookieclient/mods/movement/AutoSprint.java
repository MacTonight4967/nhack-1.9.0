/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class AutoSprint
/*    */   extends Mod {
/* 11 */   public AutoSprint() { super(Group.MOVEMENT, "AutoSprint", new String[] { "Makes you allways sprint when walking" }); }
/*    */   
/*    */   @EventHandler
/* 14 */   private Listener<PlayerUpdateMoveStatePostEvent> onUpdate = new Listener(event -> 
/*    */       
/* 16 */       mc.field_71439_g.func_70031_b(true), new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\AutoSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */