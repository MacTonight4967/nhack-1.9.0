/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.entity.AttackEntityEvent;
/*    */ import me.bebeli555.cookieclient.events.entity.EntityJumpEvent;
/*    */ import me.bebeli555.cookieclient.events.entity.EntityPushEvent;
/*    */ import me.bebeli555.cookieclient.events.player.TravelEvent;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public abstract class MixinEntityPlayer
/*    */   extends EntityLivingBase
/*    */ {
/* 24 */   public MixinEntityPlayer(World worldIn) { super(worldIn); }
/*    */ 
/*    */   
/*    */   @Inject(method = {"applyEntityCollision"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void applyEntityCollision(Entity entity, CallbackInfo callbackInfo) {
/* 29 */     EntityPushEvent event = new EntityPushEvent(entity);
/* 30 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 32 */     if (event.isCancelled()) {
/* 33 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"isPushedByWater()Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isPushedByWater(CallbackInfoReturnable<Boolean> callbackInfo) {
/* 39 */     EntityPushEvent event = new EntityPushEvent(null);
/* 40 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 42 */     if (event.isCancelled()) {
/* 43 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"travel"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void travel(float strafe, float vertical, float forward, CallbackInfo callbackInfo) {
/* 49 */     TravelEvent event = new TravelEvent();
/* 50 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 52 */     if (event.isCancelled()) {
/* 53 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 54 */       callbackInfo.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"jump"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void jump(CallbackInfo callbackInfo) {
/* 60 */     EntityJumpEvent event = new EntityJumpEvent((EntityPlayer)this);
/* 61 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 63 */     if (event.isCancelled()) {
/* 64 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"attackTargetEntityWithCurrentItem"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void attackTargetEntityWithCurrentItem(Entity target, CallbackInfo callbackInfo) {
/* 70 */     AttackEntityEvent event = new AttackEntityEvent(target);
/* 71 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 73 */     if (event.isCancelled())
/* 74 */       callbackInfo.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */