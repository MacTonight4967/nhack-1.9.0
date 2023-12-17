/*     */ package me.bebeli555.cookieclient.mods.bots.elytrabot;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AStar
/*     */   extends Mod
/*     */ {
/*     */   private static boolean check;
/*     */   
/*     */   public static ArrayList<BlockPos> generatePath(BlockPos start, BlockPos goal, BlockPos[] positions, ArrayList<BlockPos> checkPositions, int loopAmount) {
/*  20 */     AStarNode.nodes.clear();
/*  21 */     BlockPos current = start;
/*  22 */     BlockPos closest = current;
/*  23 */     ArrayList<BlockPos> open = new ArrayList<BlockPos>();
/*  24 */     ArrayList<BlockPos> closed = new ArrayList<BlockPos>();
/*     */     
/*  26 */     int noClosest = 0;
/*  27 */     for (int i = 0; i < loopAmount; ) {
/*     */       
/*  29 */       if (current.equals(goal)) {
/*  30 */         check = false;
/*  31 */         return getPath(current);
/*     */       } 
/*     */ 
/*     */       
/*  35 */       double lowestFCost = 2.147483647E9D;
/*  36 */       for (BlockPos pos : open) {
/*  37 */         double fCost = fCost(pos, goal, start);
/*     */         
/*  39 */         if (fCost < lowestFCost) {
/*  40 */           lowestFCost = fCost;
/*  41 */           current = pos;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  46 */       closed.add(current);
/*  47 */       open.remove(current);
/*     */       
/*  49 */       ArrayList<BlockPos> addToOpen = addToOpen(positions, checkPositions, current, goal, start, open, closed);
/*  50 */       if (addToOpen != null) {
/*  51 */         open.addAll(addToOpen);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  57 */         if (lowestFCost < fCost(closest, goal, start)) {
/*  58 */           closest = current;
/*  59 */           noClosest = 0;
/*     */         } else {
/*  61 */           noClosest++;
/*     */ 
/*     */           
/*  64 */           if (noClosest > 200) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/*     */         i++;
/*     */       } 
/*     */     } 
/*  72 */     if (!check) {
/*  73 */       check = true;
/*  74 */       return generatePath(start, closest, positions, checkPositions, loopAmount);
/*     */     } 
/*  76 */     check = false;
/*  77 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<BlockPos> addToOpen(BlockPos[] positions, ArrayList<BlockPos> checkPositions, BlockPos current, BlockPos goal, BlockPos start, ArrayList<BlockPos> open, ArrayList<BlockPos> closed) {
/*  85 */     ArrayList<BlockPos> list = new ArrayList<BlockPos>();
/*     */     
/*  87 */     ArrayList<BlockPos> positions2 = new ArrayList<BlockPos>();
/*  88 */     for (BlockPos pos : positions) {
/*  89 */       positions2.add(current.func_177982_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()));
/*     */     }
/*     */     
/*  92 */     label48: for (BlockPos pos : positions2) {
/*  93 */       if (!isSolid(pos) && !closed.contains(pos)) {
/*  94 */         ArrayList<BlockPos> checkPositions2 = new ArrayList<BlockPos>();
/*  95 */         for (BlockPos b : checkPositions) {
/*  96 */           checkPositions2.add(pos.func_177982_a(b.func_177958_n(), b.func_177956_o(), b.func_177952_p()));
/*     */         }
/*     */         
/*  99 */         for (BlockPos check : checkPositions2) {
/* 100 */           if (ElytraBot.mode.stringValue().equals("Highway") && !BlockUtil.isInRenderDistance(check)) {
/* 101 */             return null;
/*     */           }
/*     */           
/* 104 */           if (!isSolid(check)) { if (!BlockUtil.isInRenderDistance(check)) {
/*     */               continue label48;
/*     */             }
/*     */             
/* 108 */             if (getBlock(check) == Blocks.field_150353_l && ElytraBot.avoidLava.booleanValue()) {
/*     */               continue label48;
/*     */             }
/*     */             
/* 112 */             if (ElytraBot.maxY.intValue() != -1 && check.func_177956_o() > ElytraBot.maxY.intValue())
/*     */               continue label48;  continue; }
/*     */           
/*     */           continue label48;
/*     */         } 
/* 117 */         AStarNode n = AStarNode.getNodeFromBlockpos(pos);
/* 118 */         if (n == null) {
/* 119 */           n = new AStarNode(pos);
/*     */         }
/*     */         
/* 122 */         if (!open.contains(pos)) {
/* 123 */           list.add(pos);
/*     */         }
/*     */         
/* 126 */         if (n.parent == null || gCost(current, start) < gCost(n.parent, start)) {
/* 127 */           n.parent = current;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double fCost(BlockPos pos, BlockPos goal, BlockPos start) {
/* 140 */     double dx = (goal.func_177958_n() - pos.func_177958_n());
/* 141 */     double dz = (goal.func_177952_p() - pos.func_177952_p());
/* 142 */     double h = Math.sqrt(dx * dx + dz * dz);
/* 143 */     return gCost(pos, start) + h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double gCost(BlockPos pos, BlockPos start) {
/* 152 */     double dx = (start.func_177958_n() - pos.func_177958_n());
/* 153 */     double dy = (start.func_177956_o() - pos.func_177956_o());
/* 154 */     double dz = (start.func_177952_p() - pos.func_177952_p());
/* 155 */     return Math.sqrt(Math.abs(dx) + Math.abs(dy) + Math.abs(dz));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<BlockPos> getPath(BlockPos current) {
/* 162 */     ArrayList<BlockPos> path = new ArrayList<BlockPos>();
/*     */     
/*     */     try {
/* 165 */       AStarNode n = AStarNode.getNodeFromBlockpos(current);
/* 166 */       if (n == null) {
/* 167 */         n = (AStarNode)AStarNode.nodes.get(AStarNode.nodes.size() - 1);
/*     */       }
/* 169 */       path.add(n.pos);
/*     */       
/* 171 */       while (n != null && n.parent != null) {
/* 172 */         path.add(n.parent);
/* 173 */         n = AStarNode.getNodeFromBlockpos(n.parent);
/*     */       } 
/* 175 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */ 
/*     */ 
/*     */     
/* 179 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AStarNode
/*     */   {
/* 186 */     public static ArrayList<AStarNode> nodes = new ArrayList();
/*     */     public BlockPos pos;
/*     */     public BlockPos parent;
/*     */     
/*     */     public AStarNode(BlockPos pos) {
/* 191 */       this.pos = pos;
/* 192 */       nodes.add(this);
/*     */     }
/*     */     
/*     */     public static AStarNode getNodeFromBlockpos(BlockPos pos) {
/* 196 */       for (AStarNode node : nodes) {
/* 197 */         if (node.pos.equals(pos)) {
/* 198 */           return node;
/*     */         }
/*     */       } 
/*     */       
/* 202 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\elytrabot\AStar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */