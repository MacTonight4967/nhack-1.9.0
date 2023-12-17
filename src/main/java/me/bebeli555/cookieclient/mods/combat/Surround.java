/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class Surround extends Mod {
/*  19 */   private int oldSlot = -1;
/*     */   
/*  21 */   public static Setting blocksPerTick = new Setting(Mode.INTEGER, "BlocksPerTick", Integer.valueOf(1), new String[] { "How many blocks to place per tick" });
/*  22 */   public static Setting center = new Setting(Mode.BOOLEAN, "Center", Boolean.valueOf(true), new String[] { "Center before so it can place all the blocks" });
/*  23 */   public static Setting centerMode = new Setting(center, "Mode", "Motion", new String[][] { { "Motion" }, { "Teleport" } });
/*  24 */   public static Setting toggle = new Setting(Mode.BOOLEAN, "Toggle", Boolean.valueOf(false), new String[] { "Toggles it off when there is no blocks to place" });
/*  25 */   public static Setting toggleOnSneak = new Setting(Mode.BOOLEAN, "ToggleOnSneak", Boolean.valueOf(false), new String[] { "Only makes it work when ur sneaking" });
/*  26 */   public static Setting toggleOnJump = new Setting(Mode.BOOLEAN, "ToggleOnJump", Boolean.valueOf(false), new String[] { "Toggles surround off when u jump" }); @EventHandler
/*     */   private Listener<PlayerMotionUpdateEvent> onMotionUpdate;
/*     */   public Surround() {
/*  29 */     super(Group.COMBAT, "Surround", new String[] { "Surrounds your feet with obsidian", "Useful for blocking crystal damage" });
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
/*  43 */     this.onMotionUpdate = new Listener(p_Event -> {
/*     */           
/*  45 */           if (mc.field_71439_g == null || (!mc.field_71474_y.field_74311_E.func_151470_d() && toggleOnSneak.booleanValue())) {
/*     */             return;
/*     */           }
/*     */           
/*  49 */           if (!InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/*  50 */             disable();
/*  51 */             sendMessage("You need obsidian", true);
/*     */             
/*     */             return;
/*     */           } 
/*  55 */           if (mc.field_71474_y.field_74314_A.func_151470_d() && toggleOnJump.booleanValue()) {
/*  56 */             disable();
/*     */             
/*     */             return;
/*     */           } 
/*  60 */           if (Block.func_149634_a(mc.field_71439_g.func_184614_ca().func_77973_b()) != Blocks.field_150343_Z) {
/*  61 */             this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */           }
/*     */           
/*  64 */           int blocksPlaced = 0; BlockPos[] arrayOfBlockPos; int i; byte b;
/*  65 */           for (arrayOfBlockPos = getBlocksToPlace(), i = arrayOfBlockPos.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos[b];
/*  66 */             if (BlockUtil.distance(getPlayerPos(), pos) > 2) {
/*     */               break;
/*     */             }
/*     */             
/*  70 */             if (!isSolid(pos) && 
/*  71 */               InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/*  72 */               boolean canPlace = BlockUtil.canPlaceBlock(pos);
/*  73 */               boolean canPlaceBelow = BlockUtil.canPlaceBlock(pos.func_177982_a(0, -1, 0));
/*     */               
/*  75 */               if (center.booleanValue() && !canPlace) {
/*  76 */                 if (centerMode.stringValue().equals("Motion") && !centerMotion())
/*     */                   return; 
/*  78 */                 if (centerMode.stringValue().equals("Teleport")) {
/*  79 */                   centerTeleport();
/*     */                 }
/*     */               } 
/*     */               
/*  83 */               if (canPlace) {
/*  84 */                 BlockUtil.placeBlockOnThisThread(Blocks.field_150343_Z, pos, true);
/*  85 */               } else if (canPlaceBelow) {
/*  86 */                 BlockUtil.placeBlockOnThisThread(Blocks.field_150343_Z, pos.func_177982_a(0, -1, 0), true);
/*     */               } else {
/*     */                 continue;
/*     */               } 
/*     */               
/*  91 */               RotationUtil.stopRotating();
/*  92 */               blocksPlaced++;
/*  93 */               if (blocksPlaced >= blocksPerTick.intValue()) {
/*     */                 return;
/*     */               }
/*     */               
/*     */               b++;
/*     */             }  }
/*     */           
/* 100 */           if (blocksPlaced == 0 && toggle.booleanValue()) {
/* 101 */             disable();
/* 102 */           } else if (blocksPlaced == 0 && 
/* 103 */             this.oldSlot != -1) {
/* 104 */             mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot;
/* 105 */             this.oldSlot = -1;
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean centerMotion() {
/* 114 */     if (isCentered()) {
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     centerPos = new double[] { Math.floor(mc.field_71439_g.field_70165_t) + 0.5D, Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v) + 0.5D };
/* 119 */     mc.field_71439_g.field_70159_w = (centerPos[0] - mc.field_71439_g.field_70165_t) / 2.0D;
/* 120 */     mc.field_71439_g.field_70179_y = (centerPos[2] - mc.field_71439_g.field_70161_v) / 2.0D;
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void centerMotionFull() {
/* 128 */     if (isCentered()) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     centerPos = new double[] { Math.floor(mc.field_71439_g.field_70165_t) + 0.5D, Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v) + 0.5D };
/*     */     
/* 134 */     mc.field_71439_g.field_70159_w = (centerPos[0] - mc.field_71439_g.field_70165_t) / 2.0D;
/* 135 */     mc.field_71439_g.field_70179_y = (centerPos[2] - mc.field_71439_g.field_70161_v) / 2.0D;
/*     */     
/* 137 */     sleepUntil(() -> (Math.abs(centerPos[0] - mc.field_71439_g.field_70165_t) <= 0.1D && Math.abs(centerPos[2] - mc.field_71439_g.field_70161_v) <= 0.1D), 1000);
/* 138 */     mc.field_71439_g.field_70159_w = 0.0D;
/* 139 */     mc.field_71439_g.field_70179_y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void centerTeleport() {
/* 146 */     if (isCentered()) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     centerPos = new double[] { Math.floor(mc.field_71439_g.field_70165_t) + 0.5D, Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v) + 0.5D };
/* 151 */     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Position(centerPos[0], mc.field_71439_g.field_70163_u, centerPos[2], mc.field_71439_g.field_70122_E));
/* 152 */     mc.field_71439_g.func_70107_b(centerPos[0], mc.field_71439_g.field_70163_u, centerPos[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCentered() {
/* 159 */     centerPos = new double[] { Math.floor(mc.field_71439_g.field_70165_t) + 0.5D, Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v) + 0.5D };
/* 160 */     return (Math.abs(centerPos[0] - mc.field_71439_g.field_70165_t) <= 0.1D && Math.abs(centerPos[2] - mc.field_71439_g.field_70161_v) <= 0.1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSurrounded(BlockPos p) {
/* 167 */     BlockPos[] positions = { p.func_177982_a(1, 0, 0), p.func_177982_a(-1, 0, 0), p.func_177982_a(0, 0, 1), p.func_177982_a(0, 0, -1) };
/*     */     
/* 169 */     for (BlockPos pos : positions) {
/* 170 */       if (getBlock(pos) != Blocks.field_150343_Z && getBlock(pos) != Blocks.field_150357_h) {
/* 171 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos[] getBlocksToPlace() {
/* 182 */     p = getPlayerPos();
/* 183 */     return new BlockPos[] { p.func_177982_a(1, 0, 0), p.func_177982_a(-1, 0, 0), p.func_177982_a(0, 0, 1), p.func_177982_a(0, 0, -1) };
/*     */   }
/*     */   
/*     */   public void onDisabled() {
/*     */     if (this.oldSlot != -1)
/*     */       mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot; 
/*     */     this.oldSlot = -1;
/*     */     RotationUtil.stopRotating();
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\Surround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */