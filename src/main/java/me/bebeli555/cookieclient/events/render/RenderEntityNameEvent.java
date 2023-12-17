/*    */ package me.bebeli555.cookieclient.events.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ 
/*    */ public class RenderEntityNameEvent
/*    */   extends Cancellable {
/*    */   public AbstractClientPlayer entity;
/*    */   public double x;
/*    */   public double y;
/*    */   
/*    */   public RenderEntityNameEvent(AbstractClientPlayer entity, double x, double y, double z, String name, double distance) {
/* 13 */     this.entity = entity;
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/* 17 */     this.name = name;
/* 18 */     this.distance = distance;
/*    */   }
/*    */   
/*    */   public double z;
/*    */   public String name;
/*    */   public double distance;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\render\RenderEntityNameEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */