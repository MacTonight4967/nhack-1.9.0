/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderEntityNameEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.utils.ItemUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ public class NameTags
/*     */   extends Mod
/*     */ {
/*     */   private SPacketEntityStatus lastPacket;
/*     */   private int count;
/*  40 */   private HashMap<String, Integer> pops = new HashMap();
/*     */   
/*  42 */   public static Setting scaling = new Setting(Mode.DOUBLE, "Scaling", Double.valueOf(0.0D), new String[] { "How much bigger the nametags are rendered", "Higher = bigger" });
/*  43 */   public static Setting yAdd = new Setting(Mode.DOUBLE, "YAdd", Double.valueOf(0.0D), new String[] { "How much to add to the rendered y position", "Lower = lower" });
/*  44 */   public static Setting scaleBigger = new Setting(Mode.BOOLEAN, "Scale", Boolean.valueOf(true), new String[] { "Scales it bigger when u move further away from the player" });
/*  45 */   public static Setting scaleBiggerAmount = new Setting(scaleBigger, Mode.INTEGER, "Amount", Integer.valueOf(325), new String[] { "Lower = Scale gets more bigger when moving further away" });
/*  46 */   public static Setting minScaleAmount = new Setting(scaleBigger, Mode.DOUBLE, "MinScale", Double.valueOf(0.025D), new String[0]);
/*  47 */   public static Setting armor = new Setting(Mode.BOOLEAN, "Armor", Boolean.valueOf(true), new String[] { "Shows their armor" });
/*  48 */   public static Setting enchantments = new Setting(armor, Mode.BOOLEAN, "Enchantments", Boolean.valueOf(true), new String[] { "Shows what enchantments they got on", "Their armor and item" });
/*  49 */   public static Setting durability = new Setting(armor, Mode.BOOLEAN, "Durability", Boolean.valueOf(true), new String[] { "Shows their armor durability" });
/*  50 */   public static Setting itemName = new Setting(Mode.BOOLEAN, "ItemName", Boolean.valueOf(true), new String[] { "Shows the name of the item the", "Player has in their main hand" });
/*  51 */   public static Setting health = new Setting(Mode.BOOLEAN, "Health", Boolean.valueOf(true), new String[] { "Shows their health + absortion" });
/*  52 */   public static Setting ping = new Setting(Mode.BOOLEAN, "Ping", Boolean.valueOf(true), new String[] { "Shows their ping" });
/*  53 */   public static Setting popCounter = new Setting(Mode.BOOLEAN, "PopCounter", Boolean.valueOf(true), new String[] { "Displays how many times the people have", "Popped a totem next to their health" });
/*  54 */   public static Setting popCounterReset = new Setting(popCounter, Mode.BOOLEAN, "Reset", Boolean.valueOf(true), new String[] { "Resets the counter when the entity", "Goes out of render distance" });
/*     */   
/*     */   public NameTags() {
/*  57 */     super(Group.RENDER, "NameTags", new String[] { "Renders useful information about the player", "Above them" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     this.packetEvent = new Listener(event -> {
/*     */           
/* 316 */           if (event.packet instanceof SPacketEntityStatus && mc.field_71441_e != null) {
/* 317 */             SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/*     */             
/* 319 */             if (packet.func_149160_c() == 35 && !packet.equals(this.lastPacket)) {
/* 320 */               int amount = 1;
/* 321 */               Entity entity = packet.func_149161_a(mc.field_71441_e);
/* 322 */               if (entity == null) {
/*     */                 return;
/*     */               }
/*     */               
/* 326 */               if (this.pops.containsKey(entity.func_70005_c_())) {
/* 327 */                 amount += ((Integer)this.pops.get(entity.func_70005_c_())).intValue();
/*     */               }
/*     */               
/* 330 */               this.lastPacket = packet;
/* 331 */               this.pops.put(entity.func_70005_c_(), Integer.valueOf(amount));
/*     */             } 
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     this.onRenderEntityName = new Listener(event -> 
/*     */         
/* 354 */         event.cancel(), new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<PacketEvent> packetEvent;
/*     */   @EventHandler
/*     */   private Listener<RenderEntityNameEvent> onRenderEntityName;
/*     */   
/*     */   public void onRenderWorld(float partialTicks) {
/*     */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*     */       if (entity instanceof EntityPlayer && !entity.equals(mc.field_175622_Z))
/*     */         renderNameTag((EntityPlayer)entity, partialTicks); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderNameTag(EntityPlayer player, float partialTicks) {
/*     */     if ((mc.func_175598_ae()).field_78733_k == null || player.field_70128_L || player.func_110143_aJ() + player.func_110139_bj() == 0.0F)
/*     */       return; 
/*     */     boolean isThirdPersonFrontal = ((mc.func_175598_ae()).field_78733_k.field_74320_O == 2);
/*     */     double scale = 0.0D;
/*     */     double minScale = minScaleAmount.doubleValue();
/*     */     if (scaleBigger.booleanValue()) {
/*     */       Vec3d cameraPos = Tracers.interpolateEntity(mc.field_175622_Z, partialTicks);
/*     */       Vec3d playerPos = Tracers.interpolateEntity(player, partialTicks);
/*     */       scale = cameraPos.func_72438_d(playerPos) / scaleBiggerAmount.intValue();
/*     */     } else {
/*     */       scale = minScale;
/*     */     } 
/*     */     if (scale < minScale)
/*     */       scale = minScale; 
/*     */     scale += scaling.doubleValue();
/*     */     double playerX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * partialTicks - (mc.func_175598_ae()).field_78725_b;
/*     */     double playerY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * partialTicks - (mc.func_175598_ae()).field_78726_c;
/*     */     double playerZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * partialTicks - (mc.func_175598_ae()).field_78723_d;
/*     */     float viewerYaw = (mc.func_175598_ae()).field_78735_i;
/*     */     float viewerPitch = (mc.func_175598_ae()).field_78732_j;
/*     */     if (player.func_70093_af()) {
/*     */       playerY += 1.8D + yAdd.doubleValue();
/*     */     } else {
/*     */       playerY += 2.1D + yAdd.doubleValue();
/*     */     } 
/*     */     GlStateManager.func_179094_E();
/*     */     GlStateManager.func_179137_b(playerX, playerY, playerZ);
/*     */     GlStateManager.func_179114_b(-viewerYaw, 0.0F, 1.0F, 0.0F);
/*     */     GlStateManager.func_179114_b((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
/*     */     GlStateManager.func_179139_a(-scale, -scale, -scale);
/*     */     GlStateManager.func_179147_l();
/*     */     GlStateManager.func_179097_i();
/*     */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */     String name = StringUtils.func_76338_a(player.func_70005_c_());
/*     */     if (Friends.isFriend(player))
/*     */       name = ChatFormatting.AQUA + name; 
/*     */     int responseTime = -1;
/*     */     if (ping.booleanValue())
/*     */       try {
/*     */         responseTime = mc.func_147114_u().func_175102_a(player.func_110124_au()).func_178853_c();
/*     */       } catch (NullPointerException nullPointerException) {} 
/*     */     String playerName = name;
/*     */     if (ping.booleanValue() && responseTime != -1)
/*     */       playerName = playerName + " " + responseTime + "ms"; 
/*     */     if (NameTags.health.booleanValue()) {
/*     */       int health = (int)(player.func_110143_aJ() + player.func_110139_bj());
/*     */       ChatFormatting color2 = ChatFormatting.RED;
/*     */       if (health >= 20) {
/*     */         color2 = ChatFormatting.GREEN;
/*     */       } else if (health >= 14) {
/*     */         color2 = ChatFormatting.YELLOW;
/*     */       } else if (health >= 7) {
/*     */         color2 = ChatFormatting.GOLD;
/*     */       } 
/*     */       playerName = playerName + color2 + " " + health;
/*     */     } 
/*     */     if (popCounter.booleanValue() && this.pops.containsKey(name)) {
/*     */       int amount = ((Integer)this.pops.get(name)).intValue();
/*     */       if (amount != 0) {
/*     */         ChatFormatting color = ChatFormatting.RED;
/*     */         if (amount < 4) {
/*     */           color = ChatFormatting.GREEN;
/*     */         } else if (amount < 8) {
/*     */           color = ChatFormatting.YELLOW;
/*     */         } 
/*     */         playerName = playerName + " " + color + -amount;
/*     */       } 
/*     */     } 
/*     */     mc.field_71466_p.func_175063_a(playerName, (-mc.field_71466_p.func_78256_a(playerName) / 2), -9.0F, -1);
/*     */     boolean renderedArmor = false;
/*     */     if (armor.booleanValue()) {
/*     */       Iterator<ItemStack> items = player.func_184193_aE().iterator();
/*     */       ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
/*     */       stacks.add(player.func_184592_cb());
/*     */       while (items.hasNext()) {
/*     */         ItemStack stack = (ItemStack)items.next();
/*     */         if (stack != null && stack.func_77973_b() != Items.field_190931_a)
/*     */           stacks.add(stack); 
/*     */       } 
/*     */       stacks.add(player.func_184614_ca());
/*     */       Collections.reverse(stacks);
/*     */       int x = 0;
/*     */       for (ItemStack stack : stacks) {
/*     */         if (stack != null) {
/*     */           Item item = stack.func_77973_b();
/*     */           if (item != Items.field_190931_a) {
/*     */             if (item instanceof net.minecraft.item.ItemBlock) {
/*     */               GlStateManager.func_179094_E();
/*     */               GlStateManager.func_179147_l();
/*     */               GlStateManager.func_179097_i();
/*     */               RenderHelper.func_74519_b();
/*     */             } else {
/*     */               GlStateManager.func_179094_E();
/*     */               GlStateManager.func_179132_a(true);
/*     */               GlStateManager.func_179086_m(256);
/*     */               RenderHelper.func_74519_b();
/*     */               (mc.func_175599_af()).field_77023_b = -150.0F;
/*     */               GlStateManager.func_179118_c();
/*     */               GlStateManager.func_179126_j();
/*     */               GlStateManager.func_179129_p();
/*     */             } 
/*     */             double itemScale = mc.field_71466_p.field_78288_b / 9.0D;
/*     */             GlStateManager.func_179139_a(itemScale, itemScale, 0.0D);
/*     */             GlStateManager.func_179109_b((x - 16 * stacks.size() / 2), (-mc.field_71466_p.field_78288_b - 23), 0.0F);
/*     */             mc.func_175599_af().func_180450_b(stack, 0, 0);
/*     */             mc.func_175599_af().func_175030_a(mc.field_71466_p, stack, 0, 0);
/*     */             if (item instanceof net.minecraft.item.ItemBlock) {
/*     */               RenderHelper.func_74518_a();
/*     */               GlStateManager.func_179126_j();
/*     */               GlStateManager.func_179084_k();
/*     */               GlStateManager.func_179121_F();
/*     */             } else {
/*     */               (mc.func_175599_af()).field_77023_b = 0.0F;
/*     */               RenderHelper.func_74518_a();
/*     */               GlStateManager.func_179089_o();
/*     */               GlStateManager.func_179141_d();
/*     */               GlStateManager.func_179097_i();
/*     */               GlStateManager.func_179126_j();
/*     */               GlStateManager.func_179121_F();
/*     */             } 
/*     */             x += 16;
/*     */             renderedArmor = true;
/*     */             if (enchantments.booleanValue()) {
/*     */               int y = -2;
/*     */               ArrayList<String> stringsToDraw = new ArrayList<String>();
/*     */               if (stack.func_77986_q() != null) {
/*     */                 NBTTagList tags = stack.func_77986_q();
/*     */                 int count = 0;
/*     */                 for (int i = 0; i < tags.func_74745_c() && count < 5; i++) {
/*     */                   NBTTagCompound tagCompound = tags.func_150305_b(i);
/*     */                   if (tagCompound != null && Enchantment.func_185262_c(tagCompound.func_74771_c("id")) != null) {
/*     */                     Enchantment enchantment = Enchantment.func_185262_c(tagCompound.func_74765_d("id"));
/*     */                     short lvl = tagCompound.func_74765_d("lvl");
/*     */                     if (enchantment != null && !enchantment.func_190936_d()) {
/*     */                       String ench = "";
/*     */                       if (lvl == 1) {
/*     */                         ench = enchantment.func_77316_c(lvl).substring(0, 3);
/*     */                       } else {
/*     */                         ench = enchantment.func_77316_c(lvl).substring(0, 2) + lvl;
/*     */                       } 
/*     */                       stringsToDraw.add(ench);
/*     */                       count++;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               for (String string : stringsToDraw) {
/*     */                 GlStateManager.func_179094_E();
/*     */                 GlStateManager.func_179097_i();
/*     */                 GlStateManager.func_179109_b(x - 16.0F * stacks.size() / 2.0F - 8.0F - mc.field_71466_p.func_78256_a(string) / 4.0F, (-mc.field_71466_p.field_78288_b - 23 - y), 0.0F);
/*     */                 GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/*     */                 mc.field_71466_p.func_175063_a(string, 0.0F, 0.0F, -1);
/*     */                 GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/*     */                 GlStateManager.func_179126_j();
/*     */                 GlStateManager.func_179121_F();
/*     */                 y -= 4;
/*     */               } 
/*     */             } 
/*     */             if (durability.booleanValue() && stack.func_77958_k() != 0) {
/*     */               String string = "" + ItemUtil.getDurabilityColor(stack) + ItemUtil.getPercentageDurability(stack) + "%";
/*     */               GlStateManager.func_179094_E();
/*     */               GlStateManager.func_179097_i();
/*     */               GlStateManager.func_179109_b(x - 16.0F * stacks.size() / 2.0F - 8.0F - mc.field_71466_p.func_78256_a(string) / 4.0F, (-mc.field_71466_p.field_78288_b - 26), 0.0F);
/*     */               GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/*     */               mc.field_71466_p.func_175063_a(string, 0.0F, 0.0F, -1);
/*     */               GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/*     */               GlStateManager.func_179126_j();
/*     */               GlStateManager.func_179121_F();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     if (itemName.booleanValue() && player.func_184614_ca().func_77973_b() != Items.field_190931_a) {
/*     */       int y = 31;
/*     */       if (!renderedArmor)
/*     */         y = 5; 
/*     */       String string = player.func_184614_ca().func_82833_r();
/*     */       GlStateManager.func_179094_E();
/*     */       GlStateManager.func_179097_i();
/*     */       GlStateManager.func_179109_b(-(mc.field_71466_p.func_78256_a(string) / 4.0F), (-mc.field_71466_p.field_78288_b - y), 0.0F);
/*     */       GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/*     */       mc.field_71466_p.func_175063_a(string, 0.0F, 0.0F, -1);
/*     */       GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/*     */       GlStateManager.func_179126_j();
/*     */       GlStateManager.func_179121_F();
/*     */     } 
/*     */     GlStateManager.func_179126_j();
/*     */     GlStateManager.func_179084_k();
/*     */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*     */     if (popCounterReset.booleanValue() && mc.field_71439_g != null) {
/*     */       this.count++;
/*     */       if (this.count >= 30) {
/*     */         this.count = 0;
/*     */         for (String name : this.pops.keySet()) {
/*     */           if ((mc.field_71441_e != null && mc.field_71441_e.func_72924_a(name) == null) || (mc.field_71441_e.func_72924_a(name)).field_70128_L)
/*     */             this.pops.put(name, Integer.valueOf(0)); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\NameTags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */