/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {net.minecraft.client.Minecraft.class}, priority = 2147483647)
/*    */ public class MixinMinecraft
/*    */ {
/*    */   private ServerData oldServerData;
/*    */   
/*    */   @Inject(method = {"getCurrentServerData"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCurrentServerData(CallbackInfoReturnable<ServerData> cir) {
/* 20 */     if (Mod.mc.field_71422_O != null || Mod.mc.func_71356_B()) {
/* 21 */       cir.setReturnValue(Mod.mc.field_71422_O);
/* 22 */       if (Mod.mc.field_71422_O != null) {
/* 23 */         this.oldServerData = Mod.mc.field_71422_O;
/*    */       }
/*    */     } else {
/* 26 */       cir.setReturnValue(this.oldServerData);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */