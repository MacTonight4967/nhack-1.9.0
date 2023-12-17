/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.ReconnectButton;
/*    */ import me.bebeli555.cookieclient.utils.InformationUtil;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.multiplayer.GuiConnecting;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin(value = {net.minecraft.client.gui.GuiDisconnected.class}, priority = 2147483647)
/*    */ public class MixinGuiDisconnected
/*    */   extends MixinGuiScreen {
/*    */   @Shadow
/*    */   public int field_175353_i;
/*    */   
/*    */   @Inject(method = {"initGui"}, at = {@At("RETURN")})
/*    */   public void initGui(CallbackInfo info) {
/* 23 */     this.field_146292_n.clear();
/*    */     
/* 25 */     this.field_146292_n.add(new GuiButton(false, this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b, this.field_146295_m - 30), I18n.func_135052_a("gui.toMenu", new Object[0])));
/* 26 */     this.field_146292_n.add(new GuiButton('ʽ', this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 20, this.field_146295_m - 10), "Reconnect"));
/* 27 */     this.field_146292_n.add(new ReconnectButton('ʾ', this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 40, this.field_146295_m + 10), "AutoReconnect"));
/*    */   }
/*    */   
/*    */   @Inject(method = {"drawScreen"}, at = {@At("RETURN")})
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
/* 32 */     if (this.field_146292_n.size() > 3) {
/* 33 */       initGui(null);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"actionPerformed"}, at = {@At("RETURN")})
/*    */   protected void actionPerformed(GuiButton button, CallbackInfo info) {
/* 39 */     if (button.field_146127_k == 702) {
/* 40 */       ReconnectButton.clicked();
/* 41 */     } else if (button.field_146127_k == 701) {
/* 42 */       Mod.mc.func_147108_a(new GuiConnecting(null, Mod.mc, InformationUtil.lastIp, InformationUtil.lastPort));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinGuiDisconnected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */