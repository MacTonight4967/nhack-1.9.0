/*     */ package me.bebeli555.cookieclient.mods.world;
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
/*     */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.rendering.RenderBlock;
/*     */ import me.bebeli555.cookieclient.utils.BaritoneUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public class AutoBuilder
/*     */   extends Mod
/*     */ {
/*     */   private static Thread thread;
/*  33 */   private static ArrayList<BuildPosition> positions = new ArrayList();
/*     */   private static boolean check;
/*     */   private static boolean down;
/*     */   private static BlockPos playerPos;
/*     */   private static AutoBuilder autoBuilder;
/*  38 */   public static Setting setStructure = new Setting(Mode.BOOLEAN, "SetStructure", Boolean.valueOf(false), new String[] { "Click this to set the structure", "You can middleclick on blocks while this is on", "to add them to the structure", "When ur done toggle this off and autobuilder", "will now build the structure you made" });
/*  39 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(50), new String[] { "Delay in milliseconds between placing blocks" });
/*  40 */   public static Setting toggle = new Setting(Mode.BOOLEAN, "Toggle", Boolean.valueOf(false), new String[] { "If true then it will toggle the module off", "After the structure has been built", "Note: This has no effect if you have move on" });
/*  41 */   public static Setting move = new Setting(Mode.BOOLEAN, "Move", Boolean.valueOf(false), new String[] { "When the structure has been built it will move", "To the given coordinates below and then build it again", "The coordinates are relative to the player so", "For example X: 1 will make it walk 1 block right" });
/*  42 */   public static Setting moveX = new Setting(move, Mode.INTEGER, "X", Integer.valueOf(0), new String[] { "X-Coordinate relative to players position" });
/*  43 */   public static Setting moveY = new Setting(move, Mode.INTEGER, "Y", Integer.valueOf(0), new String[] { "Y-Coordinate relative to players position" });
/*  44 */   public static Setting moveZ = new Setting(move, Mode.INTEGER, "Z", Integer.valueOf(0), new String[] { "Z-Coordinate relative to players position" }); @EventHandler
/*     */   private Listener<PlayerUpdateEvent> onUpdate;
/*     */   
/*  47 */   public AutoBuilder() { super(Group.WORLD, "AutoBuilder", new String[] { "Builds anything you want relative to the players position" });
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
/* 138 */     this.onUpdate = new Listener(event -> {
/*     */           
/* 140 */           if (Mouse.isButtonDown(2)) {
/* 141 */             if (down == true) {
/*     */               return;
/*     */             }
/*     */             
/* 145 */             down = true;
/* 146 */             BlockPos pos = mc.field_71476_x.func_178782_a();
/*     */             
/* 148 */             if (pos != null) {
/* 149 */               BlockPos relative = new BlockPos(pos.func_177958_n() - playerPos.func_177958_n(), pos.func_177956_o() - playerPos.func_177956_o(), pos.func_177952_p() - playerPos.func_177952_p());
/*     */               
/* 151 */               for (BuildPosition buildPosition : positions) {
/* 152 */                 if (buildPosition.blockPos.equals(relative)) {
/* 153 */                   positions.remove(buildPosition);
/* 154 */                   RenderBlock.remove(pos);
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */               } 
/* 159 */               positions.add(new BuildPosition(relative, getBlock(pos)));
/* 160 */               RenderBlock.add(pos, Color.CYAN, 1.0F);
/*     */             } 
/*     */           } else {
/* 163 */             down = false;
/*     */           } 
/*     */         }new java.util.function.Predicate[0]); autoBuilder = this; } public void onEnabled() { if (!check) { check = true; readFile(); }
/*     */      thread = new Thread() { public void run() { while (AutoBuilder.access$000() != null && AutoBuilder.access$000().equals(this)) { AutoBuilder.this.loop(); Mod.sleep(35); }
/*     */            } }
/*     */       ; thread.start(); }
/*     */   public void onDisabled() { clearStatus(); RotationUtil.stopRotating(); BaritoneUtil.forceCancel(); suspend(thread); thread = null; }
/* 170 */   public void readFile() { try { File file = new File(Settings.path + "/AutoBuilder.txt");
/* 171 */       if (!file.exists()) {
/* 172 */         sendMessage("Create a structure first", true);
/* 173 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/* 177 */       Scanner scanner = new Scanner(file);
/* 178 */       positions.clear();
/*     */       
/* 180 */       while (scanner.hasNextLine()) {
/* 181 */         String line = scanner.nextLine();
/*     */         
/* 183 */         if (!line.isEmpty()) {
/* 184 */           String[] split = line.split(",");
/* 185 */           positions.add(new BuildPosition(new BlockPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])), Block.func_149729_e(Integer.parseInt(split[3]))));
/*     */         } 
/*     */       } 
/*     */       
/* 189 */       scanner.close(); }
/* 190 */     catch (Exception e)
/* 191 */     { sendMessage("Error reading structure file. More info in your games log", true);
/* 192 */       e.printStackTrace(); }
/*     */      } public void onPostInit() { setStructure.addValueChangedListener(new Setting.ValueChangedListener(this, false) { public void valueChanged() { if (AutoBuilder.setStructure.booleanValue()) {
/*     */               AutoBuilder.access$100().clear(); AutoBuilder.access$202(Mod.getPlayerPos()); RenderBlock.add(AutoBuilder.access$200(), Color.GREEN, 3.0F); Mod.EVENT_BUS.subscribe(AutoBuilder.access$300());
/*     */             } else {
/*     */               Mod.EVENT_BUS.unsubscribe(AutoBuilder.access$300());
/*     */               RenderBlock.clear();
/*     */               AutoBuilder.this.saveFile();
/*     */               AutoBuilder.this.sendMessage("Successfully saved structure!", false);
/*     */             }  } }); } public void saveFile() { try {
/* 201 */       File file = new File(Settings.path + "/AutoBuilder.txt");
/* 202 */       file.delete();
/* 203 */       file.createNewFile();
/*     */       
/* 205 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/* 206 */       for (BuildPosition b : positions) {
/* 207 */         bw.write(b.blockPos.func_177958_n() + "," + b.blockPos.func_177956_o() + "," + b.blockPos.func_177952_p() + "," + Block.func_149682_b(b.block));
/* 208 */         bw.newLine();
/*     */       } 
/*     */       
/* 211 */       bw.close();
/* 212 */     } catch (Exception e) {
/* 213 */       sendMessage("Error saving structure. More info in your games log", true);
/* 214 */       e.printStackTrace();
/*     */     }  } public void loop() { if (mc.field_71439_g == null)
/*     */       return;  for (int i = 0; i < positions.size(); i++) { BuildPosition buildPosition = (BuildPosition)positions.get(i); BlockPos pos = getPlayerPos().func_177982_a(buildPosition.blockPos.func_177958_n(), buildPosition.blockPos.func_177956_o(), buildPosition.blockPos.func_177952_p()); if (BlockUtil.canPlaceBlock(pos)) { if (!InventoryUtil.hasBlock(buildPosition.block) && (Block.func_149682_b(buildPosition.block) != 144 || (Block.func_149682_b(buildPosition.block) == 144 && !InventoryUtil.hasItem(Items.field_151144_bL)))) { sendMessage("You dont have the required materials to build this structure", true); disable(); return; }
/*     */          if (Block.func_149682_b(buildPosition.block) == 144) { setStatus("Placing skull"); BlockUtil.placeItemNoSleep(Items.field_151144_bL, pos, true); }
/*     */         else { setStatus("Placing block"); BlockUtil.placeBlockNoSleep(buildPosition.block, pos, true); }
/*     */          sleep(delay.intValue()); }
/*     */        }
/*     */      if (move.booleanValue()) { BlockPos pos = getPlayerPos().func_177982_a(moveX.intValue(), moveY.intValue(), moveZ.intValue()); setStatus("Moving to X: " + pos.func_177958_n() + " Y: " + pos.func_177956_o() + " Z: " + pos.func_177952_p()); BaritoneUtil.walkTo(pos, true); }
/*     */     else if (toggle.booleanValue()) { disable(); }
/* 223 */      } public static class BuildPosition { public BuildPosition(BlockPos blockPos, Block block) { this.blockPos = blockPos;
/* 224 */       this.block = block; }
/*     */ 
/*     */     
/*     */     public BlockPos blockPos;
/*     */     public Block block; }
/*     */ 
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */