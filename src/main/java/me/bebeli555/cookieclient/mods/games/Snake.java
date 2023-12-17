/*     */ package me.bebeli555.cookieclient.mods.games;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class Snake
/*     */   extends Mod
/*     */ {
/*     */   public static Snake instance;
/*     */   public static int snakeX;
/*  20 */   public static ArrayList<Integer> bodyX = new ArrayList();
/*  21 */   public static ArrayList<Integer> bodyY = new ArrayList(); public static int snakeY;
/*  22 */   public static int snakeSize = 0; public static int lastSnakeX;
/*     */   public static int lastSnakeY;
/*     */   public static int lastBodyX;
/*     */   public static int lastBodyY;
/*     */   public static boolean gameOver = true;
/*  27 */   public static int score = 0; public static int appleX;
/*     */   public static int appleY;
/*  29 */   public static int delay = 0;
/*  30 */   public static long lastSec = 0L;
/*     */   
/*     */   public static String direction;
/*  33 */   public static int x = mc.field_71443_c / 4;
/*  34 */   public static int y = mc.field_71440_d / 5;
/*     */   
/*     */   public Snake() {
/*  37 */     super(Group.GAMES, "Snake", new String[] { "Snake game" });
/*  38 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiDrawScreen(int mouseX, int mouseY, float partialTicks) {
/*  43 */     GlStateManager.func_179094_E();
/*  44 */     GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
/*  45 */     GuiScreen.func_73734_a(x - 1, y - 1, x + 201, y + 201, -12388932);
/*  46 */     GuiScreen.func_73734_a(x, y, x + 200, y + 200, -16777216);
/*     */ 
/*     */     
/*  49 */     if (snakeX < x || snakeX >= x + 200 || snakeY < y || snakeY >= y + 200) {
/*  50 */       gameOver();
/*     */     }
/*     */     
/*  53 */     if (!gameOver) {
/*     */       
/*  55 */       for (i = 0; i < bodyX.size(); i++) {
/*  56 */         if (((Integer)bodyX.get(i)).intValue() == snakeX && (
/*  57 */           (Integer)bodyY.get(i)).intValue() == snakeY) {
/*  58 */           gameOver();
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/*  65 */       mc.field_71466_p.func_175063_a(ChatFormatting.RED + "Score = " + ChatFormatting.GREEN + snakeSize, (x + 3), (y + 2), 65535);
/*     */ 
/*     */       
/*  68 */       if (appleX == 0 || appleY == 0) {
/*  69 */         generateApple();
/*     */       }
/*     */ 
/*     */       
/*  73 */       GuiScreen.func_73734_a(appleX, appleY, appleX + 20, appleY + 20, -65536);
/*     */       
/*  75 */       long sec = System.currentTimeMillis() / 150L;
/*  76 */       if (sec != lastSec) {
/*  77 */         delay = 0;
/*     */         
/*  79 */         if (direction.equals("Up")) {
/*  80 */           snakeY -= 20;
/*  81 */         } else if (direction.equals("Down")) {
/*  82 */           snakeY += 20;
/*  83 */         } else if (direction.equals("Right")) {
/*  84 */           snakeX += 20;
/*  85 */         } else if (direction.equals("Left")) {
/*  86 */           snakeX -= 20;
/*     */         } 
/*     */         
/*  89 */         if (!bodyX.isEmpty()) {
/*  90 */           bodyX.remove(bodyX.get(0));
/*  91 */           bodyY.remove(bodyY.get(0));
/*  92 */           bodyX.add(Integer.valueOf(lastSnakeX));
/*  93 */           bodyY.add(Integer.valueOf(lastSnakeY));
/*     */         } 
/*  95 */         lastSec = sec;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       if (snakeX == appleX && 
/* 100 */         snakeY == appleY) {
/* 101 */         snakeSize++;
/* 102 */         appleX = 0;
/* 103 */         appleY = 0;
/*     */ 
/*     */         
/* 106 */         if (bodyX.isEmpty()) {
/* 107 */           bodyX.add(Integer.valueOf(lastSnakeX));
/* 108 */           bodyY.add(Integer.valueOf(lastSnakeY));
/*     */         } else {
/* 110 */           bodyX.add(Integer.valueOf(lastBodyX));
/* 111 */           bodyY.add(Integer.valueOf(lastBodyY));
/*     */         } 
/* 113 */         mc.field_71441_e.func_184156_a(getPlayerPos(), SoundEvents.field_187802_ec, SoundCategory.AMBIENT, 100.0F, 2.0F, true);
/*     */       } 
/*     */ 
/*     */       
/* 117 */       lastSnakeX = snakeX;
/* 118 */       lastSnakeY = snakeY;
/* 119 */       if (!bodyX.isEmpty()) {
/* 120 */         lastBodyX = ((Integer)bodyX.get(bodyX.size() - 1)).intValue();
/* 121 */         lastBodyY = ((Integer)bodyY.get(bodyY.size() - 1)).intValue();
/*     */       } 
/*     */ 
/*     */       
/* 125 */       GuiScreen.func_73734_a(snakeX, snakeY, snakeX + 20, snakeY + 20, -11141376);
/* 126 */       GuiScreen.func_73734_a(snakeX + 3, snakeY + 3, snakeX + 8, snakeY + 8, -16777216);
/* 127 */       for (int i = 0; i < bodyX.size(); i++) {
/* 128 */         if (!bodyX.isEmpty()) {
/* 129 */           GuiScreen.func_73734_a(((Integer)bodyX.get(i)).intValue(), ((Integer)bodyY.get(i)).intValue(), ((Integer)bodyX.get(i)).intValue() + 20, ((Integer)bodyY.get(i)).intValue() + 20, -11141376);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (gameOver == true) {
/* 136 */       GlStateManager.func_179094_E();
/* 137 */       GlStateManager.func_179152_a(3.0F, 3.0F, 3.0F);
/* 138 */       mc.field_71466_p.func_175063_a(ChatFormatting.RED + "Game Over!", (x / 3 + 6), (y / 3 + 2), 65535);
/* 139 */       mc.field_71466_p.func_175063_a(ChatFormatting.LIGHT_PURPLE + "Score = " + ChatFormatting.GREEN + snakeSize, (x / 3 + 9), (y / 3 + 12), 65535);
/* 140 */       GlStateManager.func_179121_F();
/* 141 */       GlStateManager.func_179094_E();
/* 142 */       GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/* 143 */       mc.field_71466_p.func_175063_a(ChatFormatting.GREEN + "Click to Play!", (x / 2 + 18), (y / 2 + 85), 65535);
/* 144 */       GlStateManager.func_179121_F();
/* 145 */       GlStateManager.func_179094_E();
/* 146 */       GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
/* 147 */       mc.field_71466_p.func_175063_a(ChatFormatting.AQUA + "Use " + ChatFormatting.GREEN + "ARROW KEYS " + ChatFormatting.AQUA + "To play!", ((int)(x / 1.5D) + 5), ((int)(y / 1.5D) + 75), 65535);
/* 148 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */     
/* 151 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onGuiClick(int x, int y, int button) {
/* 156 */     x = (int)(x / 1.5D);
/* 157 */     y = (int)(y / 1.5D);
/*     */     
/* 159 */     if (Snake.x < x && Snake.x + 200 > x && Snake.y < y && Snake.y + 200 > y) {
/* 160 */       if (gameOver == true) {
/* 161 */         startGame();
/*     */       }
/*     */       
/* 164 */       return true;
/*     */     } 
/*     */     
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
/* 172 */     if (Keyboard.isKeyDown(208)) {
/* 173 */       direction = "Down";
/* 174 */     } else if (Keyboard.isKeyDown(200)) {
/* 175 */       direction = "Up";
/* 176 */     } else if (Keyboard.isKeyDown(205)) {
/* 177 */       direction = "Right";
/* 178 */     } else if (Keyboard.isKeyDown(203)) {
/* 179 */       direction = "Left";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void gameOver() {
/* 184 */     if (!gameOver) {
/* 185 */       gameOver = true;
/* 186 */       bodyX.clear();
/* 187 */       bodyY.clear();
/* 188 */       lastSnakeX = 0;
/* 189 */       lastSnakeY = 0;
/* 190 */       appleX = 0;
/* 191 */       appleY = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void startGame() {
/* 196 */     gameOver = false;
/* 197 */     snakeX = x + 100;
/* 198 */     snakeY = y + 180;
/* 199 */     direction = "Up";
/* 200 */     snakeSize = 1;
/*     */   }
/*     */   
/*     */   public static void generateApple() {
/* 204 */     for (i = 0; i < 100; i++) {
/* 205 */       Random rand = new Random();
/* 206 */       int random = rand.nextInt(10);
/* 207 */       int random2 = rand.nextInt(10);
/* 208 */       appleX = x + random * 20;
/* 209 */       appleY = y + random2 * 20;
/*     */       
/* 211 */       for (int i2 = 0; i2 < bodyX.size(); i2++) {
/* 212 */         if (!bodyX.isEmpty() && (
/* 213 */           (Integer)bodyX.get(i2)).intValue() == appleX && (
/* 214 */           (Integer)bodyY.get(i2)).intValue() == appleY) {
/* 215 */           appleX = 0;
/* 216 */           appleY = 0;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 223 */       if (snakeX == appleX && 
/* 224 */         snakeY == appleY) {
/* 225 */         appleX = 0;
/* 226 */         appleY = 0;
/*     */ 
/*     */       
/*     */       }
/* 230 */       else if (appleX < 140 || appleX > 340 || appleY < 140 || appleY > 340) {
/* 231 */         appleX = 0;
/* 232 */         appleY = 0;
/*     */ 
/*     */       
/*     */       }
/* 236 */       else if (appleX != 0 && appleY != 0) {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\games\Snake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */