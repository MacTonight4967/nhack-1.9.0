/*    */ package me.bebeli555.cookieclient.events.player;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class PlayerDestroyBlockEvent
/*    */   extends Cancellable {
/*    */   public BlockPos pos;
/*    */   
/* 10 */   public PlayerDestroyBlockEvent(BlockPos pos) { this.pos = pos; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\player\PlayerDestroyBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */