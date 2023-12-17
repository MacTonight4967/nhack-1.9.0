/*     */ package me.bebeli555.cookieclient.mods.misc;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.GuiNode;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class AutoInventoryManager
/*     */   extends Mod
/*     */ {
/*     */   public static Thread thread;
/*  25 */   public static ArrayList<ItemUtil> layout = new ArrayList();
/*     */   
/*  27 */   public static Setting saveLayout = new Setting(Mode.BOOLEAN, "SaveLayout", Boolean.valueOf(false), new String[] { "Saves the current inventory layout" });
/*  28 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(150), new String[] { "Delay in ms between the slot clicks" });
/*     */ 
/*     */   
/*  31 */   public AutoInventoryManager() { super(Group.MISC, "AutoInventoryManager", new String[] { "Keeps items in ur inventry in the same layout", "As the one you have saved" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostInit() {
/*  36 */     final GuiNode node = Settings.getGuiNodeFromId(saveLayout.id);
/*     */     
/*  38 */     node.addClickListener(new GuiNode.ClickListener() {
/*     */           public void clicked() {
/*  40 */             this.val$node.toggled = false;
/*  41 */             node.setSetting();
/*     */             
/*  43 */             AutoInventoryManager.layout.clear();
/*  44 */             for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/*  45 */               AutoInventoryManager.layout.add(new AutoInventoryManager.ItemUtil(itemStack.itemStack.func_77973_b(), itemStack.slotId));
/*     */             }
/*     */             
/*  48 */             AutoInventoryManager.this.saveFile();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  55 */     if (layout.isEmpty()) {
/*  56 */       readFile();
/*     */       
/*  58 */       if (layout.isEmpty()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     thread = new Thread() {
/*     */         public void run() {
/*  65 */           while (AutoInventoryManager.thread != null && AutoInventoryManager.thread.equals(this)) {
/*  66 */             AutoInventoryManager.this.loop();
/*     */             
/*  68 */             Mod.sleep(250);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  73 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  78 */     suspend(thread);
/*  79 */     thread = null;
/*     */   }
/*     */   
/*     */   public void loop() {
/*  83 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/*  87 */     for (ItemUtil itemUtil : layout) {
/*  88 */       ItemStack current = InventoryUtil.getItemStack(itemUtil.slotId);
/*     */       
/*  90 */       if (itemUtil.item != current.func_77973_b()) {
/*  91 */         label33: for (InventoryUtil.ItemStackUtil itemStack2 : InventoryUtil.getAllItems()) {
/*  92 */           if (itemStack2.itemStack.func_77973_b() == itemUtil.item && 
/*  93 */             itemStack2.slotId != itemUtil.slotId) {
/*  94 */             for (ItemUtil itemUtil2 : layout) {
/*  95 */               if (itemUtil2.item == itemStack2.itemStack.func_77973_b() && 
/*  96 */                 itemUtil2.slotId == itemStack2.slotId) {
/*     */                 continue label33;
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 102 */             int otherSlots = mc.field_71439_g.field_71070_bA.field_75151_b.size() - 46;
/* 103 */             if (otherSlots != 0) {
/* 104 */               otherSlots++;
/*     */             }
/*     */ 
/*     */             
/* 108 */             if (mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() != Items.field_190931_a) {
/* 109 */               int freeSlot = InventoryUtil.getEmptySlot();
/*     */               
/* 111 */               if (freeSlot != -1) {
/* 112 */                 InventoryUtil.clickSlot(freeSlot, otherSlots);
/*     */ 
/*     */ 
/*     */                 
/* 116 */                 InventoryUtil.clickSlot(itemStack2.slotId, otherSlots);
/* 117 */                 InventoryUtil.clickSlot(itemUtil.slotId, otherSlots);
/* 118 */                 InventoryUtil.clickSlot(itemStack2.slotId, otherSlots);
/* 119 */                 sleep(delay.intValue());
/*     */                 continue;
/*     */               } 
/*     */               break label33;
/*     */             } 
/*     */             break label33;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveFile() {
/*     */     try {
/* 133 */       File file = new File(Settings.path + "/AutoInventoryManager.txt");
/* 134 */       file.delete();
/* 135 */       file.createNewFile();
/*     */       
/* 137 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/* 138 */       for (ItemUtil itemUtil : layout) {
/* 139 */         bw.write(itemUtil.slotId + "," + Item.func_150891_b(itemUtil.item));
/* 140 */         bw.newLine();
/*     */       } 
/*     */       
/* 143 */       bw.close();
/* 144 */       sendMessage("Layout saved successfully", false);
/* 145 */     } catch (Exception e) {
/* 146 */       e.printStackTrace();
/* 147 */       sendMessage("Error saving layout. More info in ur games log", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFile() {
/*     */     try {
/* 154 */       File file = new File(Settings.path + "/AutoInventoryManager.txt");
/* 155 */       if (!file.exists()) {
/* 156 */         sendMessage("Save a layout first", true);
/* 157 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/* 161 */       Scanner scanner = new Scanner(file);
/* 162 */       layout.clear();
/*     */       
/* 164 */       while (scanner.hasNextLine()) {
/* 165 */         String line = scanner.nextLine();
/*     */         
/* 167 */         if (!line.isEmpty()) {
/* 168 */           String[] split = line.split(",");
/* 169 */           layout.add(new ItemUtil(Item.func_150899_d(Integer.parseInt(split[1])), Integer.parseInt(split[0])));
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       scanner.close();
/* 174 */     } catch (Exception e) {
/* 175 */       sendMessage("Error reading layout file. More info in ur games log", true);
/* 176 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class ItemUtil {
/*     */     public Item item;
/*     */     public int slotId;
/*     */     
/*     */     public ItemUtil(Item item, int slotId) {
/* 185 */       this.item = item;
/* 186 */       this.slotId = slotId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoInventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */