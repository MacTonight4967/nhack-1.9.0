/*     */ package me.bebeli555.cookieclient.mods.games.tetris;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class TetrisNode {
/*   6 */   public static ArrayList<TetrisNode> nodes = new ArrayList();
/*   7 */   private ArrayList<TetrisNode> familyNodes = new ArrayList();
/*   8 */   public static int multiplier = 10;
/*     */   
/*     */   private int x;
/*     */   private int y;
/*     */   private int color;
/*     */   private String shape;
/*     */   
/*     */   public TetrisNode(int x, int y) {
/*  16 */     this.x = x;
/*  17 */     this.y = y;
/*  18 */     this.rotation = 1;
/*  19 */     nodes.add(this);
/*     */   }
/*     */ 
/*     */   
/*  23 */   public void removeFromList() { nodes.remove(this); }
/*     */ 
/*     */   
/*     */   public void setColor(int color) {
/*  27 */     for (int i = 0; i < this.familyNodes.size(); i++)
/*  28 */       ((TetrisNode)this.familyNodes.get(i)).color = color; 
/*     */   }
/*     */   
/*     */   private int rotation;
/*     */   
/*  33 */   public int getColor() { return this.color; }
/*     */ 
/*     */   
/*     */   public void setShape(String shape) {
/*  37 */     for (int i = 0; i < this.familyNodes.size(); i++)
/*  38 */       ((TetrisNode)this.familyNodes.get(i)).shape = shape; 
/*     */   }
/*     */   
/*     */   private int downpos;
/*     */   
/*  43 */   public String getShape() { return this.shape; }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public void addToFamily(TetrisNode Node) { this.familyNodes.add(Node); }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public void setX(int x) { this.x = x; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public void setY(int y) { this.y = y; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int getX() { return this.x; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public int getY() { return this.y; }
/*     */ 
/*     */   
/*     */   public void moveDown() {
/*  67 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/*  68 */       TetrisNode Node = (TetrisNode)this.familyNodes.get(i);
/*  69 */       Node.setY(((TetrisNode)this.familyNodes.get(i)).getY() + multiplier);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveRight() {
/*  74 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/*  75 */       TetrisNode Node = (TetrisNode)this.familyNodes.get(i);
/*  76 */       Node.setX(Node.getX() + multiplier);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveLeft() {
/*  81 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/*  82 */       TetrisNode Node = (TetrisNode)this.familyNodes.get(i);
/*  83 */       Node.setX(Node.getX() - multiplier);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canMoveRight() {
/*  88 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/*  89 */       TetrisNode Node = getNode(((TetrisNode)this.familyNodes.get(i)).getX() + 10, ((TetrisNode)this.familyNodes.get(i)).getY());
/*  90 */       if (Node != null && !isInFamily(Node)) {
/*  91 */         return false;
/*     */       }
/*  93 */       if (((TetrisNode)this.familyNodes.get(i)).getX() > Tetris.toX - 20) {
/*  94 */         return false;
/*     */       }
/*     */     } 
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canMoveLeft() {
/* 101 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/* 102 */       TetrisNode Node = getNode(((TetrisNode)this.familyNodes.get(i)).getX() - 10, ((TetrisNode)this.familyNodes.get(i)).getY());
/* 103 */       if (Node != null && !isInFamily(Node)) {
/* 104 */         return false;
/*     */       }
/* 106 */       if (((TetrisNode)this.familyNodes.get(i)).getX() < Tetris.fromX + 10) {
/* 107 */         return false;
/*     */       }
/*     */     } 
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 114 */   public ArrayList<TetrisNode> getFamily() { return this.familyNodes; }
/*     */ 
/*     */   
/*     */   public void moveCompletelyDown() {
/* 118 */     for (int i = 0; i < 150; i++) {
/* 119 */       if (canGoDown()) {
/* 120 */         moveDown();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isInFamily(TetrisNode node) {
/* 126 */     if (this.familyNodes.contains(node)) {
/* 127 */       return true;
/*     */     }
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canGoDown() {
/* 133 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/* 134 */       TetrisNode Node = getNode(((TetrisNode)this.familyNodes.get(i)).getX(), ((TetrisNode)this.familyNodes.get(i)).getY() + multiplier);
/* 135 */       if (((TetrisNode)this.familyNodes.get(i)).getY() > Tetris.toY - 10) {
/* 136 */         return false;
/*     */       }
/*     */       
/* 139 */       if (Node != null && !isInFamily(Node)) {
/* 140 */         return false;
/*     */       }
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   public static TetrisNode getNode(int x, int y) {
/* 147 */     for (int i = 0; i < nodes.size(); i++) {
/* 148 */       TetrisNode Node = (TetrisNode)nodes.get(i);
/* 149 */       if (Node.getX() == x && Node.getY() == y) {
/* 150 */         return Node;
/*     */       }
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */   
/*     */   public void clearFamily() {
/* 157 */     for (int i = 0; i < this.familyNodes.size(); i++) {
/* 158 */       if (!((TetrisNode)this.familyNodes.get(i)).equals(this)) {
/* 159 */         nodes.remove(this.familyNodes.get(i));
/*     */       }
/*     */     } 
/*     */     
/* 163 */     this.familyNodes.clear();
/*     */   }
/*     */   
/*     */   public void setDownPosition() {
/* 167 */     int OldY = ((TetrisNode)this.familyNodes.get(0)).getY();
/* 168 */     int OldY2 = ((TetrisNode)this.familyNodes.get(1)).getY();
/* 169 */     int OldY3 = ((TetrisNode)this.familyNodes.get(2)).getY();
/* 170 */     int OldY4 = ((TetrisNode)this.familyNodes.get(3)).getY();
/*     */     
/* 172 */     for (int i2 = 0; i2 < 100; i2++) {
/* 173 */       for (i3 = 0; i3 < this.familyNodes.size(); i3++) {
/* 174 */         ((TetrisNode)this.familyNodes.get(i3)).setY(((TetrisNode)this.familyNodes.get(i3)).getY() + 10);
/*     */       }
/* 176 */       int NodeY = this.y;
/*     */       
/* 178 */       if (!canGoDown()) {
/* 179 */         for (int i = 0; i < this.familyNodes.size(); i++) {
/* 180 */           ((TetrisNode)this.familyNodes.get(i)).downpos = ((TetrisNode)this.familyNodes.get(i)).getY() - getY() + NodeY;
/*     */         }
/* 182 */         ((TetrisNode)this.familyNodes.get(0)).setY(OldY);
/* 183 */         ((TetrisNode)this.familyNodes.get(1)).setY(OldY2);
/* 184 */         ((TetrisNode)this.familyNodes.get(2)).setY(OldY3);
/* 185 */         ((TetrisNode)this.familyNodes.get(3)).setY(OldY4);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 192 */   public int getDownPosition() { return this.downpos; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate() {
/* 197 */     String rot = getShape();
/* 198 */     if (rot.equals("O")) {
/*     */       return;
/*     */     }
/* 201 */     this.rotation++;
/* 202 */     clearFamily();
/* 203 */     this.familyNodes.add(this);
/*     */ 
/*     */     
/* 206 */     if (rot.equals("I")) {
/* 207 */       if (this.rotation > 2) {
/* 208 */         this.rotation = 1;
/*     */       }
/* 210 */       if (this.x + 20 > Tetris.toX) this.x -= 20; 
/* 211 */       if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 212 */       if (this.rotation == 1) {
/* 213 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 214 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 20));
/* 215 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 30));
/*     */       } else {
/* 217 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 218 */         this.familyNodes.add(new TetrisNode(this.x + 20, this.y));
/* 219 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/*     */       } 
/* 221 */       setColor(-13178113);
/*     */     } 
/*     */ 
/*     */     
/* 225 */     if (rot.equals("S")) {
/* 226 */       if (this.rotation > 2) {
/* 227 */         this.rotation = 1;
/*     */       }
/* 229 */       if (this.x + 20 > Tetris.toX) this.x -= 10; 
/* 230 */       if (this.rotation == 1) {
/* 231 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 232 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 233 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y + 10));
/*     */       } else {
/* 235 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 236 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 237 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y - 10));
/*     */       } 
/* 239 */       setColor(-65527);
/*     */     } 
/*     */ 
/*     */     
/* 243 */     if (rot.equals("Z")) {
/* 244 */       if (this.rotation > 2) {
/* 245 */         this.rotation = 1;
/*     */       }
/* 247 */       if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 248 */       if (this.rotation == 1) {
/* 249 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 250 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y + 10));
/* 251 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/*     */       } else {
/* 253 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 254 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y - 10));
/* 255 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/*     */       } 
/* 257 */       setColor(-16711893);
/*     */     } 
/*     */ 
/*     */     
/* 261 */     if (rot.equals("L")) {
/* 262 */       if (this.rotation > 4) {
/* 263 */         this.rotation = 1;
/*     */       }
/*     */       
/* 266 */       if (this.rotation == 1) {
/* 267 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 268 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 20));
/* 269 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y + 20));
/* 270 */       } else if (this.rotation == 2) {
/* 271 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 272 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 273 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 274 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y + 10));
/* 275 */       } else if (this.rotation == 3) {
/* 276 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 277 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 278 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 279 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 20));
/*     */       } else {
/* 281 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 282 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 283 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y - 10));
/* 284 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/*     */       } 
/* 286 */       setColor(-1277172);
/*     */     } 
/*     */ 
/*     */     
/* 290 */     if (rot.equals("J")) {
/* 291 */       if (this.rotation > 4) {
/* 292 */         this.rotation = 1;
/*     */       }
/*     */       
/* 295 */       if (this.rotation == 1) {
/* 296 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 297 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 20));
/* 298 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y + 20));
/* 299 */       } else if (this.rotation == 2) {
/* 300 */         if (this.x + 20 > Tetris.toX) this.x -= 20; 
/* 301 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 302 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y + 10));
/* 303 */         this.familyNodes.add(new TetrisNode(this.x + 20, this.y + 10));
/* 304 */       } else if (this.rotation == 3) {
/* 305 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 306 */         this.familyNodes.add(new TetrisNode(this.x, this.y - 10));
/* 307 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y - 10));
/*     */       } else {
/* 309 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 310 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 311 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 312 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y + 10));
/*     */       } 
/* 314 */       setColor(-58897);
/*     */     } 
/*     */ 
/*     */     
/* 318 */     if (rot.equals("T")) {
/* 319 */       if (this.rotation > 4) {
/* 320 */         this.rotation = 1;
/*     */       }
/*     */       
/* 323 */       if (this.rotation == 1) {
/* 324 */         if (this.x + 10 > Tetris.toX) this.x -= 10; 
/* 325 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 326 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 327 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 328 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 329 */       } else if (this.rotation == 2) {
/* 330 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 331 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 332 */         this.familyNodes.add(new TetrisNode(this.x, this.y - 10));
/* 333 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/* 334 */       } else if (this.rotation == 3) {
/* 335 */         if (this.x + 20 > Tetris.toX) this.x -= 10; 
/* 336 */         if (this.x - 10 < Tetris.fromX) this.x += 10; 
/* 337 */         this.familyNodes.add(new TetrisNode(this.x, this.y - 10));
/* 338 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 339 */         this.familyNodes.add(new TetrisNode(this.x - 10, this.y));
/*     */       } else {
/* 341 */         if (this.x + 10 > Tetris.toX) this.x -= 10; 
/* 342 */         this.familyNodes.add(new TetrisNode(this.x + 10, this.y));
/* 343 */         this.familyNodes.add(new TetrisNode(this.x, this.y + 10));
/* 344 */         this.familyNodes.add(new TetrisNode(this.x, this.y - 10));
/*     */       } 
/* 346 */       setColor(-7274241);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\games\tetris\TetrisNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */