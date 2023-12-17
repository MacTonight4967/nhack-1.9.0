/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class DirectionComponent
/*    */   extends HudComponent
/*    */ {
/* 10 */   public DirectionComponent() { super(HudComponent.HudCorner.BOTTOM_LEFT, "Direction"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 15 */     super.onRender(partialTicks);
/* 16 */     int amount = 0;
/* 17 */     if (this.corner == HudComponent.HudCorner.BOTTOM_LEFT || this.corner == HudComponent.HudCorner.BOTTOM_RIGHT) {
/* 18 */       amount = 10;
/* 19 */       if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
/* 20 */         amount += 14;
/*    */       }
/*    */     } 
/*    */     
/* 24 */     EnumFacing dir = mc.field_175622_Z.func_174811_aO();
/* 25 */     String text = w + "North " + g + "[" + w + "-Z" + g + "]";
/*    */     
/* 27 */     if (dir == EnumFacing.EAST) {
/* 28 */       text = w + "East " + g + "[" + w + "+X" + g + "]";
/* 29 */     } else if (dir == EnumFacing.SOUTH) {
/* 30 */       text = w + "South " + g + "[" + w + "+Z" + g + "]";
/* 31 */     } else if (dir == EnumFacing.WEST) {
/* 32 */       text = w + "West " + g + "[" + w + "-X" + g + "]";
/*    */     } 
/*    */     
/* 35 */     drawString(text, 0.0F, -amount, -16777216, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean shouldRender() { return GuiSettings.direction.booleanValue(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\DirectionComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */