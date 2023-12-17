/*    */ package me.bebeli555.cookieclient.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Commands;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class Keybind
/*    */   extends Mod
/*    */ {
/* 14 */   public static ArrayList<KeybindValue> keybinds = new ArrayList();
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onKeyPress(InputEvent.KeyInputEvent e) {
/* 18 */     if (!Keyboard.isKeyDown(Keyboard.getEventKey())) {
/* 19 */       String keyName = Keyboard.getKeyName(Keyboard.getEventKey());
/* 20 */       for (KeybindValue keybind : keybinds) {
/* 21 */         if (keybind.name.equals(keyName)) {
/* 22 */           keybind.clicked = false;
/*    */         }
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 29 */     String keyName = Keyboard.getKeyName(Keyboard.getEventKey());
/* 30 */     for (KeybindValue keybind : keybinds) {
/* 31 */       if (!keybind.name.equals(keyName) || 
/* 32 */         keybind.clicked) {
/*    */         continue;
/*    */       }
/*    */       
/* 36 */       if (keybind.id.equals("Keybind")) {
/* 37 */         Commands.openGui = true;
/* 38 */         MinecraftForge.EVENT_BUS.register(Gui.gui); continue;
/*    */       } 
/* 40 */       GuiNode node = Settings.getGuiNodeFromId(keybind.id.replace("Keybind", ""));
/* 41 */       node.click();
/* 42 */       keybind.clicked = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setKeybinds() {
/* 50 */     keybinds.clear();
/*    */     
/* 52 */     for (GuiNode node : GuiNode.all) {
/* 53 */       if (node.isKeybind && 
/* 54 */         !node.stringValue.isEmpty()) {
/* 55 */         keybinds.add(new KeybindValue(node.stringValue, node.id));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class KeybindValue
/*    */   {
/*    */     public String name;
/*    */     public String id;
/*    */     public boolean clicked;
/*    */     
/*    */     public KeybindValue(String name, String id) {
/* 67 */       this.name = name;
/* 68 */       this.id = id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\Keybind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */