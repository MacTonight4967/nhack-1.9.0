/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ArmorComponent
/*    */   extends HudComponent {
/* 13 */   public ArmorComponent() { super(HudComponent.HudCorner.NONE, "Armor"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 18 */     super.onRender(partialTicks);
/* 19 */     ItemStack[] stacks = { InventoryUtil.getItemStack(36), InventoryUtil.getItemStack(37), InventoryUtil.getItemStack(38), InventoryUtil.getItemStack(39) };
/*    */     
/* 21 */     double scale = getScale();
/* 22 */     GlStateManager.func_179094_E();
/* 23 */     GlStateManager.func_179147_l();
/* 24 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 25 */     RenderHelper.func_74520_c();
/* 26 */     GlStateManager.func_179139_a(scale, scale, scale);
/*    */     
/* 28 */     int x = (int)(((mc.field_71443_c / 4) + 66.0D * scale) / scale);
/* 29 */     int y = (int)(((mc.field_71440_d / 2) - 56.0D * scale) / scale);
/* 30 */     if (scale == 1.5D) {
/* 31 */       x -= 18;
/* 32 */       y -= 15;
/*    */     } 
/*    */     
/* 35 */     for (ItemStack stack : stacks) {
/* 36 */       renderItemAndEffectIntoGUI(stack, x, y);
/* 37 */       renderItemOverlays(mc.field_71466_p, stack, x, y);
/* 38 */       x -= 17;
/*    */     } 
/*    */     
/* 41 */     RenderHelper.func_74518_a();
/* 42 */     GlStateManager.func_179084_k();
/* 43 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 44 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean shouldRender() { return GuiSettings.armor.booleanValue(); }
/*    */ 
/*    */   
/*    */   public static double getScale() {
/* 53 */     scale = 1.0D;
/* 54 */     ScaledResolution resolution = new ScaledResolution(mc);
/* 55 */     if (resolution.func_78325_e() == 4) {
/* 56 */       scale = 2.0D;
/* 57 */     } else if (resolution.func_78325_e() == 1) {
/* 58 */       scale = 0.5D;
/* 59 */     } else if (resolution.func_78325_e() == 3) {
/* 60 */       scale = 1.5D;
/*    */     } 
/*    */     
/* 63 */     return scale;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\ArmorComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */