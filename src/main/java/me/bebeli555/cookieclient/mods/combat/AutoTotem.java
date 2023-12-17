/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerMoveEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.hud.components.ArrayListComponent;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ public class AutoTotem
/*     */   extends Mod
/*     */ {
/*     */   public static AutoTotem instance;
/*     */   private int lastNumber;
/*  23 */   public static Setting stopMovement = new Setting(Mode.BOOLEAN, "StopMovement", Boolean.valueOf(false), new String[] { "Stops ur motion when its trying to replace the totem", "Because some servers like 2b2t will not", "Allow u to click the totem if ur moving" });
/*     */   
/*     */   public AutoTotem() {
/*  26 */     super(Group.COMBAT, "AutoTotem", new String[] { "Keeps a totem in ur offhand" });
/*  27 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  32 */     this.lastNumber = -1;
/*     */ 
/*     */     
/*  35 */     if (Offhand.instance.isOn()) {
/*  36 */       Offhand.instance.disable();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*  42 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/*  46 */     if (Offhand.toggleBackOn && (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj()) > Offhand.toggleHealth.doubleValue()) {
/*  47 */       Offhand.instance.enable();
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY && InventoryUtil.hasItem(Items.field_190929_cY) && !isContainerOpen()) {
/*  52 */       if (stopMovement.booleanValue()) {
/*  53 */         NoMovement.toggle(true);
/*     */       }
/*     */       
/*  56 */       Item oldItem = mc.field_71439_g.func_184592_cb().func_77973_b();
/*  57 */       int slot = InventoryUtil.getSlot(Items.field_190929_cY);
/*  58 */       InventoryUtil.clickSlot(slot);
/*  59 */       InventoryUtil.clickSlot(45);
/*  60 */       if (oldItem != Items.field_190931_a) {
/*  61 */         InventoryUtil.clickSlot(slot);
/*     */       }
/*     */     } else {
/*  64 */       NoMovement.toggle(false);
/*     */     } 
/*     */     
/*  67 */     setRenderNumber(InventoryUtil.getAmountOfItem(Items.field_190929_cY));
/*  68 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
/*  69 */       setRenderNumber(getRenderNumber() + 1);
/*     */     }
/*     */     
/*  72 */     if (getRenderNumber() != this.lastNumber)
/*     */     {
/*  74 */       ArrayListComponent.lastArraylistSize = -1;
/*     */     }
/*     */     
/*  77 */     this.lastNumber = getRenderNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isContainerOpen() {
/*  85 */     if (mc.field_71462_r != null) {
/*  86 */       return (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiShulkerBox);
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NoMovement
/*     */   {
/* 108 */     public NoMovement() { this.moveEvent = new Listener(event -> 
/*     */           
/* 110 */           event.cancel(), new java.util.function.Predicate[0]); }
/*     */     
/*     */     private static NoMovement noMovement = new NoMovement();
/*     */     @EventHandler
/*     */     private Listener<PlayerMoveEvent> moveEvent;
/*     */     
/*     */     public static void toggle(boolean on) {
/*     */       if (on) {
/*     */         Mod.EVENT_BUS.subscribe(noMovement);
/*     */       } else {
/*     */         Mod.EVENT_BUS.unsubscribe(noMovement);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\AutoTotem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */