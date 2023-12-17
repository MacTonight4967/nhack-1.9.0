/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStateEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import net.minecraft.util.MovementInput;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin(value = {net.minecraft.util.MovementInputFromOptions.class}, priority = 10000)
/*    */ public class MixinMovementInputFromOptions
/*    */   extends MovementInput
/*    */ {
/*    */   @Inject(method = {"updatePlayerMoveState"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void updatePlayerMoveState(CallbackInfo callback) {
/* 19 */     PlayerUpdateMoveStateEvent event = new PlayerUpdateMoveStateEvent();
/* 20 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 22 */     if (event.isCancelled()) {
/* 23 */       callback.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"updatePlayerMoveState"}, at = {@At("RETURN")}, cancellable = true)
/*    */   public void updatePlayerMoveStatePost(CallbackInfo callback) {
/* 29 */     PlayerUpdateMoveStatePostEvent event = new PlayerUpdateMoveStatePostEvent();
/* 30 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 32 */     if (event.isCancelled())
/* 33 */       callback.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinMovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */