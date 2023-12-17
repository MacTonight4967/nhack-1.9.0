/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ public class AutoMessager
/*    */   extends Mod
/*    */ {
/*    */   private static Thread thread;
/* 15 */   public static Setting message = new Setting(Mode.TEXT, "Message", "", new String[] { "The message to send" });
/* 16 */   public static Setting mode = new Setting(null, "Mode", "Normal", new String[][] { { "Normal", "Just sends the message until toggled off" }, { "Everyone", "Sends a message for every player online.", "<player> in the message will be replaced with the players name", "Example: /msg <player> hi" } });
/* 17 */   public static Setting everyoneToggle = new Setting(mode, "Everyone", Mode.BOOLEAN, "Toggle", Boolean.valueOf(false), new String[] { "Toggles the module off after sending the message", "To everyone online if true" });
/* 18 */   public static Setting delay = new Setting(Mode.DOUBLE, "Delay", Double.valueOf(3.5D), new String[] { "How many seconds to wait between sending the messages" });
/* 19 */   public static Setting packet = new Setting(Mode.BOOLEAN, "Packet", Boolean.valueOf(false), new String[] { "Sends a packet instead of using the method to send the message", "If you use this then other clients prefixes wont work usually" });
/*    */ 
/*    */   
/* 22 */   public AutoMessager() { super(Group.MISC, "AutoMessager", new String[] { "Sends messages automatically" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 27 */     thread = new Thread() {
/*    */         public void run() {
/* 29 */           while (AutoMessager.access$000() != null && AutoMessager.access$000().equals(this)) {
/* 30 */             AutoMessager.this.loop();
/*    */             
/* 32 */             Mod.sleep(50);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 37 */     thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 42 */     suspend(thread);
/* 43 */     thread = null;
/*    */   }
/*    */   
/*    */   public void loop() {
/* 47 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */     
/* 51 */     if (mode.stringValue().equals("Normal")) {
/* 52 */       sendMessage(message.stringValue());
/* 53 */       sleep((int)(delay.doubleValue() * 1000.0D));
/* 54 */     } else if (mode.stringValue().equals("Everyone")) {
/* 55 */       ArrayList<String> players = new ArrayList<String>();
/* 56 */       for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/*    */         try {
/* 58 */           players.add(player.func_178845_a().getName());
/* 59 */         } catch (Exception exception) {}
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 64 */       for (String player : players) {
/* 65 */         if (mc.field_71439_g == null || player.equals(mc.field_71439_g.func_70005_c_()) || !isOnline(player)) {
/*    */           continue;
/*    */         }
/*    */         
/* 69 */         sendMessage(message.stringValue().replace("<player>", player));
/* 70 */         sleep((int)(delay.doubleValue() * 1000.0D));
/*    */       } 
/*    */       
/* 73 */       if (everyoneToggle.booleanValue()) {
/* 74 */         disable();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void sendMessage(String message) {
/* 80 */     if (packet.booleanValue()) {
/* 81 */       mc.field_71439_g.field_71174_a.func_147297_a(new CPacketChatMessage(message));
/*    */     } else {
/* 83 */       mc.field_71439_g.func_71165_d(message);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isOnline(String playerName) {
/* 88 */     for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/*    */       try {
/* 90 */         if (player.func_178845_a().getName().equals(playerName)) {
/* 91 */           return true;
/*    */         }
/* 93 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoMessager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */