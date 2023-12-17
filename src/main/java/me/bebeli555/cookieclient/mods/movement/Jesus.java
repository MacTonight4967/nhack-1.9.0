/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.block.LiquidCollisionEvent;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class Jesus extends Mod {
/* 15 */   public static Setting upSpeed = new Setting(Mode.DOUBLE, "UpSpeed", Double.valueOf(0.1D), new String[] { "How fast it goes up when underwater" });
/*    */   
/*    */   public Jesus() {
/* 18 */     super(Group.MOVEMENT, "Jesus", new String[] { "Walk on water" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     this.onLiquidCollision = new Listener(event -> {
/*    */           
/* 30 */           if (mc.field_71441_e != null && mc.field_71439_g != null && 
/* 31 */             mc.field_71439_g.field_70181_x <= 0.0D) {
/* 32 */             event.boundingBox = Block.field_185505_j;
/* 33 */             event.cancel();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerUpdateMoveStatePostEvent> onUpdateMoveState = new Listener(event -> {
/*    */         if (getBlock(getPlayerPos()) == Blocks.field_150355_j)
/*    */           mc.field_71439_g.field_70181_x = upSpeed.doubleValue(); 
/*    */       }new java.util.function.Predicate[0]);
/*    */   @EventHandler
/*    */   private Listener<LiquidCollisionEvent> onLiquidCollision;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Jesus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */