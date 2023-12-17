/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryUtil
/*     */   extends Mod
/*     */ {
/*  18 */   public static int getHandSlot() { return mc.field_71439_g.field_71071_by.field_70461_c; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlot(Block block) {
/*     */     try {
/*  26 */       for (ItemStackUtil itemStack : getAllItems()) {
/*  27 */         if (Block.func_149634_a(itemStack.itemStack.func_77973_b()).equals(block)) {
/*  28 */           return itemStack.slotId;
/*     */         }
/*     */       } 
/*  31 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/*  35 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlot(Item item) {
/*     */     try {
/*  43 */       for (ItemStackUtil itemStack : getAllItems()) {
/*  44 */         if (itemStack.itemStack.func_77973_b().equals(item)) {
/*  45 */           return itemStack.slotId;
/*     */         }
/*     */       } 
/*  48 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/*  52 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clickSlot(int id) {
/*  59 */     if (id != -1) {
/*     */       try {
/*  61 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, getClickSlot(id), 0, ClickType.PICKUP, mc.field_71439_g);
/*  62 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clickSlot(int id, int otherRows) {
/*  73 */     if (id != -1) {
/*     */       try {
/*  75 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, getClickSlot(id) + otherRows, 0, ClickType.PICKUP, mc.field_71439_g);
/*  76 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getClickSlot(int id) {
/*  86 */     if (id == -1) {
/*  87 */       return id;
/*     */     }
/*     */     
/*  90 */     if (id < 9) {
/*  91 */       return 36;
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (id == 39) {
/*  96 */       id = 5;
/*  97 */     } else if (id == 38) {
/*  98 */       id = 6;
/*  99 */     } else if (id == 37) {
/* 100 */       id = 7;
/* 101 */     } else if (id == 36) {
/* 102 */       id = 8;
/* 103 */     } else if (id == 40) {
/* 104 */       id = 45;
/*     */     } 
/*     */     
/* 107 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void switchItem(int slot, boolean sleep) {
/* 114 */     int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 115 */     if (slot < 9) {
/* 116 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*     */     } else {
/* 118 */       int freeSlot = 8;
/* 119 */       for (int i = 0; i < 9; i++) {
/* 120 */         if (getItemStack(i).func_77973_b() == Items.field_190931_a) {
/* 121 */           freeSlot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 126 */       clickSlot(slot);
/* 127 */       if (sleep) sleep(200); 
/* 128 */       clickSlot(freeSlot);
/* 129 */       if (sleep) sleep(200); 
/* 130 */       clickSlot(slot);
/* 131 */       if (sleep) sleep(200); 
/* 132 */       mc.field_71439_g.field_71071_by.field_70461_c = freeSlot;
/* 133 */       if (sleep) sleep(100);
/*     */     
/*     */     } 
/* 136 */     if (oldSlot != mc.field_71439_g.field_71071_by.field_70461_c) {
/* 137 */       mc.field_71442_b.func_78765_e();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack getItemStack(int id) {
/*     */     try {
/* 146 */       return mc.field_71439_g.field_71071_by.func_70301_a(id);
/* 147 */     } catch (NullPointerException e) {
/* 148 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAmountOfItem(Item item) {
/* 156 */     int count = 0;
/*     */     
/* 158 */     for (ItemStackUtil itemStack : getAllItems()) {
/* 159 */       if (itemStack.itemStack != null && itemStack.itemStack.func_77973_b().equals(item)) {
/* 160 */         count += itemStack.itemStack.func_190916_E();
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAmountOfBlock(Block block) {
/* 171 */     int count = 0;
/*     */     
/* 173 */     for (ItemStackUtil itemStack : getAllItems()) {
/* 174 */       if (Block.func_149634_a(itemStack.itemStack.func_77973_b()).equals(block)) {
/* 175 */         count += itemStack.itemStack.func_190916_E();
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static boolean hasItem(Item item) { return (getAmountOfItem(item) != 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasHotbarItem(Item item) {
/* 193 */     for (int i = 0; i < 9; i++) {
/* 194 */       if (getItemStack(i).func_77973_b() == item) {
/* 195 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlotInHotbar(Item item) {
/* 206 */     for (int i = 0; i < 9; i++) {
/* 207 */       if (getItemStack(i).func_77973_b() == item) {
/* 208 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public static boolean hasBlock(Block block) { return (getSlot(block) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFull() {
/* 226 */     for (ItemStackUtil itemStack : getAllItems()) {
/* 227 */       if (itemStack.itemStack.func_77973_b() == Items.field_190931_a) {
/* 228 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEmptySlots() {
/* 239 */     count = 0;
/* 240 */     for (ItemStackUtil itemStack : getAllItems()) {
/* 241 */       if (itemStack.itemStack.func_77973_b() == Items.field_190931_a) {
/* 242 */         count++;
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEmptySlot() {
/* 253 */     for (ItemStackUtil itemStack : getAllItems()) {
/* 254 */       if (itemStack.itemStack.func_77973_b() == Items.field_190931_a) {
/* 255 */         return itemStack.slotId;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<ItemStackUtil> getAllItems() {
/* 266 */     items = new ArrayList();
/*     */     
/* 268 */     for (int i = 0; i < 36; i++) {
/* 269 */       items.add(new ItemStackUtil(getItemStack(i), i));
/*     */     }
/*     */     
/* 272 */     return items;
/*     */   }
/*     */   
/*     */   public static class ItemStackUtil {
/*     */     public ItemStack itemStack;
/*     */     public int slotId;
/*     */     
/*     */     public ItemStackUtil(ItemStack itemStack, int slotId) {
/* 280 */       this.itemStack = itemStack;
/* 281 */       this.slotId = slotId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */