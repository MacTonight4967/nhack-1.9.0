/*     */ package me.bebeli555.cookieclient.rendering;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.mods.render.Tracers;
/*     */ import me.bebeli555.cookieclient.utils.GLUProjection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderUtil
/*     */   extends Mod
/*     */ {
/*  27 */   private static final IntBuffer VIEWPORT = GLAllocation.func_74527_f(16);
/*  28 */   private static final FloatBuffer MODELVIEW = GLAllocation.func_74529_h(16);
/*  29 */   private static final FloatBuffer PROJECTION = GLAllocation.func_74529_h(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawLine(Vec3d posA, Vec3d posB, float width, Color c) {
/*  35 */     GlStateManager.func_179094_E();
/*  36 */     GlStateManager.func_179147_l();
/*  37 */     GlStateManager.func_179097_i();
/*  38 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/*  39 */     GlStateManager.func_179090_x();
/*  40 */     GlStateManager.func_179132_a(false);
/*  41 */     GlStateManager.func_179137_b(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/*  42 */     GL11.glEnable(2848);
/*  43 */     GL11.glHint(3154, 4354);
/*  44 */     GL11.glLineWidth(width);
/*  45 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  46 */     BufferBuilder bufferBuilder = tessellator.func_178180_c();
/*  47 */     bufferBuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/*     */     
/*  49 */     double dx = posB.field_72450_a - posA.field_72450_a;
/*  50 */     double dy = posB.field_72448_b - posA.field_72448_b;
/*  51 */     double dz = posB.field_72449_c - posA.field_72449_c;
/*     */     
/*  53 */     bufferBuilder.func_181662_b(posA.field_72450_a, posA.field_72448_b, posA.field_72449_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
/*  54 */     bufferBuilder.func_181662_b(posA.field_72450_a + dx, posA.field_72448_b + dy, posA.field_72449_c + dz).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
/*     */     
/*  56 */     tessellator.func_78381_a();
/*  57 */     GL11.glDisable(2848);
/*  58 */     GlStateManager.func_179132_a(true);
/*  59 */     GlStateManager.func_179126_j();
/*  60 */     GlStateManager.func_179098_w();
/*  61 */     GlStateManager.func_179084_k();
/*  62 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
/*  69 */     GlStateManager.func_179094_E();
/*  70 */     GlStateManager.func_179147_l();
/*  71 */     GlStateManager.func_179097_i();
/*  72 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/*  73 */     GlStateManager.func_179090_x();
/*  74 */     GlStateManager.func_179132_a(false);
/*  75 */     GL11.glEnable(2848);
/*  76 */     GL11.glHint(3154, 4354);
/*  77 */     GL11.glLineWidth(width);
/*  78 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  79 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  80 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*  81 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/*  82 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  83 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  84 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  85 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  86 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  87 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  88 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  89 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  90 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  91 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  92 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/*  93 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  94 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/*  95 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  96 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/*  97 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*  98 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/*  99 */     tessellator.func_78381_a();
/* 100 */     GL11.glDisable(2848);
/* 101 */     GlStateManager.func_179132_a(true);
/* 102 */     GlStateManager.func_179126_j();
/* 103 */     GlStateManager.func_179098_w();
/* 104 */     GlStateManager.func_179084_k();
/* 105 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void draw2DRec(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
/* 112 */     GlStateManager.func_179094_E();
/* 113 */     GlStateManager.func_179147_l();
/* 114 */     GlStateManager.func_179097_i();
/* 115 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 116 */     GlStateManager.func_179090_x();
/* 117 */     GlStateManager.func_179132_a(false);
/* 118 */     GL11.glEnable(2848);
/* 119 */     GL11.glHint(3154, 4354);
/* 120 */     GL11.glLineWidth(width);
/* 121 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 122 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 123 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 124 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 125 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 126 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 127 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 128 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 129 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 130 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 131 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 132 */     tessellator.func_78381_a();
/* 133 */     GL11.glDisable(2848);
/* 134 */     GlStateManager.func_179132_a(true);
/* 135 */     GlStateManager.func_179126_j();
/* 136 */     GlStateManager.func_179098_w();
/* 137 */     GlStateManager.func_179084_k();
/* 138 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void renderHitBox(Entity entity, float red, float green, float blue, float alpha, float lineWidth, float partialTicks) {
/* 142 */     GlStateManager.func_179094_E();
/* 143 */     GlStateManager.func_179147_l();
/* 144 */     GlStateManager.func_179097_i();
/* 145 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 146 */     GlStateManager.func_179090_x();
/* 147 */     GlStateManager.func_179132_a(false);
/* 148 */     GL11.glEnable(2848);
/* 149 */     GL11.glHint(3154, 4354);
/* 150 */     GL11.glLineWidth(lineWidth);
/* 151 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 152 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 153 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 154 */     Vec3d pos = Tracers.interpolateEntity(entity, partialTicks);
/*     */     
/* 156 */     AxisAlignedBB bb = new AxisAlignedBB(pos.field_72450_a - (entity.field_70130_N / 2.0F) - (mc.func_175598_ae()).field_78730_l, pos.field_72448_b - (mc.func_175598_ae()).field_78731_m, pos.field_72449_c + (entity.field_70130_N / 2.0F) - (mc.func_175598_ae()).field_78728_n, pos.field_72450_a + (entity.field_70130_N / 2.0F) - (mc.func_175598_ae()).field_78730_l, pos.field_72448_b + entity.field_70131_O - (mc.func_175598_ae()).field_78731_m, pos.field_72449_c - (entity.field_70130_N / 2.0F) - (mc.func_175598_ae()).field_78728_n);
/* 157 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 158 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 159 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 160 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 161 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 162 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 163 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 164 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 165 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 166 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 167 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 168 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 169 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 170 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 171 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 172 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 173 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 174 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 175 */     tessellator.func_78381_a();
/* 176 */     GL11.glDisable(2848);
/* 177 */     GlStateManager.func_179132_a(true);
/* 178 */     GlStateManager.func_179126_j();
/* 179 */     GlStateManager.func_179098_w();
/* 180 */     GlStateManager.func_179084_k();
/* 181 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void drawFilledBox(AxisAlignedBB bb, int color) {
/* 185 */     GlStateManager.func_179094_E();
/* 186 */     GlStateManager.func_179147_l();
/* 187 */     GlStateManager.func_179097_i();
/* 188 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 189 */     GlStateManager.func_179090_x();
/* 190 */     GlStateManager.func_179132_a(false);
/*     */     
/* 192 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 193 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 194 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 195 */     float blue = (color & 0xFF) / 255.0F;
/*     */     
/* 197 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 198 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 200 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 201 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 202 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 203 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 204 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     
/* 206 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 207 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 208 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 209 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     
/* 211 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 212 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 213 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 214 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     
/* 216 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 217 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 218 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 219 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     
/* 221 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 222 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 223 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 224 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     
/* 226 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 227 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 228 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 229 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 230 */     tessellator.func_78381_a();
/* 231 */     GlStateManager.func_179132_a(true);
/* 232 */     GlStateManager.func_179126_j();
/* 233 */     GlStateManager.func_179098_w();
/* 234 */     GlStateManager.func_179084_k();
/* 235 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void updateModelViewProjectionMatrix() {
/* 239 */     GL11.glGetFloat(2982, MODELVIEW);
/* 240 */     GL11.glGetFloat(2983, PROJECTION);
/* 241 */     GL11.glGetInteger(2978, VIEWPORT);
/* 242 */     res = new ScaledResolution(Minecraft.func_71410_x());
/* 243 */     GLUProjection.getInstance().updateMatrices(VIEWPORT, MODELVIEW, PROJECTION, (res.func_78326_a() / mc.field_71443_c), (res.func_78328_b() / mc.field_71440_d));
/*     */   }
/*     */   
/*     */   public static AxisAlignedBB getBB(BlockPos pos, int size) {
/* 247 */     return new AxisAlignedBB(pos.func_177958_n() - (mc.func_175598_ae()).field_78730_l, pos.func_177956_o() - (mc.func_175598_ae()).field_78731_m, pos.func_177952_p() - (mc.func_175598_ae()).field_78728_n, (pos
/* 248 */         .func_177958_n() + size) - (mc.func_175598_ae()).field_78730_l, (pos.func_177956_o() + size) - (mc.func_175598_ae()).field_78731_m, (pos.func_177952_p() + size) - (mc.func_175598_ae()).field_78728_n);
/*     */   }
/*     */   
/*     */   public static AxisAlignedBB getBB(Vec3d pos, double size, double ySize) {
/* 252 */     return new AxisAlignedBB(pos.field_72450_a - (mc.func_175598_ae()).field_78730_l, pos.field_72448_b - (mc.func_175598_ae()).field_78731_m, pos.field_72449_c - (mc.func_175598_ae()).field_78728_n, pos.field_72450_a + size - 
/* 253 */         (mc.func_175598_ae()).field_78730_l, pos.field_72448_b + ySize - (mc.func_175598_ae()).field_78731_m, pos.field_72449_c + size - (mc.func_175598_ae()).field_78728_n);
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\rendering\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */