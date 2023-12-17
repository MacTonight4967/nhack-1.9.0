/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBow;
/*    */ 
/*    */ public class Offhand extends Mod {
/*    */   public static boolean autoTotem;
/*    */   public static boolean toggleBackOn;
/* 18 */   public static Setting mode = new Setting(null, "Mode", "Gap", new String[][] { { "Gap" }, { "Crystal" }, { "Bow" }, { "XP" } });
/* 19 */   public static Setting toggleHealth = new Setting(Mode.DOUBLE, "ToggleHealth", Integer.valueOf(5), new String[] { "When your health goes below or equal to this amount", "It will toggle this off and enabled autototem", "If you had it on previously" });
/* 20 */   public static Setting toggleBack = new Setting(Mode.BOOLEAN, "ToggleBack", Boolean.valueOf(false), new String[] { "If offhand gets toggled off because low health", "Then it will toggle it back on when", "Health is above the set amount above" });
/*    */   
/*    */   public Offhand() {
/* 23 */     super(Group.COMBAT, "Offhand", new String[] { "Pauses autototem and puts something else to your offhand" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     this.onPlayerUpdate = new Listener(event -> {
/*    */           
/* 45 */           if ((mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj()) <= toggleHealth.doubleValue()) {
/* 46 */             disable();
/* 47 */             toggleBackOn = toggleBack.booleanValue();
/*    */             
/*    */             return;
/*    */           } 
/* 51 */           if (!AutoTotem.isContainerOpen()) {
/* 52 */             Item item = null;
/* 53 */             if (mode.stringValue().equals("Gap")) {
/* 54 */               item = Items.field_151153_ao;
/* 55 */             } else if (mode.stringValue().equals("Crystal")) {
/* 56 */               item = Items.field_185158_cP;
/* 57 */             } else if (mode.stringValue().equals("Bow")) {
/* 58 */               ItemBow itemBow = Items.field_151031_f;
/* 59 */             } else if (mode.stringValue().equals("XP")) {
/* 60 */               item = Items.field_151062_by;
/*    */             } 
/*    */             
/* 63 */             if (mc.field_71439_g.func_184592_cb().func_77973_b() != item) {
/* 64 */               int slot = InventoryUtil.getSlot(item);
/*    */               
/* 66 */               if (slot != -1) {
/* 67 */                 InventoryUtil.clickSlot(slot);
/* 68 */                 InventoryUtil.clickSlot(45);
/* 69 */                 InventoryUtil.clickSlot(slot);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */     instance = this;
/*    */   }
/*    */   
/*    */   public static Offhand instance;
/*    */   @EventHandler
/*    */   private Listener<PlayerUpdateEvent> onPlayerUpdate;
/*    */   
/*    */   public void onEnabled() {
/*    */     autoTotem = AutoTotem.instance.isOn();
/*    */     if (autoTotem)
/*    */       AutoTotem.instance.disable(); 
/*    */   }
/*    */   
/*    */   public void onDisabled() {
/*    */     toggleBackOn = false;
/*    */     if (autoTotem)
/*    */       AutoTotem.instance.enable(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\Offhand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */