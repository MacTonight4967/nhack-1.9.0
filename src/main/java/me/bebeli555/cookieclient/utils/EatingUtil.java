/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class EatingUtil
/*    */   extends Mod {
/*    */   private static boolean eating;
/*    */   private static long eatingMs;
/*    */   
/*    */   public static boolean eatItem(Item item, boolean sleepUntilDone) {
/* 16 */     if (!InventoryUtil.hasItem(item)) {
/* 17 */       return false;
/*    */     }
/*    */     
/* 20 */     InventoryUtil.switchItem(InventoryUtil.getSlot(item), false);
/*    */     
/* 22 */     if (mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == item) {
/* 23 */       if (mc.field_71462_r != null) {
/* 24 */         mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, EnumHand.MAIN_HAND);
/*    */       } else {
/* 26 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), true);
/*    */       } 
/* 28 */       eating = true;
/*    */ 
/*    */       
/* 31 */       if (!sleepUntilDone) {
/* 32 */         (new Thread() {
/*    */             public void run() {
/* 34 */               EatingUtil.access$002(System.currentTimeMillis());
/* 35 */               long check = EatingUtil.access$000();
/*    */               
/* 37 */               Mod.sleepUntil(() -> !EatingUtil.access$100(), 5000);
/* 38 */               if (EatingUtil.access$000() == check) {
/* 39 */                 EatingUtil.access$102(false);
/*    */               }
/*    */             }
/* 42 */           }).start();
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 47 */         sleepUntil(() -> !eating, 5000);
/* 48 */         eating = false;
/*    */       } 
/*    */       
/* 51 */       return true;
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public static boolean isEating() { return eating; }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void finishedEating(LivingEntityUseItemEvent.Finish e) {
/* 64 */     if (eating) {
/* 65 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), false);
/* 66 */       eating = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\EatingUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */