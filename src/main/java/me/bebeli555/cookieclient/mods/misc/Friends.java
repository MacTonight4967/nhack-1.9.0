/*     */ package me.bebeli555.cookieclient.mods.misc;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Friends
/*     */   extends Mod
/*     */ {
/*     */   public static boolean toggled;
/*  25 */   public static ArrayList<String> friends = new ArrayList();
/*  26 */   public static File file = new File(Settings.path + "/Friends.txt");
/*     */   
/*  28 */   public static Setting message = new Setting(Mode.BOOLEAN, "Message", Boolean.valueOf(false), new String[] { "/msg's the player that he has", "Been added/removed on the client" });
/*     */   
/*     */   public Friends() {
/*  31 */     super(Group.MISC, "Friends", new String[] { "Makes the friend system work", "Disabling this will disable all friend features" });
/*  32 */     this.defaultOn = true;
/*  33 */     this.defaultHidden = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnabled() {
/*  38 */     toggled = true;
/*     */     
/*  40 */     for (EntityPlayer player : PlayerUtil.getAll()) {
/*  41 */       player.refreshDisplayName();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  47 */     toggled = false;
/*     */     
/*  49 */     for (EntityPlayer player : PlayerUtil.getAll()) {
/*  50 */       player.refreshDisplayName();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderName(PlayerEvent.NameFormat event) {
/*  56 */     if (event.getEntityPlayer() != mc.field_71439_g && isFriend(event.getEntityPlayer())) {
/*  57 */       event.setDisplayname(ChatFormatting.AQUA + event.getDisplayname());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadFriends() {
/*     */     try {
/*  64 */       if (!file.exists()) {
/*  65 */         file.createNewFile();
/*     */         
/*     */         return;
/*     */       } 
/*  69 */       s = new Scanner(file);
/*  70 */       while (s.hasNextLine()) {
/*  71 */         String line = s.nextLine();
/*     */         
/*  73 */         if (!line.isEmpty()) {
/*  74 */           friends.add(line);
/*     */         }
/*     */       } 
/*     */       
/*  78 */       s.close();
/*  79 */     } catch (Exception e) {
/*  80 */       System.out.println("CookieClient - Error loading friends");
/*  81 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveFriends() {
/*     */     try {
/*  88 */       file.delete();
/*  89 */       file.createNewFile();
/*     */       
/*  91 */       bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
/*  92 */       for (String name : friends) {
/*  93 */         bw.write(name);
/*  94 */         bw.newLine();
/*     */       } 
/*     */       
/*  97 */       bw.close();
/*  98 */     } catch (Exception e) {
/*  99 */       System.out.println("CookieClient - Error saving friends");
/* 100 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addFriend(String name) {
/* 106 */     if (message.booleanValue()) {
/* 107 */       mc.field_71439_g.func_71165_d("/msg " + name + " You have been added to friends in " + "CookieClient");
/*     */     }
/*     */     
/* 110 */     friends.add(name);
/* 111 */     saveFriends();
/*     */     
/* 113 */     for (EntityPlayer player : PlayerUtil.getAll()) {
/* 114 */       player.refreshDisplayName();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeFriend(String name) {
/* 120 */     if (message.booleanValue()) {
/* 121 */       mc.field_71439_g.func_71165_d("/msg " + name + " You have been removed from friends in " + "CookieClient");
/*     */     }
/*     */     
/* 124 */     friends.remove(name);
/* 125 */     saveFriends();
/*     */     
/* 127 */     for (EntityPlayer player : PlayerUtil.getAll()) {
/* 128 */       player.refreshDisplayName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isFriend(String name) {
/* 133 */     if (!toggled) return false; 
/* 134 */     return friends.contains(name);
/*     */   }
/*     */   
/*     */   public static boolean isFriend(Entity entity) {
/* 138 */     if (!toggled) return false; 
/* 139 */     return friends.contains(entity.func_70005_c_());
/*     */   }
/*     */   
/*     */   public static class OldName { public String player;
/*     */     public String name;
/*     */     
/*     */     public OldName(String player, String name) {
/* 146 */       this.player = player;
/* 147 */       this.name = name;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\Friends.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */