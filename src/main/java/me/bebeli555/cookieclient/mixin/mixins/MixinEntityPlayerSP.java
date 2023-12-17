/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.entity.EntityPushEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMoveEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.world.World;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({EntityPlayerSP.class})
/*    */ public abstract class MixinEntityPlayerSP
/*    */   extends MixinEntityPlayer
/*    */ {
/* 26 */   public MixinEntityPlayerSP(World worldIn) { super(worldIn); }
/*    */ 
/*    */   
/*    */   @Inject(method = {"pushOutOfBlocks(DDD)Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfo) {
/* 31 */     EntityPushEvent event = new EntityPushEvent(null);
/* 32 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 34 */     if (event.isCancelled()) {
/* 35 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"move"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void move(MoverType type, double x, double y, double z, CallbackInfo callbackInfo) {
/* 41 */     PlayerMoveEvent event = new PlayerMoveEvent(type, x, y, z);
/* 42 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 44 */     if (event.isCancelled()) {
/* 45 */       func_70091_d(type, event.x, event.y, event.z);
/* 46 */       callbackInfo.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void motionUpdate(CallbackInfo callbackInfo) {
/* 52 */     PlayerMotionUpdateEvent event = new PlayerMotionUpdateEvent();
/* 53 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 55 */     if (event.isCancelled()) {
/* 56 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onUpdate(CallbackInfo callbackInfo) {
/* 62 */     PlayerUpdateEvent event = new PlayerUpdateEvent();
/* 63 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 65 */     if (event.isCancelled()) {
/* 66 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"isCurrentViewEntity"}, at = {@At("RETURN")}, cancellable = true)
/*    */   protected void isCurrentViewEntity(CallbackInfoReturnable<Boolean> cir) {
/* 72 */     if (Freecam.isToggled && Freecam.camera != null) {
/* 73 */       cir.setReturnValue(Boolean.valueOf(Mod.mc.field_175622_Z.equals(Freecam.camera)));
/*    */     }
/*    */   }
/*    */   
/*    */   @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
/*    */   public void closeScreen(EntityPlayerSP player) {
/* 79 */     if (!GuiSettings.portalGui.booleanValue()) {
/* 80 */       player.func_71053_j();
/*    */     }
/*    */   }
/*    */   
/*    */   @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
/*    */   public void closeScreen(Minecraft minecraft, GuiScreen screen) {
/* 86 */     if (!GuiSettings.portalGui.booleanValue())
/* 87 */       Mod.mc.func_147108_a(screen); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */