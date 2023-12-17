/*    */ package me.bebeli555.cookieclient.events.entity;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class EntityPushEvent
/*    */   extends Cancellable {
/*    */   public Entity entity;
/*    */   
/* 10 */   public EntityPushEvent(Entity entity) { this.entity = entity; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\entity\EntityPushEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */