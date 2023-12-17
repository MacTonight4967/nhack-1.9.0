/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.gui.GuiIngame.class})
/*    */ public class MixinGuiIngame
/*    */ {
/*    */   @Inject(method = {"renderPotionEffects"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderPotionEffects(ScaledResolution resolution, CallbackInfo info) {
/* 16 */     if (GuiSettings.hud.booleanValue() && GuiSettings.potions.booleanValue())
/* 17 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinGuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */