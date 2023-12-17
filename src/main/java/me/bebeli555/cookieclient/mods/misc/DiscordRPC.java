/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ import club.minnced.discord.rpc.DiscordEventHandlers;
/*    */ import club.minnced.discord.rpc.DiscordRPC;
/*    */ import club.minnced.discord.rpc.DiscordRichPresence;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ 
/*    */ public class DiscordRPC extends Mod {
/* 12 */   public static DiscordRichPresence presence = new DiscordRichPresence();
/* 13 */   public static DiscordRPC rpc = DiscordRPC.INSTANCE;
/*    */   public static Thread thread;
/* 15 */   public static int index = 1;
/*    */   
/* 17 */   public static Setting topText = new Setting(Mode.TEXT, "TopText", "Username: <username>", new String[] { "The top text. <username> will be turned to ur ingame username" });
/* 18 */   public static Setting bottomText = new Setting(Mode.TEXT, "BottomText", "Playing on <server>", new String[] { "The bottom text. <server> will be turned to the server ip if it has letters in it" });
/*    */   
/*    */   public DiscordRPC() {
/* 21 */     super(Group.MISC, "DiscordRPC", new String[] { "Discord rich presence" });
/* 22 */     this.defaultOn = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 27 */     DiscordEventHandlers handlers = new DiscordEventHandlers();
/* 28 */     rpc.Discord_Initialize("848051093295726622", handlers, true, "");
/* 29 */     presence.startTimestamp = System.currentTimeMillis() / 1000L;
/* 30 */     presence.details = topText.stringValue().replace("<username>", getUsername()) + " | v" + "1.01";
/* 31 */     presence.state = bottomText.stringValue().replace("<server>", getServer());
/* 32 */     presence.largeImageKey = "nhack";
/* 33 */     presence.largeImageText = "winner pov";
/* 34 */     rpc.Discord_UpdatePresence(presence);
/*    */     
/* 36 */     thread = new Thread(() -> {
/* 37 */           while (thread != null) {
/* 38 */             rpc.Discord_RunCallbacks();
/* 39 */             presence.details = topText.stringValue().replace("<username>", getUsername()) + " | v" + "1.01";
/* 40 */             presence.state = bottomText.stringValue().replace("<server>", getServer());
/* 41 */             rpc.Discord_UpdatePresence(presence);
/* 42 */             Mod.sleep(2000);
/*    */           } 
/*    */         }"RPC-Callback-Handler");
/*    */     
/* 46 */     thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 51 */     suspend(thread);
/* 52 */     thread = null;
/* 53 */     rpc.Discord_Shutdown();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getServer() {
/* 59 */     if (mc.field_71439_g == null) {
/* 60 */       return "Main menu";
/*    */     }
/*    */     
/* 63 */     ip = PlayerUtil.getServerIp();
/* 64 */     boolean hasLetters = false;
/*    */     
/* 66 */     for (char c : ip.toCharArray()) {
/* 67 */       if (Character.isLetter(c)) {
/* 68 */         hasLetters = true;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 73 */     if (!hasLetters) {
/* 74 */       return "Unknown";
/*    */     }
/* 76 */     return ip;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public static String getUsername() { return mc.field_71449_j.func_111285_a(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\DiscordRPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */