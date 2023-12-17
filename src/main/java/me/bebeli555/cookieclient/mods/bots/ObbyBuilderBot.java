/*     */ package me.bebeli555.cookieclient.mods.bots;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.combat.Surround;
/*     */ import me.bebeli555.cookieclient.utils.BaritoneUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.MiningUtil;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObbyBuilderBot
/*     */   extends Mod
/*     */ {
/*     */   private static Thread thread;
/*     */   private static BaritoneUtil.BaritoneSettings baritoneSettings;
/*  39 */   public static Setting name = new Setting(Mode.TEXT, "Name", "", new String[] { "Name of the schematic it will build", "You need to give the extension too like test.schematic", "Needs to be stored at .minecraft/schematics/" });
/*  40 */   public static Setting x = new Setting(Mode.INTEGER, "X", "", new String[] { "X-Coordinate of the schematic" });
/*  41 */   public static Setting y = new Setting(Mode.INTEGER, "Y", "", new String[] { "Y-Coordinate of the schematic" });
/*  42 */   public static Setting z = new Setting(Mode.INTEGER, "Z", "", new String[] { "Z-Coordinate of the schematic" });
/*  43 */   public static Setting shulkerName = new Setting(Mode.TEXT, "ShulkerName", "", new String[] { "The exact name of the shulker where you have the echests", "It will take the shulker with the given name to get more echests to mine", "Note: If the shulker has no echests the bot will throw it away" });
/*     */ 
/*     */   
/*  46 */   public ObbyBuilderBot() { super(Group.BOTS, "ObbyBuilderBot", new String[] { "Builds obsidian structures using baritone", "And mines echests for more obby when out", "Works on schematics that are built on the build limit!" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  51 */     if (!InventoryUtil.hasBlock(Blocks.field_150477_bB)) {
/*  52 */       sendMessage("You need to have an enderchest in ur inventory", true);
/*  53 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     baritoneSettings = new BaritoneUtil.BaritoneSettings();
/*  58 */     baritoneSettings.saveCurrentSettings();
/*     */     
/*  60 */     BaritoneUtil.setSetting("allowInventory", true);
/*  61 */     BaritoneUtil.setSetting("allowSprint", false);
/*  62 */     BaritoneUtil.setSetting("allowBreak", true);
/*  63 */     BaritoneUtil.setSetting("allowPlace", true);
/*     */     
/*  65 */     thread = new Thread() {
/*     */         public void run() {
/*  67 */           while (ObbyBuilderBot.access$000() != null && ObbyBuilderBot.access$000().equals(this)) {
/*  68 */             if (Mod.mc.field_71439_g != null) {
/*     */               try {
/*  70 */                 ObbyBuilderBot.this.loop();
/*  71 */               } catch (Exception e) {
/*  72 */                 System.out.println("ObbyBuilderBot - Unhandled exception");
/*  73 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */             
/*  77 */             Mod.sleep(50);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  82 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  87 */     Mod.EVENT_BUS.unsubscribe(MiningUtil.miningUtil);
/*  88 */     clearStatus();
/*  89 */     if (baritoneSettings != null) baritoneSettings.loadSettings(); 
/*  90 */     if (mc.field_71439_g != null) {
/*  91 */       BaritoneUtil.forceCancel();
/*  92 */       BaritoneUtil.sendCommand("sel clear");
/*     */     } 
/*     */     
/*  95 */     suspend(thread);
/*  96 */     thread = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loop() {
/* 101 */     if (InventoryUtil.getAmountOfBlock(Blocks.field_150343_Z) <= 80) {
/* 102 */       BaritoneUtil.forceCancel();
/*     */       
/* 104 */       if (!MiningUtil.hasPickaxe()) {
/* 105 */         sendMessage("You need a diamond pickaxe!", true);
/* 106 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/* 110 */       if (mc.field_71439_g.field_70163_u > 255.0D && !InventoryUtil.hasItem(Items.field_151131_as)) {
/* 111 */         sendMessage("You need a water bucket!", true);
/* 112 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       if (mc.field_71439_g.field_70163_u > 255.0D && !InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/* 117 */         sendMessage("You need atleast 4 obsidian in your inventory!", true);
/* 118 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 123 */       BlockPos bestPos = null;
/* 124 */       label148: for (BlockPos pos : BlockUtil.getAll(50)) {
/* 125 */         if (!isSolid(pos)) {
/* 126 */           BlockPos[] solid = { pos.func_177982_a(0, -1, 0), pos.func_177982_a(0, -1, 1), pos.func_177982_a(-1, -1, 0), pos.func_177982_a(-1, -1, 1), pos.func_177982_a(1, -1, 0), pos.func_177982_a(-2, -1, 0) };
/* 127 */           BlockPos[] air = { pos.func_177982_a(0, 0, 1), pos.func_177982_a(-1, 0, 0), pos.func_177982_a(-1, 0, 1), pos.func_177982_a(0, 1, 0), pos.func_177982_a(0, 1, 1), pos.func_177982_a(-1, 1, 0), pos.func_177982_a(-1, 1, 1) };
/*     */           
/* 129 */           for (BlockPos solidCheck : solid) {
/* 130 */             if (!isSolid(solidCheck)) {
/*     */               continue label148;
/*     */             }
/*     */           } 
/*     */           
/* 135 */           for (BlockPos airCheck : air) {
/* 136 */             if (getBlock(airCheck) != Blocks.field_150350_a) {
/*     */               continue label148;
/*     */             }
/*     */           } 
/*     */           
/* 141 */           if (BaritoneUtil.canPath(pos)) {
/* 142 */             bestPos = pos;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 148 */       if (bestPos == null) {
/* 149 */         sendMessage("Found no suitable place nearby to break echests", true);
/* 150 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 155 */       if (bestPos.func_177956_o() > 255) {
/* 156 */         setStatus("Walking closer to location where to grind echests");
/* 157 */         BaritoneUtil.walkTo(bestPos.func_177982_a(-2, 0, 0), true);
/* 158 */         Surround.centerMotionFull();
/*     */         
/* 160 */         BlockPos[] breakPositions = { bestPos.func_177982_a(0, -1, 0), bestPos.func_177982_a(0, -1, 1), bestPos.func_177982_a(-1, -1, 0), bestPos.func_177982_a(-1, -1, 1) };
/* 161 */         setStatus("Mining area to place echests on");
/* 162 */         while (thread != null && thread.equals(Thread.currentThread())) {
/* 163 */           boolean allAir = true;
/* 164 */           for (BlockPos pos : breakPositions) {
/* 165 */             if (getBlock(pos) != Blocks.field_150350_a) {
/* 166 */               allAir = false;
/* 167 */               MiningUtil.mine(pos, false);
/* 168 */               sleep(500);
/*     */             } 
/*     */           } 
/*     */           
/* 172 */           if (allAir) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 178 */         setStatus("Placing water so we can place a block below");
/* 179 */         InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151131_as), true);
/* 180 */         RotationUtil.rotate((new Vec3d(bestPos)).func_72441_c(-0.8D, 1.0D, 0.5D), true);
/* 181 */         PlayerUtil.rightClick();
/* 182 */         BlockPos pos = bestPos.func_177982_a(0, -3, 0);
/* 183 */         sleepUntil(() -> (getBlock(pos) == Blocks.field_150355_j), 15000);
/*     */ 
/*     */         
/* 186 */         setStatus("Placing a block below");
/* 187 */         BlockPos pos2 = bestPos.func_177982_a(-1, -2, 1);
/* 188 */         BlockUtil.placeBlock(Blocks.field_150343_Z, pos2, false);
/* 189 */         sleepUntil(() -> (getBlock(pos2) == Blocks.field_150343_Z || getBlock(pos2.func_177982_a(false, false, -1)) == Blocks.field_150343_Z), 15000);
/*     */ 
/*     */         
/* 192 */         setStatus("Taking water back");
/* 193 */         RotationUtil.rotate((new Vec3d(bestPos)).func_72441_c(-0.8D, 1.0D, 0.5D), true);
/* 194 */         InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151133_ar), true);
/* 195 */         PlayerUtil.rightClick();
/* 196 */         setStatus("Waiting for water to go down");
/* 197 */         sleepUntil(() -> (mc.field_71441_e.func_180495_p(pos).func_185904_a() != Material.field_151586_h), 15000);
/*     */ 
/*     */         
/* 200 */         setStatus("Filling floor with obsidian");
/* 201 */         sleep(50);
/* 202 */         BaritoneUtil.sendCommand("sel clear");
/* 203 */         BaritoneUtil.sendCommand("sel pos1 " + bestPos.func_177958_n() + " " + (bestPos.func_177956_o() - 2) + " " + bestPos.func_177952_p());
/* 204 */         BaritoneUtil.sendCommand("sel pos2 " + (bestPos.func_177958_n() - 1) + " " + (bestPos.func_177956_o() - 2) + " " + (bestPos.func_177952_p() + 1));
/* 205 */         BaritoneUtil.sendCommand("sel set obsidian");
/* 206 */         sleepUntil(() -> !BaritoneUtil.isBuilding(), 15000);
/*     */         
/* 208 */         bestPos = bestPos.func_177982_a(0, -1, 0);
/*     */       } 
/*     */       
/* 211 */       int broken = 0;
/* 212 */       label149: while (!InventoryUtil.isFull() && thread != null && thread.equals(Thread.currentThread())) {
/* 213 */         BlockPos pos = bestPos.func_177982_a(0, 0, 1);
/*     */         
/* 215 */         if (!isSolid(pos.func_177982_a(0, -1, 0)) || !isSolid(bestPos.func_177982_a(0, -1, 0))) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 220 */         if (!getPlayerPos().equals(bestPos) || !Surround.isCentered()) {
/* 221 */           setStatus("Walking to position");
/* 222 */           BaritoneUtil.walkTo(bestPos, true);
/* 223 */           Surround.centerMotionFull();
/*     */         } 
/*     */ 
/*     */         
/* 227 */         if (InventoryUtil.getAmountOfBlock(Blocks.field_150477_bB) > 4) {
/* 228 */           if (isSolid(pos)) {
/* 229 */             setStatus("Mining enderchest");
/* 230 */             MiningUtil.mine(pos, false);
/* 231 */             broken++;
/* 232 */             Mod.sleep(300);
/*     */           } else {
/* 234 */             setStatus("Placing enderchest");
/* 235 */             Surround.centerMotionFull();
/* 236 */             BlockUtil.placeBlock(Blocks.field_150477_bB, pos, false);
/* 237 */             Mod.sleep(300);
/*     */           } 
/*     */ 
/*     */           
/* 241 */           if (broken >= 64) {
/* 242 */             broken = 0;
/* 243 */             collectGroundItems();
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 249 */         if (!isSolid(pos)) {
/* 250 */           setStatus("Placing enderchest");
/* 251 */           BlockUtil.placeBlock(Blocks.field_150477_bB, pos, false);
/* 252 */           sleep(1000);
/*     */         } 
/*     */         
/* 255 */         BlockPos shulker = bestPos.func_177982_a(-1, 0, 0);
/*     */ 
/*     */         
/* 258 */         if (!isSolid(shulker)) {
/* 259 */           for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 260 */             if (itemStack.itemStack.func_82833_r().equals(shulkerName.stringValue())) {
/* 261 */               setStatus("Placing shulker");
/* 262 */               InventoryUtil.switchItem(itemStack.slotId, true);
/* 263 */               BlockUtil.placeBlock(null, shulker, false);
/* 264 */               sleep(500);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 271 */         if (isSolid(shulker)) {
/*     */           
/* 273 */           if (!(getBlock(shulker) instanceof net.minecraft.block.BlockShulkerBox)) {
/* 274 */             setStatus("Mining block where shulker is supposed to be");
/* 275 */             MiningUtil.mine(shulker, false);
/*     */             
/*     */             continue;
/*     */           } 
/* 279 */           setStatus("Getting enderchests from shulker");
/* 280 */           Vec3d hitVec = (new Vec3d(shulker)).func_72441_c(0.5D, -0.5D, 0.5D);
/* 281 */           RotationUtil.rotate(hitVec, true);
/* 282 */           mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, shulker, EnumFacing.EAST, hitVec, EnumHand.MAIN_HAND);
/* 283 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 284 */           sleepUntil(() -> mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiShulkerBox, 2500);
/* 285 */           sleep(1000);
/*     */           
/* 287 */           if (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiShulkerBox) {
/* 288 */             NonNullList<ItemStack> items = mc.field_71439_g.field_71070_bA.func_75138_a();
/* 289 */             boolean empty = true;
/*     */             
/* 291 */             for (int i = 0; i < 27; i++) {
/* 292 */               if (InventoryUtil.getAmountOfBlock(Blocks.field_150477_bB) < 245 && 
/* 293 */                 Block.func_149634_a(((ItemStack)items.get(i)).func_77973_b()) == Blocks.field_150477_bB) {
/* 294 */                 empty = false;
/*     */                 
/* 296 */                 if (InventoryUtil.getEmptySlots() > 1) {
/* 297 */                   mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.PICKUP, mc.field_71439_g);
/* 298 */                   sleep(100);
/* 299 */                   InventoryUtil.clickSlot(InventoryUtil.getEmptySlot(), 18);
/* 300 */                   sleep(500);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 307 */             setStatus("Mining shulker");
/* 308 */             mc.field_71439_g.func_71053_j();
/* 309 */             MiningUtil.mine(shulker, false);
/* 310 */             sleepUntil(() -> hasShulker(), 3500);
/* 311 */             if (!hasShulker()) {
/* 312 */               collectGroundItems();
/* 313 */               sleep(500);
/*     */             } 
/*     */ 
/*     */             
/* 317 */             if (empty && hasShulker()) {
/* 318 */               setStatus("Throwing empty shulker away");
/*     */               
/* 320 */               mc.field_71439_g.field_70177_z = 179.0F;
/* 321 */               mc.field_71439_g.field_70125_A = -25.0F;
/*     */               
/* 323 */               for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 324 */                 if (itemStack.itemStack.func_82833_r().equals(shulkerName.stringValue())) {
/* 325 */                   InventoryUtil.switchItem(itemStack.slotId, true);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 330 */               sleep(500);
/* 331 */               mc.field_71439_g.func_71040_bB(false);
/* 332 */               sleep(500);
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 338 */         if (!hasShulker()) {
/* 339 */           ItemStack held = mc.field_71439_g.field_71071_by.func_70448_g();
/* 340 */           if (held != null && held.func_82833_r().equals(shulkerName.stringValue())) {
/* 341 */             InventoryUtil.clickSlot(8);
/* 342 */             sleep(150);
/* 343 */             InventoryUtil.clickSlot(InventoryUtil.getEmptySlot());
/* 344 */             sleep(150);
/*     */             
/*     */             continue;
/*     */           } 
/* 348 */           setStatus("Getting shulker from enderchest");
/* 349 */           if (!(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest)) {
/* 350 */             Vec3d hitVec = (new Vec3d(pos)).func_72441_c(0.5D, -0.5D, 0.5D);
/* 351 */             RotationUtil.rotate(hitVec, true);
/* 352 */             mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, EnumFacing.NORTH, hitVec, EnumHand.MAIN_HAND);
/* 353 */             mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 354 */             sleepUntil(() -> mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest, 5000);
/* 355 */             sleep(1000);
/*     */           } 
/*     */           
/* 358 */           if (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest) {
/* 359 */             NonNullList<ItemStack> items = mc.field_71439_g.field_71070_bA.func_75138_a();
/* 360 */             for (int i = 0; i < 27; i++) {
/* 361 */               if (((ItemStack)items.get(i)).func_82833_r().equals(shulkerName.stringValue())) {
/* 362 */                 sleep(500);
/* 363 */                 mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.PICKUP, mc.field_71439_g);
/* 364 */                 sleep(300);
/* 365 */                 InventoryUtil.clickSlot(InventoryUtil.getEmptySlot(), 18);
/* 366 */                 mc.field_71439_g.func_71053_j();
/* 367 */                 sleep(1000);
/*     */                 
/*     */                 continue label149;
/*     */               } 
/*     */             } 
/* 372 */             sendMessage("No shulker box with name " + shulkerName.stringValue() + " was found in your enderchest", true);
/* 373 */             disable();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 380 */       collectGroundItems();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 385 */       setStatus("Building schematic named " + name.stringValue() + " to X: " + x.intValue() + " Y: " + y.intValue() + " Z: " + z.intValue());
/* 386 */       BaritoneUtil.forceCancel();
/* 387 */       BaritoneUtil.sendCommand("build " + name.stringValue() + " " + x.intValue() + " " + y.intValue() + " " + z.intValue());
/*     */       
/* 389 */       sleepUntil(() -> (InventoryUtil.getAmountOfBlock(Blocks.field_150343_Z) <= 80 || mc.field_71439_g == null), -1, 1000);
/* 390 */       BaritoneUtil.forceCancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void collectGroundItems() {
/* 396 */     setStatus("Collecting nearby ground items", "ObbyBuilderBot");
/*     */     
/* 398 */     list = new ArrayList();
/* 399 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 400 */       if (entity instanceof net.minecraft.entity.item.EntityItem && entity.func_70032_d(mc.field_71439_g) <= 5.0F) {
/* 401 */         list.add(entity.func_180425_c());
/*     */       }
/*     */     } 
/*     */     
/* 405 */     for (BlockPos pos : list) {
/* 406 */       if (BaritoneUtil.canPath(pos)) {
/* 407 */         BaritoneUtil.walkTo(pos, true);
/* 408 */         sleep(1000);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean hasShulker() {
/* 414 */     for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 415 */       if (itemStack.itemStack.func_82833_r().equals(shulkerName.stringValue())) {
/* 416 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 420 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\ObbyBuilderBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */