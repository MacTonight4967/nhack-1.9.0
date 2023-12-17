/*    */ package me.bebeli555.cookieclient.hud;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.util.Scanner;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Settings;
/*    */ 
/*    */ public class HudSettings
/*    */   extends Mod
/*    */ {
/* 14 */   public static String path = Settings.path + "/Hud.txt";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void saveSettings() {
/*    */     try {
/* 20 */       file = new File(path);
/* 21 */       file.delete();
/* 22 */       file.createNewFile();
/*    */       
/* 24 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/* 25 */       for (HudComponent component : HudComponent.components) {
/* 26 */         bw.write(component.name + "," + component.corner.id + "," + component.xAdd + "," + component.yAdd);
/* 27 */         bw.newLine();
/*    */       } 
/*    */       
/* 30 */       bw.close();
/* 31 */     } catch (Exception e) {
/* 32 */       System.out.println("CookieClient - Error saving HUD settings");
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void loadSettings() {
/*    */     try {
/* 40 */       file = new File(path);
/* 41 */       if (file.exists()) {
/* 42 */         Scanner s = new Scanner(file);
/* 43 */         while (s.hasNextLine()) {
/* 44 */           String line = s.nextLine();
/*    */           
/* 46 */           if (!line.isEmpty()) {
/* 47 */             String[] split = line.split(",");
/* 48 */             for (HudComponent component : HudComponent.components) {
/* 49 */               if (component.name.equals(split[0])) {
/* 50 */                 component.corner = HudComponent.HudCorner.getCornerFromId(Integer.parseInt(split[1]));
/* 51 */                 component.xAdd = Integer.parseInt(split[2]);
/* 52 */                 component.yAdd = Integer.parseInt(split[3]);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/*    */ 
/*    */         
/* 59 */         s.close();
/*    */       } 
/* 61 */     } catch (Exception e) {
/* 62 */       System.out.println("CookieClient - Error loading HUD settings");
/* 63 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\HudSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */