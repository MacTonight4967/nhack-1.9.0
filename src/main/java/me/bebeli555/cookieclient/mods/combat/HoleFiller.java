/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class HoleFiller
/*     */   extends Mod {
/*     */   private static Thread thread;
/*  19 */   private static ArrayList<BlockPos> holes = new ArrayList();
/*     */   
/*  21 */   public static Setting radius = new Setting(Mode.INTEGER, "Radius", Integer.valueOf(4), new String[] { "Radius around the player to search for holes to fill" });
/*  22 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(100), new String[] { "Delay in ms to wait between placing the blocks" });
/*  23 */   public static Setting render = new Setting(Mode.BOOLEAN, "Render", Boolean.valueOf(true), new String[] { "Render the holes its trying to fill" });
/*     */ 
/*     */   
/*  26 */   public HoleFiller() { super(Group.COMBAT, "HoleFiller", new String[] { "Fills nearby holes with obsidian" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  31 */     thread = new Thread() {
/*     */         public void run() {
/*  33 */           while (HoleFiller.access$000() != null && HoleFiller.access$000().equals(this)) {
/*  34 */             HoleFiller.this.loop();
/*     */             
/*  36 */             Mod.sleep(150);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  41 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  46 */     thread = null;
/*  47 */     RotationUtil.stopRotating();
/*     */   }
/*     */   
/*     */   public void loop() {
/*  51 */     if (!InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/*  52 */       disable();
/*  53 */       sendMessage("You need obsidian", true);
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     holes = getHoles();
/*  58 */     ArrayList<BlockPos> temp = new ArrayList<BlockPos>();
/*  59 */     temp.addAll(holes);
/*     */     
/*  61 */     for (BlockPos pos : temp) {
/*  62 */       if (!InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/*     */         return;
/*     */       }
/*     */       
/*  66 */       BlockUtil.placeBlock(Blocks.field_150343_Z, pos, true);
/*  67 */       RotationUtil.stopRotating();
/*  68 */       holes.remove(pos);
/*  69 */       sleep(delay.intValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(float partialTicks) {
/*  75 */     if (!render.booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     Color c = Color.CYAN;
/*     */     try {
/*  81 */       for (BlockPos pos : holes) {
/*  82 */         RenderUtil.drawBoundingBox(RenderUtil.getBB(pos, 1), 1.0F, (c.getRed() / 255), (c.getGreen() / 255), (c.getBlue() / 255), 1.0F);
/*     */       }
/*  84 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<BlockPos> getHoles() {
/*  90 */     list = new ArrayList();
/*     */     
/*  92 */     label28: for (BlockPos pos : BlockUtil.getAll(radius.intValue())) {
/*  93 */       if (BlockUtil.distance(getPlayerPos(), pos) > radius.intValue() || 
/*  94 */         getBlock(pos) != Blocks.field_150350_a) {
/*     */         continue;
/*     */       }
/*     */       
/*  98 */       BlockPos[] solid = { pos.func_177982_a(1, 0, 0), pos.func_177982_a(-1, 0, 0), pos.func_177982_a(0, 0, 1), pos.func_177982_a(0, 0, -1), pos.func_177982_a(0, -1, 0) };
/*  99 */       for (BlockPos check : solid) {
/* 100 */         if (!isSolid(check)) {
/*     */           continue label28;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       BlockPos[] notSolid = { pos.func_177982_a(0, 1, 0), pos.func_177982_a(0, 2, 0) };
/* 106 */       for (BlockPos check : notSolid) {
/* 107 */         if (isSolid(check)) {
/*     */           continue label28;
/*     */         }
/*     */       } 
/*     */       
/* 112 */       list.add(pos);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\HoleFiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */