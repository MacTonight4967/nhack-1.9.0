/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class AutoHotbar extends Mod {
/* 12 */   private int lastSlot = -1;
/*    */   
/*    */   private Item lastItem;
/*    */   
/* 16 */   public AutoHotbar() { super(Group.MISC, "AutoHotbar", new String[] { "When u use a stack from ur hotbar this will automatically", "Put a new stack of the same item to the same slot" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 21 */     this.lastSlot = -1;
/* 22 */     this.lastItem = null;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 27 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     Item held = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 32 */     if (this.lastSlot == mc.field_71439_g.field_71071_by.field_70461_c && this.lastItem != held && held == Items.field_190931_a && mc.field_71462_r == null && (
/* 33 */       !mc.field_71474_y.field_74316_C.func_151470_d() || (mc.field_71474_y.field_74316_C.func_151470_d() && mc.field_71474_y.field_74313_G.func_151470_d()))) {
/* 34 */       int slot = InventoryUtil.getSlot(this.lastItem);
/* 35 */       if (slot != -1) {
/* 36 */         InventoryUtil.clickSlot(slot);
/* 37 */         InventoryUtil.clickSlot(mc.field_71439_g.field_71071_by.field_70461_c);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 42 */     this.lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 43 */     this.lastItem = mc.field_71439_g.func_184614_ca().func_77973_b();
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoHotbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */