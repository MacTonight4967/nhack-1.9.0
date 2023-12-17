/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ 
/*    */ public class AntiHunger extends Mod {
/*    */   public AntiHunger() {
/* 12 */     super(Group.MOVEMENT, "AntiHunger", new String[] { "Reduces lost hunger" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 22 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 24 */           if (event.packet instanceof CPacketEntityAction) {
/* 25 */             CPacketEntityAction packet = (CPacketEntityAction)event.packet;
/* 26 */             if (packet.field_149515_b == CPacketEntityAction.Action.START_SPRINTING || packet.field_149515_b == CPacketEntityAction.Action.STOP_SPRINTING)
/* 27 */               event.cancel(); 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */   
/*    */   public void onEnabled() {
/*    */     if (mc.field_71439_g != null && mc.field_71439_g.func_70051_ag())
/*    */       mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\AntiHunger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */