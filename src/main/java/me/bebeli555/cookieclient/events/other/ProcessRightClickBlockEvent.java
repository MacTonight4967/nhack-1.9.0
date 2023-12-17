/*    */ package me.bebeli555.cookieclient.events.other;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class ProcessRightClickBlockEvent extends Cancellable {
/*    */   public EntityPlayerSP player;
/*    */   public WorldClient world;
/*    */   public BlockPos pos;
/*    */   public EnumFacing facing;
/*    */   public Vec3d vec;
/*    */   public EnumHand hand;
/*    */   
/*    */   public ProcessRightClickBlockEvent(EntityPlayerSP player, WorldClient world, BlockPos pos, EnumFacing facing, Vec3d vec, EnumHand hand) {
/* 20 */     this.player = player;
/* 21 */     this.world = world;
/* 22 */     this.pos = pos;
/* 23 */     this.facing = facing;
/* 24 */     this.vec = vec;
/* 25 */     this.hand = hand;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\other\ProcessRightClickBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */