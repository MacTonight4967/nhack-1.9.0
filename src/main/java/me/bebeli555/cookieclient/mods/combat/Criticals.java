/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ 
/*    */ public class Criticals
/*    */   extends Mod {
/* 15 */   public static Setting distance = new Setting(Mode.DOUBLE, "Distance", Double.valueOf(0.05D), new String[] { "How far it will teleport up" });
/*    */   
/*    */   public Criticals() {
/* 18 */     super(Group.COMBAT, "Criticals", new String[] { "Do critical damage to ur opponent", "Without jumping or falling" });
/*    */ 
/*    */     
/* 21 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 23 */           if (event.packet instanceof CPacketUseEntity) {
/* 24 */             CPacketUseEntity packet = (CPacketUseEntity)event.packet;
/*    */             
/* 26 */             if (packet.func_149565_c() == CPacketUseEntity.Action.ATTACK && packet.func_149564_a(mc.field_71441_e) instanceof net.minecraft.entity.EntityLivingBase) {
/* 27 */               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + distance.doubleValue(), mc.field_71439_g.field_70161_v, false));
/* 28 */               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */