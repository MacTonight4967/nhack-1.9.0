/*    */ package me.bebeli555.cookieclient.events.block;
/*    */ 
/*    */ import baritone.api.event.events.type.Cancellable;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class LiquidCollisionEvent
/*    */   extends Cancellable {
/*    */   public BlockPos pos;
/*    */   public AxisAlignedBB boundingBox;
/*    */   
/* 12 */   public LiquidCollisionEvent(BlockPos pos) { this.pos = pos; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\block\LiquidCollisionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */