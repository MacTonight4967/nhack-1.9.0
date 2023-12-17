/*    */ package me.bebeli555.cookieclient.events.other;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class PacketEvent
/*    */   extends Cancellable
/*    */ {
/*    */   public Packet packet;
/*    */   
/* 11 */   public PacketEvent(Packet packet) { this.packet = packet; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\other\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */