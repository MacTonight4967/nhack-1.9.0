/*     */ package me.bebeli555.cookieclient.hud;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.hud.components.ArmorComponent;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class HudComponent
/*     */   extends Mod
/*     */ {
/*  14 */   public static ArrayList<HudComponent> components = new ArrayList();
/*     */   public HudCorner corner;
/*     */   public int xAdd;
/*     */   public int yAdd;
/*  18 */   public ArrayList<HudPoint> renderedPoints = new ArrayList();
/*  19 */   public static ChatFormatting w = ChatFormatting.WHITE;
/*  20 */   public static ChatFormatting g = ChatFormatting.GRAY;
/*     */   
/*     */   public HudComponent(HudCorner defaultCorner, String name) {
/*  23 */     this.corner = defaultCorner;
/*  24 */     this.name = name;
/*  25 */     components.add(this);
/*     */   }
/*     */ 
/*     */   
/*  29 */   public void onRender(float partialTicks) { this.renderedPoints.clear(); }
/*     */   
/*     */   public String name;
/*     */   
/*  33 */   public boolean shouldRender() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public void renderItemAndEffectIntoGUI(ItemStack itemStack, int x, int y) { mc.func_175599_af().func_180450_b(itemStack, x + this.xAdd, y + this.yAdd); }
/*     */ 
/*     */   
/*     */   public void renderItemOverlays(FontRenderer fontRenderer, ItemStack itemStack, int x, int y) {
/*  41 */     int plus = mc.field_71466_p.field_78288_b;
/*  42 */     int minus = -3;
/*  43 */     double scale = ArmorComponent.getScale();
/*  44 */     this.renderedPoints.add(new HudPoint((x + this.xAdd - minus) * (float)scale, (y + this.yAdd - minus) * (float)scale, (x + this.xAdd + plus - minus) * (float)scale, (y + this.yAdd + plus - minus) * (float)scale));
/*  45 */     mc.func_175599_af().func_175030_a(fontRenderer, itemStack, x + this.xAdd, y + this.yAdd);
/*     */   }
/*     */   
/*     */   public void drawString(String text, float x, float y, int color, boolean shadow) {
/*  49 */     int displayWidth = mc.field_71443_c / 2;
/*  50 */     int displayHeight = mc.field_71440_d / 2;
/*     */ 
/*     */ 
/*     */     
/*  54 */     ScaledResolution sr = new ScaledResolution(mc);
/*  55 */     if (sr.func_78325_e() == 3) {
/*  56 */       displayWidth = (int)(displayWidth / 1.05D);
/*  57 */       displayHeight = (int)(displayHeight / 1.05D);
/*     */     } 
/*     */     
/*  60 */     if (this.corner == HudCorner.BOTTOM_RIGHT) {
/*  61 */       drawString2(text, (displayWidth - 1 - mc.field_71466_p.func_78256_a(text)) + x, (displayHeight - 9) + y, color, shadow);
/*  62 */     } else if (this.corner == HudCorner.BOTTOM_LEFT) {
/*  63 */       drawString2(text, x + 1.0F, (displayHeight - 9) + y, color, shadow);
/*  64 */     } else if (this.corner == HudCorner.TOP_LEFT) {
/*  65 */       drawString2(text, x + 1.0F, y + 1.0F, color, shadow);
/*  66 */     } else if (this.corner == HudCorner.TOP_RIGHT) {
/*  67 */       drawString2(text, (displayWidth - mc.field_71466_p.func_78256_a(text) - 1), y + 1.0F, color, shadow);
/*  68 */     } else if (this.corner == HudCorner.NONE) {
/*  69 */       drawString2(text, x, y, color, shadow);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawString2(String text, float x, float y, int color, boolean shadow) {
/*  74 */     if (shadow) {
/*  75 */       mc.field_71466_p.func_175063_a(text, x + this.xAdd, y + this.yAdd, color);
/*     */     } else {
/*  77 */       mc.field_71466_p.func_175065_a(text, x + this.xAdd, y + this.yAdd, color, false);
/*     */     } 
/*     */     
/*  80 */     this.renderedPoints.add(new HudPoint(x + this.xAdd, y + this.yAdd, x + this.xAdd + mc.field_71466_p.func_78256_a(text), y + this.yAdd + mc.field_71466_p.field_78288_b));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String decimal(double d, int decimal) {
/*  85 */     String s = Double.toString(d);
/*     */     try {
/*  87 */       return s.substring(0, s.indexOf(".") + 1 + decimal);
/*  88 */     } catch (IndexOutOfBoundsException e) {
/*  89 */       return s.substring(0, s.indexOf(".") + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum HudCorner {
/*  94 */     TOP_RIGHT(false),
/*  95 */     TOP_LEFT(true),
/*  96 */     BOTTOM_RIGHT(2),
/*  97 */     BOTTOM_LEFT(3),
/*  98 */     NONE(4);
/*     */     
/*     */     public int id;
/*     */     
/* 102 */     HudCorner(int id) { this.id = id; }
/*     */ 
/*     */     
/*     */     public static HudCorner getCornerFromId(int id) {
/* 106 */       for (HudCorner corner : values()) {
/* 107 */         if (corner.id == id) {
/* 108 */           return corner;
/*     */         }
/*     */       } 
/*     */       
/* 112 */       return null;
/*     */     } }
/*     */   
/*     */   public class HudPoint {
/*     */     public float x;
/*     */     public float y;
/*     */     
/*     */     public HudPoint(float x, float y, float x2, float y2) {
/* 120 */       this.x = x;
/* 121 */       this.y = y;
/* 122 */       this.x2 = x2;
/* 123 */       this.y2 = y2;
/*     */     }
/*     */     
/*     */     public float x2;
/*     */     public float y2;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\HudComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */