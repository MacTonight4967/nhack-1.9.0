/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.render.SetupFogEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class LiquidVision extends Mod {
/*    */   public LiquidVision() {
/* 11 */     super(Group.RENDER, "LiquidVision", new String[] { "Allows you to see clearly in water/lava", "(Stops fog from being rendered)" });
/*    */ 
/*    */     
/* 14 */     this.setupFog = new Listener(event -> {
/*    */           
/* 16 */           if (mc.field_71439_g.field_70173_aa < 20) {
/*    */             return;
/*    */           }
/*    */           
/* 20 */           event.cancel();
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<SetupFogEvent> setupFog;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\LiquidVision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */