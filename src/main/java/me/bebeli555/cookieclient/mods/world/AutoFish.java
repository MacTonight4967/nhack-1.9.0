/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*    */ 
/*    */ public class AutoFish extends Mod {
/*    */   private static Thread thread;
/*    */   private static boolean splash;
/* 17 */   private static Timer timer = new Timer();
/*    */   
/* 19 */   public static Setting castDelay = new Setting(Mode.INTEGER, "CastDelay", Integer.valueOf(500), new String[] { "How long to wait in ms before casting the rod again" });
/* 20 */   public static Setting catchDelay = new Setting(Mode.INTEGER, "CatchDelay", Integer.valueOf(500), new String[] { "How long to wait in ms before taking the fish out" });
/*    */   
/*    */   public AutoFish() {
/* 23 */     super(Group.WORLD, "AutoFish", new String[] { "Automatically fishes for you" });
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
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 65 */           if (event.packet instanceof SPacketSoundEffect) {
/* 66 */             SPacketSoundEffect packet = (SPacketSoundEffect)event.packet;
/*    */             
/* 68 */             if (packet.func_186978_a().func_187503_a().func_110623_a().contains("entity.bobber.splash") && timer.hasPassed(3500))
/* 69 */               splash = true; 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */   
/*    */   public void onEnabled() {
/*    */     thread = new Thread() {
/*    */         public void run() {
/*    */           while (AutoFish.access$000() != null && AutoFish.access$000().equals(this)) {
/*    */             AutoFish.this.loop();
/*    */             Mod.sleep(50);
/*    */           } 
/*    */         }
/*    */       };
/*    */     thread.start();
/*    */   }
/*    */   
/*    */   public void onDisabled() {
/*    */     thread = null;
/*    */     splash = false;
/*    */   }
/*    */   
/*    */   public void loop() {
/*    */     if (mc.field_71439_g == null)
/*    */       return; 
/*    */     if (mc.field_71439_g.field_71104_cf == null) {
/*    */       sleep(castDelay.intValue());
/*    */       PlayerUtil.rightClick();
/*    */       timer.reset();
/*    */     } else if (splash) {
/*    */       sleep(catchDelay.intValue());
/*    */       PlayerUtil.rightClick();
/*    */       splash = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */