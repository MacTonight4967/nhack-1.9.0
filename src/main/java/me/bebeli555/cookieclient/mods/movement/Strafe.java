/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMoveEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.network.play.server.SPacketExplosion;
/*    */ 
/*    */ public class Strafe extends Mod {
/* 17 */   private Timer knockbackTimer = new Timer();
/*    */   private double boostedX;
/*    */   private double boostedZ;
/* 20 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Double.valueOf(0.285D), new String[] { "How fast it goes" });
/* 21 */   public static Setting height = new Setting(Mode.DOUBLE, "Height", Double.valueOf(0.41D), new String[] { "How far up it will jump" });
/* 22 */   public static Setting crystalBoost = new Setting(Mode.BOOLEAN, "CrystalBoost", Boolean.valueOf(false), new String[] { "Applies the knockback u take from crystals to the strafe speed" });
/* 23 */   public static Setting knockbackAmount = new Setting(crystalBoost, Mode.INTEGER, "Amount", Integer.valueOf(850), new String[] { "How long to keep the boosted motion in milliseconds" });
/*    */   
/*    */   public Strafe() {
/* 26 */     super(Group.MOVEMENT, "Strafe", new String[] { "Goes a bit faster than normal", "Also when u take knockback it will make the speed faster" });
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
/* 38 */     this.onPlayerMove = new Listener(event -> {
/*    */           
/* 40 */           if (!mc.field_175622_Z.equals(mc.field_71439_g)) {
/*    */             return;
/*    */           }
/*    */           
/* 44 */           float playerSpeed = (float)speed.doubleValue();
/* 45 */           float moveForward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 46 */           float moveStrafe = mc.field_71439_g.field_71158_b.field_78902_a;
/* 47 */           float rotationYaw = mc.field_71439_g.field_70177_z;
/*    */           
/* 49 */           if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 50 */             int amplifier = mc.field_71439_g.func_70660_b(MobEffects.field_76424_c).func_76458_c();
/* 51 */             playerSpeed *= (1.0F + 0.2F * (amplifier + 1));
/*    */           } 
/*    */           
/* 54 */           if (moveForward == 0.0F && moveStrafe == 0.0F) {
/* 55 */             event.x = 0.0D;
/* 56 */             event.z = 0.0D;
/*    */           } else {
/* 58 */             if (moveForward != 0.0F) {
/* 59 */               if (moveStrafe > 0.0F) {
/* 60 */                 rotationYaw += ((moveForward > 0.0F) ? -45 : 45);
/* 61 */               } else if (moveStrafe < 0.0F) {
/* 62 */                 rotationYaw += ((moveForward > 0.0F) ? 45 : -45);
/*    */               } 
/*    */               
/* 65 */               moveStrafe = 0.0F;
/* 66 */               if (moveForward > 0.0F) {
/* 67 */                 moveForward = 1.0F;
/* 68 */               } else if (moveForward < 0.0F) {
/* 69 */                 moveForward = -1.0F;
/*    */               } 
/*    */             } 
/*    */             
/* 73 */             if (crystalBoost.booleanValue() && !this.knockbackTimer.hasPassed(knockbackAmount.intValue())) {
/* 74 */               playerSpeed = (float)Math.abs(this.boostedX);
/* 75 */               playerSpeed = (float)(playerSpeed + Math.abs(this.boostedZ));
/*    */             } 
/*    */             
/* 78 */             event.x = (moveForward * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0F))) + (moveStrafe * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0F)));
/* 79 */             event.z = (moveForward * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0F))) - (moveStrafe * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0F)));
/*    */           } 
/*    */           
/* 82 */           event.cancel();
/*    */         }new java.util.function.Predicate[0]);
/*    */     
/* 85 */     this.packetEvent = new Listener(event -> {
/*    */           
/* 87 */           if (event.packet instanceof SPacketExplosion) {
/* 88 */             SPacketExplosion packet = (SPacketExplosion)event.packet;
/*    */             
/* 90 */             this.boostedX = packet.func_149149_c();
/* 91 */             this.boostedZ = packet.func_149147_e();
/* 92 */             this.knockbackTimer.reset();
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener(event -> {
/*    */         if (!EntitySpeed.isInputting() || mc.field_71439_g.func_184218_aH() || !mc.field_71439_g.field_70122_E || !mc.field_175622_Z.equals(mc.field_71439_g))
/*    */           return; 
/*    */         mc.field_71439_g.field_70181_x = height.doubleValue();
/*    */       }new java.util.function.Predicate[0]);
/*    */   @EventHandler
/*    */   private Listener<PlayerMoveEvent> onPlayerMove;
/*    */   @EventHandler
/*    */   private Listener<PacketEvent> packetEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Strafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */