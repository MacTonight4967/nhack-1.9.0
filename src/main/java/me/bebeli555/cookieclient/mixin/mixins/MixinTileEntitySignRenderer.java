/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.render.RenderSignEvent;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.renderer.tileentity.TileEntitySignRenderer.class})
/*    */ public class MixinTileEntitySignRenderer
/*    */ {
/*    */   @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/tileentity/TileEntitySign;signText:[Lnet/minecraft/util/text/ITextComponent;", opcode = 180))
/*    */   public ITextComponent[] getRenderViewEntity(TileEntitySign sign) {
/* 18 */     RenderSignEvent event = new RenderSignEvent();
/* 19 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 21 */     if (event.isCancelled()) {
/* 22 */       return new ITextComponent[0];
/*    */     }
/*    */     
/* 25 */     return sign.field_145915_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinTileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */