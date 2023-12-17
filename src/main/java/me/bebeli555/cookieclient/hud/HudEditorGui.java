/*     */ package me.bebeli555.cookieclient.hud;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Gui;
/*     */ import me.bebeli555.cookieclient.hud.components.ArmorComponent;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public class HudEditorGui
/*     */   extends GuiScreen
/*     */ {
/*  17 */   public static Minecraft mc = Mod.mc;
/*     */   
/*     */   public static int lastMouseX;
/*  20 */   public static int EXTEND = 3;
/*     */   public static int lastMouseY;
/*     */   public static HudComponent dragging;
/*     */   
/*  24 */   public void func_73866_w_() { super.func_73866_w_(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public boolean func_73868_f() { return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  34 */     GlStateManager.func_179094_E();
/*  35 */     double guiScale = Gui.getGuiScale(1.0F);
/*  36 */     GlStateManager.func_179139_a(guiScale, guiScale, guiScale);
/*     */     
/*  38 */     func_183500_a(2147483647, 2147483647);
/*  39 */     func_146276_q_();
/*     */     
/*  41 */     mouseX = (int)(mouseX / guiScale);
/*  42 */     mouseY = (int)(mouseY / guiScale);
/*     */ 
/*     */     
/*  45 */     for (HudComponent component : HudComponent.components) {
/*  46 */       if (component.shouldRender()) {
/*  47 */         for (HudComponent.HudPoint point : component.renderedPoints) {
/*  48 */           func_73734_a((int)point.x - EXTEND, (int)point.y - EXTEND, (int)point.x2 + EXTEND, (int)point.y2 + EXTEND, -16777216);
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  55 */     for (HudComponent component : HudComponent.components) {
/*  56 */       if (component.shouldRender()) {
/*  57 */         component.onRender(partialTicks);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  62 */     if (dragging != null) {
/*  63 */       if (Mouse.isButtonDown(0)) {
/*  64 */         if (dragging.name.equals("Armor")) {
/*  65 */           double scale = ArmorComponent.getScale();
/*  66 */           dragging.xAdd = (int)(dragging.xAdd + (mouseX - lastMouseX) / scale);
/*  67 */           dragging.yAdd = (int)(dragging.yAdd + (mouseY - lastMouseY) / scale);
/*     */         } else {
/*  69 */           dragging.xAdd += mouseX - lastMouseX;
/*  70 */           dragging.yAdd += mouseY - lastMouseY;
/*     */         } 
/*     */       } else {
/*  73 */         dragging = null;
/*     */       } 
/*     */     }
/*     */     
/*  77 */     lastMouseX = mouseX;
/*  78 */     lastMouseY = mouseY;
/*  79 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/*  84 */     HudComponent component = null;
/*  85 */     for (HudComponent component2 : HudComponent.components) {
/*  86 */       if (component2.shouldRender()) {
/*  87 */         for (HudComponent.HudPoint point : component2.renderedPoints) {
/*  88 */           if (point.x2 + EXTEND > lastMouseX && point.x - EXTEND < lastMouseX && point.y2 + EXTEND > lastMouseY && point.y - EXTEND < lastMouseY) {
/*  89 */             component = component2;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (component == null) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     if (button == 0) {
/* 101 */       dragging = component;
/* 102 */     } else if (button == 1) {
/* 103 */       if (component.corner == HudComponent.HudCorner.BOTTOM_RIGHT) {
/* 104 */         component.corner = HudComponent.HudCorner.BOTTOM_LEFT;
/* 105 */       } else if (component.corner == HudComponent.HudCorner.BOTTOM_LEFT) {
/* 106 */         component.corner = HudComponent.HudCorner.TOP_LEFT;
/* 107 */       } else if (component.corner == HudComponent.HudCorner.TOP_LEFT) {
/* 108 */         component.corner = HudComponent.HudCorner.TOP_RIGHT;
/* 109 */       } else if (component.corner == HudComponent.HudCorner.TOP_RIGHT) {
/* 110 */         component.corner = HudComponent.HudCorner.BOTTOM_RIGHT;
/*     */       } 
/*     */       
/* 113 */       component.xAdd = 0;
/* 114 */       component.yAdd = 0;
/* 115 */     } else if (button == 2) {
/* 116 */       component.xAdd = 0;
/* 117 */       component.yAdd = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent event) {
/* 123 */     if (!(mc.field_71462_r instanceof HudEditorGui))
/* 124 */       HudEditor.module.disable(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\HudEditorGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */