/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin(value = {net.minecraft.client.renderer.entity.RenderLivingBase.class}, priority = 2147483647)
/*    */ public class MixinRenderLivingBase
/*    */ {
/*    */   private float oldPitch;
/*    */   private float oldPrevPitch;
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
/* 19 */     if (RotationUtil.isRotateSpoofing && entity == Mod.mc.field_71439_g) {
/* 20 */       this.oldPitch = entity.field_70125_A;
/* 21 */       this.oldPrevPitch = entity.field_70127_C;
/*    */       
/* 23 */       Mod.mc.field_71439_g.field_70125_A = RotationUtil.pitch;
/* 24 */       Mod.mc.field_71439_g.field_70127_C = RotationUtil.pitch;
/* 25 */       Mod.mc.field_71439_g.field_70759_as = RotationUtil.yaw;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("RETURN")}, cancellable = true)
/*    */   public void doRenderPost(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
/* 31 */     if (RotationUtil.isRotateSpoofing && entity == Mod.mc.field_71439_g) {
/* 32 */       Mod.mc.field_71439_g.field_70125_A = this.oldPitch;
/* 33 */       Mod.mc.field_71439_g.field_70127_C = this.oldPrevPitch;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinRenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */