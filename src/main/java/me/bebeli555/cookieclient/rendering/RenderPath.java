/*    */ package me.bebeli555.cookieclient.rendering;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class RenderPath
/*    */ {
/*  9 */   public static ArrayList<BlockPos> path = new ArrayList();
/*    */   public static Color color;
/*    */   
/*    */   public static void setPath(ArrayList<BlockPos> path, Color color) {
/* 13 */     RenderPath.path = path;
/* 14 */     RenderPath.color = color;
/*    */   }
/*    */ 
/*    */   
/* 18 */   public static void clearPath() { path.clear(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\rendering\RenderPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */