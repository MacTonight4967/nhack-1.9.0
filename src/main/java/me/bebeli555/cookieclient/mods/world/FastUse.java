/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.init.Items;
/*    */ 
/*    */ public class FastUse extends Mod {
/* 13 */   public static Setting xpOnly = new Setting(Mode.BOOLEAN, "XPOnly", Boolean.valueOf(false), new String[] { "Only works for xp" });
/*    */ 
/*    */   
/* 16 */   public FastUse() { super(Group.WORLD, "FastUse", new String[] { "Use items and place blocks with no click delay" }); }
/*    */   
/*    */   @EventHandler
/* 19 */   private Listener<PlayerMotionUpdateEvent> onPlayerUpdate = new Listener(event -> {
/*    */         
/* 21 */         if (!xpOnly.booleanValue() || (xpOnly.booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by))
/* 22 */           mc.field_71467_ac = 0; 
/*    */       }new java.util.function.Predicate[0]);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\FastUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */