/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class Search
/*     */   extends Mod {
/*     */   private static Thread thread;
/*     */   private static boolean loaded;
/*  31 */   private static ArrayList<Block> blocks = new ArrayList();
/*  32 */   private static ArrayList<BlockPos> foundBlocks = new ArrayList();
/*     */   
/*  34 */   public static Setting esp = new Setting(Mode.BOOLEAN, "ESP", Boolean.valueOf(true), new String[] { "Render a rectangle around the block" });
/*  35 */   public static Setting espAlpha = new Setting(esp, Mode.DOUBLE, "Alpha", Integer.valueOf(1), new String[] { "How transparent the rendered blocks are", "1 = max" });
/*  36 */   public static Setting espWidth = new Setting(esp, Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "The width of the rendered lines" });
/*  37 */   public static Setting tracers = new Setting(Mode.BOOLEAN, "Tracers", Boolean.valueOf(true), new String[] { "Render tracers on the block" });
/*  38 */   public static Setting tracersWidth = new Setting(tracers, Mode.DOUBLE, "Width", Double.valueOf(0.5D), new String[] { "The width of the rendered lines" }); @EventHandler
/*     */   private Listener<PacketEvent> onPacket;
/*     */   
/*  41 */   public Search() { super(Group.RENDER, "Search", new String[] { "Search for blocks", "You can add blocks with "search add id"", "And remove blocks with "search remove id"" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.onPacket = new Listener(event -> {
/*     */           
/*  98 */           if (event.packet instanceof SPacketBlockChange)
/*  99 */           { SPacketBlockChange packet = (SPacketBlockChange)event.packet;
/*     */             
/* 101 */             if (isValid(packet.func_179827_b())) {
/* 102 */               foundBlocks.add(packet.func_179827_b());
/*     */             } }
/* 104 */           else if (event.packet instanceof SPacketChunkData)
/* 105 */           { SPacketChunkData packet = (SPacketChunkData)event.packet;
/* 106 */             final int chunkX = packet.func_149273_e() * 16 - 16;
/* 107 */             final int chunkZ = packet.func_149271_f() * 16 - 16;
/*     */             
/* 109 */             (new Thread() {
/*     */                 public void run() {
/* 111 */                   for (int x = chunkX; x < chunkX + 16; x++) {
/* 112 */                     for (int z = chunkZ; z < chunkZ + 16; z++) {
/* 113 */                       for (int y = 0; y < 260; y++) {
/* 114 */                         BlockPos pos = new BlockPos(x, y, z);
/*     */                         
/* 116 */                         if (Search.isValid(pos)) {
/* 117 */                           Search.access$200().add(pos);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 }
/* 123 */               }).start(); }  }new java.util.function.Predicate[0]); } public void onEnabled() { (new Thread() { public void run() { if (Search.access$000()) { Search.searchForBlocks(); return; }  Search.access$002(true); try { File file = new File(Settings.path + "/Search.txt"); if (file.exists()) { Search.access$100().clear(); Scanner s = new Scanner(file); while (s.hasNextLine()) { String line = s.nextLine(); if (!line.isEmpty())
/*     */                   Search.access$100().add(Block.func_149729_e(Integer.parseInt(line)));  }  s.close(); }  } catch (Exception e) { e.printStackTrace(); }
/*     */            if (Search.access$100().isEmpty()) { Block[] defaultBlocks = { 
/*     */                 Blocks.field_150427_aO, Blocks.field_150477_bB, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_190975_dA, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190986_du, 
/*     */                 Blocks.field_190986_du, Blocks.field_190984_ds, Blocks.field_190990_dy, Blocks.field_190980_do, Blocks.field_190982_dq, Blocks.field_190979_dn, Blocks.field_190978_dm, Blocks.field_190983_dr, Blocks.field_190983_dr, Blocks.field_190987_dv, 
/*     */                 Blocks.field_190991_dz, Blocks.field_190985_dt, Blocks.field_190977_dl, Blocks.field_190977_dl, Blocks.field_150474_ac, Blocks.field_150378_br }; for (Block block : defaultBlocks)
/*     */               Search.access$100().add(block);  }
/* 130 */            Search.searchForBlocks(); } }).start(); } public void onRenderWorld(float partialTicks) { try { ArrayList<BlockPos> remove = new ArrayList<BlockPos>();
/*     */       
/* 132 */       for (BlockPos pos : foundBlocks) {
/* 133 */         Block block = getBlock(pos);
/* 134 */         if (block == Blocks.field_150350_a) {
/* 135 */           remove.add(pos);
/*     */           continue;
/*     */         } 
/* 138 */         Color c = getColor(block);
/*     */         
/* 140 */         if (esp.booleanValue()) {
/* 141 */           RenderUtil.drawBoundingBox(RenderUtil.getBB(pos, 1), (float)espWidth.doubleValue(), (c.getRed() / 255), (c.getGreen() / 255), (c.getBlue() / 255), (float)espAlpha.doubleValue());
/*     */         }
/*     */         
/* 144 */         if (tracers.booleanValue()) {
/* 145 */           Vec3d vec = (new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D)).func_178786_a((mc.func_175598_ae()).field_78725_b, (mc.func_175598_ae()).field_78726_c, (mc.func_175598_ae()).field_78723_d);
/* 146 */           Tracers.renderTracer(vec, c, (float)tracersWidth.doubleValue(), partialTicks);
/*     */         } 
/*     */       } 
/*     */       
/* 150 */       foundBlocks.removeAll(remove); }
/* 151 */     catch (Exception exception) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void searchForBlocks() {
/* 159 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     suspend(thread);
/* 164 */     thread = new Thread() {
/*     */         public void run() {
/* 166 */           for (BlockPos pos : BlockUtil.getAllNoSort(125)) {
/* 167 */             if (Search.isValid(pos)) {
/* 168 */               Search.access$200().add(pos);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 174 */     thread.start(); } public void onDisabled() {
/*     */     thread = null;
/*     */     suspend(thread);
/*     */     foundBlocks.clear();
/*     */   } public static Color getColor(Block block) {
/* 179 */     if (block == Blocks.field_150427_aO)
/* 180 */       return Color.MAGENTA; 
/* 181 */     if (block == Blocks.field_150474_ac)
/* 182 */       return Color.BLUE; 
/* 183 */     if (block == Blocks.field_150486_ae || block == Blocks.field_150447_bR)
/* 184 */       return Color.YELLOW; 
/* 185 */     if (block == Blocks.field_150477_bB) {
/* 186 */       return Color.BLACK;
/*     */     }
/* 188 */     return Color.LIGHT_GRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValid(BlockPos pos) {
/* 193 */     Block block = getBlock(pos);
/* 194 */     if (block == Blocks.field_150350_a) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     for (Block block2 : blocks) {
/* 199 */       if (block == block2 && !foundBlocks.contains(pos)) {
/* 200 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlock(int id) {
/* 209 */     blocks.add(Block.func_149729_e(id));
/* 210 */     updateFile();
/* 211 */     searchForBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeBlock(int id) {
/* 216 */     blocks.remove(Block.func_149729_e(id));
/* 217 */     updateFile();
/* 218 */     foundBlocks.clear();
/* 219 */     searchForBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateFile() {
/*     */     try {
/* 225 */       file = new File(Settings.path + "/Search.txt");
/* 226 */       file.delete();
/* 227 */       file.createNewFile();
/*     */       
/* 229 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/* 230 */       for (Block block : blocks) {
/* 231 */         bw.write("" + Block.func_149682_b(block));
/* 232 */         bw.newLine();
/*     */       } 
/*     */       
/* 235 */       bw.close();
/* 236 */     } catch (Exception e) {
/* 237 */       System.out.println("CookieClient - Error updating Search file");
/* 238 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class SearchBlock {
/*     */     public Color color;
/*     */     public BlockPos pos;
/* 245 */     public static ArrayList<SearchBlock> list = new ArrayList();
/*     */     
/*     */     public SearchBlock(Color color, BlockPos pos) {
/* 248 */       this.color = color;
/* 249 */       this.pos = pos;
/* 250 */       list.add(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Search.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */