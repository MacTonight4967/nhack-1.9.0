/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*     */ import me.bebeli555.cookieclient.utils.CrystalUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ public class PistonAura
/*     */   extends Mod
/*     */ {
/*  26 */   private Timer breakTimer = new Timer();
/*     */   private EnumFacing placed;
/*     */   private int counter;
/*     */   private int ticksCounter;
/*  30 */   public static Setting breakDelay = new Setting(Mode.INTEGER, "BreakDelay", Integer.valueOf(250), new String[] { "How long to wait in ms when breaking the crystal" });
/*  31 */   public static Setting waitTicks = new Setting(Mode.INTEGER, "WaitTicks", Integer.valueOf(30), new String[] { "How many ticks to wait until the old place", "Position is free again" });
/*  32 */   public static Setting ticks = new Setting(Mode.INTEGER, "Ticks", Integer.valueOf(2), new String[] { "How many ticks to ignore" });
/*     */ 
/*     */   
/*  35 */   public PistonAura() { super(Group.COMBAT, "PistonAura", new String[] { "Places crystals and pushes them with pistons", "To do full damage to players that are sitting in a hole", "Note: To get the pistons placed with right direction", "You need to be at the edge of the block", "Next to ur target", "You need piston, redstone torch or block and crystals" }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisabled() {
/*  40 */     this.placed = null;
/*  41 */     this.ticksCounter = 0;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*  46 */     EntityPlayer target = PlayerUtil.getClosestEnemy();
/*  47 */     if (mc.field_71439_g == null || target == null) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     this.ticksCounter++;
/*  52 */     if (this.ticksCounter <= ticks.intValue())
/*  53 */       return;  this.ticksCounter = 0;
/*     */ 
/*     */     
/*  56 */     EntityEnderCrystal crystal = CrystalUtil.getCrystalInPos(new BlockPos(target.field_70165_t, target.field_70163_u + 1.0D, target.field_70161_v));
/*  57 */     if (crystal != null) {
/*  58 */       if (this.breakTimer.hasPassed(breakDelay.intValue())) {
/*  59 */         mc.field_71442_b.func_78764_a(mc.field_71439_g, crystal);
/*  60 */         this.breakTimer.reset();
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  67 */     if (this.placed != null && !canPlace(new BlockPos(target.field_70165_t, target.field_70163_u, target.field_70161_v), this.placed)) {
/*  68 */       this.counter++;
/*  69 */       int ticks2 = 1;
/*  70 */       if (ticks.intValue() > 0) ticks2 = ticks.intValue(); 
/*  71 */       if (this.counter > waitTicks.intValue() / ticks2) {
/*  72 */         this.counter = 0;
/*  73 */         this.placed = null;
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  80 */     place(target);
/*     */   }
/*     */   
/*     */   public void place(EntityPlayer player) {
/*  84 */     EnumFacing closest = null;
/*  85 */     double lowestDistance = 2.147483647E9D;
/*  86 */     BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
/*     */     
/*  88 */     for (EnumFacing facing : EnumFacing.values()) {
/*  89 */       if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
/*     */       {
/*     */ 
/*     */         
/*  93 */         if ((closest == null || BlockUtil.distance(getPlayerPos(), pos.func_177972_a(facing)) < lowestDistance) && 
/*  94 */           canPlace(pos, facing)) {
/*  95 */           lowestDistance = BlockUtil.distance(getPlayerPos(), pos.func_177972_a(facing));
/*  96 */           closest = facing;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 101 */     if (closest != null && InventoryUtil.hasItem(Items.field_185158_cP) && getPiston() != null && getRedstone() != null) {
/* 102 */       BlockUtil.placeBlockOnThisThread(getPiston(), pos.func_177972_a(closest).func_177972_a(closest).func_177982_a(0, 1, 0), true);
/* 103 */       BlockUtil.placeItemOnThisThread(Items.field_185158_cP, pos.func_177972_a(closest), true);
/* 104 */       BlockUtil.placeBlockOnThisThread(getRedstone(), pos.func_177972_a(closest).func_177972_a(closest).func_177972_a(closest).func_177982_a(0, 1, 0), true);
/* 105 */       this.placed = closest;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 110 */   public static boolean canPlace(BlockPos pos, EnumFacing facing) { return (CrystalUtil.canPlaceCrystal(pos.func_177972_a(facing)) && BlockUtil.canPlaceBlock(pos.func_177972_a(facing).func_177972_a(facing).func_177982_a(0, 1, 0)) && BlockUtil.canPlaceBlock(pos.func_177972_a(facing).func_177972_a(facing).func_177972_a(facing).func_177982_a(0, 1, 0))); }
/*     */ 
/*     */   
/*     */   public static Block getPiston() {
/* 114 */     if (InventoryUtil.hasBlock(Blocks.field_150331_J))
/* 115 */       return Blocks.field_150331_J; 
/* 116 */     if (InventoryUtil.hasBlock(Blocks.field_150320_F)) {
/* 117 */       return Blocks.field_150320_F;
/*     */     }
/*     */     
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   public static Block getRedstone() {
/* 124 */     if (InventoryUtil.hasBlock(Blocks.field_150429_aA))
/* 125 */       return Blocks.field_150429_aA; 
/* 126 */     if (InventoryUtil.hasBlock(Blocks.field_150451_bX)) {
/* 127 */       return Blocks.field_150451_bX;
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\PistonAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */