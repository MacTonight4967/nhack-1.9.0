/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.entity.GetEntitiesEvent;
/*    */ import me.bebeli555.cookieclient.events.render.RenderHurtcamEvent;
/*    */ import me.bebeli555.cookieclient.events.render.RenderUpdateLightMapEvent;
/*    */ import me.bebeli555.cookieclient.events.render.SetupFogEvent;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.EntityRenderer.class})
/*    */ public class MixinEntityRenderer
/*    */ {
/*    */   @Inject(method = {"setupFog"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void setupFog(int startCoords, float partialTicks, CallbackInfo callbackInfo) {
/* 30 */     SetupFogEvent event = new SetupFogEvent();
/* 31 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 33 */     if (event.isCancelled()) {
/* 34 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
/*    */   public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
/* 41 */     GetEntitiesEvent event = new GetEntitiesEvent();
/* 42 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 44 */     if (event.isCancelled()) {
/* 45 */       return new ArrayList();
/*    */     }
/* 47 */     return worldClient.func_175674_a(entityIn, boundingBox, predicate);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void hurtCameraEffect(float ticks, CallbackInfo info) {
/* 53 */     RenderHurtcamEvent event = new RenderHurtcamEvent(ticks);
/* 54 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 56 */     if (event.isCancelled()) {
/* 57 */       info.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"updateLightmap"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void updateLightmap(float partialTicks, CallbackInfo info) {
/* 63 */     RenderUpdateLightMapEvent event = new RenderUpdateLightMapEvent();
/* 64 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 66 */     if (event.isCancelled()) {
/* 67 */       info.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderWorldPass"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.AFTER)})
/* 73 */   private void renderWorldPassPost(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) { RenderUtil.updateModelViewProjectionMatrix(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */