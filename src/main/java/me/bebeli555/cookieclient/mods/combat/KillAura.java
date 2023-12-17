/*     */ package me.bebeli555.cookieclient.mods.combat;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.hud.components.LagNotifierComponent;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.utils.EntityUtil;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityMultiPart;
/*     */ import net.minecraft.entity.MultiPartEntityPart;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ public class KillAura
/*     */   extends Mod
/*     */ {
/*     */   private static boolean isRotating;
/*  43 */   public static Setting range = new Setting(Mode.DOUBLE, "Range", Double.valueOf(4.5D), new String[] { "How far the target can be" });
/*  44 */   public static Setting players = new Setting(Mode.BOOLEAN, "Players", Boolean.valueOf(true), new String[] { "Attacks players but not friends" });
/*  45 */   public static Setting monsters = new Setting(Mode.BOOLEAN, "Monsters", Boolean.valueOf(false), new String[] { "Attacks monsters" });
/*  46 */   public static Setting neutrals = new Setting(Mode.BOOLEAN, "Neutrals", Boolean.valueOf(false), new String[] { "Attacks neutral entities like enderman" });
/*  47 */   public static Setting passive = new Setting(Mode.BOOLEAN, "Passive", Boolean.valueOf(false), new String[] { "Attacks passive entities like animals" });
/*  48 */   public static Setting pauseIfCrystal = new Setting(Mode.BOOLEAN, "PauseIfCrystal", Boolean.valueOf(false), new String[] { "Pauses killaura if ur holding an end crystal" });
/*  49 */   public static Setting pauseIfGap = new Setting(Mode.BOOLEAN, "PauseIfGap", Boolean.valueOf(true), new String[] { "Pauses killaura if ur holding a gapple" });
/*  50 */   public static Setting tpsSync = new Setting(Mode.BOOLEAN, "TPSSync", Boolean.valueOf(true), new String[] { "Syncs attack delay with tps" });
/*     */ 
/*     */   
/*  53 */   public KillAura() { super(Group.COMBAT, "KillAura", new String[] { "Attacks nearby targets automatically" }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void onDisabled() { RotationUtil.stopRotating(); }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*  63 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  68 */     if ((pauseIfCrystal.booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) || (pauseIfGap.booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151153_ao)) {
/*  69 */       if (isRotating) RotationUtil.stopRotating(); 
/*  70 */       isRotating = false;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  75 */     Entity best = null;
/*  76 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  77 */       if (entity.func_70032_d(mc.field_71439_g) <= range.doubleValue() && isValid(entity) && (
/*  78 */         best == null || entity.func_70032_d(mc.field_71439_g) < best.func_70032_d(mc.field_71439_g))) {
/*  79 */         best = entity;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  85 */     if (best == null) {
/*  86 */       if (isRotating) RotationUtil.stopRotating(); 
/*  87 */       isRotating = false;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  92 */     int slot = getWeaponSlot();
/*     */     
/*  94 */     if (slot != -1 && slot != mc.field_71439_g.field_71071_by.field_70461_c) {
/*  95 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*  96 */       mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */ 
/*     */     
/* 100 */     isRotating = true;
/* 101 */     RotationUtil.rotateSpoofNoPacket(new Vec3d(best.field_70165_t, best.field_70163_u + 1.0D, best.field_70161_v));
/*     */ 
/*     */     
/* 104 */     float ticks = 20.0F - (float)LagNotifierComponent.getTps();
/* 105 */     boolean isReady = (mc.field_71439_g.func_184825_o(tpsSync.booleanValue() ? -ticks : 0.0F) >= 1.0F);
/*     */     
/* 107 */     if (isReady) {
/* 108 */       mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(best));
/* 109 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */       
/* 111 */       if (mc.field_71442_b.field_78779_k != GameType.SPECTATOR) {
/* 112 */         attackTargetEntityWithCurrentItem(best);
/* 113 */         mc.field_71439_g.func_184821_cY();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isValid(Entity entity) {
/* 119 */     if (entity.equals(mc.field_71439_g) || entity.equals(mc.field_175622_Z) || !entity.func_70089_S()) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     if (entity instanceof EntityPlayer && players.booleanValue() && !Friends.isFriend(entity)) {
/* 124 */       return true;
/*     */     }
/*     */     
/* 127 */     if (EntityUtil.isHostileMob(entity) && monsters.booleanValue()) {
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     if (EntityUtil.isNeutralMob(entity) && neutrals.booleanValue()) {
/* 132 */       return true;
/*     */     }
/*     */     
/* 135 */     if (EntityUtil.isPassive(entity) && passive.booleanValue()) {
/* 136 */       return true;
/*     */     }
/*     */     
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public static int getWeaponSlot() {
/* 143 */     items = new Item[] { Items.field_151048_u, Items.field_151056_x, Items.field_151040_l, Items.field_151036_c, Items.field_151010_B, Items.field_151006_E, Items.field_151052_q, Items.field_151049_t, Items.field_151041_m, Items.field_151053_p };
/*     */ 
/*     */     
/* 146 */     for (Item item : items) {
/* 147 */       for (int i = 0; i < 9; i++) {
/* 148 */         ItemStack itemStack = InventoryUtil.getItemStack(i);
/*     */         
/* 150 */         if (itemStack.func_77973_b() == item) {
/* 151 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isWeapon(Item item) {
/* 160 */     Item[] items = { Items.field_151048_u, Items.field_151056_x, Items.field_151040_l, Items.field_151036_c, Items.field_151010_B, Items.field_151006_E, Items.field_151052_q, Items.field_151049_t, Items.field_151041_m, Items.field_151053_p };
/*     */ 
/*     */     
/* 163 */     for (Item check : items) {
/* 164 */       if (check == item) {
/* 165 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 174 */     if (targetEntity.func_70075_an() && 
/* 175 */       !targetEntity.func_85031_j(mc.field_71439_g)) {
/* 176 */       float f1, f = (float)mc.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
/*     */ 
/*     */       
/* 179 */       if (targetEntity instanceof EntityLivingBase) {
/* 180 */         f1 = EnchantmentHelper.func_152377_a(mc.field_71439_g.func_184614_ca(), ((EntityLivingBase)targetEntity).func_70668_bt());
/*     */       } else {
/* 182 */         f1 = EnchantmentHelper.func_152377_a(mc.field_71439_g.func_184614_ca(), EnumCreatureAttribute.UNDEFINED);
/*     */       } 
/*     */       
/* 185 */       float f2 = mc.field_71439_g.func_184825_o(0.5F);
/* 186 */       f *= (0.2F + f2 * f2 * 0.8F);
/* 187 */       f1 *= f2;
/* 188 */       mc.field_71439_g.func_184821_cY();
/*     */       
/* 190 */       if (f > 0.0F || f1 > 0.0F) {
/* 191 */         boolean flag = (f2 > 0.9F);
/* 192 */         boolean flag1 = false;
/* 193 */         int i = 0;
/* 194 */         i += EnchantmentHelper.func_77501_a(mc.field_71439_g);
/*     */         
/* 196 */         if (mc.field_71439_g.func_70051_ag() && flag) {
/* 197 */           mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187721_dT, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/* 198 */           i++;
/* 199 */           flag1 = true;
/*     */         } 
/*     */         
/* 202 */         boolean flag2 = (flag && mc.field_71439_g.field_70143_R > 0.0F && !mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_70644_a(MobEffects.field_76440_q) && !mc.field_71439_g.func_184218_aH() && targetEntity instanceof EntityLivingBase);
/* 203 */         flag2 = (flag2 && !mc.field_71439_g.func_70051_ag());
/*     */         
/* 205 */         if (flag2) {
/* 206 */           f *= 1.5F;
/*     */         }
/*     */         
/* 209 */         f += f1;
/* 210 */         boolean flag3 = false;
/* 211 */         double d0 = (mc.field_71439_g.field_70140_Q - mc.field_71439_g.field_70141_P);
/*     */         
/* 213 */         if (flag && !flag2 && !flag1 && mc.field_71439_g.field_70122_E && d0 < mc.field_71439_g.func_70689_ay()) {
/* 214 */           ItemStack itemstack = mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND);
/*     */           
/* 216 */           if (itemstack.func_77973_b() instanceof net.minecraft.item.ItemSword) {
/* 217 */             flag3 = true;
/*     */           }
/*     */         } 
/*     */         
/* 221 */         float f4 = 0.0F;
/* 222 */         boolean flag4 = false;
/* 223 */         int j = EnchantmentHelper.func_90036_a(mc.field_71439_g);
/*     */         
/* 225 */         if (targetEntity instanceof EntityLivingBase) {
/* 226 */           f4 = ((EntityLivingBase)targetEntity).func_110143_aJ();
/*     */           
/* 228 */           if (j > 0 && !targetEntity.func_70027_ad()) {
/* 229 */             flag4 = true;
/* 230 */             targetEntity.func_70015_d(1);
/*     */           } 
/*     */         } 
/*     */         
/* 234 */         double d1 = targetEntity.field_70159_w;
/* 235 */         double d2 = targetEntity.field_70181_x;
/* 236 */         double d3 = targetEntity.field_70179_y;
/* 237 */         boolean flag5 = targetEntity.func_70097_a(DamageSource.func_76365_a(mc.field_71439_g), f);
/*     */         
/* 239 */         if (flag5) {
/* 240 */           if (i > 0) {
/* 241 */             if (targetEntity instanceof EntityLivingBase) {
/* 242 */               ((EntityLivingBase)targetEntity).func_70653_a(mc.field_71439_g, i * 0.5F, MathHelper.func_76126_a(mc.field_71439_g.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(mc.field_71439_g.field_70177_z * 0.017453292F));
/*     */             } else {
/* 244 */               targetEntity.func_70024_g((-MathHelper.func_76126_a(mc.field_71439_g.field_70177_z * 0.017453292F) * i * 0.5F), 0.1D, (MathHelper.func_76134_b(mc.field_71439_g.field_70177_z * 0.017453292F) * i * 0.5F));
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 252 */           if (flag3) {
/* 253 */             float f3 = 1.0F + EnchantmentHelper.func_191527_a(mc.field_71439_g) * f;
/*     */             
/* 255 */             for (EntityLivingBase entitylivingbase : mc.field_71439_g.field_70170_p.func_72872_a(EntityLivingBase.class, targetEntity.func_174813_aQ().func_72321_a(1.0D, 0.25D, 1.0D))) {
/* 256 */               if (entitylivingbase != mc.field_71439_g && entitylivingbase != targetEntity && !mc.field_71439_g.func_184191_r(entitylivingbase) && mc.field_71439_g.func_70068_e(entitylivingbase) < 9.0D) {
/* 257 */                 entitylivingbase.func_70653_a(mc.field_71439_g, 0.4F, MathHelper.func_76126_a(mc.field_71439_g.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(mc.field_71439_g.field_70177_z * 0.017453292F));
/* 258 */                 entitylivingbase.func_70097_a(DamageSource.func_76365_a(mc.field_71439_g), f3);
/*     */               } 
/*     */             } 
/*     */             
/* 262 */             mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187730_dW, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/* 263 */             mc.field_71439_g.func_184810_cG();
/*     */           } 
/*     */           
/* 266 */           if (targetEntity instanceof EntityPlayerMP && targetEntity.field_70133_I) {
/* 267 */             ((EntityPlayerMP)targetEntity).field_71135_a.func_147359_a(new SPacketEntityVelocity(targetEntity));
/* 268 */             targetEntity.field_70133_I = false;
/* 269 */             targetEntity.field_70159_w = d1;
/* 270 */             targetEntity.field_70181_x = d2;
/* 271 */             targetEntity.field_70179_y = d3;
/*     */           } 
/*     */           
/* 274 */           if (flag2) {
/* 275 */             mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187718_dS, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/* 276 */             mc.field_71439_g.func_71009_b(targetEntity);
/*     */           } 
/*     */           
/* 279 */           if (!flag2 && !flag3) {
/* 280 */             if (flag) {
/* 281 */               mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187727_dV, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/*     */             } else {
/* 283 */               mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187733_dX, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/*     */             } 
/*     */           }
/*     */           
/* 287 */           if (f1 > 0.0F) {
/* 288 */             mc.field_71439_g.func_71047_c(targetEntity);
/*     */           }
/*     */           
/* 291 */           mc.field_71439_g.func_130011_c(targetEntity);
/*     */           
/* 293 */           if (targetEntity instanceof EntityLivingBase) {
/* 294 */             EnchantmentHelper.func_151384_a((EntityLivingBase)targetEntity, mc.field_71439_g);
/*     */           }
/*     */           
/* 297 */           EnchantmentHelper.func_151385_b(mc.field_71439_g, targetEntity);
/* 298 */           ItemStack itemstack1 = mc.field_71439_g.func_184614_ca();
/* 299 */           EntityLivingBase entityLivingBase = targetEntity;
/*     */           
/* 301 */           if (targetEntity instanceof MultiPartEntityPart) {
/* 302 */             IEntityMultiPart ientitymultipart = ((MultiPartEntityPart)targetEntity).field_70259_a;
/*     */             
/* 304 */             if (ientitymultipart instanceof EntityLivingBase) {
/* 305 */               entityLivingBase = (EntityLivingBase)ientitymultipart;
/*     */             }
/*     */           } 
/*     */           
/* 309 */           if (!itemstack1.func_190926_b() && entityLivingBase instanceof EntityLivingBase) {
/* 310 */             itemstack1.func_77961_a((EntityLivingBase)entityLivingBase, mc.field_71439_g);
/*     */             
/* 312 */             if (itemstack1.func_190926_b()) {
/* 313 */               mc.field_71439_g.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
/*     */             }
/*     */           } 
/*     */           
/* 317 */           if (targetEntity instanceof EntityLivingBase) {
/* 318 */             float f5 = f4 - ((EntityLivingBase)targetEntity).func_110143_aJ();
/* 319 */             mc.field_71439_g.func_71064_a(StatList.field_188111_y, Math.round(f5 * 10.0F));
/*     */             
/* 321 */             if (j > 0) {
/* 322 */               targetEntity.func_70015_d(j * 4);
/*     */             }
/*     */             
/* 325 */             if (mc.field_71439_g.field_70170_p instanceof WorldServer && f5 > 2.0F) {
/* 326 */               int k = (int)(f5 * 0.5D);
/* 327 */               ((WorldServer)mc.field_71439_g.field_70170_p).func_175739_a(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.field_70165_t, targetEntity.field_70163_u + (targetEntity.field_70131_O * 0.5F), targetEntity.field_70161_v, k, 0.1D, 0.0D, 0.1D, 0.2D, new int[0]);
/*     */             } 
/*     */           } 
/*     */           
/* 331 */           mc.field_71439_g.func_71020_j(0.1F);
/*     */         } else {
/* 333 */           mc.field_71439_g.field_70170_p.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187724_dU, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
/*     */           
/* 335 */           if (flag4)
/* 336 */             targetEntity.func_70066_B(); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\KillAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */