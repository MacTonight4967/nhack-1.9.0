/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemUtil
/*    */   extends Mod
/*    */ {
/* 14 */   public static int getPercentageDurability(ItemStack itemStack) { return (int)(getDurability(itemStack) / itemStack.func_77958_k() * 100.0D); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static boolean hasDurability(ItemStack itemStack) { return (itemStack.func_77958_k() != 0); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChatFormatting getDurabilityColor(ItemStack itemStack) {
/* 29 */     ChatFormatting color = ChatFormatting.GREEN;
/* 30 */     int durability = getPercentageDurability(itemStack);
/*    */     
/* 32 */     if (durability < 20) {
/* 33 */       color = ChatFormatting.RED;
/* 34 */     } else if (durability < 60) {
/* 35 */       color = ChatFormatting.GOLD;
/*    */     } 
/*    */     
/* 38 */     return color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public static int getDurability(ItemStack itemStack) { return itemStack.func_77958_k() - itemStack.func_77952_i(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\ItemUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */