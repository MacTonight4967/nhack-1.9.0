/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class AutoEnderpearl
/*    */   extends Mod {
/* 12 */   public AutoEnderpearl() { super(Group.WORLD, "AutoEnderpearl", new String[] { "Throws enderpearl if you have one", "Also other players will not see", "That you ever held the pearl in ur hand", "When it throws it" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 17 */     if (mc.field_71439_g == null || !InventoryUtil.hasItem(Items.field_151079_bi)) {
/* 18 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/* 22 */     int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 23 */     InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151079_bi), false);
/* 24 */     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 25 */     InventoryUtil.switchItem(oldSlot, false);
/* 26 */     disable();
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoEnderpearl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */