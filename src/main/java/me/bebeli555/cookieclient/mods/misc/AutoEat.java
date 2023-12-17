/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.BaritoneUtil;
/*    */ import me.bebeli555.cookieclient.utils.EatingUtil;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class AutoEat
/*    */   extends Mod
/*    */ {
/*    */   private static boolean paused;
/* 20 */   public static Setting hungerSetting = new Setting(Mode.INTEGER, "Hunger", Integer.valueOf(15), new String[] { "If hunger goes below or equal to this then it eats" });
/* 21 */   public static Setting healthSetting = new Setting(Mode.INTEGER, "Health", Integer.valueOf(10), new String[] { "If health goes below or equal to this then it eats" });
/* 22 */   public static Setting preferGaps = new Setting(Mode.BOOLEAN, "PreferGaps", Boolean.valueOf(false), new String[] { "Prefers gaps over other food" });
/* 23 */   public static Setting pauseBaritone = new Setting(Mode.BOOLEAN, "PauseBaritone", Boolean.valueOf(true), new String[] { "Pauses baritone while eating" });
/*    */ 
/*    */   
/* 26 */   public AutoEat() { super(Group.MISC, "AutoEat", new String[] { "Automatically eats food" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 31 */     if (paused) {
/* 32 */       BaritoneUtil.sendCommand("resume");
/* 33 */       paused = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 39 */     if (mc.field_71439_g == null || EatingUtil.isEating()) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     if (paused) {
/* 44 */       paused = false;
/* 45 */       BaritoneUtil.sendCommand("resume");
/*    */     } 
/*    */     
/* 48 */     float health = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
/* 49 */     int hunger = mc.field_71439_g.func_71024_bL().func_75116_a();
/*    */     
/* 51 */     if (health <= healthSetting.intValue() || hunger <= hungerSetting.intValue()) {
/* 52 */       Item food = null;
/* 53 */       int highest = -1;
/*    */       
/* 55 */       for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 56 */         if (itemStack.itemStack.func_77973_b() instanceof ItemFood) {
/* 57 */           if (preferGaps.booleanValue() && itemStack.itemStack.func_77973_b() == Items.field_151153_ao) {
/* 58 */             food = itemStack.itemStack.func_77973_b();
/*    */             
/*    */             break;
/*    */           } 
/* 62 */           ItemFood itemFood = (ItemFood)itemStack.itemStack.func_77973_b();
/* 63 */           if (itemFood.field_77853_b > highest) {
/* 64 */             highest = itemFood.field_77853_b;
/* 65 */             food = itemStack.itemStack.func_77973_b();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 70 */       if (food != null) {
/* 71 */         if (hunger == 20 && food != Items.field_151153_ao) {
/*    */           return;
/*    */         }
/*    */         
/* 75 */         if (pauseBaritone.booleanValue()) {
/* 76 */           paused = true;
/* 77 */           BaritoneUtil.sendCommand("pause");
/*    */         } 
/*    */         
/* 80 */         EatingUtil.eatItem(food, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoEat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */