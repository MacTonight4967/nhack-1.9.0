/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class AutoRespawn
/*    */   extends Mod {
/* 10 */   public AutoRespawn() { super(Group.WORLD, "AutoRespawn", new String[] { "Automatically respawns if you die" }); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 15 */     if (mc.field_71439_g != null && mc.field_71439_g.field_70128_L)
/* 16 */       mc.field_71439_g.func_71004_bE(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */