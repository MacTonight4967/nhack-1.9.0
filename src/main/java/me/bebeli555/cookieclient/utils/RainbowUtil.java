/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class RainbowUtil {
/*    */   public int speed;
/*  8 */   public ArrayList<Integer> currentRainbowIndexes = new ArrayList();
/*  9 */   public ArrayList<Integer> rainbowArrayList = new ArrayList();
/* 10 */   public Timer timer = new Timer();
/*    */   
/*    */   public RainbowUtil() {
/* 13 */     for (int i = 0; i < 360; i++) {
/* 14 */       this.rainbowArrayList.add(Integer.valueOf(getRainbowColor(i, 90.0F, 50.0F, 1.0F).getRGB()));
/* 15 */       this.currentRainbowIndexes.add(Integer.valueOf(i));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 20 */   public void setSpeed(int speed) { this.speed = speed; }
/*    */ 
/*    */   
/*    */   public int getRainbowColorAt(int index) {
/* 24 */     if (index > this.currentRainbowIndexes.size() - 1) {
/* 25 */       index = this.currentRainbowIndexes.size() - 1;
/*    */     }
/*    */     
/* 28 */     return ((Integer)this.rainbowArrayList.get(((Integer)this.currentRainbowIndexes.get(index)).intValue())).intValue();
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 32 */     if (this.timer.hasPassed(this.speed)) {
/* 33 */       this.timer.reset();
/* 34 */       moveListToNextColor();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void moveListToNextColor() {
/* 39 */     if (this.currentRainbowIndexes.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     this.currentRainbowIndexes.remove(this.currentRainbowIndexes.get(0));
/*    */     
/* 45 */     int l_Index = ((Integer)this.currentRainbowIndexes.get(this.currentRainbowIndexes.size() - 1)).intValue() + 1;
/* 46 */     if (l_Index >= this.rainbowArrayList.size() - 1) {
/* 47 */       l_Index = 0;
/*    */     }
/*    */     
/* 50 */     this.currentRainbowIndexes.add(Integer.valueOf(l_Index));
/*    */   }
/*    */   private Color getRainbowColor(float hue, float saturation, float lightness, float alpha) {
/*    */     float n5;
/* 54 */     hue = hue %= 360.0F / 360.0F;
/* 55 */     saturation /= 100.0F;
/* 56 */     lightness /= 100.0F;
/*    */ 
/*    */     
/* 59 */     if (lightness < 0.0D) {
/* 60 */       n5 = lightness * (1.0F + saturation);
/*    */     } else {
/* 62 */       n5 = lightness + saturation - saturation * lightness;
/*    */     } 
/*    */     
/* 65 */     saturation = 2.0F * lightness - n5;
/* 66 */     lightness = Math.max(0.0F, calculateColor(saturation, n5, hue + 0.33333334F));
/* 67 */     float max = Math.max(0.0F, calculateColor(saturation, n5, hue));
/* 68 */     saturation = Math.max(0.0F, calculateColor(saturation, n5, hue - 0.33333334F));
/* 69 */     lightness = Math.min(lightness, 1.0F);
/* 70 */     float min = Math.min(max, 1.0F);
/* 71 */     saturation = Math.min(saturation, 1.0F);
/* 72 */     return new Color(lightness, min, saturation, alpha);
/*    */   }
/*    */   
/*    */   private float calculateColor(float n, float n2, float n3) {
/* 76 */     if (n3 < 0.0F) {
/* 77 */       n3++;
/*    */     }
/*    */     
/* 80 */     if (n3 > 1.0F) {
/* 81 */       n3--;
/*    */     }
/*    */     
/* 84 */     if (6.0F * n3 < 1.0F) {
/* 85 */       return n + (n2 - n) * 6.0F * n3;
/*    */     }
/*    */     
/* 88 */     if (2.0F * n3 < 1.0F) {
/* 89 */       return n2;
/*    */     }
/*    */     
/* 92 */     if (3.0F * n3 < 2.0F) {
/* 93 */       return n + (n2 - n) * 6.0F * (0.6666667F - n3);
/*    */     }
/*    */     
/* 96 */     return n;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\RainbowUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */