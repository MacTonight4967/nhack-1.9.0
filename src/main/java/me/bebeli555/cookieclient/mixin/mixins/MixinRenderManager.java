/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderEntityEvent;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.entity.RenderManager.class})
/*    */ public class MixinRenderManager
/*    */ {
/*    */   @Inject(method = {"shouldRender"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isPotionActive(Entity entityIn, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> callback) {
/* 18 */     RenderEntityEvent event = new RenderEntityEvent(entityIn, camera, camX, camY, camZ);
/* 19 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 21 */     if (event.isCancelled())
/* 22 */       callback.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinRenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */