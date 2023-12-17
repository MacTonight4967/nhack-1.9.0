/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.block.CanCollideEvent;
/*    */ import me.bebeli555.cookieclient.events.block.LiquidCollisionEvent;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.block.BlockLiquid.class})
/*    */ public class MixinBlockLiquid
/*    */ {
/*    */   @Inject(method = {"getCollisionBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> info) {
/* 23 */     LiquidCollisionEvent event = new LiquidCollisionEvent(pos);
/* 24 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 26 */     if (event.isCancelled()) {
/* 27 */       info.setReturnValue(event.boundingBox);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"canCollideCheck"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canCollideCheck(IBlockState state, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> info) {
/* 33 */     CanCollideEvent event = new CanCollideEvent(state);
/* 34 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 36 */     if (event.isCancelled())
/* 37 */       info.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinBlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */