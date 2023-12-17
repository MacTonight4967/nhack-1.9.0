/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraftforge.client.event.RenderBlockOverlayEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class BlockVision
/*    */   extends Mod
/*    */ {
/* 11 */   public BlockVision() { super(Group.RENDER, "BlockVision", new String[] { "See clearly when inside blocks" }); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event) {
/* 16 */     if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK)
/* 17 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\BlockVision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */