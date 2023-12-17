/*     */ package me.bebeli555.cookieclient.mods.bots.elytrabot;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.combat.Surround;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoMend;
/*     */ import me.bebeli555.cookieclient.rendering.RenderPath;
/*     */ import me.bebeli555.cookieclient.utils.BaritoneUtil;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.EatingUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.MiningUtil;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class ElytraBot extends Mod {
/*     */   private static Thread thread;
/*     */   private static ArrayList<BlockPos> path;
/*     */   private static BlockPos goal;
/*     */   private static BlockPos previous;
/*     */   private static BlockPos lastSecondPos;
/*     */   private static Direction direction;
/*     */   private static int x;
/*     */   private static int z;
/*  47 */   private static double jumpY = -1.0D; private static int packetsSent; private static int lagbackCounter;
/*     */   private static int useBaritoneCounter;
/*     */   private static boolean lagback;
/*     */   private static double blocksPerSecond;
/*     */   private static int blocksPerSecondCounter;
/*  52 */   private static Timer blocksPerSecondTimer = new Timer();
/*  53 */   private static Timer packetTimer = new Timer();
/*  54 */   private static Timer fireworkTimer = new Timer();
/*  55 */   private static Timer takeoffTimer = new Timer();
/*     */   
/*  57 */   public static Setting mode = new Setting(null, "Mode", "Highway", new String[][] { { "Overworld", "Pathfinding designed for overworld" }, { "Highway", "Pathfinding designed for the highways." }, { "Tunnel", "For 2x1 tunnels", "Also mines above block if its preventing takeoff" } });
/*  58 */   public static Setting useBaritone = new Setting(mode, "Highway", Mode.BOOLEAN, "UseBaritone", Boolean.valueOf(true), new String[] { "Uses baritone to walk a bit if stuck or cant find a path", "Then tries to takeoff again" });
/*  59 */   public static Setting useBaritoneBlocks = new Setting(useBaritone, Mode.INTEGER, "Blocks", Integer.valueOf(20), new String[] { "Amount of blocks to walk from current position" });
/*  60 */   public static Setting takeoffMode = new Setting(null, "Takeoff", "SlowGlide", new String[][] { { "PacketFly", "Uses packetfly for takeoff" }, { "Jump", "Just jumps and tries to open the elytra", "Only works if the server has really good tps" }, { "SlowGlide", "Glides slowly down giving more time to open elytra" } });
/*  61 */   public static Setting flyMode = new Setting(null, "FlyMode", "ElytraFly", new String[][] { { "Firework", "Uses fireworks and rotates head to traverse the path" }, { "ElytraFly", "Uses elytrafly to traverse the path" } });
/*  62 */   public static Setting elytraFlySpeed = new Setting(flyMode, "ElytraFly", Mode.DOUBLE, "Speed", Double.valueOf(1.81D), new String[] { "Speed for elytrafly" });
/*  63 */   public static Setting elytraFlyManuverSpeed = new Setting(flyMode, "ElytraFly", Mode.DOUBLE, "ManuverSpeed", Double.valueOf(1.0D), new String[] { "Speed used for manuvering", "It has to be lower because it would", "Go off the target pos with too high speed" });
/*  64 */   public static Setting fireworkDelay = new Setting(flyMode, "Firework", Mode.DOUBLE, "FireworkDelay", Double.valueOf(2.8D), new String[] { "Delay between the clicks on the fireworks", "In seconds" });
/*  65 */   public static Setting pathfinding = new Setting(Mode.LABEL, "Pathfinding", Boolean.valueOf(true), new String[] { "Settings about the pathfinding" });
/*  66 */   public static Setting avoidLava = new Setting(pathfinding, Mode.BOOLEAN, "AvoidLava", Boolean.valueOf(false), new String[] { "Avoids lava" });
/*  67 */   public static Setting maxY = new Setting(pathfinding, Mode.INTEGER, "MaxY", "", new String[] { "Max Y-Coordinate the pathfinding can go to" });
/*  68 */   public static Setting coordinates = new Setting(Mode.BOOLEAN, "Coordinates", Boolean.valueOf(false), new String[] { "If false then it will just start going straight", "To the direction you are looking at", "If true then it will go to the given coordinates below" });
/*  69 */   public static Setting gotoX = new Setting(coordinates, Mode.INTEGER, "X", "", new String[] { "X-Coordinate where the bot will try to go" });
/*  70 */   public static Setting gotoZ = new Setting(coordinates, Mode.INTEGER, "Z", "", new String[] { "Z-Coordinate where the bot will try to go" });
/*  71 */   public static Setting autoSwitch = new Setting(Mode.BOOLEAN, "AutoSwitch", Boolean.valueOf(true), new String[] { "Switches equipped low durability elytra with a new one" });
/*  72 */   public static Setting autoEat = new Setting(Mode.BOOLEAN, "AutoEat", Boolean.valueOf(true), new String[] { "Eats gaps or other food when hunger is low or health is low" });
/*  73 */   public static Setting minHealth = new Setting(autoEat, Mode.INTEGER, "Health", Integer.valueOf(10), new String[] { "When health goes below or equal to the given amount it will eat food" });
/*  74 */   public static Setting minHunger = new Setting(autoEat, Mode.INTEGER, "Hunger", Integer.valueOf(10), new String[] { "When hunger goes below or equal to the given amount it will eat food" });
/*  75 */   public static Setting gaps = new Setting(autoEat, Mode.BOOLEAN, "Gaps", Boolean.valueOf(true), new String[] { "Allows the bot to eat gapples" });
/*  76 */   public static Setting toggleOnPop = new Setting(Mode.BOOLEAN, "ToggleOnPop", Boolean.valueOf(false), new String[] { "Toggles the module off", "When you pop a totem" }); @EventHandler
/*     */   private Listener<PacketEvent> packetEvent;
/*     */   
/*  79 */   public ElytraBot() { super(Group.BOTS, "ElytraBot", new String[] { "Pathfinding bot for elytras" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 493 */     this.packetEvent = new Listener(event -> {
/*     */           
/* 495 */           if (event.packet instanceof SPacketEntityStatus)
/* 496 */           { SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/*     */             
/* 498 */             if (packet.func_149160_c() == 35 && packet.func_149161_a(mc.field_71441_e) == mc.field_71439_g && toggleOnPop.booleanValue())
/* 499 */             { sendMessage("You popped a totem.", false);
/* 500 */               disable(); }  }  }new java.util.function.Predicate[0]); } public void onEnabled() { int up = 1; if (!coordinates.booleanValue()) { if (Math.abs(Math.abs(mc.field_71439_g.field_70165_t) - Math.abs(mc.field_71439_g.field_70161_v)) <= 5.0D && Math.abs(mc.field_71439_g.field_70165_t) > 10.0D && Math.abs(mc.field_71439_g.field_70161_v) > 10.0D && mode.stringValue().equals("Highway")) { direction = Direction.getDiagonalDirection(); } else { direction = Direction.getDirection(); }  goal = generateGoalFromDirection(direction, up); } else { x = gotoX.intValue(); z = gotoZ.intValue(); goal = new BlockPos(gotoX.intValue(), mc.field_71439_g.field_70163_u + up, gotoZ.intValue()); }
/*     */      thread = new Thread() { public void run() { while (ElytraBot.access$000() != null && ElytraBot.access$000().equals(this)) { try { ElytraBot.this.loop(); }
/*     */             catch (NullPointerException nullPointerException) {} Mod.sleep(50); }
/*     */            } }
/*     */       ; blocksPerSecondTimer.reset(); Mod.EVENT_BUS.subscribe(this); thread.start(); }
/*     */   public void onDisabled() { Mod.EVENT_BUS.unsubscribe(this); direction = null; path = null; useBaritoneCounter = 0; lagback = false; lagbackCounter = 0; blocksPerSecond = 0.0D; blocksPerSecondCounter = 0; lastSecondPos = null; jumpY = -1.0D; RenderPath.clearPath(); PacketFly.toggle(false); ElytraFly.toggle(false); clearStatus(); BaritoneUtil.forceCancel(); suspend(thread); thread = null; }
/* 506 */   public static void useBaritone() { ElytraFly.toggle(false);
/* 507 */     y = (int)(jumpY - mc.field_71439_g.field_70163_u);
/* 508 */     int x = 0;
/* 509 */     int z = 0;
/*     */     
/* 511 */     int blocks = useBaritoneBlocks.intValue();
/* 512 */     if (direction == Direction.ZM) {
/* 513 */       z = -blocks;
/* 514 */     } else if (direction == Direction.XM) {
/* 515 */       x = -blocks;
/* 516 */     } else if (direction == Direction.XP) {
/* 517 */       x = blocks;
/* 518 */     } else if (direction == Direction.ZP) {
/* 519 */       z = blocks;
/* 520 */     } else if (direction == Direction.XP_ZP) {
/* 521 */       x = blocks;
/* 522 */       z = blocks;
/* 523 */     } else if (direction == Direction.XM_ZM) {
/* 524 */       x = -blocks;
/* 525 */       z = -blocks;
/* 526 */     } else if (direction == Direction.XP_ZM) {
/* 527 */       x = blocks;
/* 528 */       z = -blocks;
/* 529 */     } else if (direction == Direction.XM_ZP) {
/* 530 */       x = -blocks;
/* 531 */       z = blocks;
/*     */     } 
/*     */     
/* 534 */     BaritoneUtil.walkTo(getPlayerPos().func_177982_a(x, y, z), true);
/* 535 */     sleep(5000);
/* 536 */     sleepUntil(() -> !BaritoneUtil.isPathing(), 120000);
/* 537 */     BaritoneUtil.forceCancel(); }
/*     */ 
/*     */   
/*     */   public static void clickOnFirework() {
/* 541 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151152_bP) {
/* 542 */       InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151152_bP), false);
/*     */     }
/*     */ 
/*     */     
/* 546 */     mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, EnumHand.MAIN_HAND);
/* 547 */     fireworkTimer.reset(); } public void loop() { if (mc.field_71439_g == null) return;  if (BlockUtil.distance(getPlayerPos(), goal) < 15) { mc.field_71441_e.func_184156_a(getPlayerPos(), SoundEvents.field_187802_ec, SoundCategory.AMBIENT, 100.0F, 18.0F, true); sendMessage("Goal reached!", false); disable(); return; }  if (InventoryUtil.getItemStack(38).func_77973_b() != Items.field_185160_cR) if (InventoryUtil.hasItem(Items.field_185160_cR)) { int elytraSlot = InventoryUtil.getSlot(Items.field_185160_cR); InventoryUtil.clickSlot(elytraSlot); InventoryUtil.clickSlot(38); InventoryUtil.clickSlot(elytraSlot); } else { sendMessage("You need an elytra", true); disable(); return; }   if (flyMode.stringValue().equals("Firework") && !InventoryUtil.hasItem(Items.field_151152_bP)) { sendMessage("You need fireworks as your using firework mode", true); disable(); return; }  if (!BlockUtil.isInRenderDistance(getPlayerPos())) { setStatus("We are in unloaded chunk. Waiting"); mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D); return; }  if (autoSwitch.booleanValue()) { ItemStack elytra = InventoryUtil.getItemStack(38); if (AutoMend.getDurability(elytra) <= 15) for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) { if (itemStack.itemStack.func_77973_b() == Items.field_185160_cR && AutoMend.getDurability(itemStack.itemStack) >= 100) { InventoryUtil.clickSlot(itemStack.slotId); InventoryUtil.clickSlot(38); InventoryUtil.clickSlot(itemStack.slotId); break; }  }   }  double preventPhase = jumpY + 0.6D; if ((mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70163_u < preventPhase || mc.field_71439_g.field_70122_E) && PacketFly.toggled) { sleep(1500); if (mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70163_u < preventPhase || mc.field_71439_g.field_70122_E) { PacketFly.toggle(false); sleep(100); }  }  if (!mc.field_71439_g.func_184613_cA()) { ElytraFly.toggle(false); if (mc.field_71439_g.field_70122_E && isSolid(getPlayerPos().func_177982_a(0, 2, 0)) && useBaritone.booleanValue() && mode.stringValue().equals("Highway")) { setStatus("Using baritone because a block above is preventing takeoff"); useBaritone(); }  if (isSolid(getPlayerPos().func_177982_a(0, 2, 0)) && mode.stringValue().equals("Tunnel")) if (getBlock(getPlayerPos().func_177982_a(false, 2, false)) != Blocks.field_150357_h) { setStatus("Mining above block so we can takeoff"); Surround.centerMotionFull(); MiningUtil.mineAnyway(getPlayerPos().func_177982_a(0, 2, 0), false); } else if (useBaritone.booleanValue()) { setStatus("Using baritone to walk because above block is bedrock"); useBaritone(); } else { sendMessage("Above block is bedrock and usebaritone is false", true); disable(); return; }   if (jumpY != -1.0D && Math.abs(mc.field_71439_g.field_70163_u - jumpY) >= 2.0D && useBaritone.booleanValue() && direction != null && mode.stringValue().equals("Highway")) { setStatus("Using baritone to get back to the highway"); useBaritone(); }  if (packetsSent < 20) setStatus("Trying to takeoff");  fireworkTimer.ms = 0L; if (mc.field_71439_g.field_70122_E) { jumpY = mc.field_71439_g.field_70163_u; generatePath(); mc.field_71439_g.func_70664_aZ(); } else if (mc.field_71439_g.field_70163_u < mc.field_71439_g.field_70137_T) { if (takeoffMode.stringValue().equals("PacketFly")) { if (mc.field_71439_g.field_70163_u > preventPhase && !PacketFly.toggled) PacketFly.toggle(true);  } else if (takeoffMode.stringValue().equals("SlowGlide")) { mc.field_71439_g.func_70016_h(0.0D, -0.04D, 0.0D); }  if (packetsSent <= 15) { if (takeoffTimer.hasPassed(650)) { mc.func_147114_u().func_147297_a(new CPacketEntityAction(mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING)); takeoffTimer.reset(); packetTimer.reset(); packetsSent++; }  } else if (packetTimer.hasPassed(15000)) { packetsSent = 0; } else { setStatus("Waiting for 15s before sending elytra open packets again"); }  }  return; }  if (!PacketFly.toggled) { packetsSent = 0; double speed = PlayerUtil.getSpeed(mc.field_71439_g); if (speed < 0.1D) { useBaritoneCounter++; if (useBaritoneCounter >= 15) { useBaritoneCounter = 0; if (useBaritone.booleanValue()) { setStatus("Using baritone to walk a bit because we are stuck"); useBaritone(); } else { sendMessage("We are stuck. UseBaritone setting would help", true); disable(); return; }  }  } else { useBaritoneCounter = 0; }  if (flyMode.stringValue().equals("Firework")) { if (speed > 3.0D) lagback = true;  if (lagback) if (speed < 1.0D) { lagbackCounter++; if (lagbackCounter > 3) { lagback = false; lagbackCounter = 0; }  } else { lagbackCounter = 0; }   if (fireworkTimer.hasPassed((int)(fireworkDelay.doubleValue() * 1000.0D)) && !lagback) clickOnFirework();  }  }  if (autoEat.booleanValue() && !EatingUtil.isEating() && (!flyMode.stringValue().equals("Firework") || (flyMode.stringValue().equals("Firework") && !fireworkTimer.hasPassed(100)))) { float health = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj(); float hunger = mc.field_71439_g.func_71024_bL().func_75116_a(); if (health <= minHealth.intValue() || hunger <= minHunger.intValue()) { Item eat = null; for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) { Item item = itemStack.itemStack.func_77973_b(); if (item instanceof net.minecraft.item.ItemFood) { if (item == Items.field_151153_ao && gaps.booleanValue()) { eat = item; break; }  if (item != Items.field_185161_cS && item != Items.field_151070_bp) eat = item;  }  }  EatingUtil.eatItem(eat, false); }  }  if (path == null || path.size() <= 20 || isNextPathTooFar()) generatePath();  int distance = 12; if (mode.stringValue().equals("Highway") || flyMode.stringValue().equals("ElytraFly")) distance = 2;  boolean remove = false; ArrayList<BlockPos> removePositions = new ArrayList<BlockPos>(); for (BlockPos pos : path) { if (!remove && BlockUtil.distance(pos, getPlayerPos()) <= distance)
/*     */         remove = true;  if (remove)
/*     */         removePositions.add(pos);  }  for (BlockPos pos : removePositions) { path.remove(pos); previous = pos; }  if (path.size() > 0) { if (direction != null) { setStatus("Going to " + direction.name); } else { setStatus("Going to X: " + x + " Z: " + z); if (blocksPerSecondTimer.hasPassed(1000)) { blocksPerSecondTimer.reset(); if (lastSecondPos != null) { blocksPerSecondCounter++; blocksPerSecond += BlockUtil.distance(getPlayerPos(), lastSecondPos); }  lastSecondPos = getPlayerPos(); }  int seconds = (int)(BlockUtil.distance(getPlayerPos(), goal) / blocksPerSecond / blocksPerSecondCounter); int h = seconds / 3600; int m = seconds % 3600 / 60; int s = seconds % 60; addToStatus("Estimated arrival in " + ChatFormatting.GOLD + h + "h " + m + "m " + s + "s", 1); if (flyMode.stringValue().equals("Firework"))
/*     */           addToStatus("Estimated fireworks needed: " + ChatFormatting.GOLD + (int)(seconds / fireworkDelay.doubleValue()), 2);  }  if (flyMode.stringValue().equals("Firework")) { RotationUtil.rotate((new Vec3d((Vec3i)path.get(path.size() - 1))).func_72441_c(0.5D, 0.5D, 0.5D), false); } else if (flyMode.stringValue().equals("ElytraFly")) { ElytraFly.toggle(true); BlockPos next = null; if (path.size() > 1)
/* 551 */           next = (BlockPos)path.get(path.size() - 2);  ElytraFly.setMotion((BlockPos)path.get(path.size() - 1), next, previous); }  }  } public static BlockPos generateGoalFromDirection(Direction direction, int up) { if (direction == Direction.ZM)
/* 552 */       return new BlockPos(0.0D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v - 4.2042069E7D); 
/* 553 */     if (direction == Direction.ZP)
/* 554 */       return new BlockPos(0.0D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v + 4.2042069E7D); 
/* 555 */     if (direction == Direction.XM)
/* 556 */       return new BlockPos(mc.field_71439_g.field_70165_t - 4.2042069E7D, mc.field_71439_g.field_70163_u + up, 0.0D); 
/* 557 */     if (direction == Direction.XP)
/* 558 */       return new BlockPos(mc.field_71439_g.field_70165_t + 4.2042069E7D, mc.field_71439_g.field_70163_u + up, 0.0D); 
/* 559 */     if (direction == Direction.XP_ZP)
/* 560 */       return new BlockPos(mc.field_71439_g.field_70165_t + 4.2042069E7D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v + 4.2042069E7D); 
/* 561 */     if (direction == Direction.XM_ZM)
/* 562 */       return new BlockPos(mc.field_71439_g.field_70165_t - 4.2042069E7D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v - 4.2042069E7D); 
/* 563 */     if (direction == Direction.XP_ZM) {
/* 564 */       return new BlockPos(mc.field_71439_g.field_70165_t + 4.2042069E7D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v - 4.2042069E7D);
/*     */     }
/* 566 */     return new BlockPos(mc.field_71439_g.field_70165_t - 4.2042069E7D, mc.field_71439_g.field_70163_u + up, mc.field_71439_g.field_70161_v + 4.2042069E7D); } public void generatePath() { BlockPos[] positions = { new BlockPos(true, false, false), new BlockPos(-1, false, false), new BlockPos(false, false, true), new BlockPos(false, false, -1), new BlockPos(true, false, true), new BlockPos(-1, false, -1), new BlockPos(-1, false, true), new BlockPos(true, false, -1), new BlockPos(false, -1, false), new BlockPos(false, true, false) }; ArrayList<BlockPos> checkPositions = new ArrayList<BlockPos>(); if (mode.stringValue().equals("Highway")) { BlockPos[] list = { new BlockPos(true, false, false), new BlockPos(-1, false, false), new BlockPos(false, false, true), new BlockPos(false, false, -1), new BlockPos(true, false, true), new BlockPos(-1, false, -1), new BlockPos(-1, false, true), new BlockPos(true, false, -1) }; checkPositions = new ArrayList<BlockPos>(Arrays.asList(list)); } else if (mode.stringValue().equals("Overworld")) { int radius = 3; for (int x = -radius; x < radius; x++) { for (int z = -radius; z < radius; z++) { for (int y = radius; y > -radius; y--)
/*     */             checkPositions.add(new BlockPos(x, y, z));  }  }  } else if (mode.stringValue().equals("Tunnel")) { positions = new BlockPos[] { new BlockPos(true, false, false), new BlockPos(-1, false, false), new BlockPos(false, false, true), new BlockPos(false, false, -1) }; checkPositions = new ArrayList<BlockPos>(Arrays.asList(new BlockPos[] { new BlockPos(false, -1, false) })); }  if (path == null || path.size() == 0 || isNextPathTooFar() || mc.field_71439_g.field_70122_E) { BlockPos blockPos; if (mode.stringValue().equals("Overworld")) { blockPos = getPlayerPos().func_177982_a(0, 4, 0); } else if (Math.abs(jumpY - mc.field_71439_g.field_70163_u) <= 2.0D) { blockPos = new BlockPos(mc.field_71439_g.field_70165_t, jumpY + 1.0D, mc.field_71439_g.field_70161_v); } else { blockPos = getPlayerPos().func_177982_a(0, 1, 0); }  if (isNextPathTooFar())
/*     */         blockPos = getPlayerPos();  path = AStar.generatePath(blockPos, goal, positions, checkPositions, 500); }
/*     */     else { ArrayList<BlockPos> temp = AStar.generatePath((BlockPos)path.get(0), goal, positions, checkPositions, 500); try { temp.addAll(path); }
/*     */       catch (NullPointerException nullPointerException) {} path = temp; }
/*     */      RenderPath.setPath(path, new Color('ÿ', false, false, '')); }
/* 572 */   public static boolean isNextPathTooFar() { try { return (BlockUtil.distance(getPlayerPos(), (BlockPos)path.get(path.size() - 1)) > 15); }
/* 573 */     catch (Exception e)
/* 574 */     { return false; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\elytrabot\ElytraBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */