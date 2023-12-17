/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.combat.Surround;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import me.bebeli555.cookieclient.utils.MiningUtil;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class AutoEnderChestMiner
/*    */   extends Mod {
/*    */   private static Thread thread;
/* 17 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(350), new String[] { "Amount to wait after a place/break in milliseconds" });
/*    */ 
/*    */   
/* 20 */   public AutoEnderChestMiner() { super(Group.WORLD, "AutoEnderChestMiner", new String[] { "Places and mines enderchests for obsidian" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 25 */     thread = new Thread() {
/*    */         public void run() {
/* 27 */           while (AutoEnderChestMiner.access$000() != null && AutoEnderChestMiner.access$000().equals(this)) {
/* 28 */             AutoEnderChestMiner.this.loop();
/*    */             
/* 30 */             Mod.sleep(50);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 35 */     thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 40 */     Mod.EVENT_BUS.unsubscribe(MiningUtil.miningUtil);
/* 41 */     thread = null;
/*    */   }
/*    */   
/*    */   public void loop() {
/* 45 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */     
/* 49 */     BlockPos[] placements = { new BlockPos(true, false, false), new BlockPos(-1, false, false), new BlockPos(false, false, true), new BlockPos(false, false, -1), new BlockPos(true, true, false), new BlockPos(-1, true, false), new BlockPos(false, true, true), new BlockPos(false, true, -1) };
/*    */     
/* 51 */     BlockPos availableSpot = null;
/* 52 */     for (BlockPos pos : placements) {
/* 53 */       pos = getPlayerPos().func_177982_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*    */       
/* 55 */       if (getBlock(pos) == Blocks.field_150477_bB) {
/* 56 */         MiningUtil.mine(pos, true);
/* 57 */         sleep(delay.intValue()); return;
/*    */       } 
/* 59 */       if (BlockUtil.canPlaceBlock(pos) && isSolid(pos.func_177982_a(0, -1, 0))) {
/* 60 */         availableSpot = pos;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 65 */     if (!InventoryUtil.hasBlock(Blocks.field_150477_bB)) {
/* 66 */       sendMessage("You dont have any enderchests in your inventory", true);
/* 67 */       disable(); return;
/*    */     } 
/* 69 */     if (mc.field_71439_g.field_70163_u > 255.0D) {
/* 70 */       sendMessage("Cant place enderchests on the build limit", true);
/* 71 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/* 75 */     if (availableSpot != null) {
/* 76 */       Surround.centerMotionFull();
/* 77 */       BlockUtil.placeBlock(Blocks.field_150477_bB, availableSpot, true);
/* 78 */       sleep(delay.intValue());
/*    */     } else {
/* 80 */       sendMessage("No spot found to place an enderchest nearby", true);
/* 81 */       disable();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoEnderChestMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */