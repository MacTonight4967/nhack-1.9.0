/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ 
/*     */ public class Settings
/*     */   extends Mod {
/*  13 */   public static Timer lastSave = new Timer();
/*  14 */   public static String path = mc.field_71412_D.getPath() + "/CookieClient";
/*  15 */   public static File settings = new File(path + "/Settings.txt");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings() {
/*  21 */     if (!lastSave.hasPassed(1000)) {
/*     */       return;
/*     */     }
/*  24 */     lastSave.reset();
/*     */     
/*  26 */     (new Thread() {
/*     */         public void run() {
/*     */           try {
/*  29 */             Settings.settings.delete();
/*  30 */             Settings.settings.createNewFile();
/*     */             
/*  32 */             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Settings.settings)));
/*  33 */             for (GuiNode node : GuiNode.all) {
/*  34 */               bw.write(node.id + "=");
/*  35 */               if (!node.isTypeable && node.modes.size() == 0) {
/*  36 */                 bw.write("" + node.toggled);
/*     */               } else {
/*  38 */                 bw.write("" + node.stringValue);
/*     */               } 
/*     */               
/*  41 */               bw.newLine();
/*     */             } 
/*     */ 
/*     */             
/*  45 */             for (Group group : Group.values()) {
/*  46 */               bw.write("Group88" + group.name + "=" + group.x + "," + group.y);
/*  47 */               bw.newLine();
/*     */             } 
/*     */             
/*  50 */             bw.close();
/*  51 */           } catch (Exception e) {
/*  52 */             System.out.println("CookieClient - Error saving settings");
/*  53 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*  56 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadSettings() {
/*     */     try {
/*  64 */       if (!settings.exists()) {
/*     */         return;
/*     */       }
/*     */       
/*  68 */       scanner = new Scanner(settings);
/*  69 */       while (scanner.hasNextLine()) {
/*  70 */         String value, split[] = scanner.nextLine().split("=");
/*  71 */         String id = split[0];
/*     */         
/*     */         try {
/*  74 */           value = split[1];
/*  75 */         } catch (IndexOutOfBoundsException e) {
/*  76 */           value = "";
/*     */         } 
/*     */ 
/*     */         
/*  80 */         if (id.startsWith("Group88")) {
/*  81 */           String name = id.replace("Group88", "");
/*  82 */           int x = Integer.parseInt(value.split(",")[0]);
/*  83 */           int y = Integer.parseInt(value.split(",")[1]);
/*     */           
/*  85 */           for (Group group : Group.values()) {
/*  86 */             if (group.name.equals(name)) {
/*  87 */               group.x = x;
/*  88 */               group.y = y;
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*  95 */         GuiNode node = getGuiNodeFromId(id);
/*  96 */         if (node == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 100 */         if (isBoolean(value)) {
/* 101 */           node.toggled = Boolean.parseBoolean(value);
/*     */           try {
/* 103 */             Setting.getSettingWithId(node.id).setValue(Boolean.valueOf(node.toggled));
/* 104 */           } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */           
/* 108 */           for (Mod module : modules) {
/* 109 */             if (module.name.equals(id) && 
/* 110 */               node.toggled) {
/* 111 */               module.enable();
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         } 
/* 116 */         node.stringValue = value;
/*     */         try {
/* 118 */           node.setSetting();
/* 119 */         } catch (NullPointerException nullPointerException) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 124 */       scanner.close();
/* 125 */     } catch (Exception e) {
/* 126 */       System.out.println("CookieClient - Error loading settings");
/* 127 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public static boolean isOn(String id) { return (getGuiNodeFromId(id)).toggled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static String getStringValue(String id) { return (getGuiNodeFromId(id)).stringValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static int getIntValue(String id) { return Integer.parseInt((getGuiNodeFromId(id)).stringValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static double getDoubleValue(String id) { return Double.parseDouble((getGuiNodeFromId(id)).stringValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GuiNode getGuiNodeFromId(String id) {
/* 163 */     for (GuiNode node : GuiNode.all) {
/* 164 */       if (node.id.equals(id)) {
/* 165 */         return node;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public static boolean isBoolean(String string) { return ("true".equals(string) || "false".equals(string)); }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\Settings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */