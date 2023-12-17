/*    */ package me.bebeli555.cookieclient.gui;
/*    */ 
/*    */ import me.bebeli555.cookieclient.hud.components.CoordsComponent;
/*    */ import me.bebeli555.cookieclient.mods.misc.AutoReconnect;
/*    */ import me.bebeli555.cookieclient.utils.InformationUtil;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.multiplayer.GuiConnecting;
/*    */ 
/*    */ public class ReconnectButton extends GuiButton {
/* 12 */   public static Timer timer = new Timer();
/*    */   
/*    */   public ReconnectButton(int buttonId, int x, int y, String buttonText) {
/* 15 */     super(buttonId, x, y, buttonText);
/* 16 */     timer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/* 21 */     super.func_191745_a(mc, mouseX, mouseY, partialTicks);
/*    */     
/* 23 */     if (this.field_146125_m) {
/* 24 */       if (AutoReconnect.module.isOn()) {
/* 25 */         this.field_146126_j = "AutoReconnect: " + CoordsComponent.decimal((Math.abs(timer.ms + (long)AutoReconnect.delay.doubleValue() * 1000L) - System.currentTimeMillis()) / 1000.0D, 1);
/*    */       } else {
/* 27 */         this.field_146126_j = "AutoReconnect";
/*    */       } 
/*    */       
/* 30 */       if (AutoReconnect.module.isOn() && timer.hasPassed((int)(AutoReconnect.delay.doubleValue() * 1000.0D)) && !InformationUtil.lastIp.isEmpty() && InformationUtil.lastPort != -1) {
/* 31 */         mc.func_147108_a(new GuiConnecting(null, mc, InformationUtil.lastIp, InformationUtil.lastPort));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void clicked() {
/* 37 */     AutoReconnect.module.toggle();
/* 38 */     timer.reset();
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\ReconnectButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */