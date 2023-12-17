/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ 
/*    */ public class CoordsComponent
/*    */   extends HudComponent
/*    */ {
/*  9 */   public CoordsComponent() { super(HudComponent.HudCorner.BOTTOM_LEFT, "Coords"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 14 */     super.onRender(partialTicks);
/* 15 */     float amount = 0.0F;
/* 16 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
/* 17 */       amount = 14.0F;
/*    */     }
/*    */     
/* 20 */     String text = g + "XYZ " + w + decimal(mc.field_175622_Z.field_70165_t, 1) + g + ", " + w + decimal(mc.field_175622_Z.field_70163_u, 1) + g + ", " + w + decimal(mc.field_175622_Z.field_70161_v, 1) + " ";
/* 21 */     if (GuiSettings.netherCoords.booleanValue()) {
/* 22 */       if (mc.field_71439_g.field_71093_bK == 0) {
/* 23 */         text = text + g + "[" + w + decimal(mc.field_175622_Z.field_70165_t / 8.0D, 1) + g + ", " + w + decimal(mc.field_175622_Z.field_70161_v / 8.0D, 1) + g + "]";
/*    */       } else {
/* 25 */         text = text + g + "[" + w + decimal(mc.field_175622_Z.field_70165_t * 8.0D, 1) + g + ", " + w + decimal(mc.field_175622_Z.field_70161_v * 8.0D, 1) + g + "]";
/*    */       } 
/*    */     }
/*    */     
/* 29 */     drawString(text, 0.0F, -amount, -16777216, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public boolean shouldRender() { return GuiSettings.coords.booleanValue(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\CoordsComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */