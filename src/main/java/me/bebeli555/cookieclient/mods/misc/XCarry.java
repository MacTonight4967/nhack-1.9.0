/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*    */ 
/*    */ public class XCarry extends Mod {
/*    */   public XCarry() {
/* 12 */     super(Group.MISC, "XCarry", new String[] { "Allows you to carry items in ur crafting slots" });
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
/* 24 */           if (event.packet instanceof CPacketCloseWindow) {
/* 25 */             CPacketCloseWindow packet = (CPacketCloseWindow)event.packet;
/*    */             
/* 27 */             if (packet.field_149556_a == mc.field_71439_g.field_71069_bz.field_75152_c)
/* 28 */               event.cancel(); 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */   
/*    */   public void onDisabled() {
/*    */     if (mc.field_71441_e != null)
/*    */       mc.field_71439_g.field_71174_a.func_147297_a(new CPacketCloseWindow(mc.field_71439_g.field_71069_bz.field_75152_c)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\XCarry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */