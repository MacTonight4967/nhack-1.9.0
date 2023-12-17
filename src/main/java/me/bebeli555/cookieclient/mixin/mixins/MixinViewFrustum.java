/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.ViewFrustum.class})
/*    */ public abstract class MixinViewFrustum
/*    */ {
/*    */   @Shadow
/*    */   public RenderChunk[] field_178164_f;
/*    */   @Shadow
/*    */   protected int field_178165_d;
/*    */   
/*    */   @Inject(method = {"updateChunkPositions"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void updateChunkPositionsHead(double viewEntityX, double viewEntityZ, CallbackInfo ci) {
/* 29 */     if (!Freecam.isToggled)
/*    */       return; 
/* 31 */     EntityPlayerSP player = Mod.mc.field_71439_g;
/* 32 */     if (player == null)
/*    */       return; 
/* 34 */     int centerX = MathHelper.func_76128_c(player.field_70165_t) - 8;
/* 35 */     int centerZ = MathHelper.func_76128_c(player.field_70161_v) - 8;
/*    */     
/* 37 */     int multipliedCountX = this.field_178165_d * 16;
/*    */     
/* 39 */     for (int x = 0; x < this.field_178165_d; x++) {
/* 40 */       int posX = func_178157_a(centerX, multipliedCountX, x);
/*    */       
/* 42 */       for (int z = 0; z < this.field_178166_e; z++) {
/* 43 */         int poxZ = func_178157_a(centerZ, multipliedCountX, z);
/*    */         
/* 45 */         for (int y = 0; y < this.field_178168_c; y++) {
/* 46 */           int poxY = y * 16;
/* 47 */           RenderChunk renderchunk = this.field_178164_f[(z * this.field_178168_c + y) * this.field_178165_d + x];
/* 48 */           renderchunk.func_189562_a(posX, poxY, poxZ);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     ci.cancel();
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   protected int field_178168_c;
/*    */   @Shadow
/*    */   protected int field_178166_e;
/*    */   
/*    */   @Shadow
/*    */   protected abstract int func_178157_a(int paramInt1, int paramInt2, int paramInt3);
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinViewFrustum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */