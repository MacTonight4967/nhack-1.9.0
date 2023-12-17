/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.entity.EntityRemovedEvent;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.CrystalUtil;
/*     */ import me.bebeli555.cookieclient.utils.EntityUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoCrystal
/*     */   extends Mod
/*     */ {
/*  55 */   public static Timer removeVisualTimer = new Timer();
/*  56 */   public static List<BlockPos> placedCrystals = new CopyOnWriteArrayList();
/*  57 */   private ConcurrentHashMap<EntityEnderCrystal, Integer> attackedCrystals = new ConcurrentHashMap();
/*  58 */   private List<BlockPos> placeLocations = new CopyOnWriteArrayList(); private int remainingTicks;
/*     */   private BlockPos placePos;
/*     */   private BlockPos breakPos;
/*  61 */   private Timer lastPlaceOrBreak = new Timer();
/*  62 */   private Timer checkPlacedTimer = new Timer();
/*     */   
/*     */   private boolean rotating;
/*  65 */   public static Setting players = new Setting(Mode.BOOLEAN, "Players", Boolean.valueOf(true), new String[] { "Attacks players but not friends" });
/*  66 */   public static Setting monsters = new Setting(Mode.BOOLEAN, "Monsters", Boolean.valueOf(false), new String[] { "Attacks monsters" });
/*  67 */   public static Setting neutrals = new Setting(Mode.BOOLEAN, "Neutrals", Boolean.valueOf(false), new String[] { "Attacks neutral entities like enderman" });
/*  68 */   public static Setting passive = new Setting(Mode.BOOLEAN, "Passive", Boolean.valueOf(false), new String[] { "Attacks passive entities like animals" });
/*  69 */   public static Setting breakMode = new Setting(null, "BreakMode", "OnlyOwn", new String[][] { { "Always" }, { "OnlyOwn" } });
/*  70 */   public static Setting placeRadius = new Setting(Mode.DOUBLE, "PlaceRadius", Double.valueOf(5.5D), new String[] { "Radius for placing" });
/*  71 */   public static Setting breakRadius = new Setting(Mode.DOUBLE, "BreakRadius", Double.valueOf(5.5D), new String[] { "Radius for breaking" });
/*  72 */   public static Setting wallsRange = new Setting(Mode.DOUBLE, "WallsRange", Double.valueOf(3.5D), new String[] { "Max distance through walls" });
/*  73 */   public static Setting multiPlace = new Setting(Mode.BOOLEAN, "MultiPlace", Boolean.valueOf(false), new String[] { "Tries to multiplace" });
/*  74 */   public static Setting ticks = new Setting(Mode.INTEGER, "Ticks", Integer.valueOf(2), new String[] { "The number of ticks to ignore on client update" });
/*  75 */   public static Setting minDmg = new Setting(Mode.DOUBLE, "MinDMG", Integer.valueOf(6), new String[] { "Minimum damage to do to your opponent" });
/*  76 */   public static Setting maxSelfDmg = new Setting(Mode.DOUBLE, "MaxSelfDMG", Integer.valueOf(13), new String[] { "Max self damage allowed" });
/*  77 */   public static Setting facePlace = new Setting(Mode.DOUBLE, "FacePlace", Integer.valueOf(9), new String[] { "Required target health for faceplacing" });
/*  78 */   public static Setting autoSwitch = new Setting(Mode.BOOLEAN, "AutoSwitch", Boolean.valueOf(true), new String[] { "Automatically switches to crystals in your hotbar" });
/*  79 */   public static Setting autoSwitchReplace = new Setting(autoSwitch, Mode.BOOLEAN, "Replace", Boolean.valueOf(false), new String[] { "If hotbar has no crystals then it puts crystals to", "Ur hotbar if ur inventory has crystals" });
/*  80 */   public static Setting pauseIfHittingBlock = new Setting(Mode.BOOLEAN, "PauseIfHittingBlock", Boolean.valueOf(false), new String[] { "Pauses when your hitting a block with a pickaxe" });
/*  81 */   public static Setting pauseWhileEating = new Setting(Mode.BOOLEAN, "PauseWhileEating", Boolean.valueOf(false), new String[] { "Pauses while eating" });
/*  82 */   public static Setting noSuicide = new Setting(Mode.BOOLEAN, "NoSuicide", Boolean.valueOf(true), new String[] { "Doesn't commit suicide/pop if you are going to take fatal damage from self placed crystal" });
/*  83 */   public static Setting antiWeakness = new Setting(Mode.BOOLEAN, "AntiWeakness", Boolean.valueOf(true), new String[] { "Switches to a sword to try and break crystals" });
/*  84 */   public static Setting extraRotatePackets = new Setting(Mode.BOOLEAN, "ExtraRotatePackets", Boolean.valueOf(false), new String[] { "Sends a rotation packet every rotation", "Having this on will not work on most servers" });
/*  85 */   public static Setting renderFillBox = new Setting(Mode.BOOLEAN, "RenderFillBox", Boolean.valueOf(true), new String[] { "Renders filled box around place / break spot" });
/*  86 */   public static Setting renderFillBoxColor = new Setting(renderFillBox, Mode.INTEGER, "Color", Integer.valueOf(906035135), new String[] { "Color for the box in hex with alpha" }); @EventHandler private Listener<EntityRemovedEvent> onEntityRemove; @EventHandler
/*  87 */   private Listener<PacketEvent> packetEvent; public static Setting boundingBox = new Setting(Mode.BOOLEAN, "BoundingBox", Boolean.valueOf(true), new String[] { "Renders bounding box around the place / break spot", "Like the corners", "If its breaking then its red and placing is green" }); public void onDisabled() { this.placePos = null; this.breakPos = null; this.remainingTicks = 0; this.rotating = false; this.lastPlaceOrBreak.reset(); removeVisualTimer.reset(); this.checkPlacedTimer.reset(); this.attackedCrystals.clear(); this.placeLocations.clear();
/*     */     placedCrystals.clear();
/*     */     RotationUtil.stopRotating(); }
/*  90 */   public AutoCrystal() { super(Group.COMBAT, "AutoCrystal", new String[] { "Places and destroyes crystals", "In attempt to kill target" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.onEntityRemove = new Listener(event -> {
/*     */           
/* 110 */           if (event.entity instanceof EntityEnderCrystal) {
/* 111 */             this.attackedCrystals.remove((EntityEnderCrystal)event.entity);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     this.packetEvent = new Listener(event -> {
/*     */           
/* 439 */           if (event.packet instanceof SPacketSoundEffect)
/* 440 */           { SPacketSoundEffect packet = (SPacketSoundEffect)event.packet;
/*     */             
/* 442 */             if (mc.field_71441_e == null) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 447 */             if (packet.func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB)
/*     */             {
/* 449 */               (new ArrayList(mc.field_71441_e.field_72996_f)).forEach(());
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */             
/*     */              }
/*     */           
/* 458 */           else if (event.packet instanceof CPacketPlayerTryUseItemOnBlock && mc.field_71439_g != null)
/*     */           
/* 460 */           { CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.packet;
/* 461 */             if ((mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP || mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) && 
/* 462 */               mc.field_71474_y.field_74313_G.func_151470_d()) {
/* 463 */               placedCrystals.add(packet.func_187023_a());
/*     */             } }
/*     */           
/* 466 */           else if (event.packet instanceof net.minecraft.network.play.server.SPacketExplosion)
/* 467 */           { event.cancel(); } 
/*     */         }new java.util.function.Predicate[0]); } private boolean validateCrystal(EntityEnderCrystal e) { if (e == null || e.field_70128_L)
/*     */       return false;  if (this.attackedCrystals.containsKey(e) && ((Integer)this.attackedCrystals.get(e)).intValue() > 5)
/*     */       return false;  if (e.func_70032_d(mc.field_71439_g) > (!mc.field_71439_g.func_70685_l(e) ? wallsRange.doubleValue() : breakRadius.doubleValue()))
/*     */       return false;  float selfDamage = CrystalUtil.calculateDamage(new Vec3d(e.field_70165_t, e.field_70163_u, e.field_70161_v), mc.field_71439_g); if (selfDamage > maxSelfDmg.doubleValue() || (noSuicide.booleanValue() && selfDamage >= mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj()))
/*     */       return false;  if (breakMode.stringValue().equals("OnlyOwn"))
/* 473 */       return placedCrystals.contains(e.func_180425_c().func_177982_a(0, -1, 0));  return true; } public EntityEnderCrystal getNearestCrystalTo(Entity entity) { return (EntityEnderCrystal)mc.field_71441_e.func_72910_y().stream().filter(e -> (e instanceof EntityEnderCrystal && validateCrystal((EntityEnderCrystal)e))).map(e -> (EntityEnderCrystal)e).min(Comparator.comparing(e -> Float.valueOf(entity.func_70032_d(e)))).orElse(null); } public void onRenderWorld(float partialTicks) { BlockPos pos = this.placePos;
/* 474 */     Color color = Color.GREEN;
/* 475 */     if (pos == null) {
/* 476 */       pos = this.breakPos;
/* 477 */       color = Color.RED;
/*     */     } 
/*     */     
/* 480 */     if (pos != null) {
/* 481 */       if (boundingBox.booleanValue()) {
/* 482 */         RenderUtil.drawBoundingBox(RenderUtil.getBB(pos, 1), 1.0F, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */       }
/*     */       
/* 485 */       if (renderFillBox.booleanValue())
/* 486 */         RenderUtil.drawFilledBox(RenderUtil.getBB(pos, 1), renderFillBoxColor.intValue()); 
/*     */     }  } public void addAttackedCrystal(EntityEnderCrystal crystal) {
/*     */     if (this.attackedCrystals.containsKey(crystal)) {
/*     */       int value = ((Integer)this.attackedCrystals.get(crystal)).intValue();
/*     */       this.attackedCrystals.put(crystal, Integer.valueOf(value + 1));
/*     */     } else {
/*     */       this.attackedCrystals.put(crystal, Integer.valueOf(1));
/*     */     } 
/*     */   } public static EnumFacing getFacing(BlockPos pos) {
/* 495 */     double closest = 2.147483647E9D;
/* 496 */     EnumFacing best = null;
/* 497 */     for (EnumFacing facing : EnumFacing.values()) {
/* 498 */       Vec3d vec = (new Vec3d(pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D));
/* 499 */       BlockPos neighbor = pos.func_177972_a(facing);
/*     */       
/* 501 */       if (!isSolid(neighbor)) {
/* 502 */         double distance = vec.func_72438_d(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v));
/* 503 */         if (best == null || distance < closest) {
/* 504 */           closest = distance;
/* 505 */           best = facing;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 510 */     if (best != null) {
/* 511 */       return best;
/*     */     }
/* 513 */     RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177956_o() - 0.5D, pos.func_177952_p() + 0.5D));
/* 514 */     if (result != null && result.func_178782_a() != null) {
/* 515 */       return result.field_178784_b;
/*     */     }
/* 517 */     return EnumFacing.UP; } private boolean verifyCrystalBlocks(BlockPos pos) { if (mc.field_71439_g.func_174818_b(pos) > placeRadius.doubleValue() * placeRadius.doubleValue())
/*     */       return false;  if (wallsRange.doubleValue() > 0.0D && !BlockUtil.canSeePos(pos) && pos.func_185332_f((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v) > wallsRange.doubleValue())
/*     */       return false;  float selfDamage = CrystalUtil.calculateDamage(pos, mc.field_71439_g); if (selfDamage > maxSelfDmg.doubleValue())
/*     */       return false;  if (noSuicide.booleanValue() && selfDamage >= mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj())
/*     */       return false; 
/*     */     return true; }
/* 523 */   public void rotate(Vec3d vec) { if (extraRotatePackets.booleanValue()) {
/* 524 */       RotationUtil.rotateSpoof(vec);
/*     */     } else {
/* 526 */       RotationUtil.rotateSpoofNoPacket(vec);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/* 531 */   public boolean needPause() { return false; }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*     */     if (mc.field_71439_g == null)
/*     */       return; 
/*     */     if ((pauseIfHittingBlock.booleanValue() && mc.field_71442_b.field_78778_j) || (pauseWhileEating.booleanValue() && mc.field_71439_g.func_184587_cr() && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemFood)) {
/*     */       if (this.rotating) {
/*     */         this.rotating = false;
/*     */         this.breakPos = null;
/*     */         this.placePos = null;
/*     */         RotationUtil.stopRotating();
/*     */       } 
/*     */       return;
/*     */     } 
/*     */     if (this.checkPlacedTimer.hasPassed(1000)) {
/*     */       ArrayList<BlockPos> temp = new ArrayList<BlockPos>();
/*     */       temp.addAll(placedCrystals);
/*     */       for (BlockPos pos : temp) {
/*     */         if (CrystalUtil.getCrystalInPos(pos) == null || BlockUtil.distance(getPlayerPos(), pos) > 15)
/*     */           placedCrystals.remove(pos); 
/*     */       } 
/*     */       this.checkPlacedTimer.reset();
/*     */     } 
/*     */     if (this.rotating && this.lastPlaceOrBreak.hasPassed(500)) {
/*     */       this.rotating = false;
/*     */       this.placePos = null;
/*     */       this.breakPos = null;
/*     */       RotationUtil.stopRotating();
/*     */     } 
/*     */     if (removeVisualTimer.hasPassed(1000)) {
/*     */       removeVisualTimer.reset();
/*     */       this.attackedCrystals.clear();
/*     */     } 
/*     */     if (this.remainingTicks > 0) {
/*     */       this.remainingTicks--;
/*     */       return;
/*     */     } 
/*     */     if (needPause()) {
/*     */       this.remainingTicks = 0;
/*     */       return;
/*     */     } 
/*     */     this.remainingTicks = ticks.intValue();
/*     */     List<BlockPos> cachedCrystalBlocks = (List)CrystalUtil.findCrystalBlocks(mc.field_71439_g, (float)placeRadius.doubleValue()).stream().filter(pos -> verifyCrystalBlocks(pos)).collect(Collectors.toList());
/*     */     if (!cachedCrystalBlocks.isEmpty()) {
/*     */       float damage = -2.14748365E9F;
/*     */       EntityLivingBase target = null;
/*     */       for (Entity entity2 : mc.field_71441_e.field_72996_f) {
/*     */         EntityLivingBase entity = null;
/*     */         try {
/*     */           entity = (EntityLivingBase)entity2;
/*     */         } catch (ClassCastException ex) {
/*     */           continue;
/*     */         } 
/*     */         if (entity.equals(mc.field_71439_g) || entity.equals(mc.field_175622_Z) || entity.field_70128_L || entity.func_110143_aJ() + entity.func_110139_bj() <= 0.0F)
/*     */           continue; 
/*     */         if ((entity instanceof net.minecraft.entity.player.EntityPlayer && !players.booleanValue()) || (entity instanceof net.minecraft.entity.player.EntityPlayer && Friends.isFriend(entity)) || (EntityUtil.isHostileMob(entity) && !monsters.booleanValue()) || (EntityUtil.isNeutralMob(entity) && !neutrals.booleanValue()) || (EntityUtil.isPassive(entity) && !passive.booleanValue()))
/*     */           continue; 
/*     */         if (BlockUtil.distance(getPlayerPos(), entity.func_180425_c()) > 22)
/*     */           continue; 
/*     */         double minDamage = minDmg.doubleValue();
/*     */         if ((entity.func_110143_aJ() + entity.func_110139_bj()) <= facePlace.doubleValue())
/*     */           minDamage = 1.0D; 
/*     */         for (BlockPos pos : cachedCrystalBlocks) {
/*     */           float calculatedDamage = CrystalUtil.calculateDamage(pos, entity);
/*     */           if (calculatedDamage >= minDamage && calculatedDamage > damage) {
/*     */             damage = calculatedDamage;
/*     */             if (!this.placeLocations.contains(pos))
/*     */               this.placeLocations.add(pos); 
/*     */             target = entity;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       if (target != null) {
/*     */         if (target.field_70128_L || target.func_110143_aJ() + target.func_110139_bj() <= 0.0F)
/*     */           return; 
/*     */         if (!this.placeLocations.isEmpty()) {
/*     */           double minDamage = minDmg.doubleValue();
/*     */           if ((target.func_110143_aJ() + target.func_110139_bj()) <= facePlace.doubleValue())
/*     */             minDamage = 1.0D; 
/*     */           for (BlockPos pos : this.placeLocations) {
/*     */             float calculatedDamage = CrystalUtil.calculateDamage(pos, target);
/*     */             if (calculatedDamage < minDamage)
/*     */               this.placeLocations.remove(pos); 
/*     */           } 
/*     */           Collections.reverse(this.placeLocations);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     EntityEnderCrystal crystal = getNearestCrystalTo(mc.field_71439_g);
/*     */     boolean isValidCrystal = (crystal != null) ? ((mc.field_71439_g.func_70032_d(crystal) < breakRadius.doubleValue())) : false;
/*     */     if (!isValidCrystal && this.placeLocations.isEmpty()) {
/*     */       this.remainingTicks = 0;
/*     */       return;
/*     */     } 
/*     */     if (isValidCrystal) {
/*     */       if (antiWeakness.booleanValue() && mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
/*     */         boolean hasStrenght2 = false;
/*     */         for (PotionEffect effect : mc.field_71439_g.func_70651_bq()) {
/*     */           if (effect.func_76453_d().contains("damageBoost") && effect.func_76458_c() == 1) {
/*     */             hasStrenght2 = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         if ((!hasStrenght2 && mc.field_71439_g.func_184614_ca() == ItemStack.field_190927_a) || (!hasStrenght2 && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemSword) && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemTool)))
/*     */           for (int i = 0; i < 9; i++) {
/*     */             ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */             if (!stack.func_190926_b())
/*     */               if (stack.func_77973_b() instanceof net.minecraft.item.ItemTool || stack.func_77973_b() instanceof net.minecraft.item.ItemSword) {
/*     */                 mc.field_71439_g.field_71071_by.field_70461_c = i;
/*     */                 mc.field_71442_b.func_78765_e();
/*     */                 break;
/*     */               }  
/*     */           }  
/*     */       } 
/*     */       rotate(new Vec3d(crystal.field_70165_t + 0.5D, crystal.field_70163_u + 0.5D, crystal.field_70161_v + 0.5D));
/*     */       this.rotating = true;
/*     */       this.breakPos = crystal.func_180425_c().func_177982_a(0, -1, 0);
/*     */       this.placePos = null;
/*     */       this.lastPlaceOrBreak.reset();
/*     */       mc.field_71442_b.func_78764_a(mc.field_71439_g, crystal);
/*     */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */       addAttackedCrystal(crystal);
/*     */       if (!multiPlace.booleanValue())
/*     */         return; 
/*     */     } 
/*     */     if (!this.placeLocations.isEmpty()) {
/*     */       BlockPos selectedPos = null;
/*     */       for (BlockPos pos : this.placeLocations) {
/*     */         if (CrystalUtil.canPlaceCrystal(pos)) {
/*     */           selectedPos = pos;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       if (selectedPos == null) {
/*     */         this.remainingTicks = 0;
/*     */         return;
/*     */       } 
/*     */       if (autoSwitch.booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_185158_cP && mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP)
/*     */         if (autoSwitchReplace.booleanValue() && InventoryUtil.hasItem(Items.field_185158_cP)) {
/*     */           InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_185158_cP), false);
/*     */           mc.field_71442_b.func_78765_e();
/*     */         } else if (InventoryUtil.hasHotbarItem(Items.field_185158_cP)) {
/*     */           InventoryUtil.switchItem(InventoryUtil.getSlotInHotbar(Items.field_185158_cP), false);
/*     */           mc.field_71442_b.func_78765_e();
/*     */         } else {
/*     */           return;
/*     */         }  
/*     */       rotate(new Vec3d(selectedPos.func_177958_n() + 0.5D, selectedPos.func_177956_o() + 0.5D, selectedPos.func_177952_p() + 0.5D));
/*     */       this.rotating = true;
/*     */       this.placePos = selectedPos;
/*     */       this.breakPos = null;
/*     */       this.lastPlaceOrBreak.reset();
/*     */       mc.func_147114_u().func_147297_a(new CPacketPlayerTryUseItemOnBlock(selectedPos, getFacing(selectedPos), (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
/*     */       placedCrystals.add(selectedPos);
/*     */       this.placeLocations.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\AutoCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */