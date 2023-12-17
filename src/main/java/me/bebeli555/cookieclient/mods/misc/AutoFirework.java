/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class AutoFirework extends Mod {
/*    */   private static Thread thread;
/*    */   private static Thread thread2;
/*    */   private static boolean lagback;
/*    */   private static int lagbackCounter;
/* 17 */   public static Setting delay = new Setting(Mode.DOUBLE, "Delay", Double.valueOf(2.8D), new String[] { "The delay between clicks on the firework", "In seconds" });
/* 18 */   public static Setting antiLagback = new Setting(Mode.BOOLEAN, "AntiLagback", Boolean.valueOf(true), new String[] { "Doesnt click on a firework if ur lagbacking on 2b2t" });
/*    */ 
/*    */   
/* 21 */   public AutoFirework() { super(Group.MISC, "AutoFirework", new String[] { "Clicks on fireworks for you when flying with elytra" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 26 */     thread = new Thread() {
/*    */         public void run() {
/* 28 */           while (AutoFirework.access$000() != null && AutoFirework.access$000().equals(this)) {
/* 29 */             AutoFirework.this.loop();
/*    */             
/* 31 */             Mod.sleep(50);
/*    */           } 
/*    */         }
/*    */       };
/* 35 */     thread.start();
/*    */     
/* 37 */     thread2 = new Thread() {
/*    */         public void run() {
/* 39 */           while (AutoFirework.access$100() != null && AutoFirework.access$100().equals(this)) {
/* 40 */             if (Mod.mc.field_71439_g != null) {
/* 41 */               double speed = PlayerUtil.getSpeed(Mod.mc.field_71439_g);
/* 42 */               if (speed > 4.0D) {
/* 43 */                 AutoFirework.access$202(true);
/*    */               }
/*    */               
/* 46 */               if (AutoFirework.access$200()) {
/* 47 */                 if (speed < 1.0D) {
/* 48 */                   AutoFirework.access$308();
/* 49 */                   if (AutoFirework.access$300() > 4) {
/* 50 */                     AutoFirework.access$202(false);
/* 51 */                     AutoFirework.access$302(0);
/*    */                   } 
/*    */                 } else {
/* 54 */                   AutoFirework.access$302(0);
/*    */                 } 
/*    */               }
/*    */             } 
/*    */             
/* 59 */             Mod.sleep(50);
/*    */           } 
/*    */         }
/*    */       };
/* 63 */     thread2.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 68 */     thread = null;
/* 69 */     thread2 = null;
/*    */   }
/*    */   
/*    */   public void loop() {
/* 73 */     if (mc.field_71439_g == null || !mc.field_71439_g.func_184613_cA()) {
/*    */       return;
/*    */     }
/*    */     
/* 77 */     if (!InventoryUtil.hasItem(Items.field_151152_bP)) {
/* 78 */       sendMessage("You have no fireworks in inventory", true);
/* 79 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 84 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151152_bP) {
/* 85 */       InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151152_bP), false);
/*    */     }
/*    */ 
/*    */     
/* 89 */     if (!lagback) {
/* 90 */       mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, EnumHand.MAIN_HAND);
/* 91 */       sleepUntil(() -> !mc.field_71439_g.func_184613_cA(), (int)(delay.doubleValue() * 1000.0D));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */