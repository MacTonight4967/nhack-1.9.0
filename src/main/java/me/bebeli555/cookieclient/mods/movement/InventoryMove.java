/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryMove
/*    */   extends Mod
/*    */ {
/* 17 */   public InventoryMove() { super(Group.MOVEMENT, "InventoryMove", new String[] { "Allows you to move while having GUI's open" }); }
/*    */   
/*    */   @EventHandler
/* 20 */   private Listener<PlayerUpdateMoveStatePostEvent> onKeyPress = new Listener(event -> {
/*    */         
/* 22 */         if (mc.field_71462_r == null || mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiEditSign || mc.field_71462_r instanceof net.minecraft.client.gui.GuiScreenBook) {
/*    */           return;
/*    */         }
/*    */         
/* 26 */         mc.field_71439_g.field_71158_b.field_78902_a = 0.0F;
/* 27 */         mc.field_71439_g.field_71158_b.field_192832_b = 0.0F;
/*    */         
/* 29 */         KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_151444_V.func_151463_i()));
/* 30 */         if (Keyboard.isKeyDown(mc.field_71474_y.field_151444_V.func_151463_i())) {
/* 31 */           mc.field_71439_g.func_70031_b(true);
/*    */         }
/*    */         
/* 34 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74351_w.func_151463_i()));
/* 35 */         if (Keyboard.isKeyDown(mc.field_71474_y.field_74351_w.func_151463_i())) {
/* 36 */           mc.field_71439_g.field_71158_b.field_192832_b++;
/* 37 */           mc.field_71439_g.field_71158_b.field_187255_c = true;
/*    */         } else {
/* 39 */           mc.field_71439_g.field_71158_b.field_187255_c = false;
/*    */         } 
/*    */         
/* 42 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74368_y.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74368_y.func_151463_i()));
/* 43 */         if (Keyboard.isKeyDown(mc.field_71474_y.field_74368_y.func_151463_i())) {
/* 44 */           mc.field_71439_g.field_71158_b.field_192832_b--;
/* 45 */           mc.field_71439_g.field_71158_b.field_187256_d = true;
/*    */         } else {
/* 47 */           mc.field_71439_g.field_71158_b.field_187256_d = false;
/*    */         } 
/*    */         
/* 50 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74370_x.func_151463_i()));
/* 51 */         if (Keyboard.isKeyDown(mc.field_71474_y.field_74370_x.func_151463_i())) {
/* 52 */           mc.field_71439_g.field_71158_b.field_78902_a++;
/* 53 */           mc.field_71439_g.field_71158_b.field_187257_e = true;
/*    */         } else {
/* 55 */           mc.field_71439_g.field_71158_b.field_187257_e = false;
/*    */         } 
/*    */         
/* 58 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74366_z.func_151463_i()));
/* 59 */         if (Keyboard.isKeyDown(mc.field_71474_y.field_74366_z.func_151463_i())) {
/* 60 */           mc.field_71439_g.field_71158_b.field_78902_a--;
/* 61 */           mc.field_71439_g.field_71158_b.field_187258_f = true;
/*    */         } else {
/* 63 */           mc.field_71439_g.field_71158_b.field_187258_f = false;
/*    */         } 
/*    */         
/* 66 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74314_A.func_151463_i()));
/* 67 */         mc.field_71439_g.field_71158_b.field_78901_c = Keyboard.isKeyDown(mc.field_71474_y.field_74314_A.func_151463_i());
/*    */       }new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\InventoryMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */