/*   */ package me.bebeli555.cookieclient.events.render;
/*   */ 
/*   */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*   */ 
/*   */ public class GetRainStrenghtEvent
/*   */   extends Cancellable {
/*   */   public float value;
/*   */   
/* 9 */   public GetRainStrenghtEvent(float value) { this.value = value; }
/*   */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\render\GetRainStrenghtEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */