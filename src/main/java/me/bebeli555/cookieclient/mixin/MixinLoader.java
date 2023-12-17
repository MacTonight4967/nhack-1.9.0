/*    */ package me.bebeli555.cookieclient.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ 
/*    */ public class MixinLoader
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public MixinLoader() {
/* 15 */     MixinBootstrap.init();
/* 16 */     Mixins.addConfigurations(new String[] { "mixins.cookieclient.json", "mixins.baritone.json" });
/* 17 */     MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public String[] getASMTransformerClass() { return new String[0]; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public String getModContainerClass() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/* 33 */   public String getSetupClass() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public String getAccessTransformerClass() { return null; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\MixinLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */