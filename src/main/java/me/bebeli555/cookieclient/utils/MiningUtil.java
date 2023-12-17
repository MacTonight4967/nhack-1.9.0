/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class MiningUtil extends Mod {
/*  16 */   public static MiningUtil miningUtil = new MiningUtil();
/*     */   
/*     */   public static EnumFacing facing;
/*     */   public static BlockPos pos;
/*     */   public static boolean start;
/*     */   public static boolean spoofRotation;
/*     */   public static boolean isMining;
/*     */   
/*     */   public static boolean mine(BlockPos pos, boolean spoofRotation) {
/*  25 */     if (!hasPickaxe()) {
/*  26 */       return false;
/*     */     }
/*     */     
/*  29 */     InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151046_w), false);
/*     */     
/*  31 */     if (mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == Items.field_151046_w) {
/*  32 */       MiningUtil.pos = pos;
/*  33 */       facing = getFacing(pos);
/*  34 */       start = true;
/*  35 */       MiningUtil.spoofRotation = spoofRotation;
/*     */       
/*  37 */       isMining = true;
/*  38 */       Mod.EVENT_BUS.subscribe(miningUtil);
/*  39 */       sleepUntil(() -> !isSolid(pos), 15000);
/*  40 */       Mod.EVENT_BUS.unsubscribe(miningUtil);
/*  41 */       isMining = false;
/*  42 */       RotationUtil.stopRotating();
/*  43 */       return !isSolid(pos);
/*     */     } 
/*     */     
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mineAnyway(BlockPos pos, boolean spoofRotation) {
/*  53 */     if (hasPickaxe()) {
/*  54 */       InventoryUtil.switchItem(InventoryUtil.getSlot(Items.field_151046_w), false);
/*     */     }
/*     */     
/*  57 */     MiningUtil.pos = pos;
/*  58 */     facing = getFacing(pos);
/*  59 */     start = true;
/*  60 */     MiningUtil.spoofRotation = spoofRotation;
/*     */     
/*  62 */     isMining = true;
/*  63 */     Mod.EVENT_BUS.subscribe(miningUtil);
/*  64 */     sleepUntil(() -> !isSolid(pos), 15000);
/*  65 */     Mod.EVENT_BUS.unsubscribe(miningUtil);
/*  66 */     isMining = false;
/*  67 */     RotationUtil.stopRotating();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mineWithoutSwitch(BlockPos pos) {
/*  74 */     MiningUtil.pos = pos;
/*  75 */     facing = getFacing(pos);
/*  76 */     start = true;
/*     */     
/*  78 */     Mod.EVENT_BUS.subscribe(miningUtil);
/*  79 */     sleepUntil(() -> (!isSolid(pos) && getBlock(pos) != Blocks.field_150321_G), 6000);
/*  80 */     Mod.EVENT_BUS.unsubscribe(miningUtil);
/*  81 */     return !isSolid(pos);
/*     */   }
/*     */   @EventHandler
/*  84 */   private Listener<PlayerMotionUpdateEvent> onMotionUpdate = new Listener(event -> {
/*     */         
/*     */         try {
/*  87 */           if (mc.field_71439_g == null || getBlock(pos) == Blocks.field_150350_a) {
/*     */             return;
/*     */           }
/*     */           
/*  91 */           if (spoofRotation) {
/*  92 */             RotationUtil.rotateSpoof((new Vec3d(pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D)));
/*     */           } else {
/*  94 */             RotationUtil.rotate((new Vec3d(pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D)), false);
/*     */           } 
/*  96 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */           
/*  98 */           if (start) {
/*  99 */             start = false;
/* 100 */             if (!spoofRotation) RotationUtil.rotate((new Vec3d(pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D)), true); 
/*     */           } else {
/* 102 */             mc.field_71442_b.func_180512_c(pos, facing);
/*     */           } 
/* 104 */         } catch (Exception exception) {}
/*     */       }new java.util.function.Predicate[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static boolean hasPickaxe() { return InventoryUtil.hasItem(Items.field_151046_w); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canMine(BlockPos pos) {
/* 120 */     if (!isSolid(pos) || getBlock(pos) == Blocks.field_150357_h || mc.field_71439_g.func_174818_b(pos) > 7.0D) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     Vec3d start = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/* 125 */     Vec3d end = new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D);
/* 126 */     RayTraceResult result = mc.field_71441_e.func_72933_a(start, end);
/*     */     
/* 128 */     if (result != null && result.func_178782_a().equals(pos)) {
/* 129 */       return true;
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(BlockPos pos) {
/* 136 */     EnumFacing closest = null;
/* 137 */     double lowestDistance = 2.147483647E9D;
/* 138 */     for (EnumFacing facing : EnumFacing.values()) {
/* 139 */       BlockPos neighbor = pos.func_177972_a(facing);
/*     */       
/* 141 */       if (!isSolid(neighbor)) {
/*     */ 
/*     */ 
/*     */         
/* 145 */         double distance = mc.field_71439_g.func_174818_b(neighbor);
/* 146 */         if (distance < lowestDistance) {
/* 147 */           closest = facing;
/* 148 */           lowestDistance = distance;
/*     */         } 
/*     */       } 
/*     */     } 
/* 152 */     return closest;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\MiningUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */