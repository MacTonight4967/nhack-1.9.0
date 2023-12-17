/*     */ package me.bebeli555.cookieclient.mods.games.tetris;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.Random;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class Tetris
/*     */   extends Mod {
/*     */   public static Tetris instance;
/*     */   public static int fromX;
/*     */   public static int toX;
/*     */   public static int fromY;
/*     */   public static int toY;
/*     */   public static TetrisNode currentNode;
/*  23 */   public static int beenDown = 0;
/*     */   public static boolean gameOver = true;
/*  25 */   public static int score = 0;
/*  26 */   public static long lastSec = 0L;
/*  27 */   public static long lastSecMove = 0L;
/*     */   
/*     */   public Tetris() {
/*  30 */     super(Group.GAMES, "Tetris", new String[] { "Tetris game" });
/*  31 */     instance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiDrawScreen(int mouseX, int mouseY, float partialTicks) {
/*  37 */     fromX = 400;
/*  38 */     toX = fromX + 150;
/*  39 */     fromY = 150;
/*  40 */     toY = fromY + 250;
/*     */     
/*  42 */     GuiScreen.func_73734_a(fromX, fromY, toX, toY, -16777216);
/*     */     
/*  44 */     int Divided = 150 - score / 10;
/*  45 */     if (Divided < 10) {
/*  46 */       Divided = 10;
/*     */     }
/*  48 */     long sec = System.currentTimeMillis() / Divided;
/*  49 */     if (sec != lastSec && !gameOver) {
/*     */       
/*  51 */       if (TetrisNode.nodes.isEmpty() || !currentNode.canGoDown()) {
/*  52 */         score++;
/*  53 */         BlockPos Player = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/*  54 */         mc.field_71441_e.func_184156_a(Player, SoundEvents.field_187567_bP, SoundCategory.AMBIENT, 10222.5F, 1.5F, true);
/*  55 */         removeLayer();
/*  56 */         setShapes();
/*     */         
/*  58 */         if (!currentNode.canGoDown()) {
/*  59 */           GameOver();
/*     */         }
/*     */       } 
/*     */       
/*  63 */       if (currentNode.canGoDown()) {
/*  64 */         currentNode.moveDown();
/*     */       }
/*  66 */       lastSec = sec;
/*     */     } 
/*     */     
/*  69 */     long sec2 = System.currentTimeMillis() / 40L;
/*  70 */     if (sec2 != lastSecMove) {
/*     */       
/*  72 */       if (!gameOver && currentNode != null) {
/*  73 */         if (Keyboard.isKeyDown(205) || Keyboard.isKeyDown(203) || Keyboard.isKeyDown(208)) {
/*  74 */           beenDown++;
/*  75 */           if (beenDown > 3) {
/*  76 */             if (Keyboard.isKeyDown(205)) {
/*  77 */               if (currentNode.canMoveRight()) {
/*  78 */                 currentNode.moveRight();
/*     */               }
/*  80 */             } else if (Keyboard.isKeyDown(203)) {
/*  81 */               if (currentNode.canMoveLeft()) {
/*  82 */                 currentNode.moveLeft();
/*     */               
/*     */               }
/*     */             }
/*  86 */             else if (currentNode.canGoDown()) {
/*  87 */               currentNode.moveDown();
/*     */             } 
/*     */           }
/*     */         } else {
/*     */           
/*  92 */           beenDown = 0;
/*     */         } 
/*     */       }
/*  95 */       lastSecMove = sec2;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     for (i = 0; i < TetrisNode.nodes.size(); i++) {
/* 100 */       TetrisNode node = (TetrisNode)TetrisNode.nodes.get(i);
/* 101 */       GuiScreen.func_73734_a(node.getX(), node.getY(), node.getX() + TetrisNode.multiplier, node.getY() - TetrisNode.multiplier, node.getColor());
/*     */     } 
/*     */ 
/*     */     
/* 105 */     drawWhiteLine(fromX, toX, fromY, toY);
/*     */ 
/*     */     
/* 108 */     GlStateManager.func_179094_E();
/* 109 */     GlStateManager.func_179152_a(1.25F, 1.25F, 1.25F);
/* 110 */     mc.field_71466_p.func_175063_a(ChatFormatting.DARK_AQUA + "Score: " + ChatFormatting.GREEN + score, 325.0F, 125.0F, 65535);
/* 111 */     GlStateManager.func_179121_F();
/*     */     
/* 113 */     if (gameOver == true) {
/*     */       
/* 115 */       GlStateManager.func_179094_E();
/* 116 */       GlStateManager.func_179152_a(2.5F, 2.5F, 2.5F);
/* 117 */       mc.field_71466_p.func_175063_a(ChatFormatting.RED + "Game Over!", 163.0F, 70.0F, 65535);
/* 118 */       mc.field_71466_p.func_175063_a(ChatFormatting.RED + "Score = " + ChatFormatting.GREEN + score, 165.0F, 80.0F, 65535);
/* 119 */       mc.field_71466_p.func_175063_a(ChatFormatting.AQUA + "Controls:", 167.0F, 95.0F, 65535);
/* 120 */       GuiScreen.func_73734_a(165, 140, 215, 155, -1);
/* 121 */       GlStateManager.func_179121_F();
/*     */       
/* 123 */       GlStateManager.func_179094_E();
/* 124 */       GlStateManager.func_179152_a(1.25F, 1.25F, 1.25F);
/* 125 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Arrow UP: " + ChatFormatting.DARK_AQUA + "Rotate", 335.0F, 215.0F, 65535);
/* 126 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Arrow Right: " + ChatFormatting.DARK_AQUA + "Move Right", 322.0F, 225.0F, 65535);
/* 127 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Arrow Left: " + ChatFormatting.DARK_AQUA + "Move Left", 325.0F, 235.0F, 65535);
/* 128 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Arrow Down: " + ChatFormatting.DARK_AQUA + "Drop Soft", 325.0F, 245.0F, 65535);
/* 129 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Space: " + ChatFormatting.DARK_AQUA + "Drop Hard", 335.0F, 255.0F, 65535);
/* 130 */       GlStateManager.func_179121_F();
/*     */       
/* 132 */       GlStateManager.func_179094_E();
/* 133 */       GlStateManager.func_179152_a(1.8F, 1.8F, 1.8F);
/* 134 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Click to play", 233.0F, 200.0F, 65535);
/* 135 */       GlStateManager.func_179121_F();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 140 */     for (int i = 0; i < currentNode.getFamily().size(); i++) {
/* 141 */       if (currentNode.canGoDown()) {
/* 142 */         currentNode.setDownPosition();
/* 143 */         TetrisNode Node = (TetrisNode)currentNode.getFamily().get(i);
/* 144 */         int x = Node.getX();
/* 145 */         int y = Node.getDownPosition();
/* 146 */         GuiScreen.func_73734_a(x, y, x + 1, y - TetrisNode.multiplier, -1);
/* 147 */         GuiScreen.func_73734_a(x, y, x + TetrisNode.multiplier, y + 1, -1);
/* 148 */         GuiScreen.func_73734_a(x + TetrisNode.multiplier, y, x + TetrisNode.multiplier + 1, y - TetrisNode.multiplier, -1);
/* 149 */         GuiScreen.func_73734_a(x, y - TetrisNode.multiplier, x + TetrisNode.multiplier, y - TetrisNode.multiplier + 1, -1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onGuiClick(int x, int y, int button) {
/* 156 */     if (fromX < x && toX > x && fromY < y && toY > y) {
/* 157 */       if (gameOver == true) {
/* 158 */         startGame();
/*     */       }
/*     */       
/* 161 */       return true;
/*     */     } 
/*     */     
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
/* 170 */     if (currentNode == null || gameOver == true) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     if (Keyboard.isKeyDown(205)) {
/* 175 */       if (currentNode.canMoveRight()) {
/* 176 */         currentNode.moveRight();
/*     */       }
/* 178 */     } else if (Keyboard.isKeyDown(203)) {
/* 179 */       if (currentNode.canMoveLeft()) {
/* 180 */         currentNode.moveLeft();
/*     */       }
/* 182 */     } else if (Keyboard.isKeyDown(57)) {
/* 183 */       currentNode.moveCompletelyDown();
/* 184 */     } else if (Keyboard.isKeyDown(200)) {
/* 185 */       currentNode.rotate();
/* 186 */     } else if (Keyboard.isKeyDown(208) && 
/* 187 */       currentNode.canGoDown()) {
/* 188 */       currentNode.moveDown();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setShapes() {
/* 195 */     n = new TetrisNode(fromX + 50, fromY);
/* 196 */     n.addToFamily(n);
/* 197 */     int x = n.getX();
/* 198 */     int y = n.getY();
/*     */     
/* 200 */     Random rand = new Random();
/* 201 */     int random = rand.nextInt(7);
/* 202 */     if (random == 0) {
/* 203 */       n.addToFamily(new TetrisNode(x + 10, y));
/* 204 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 205 */       n.addToFamily(new TetrisNode(x + 10, y + 10));
/* 206 */       n.setShape("O");
/* 207 */       n.setColor(-256);
/* 208 */     } else if (random == 1) {
/* 209 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 210 */       n.addToFamily(new TetrisNode(x, y + 20));
/* 211 */       n.addToFamily(new TetrisNode(x, y + 30));
/* 212 */       n.setShape("I");
/* 213 */       n.setColor(-13178113);
/* 214 */     } else if (random == 2) {
/* 215 */       n.addToFamily(new TetrisNode(x + 10, y));
/* 216 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 217 */       n.addToFamily(new TetrisNode(x - 10, y + 10));
/* 218 */       n.setShape("S");
/* 219 */       n.setColor(-65527);
/* 220 */     } else if (random == 3) {
/* 221 */       n.addToFamily(new TetrisNode(x - 10, y));
/* 222 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 223 */       n.addToFamily(new TetrisNode(x + 10, y + 10));
/* 224 */       n.setShape("Z");
/* 225 */       n.setColor(-16711893);
/* 226 */     } else if (random == 4) {
/* 227 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 228 */       n.addToFamily(new TetrisNode(x, y + 20));
/* 229 */       n.addToFamily(new TetrisNode(x + 10, y + 20));
/* 230 */       n.setShape("L");
/* 231 */       n.setColor(-1277172);
/* 232 */     } else if (random == 5) {
/* 233 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 234 */       n.addToFamily(new TetrisNode(x, y + 20));
/* 235 */       n.addToFamily(new TetrisNode(x - 10, y + 20));
/* 236 */       n.setShape("J");
/* 237 */       n.setColor(-58897);
/*     */     } else {
/* 239 */       n.addToFamily(new TetrisNode(x + 10, y));
/* 240 */       n.addToFamily(new TetrisNode(x - 10, y));
/* 241 */       n.addToFamily(new TetrisNode(x, y + 10));
/* 242 */       n.setShape("T");
/* 243 */       n.setColor(-7274241);
/*     */     } 
/*     */     
/* 246 */     currentNode = n;
/*     */   }
/*     */   
/*     */   public static void removeLayer() {
/* 250 */     if (currentNode == null) {
/*     */       return;
/*     */     }
/*     */     
/* 254 */     for (i = 0; i < currentNode.getFamily().size(); i++) {
/* 255 */       int y = ((TetrisNode)currentNode.getFamily().get(i)).getY();
/* 256 */       int x = fromX - 10;
/* 257 */       for (int i2 = 0; i2 < 100; i2++) {
/* 258 */         x += 10;
/* 259 */         if (x > toX - 10) {
/* 260 */           x = fromX - 10;
/* 261 */           for (i3 = 0; i3 < 100; i3++) {
/* 262 */             x += 10;
/* 263 */             TetrisNode.nodes.remove(TetrisNode.getNode(x, y));
/*     */           } 
/* 265 */           for (int i4 = 0; i4 < TetrisNode.nodes.size(); i4++) {
/* 266 */             if (((TetrisNode)TetrisNode.nodes.get(i4)).getY() <= y) {
/* 267 */               ((TetrisNode)TetrisNode.nodes.get(i4)).setY(((TetrisNode)TetrisNode.nodes.get(i4)).getY() + 10);
/*     */             }
/*     */           } 
/* 270 */           BlockPos Player = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/* 271 */           mc.field_71441_e.func_184156_a(Player, SoundEvents.field_187698_i, SoundCategory.AMBIENT, 10222.5F, 1.5F, true);
/* 272 */           score += 5;
/*     */           
/*     */           break;
/*     */         } 
/* 276 */         if (TetrisNode.getNode(x, y) == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void GameOver() {
/* 284 */     gameOver = true;
/* 285 */     TetrisNode.nodes.clear();
/*     */   }
/*     */   
/*     */   public static void startGame() {
/* 289 */     gameOver = false;
/* 290 */     score = 0;
/*     */   }
/*     */   
/*     */   public static void drawWhiteLine(int x, int x2, int y, int y2) {
/* 294 */     GlStateManager.func_179094_E();
/* 295 */     GlStateManager.func_179152_a(0.25F, 0.25F, 0.25F);
/* 296 */     int thisx = x * 4, thisy = y * 4, thisx2 = x2 * 4, thisy2 = y2 * 4;
/* 297 */     GuiScreen.func_73734_a(thisx, thisy, thisx + 2, thisy2, -1);
/* 298 */     GuiScreen.func_73734_a(thisx2, thisy, thisx2 - 2, thisy2, -1);
/* 299 */     GuiScreen.func_73734_a(thisx, thisy, thisx2, thisy + 2, -1);
/* 300 */     GuiScreen.func_73734_a(thisx, thisy2, thisx2, thisy2 + 2, -1);
/* 301 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\games\tetris\Tetris.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */