/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.entity.EntityAddedEvent;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoLog
/*    */   extends Mod {
/* 21 */   public static Setting mode = new Setting(null, "Mode", "TotemPop", new String[][] { { "TotemPop" }, { "OnRender" }, { "Health" } });
/* 22 */   public static Setting onRenderFriend = new Setting(mode, "OnRender", Mode.BOOLEAN, "Friend", Boolean.valueOf(true), new String[] { "Log even if its a friend" });
/* 23 */   public static Setting onPop = new Setting(mode, "TotemPop", Mode.BOOLEAN, "OnPop", Boolean.valueOf(true), new String[] { "Logs when u pop a totem" });
/* 24 */   public static Setting onPopCount = new Setting(mode, "TotemPop", Mode.INTEGER, "Count", Integer.valueOf(1), new String[] { "If ur inventory has less or equal to this amount", "Of totems when u pop then it logs", "Turn off OnPop if u wanna use this" }); @EventHandler
/* 25 */   private Listener<PacketEvent> packetEvent; public static Setting onDamageHealth = new Setting(mode, "Health", Mode.INTEGER, "Health", Integer.valueOf(20), new String[] { "If ur health is less or equal to this", "When you take damage then it logs" }); @EventHandler
/*    */   private Listener<EntityAddedEvent> onEntityAdded;
/*    */   public AutoLog() {
/* 28 */     super(Group.COMBAT, "AutoLog", new String[] { "Logs out of the server if the", "Given parameters are reached" });
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
/* 40 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 42 */           if (event.packet instanceof SPacketEntityStatus) {
/* 43 */             SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/*    */             
/* 45 */             if (packet.func_149160_c() == 35 && packet.func_149161_a(mc.field_71441_e) == mc.field_71439_g && 
/* 46 */               mode.stringValue().equals("TotemPop") && (
/* 47 */               onPop.booleanValue() || InventoryUtil.getAmountOfItem(Items.field_190929_cY) <= onPopCount.intValue())) {
/* 48 */               if (onPop.booleanValue()) {
/* 49 */                 log("CookieClient AutoLog - You popped a totem");
/*    */               } else {
/* 51 */                 log("CookieClient AutoLog - less or equal amount of totems as TotemPopCount");
/*    */               } 
/*    */             }
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */ 
/*    */ 
/*    */     
/* 59 */     this.onEntityAdded = new Listener(event -> {
/*    */           
/* 61 */           if (event.entity instanceof net.minecraft.entity.player.EntityPlayer && mode.stringValue().equals("OnRender")) {
/* 62 */             if (Friends.isFriend(event.entity) && !onRenderFriend.booleanValue()) {
/*    */               return;
/*    */             }
/*    */             
/* 66 */             log("CookieClient AutoLog - " + event.entity.func_70005_c_() + " entered render distance");
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   public void log(String message) {
/* 71 */     if (mc.field_71439_g == null || mc.field_71439_g.field_71174_a == null) {
/*    */       return;
/*    */     }
/*    */     
/* 75 */     mc.field_71439_g.field_71174_a.func_147298_b().func_150718_a(new TextComponentString(message));
/* 76 */     disable();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onDamage(LivingHurtEvent event) {
/*    */     if (event.getEntity().equals(mc.field_71439_g) && mode.stringValue().equals("Health") && mc.field_71439_g.func_110143_aJ() <= onDamageHealth.intValue())
/*    */       log("CookieClient AutoLog - Your health was lover than the given min health"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\AutoLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */