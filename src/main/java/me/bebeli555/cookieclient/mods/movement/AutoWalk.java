/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class AutoWalk
/*    */   extends Mod {
/* 11 */   public AutoWalk() { super(Group.MOVEMENT, "AutoWalk", new String[] { "Automatically walks forward" }); }
/*    */   
/*    */   @EventHandler
/* 14 */   private Listener<PlayerUpdateMoveStatePostEvent> onUpdate = new Listener(event -> 
/*    */       
/* 16 */       mc.field_71439_g.field_71158_b.field_192832_b++, new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\AutoWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */