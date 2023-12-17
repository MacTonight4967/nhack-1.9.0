/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.CrystalUtil;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class CrystalBlock extends Mod {
/* 17 */   public static Setting range = new Setting(Mode.INTEGER, "Range", Integer.valueOf(4), new String[] { "Range around player to search for", "Spots to place the obby on" });
/*    */ 
/*    */   
/* 20 */   public CrystalBlock() { super(Group.WORLD, "CrystalBlock", new String[] { "Places obsidian to the best spot between", "You and the end crystal to block damage" }); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 26 */     if (!InventoryUtil.hasBlock(Blocks.field_150343_Z)) {
/* 27 */       sendMessage("You need obsidian", true);
/* 28 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 33 */     EntityEnderCrystal best = null;
/* 34 */     double mostDamage = -2.147483648E9D;
/* 35 */     for (EntityEnderCrystal crystal : CrystalUtil.getCrystals(10.0D)) {
/* 36 */       double damage = CrystalUtil.calculateDamage(crystal.func_174791_d(), mc.field_71439_g);
/*    */       
/* 38 */       if (damage > 0.0D && damage > mostDamage) {
/* 39 */         mostDamage = damage;
/* 40 */         best = crystal;
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     if (best == null) {
/* 45 */       sendMessage("Found no nearby crystals to block", true);
/* 46 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 51 */     BlockPos bestPlace = null;
/* 52 */     double lowestDamage = 2.147483647E9D;
/* 53 */     for (BlockPos pos : BlockUtil.getAll(range.intValue())) {
/* 54 */       if (BlockUtil.canPlaceBlock(pos)) {
/* 55 */         IBlockState old = mc.field_71441_e.func_180495_p(pos);
/* 56 */         mc.field_71441_e.func_175656_a(pos, Blocks.field_150343_Z.func_176223_P());
/* 57 */         double damage = CrystalUtil.calculateDamage(best.func_174791_d(), mc.field_71439_g);
/* 58 */         mc.field_71441_e.func_175656_a(pos, old);
/*    */         
/* 60 */         if (damage < lowestDamage) {
/* 61 */           lowestDamage = damage;
/* 62 */           bestPlace = pos;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     if (bestPlace == null) {
/* 68 */       sendMessage("Found no spot to place block on to block dmg", true);
/* 69 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 74 */     BlockUtil.placeBlockOnThisThread(Blocks.field_150343_Z, bestPlace, true);
/* 75 */     disable();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public void onDisabled() { RotationUtil.stopRotating(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\CrystalBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */