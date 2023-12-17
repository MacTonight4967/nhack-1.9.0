/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderArmorLayerEvent;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.entity.layers.LayerArmorBase.class})
/*    */ public class MixinLayerArmorBase
/*    */ {
/*    */   @Inject(method = {"renderArmorLayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderArmorLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo info) {
/* 18 */     RenderArmorLayerEvent event = new RenderArmorLayerEvent();
/* 19 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 21 */     if (event.isCancelled())
/* 22 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinLayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */