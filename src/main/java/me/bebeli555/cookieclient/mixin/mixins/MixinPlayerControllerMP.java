/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.block.GetBlockReachDistanceEvent;
/*    */ import me.bebeli555.cookieclient.events.other.ProcessRightClickBlockEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDamageBlockEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDamageBlockEvent2;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDestroyBlockEvent;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.multiplayer.PlayerControllerMP.class})
/*    */ public class MixinPlayerControllerMP
/*    */ {
/*    */   @Inject(method = {"clickBlock"}, at = {@At("HEAD")}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
/*    */   private void onPlayerDamageBlock(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
/* 29 */     PlayerDamageBlockEvent event = new PlayerDamageBlockEvent(loc, face);
/* 30 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 32 */     if (event.isCancelled()) {
/* 33 */       cir.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"getBlockReachDistance"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getBlockReachDistance(CallbackInfoReturnable<Float> callbackInfo) {
/* 39 */     GetBlockReachDistanceEvent event = new GetBlockReachDistanceEvent();
/* 40 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 42 */     if (event.reach > 0.0F) {
/* 43 */       callbackInfo.setReturnValue(Float.valueOf(event.reach));
/* 44 */       callbackInfo.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDestroyBlock"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
/* 50 */     PlayerDestroyBlockEvent event = new PlayerDestroyBlockEvent(pos);
/* 51 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 53 */     if (event.isCancelled()) {
/* 54 */       info.setReturnValue(Boolean.valueOf(false));
/* 55 */       info.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"processRightClickBlock"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void processRightClickBlock(EntityPlayerSP player, WorldClient world, BlockPos pos, EnumFacing facing, Vec3d vec, EnumHand hand, CallbackInfoReturnable<EnumActionResult> info) {
/* 61 */     ProcessRightClickBlockEvent event = new ProcessRightClickBlockEvent(player, world, pos, facing, vec, hand);
/* 62 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 64 */     if (event.isCancelled()) {
/* 65 */       info.setReturnValue(EnumActionResult.SUCCESS);
/* 66 */       info.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onPlayerDamageBlock2(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> p_Info) {
/* 72 */     PlayerDamageBlockEvent2 event = new PlayerDamageBlockEvent2(posBlock, directionFacing);
/* 73 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 75 */     if (event.isCancelled()) {
/* 76 */       p_Info.setReturnValue(Boolean.valueOf(false));
/* 77 */       p_Info.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */