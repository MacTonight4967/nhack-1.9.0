/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import java.util.List;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderTooltipEvent;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.gui.GuiScreen.class})
/*    */ public class MixinGuiScreen
/*    */ {
/*    */   @Shadow
/*    */   protected List<GuiButton> field_146292_n;
/*    */   @Shadow
/*    */   public int field_146294_l;
/*    */   
/*    */   @Inject(method = {"renderToolTip"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo callbackInfo) {
/* 27 */     RenderTooltipEvent event = new RenderTooltipEvent(stack, x, y);
/* 28 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 30 */     if (event.isCancelled())
/* 31 */       callbackInfo.cancel(); 
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   public int field_146295_m;
/*    */   @Shadow
/*    */   protected FontRenderer field_146289_q;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinGuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */