/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.ProcessRightClickBlockEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDestroyBlockEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ 
/*    */ 
/*    */ public class NoGlitchBlocks
/*    */   extends Mod
/*    */ {
/* 18 */   public static Setting place = new Setting(Mode.BOOLEAN, "Place", Boolean.valueOf(true), new String[0]);
/* 19 */   public static Setting destroy = new Setting(Mode.BOOLEAN, "Destroy", Boolean.valueOf(true), new String[0]);
/*    */   
/*    */   public NoGlitchBlocks() {
/* 22 */     super(Group.WORLD, "NoGlitchBlocks", new String[] { "Only places and destroys blocks after the server has confirmed it" });
/*    */ 
/*    */     
/* 25 */     this.onPlayerDestroyBlock = new Listener(event -> {
/*    */           
/* 27 */           if (destroy.booleanValue()) {
/* 28 */             mc.field_71441_e.func_175718_b(2001, event.pos, Block.func_176210_f(mc.field_71441_e.func_180495_p(event.pos)));
/* 29 */             event.cancel();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */     
/* 33 */     this.onRightClickBlock = new Listener(event -> {
/*    */           
/* 35 */           if (place.booleanValue()) {
/* 36 */             event.cancel();
/*    */             
/* 38 */             float f = (float)(event.vec.field_72450_a - event.pos.func_177958_n());
/* 39 */             float f1 = (float)(event.vec.field_72448_b - event.pos.func_177956_o());
/* 40 */             float f2 = (float)(event.vec.field_72449_c - event.pos.func_177952_p());
/* 41 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(event.pos, event.facing, event.hand, f, f1, f2));
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerDestroyBlockEvent> onPlayerDestroyBlock;
/*    */   @EventHandler
/*    */   private Listener<ProcessRightClickBlockEvent> onRightClickBlock;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\NoGlitchBlocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */