/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({Entity.class})
/*    */ public class MixinEntity
/*    */ {
/*    */   @Inject(method = {"turn"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void turn(float yaw, float pitch, CallbackInfo ci) {
/* 19 */     if (!Freecam.isToggled || Freecam.camera == null)
/* 20 */       return;  Entity entity = (Entity)this;
/*    */     
/* 22 */     if (entity.equals(Mod.mc.field_71439_g)) {
/* 23 */       Freecam.camera.func_70082_c(yaw, pitch);
/* 24 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */