/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.entity.EntityPushEvent;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFishHook;
/*    */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*    */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*    */ 
/*    */ public class NoKnockback
/*    */   extends Mod {
/*    */   public NoKnockback() {
/* 17 */     super(Group.COMBAT, "NoKnockback", new String[] { "Disables knockback" });
/*    */ 
/*    */     
/* 20 */     this.entityPushEvent = new Listener(event -> 
/*    */         
/* 22 */         event.cancel(), new java.util.function.Predicate[0]);
/*    */ 
/*    */     
/* 25 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 27 */           if (event.packet instanceof SPacketEntityStatus) {
/* 28 */             SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/*    */             
/* 30 */             if (packet.func_149160_c() == 31) {
/* 31 */               Entity entity = packet.func_149161_a(mc.field_71441_e);
/*    */               
/* 33 */               if (entity != null && entity instanceof EntityFishHook) {
/* 34 */                 EntityFishHook fishHook = (EntityFishHook)entity;
/*    */                 
/* 36 */                 if (fishHook.field_146043_c == mc.field_71439_g) {
/* 37 */                   event.cancel();
/*    */                 }
/*    */               } 
/*    */             } 
/* 41 */           } else if (event.packet instanceof SPacketEntityVelocity) {
/* 42 */             SPacketEntityVelocity packet = (SPacketEntityVelocity)event.packet;
/* 43 */             if (mc.field_71439_g != null && packet.func_149412_c() != mc.field_71439_g.func_145782_y()) {
/*    */               return;
/*    */             }
/*    */             
/* 47 */             event.cancel();
/* 48 */           } else if (event.packet instanceof net.minecraft.network.play.server.SPacketExplosion) {
/* 49 */             event.cancel();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<EntityPushEvent> entityPushEvent;
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\NoKnockback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */