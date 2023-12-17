/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ChestSwap
/*    */   extends Mod {
/* 12 */   public ChestSwap() { super(Group.MISC, "ChestSwap", new String[] { "Switches chestplate with elytra", "Or elytra with chestplate", "Depending which one ur wearing currently" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 17 */     ItemStack itemStack = InventoryUtil.getItemStack(38);
/*    */     
/* 19 */     if (itemStack.func_77973_b() == Items.field_185160_cR) {
/* 20 */       int slot = getChestPlateSlot();
/*    */       
/* 22 */       if (slot != -1) {
/* 23 */         InventoryUtil.clickSlot(slot);
/* 24 */         InventoryUtil.clickSlot(38);
/* 25 */         InventoryUtil.clickSlot(slot);
/*    */       } else {
/* 27 */         sendMessage("You dont have a chestplate", true);
/*    */       } 
/* 29 */     } else if (InventoryUtil.hasItem(Items.field_185160_cR)) {
/* 30 */       int slot = InventoryUtil.getSlot(Items.field_185160_cR);
/* 31 */       InventoryUtil.clickSlot(slot);
/* 32 */       InventoryUtil.clickSlot(38);
/* 33 */       InventoryUtil.clickSlot(slot);
/*    */     } else {
/* 35 */       sendMessage("You dont have an elytra", true);
/*    */     } 
/*    */     
/* 38 */     disable();
/*    */   }
/*    */   
/*    */   public int getChestPlateSlot() {
/* 42 */     Item[] items = { Items.field_151163_ad, Items.field_151023_V, Items.field_151030_Z, Items.field_151171_ah, Items.field_151027_R };
/*    */     
/* 44 */     for (Item item : items) {
/* 45 */       if (InventoryUtil.hasItem(item)) {
/* 46 */         return InventoryUtil.getSlot(item);
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\ChestSwap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */