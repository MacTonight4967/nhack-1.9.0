/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.events.other.PacketServerEvent;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import me.bebeli555.cookieclient.hud.HudEditor;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class LagNotifierComponent extends HudComponent {
/*    */   private static long prevTime;
/*    */   private static long lastServerPacket;
/* 16 */   private static float[] ticks = new float[20];
/*    */   private static int currentTick;
/*    */   
/*    */   public LagNotifierComponent() {
/* 20 */     super(HudComponent.HudCorner.NONE, "LagNotifier");
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 58 */           if (event.packet instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
/* 59 */             if (prevTime != -1L) {
/* 60 */               ticks[currentTick % ticks.length] = MathHelper.func_76131_a(20.0F / (float)(System.currentTimeMillis() - prevTime) / 1000.0F, 0.0F, 20.0F);
/* 61 */               currentTick++;
/*    */             } 
/*    */             
/* 64 */             prevTime = System.currentTimeMillis();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */     
/* 68 */     this.packetServerEvent = new Listener(event -> 
/*    */         
/* 70 */         lastServerPacket = System.currentTimeMillis(), new java.util.function.Predicate[0]);
/*    */     Mod.EVENT_BUS.subscribe(this);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */   @EventHandler
/*    */   private Listener<PacketServerEvent> packetServerEvent;
/*    */   
/*    */   public void onRender(float partialTicks) {
/*    */     super.onRender(partialTicks);
/*    */     if ((lastServerPacket != -1L && Math.abs(System.currentTimeMillis() - lastServerPacket) > 3500L && !mc.func_71356_B()) || HudEditor.module.isOn()) {
/*    */       String seconds = decimal(Math.abs(System.currentTimeMillis() - lastServerPacket) / 1000.0D, 1);
/*    */       String text = g + "Server not responding " + w + seconds + "s";
/*    */       drawString(text, (mc.field_71443_c / 4 - mc.field_71466_p.func_78256_a(text) / 2), 2.0F, -1, true);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldRender() { return GuiSettings.lagNotifier.booleanValue(); }
/*    */   
/*    */   public static double getTps() {
/*    */     tickCount = 0;
/*    */     float tickRate = 0.0F;
/*    */     for (int i = 0; i < ticks.length; i++) {
/*    */       float tick = ticks[i];
/*    */       if (tick > 0.0F) {
/*    */         tickRate += tick;
/*    */         tickCount++;
/*    */       } 
/*    */     } 
/*    */     return MathHelper.func_76131_a(tickRate / tickCount, 0.0F, 20.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\LagNotifierComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */