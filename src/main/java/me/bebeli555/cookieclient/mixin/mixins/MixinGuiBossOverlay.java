/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderBossHealthEvent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.gui.GuiBossOverlay.class})
/*    */ public class MixinGuiBossOverlay
/*    */ {
/*    */   @Inject(method = {"renderBossHealth"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderBossHealth(CallbackInfo info) {
/* 16 */     RenderBossHealthEvent event = new RenderBossHealthEvent();
/* 17 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 19 */     if (event.isCancelled())
/* 20 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinGuiBossOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */