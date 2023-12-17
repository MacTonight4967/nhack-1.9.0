/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.entity.SteerEntityEvent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.entity.passive.EntityLlama.class})
/*    */ public class MixinEntityLlama
/*    */ {
/*    */   @Inject(method = {"canBeSteered"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
/* 16 */     SteerEntityEvent event = new SteerEntityEvent();
/* 17 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 19 */     if (event.isCancelled()) {
/* 20 */       cir.cancel();
/* 21 */       cir.setReturnValue(Boolean.valueOf(true));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntityLlama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */