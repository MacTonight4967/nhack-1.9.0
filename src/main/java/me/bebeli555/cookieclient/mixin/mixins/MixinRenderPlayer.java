/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderEntityNameEvent;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.entity.RenderPlayer.class})
/*    */ public abstract class MixinRenderPlayer
/*    */   extends RenderLivingBase<AbstractClientPlayer>
/*    */ {
/* 22 */   public MixinRenderPlayer(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) { super(renderManagerIn, modelBaseIn, shadowSizeIn); }
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_177137_d(AbstractClientPlayer paramAbstractClientPlayer);
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderViewEntity:Lnet/minecraft/entity/Entity;")})
/*    */   public void doRenderGetRenderViewEntity(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 29 */     if (Freecam.isToggled && !Mod.mc.field_175622_Z.equals(entity)) {
/* 30 */       double renderY = y;
/*    */       
/* 32 */       if (entity.func_70093_af()) {
/* 33 */         renderY = y - 0.125D;
/*    */       }
/*    */       
/* 36 */       func_177137_d(entity);
/* 37 */       GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
/* 38 */       func_76986_a(entity, x, renderY, z, entityYaw, partialTicks);
/* 39 */       GlStateManager.func_187440_b(GlStateManager.Profile.PLAYER_SKIN);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderEntityName"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
/* 45 */     RenderEntityNameEvent event = new RenderEntityNameEvent(entityIn, x, y, z, name, distanceSq);
/* 46 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 48 */     if (event.isCancelled())
/* 49 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */