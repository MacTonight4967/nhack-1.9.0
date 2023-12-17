/*     */ package me.bebeli555.cookieclient.mods.world;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.player.TravelEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.movement.SafeWalk;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ public class Scaffold
/*     */   extends Mod
/*     */ {
/*  30 */   private static Timer towerTimer = new Timer(); private static int counter;
/*     */   private static int counter2;
/*  32 */   private static int lastSlot = -1;
/*     */   
/*  34 */   public static Setting safeWalk = new Setting(Mode.BOOLEAN, "UseSafeWalk", Boolean.valueOf(true), new String[] { "Uses safewalk module so you dont fall off" });
/*  35 */   public static Setting tower = new Setting(Mode.BOOLEAN, "Tower", Boolean.valueOf(true), new String[] { "Bridge up fast" });
/*  36 */   public static Setting towerWait = new Setting(tower, Mode.INTEGER, "Wait", Integer.valueOf(500), new String[] { "How long to wait after the server rubberbands you in ms" }); public void onDisabled() { RotationUtil.stopRotating(); SafeWalk.instance.setHiddenOn(false); if (lastSlot != -1) mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;  lastSlot = -1; }
/*  37 */   public static Setting delay = new Setting(Mode.INTEGER, "Delay", Integer.valueOf(3), new String[] { "Delay in ticks to wait after placing block" }); @SubscribeEvent public void onTick(TickEvent.ClientTickEvent e) { if (mc.field_71439_g == null) return;  SafeWalk.instance.setHiddenOn(safeWalk.booleanValue()); counter++; if (counter < delay.intValue())
/*     */       return;  BlockPos pos = getPlayerPos().func_177982_a(0, -1, 0); if (getBlock(pos) == Blocks.field_150350_a && !BlockUtil.canPlaceBlock(pos)) { BlockPos[] list = { getPlayerPos().func_177982_a(1, -1, 0), getPlayerPos().func_177982_a(-1, -1, 0), getPlayerPos().func_177982_a(0, -1, 1), getPlayerPos().func_177982_a(0, -1, -1) }; for (BlockPos pos2 : list) { if (BlockUtil.canPlaceBlock(pos2)) { pos = pos2; break; }  }  }  if (!BlockUtil.canPlaceBlock(pos)) { counter2++; if (counter2 > 40)
/*     */         RotationUtil.stopRotating();  return; }  counter2 = 0; if (isSolid(pos) || !BlockUtil.canPlaceBlock(pos)) { RotationUtil.stopRotating(); return; }  int slot = getBlockSlot(); if (slot == -1) { sendMessage("You dont have any blocks", true); disable(); return; }  if (mc.field_71439_g.field_71071_by.field_70461_c != slot)
/*  40 */       lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;  BlockUtil.Place place = new BlockUtil.Place(null, Block.func_149634_a(InventoryUtil.getItemStack(slot).func_77973_b()), pos, true); place.dontStopRotating = true; place.rotateSpoofNoPacket = true; place.onTick(null); counter = 0; } public Scaffold() { super(Group.WORLD, "Scaffold", new String[] { "Places blocks under you" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.packetEvent = new Listener(event -> {
/*     */           
/* 127 */           if (event.packet instanceof SPacketPlayerPosLook && mc.field_71439_g != null) {
/* 128 */             event.cancel();
/* 129 */             towerTimer.reset();
/*     */             
/* 131 */             SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.packet;
/* 132 */             mc.field_71439_g.func_70107_b(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e());
/*     */             
/* 134 */             mc.func_147114_u().func_147297_a(new CPacketConfirmTeleport(packet.func_186965_f()));
/* 135 */             mc.func_147114_u().func_147297_a(new CPacketPlayer.PositionRotation(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e(), packet.func_148931_f(), packet.func_148930_g(), false));
/*     */           } 
/*     */         }new java.util.function.Predicate[0]); }
/*     */   
/*     */   public static int getBlockSlot() {
/* 140 */     if (isValidBlock(mc.field_71439_g.func_184614_ca().func_77973_b())) {
/* 141 */       return mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*     */ 
/*     */     
/* 145 */     for (i = 0; i < 9; i++) {
/* 146 */       ItemStack itemStack = InventoryUtil.getItemStack(i);
/*     */       
/* 148 */       if (isValidBlock(itemStack.func_77973_b())) {
/* 149 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 154 */     for (InventoryUtil.ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
/* 155 */       if (isValidBlock(itemStack.itemStack.func_77973_b())) {
/* 156 */         return itemStack.slotId;
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return -1; } @EventHandler private Listener<TravelEvent> travelEvent = new Listener(event -> { if (tower.booleanValue() && mc.field_71474_y.field_74314_A.func_151470_d() && isSolid(getPlayerPos().func_177982_a(0, -2, 0)))
/*     */           if (towerTimer.hasPassed(towerWait.intValue())) { if (!mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70163_u - Math.floor(mc.field_71439_g.field_70163_u) <= 0.1D)
/*     */               mc.field_71439_g.field_70181_x = 0.41999998688697815D;  } else if (mc.field_71439_g.field_70143_R <= 2.0F) { mc.field_71439_g.field_70181_x = -0.169D; }
/*     */            
/* 164 */       }new java.util.function.Predicate[0]); @EventHandler private Listener<PacketEvent> packetEvent; public static boolean isValidBlock(Item item) { return (item instanceof net.minecraft.item.ItemBlock && Block.func_149634_a(item) != Blocks.field_150321_G); }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\Scaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */