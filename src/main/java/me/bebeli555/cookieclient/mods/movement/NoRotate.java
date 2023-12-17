/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*    */ 
/*    */ public class NoRotate extends Mod {
/*    */   public NoRotate() {
/* 15 */     super(Group.MOVEMENT, "NoRotate", new String[] { "Cancels server rotations" });
/*    */ 
/*    */     
/* 18 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 20 */           if (event.packet instanceof SPacketPlayerPosLook && mc.field_71439_g != null && !mc.field_71439_g.field_70128_L) {
/* 21 */             SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.packet;
/* 22 */             event.cancel();
/*    */             
/* 24 */             double x = packet.func_148932_c();
/* 25 */             double y = packet.func_148928_d();
/* 26 */             double z = packet.func_148933_e();
/*    */             
/* 28 */             if (packet.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X)) {
/* 29 */               x += mc.field_71439_g.field_70165_t;
/*    */             } else {
/* 31 */               mc.field_71439_g.field_70159_w = 0.0D;
/*    */             } 
/*    */             
/* 34 */             if (packet.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
/* 35 */               y += mc.field_71439_g.field_70163_u;
/*    */             } else {
/* 37 */               mc.field_71439_g.field_70181_x = 0.0D;
/*    */             } 
/*    */             
/* 40 */             if (packet.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
/* 41 */               z += mc.field_71439_g.field_70161_v;
/*    */             } else {
/* 43 */               mc.field_71439_g.field_70179_y = 0.0D;
/*    */             } 
/*    */             
/* 46 */             mc.field_71439_g.func_70107_b(x, y, z);
/* 47 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketConfirmTeleport(packet.func_186965_f()));
/* 48 */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70165_t, (mc.field_71439_g.func_174813_aQ()).field_72338_b, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, false));
/*    */             
/* 50 */             if (!mc.field_71439_g.field_71174_a.field_147309_h) {
/* 51 */               mc.field_71439_g.field_70169_q = mc.field_71439_g.field_70165_t;
/* 52 */               mc.field_71439_g.field_70167_r = mc.field_71439_g.field_70163_u;
/* 53 */               mc.field_71439_g.field_70166_s = mc.field_71439_g.field_70161_v;
/* 54 */               mc.field_71439_g.field_71174_a.field_147309_h = true;
/* 55 */               mc.func_147108_a((GuiScreen)null);
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\NoRotate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */