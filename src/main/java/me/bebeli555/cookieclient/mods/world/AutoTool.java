/*     */ package me.bebeli555.cookieclient.mods.world;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerDamageBlockEvent2;
/*     */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ 
/*     */ public class AutoTool
/*     */   extends Mod {
/*  22 */   private Timer timer = new Timer();
/*     */   
/*     */   private int oldSlot;
/*  25 */   public static Setting switchBack = new Setting(Mode.BOOLEAN, "SwitchBack", Boolean.valueOf(true), new String[] { "Switches back to old slot when done" });
/*     */   
/*     */   public AutoTool() {
/*  28 */     super(Group.WORLD, "AutoTool", new String[] { "Switches to best tool when breaking blocks" });
/*     */ 
/*     */     
/*  31 */     this.onDamageBlock = new Listener(event -> {
/*     */           
/*  33 */           InventoryUtil.ItemStackUtil best = null;
/*  34 */           float bestSpeed = -1.0F;
/*     */           
/*  36 */           for (int i = 0; i < 9; i++) {
/*  37 */             ItemStack stack = InventoryUtil.getItemStack(i);
/*  38 */             float speed = getBreakSpeed(event.pos, stack);
/*     */             
/*  40 */             if (speed > bestSpeed) {
/*  41 */               bestSpeed = speed;
/*  42 */               best = new InventoryUtil.ItemStackUtil(stack, i);
/*     */             } 
/*     */           } 
/*     */           
/*  46 */           if (best != null && best.itemStack.func_77973_b() != mc.field_71439_g.func_184614_ca().func_77973_b() && bestSpeed > getBreakSpeed(event.pos, mc.field_71439_g.func_184614_ca())) {
/*  47 */             this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  48 */             InventoryUtil.switchItem(best.slotId, false);
/*  49 */             mc.field_71442_b.func_78765_e();
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  53 */     this.onPlayerUpdate = new Listener(event -> {
/*     */           
/*  55 */           if (mc.field_71442_b.field_78778_j) {
/*  56 */             this.timer.reset();
/*     */           }
/*     */ 
/*     */           
/*  60 */           if (!mc.field_71442_b.field_78778_j && this.oldSlot != -1 && switchBack.booleanValue() && this.timer.hasPassed(500)) {
/*  61 */             InventoryUtil.switchItem(this.oldSlot, false);
/*  62 */             mc.field_71442_b.func_78765_e();
/*  63 */             this.timer.reset();
/*  64 */             this.oldSlot = -1;
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   public static float getBreakSpeed(BlockPos pos, ItemStack stack) {
/*  70 */     float f = 1.0F + stack.func_150997_a(mc.field_71441_e.func_180495_p(pos));
/*     */     
/*  72 */     if (f > 1.0F) {
/*  73 */       int i = EnchantmentHelper.func_185293_e(mc.field_71439_g);
/*     */       
/*  75 */       if (i > 0 && !stack.func_190926_b()) {
/*  76 */         f += (i * i + 1);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
/*  81 */       f *= (1.0F + (mc.field_71439_g.func_70660_b(MobEffects.field_76422_e).func_76458_c() + 1) * 0.2F);
/*     */     }
/*     */     
/*  84 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
/*     */       float f1, f1, f1, f1;
/*     */       
/*  87 */       switch (mc.field_71439_g.func_70660_b(MobEffects.field_76419_f).func_76458_c()) {
/*     */         case 0:
/*  89 */           f1 = 0.3F;
/*     */           break;
/*     */         case 1:
/*  92 */           f1 = 0.09F;
/*     */           break;
/*     */         case 2:
/*  95 */           f1 = 0.0027F;
/*     */           break;
/*     */         
/*     */         default:
/*  99 */           f1 = 8.1E-4F;
/*     */           break;
/*     */       } 
/* 102 */       f *= f1;
/*     */     } 
/*     */     
/* 105 */     if (mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i(mc.field_71439_g)) {
/* 106 */       f /= 5.0F;
/*     */     }
/*     */     
/* 109 */     if (!mc.field_71439_g.field_70122_E) {
/* 110 */       f /= 5.0F;
/*     */     }
/*     */     
/* 113 */     f = ForgeEventFactory.getBreakSpeed(mc.field_71439_g, mc.field_71441_e.func_180495_p(pos), f, pos);
/* 114 */     return (f < 0.0F) ? 0.0F : f;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<PlayerDamageBlockEvent2> onDamageBlock;
/*     */   @EventHandler
/*     */   private Listener<PlayerUpdateEvent> onPlayerUpdate;
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\AutoTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */