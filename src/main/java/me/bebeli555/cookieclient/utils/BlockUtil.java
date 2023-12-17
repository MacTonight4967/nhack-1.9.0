/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockUtil
/*     */   extends Mod
/*     */ {
/*     */   public static BlockPos findBlock(Block block, int radius) {
/*  33 */     for (int x = (int)(mc.field_71439_g.field_70165_t - radius); x < mc.field_71439_g.field_70165_t + radius; x++) {
/*  34 */       for (int z = (int)(mc.field_71439_g.field_70161_v - radius); z < mc.field_71439_g.field_70161_v + radius; z++) {
/*  35 */         for (int y = (int)(mc.field_71439_g.field_70163_u + radius); y > mc.field_71439_g.field_70163_u - radius; y--) {
/*  36 */           BlockPos pos = new BlockPos(x, y, z);
/*  37 */           if (mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(block)) {
/*  38 */             return pos;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<BlockPos> getAll(int radius) {
/*  51 */     List<BlockPos> list = new ArrayList<BlockPos>();
/*     */     try {
/*  53 */       for (int x = (int)(mc.field_71439_g.field_70165_t - radius); x < mc.field_71439_g.field_70165_t + radius; x++) {
/*  54 */         for (int z = (int)(mc.field_71439_g.field_70161_v - radius); z < mc.field_71439_g.field_70161_v + radius; z++) {
/*  55 */           for (int y = (int)(mc.field_71439_g.field_70163_u + radius); y > mc.field_71439_g.field_70163_u - radius; y--) {
/*  56 */             list.add(new BlockPos(x, y, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       Collections.sort(list, new Comparator<BlockPos>()
/*     */           {
/*     */             public int compare(BlockPos lhs, BlockPos rhs) {
/*  64 */               return (Mod.mc.field_71439_g.func_174818_b(lhs) > Mod.mc.field_71439_g.func_174818_b(rhs)) ? 1 : ((Mod.mc.field_71439_g.func_174818_b(lhs) < Mod.mc.field_71439_g.func_174818_b(rhs)) ? -1 : 0);
/*     */             }
/*     */           });
/*     */       
/*  68 */       return list;
/*  69 */     } catch (Exception e) {
/*  70 */       return list;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ArrayList<BlockPos> getAllNoSort(int radius) {
/*  75 */     ArrayList<BlockPos> list = new ArrayList<BlockPos>();
/*     */     
/*  77 */     for (int x = (int)(mc.field_71439_g.field_70165_t - radius); x < mc.field_71439_g.field_70165_t + radius; x++) {
/*  78 */       for (int z = (int)(mc.field_71439_g.field_70161_v - radius); z < mc.field_71439_g.field_70161_v + radius; z++) {
/*  79 */         for (int y = (int)(mc.field_71439_g.field_70163_u + radius); y > mc.field_71439_g.field_70163_u - radius; y--) {
/*  80 */           list.add(new BlockPos(x, y, z));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<BlockPos> getAll(Vec3d pos, int radius) {
/*  92 */     ArrayList<BlockPos> list = new ArrayList<BlockPos>();
/*     */     
/*  94 */     for (int x = (int)(pos.field_72450_a - radius); x < pos.field_72450_a + radius; x++) {
/*  95 */       for (int z = (int)(pos.field_72449_c - radius); z < pos.field_72449_c + radius; z++) {
/*  96 */         for (int y = (int)(pos.field_72448_b + radius); y > pos.field_72448_b - radius; y--) {
/*  97 */           list.add(new BlockPos(x, y, z));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPlaceBlock(BlockPos pos) {
/*     */     try {
/* 110 */       for (Entity entity : mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos))) {
/* 111 */         if (!(entity instanceof net.minecraft.entity.item.EntityItem)) {
/* 112 */           return false;
/*     */         }
/*     */       } 
/* 115 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (!isSolid(pos) && canBeClicked(pos)) {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean placeBlock(Block block, BlockPos pos, boolean spoofRotation) {
/* 133 */     Place place = new Place(null, block, pos, spoofRotation);
/* 134 */     sleepUntil(() -> place.done, -1);
/* 135 */     return place.success;
/*     */   }
/*     */ 
/*     */   
/* 139 */   public static void placeBlockNoSleep(Block block, BlockPos pos, boolean spoofRotation) { new Place(null, block, pos, spoofRotation); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public static void placeBlockOnThisThread(Block block, BlockPos pos, boolean spoofRotation) { (new Place(null, block, pos, spoofRotation)).onTick(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean placeItem(Item item, BlockPos pos, boolean spoofRotation) {
/* 151 */     Place place = new Place(item, null, pos, spoofRotation);
/* 152 */     sleepUntil(() -> place.done, -1);
/* 153 */     return place.success;
/*     */   }
/*     */ 
/*     */   
/* 157 */   public static void placeItemNoSleep(Item item, BlockPos pos, boolean spoofRotation) { new Place(item, null, pos, spoofRotation); }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public static void placeItemOnThisThread(Item item, BlockPos pos, boolean spoofRotation) { (new Place(item, null, pos, spoofRotation)).onTick(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static int distance(BlockPos first, BlockPos second) { return Math.abs(first.func_177958_n() - second.func_177958_n()) + Math.abs(first.func_177956_o() - second.func_177956_o()) + Math.abs(first.func_177952_p() - second.func_177952_p()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static boolean isInRenderDistance(BlockPos pos) { return mc.field_71441_e.func_175726_f(pos).func_177410_o(); }
/*     */ 
/*     */   
/*     */   public static boolean canBeClicked(BlockPos pos) {
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */     int i;
/*     */     byte b;
/* 182 */     for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) { EnumFacing facing = arrayOfEnumFacing[b];
/* 183 */       BlockPos neighbor = pos.func_177972_a(facing);
/*     */ 
/*     */       
/* 186 */       if (!mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbor), false) && getBlock(neighbor) != Blocks.field_150355_j) {
/*     */         b++;
/*     */         continue;
/*     */       } 
/* 190 */       return true; }
/*     */ 
/*     */     
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public static boolean canSeePos(BlockPos pos) { return (getLegitFacing(pos) != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing getLegitFacing(BlockPos pos) {
/* 207 */     for (EnumFacing facing : EnumFacing.values()) {
/* 208 */       Vec3d start = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.eyeHeight, mc.field_71439_g.field_70161_v);
/* 209 */       Vec3d end = (new Vec3d(pos)).func_72441_c(0.5D, 0.499D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D));
/* 210 */       RayTraceResult result = mc.field_71441_e.func_72933_a(start, end);
/*     */       
/* 212 */       if (result != null && result.func_178782_a().equals(pos)) {
/* 213 */         return facing;
/*     */       }
/*     */     } 
/*     */     
/* 217 */     return null;
/*     */   }
/*     */   
/*     */   public static class Place { public boolean done;
/*     */     public boolean success;
/*     */     public boolean spoofRotation;
/*     */     public boolean dontRotate;
/*     */     public boolean rotateSpoofNoPacket;
/*     */     public boolean dontStopRotating;
/*     */     
/*     */     public Place(Item item, Block block, BlockPos pos, boolean spoofRotation) {
/* 228 */       this.item = item;
/* 229 */       this.pos = pos;
/* 230 */       this.block = block;
/* 231 */       this.spoofRotation = spoofRotation;
/* 232 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onTick(TickEvent.ClientTickEvent e) {
/* 237 */       if (this.block != null || this.item != null) {
/* 238 */         int slot = -1;
/* 239 */         if (this.block != null) {
/* 240 */           slot = InventoryUtil.getSlot(this.block);
/* 241 */         } else if (this.item != null) {
/* 242 */           slot = InventoryUtil.getSlot(this.item);
/*     */         } 
/*     */ 
/*     */         
/* 246 */         if (slot == -1) {
/* 247 */           done(false);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 252 */         if (InventoryUtil.getHandSlot() != slot)
/* 253 */           InventoryUtil.switchItem(slot, false); 
/*     */       }  EnumFacing[] arrayOfEnumFacing;
/*     */       int i;
/*     */       byte b;
/* 257 */       for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) { Vec3d hitVec; EnumFacing facing = arrayOfEnumFacing[b];
/* 258 */         if (this.setFacing != null) {
/* 259 */           facing = this.setFacing;
/*     */         }
/*     */         
/* 262 */         BlockPos neighbor = this.pos.func_177972_a(facing);
/* 263 */         EnumFacing side = facing.func_176734_d();
/*     */ 
/*     */         
/* 266 */         if (this.setFacing == null && !Mod.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(Mod.mc.field_71441_e.func_180495_p(neighbor), false) && Mod.getBlock(neighbor) != Blocks.field_150355_j) {
/*     */           b++;
/*     */           
/*     */           continue;
/*     */         } 
/* 271 */         if (this.item != null) {
/* 272 */           hitVec = (new Vec3d(this.pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side.func_176730_m())).func_186678_a(0.5D));
/*     */         } else {
/* 274 */           hitVec = (new Vec3d(neighbor)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side.func_176730_m())).func_186678_a(0.5D));
/*     */         } 
/*     */         
/* 277 */         if (!this.dontRotate) {
/* 278 */           if (this.spoofRotation) {
/* 279 */             RotationUtil.rotateSpoof(hitVec);
/*     */           } else {
/* 281 */             RotationUtil.rotate(hitVec, true);
/*     */           } 
/* 283 */         } else if (this.rotateSpoofNoPacket) {
/* 284 */           RotationUtil.rotateSpoofNoPacket(hitVec);
/*     */         } 
/*     */         
/* 287 */         Mod.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(Mod.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 288 */         if (this.item != null) {
/* 289 */           Mod.mc.field_71442_b.func_187099_a(Mod.mc.field_71439_g, Mod.mc.field_71441_e, this.pos, side, hitVec, EnumHand.MAIN_HAND);
/*     */         } else {
/* 291 */           Mod.mc.field_71442_b.func_187099_a(Mod.mc.field_71439_g, Mod.mc.field_71441_e, neighbor, side, hitVec, EnumHand.MAIN_HAND);
/* 292 */           Mod.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */         } 
/* 294 */         Mod.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(Mod.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/* 295 */         done(true);
/*     */         
/*     */         return; }
/*     */       
/* 299 */       done(false);
/*     */     }
/*     */     public Item item; public Block block;
/*     */     
/*     */     public void done(boolean success) {
/* 304 */       this.done = true;
/* 305 */       this.success = success;
/* 306 */       MinecraftForge.EVENT_BUS.unregister(this);
/* 307 */       if (!this.dontRotate && !this.dontStopRotating)
/* 308 */         RotationUtil.stopRotating(); 
/*     */     }
/*     */     
/*     */     public BlockPos pos;
/*     */     public EnumFacing setFacing; }
/*     */ 
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\BlockUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */