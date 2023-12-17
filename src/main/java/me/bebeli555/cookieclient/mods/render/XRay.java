/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ 
/*     */ public class XRay
/*     */   extends Mod {
/*     */   private static boolean loaded;
/*     */   public static boolean isToggled;
/*  19 */   public static ArrayList<Block> blocks = new ArrayList();
/*     */ 
/*     */   
/*  22 */   public XRay() { super(Group.RENDER, "XRay", new String[] { "Only renders the important blocks", "You can add a block with "xray add id"", "And delete block with "xray remove id"", "You need to send that as a command in chat" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  27 */     isToggled = true;
/*  28 */     if (loaded) {
/*  29 */       mc.field_71438_f.func_72712_a();
/*     */       
/*     */       return;
/*     */     } 
/*  33 */     loaded = true;
/*     */     try {
/*  35 */       File file = new File(Settings.path + "/XRay.txt");
/*  36 */       if (file.exists()) {
/*  37 */         blocks.clear();
/*     */         
/*  39 */         Scanner s = new Scanner(file);
/*  40 */         while (s.hasNextLine()) {
/*  41 */           String line = s.nextLine();
/*  42 */           if (!line.isEmpty()) {
/*  43 */             blocks.add(Block.func_149729_e(Integer.parseInt(line)));
/*     */           }
/*     */         } 
/*     */         
/*  47 */         s.close();
/*     */       } 
/*  49 */     } catch (Exception e) {
/*  50 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*  54 */     if (blocks.isEmpty()) {
/*  55 */       Block[] defaultBlocks = { Blocks.field_150412_bA, Blocks.field_150352_o, Blocks.field_150366_p, Blocks.field_150365_q, Blocks.field_150369_x, Blocks.field_150482_ag, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150335_W, Blocks.field_150412_bA, Blocks.field_150460_al, Blocks.field_150470_am, Blocks.field_150484_ah, Blocks.field_150339_S, Blocks.field_150340_R, Blocks.field_150475_bE, Blocks.field_150449_bY, Blocks.field_150461_bJ, Blocks.field_150474_ac, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150477_bB, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150427_aO, Blocks.field_150381_bn };
/*     */ 
/*     */       
/*  58 */       for (Block block : defaultBlocks) {
/*  59 */         blocks.add(block);
/*     */       }
/*     */     } 
/*     */     
/*  63 */     mc.field_71438_f.func_72712_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  68 */     isToggled = false;
/*  69 */     mc.field_71438_f.func_72712_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlock(int id) {
/*  74 */     blocks.add(Block.func_149729_e(id));
/*  75 */     updateFile();
/*  76 */     mc.field_71438_f.func_72712_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeBlock(int id) {
/*  81 */     blocks.remove(Block.func_149729_e(id));
/*  82 */     updateFile();
/*  83 */     mc.field_71438_f.func_72712_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateFile() {
/*     */     try {
/*  89 */       file = new File(Settings.path + "/XRay.txt");
/*  90 */       file.delete();
/*  91 */       file.createNewFile();
/*     */       
/*  93 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/*  94 */       for (Block block : blocks) {
/*  95 */         bw.write("" + Block.func_149682_b(block));
/*  96 */         bw.newLine();
/*     */       } 
/*     */       
/*  99 */       bw.close();
/* 100 */     } catch (Exception e) {
/* 101 */       System.out.println("CookieClient - Error updating XRay file");
/* 102 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean shouldRender(Block block) {
/* 107 */     if (block == Blocks.field_150350_a) {
/* 108 */       return true;
/*     */     }
/*     */     
/* 111 */     for (Block block2 : blocks) {
/* 112 */       if (block2 == block) {
/* 113 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\XRay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */