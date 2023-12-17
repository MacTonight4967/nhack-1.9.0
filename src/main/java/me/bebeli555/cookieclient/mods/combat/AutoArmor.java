/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class AutoArmor
/*    */   extends Mod
/*    */ {
/*    */   private static Thread thread;
/* 15 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(200), new String[] { "Delay in ms to wait between placing armor" });
/*    */ 
/*    */   
/* 18 */   public AutoArmor() { super(Group.COMBAT, "AutoArmor", new String[] { "Automatically wears armor if u have any" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 23 */     thread = new Thread() {
/*    */         public void run() {
/* 25 */           while (AutoArmor.access$000() != null && AutoArmor.access$000().equals(this)) {
/* 26 */             AutoArmor.this.loop();
/*    */             
/* 28 */             Mod.sleep(150);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 33 */     thread.start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public void onDisabled() { thread = null; }
/*    */ 
/*    */   
/*    */   public void loop() {
/* 42 */     if (mc.field_71439_g == null || AutoTotem.isContainerOpen()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 47 */     if (InventoryUtil.getItemStack(39).func_77973_b() == Items.field_190931_a) {
/* 48 */       switchSlot(getBestSlot(new Item[] { Items.field_151161_ac, Items.field_151028_Y, Items.field_151020_U, Items.field_151169_ag, Items.field_151024_Q }, ), 39);
/*    */     }
/*    */ 
/*    */     
/* 52 */     if (InventoryUtil.getItemStack(38).func_77973_b() == Items.field_190931_a) {
/* 53 */       switchSlot(getBestSlot(new Item[] { Items.field_151163_ad, Items.field_151030_Z, Items.field_151023_V, Items.field_151171_ah, Items.field_151027_R }, ), 38);
/*    */     }
/*    */ 
/*    */     
/* 57 */     if (InventoryUtil.getItemStack(37).func_77973_b() == Items.field_190931_a) {
/* 58 */       switchSlot(getBestSlot(new Item[] { Items.field_151173_ae, Items.field_151165_aa, Items.field_151022_W, Items.field_151149_ai, Items.field_151026_S }, ), 37);
/*    */     }
/*    */ 
/*    */     
/* 62 */     if (InventoryUtil.getItemStack(36).func_77973_b() == Items.field_190931_a) {
/* 63 */       switchSlot(getBestSlot(new Item[] { Items.field_151175_af, Items.field_151167_ab, Items.field_151029_X, Items.field_151151_aj, Items.field_151021_T }, ), 36);
/*    */     }
/*    */   }
/*    */   
/*    */   public void switchSlot(int slot, int slot2) {
/* 68 */     if (slot == -1) {
/*    */       return;
/*    */     }
/*    */     
/* 72 */     InventoryUtil.clickSlot(slot);
/* 73 */     InventoryUtil.clickSlot(slot2);
/* 74 */     sleep(delay.intValue());
/*    */   }
/*    */   
/*    */   public static int getBestSlot(Item[] items) {
/* 78 */     for (Item item : items) {
/* 79 */       for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 80 */         if (itemStack.itemStack.func_77973_b() == item) {
/* 81 */           return itemStack.slotId;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\AutoArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */