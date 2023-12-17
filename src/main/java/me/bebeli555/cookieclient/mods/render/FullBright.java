/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class FullBright
/*    */   extends Mod {
/*    */   public static float oldGamma;
/*    */   
/* 10 */   public FullBright() { super(Group.RENDER, "FullBright", new String[] { "Full brightness" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 15 */     oldGamma = mc.field_71474_y.field_74333_Y;
/* 16 */     mc.field_71474_y.field_74333_Y = 69.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 21 */     if (mc.field_71474_y.field_74333_Y == oldGamma) {
/* 22 */       mc.field_71474_y.field_74333_Y = 1.0F;
/*    */     } else {
/* 24 */       mc.field_71474_y.field_74333_Y = oldGamma;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\FullBright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */