/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.entity.EntitySaddledEvent;
/*    */ import me.bebeli555.cookieclient.events.entity.SteerEntityEvent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.entity.passive.AbstractHorse.class})
/*    */ public class MixinAbstractHorse
/*    */ {
/*    */   @Inject(method = {"canBeSteered"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
/* 17 */     SteerEntityEvent event = new SteerEntityEvent();
/* 18 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 20 */     if (event.isCancelled()) {
/* 21 */       cir.cancel();
/* 22 */       cir.setReturnValue(Boolean.valueOf(true));
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"isHorseSaddled"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isHorseSaddled(CallbackInfoReturnable<Boolean> cir) {
/* 28 */     EntitySaddledEvent event = new EntitySaddledEvent();
/* 29 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 31 */     if (event.isCancelled()) {
/* 32 */       cir.cancel();
/* 33 */       cir.setReturnValue(Boolean.valueOf(true));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinAbstractHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */