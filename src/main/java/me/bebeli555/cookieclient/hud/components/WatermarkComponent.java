/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ 
/*    */ 
/*    */ public class WatermarkComponent
/*    */   extends HudComponent
/*    */ {
/* 11 */   public WatermarkComponent() { super(HudComponent.HudCorner.TOP_LEFT, "Watermark"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 16 */     super.onRender(partialTicks);
/* 17 */     drawString(ChatFormatting.BLUE + "nhack" + w + " v" + "1.01", 0.0F, 0.0F, -1, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public boolean shouldRender() { return GuiSettings.waterMark.booleanValue(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\WatermarkComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */