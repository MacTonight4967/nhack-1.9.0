/*    */ package me.bebeli555.cookieclient.events.block;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class CanCollideEvent
/*    */   extends Cancellable {
/*    */   public IBlockState state;
/*    */   
/* 10 */   public CanCollideEvent(IBlockState state) { this.state = state; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\block\CanCollideEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */