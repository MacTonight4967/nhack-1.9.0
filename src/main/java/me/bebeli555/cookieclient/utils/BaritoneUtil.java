/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.Settings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.Consumer;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class BaritoneUtil
/*     */   extends Mod
/*     */ {
/*     */   private static Consumer<ITextComponent> oldValue;
/*     */   private static long lastCommand;
/*     */   
/*     */   public static void sendCommand(String command) {
/*  21 */     final long ms = System.currentTimeMillis();
/*  22 */     lastCommand = ms;
/*     */     
/*  24 */     if (oldValue == null) oldValue = (Consumer)(BaritoneAPI.getSettings()).logger.value; 
/*  25 */     (BaritoneAPI.getSettings()).logger.value = (component -> { 
/*  26 */       }); BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(command);
/*     */     
/*  28 */     (new Thread() {
/*     */         public void run() {
/*  30 */           Mod.sleep(250);
/*  31 */           if (ms == BaritoneUtil.access$000()) (BaritoneAPI.getSettings()).logger.value = BaritoneUtil.access$100(); 
/*     */         }
/*  33 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void walkTo(BlockPos goal, boolean sleepUntilDone) {
/*  42 */     if (getBlock(getPlayerPos()) == Blocks.field_150321_G && 
/*  43 */       InventoryUtil.hasItem(Items.field_151048_u)) {
/*  44 */       InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151048_u), true);
/*  45 */       MiningUtil.mineWithoutSwitch(getPlayerPos());
/*     */     } 
/*     */ 
/*     */     
/*  49 */     sendCommand("goto " + goal.func_177958_n() + " " + goal.func_177956_o() + " " + goal.func_177952_p());
/*     */     
/*  51 */     if (sleepUntilDone) {
/*  52 */       sleepUntil(() -> BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing(), 100);
/*  53 */       sleepUntil(() -> !BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing(), -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPath(BlockPos goal) {
/*  62 */     walkTo(goal, false);
/*  63 */     boolean value = false;
/*  64 */     for (int i = 0; i < 35; i++) {
/*  65 */       if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
/*  66 */         value = true;
/*     */         
/*     */         break;
/*     */       } 
/*  70 */       sleep(1);
/*     */     } 
/*     */     
/*  73 */     BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().forceCancel();
/*  74 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static void forceCancel() { BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().forceCancel(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static boolean isPathing() { return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static boolean isBuilding() { return BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().isActive(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static void setSetting(String name, boolean value) { sendCommand("setting " + name + " " + value); }
/*     */   
/*     */   public static class BaritoneSettings
/*     */   {
/* 106 */     public ArrayList<String> names = new ArrayList();
/* 107 */     public ArrayList<Boolean> values = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void saveCurrentSettings() {
/* 114 */       ArrayList<Settings.Setting<Boolean>> settings = new ArrayList<Settings.Setting<Boolean>>();
/*     */       
/* 116 */       settings.add((BaritoneAPI.getSettings()).allowInventory);
/* 117 */       settings.add((BaritoneAPI.getSettings()).allowSprint);
/* 118 */       settings.add((BaritoneAPI.getSettings()).allowBreak);
/* 119 */       settings.add((BaritoneAPI.getSettings()).allowSprint);
/* 120 */       settings.add((BaritoneAPI.getSettings()).allowPlace);
/*     */       
/* 122 */       for (Settings.Setting<Boolean> setting : settings) {
/* 123 */         this.names.add(setting.getName());
/* 124 */         this.values.add(setting.value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void loadSettings() {
/* 132 */       for (int i = 0; i < this.names.size(); i++)
/* 133 */         BaritoneUtil.setSetting((String)this.names.get(i), ((Boolean)this.values.get(i)).booleanValue()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\BaritoneUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */