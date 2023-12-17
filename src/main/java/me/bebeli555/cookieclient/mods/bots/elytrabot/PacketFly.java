/*    */ package me.bebeli555.cookieclient.mods.bots.elytrabot;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketFly
/*    */   extends Mod
/*    */ {
/*    */   public PacketFly() {
/* 30 */     this.playerMotionUpdateEvent = new Listener(event -> {
/*    */           
/* 32 */           mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/* 33 */           event.cancel();
/*    */           
/* 35 */           float speedY = 0.0F;
/* 36 */           if (mc.field_71439_g.field_70163_u < startY) {
/* 37 */             if (!antiKickTimer.hasPassed(3000)) {
/* 38 */               speedY = (mc.field_71439_g.field_70173_aa % 20 == 0) ? -0.1F : 0.031F;
/*    */             } else {
/* 40 */               antiKickTimer.reset();
/* 41 */               speedY = -0.1F;
/*    */             } 
/* 43 */           } else if (mc.field_71439_g.field_70173_aa % 4 == 0) {
/* 44 */             speedY = -0.1F;
/*    */           } 
/*    */           
/* 47 */           mc.field_71439_g.field_70181_x = speedY;
/* 48 */           mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70165_t + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u + mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70161_v + mc.field_71439_g.field_70179_y, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, mc.field_71439_g.field_70122_E));
/*    */           
/* 50 */           double y = mc.field_71439_g.field_70163_u + mc.field_71439_g.field_70181_x;
/* 51 */           y += 1337.0D;
/* 52 */           mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70165_t + mc.field_71439_g.field_70159_w, y, mc.field_71439_g.field_70161_v + mc.field_71439_g.field_70179_y, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, mc.field_71439_g.field_70122_E));
/*    */         }new java.util.function.Predicate[0]);
/*    */     
/* 55 */     this.onPacket = new Listener(event -> {
/*    */           
/* 57 */           if (event.packet instanceof SPacketPlayerPosLook && mc.field_71462_r == null) {
/* 58 */             SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.packet;
/* 59 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketConfirmTeleport(packet.func_186965_f()));
/* 60 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e(), packet.func_148931_f(), packet.func_148930_g(), false));
/* 61 */             mc.field_71439_g.func_70107_b(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e());
/*    */             
/* 63 */             event.cancel();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   private static PacketFly packetFly = new PacketFly();
/*    */   private static Timer antiKickTimer = new Timer();
/*    */   private static double startY;
/*    */   public static boolean toggled;
/*    */   @EventHandler
/*    */   public Listener<PlayerMotionUpdateEvent> playerMotionUpdateEvent;
/*    */   @EventHandler
/*    */   public Listener<PacketEvent> onPacket;
/*    */   
/*    */   public static void toggle(boolean on) {
/*    */     toggled = on;
/*    */     if (on) {
/*    */       startY = mc.field_71439_g.field_70163_u;
/*    */       Mod.EVENT_BUS.subscribe(packetFly);
/*    */     } else {
/*    */       Mod.EVENT_BUS.unsubscribe(packetFly);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\elytrabot\PacketFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */