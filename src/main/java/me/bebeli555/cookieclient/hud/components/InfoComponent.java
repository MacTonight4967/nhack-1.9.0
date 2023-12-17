/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import me.bebeli555.cookieclient.utils.ItemUtil;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class InfoComponent
/*    */   extends HudComponent
/*    */ {
/* 17 */   public InfoComponent() { super(HudComponent.HudCorner.TOP_RIGHT, "Info"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 22 */     super.onRender(partialTicks);
/* 23 */     int amount = 0;
/*    */ 
/*    */     
/* 26 */     if (GuiSettings.potions.booleanValue()) {
/* 27 */       for (PotionEffect effect : mc.field_71439_g.func_70651_bq()) {
/* 28 */         String text = I18n.func_135052_a(effect.func_188419_a().func_76393_a(), new Object[0]);
/* 29 */         if (effect.func_76458_c() > 0) {
/* 30 */           text = text + " " + (effect.func_76458_c() + 1);
/*    */         }
/* 32 */         text = text + " " + w + Potion.func_188410_a(effect, 1.0F);
/* 33 */         if (this.corner == HudComponent.HudCorner.TOP_LEFT || this.corner == HudComponent.HudCorner.TOP_RIGHT) {
/* 34 */           drawString(text, 0.0F, (amount * 10), effect.func_188419_a().func_76401_j(), GuiSettings.infoShadow.booleanValue());
/*    */         } else {
/* 36 */           drawString(text, 0.0F, -(amount * 10), effect.func_188419_a().func_76401_j(), GuiSettings.infoShadow.booleanValue());
/*    */         } 
/*    */         
/* 39 */         amount++;
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 44 */     if (GuiSettings.speed.booleanValue()) {
/* 45 */       renderInfo(g + "Speed " + w + decimal(PlayerUtil.getSpeed(mc.field_71439_g) * 71.35D, 1) + "km/h", amount);
/* 46 */       amount++;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     if (GuiSettings.ping.booleanValue() && 
/* 51 */       !mc.func_71356_B()) {
/*    */       try {
/* 53 */         renderInfo(g + "Ping " + w + mc.func_147114_u().func_175102_a(mc.field_175622_Z.func_110124_au()).func_178853_c() + "ms", amount);
/* 54 */         amount++;
/* 55 */       } catch (NullPointerException nullPointerException) {}
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (GuiSettings.durability.booleanValue()) {
/* 63 */       ItemStack itemStack = mc.field_71439_g.func_184614_ca();
/* 64 */       if (ItemUtil.hasDurability(itemStack)) {
/* 65 */         renderInfo(g + "Durability " + ItemUtil.getDurabilityColor(itemStack) + ItemUtil.getDurability(itemStack), amount);
/* 66 */         amount++;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 71 */     if (GuiSettings.tps.booleanValue()) {
/* 72 */       renderInfo(g + "TPS " + w + decimal(LagNotifierComponent.getTps(), 2), amount);
/* 73 */       amount++;
/*    */     } 
/*    */ 
/*    */     
/* 77 */     if (GuiSettings.fps.booleanValue()) {
/* 78 */       renderInfo(g + "FPS " + w + Minecraft.func_175610_ah(), amount);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender() {
/* 84 */     return (GuiSettings.speed.booleanValue() || GuiSettings.ping.booleanValue() || GuiSettings.durability.booleanValue() || GuiSettings.tps
/* 85 */       .booleanValue() || GuiSettings.fps.booleanValue() || GuiSettings.potions.booleanValue());
/*    */   }
/*    */   
/*    */   public void renderInfo(String text, int amount) {
/* 89 */     if (this.corner == HudComponent.HudCorner.TOP_LEFT || this.corner == HudComponent.HudCorner.TOP_RIGHT) {
/* 90 */       drawString(text, 0.0F, (amount * 10), -1, GuiSettings.infoShadow.booleanValue());
/*    */     } else {
/* 92 */       drawString(text, 0.0F, -(amount * 10), -1, GuiSettings.infoShadow.booleanValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 98 */   public static ChatFormatting getPotionColor(PotionEffect potion) { return ChatFormatting.GRAY; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\InfoComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */