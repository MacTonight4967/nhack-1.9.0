/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderMapEvent;
/*    */ import net.minecraft.world.storage.MapData;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.gui.MapItemRenderer.class})
/*    */ public class MixinMapItemRenderer
/*    */ {
/*    */   @Inject(method = {"renderMap"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void render(MapData mapdataIn, boolean noOverlayRendering, CallbackInfo info) {
/* 17 */     RenderMapEvent event = new RenderMapEvent();
/* 18 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 20 */     if (event.isCancelled())
/* 21 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinMapItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */