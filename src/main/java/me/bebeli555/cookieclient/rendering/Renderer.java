/*    */ package me.bebeli555.cookieclient.rendering;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Gui;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import me.bebeli555.cookieclient.hud.HudEditor;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*    */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Renderer
/*    */   extends Mod
/*    */ {
/*    */   public static String[] status;
/* 22 */   public static ArrayList<RenderBlock.BlockColor> rectangles = new ArrayList();
/*    */   
/*    */   @SubscribeEvent
/*    */   public void renderText(RenderGameOverlayEvent.Text e) {
/* 26 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 31 */     if (status != null) {
/* 32 */       for (int i = 0; i < status.length; i++) {
/* 33 */         String text = status[i];
/* 34 */         if (text != null) {
/*    */ 
/*    */ 
/*    */           
/* 38 */           GuiScreen.func_73734_a(mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2 - 3, i * 10 - 1, mc.field_71443_c / 4 + mc.field_71466_p.func_78256_a(text) / 2 + 3, (i + 1) * 10, -16777216);
/* 39 */           GuiScreen.func_73734_a(mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2 - 3, i * 10 + 9, mc.field_71443_c / 4 + mc.field_71466_p.func_78256_a(text) / 2 + 3, (i + 1) * 10, -14158402);
/* 40 */           GuiScreen.func_73734_a(mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2 - 3, i * 10 - 1, mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2 - 2, (i + 1) * 10, -14158402);
/* 41 */           GuiScreen.func_73734_a(mc.field_71443_c / 4 + mc.field_71466_p.func_78256_a(text) / 2 + 2, i * 10 - 1, mc.field_71443_c / 4 + mc.field_71466_p.func_78256_a(text) / 2 + 3, (i + 1) * 10, -14158402);
/* 42 */           mc.field_71466_p.func_78276_b(text, mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2, i * 10, -16777216);
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 47 */     if (!HudEditor.module.isOn()) {
/* 48 */       if (!GuiSettings.hud.booleanValue())
/* 49 */         return;  GlStateManager.func_179094_E();
/* 50 */       double guiScale = Gui.getGuiScale(1.0F);
/* 51 */       GlStateManager.func_179139_a(guiScale, guiScale, guiScale);
/*    */       
/* 53 */       for (HudComponent component : HudComponent.components) {
/* 54 */         if (component.shouldRender()) {
/* 55 */           component.onRender(e.getPartialTicks());
/*    */         }
/*    */       } 
/*    */       
/* 59 */       GlStateManager.func_179121_F();
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void renderWorld(RenderWorldLastEvent event) {
/* 65 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 71 */       BlockPos last = null;
/* 72 */       for (BlockPos pos : RenderPath.path) {
/* 73 */         if (last != null) {
/* 74 */           RenderUtil.drawLine(new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D), new Vec3d(last.func_177958_n() + 0.5D, last.func_177956_o() + 0.5D, last.func_177952_p() + 0.5D), 2.0F, RenderPath.color);
/*    */         }
/*    */         
/* 77 */         last = pos;
/*    */       } 
/*    */ 
/*    */       
/* 81 */       for (RenderBlock.BlockColor blockColor : RenderBlock.list) {
/* 82 */         Color c = blockColor.color;
/* 83 */         RenderUtil.drawBoundingBox(RenderUtil.getBB(blockColor.pos, 1), blockColor.width, (c.getRed() / 255), (c.getGreen() / 255), (c.getBlue() / 255), 1.0F);
/*    */       } 
/*    */ 
/*    */       
/* 87 */       for (RenderBlock.BlockColor rectangle : rectangles) {
/* 88 */         Color c = rectangle.color;
/* 89 */         RenderUtil.draw2DRec(RenderUtil.getBB(rectangle.pos, 1), rectangle.width, (c.getRed() / 255), (c.getGreen() / 255), (c.getBlue() / 255), 1.0F);
/*    */       } 
/* 91 */     } catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/* 95 */     for (Mod module : Mod.modules) {
/* 96 */       if (module.isOn())
/* 97 */         module.onRenderWorld(event.getPartialTicks()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\rendering\Renderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */