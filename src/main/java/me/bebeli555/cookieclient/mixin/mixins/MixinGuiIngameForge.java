/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {net.minecraftforge.client.GuiIngameForge.class}, remap = false)
/*    */ public class MixinGuiIngameForge
/*    */ {
/*    */   @ModifyVariable(method = {"renderAir"}, at = @At(value = "STORE", ordinal = 0))
/* 16 */   private EntityPlayer renderAir$getRenderViewEntity(EntityPlayer renderViewEntity) { return getRenderViewEntity(renderViewEntity); }
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"renderHealth"}, at = @At(value = "STORE", ordinal = 0))
/* 21 */   private EntityPlayer renderHealth$getRenderViewEntity(EntityPlayer renderViewEntity) { return getRenderViewEntity(renderViewEntity); }
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"renderFood"}, at = @At(value = "STORE", ordinal = 0))
/* 26 */   private EntityPlayer renderFood$getRenderViewEntity(EntityPlayer renderViewEntity) { return getRenderViewEntity(renderViewEntity); }
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"renderHealthMount"}, at = @At(value = "STORE", ordinal = 0))
/* 31 */   private EntityPlayer renderHealthMount$getRenderViewEntity(EntityPlayer renderViewEntity) { return getRenderViewEntity(renderViewEntity); }
/*    */ 
/*    */   
/*    */   private EntityPlayer getRenderViewEntity(EntityPlayer renderViewEntity) {
/* 35 */     if (Freecam.isToggled && Mod.mc.field_71439_g != null) {
/* 36 */       return Mod.mc.field_71439_g;
/*    */     }
/*    */     
/* 39 */     return renderViewEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinGuiIngameForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */