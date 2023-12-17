/*    */ package me.bebeli555.cookieclient.events.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class RenderEntityEvent
/*    */   extends Cancellable {
/*    */   public Entity entity;
/*    */   public ICamera camera;
/*    */   
/*    */   public RenderEntityEvent(Entity entity, ICamera camera, double x, double y, double z) {
/* 13 */     this.entity = entity;
/* 14 */     this.camera = camera;
/* 15 */     this.z = z;
/*    */   }
/*    */   
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\render\RenderEntityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */