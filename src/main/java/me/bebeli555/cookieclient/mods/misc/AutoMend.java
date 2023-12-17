/*     */ package me.bebeli555.cookieclient.mods.misc;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoMend
/*     */   extends Mod
/*     */ {
/*     */   public static Thread thread;
/*  22 */   public static Setting armor = new Setting(Mode.BOOLEAN, "Armor", Boolean.valueOf(true), new String[] { "Mends your armor" });
/*  23 */   public static Setting tools = new Setting(Mode.BOOLEAN, "Tools", Boolean.valueOf(false), new String[] { "Mends your tools like pickaxe and others", "Remember to disable your AutoTotem if you want to use this" });
/*  24 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(100), new String[] { "Delay in ms to wait between the clicks on the XP-Bottle" });
/*     */ 
/*     */   
/*  27 */   public AutoMend() { super(Group.MISC, "AutoMend", new String[] { "Mends your armor and tools using xp bottles" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  32 */     thread = new Thread() {
/*     */         public void run() {
/*  34 */           while (AutoMend.thread != null && AutoMend.thread.equals(this)) {
/*  35 */             AutoMend.this.loop();
/*     */             
/*  37 */             Mod.sleep(50);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  42 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  47 */     clearStatus();
/*  48 */     suspend(thread);
/*  49 */     thread = null;
/*     */   }
/*     */   
/*     */   public void loop() {
/*  53 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/*  57 */     if (!InventoryUtil.hasItem(Items.field_151062_by)) {
/*  58 */       sendMessage("No xp bottles in inventory", true);
/*  59 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     if (!isSolid(getPlayerPos().func_177982_a(0, -1, 0))) {
/*  64 */       sendMessage("Block below the player is not solid", true);
/*  65 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  70 */     RotationUtil.rotate((new Vec3d(getPlayerPos().func_177982_a(0, -1, 0))).func_72441_c(0.5D, 0.5D, 0.5D), false);
/*     */     
/*  72 */     boolean allArmorMended = true;
/*  73 */     if (AutoMend.armor.booleanValue()) {
/*     */       
/*  75 */       InventoryUtil.ItemStackUtil[] arrayOfItemStackUtil = { new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(39), 39), new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(38), 38), new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(37), 37), new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(36), 36) };
/*  76 */       boolean mended = false;
/*     */ 
/*     */       
/*  79 */       for (InventoryUtil.ItemStackUtil stack : arrayOfItemStackUtil) {
/*  80 */         if (stack.itemStack.func_77973_b() != Items.field_190931_a) {
/*  81 */           if (!isMaxDurability(stack.itemStack)) {
/*  82 */             mended = true;
/*  83 */             setStatus("Mending armor");
/*     */             
/*  85 */             for (int i = 0; i < 5; i++) {
/*  86 */               clickOnXp();
/*     */             }
/*     */           } else {
/*  89 */             int freeSlot = InventoryUtil.getEmptySlot();
/*  90 */             if (freeSlot == -1) {
/*  91 */               InventoryUtil.switchItem(8, false);
/*  92 */               mc.field_71439_g.func_71040_bB(true);
/*  93 */               freeSlot = 8;
/*     */             } 
/*     */             
/*  96 */             InventoryUtil.clickSlot(stack.slotId);
/*  97 */             sleep(100);
/*  98 */             InventoryUtil.clickSlot(freeSlot);
/*  99 */             sleep(100);
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 105 */       if (!mended) {
/* 106 */         for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 107 */           if (itemStack.itemStack.func_77973_b() instanceof net.minecraft.item.ItemArmor && !isMaxDurability(itemStack.itemStack)) {
/* 108 */             allArmorMended = false;
/*     */             
/* 110 */             int slot = 39;
/* 111 */             EntityEquipmentSlot type = EntityLiving.func_184640_d(itemStack.itemStack);
/* 112 */             if (type.equals(EntityEquipmentSlot.CHEST)) {
/* 113 */               slot = 38;
/* 114 */             } else if (type.equals(EntityEquipmentSlot.LEGS)) {
/* 115 */               slot = 37;
/* 116 */             } else if (type.equals(EntityEquipmentSlot.FEET)) {
/* 117 */               slot = 36;
/*     */             } 
/*     */             
/* 120 */             InventoryUtil.clickSlot(itemStack.slotId);
/* 121 */             sleep(100);
/* 122 */             InventoryUtil.clickSlot(slot);
/* 123 */             sleep(100);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 127 */         allArmorMended = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if (tools.booleanValue() && allArmorMended) {
/* 133 */       ItemStack offHand = InventoryUtil.getItemStack(40);
/*     */       
/* 135 */       if (!isMaxDurability(offHand)) {
/* 136 */         setStatus("Mending tools");
/* 137 */         for (int i = 0; i < 5; i++) {
/* 138 */           clickOnXp();
/*     */         }
/*     */       } else {
/* 141 */         for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 142 */           if (itemStack.itemStack.func_77973_b() instanceof net.minecraft.item.ItemTool && !isMaxDurability(itemStack.itemStack)) {
/* 143 */             InventoryUtil.clickSlot(itemStack.slotId);
/* 144 */             sleep(100);
/* 145 */             InventoryUtil.clickSlot(40);
/* 146 */             sleep(100);
/* 147 */             InventoryUtil.clickSlot(itemStack.slotId);
/* 148 */             sleep(100);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 153 */         disable();
/*     */       } 
/* 155 */     } else if (allArmorMended) {
/* 156 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clickOnXp() {
/* 161 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151062_by) {
/* 162 */       InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151062_by), true);
/*     */     }
/*     */     
/* 165 */     PlayerUtil.rightClick();
/* 166 */     sleep(delay.intValue());
/*     */   }
/*     */ 
/*     */   
/* 170 */   public static int getDurability(ItemStack itemStack) { return itemStack.func_77958_k() - itemStack.func_77952_i(); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public static boolean isMaxDurability(ItemStack itemStack) { return (itemStack.func_77952_i() == 0); }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoMend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */