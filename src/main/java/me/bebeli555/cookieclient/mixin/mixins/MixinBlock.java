/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.mods.render.XRay;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({Block.class})
/*    */ public class MixinBlock
/*    */ {
/*    */   @Inject(method = {"getRenderLayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getRenderLayer(CallbackInfoReturnable<BlockRenderLayer> cir) {
/* 22 */     if (!XRay.isToggled)
/* 23 */       return;  if (XRay.shouldRender((Block)this)) {
/* 24 */       cir.setReturnValue(BlockRenderLayer.CUTOUT);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"isOpaqueCube"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isOpaqueCube(IBlockState blockState, CallbackInfoReturnable<Boolean> cir) {
/* 30 */     if (!XRay.isToggled)
/* 31 */       return;  cir.setReturnValue(Boolean.valueOf(XRay.shouldRender((Block)this)));
/*    */   }
/*    */   
/*    */   @Inject(method = {"shouldSideBeRendered"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void shouldSideBeRendered(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
/* 36 */     if (!XRay.isToggled)
/* 37 */       return;  cir.setReturnValue(Boolean.valueOf(XRay.shouldRender((Block)this)));
/*    */   }
/*    */   
/*    */   @Inject(method = {"getLightValue"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getLightValue(IBlockState state, CallbackInfoReturnable<Integer> cir) {
/* 42 */     if (!XRay.isToggled) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 47 */     if (Mod.mc.field_175622_Z != null && Mod.mc.field_175622_Z.field_71093_bK != -1) {
/* 48 */       cir.setReturnValue(Integer.valueOf(2147483647));
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"getLightOpacity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getLightOpacity(IBlockState state, CallbackInfoReturnable<Integer> cir) {
/* 54 */     if (!XRay.isToggled)
/* 55 */       return;  cir.setReturnValue(Integer.valueOf(2147483647));
/*    */   }
/*    */   
/*    */   @Inject(method = {"getRenderType"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getRenderType(IBlockState state, CallbackInfoReturnable<EnumBlockRenderType> cir) {
/* 60 */     if (!XRay.isToggled)
/* 61 */       return;  if (!XRay.shouldRender((Block)this))
/* 62 */       cir.setReturnValue(EnumBlockRenderType.INVISIBLE); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */