/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrystalUtil
/*     */   extends Mod
/*     */ {
/*     */   public static ArrayList<EntityEnderCrystal> getCrystals(double distance) {
/*  32 */     ArrayList<EntityEnderCrystal> list = new ArrayList<EntityEnderCrystal>();
/*     */     
/*  34 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  35 */       if (entity instanceof EntityEnderCrystal && 
/*  36 */         entity.func_70032_d(mc.field_71439_g) <= distance) {
/*  37 */         list.add((EntityEnderCrystal)entity);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  42 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityEnderCrystal getCrystalInPos(BlockPos pos) {
/*  49 */     for (Entity entity : mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos.func_177982_a(0, 1, 0)))) {
/*  50 */       if (entity instanceof EntityEnderCrystal && entity.func_70089_S()) {
/*  51 */         return (EntityEnderCrystal)entity;
/*     */       }
/*     */     } 
/*     */     
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float calculateDamage(Vec3d pos, EntityLivingBase entity) {
/*     */     try {
/*  63 */       if (entity.func_70011_f(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) > 12.0D) {
/*  64 */         return 0.0F;
/*     */       }
/*     */       
/*  67 */       double blockDensity = entity.field_70170_p.func_72842_a(pos, entity.func_174813_aQ());
/*  68 */       double power = (1.0D - entity.func_70011_f(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) / 12.0D) * blockDensity;
/*  69 */       float damage = (int)((power * power + power) / 2.0D * 7.0D * 12.0D + 1.0D);
/*     */       
/*  71 */       int difficulty = mc.field_71441_e.func_175659_aa().func_151525_a();
/*  72 */       damage *= ((difficulty == 0) ? 0.0F : ((difficulty == 2) ? 1.0F : ((difficulty == 1) ? 0.5F : 1.5F)));
/*     */       
/*  74 */       return getReduction(entity, damage, new Explosion(mc.field_71441_e, null, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 6.0F, false, true));
/*  75 */     } catch (NullPointerException e) {
/*  76 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float calculateDamage(BlockPos pos, EntityLivingBase entity) {
/*     */     try {
/*  82 */       return calculateDamage(new Vec3d(pos.func_177958_n() + 0.5D, (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D), entity);
/*  83 */     } catch (NullPointerException e) {
/*  84 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float getReduction(EntityLivingBase player, float damage, Explosion explosion) {
/*  89 */     damage = CombatRules.func_189427_a(damage, player.func_70658_aO(), (float)player.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/*  90 */     damage *= (1.0F - EnchantmentHelper.func_77508_a(player.func_184193_aE(), DamageSource.func_94539_a(explosion)) / 25.0F);
/*     */     
/*  92 */     if (player.func_70644_a(Potion.func_188412_a(11))) {
/*  93 */       damage -= damage / 4.0F;
/*     */     }
/*     */     
/*  96 */     return damage;
/*     */   }
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos pos) {
/* 100 */     Block block = getBlock(pos);
/*     */     
/* 102 */     if (block == Blocks.field_150343_Z || block == Blocks.field_150357_h) {
/* 103 */       Block floor = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c();
/* 104 */       Block ceil = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c();
/*     */       
/* 106 */       if (floor == Blocks.field_150350_a && ceil == Blocks.field_150350_a) {
/* 107 */         ArrayList<Entity> entities = new ArrayList<Entity>();
/* 108 */         entities.addAll(mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos.func_177982_a(0, 1, 0))));
/* 109 */         for (Entity entity : entities) {
/* 110 */           if (entity.func_70089_S()) {
/* 111 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 115 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public static List<BlockPos> findCrystalBlocks(EntityPlayer player, float range) {
/* 123 */     NonNullList<BlockPos> positions = NonNullList.func_191196_a();
/* 124 */     positions.addAll((Collection)getSphere(GetPlayerPosFloored(player), range, (int)range, false, true, 0)
/* 125 */         .stream().filter(CrystalUtil::canPlaceCrystal).collect(Collectors.toList()));
/* 126 */     return positions;
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plusY) {
/* 130 */     List<BlockPos> circleblocks = new ArrayList<BlockPos>();
/* 131 */     int cx = pos.func_177958_n();
/* 132 */     int cy = pos.func_177956_o();
/* 133 */     int cz = pos.func_177952_p();
/* 134 */     for (int x = cx - (int)r; x <= cx + r; x++) {
/* 135 */       for (int z = cz - (int)r; z <= cz + r; z++) {
/* 136 */         for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : (cy + h)); y++) {
/* 137 */           double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/* 138 */           if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
/* 139 */             circleblocks.add(new BlockPos(x, y + plusY, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return circleblocks;
/*     */   }
/*     */ 
/*     */   
/* 149 */   public static BlockPos GetPlayerPosFloored(EntityPlayer player) { return new BlockPos(Math.floor(player.field_70165_t), Math.floor(player.field_70163_u), Math.floor(player.field_70161_v)); }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\CrystalUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */