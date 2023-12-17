/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ public class PlayerUtil
/*     */   extends Mod {
/*  17 */   private static PlayerUtil playerUtil = new PlayerUtil();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<EntityPlayer> getAll() {
/*     */     try {
/*  24 */       players = new ArrayList();
/*     */       
/*  26 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  27 */         if (!player.func_70028_i(mc.field_71439_g)) {
/*  28 */           players.add(player);
/*     */         }
/*     */       } 
/*     */       
/*  32 */       return players;
/*  33 */     } catch (NullPointerException ignored) {
/*  34 */       return new ArrayList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<EntityPlayer> getAllEnemies() {
/*     */     try {
/*  43 */       players = new ArrayList();
/*     */       
/*  45 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  46 */         if (!player.func_70028_i(mc.field_71439_g) && !Friends.isFriend(player)) {
/*  47 */           players.add(player);
/*     */         }
/*     */       } 
/*     */       
/*  51 */       return players;
/*  52 */     } catch (NullPointerException ignored) {
/*  53 */       return new ArrayList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer getPlayer(String name) {
/*  61 */     for (EntityPlayer player : getAll()) {
/*  62 */       if (player.func_70005_c_().equals(name)) {
/*  63 */         return player;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosest() {
/*  74 */     lowestDistance = 2.147483647E9D;
/*  75 */     EntityPlayer closest = null;
/*     */     
/*  77 */     for (EntityPlayer player : getAll()) {
/*  78 */       if (player.func_70032_d(mc.field_71439_g) < lowestDistance) {
/*  79 */         lowestDistance = player.func_70032_d(mc.field_71439_g);
/*  80 */         closest = player;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     return closest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy() {
/*  91 */     lowestDistance = 2.147483647E9D;
/*  92 */     EntityPlayer closest = null;
/*     */     
/*  94 */     for (EntityPlayer player : getAllEnemies()) {
/*  95 */       if (player.func_70032_d(mc.field_71439_g) < lowestDistance) {
/*  96 */         lowestDistance = player.func_70032_d(mc.field_71439_g);
/*  97 */         closest = player;
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return closest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInSameBlock(EntityPlayer player, EntityPlayer other, int y) {
/* 109 */     BlockPos first = new BlockPos((int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
/* 110 */     BlockPos second = new BlockPos((int)other.field_70165_t, (int)other.field_70163_u, (int)other.field_70161_v);
/*     */     
/* 112 */     return (first.func_177958_n() == second.func_177958_n() && Math.abs(first.func_177956_o() - second.func_177956_o()) <= y && first.func_177952_p() == second.func_177952_p());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getServerIp() {
/*     */     try {
/* 121 */       return (mc.func_147104_D()).field_78845_b;
/* 122 */     } catch (NullPointerException e) {
/* 123 */       return "Singleplayer";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static void rightClick() { MinecraftForge.EVENT_BUS.register(playerUtil); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public static double getSpeed(Entity entity) { return (new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_72438_d(new Vec3d(mc.field_71439_g.field_70142_S, mc.field_71439_g.field_70137_T, mc.field_71439_g.field_70136_U)); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static boolean isMoving(Entity entity) { return (getSpeed(entity) == 0.0D); }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/* 149 */     mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, EnumHand.MAIN_HAND);
/* 150 */     MinecraftForge.EVENT_BUS.unregister(playerUtil);
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\PlayerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */