/*    */ package me.bebeli555.cookieclient.hud;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.hud.components.ArmorComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.ArrayListComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.CoordsComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.DirectionComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.InfoComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.LagNotifierComponent;
/*    */ import me.bebeli555.cookieclient.hud.components.WatermarkComponent;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ 
/*    */ public class HudEditor extends Mod {
/*    */   public static HudEditor module;
/* 16 */   public static HudEditorGui hudEditorGui = new HudEditorGui();
/*    */   
/*    */   public HudEditor() {
/* 19 */     super(Group.GUI, "HudEditor", new String[] { "Change the position of the HUD components" });
/* 20 */     initComponents();
/* 21 */     module = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 26 */     MinecraftForge.EVENT_BUS.register(hudEditorGui);
/* 27 */     mc.func_147108_a(hudEditorGui);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 32 */     MinecraftForge.EVENT_BUS.unregister(hudEditorGui);
/* 33 */     HudSettings.saveSettings();
/*    */   }
/*    */   
/*    */   public static void initComponents() {
/* 37 */     new ArrayListComponent();
/* 38 */     new WatermarkComponent();
/* 39 */     new ArmorComponent();
/* 40 */     new CoordsComponent();
/* 41 */     new DirectionComponent();
/* 42 */     new InfoComponent();
/* 43 */     new LagNotifierComponent();
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\HudEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */