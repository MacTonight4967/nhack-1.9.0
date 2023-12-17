/*    */ package me.bebeli555.cookieclient.events.other;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ public class IsPotionEffectActiveEvent
/*    */   extends Cancellable {
/*    */   public Potion potion;
/*    */   
/* 10 */   public IsPotionEffectActiveEvent(Potion potion) { this.potion = potion; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\other\IsPotionEffectActiveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */