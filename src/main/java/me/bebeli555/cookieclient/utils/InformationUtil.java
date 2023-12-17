/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.handshake.client.C00Handshake;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InformationUtil
/*    */ {
/* 16 */   public InformationUtil() { this.packetEvent = new Listener(event -> {
/*    */ 
/*    */           
/* 19 */           if (event.packet instanceof C00Handshake) {
/* 20 */             C00Handshake packet = (C00Handshake)event.packet;
/* 21 */             if (packet.func_149594_c() == EnumConnectionState.LOGIN) {
/* 22 */               lastIp = packet.field_149598_b;
/* 23 */               lastPort = packet.field_149599_c;
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]); }
/*    */   
/*    */   public static String lastIp = "";
/*    */   public static int lastPort = -1;
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\InformationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */