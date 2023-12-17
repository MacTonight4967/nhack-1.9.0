/*    */ package me.bebeli555.cookieclient.rendering;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class RenderBlock
/*    */ {
/*  9 */   public static ArrayList<BlockColor> list = new ArrayList();
/*    */ 
/*    */   
/* 12 */   public static void add(BlockPos pos, Color color, float width) { list.add(new BlockColor(pos, color, width)); }
/*    */ 
/*    */   
/*    */   public static void remove(BlockPos pos) {
/* 16 */     ArrayList<BlockColor> remove = new ArrayList<BlockColor>();
/* 17 */     for (BlockColor blockColor : list) {
/* 18 */       if (blockColor.pos.equals(pos)) {
/* 19 */         remove.add(blockColor);
/*    */       }
/*    */     } 
/*    */     
/* 23 */     list.removeAll(remove);
/*    */   }
/*    */ 
/*    */   
/* 27 */   public static void clear() { list.clear(); }
/*    */   
/*    */   public static class BlockColor
/*    */   {
/*    */     public BlockPos pos;
/*    */     public Color color;
/*    */     public float width;
/*    */     
/*    */     public BlockColor(BlockPos pos, Color color, float width) {
/* 36 */       this.pos = pos;
/* 37 */       this.color = color;
/* 38 */       this.width = width;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\rendering\RenderBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */