/*    */ package me.bebeli555.cookieclient.events.other;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class PacketPostEvent
/*    */   extends Cancellable
/*    */ {
/*    */   public Packet packet;
/*    */   
/* 11 */   public PacketPostEvent(Packet packet) { this.packet = packet; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\other\PacketPostEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */