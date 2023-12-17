/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.IsPotionEffectActiveEvent;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.render.GetRainStrenghtEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderArmorLayerEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderBossHealthEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderEntityEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderHurtcamEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderMapEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderSignEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderUpdateLightMapEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraftforge.client.event.RenderBlockOverlayEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class NoRender
/*     */   extends Mod
/*     */ {
/*  27 */   public static Setting noItems = new Setting(Mode.BOOLEAN, "NoItems", Boolean.valueOf(false), new String[] { "Doesnt render items" });
/*  28 */   public static Setting fire = new Setting(Mode.BOOLEAN, "Fire", Boolean.valueOf(false), new String[] { "Doesnt render fire overlay" });
/*  29 */   public static Setting hurtCamera = new Setting(Mode.BOOLEAN, "HurtCamera", Boolean.valueOf(false), new String[] { "Doesnt render hurtcam effect" });
/*  30 */   public static Setting blindness = new Setting(Mode.BOOLEAN, "Blindness", Boolean.valueOf(false), new String[] { "Doesnt render blindness effect" });
/*  31 */   public static Setting totemAnimation = new Setting(Mode.BOOLEAN, "TotemAnimation", Boolean.valueOf(false), new String[] { "Doesnt render totem pop animation" });
/*  32 */   public static Setting skylight = new Setting(Mode.BOOLEAN, "Skylight", Boolean.valueOf(false), new String[] { "Doesnt render skylight updates" });
/*  33 */   public static Setting signText = new Setting(Mode.BOOLEAN, "SignText", Boolean.valueOf(false), new String[] { "Doesnt render text in signs" });
/*  34 */   public static Setting noArmor = new Setting(Mode.BOOLEAN, "NoArmor", Boolean.valueOf(false), new String[] { "Doesnt render armor" });
/*  35 */   public static Setting maps = new Setting(Mode.BOOLEAN, "Maps", Boolean.valueOf(false), new String[] { "Doesnt render maps" });
/*  36 */   public static Setting bossHealth = new Setting(Mode.BOOLEAN, "BossHealth", Boolean.valueOf(false), new String[] { "Doesnt render the boss bar" });
/*  37 */   public static Setting weather = new Setting(Mode.BOOLEAN, "Weather", Boolean.valueOf(false), new String[] { "Doesnt render weather" });
/*  38 */   public static Setting portal = new Setting(Mode.BOOLEAN, "Portal", Boolean.valueOf(false), new String[] { "Doesnt render the portal effect", "You can also use NoSound in Misc to disable the sound of it" });
/*     */   
/*     */   public NoRender() {
/*  41 */     super(Group.RENDER, "NoRender", new String[] { "Doesnt render certain things" });
/*     */ 
/*     */     
/*  44 */     this.getRainStrenght = new Listener(event -> {
/*     */           
/*  46 */           if (weather.booleanValue()) {
/*  47 */             event.value = 0.0F;
/*  48 */             event.cancel();
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  52 */     this.onRenderEntity = new Listener(event -> {
/*     */           
/*  54 */           if (event.entity instanceof net.minecraft.entity.item.EntityItem && noItems.booleanValue()) {
/*  55 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  59 */     this.onRenderHurtcam = new Listener(event -> {
/*     */           
/*  61 */           if (hurtCamera.booleanValue()) {
/*  62 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.isPotionActive = new Listener(event -> {
/*     */           
/*  75 */           if (event.potion == MobEffects.field_76440_q && blindness.booleanValue()) {
/*  76 */             event.cancel();
/*     */           }
/*     */           
/*  79 */           if (portal.booleanValue() && event.potion == MobEffects.field_76431_k && mc.field_71439_g != null) {
/*  80 */             mc.field_71439_g.func_184589_d(event.potion);
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  84 */     this.onPacket = new Listener(event -> {
/*     */           
/*  86 */           if (event.packet instanceof SPacketEntityStatus) {
/*  87 */             SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/*     */             
/*  89 */             if (packet.func_149160_c() == 35 && totemAnimation.booleanValue()) {
/*  90 */               event.cancel();
/*     */             }
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  95 */     this.onSkylightUpdate = new Listener(event -> {
/*     */           
/*  97 */           if (skylight.booleanValue()) {
/*  98 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/* 102 */     this.onRenderSign = new Listener(event -> {
/*     */           
/* 104 */           if (signText.booleanValue()) {
/* 105 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/* 109 */     this.onRenderArmorLayer = new Listener(event -> {
/*     */           
/* 111 */           if (noArmor.booleanValue()) {
/* 112 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/* 116 */     this.onRenderMap = new Listener(event -> {
/*     */           
/* 118 */           if (maps.booleanValue()) {
/* 119 */             event.cancel();
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/* 123 */     this.onRenderBossHealth = new Listener(event -> {
/*     */           
/* 125 */           if (bossHealth.booleanValue())
/* 126 */             event.cancel(); 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<GetRainStrenghtEvent> getRainStrenght;
/*     */   @EventHandler
/*     */   private Listener<RenderEntityEvent> onRenderEntity;
/*     */   @EventHandler
/*     */   private Listener<RenderHurtcamEvent> onRenderHurtcam;
/*     */   @EventHandler
/*     */   private Listener<IsPotionEffectActiveEvent> isPotionActive;
/*     */   @EventHandler
/*     */   private Listener<PacketEvent> onPacket;
/*     */   @EventHandler
/*     */   private Listener<RenderUpdateLightMapEvent> onSkylightUpdate;
/*     */   @EventHandler
/*     */   private Listener<RenderSignEvent> onRenderSign;
/*     */   @EventHandler
/*     */   private Listener<RenderArmorLayerEvent> onRenderArmorLayer;
/*     */   @EventHandler
/*     */   private Listener<RenderMapEvent> onRenderMap;
/*     */   @EventHandler
/*     */   private Listener<RenderBossHealthEvent> onRenderBossHealth;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event) {
/*     */     if (fire.booleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
/*     */       event.setCanceled(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\NoRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */