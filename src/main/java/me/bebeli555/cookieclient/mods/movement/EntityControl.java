/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.entity.EntitySaddledEvent;
/*    */ import me.bebeli555.cookieclient.events.entity.SteerEntityEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class EntityControl extends Mod {
/*    */   public EntityControl() {
/* 12 */     super(Group.MOVEMENT, "EntityControl", new String[] { "Allows you to control entities without saddle" });
/*    */ 
/*    */     
/* 15 */     this.onSteerEntity = new Listener(event -> 
/*    */         
/* 17 */         event.cancel(), new java.util.function.Predicate[0]);
/*    */ 
/*    */     
/* 20 */     this.onEntitySaddled = new Listener(event -> 
/*    */         
/* 22 */         event.cancel(), new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<SteerEntityEvent> onSteerEntity;
/*    */   @EventHandler
/*    */   private Listener<EntitySaddledEvent> onEntitySaddled;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\EntityControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */