/*    */ package me.bebeli555.cookieclient.events.entity;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class AttackEntityEvent
/*    */   extends Cancellable {
/*    */   public Entity target;
/*    */   
/* 10 */   public AttackEntityEvent(Entity target) { this.target = target; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\entity\AttackEntityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */