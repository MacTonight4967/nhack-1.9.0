/*    */ package me.bebeli555.cookieclient.events.entity;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityJumpEvent
/*    */   extends Cancellable {
/*    */   public EntityPlayer entity;
/*    */   
/* 10 */   public EntityJumpEvent(EntityPlayer entity) { this.entity = entity; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\entity\EntityJumpEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */