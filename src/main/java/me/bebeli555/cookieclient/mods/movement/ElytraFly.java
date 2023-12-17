/*     */ package me.bebeli555.cookieclient.mods.movement;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.TravelEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class ElytraFly extends Mod {
/*  18 */   private static Timer elytraOpenPacketTimer = new Timer();
/*  19 */   private static Timer equipTimer = new Timer();
/*     */   
/*     */   private static boolean isPacketFlying;
/*  22 */   public static Setting mode = new Setting(null, "Mode", "Control", new String[][] { { "Control", "Doesnt change y level unless u jump or sneak" }, { "Boost", "Boosts ur velocity to the direction ur looking" }, { "Packet" } });
/*     */   
/*  24 */   public static Setting speedBoost = new Setting(mode, "Boost", Mode.DOUBLE, "Speed", Integer.valueOf(1), new String[] { "Speed Boost" });
/*  25 */   public static Setting upSpeedBoost = new Setting(mode, "Boost", Mode.DOUBLE, "UpSpeed", Integer.valueOf(1), new String[] { "UpSpeed Boost" });
/*  26 */   public static Setting downSpeedBoost = new Setting(mode, "Boost", Mode.DOUBLE, "DownSpeed", Integer.valueOf(1), new String[] { "DownSpeed Boost" });
/*     */   
/*  28 */   public static Setting controlSpeed = new Setting(mode, "Control", Mode.DOUBLE, "Speed", Integer.valueOf(2), new String[0]);
/*  29 */   public static Setting controlGlideSpeed = new Setting(mode, "Control", Mode.DOUBLE, "GlideSpeed", Double.valueOf(1.0E-4D), new String[0]);
/*  30 */   public static Setting controlUpSpeed = new Setting(mode, "Control", Mode.DOUBLE, "UpSpeed", Integer.valueOf(1), new String[0]);
/*  31 */   public static Setting controlDownSpeed = new Setting(mode, "Control", Mode.DOUBLE, "DownSpeed", Integer.valueOf(1), new String[0]);
/*     */   
/*  33 */   public static Setting packetSpeed = new Setting(mode, "Packet", Mode.DOUBLE, "Speed", Double.valueOf(1.81D), new String[0]);
/*  34 */   public static Setting packetGlideSpeed = new Setting(mode, "Packet", Mode.DOUBLE, "GlideSpeed", Double.valueOf(1.0E-4D), new String[0]);
/*  35 */   public static Setting packetUpSpeed = new Setting(mode, "Packet", Mode.DOUBLE, "UpSpeed", Integer.valueOf(1), new String[0]);
/*  36 */   public static Setting packetDownSpeed = new Setting(mode, "Packet", Mode.DOUBLE, "DownSpeed", Integer.valueOf(1), new String[0]);
/*  37 */   public static Setting autoTakeoff = new Setting(Mode.BOOLEAN, "AutoTakeoff", Boolean.valueOf(true), new String[] { "Automatically takes off when ur falling with elytra" });
/*  38 */   public static Setting timerTps = new Setting(autoTakeoff, Mode.DOUBLE, "TimerTPS", Integer.valueOf(10), new String[] { "The value to change client-sided tps to give more time to open elytra", "Lower = slower", "20 = No timer" });
/*  39 */   public static Setting autoEquip = new Setting(autoTakeoff, Mode.BOOLEAN, "AutoEquip", Boolean.valueOf(true), new String[] { "Equips elytra automatically" }); @EventHandler
/*  40 */   private Listener<TravelEvent> onTravel; public static Setting waitAmount = new Setting(autoTakeoff, Mode.INTEGER, "WaitAmount", Integer.valueOf(500), new String[] { "How long to wait between sending elytra open packets in ms" });
/*     */   
/*     */   public ElytraFly() {
/*  43 */     super(Group.MOVEMENT, "ElytraFly", new String[] { "Allows you to fly faster and better with elytras" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     this.onTravel = new Listener(event -> {
/*     */           
/*  54 */           if (mc.field_71439_g == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  59 */           if (mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70122_E) {
/*  60 */             mc.field_71428_T.field_194149_e = 50.0F;
/*     */           }
/*     */           
/*  63 */           if (!mc.field_71439_g.func_184613_cA() && !isPacketFlying)
/*     */           {
/*  65 */             if (autoTakeoff.booleanValue()) {
/*  66 */               if (mc.field_71439_g.field_70163_u < mc.field_71439_g.field_70137_T || mc.field_71439_g.field_70143_R > 1.0F) {
/*     */                 
/*  68 */                 if (autoEquip.booleanValue() && equipTimer.hasPassed(250) && InventoryUtil.getItemStack(38).func_77973_b() != Items.field_185160_cR && InventoryUtil.hasItem(Items.field_185160_cR)) {
/*  69 */                   equipTimer.reset();
/*  70 */                   int elytraSlot = InventoryUtil.getSlot(Items.field_185160_cR);
/*  71 */                   InventoryUtil.clickSlot(elytraSlot);
/*  72 */                   InventoryUtil.clickSlot(38);
/*  73 */                   InventoryUtil.clickSlot(elytraSlot);
/*     */                 } 
/*     */ 
/*     */                 
/*  77 */                 if (elytraOpenPacketTimer.hasPassed(waitAmount.intValue())) {
/*     */                   
/*  79 */                   mc.field_71428_T.field_194149_e = 1000.0F / (float)timerTps.doubleValue();
/*     */                   
/*  81 */                   elytraOpenPacketTimer.reset();
/*  82 */                   mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           }
/*     */           
/*  91 */           if (mode.stringValue().equals("Control")) {
/*  92 */             handleControlMode(event);
/*  93 */           } else if (mode.stringValue().equals("Boost")) {
/*  94 */             handleBoostMode();
/*  95 */           } else if (mode.stringValue().equals("Packet")) {
/*  96 */             handlePacketMode(event);
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/* 101 */   public void handleControlMode(TravelEvent event) { if (EntitySpeed.isInputting()) {
/* 102 */       double yawRad = Math.toRadians(mc.field_71439_g.field_70177_z - Freecam.getRotationFromVec(new Vec3d(-mc.field_71439_g.field_70702_br, 0.0D, mc.field_71439_g.field_191988_bg))[0]);
/* 103 */       mc.field_71439_g.field_70159_w = -Math.sin(yawRad) * controlSpeed.doubleValue();
/* 104 */       mc.field_71439_g.field_70179_y = Math.cos(yawRad) * controlSpeed.doubleValue();
/*     */     } else {
/* 106 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 107 */       mc.field_71439_g.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 110 */     if (mc.field_71439_g.field_71158_b.field_78901_c) {
/* 111 */       mc.field_71439_g.field_70181_x = controlUpSpeed.doubleValue();
/* 112 */     } else if (mc.field_71439_g.field_71158_b.field_78899_d) {
/* 113 */       mc.field_71439_g.field_70181_x = -controlDownSpeed.doubleValue();
/*     */     } else {
/* 115 */       mc.field_71439_g.field_70181_x = -controlGlideSpeed.doubleValue();
/*     */     } 
/*     */     
/* 118 */     event.cancel(); }
/*     */ 
/*     */   
/*     */   public void handleBoostMode() {
/* 122 */     float yaw = (float)Math.toRadians(mc.field_71439_g.field_70177_z);
/* 123 */     mc.field_71439_g.field_70159_w -= mc.field_71439_g.field_71158_b.field_192832_b * Math.sin(yaw) * speedBoost.doubleValue() / 20.0D;
/* 124 */     mc.field_71439_g.field_70179_y += mc.field_71439_g.field_71158_b.field_192832_b * Math.cos(yaw) * speedBoost.doubleValue() / 20.0D;
/*     */     
/* 126 */     if (mc.field_71439_g.field_71158_b.field_78901_c) {
/* 127 */       mc.field_71439_g.field_70181_x += upSpeedBoost.doubleValue() / 15.0D;
/* 128 */     } else if (mc.field_71439_g.field_71158_b.field_78899_d) {
/* 129 */       mc.field_71439_g.field_70181_x -= downSpeedBoost.doubleValue() / 15.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handlePacketMode(TravelEvent event) {
/* 134 */     if (mc.field_71439_g.field_70122_E) {
/* 135 */       isPacketFlying = false;
/*     */       
/*     */       return;
/*     */     } 
/* 139 */     isPacketFlying = true;
/* 140 */     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/*     */     
/* 142 */     if (EntitySpeed.isInputting()) {
/* 143 */       double yawRad = Math.toRadians(mc.field_71439_g.field_70177_z - Freecam.getRotationFromVec(new Vec3d(-mc.field_71439_g.field_70702_br, 0.0D, mc.field_71439_g.field_191988_bg))[0]);
/* 144 */       mc.field_71439_g.field_70159_w = -Math.sin(yawRad) * controlSpeed.doubleValue();
/* 145 */       mc.field_71439_g.field_70179_y = Math.cos(yawRad) * controlSpeed.doubleValue();
/*     */     } else {
/* 147 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 148 */       mc.field_71439_g.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 151 */     if (mc.field_71439_g.field_71158_b.field_78899_d) {
/* 152 */       mc.field_71439_g.field_70181_x = -packetDownSpeed.doubleValue();
/* 153 */     } else if (mc.field_71439_g.field_71158_b.field_78901_c) {
/* 154 */       mc.field_71439_g.field_70181_x = packetUpSpeed.doubleValue();
/*     */     } else {
/* 156 */       mc.field_71439_g.field_70181_x = -packetGlideSpeed.doubleValue();
/*     */     } 
/*     */     
/* 159 */     event.cancel();
/*     */   }
/*     */   
/*     */   public void onDisabled() {
/*     */     mc.field_71428_T.field_194149_e = 50.0F;
/*     */     isPacketFlying = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\ElytraFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */