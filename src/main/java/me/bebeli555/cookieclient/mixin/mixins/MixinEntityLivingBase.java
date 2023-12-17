/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.other.IsPotionEffectActiveEvent;
/*    */ import net.minecraft.potion.Potion;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.entity.EntityLivingBase.class})
/*    */ public class MixinEntityLivingBase
/*    */ {
/*    */   @Inject(method = {"isPotionActive"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isPotionActive(Potion potionIn, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/* 17 */     IsPotionEffectActiveEvent event = new IsPotionEffectActiveEvent(potionIn);
/* 18 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 20 */     if (event.isCancelled())
/* 21 */       callbackInfoReturnable.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */