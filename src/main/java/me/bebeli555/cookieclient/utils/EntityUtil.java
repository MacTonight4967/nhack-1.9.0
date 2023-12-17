/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EnumCreatureType;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.entity.passive.EntityWolf;
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
/*    */ public class EntityUtil
/*    */ {
/*    */   public static boolean isPassive(Entity e) {
/* 21 */     if (e instanceof EntityWolf && ((EntityWolf)e).func_70919_bu()) {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     if (e instanceof net.minecraft.entity.passive.EntityAnimal || e instanceof net.minecraft.entity.EntityAgeable || e instanceof net.minecraft.entity.passive.EntityTameable || e instanceof net.minecraft.entity.passive.EntityAmbientCreature || e instanceof net.minecraft.entity.passive.EntitySquid) {
/* 26 */       return true;
/*    */     }
/*    */     
/* 29 */     if (e instanceof EntityIronGolem && ((EntityIronGolem)e).func_70643_av() == null) {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public static boolean isNeutralMob(Entity entity) { return (entity instanceof net.minecraft.entity.monster.EntityPigZombie || entity instanceof EntityWolf || entity instanceof net.minecraft.entity.monster.EntityEnderman); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public static boolean isHostileMob(Entity entity) { return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity)); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */