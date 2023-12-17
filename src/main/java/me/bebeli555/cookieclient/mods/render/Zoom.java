/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Keybind;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Zoom
/*    */   extends Mod
/*    */ {
/*    */   private float oldFov;
/* 18 */   public static Setting fov = new Setting(Mode.INTEGER, "FOV", Integer.valueOf(30), new String[] { "What to change the FOV to when toggled" });
/*    */ 
/*    */   
/* 21 */   public Zoom() { super(Group.RENDER, "Zoom", new String[] { "Zoom like optifine" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 26 */     this.oldFov = mc.field_71474_y.field_74334_X;
/* 27 */     update();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 32 */     mc.field_71474_y.field_74334_X = this.oldFov;
/* 33 */     mc.field_71474_y.field_74326_T = false;
/* 34 */     mc.field_71460_t.field_175074_C = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPostInit() {
/* 39 */     fov.addValueChangedListener(new Setting.ValueChangedListener(this, true)
/*    */         {
/* 41 */           public void valueChanged() { Zoom.this.update(); }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 47 */     mc.field_71474_y.field_74326_T = true;
/* 48 */     mc.field_71474_y.field_74334_X = fov.intValue();
/* 49 */     mc.field_71460_t.field_175074_C = false;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onKeyPress(InputEvent.KeyInputEvent e) {
/* 54 */     for (Keybind.KeybindValue keybind : Keybind.keybinds) {
/* 55 */       if (keybind.id.replace("Keybind", "").equals(this.name) && 
/* 56 */         !Keyboard.isKeyDown(Keyboard.getKeyIndex(keybind.name))) {
/* 57 */         disable();
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\Zoom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */