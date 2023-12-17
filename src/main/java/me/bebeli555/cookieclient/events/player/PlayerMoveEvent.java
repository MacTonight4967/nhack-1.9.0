/*    */ package me.bebeli555.cookieclient.events.player;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.entity.MoverType;
/*    */ 
/*    */ public class PlayerMoveEvent extends Cancellable {
/*    */   public MoverType type;
/*    */   public double x;
/*    */   
/*    */   public PlayerMoveEvent(MoverType type, double x, double y, double z) {
/* 11 */     this.type = type;
/* 12 */     this.x = x;
/* 13 */     this.y = y;
/* 14 */     this.z = z;
/*    */   }
/*    */   
/*    */   public double y;
/*    */   public double z;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\player\PlayerMoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */