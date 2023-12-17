/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class NoFall extends Mod {
/*    */   public NoFall() {
/* 14 */     super(Group.MOVEMENT, "NoFall", new String[] { "Prevents fall damage" });
/*    */ 
/*    */     
/* 17 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 19 */           if (event.packet instanceof CPacketPlayer) {
/* 20 */             if (mc.field_71439_g.func_184613_cA() || (InventoryUtil.getItemStack(38).func_77973_b() == Items.field_185160_cR && mc.field_71474_y.field_74314_A.func_151470_d())) {
/*    */               return;
/*    */             }
/*    */             
/* 24 */             CPacketPlayer packet = (CPacketPlayer)event.packet;
/* 25 */             packet.field_149474_g = true;
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */