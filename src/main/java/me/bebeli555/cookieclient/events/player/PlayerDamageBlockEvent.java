/*    */ package me.bebeli555.cookieclient.events.player;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class PlayerDamageBlockEvent extends Cancellable {
/*    */   public BlockPos pos;
/*    */   public EnumFacing facing;
/*    */   
/*    */   public PlayerDamageBlockEvent(BlockPos pos, EnumFacing facing) {
/* 12 */     this.pos = pos;
/* 13 */     this.facing = facing;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\player\PlayerDamageBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */