/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class AutoTrap
/*     */   extends Mod {
/*     */   public static Thread thread;
/*  19 */   public static int oldSlot = -1;
/*     */   
/*  21 */   public static Setting toggle = new Setting(Mode.BOOLEAN, "Toggle", Boolean.valueOf(false), new String[] { "Toggle the module off after a trap" });
/*  22 */   public static Setting range = new Setting(Mode.DOUBLE, "Range", Integer.valueOf(4), new String[] { "How far the player can be for autotrap to work" });
/*  23 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(50), new String[] { "Delay in ms to wait between placing blocks" });
/*     */ 
/*     */   
/*  26 */   public AutoTrap() { super(Group.COMBAT, "AutoTrap", new String[] { "Traps the nearby enemy with obsidian" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  31 */     thread = new Thread() {
/*     */         public void run() {
/*  33 */           while (AutoTrap.thread != null && AutoTrap.thread.equals(this)) {
/*  34 */             AutoTrap.this.loop();
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  39 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  44 */     RotationUtil.stopRotating();
/*  45 */     suspend(thread);
/*  46 */     thread = null;
/*     */   }
/*     */   
/*     */   public void loop() {
/*  50 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     if (!InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/*  55 */       sendMessage("You dont have any obsidian", true);
/*  56 */       disable();
/*     */     } 
/*     */     
/*  59 */     EntityPlayer closest = PlayerUtil.getClosestEnemy();
/*  60 */     if (closest != null && mc.field_71439_g.func_70032_d(closest) <= range.doubleValue()) {
/*  61 */       BlockPos p = new BlockPos(closest.field_70165_t, closest.field_70163_u, closest.field_70161_v);
/*     */       
/*  63 */       ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
/*  64 */       positions.add(p.func_177982_a(1, 0, 0));
/*  65 */       positions.add(p.func_177982_a(-1, 0, 0));
/*  66 */       positions.add(p.func_177982_a(0, 0, 1));
/*  67 */       positions.add(p.func_177982_a(0, 0, -1));
/*  68 */       positions.add(p.func_177982_a(1, 1, 0));
/*  69 */       positions.add(p.func_177982_a(-1, 1, 0));
/*  70 */       positions.add(p.func_177982_a(0, 1, -1));
/*  71 */       positions.add(p.func_177982_a(0, 1, 1));
/*  72 */       positions.add(p.func_177982_a(0, 2, 0));
/*     */       
/*  74 */       if (!isSolid(p.func_177982_a(0, 3, 0))) {
/*  75 */         BlockPos best = null;
/*  76 */         double highestDistance = -2.147483648E9D;
/*     */         
/*  78 */         for (BlockPos pos : new BlockPos[] { p.func_177982_a(1, 2, 0), p.func_177982_a(-1, 2, 0), p.func_177982_a(0, 2, 1), p.func_177982_a(0, 2, -1) }) {
/*  79 */           if (mc.field_71439_g.func_174818_b(pos) > highestDistance) {
/*  80 */             best = pos;
/*  81 */             highestDistance = mc.field_71439_g.func_174818_b(pos);
/*     */           } 
/*     */         } 
/*     */         
/*  85 */         positions.add(best);
/*     */       } 
/*     */ 
/*     */       
/*  89 */       BlockPos best = null;
/*  90 */       double highestDistance = -2.147483648E9D;
/*     */       
/*  92 */       for (BlockPos pos : positions) {
/*  93 */         if (BlockUtil.canPlaceBlock(pos) && BlockUtil.canBeClicked(pos) && mc.field_71439_g.func_174818_b(pos) > highestDistance) {
/*  94 */           best = pos;
/*  95 */           highestDistance = mc.field_71439_g.func_174818_b(pos);
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       if (best != null) {
/* 100 */         if (oldSlot == -1) {
/* 101 */           oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */         }
/*     */         
/* 104 */         BlockUtil.placeBlockNoSleep(Blocks.field_150343_Z, best, true);
/* 105 */         sleep(delay.intValue());
/* 106 */       } else if (toggle.booleanValue()) {
/* 107 */         disable();
/*     */       } else {
/* 109 */         RotationUtil.stopRotating();
/* 110 */         if (oldSlot != -1) mc.field_71439_g.field_71071_by.field_70461_c = oldSlot; 
/* 111 */         oldSlot = -1;
/* 112 */         sleep(25);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\AutoTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */