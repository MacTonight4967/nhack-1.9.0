/*   */ package me.bebeli555.cookieclient.events.render;
/*   */ 
/*   */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*   */ 
/*   */ public class RenderHurtcamEvent
/*   */   extends Cancellable {
/*   */   public float ticks;
/*   */   
/* 9 */   public RenderHurtcamEvent(float ticks) { this.ticks = ticks; }
/*   */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\render\RenderHurtcamEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */