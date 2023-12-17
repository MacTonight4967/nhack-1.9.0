/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.ItemRenderer.class})
/*    */ public class MixinItemRenderer
/*    */ {
/*    */   @Inject(method = {"rotateArm"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void rotateArm(float partialTicks, CallbackInfo ci) {
/* 17 */     if (Freecam.isToggled && Freecam.camera != null)
/* 18 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */