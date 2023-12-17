/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ public class Blink
/*    */   extends Mod
/*    */ {
/*    */   private static EntityOtherPlayerMP original;
/* 20 */   private static ArrayList<Packet<?>> packets = new ArrayList();
/* 21 */   private static ArrayList<Vec3d> lines = new ArrayList();
/*    */   
/*    */   public Blink() {
/* 24 */     super(Group.MOVEMENT, "Blink", new String[] { "Holds movement packets until toggled off like fakelag", "(Dont use this for long duration or u will get kicked)" });
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
/* 51 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 53 */           if (event.packet instanceof net.minecraft.network.play.client.CPacketPlayer || event.packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport) {
/* 54 */             event.cancel();
/* 55 */             packets.add(event.packet);
/*    */             
/* 57 */             setRenderNumber(packets.size());
/* 58 */             lines.add(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v));
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   public void onRenderWorld(float partialTicks) {
/*    */     try {
/* 65 */       for (int i = 0; i < lines.size(); i++) {
/* 66 */         RenderUtil.drawLine((Vec3d)lines.get(i), (Vec3d)lines.get(i + 1), 1.0F, Color.CYAN);
/*    */       }
/* 68 */     } catch (Exception exception) {}
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */   
/*    */   public void onEnabled() {
/*    */     original = new EntityOtherPlayerMP(mc.field_71441_e, mc.field_71449_j.func_148256_e());
/*    */     original.func_82149_j(mc.field_71439_g);
/*    */     original.field_70177_z = mc.field_71439_g.field_70177_z;
/*    */     original.field_70759_as = mc.field_71439_g.field_70759_as;
/*    */     original.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
/*    */     mc.field_71441_e.func_73027_a(-100, original);
/*    */   }
/*    */   
/*    */   public void onDisabled() {
/*    */     if (mc.field_71441_e != null)
/*    */       while (!packets.isEmpty()) {
/*    */         mc.func_147114_u().func_147297_a((Packet)packets.get(0));
/*    */         packets.remove(0);
/*    */       }  
/*    */     mc.field_71441_e.func_72900_e(original);
/*    */     packets.clear();
/*    */     lines.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */