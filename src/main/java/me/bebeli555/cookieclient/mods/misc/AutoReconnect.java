/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ 
/*    */ public class AutoReconnect extends Mod {
/*    */   public static AutoReconnect module;
/* 10 */   public static Setting delay = new Setting(Mode.DOUBLE, "Delay", Integer.valueOf(3), new String[] { "Delay to wait in seconds" });
/*    */   
/*    */   public AutoReconnect() {
/* 13 */     super(Group.MISC, "AutoReconnect", new String[] { "Automatically reconnects to the server" });
/* 14 */     module = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AutoReconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */