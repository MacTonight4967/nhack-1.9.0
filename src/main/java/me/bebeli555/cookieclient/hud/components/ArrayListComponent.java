/*    */ package me.bebeli555.cookieclient.hud.components;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*    */ import me.bebeli555.cookieclient.hud.HudComponent;
/*    */ import me.bebeli555.cookieclient.utils.RainbowUtil;
/*    */ 
/*    */ public class ArrayListComponent
/*    */   extends HudComponent {
/* 11 */   private static RainbowUtil rainbow = new RainbowUtil();
/*    */   public static int lastArraylistSize;
/* 13 */   public static ArrayList<Mod> arraylist = new ArrayList();
/*    */ 
/*    */   
/* 16 */   public ArrayListComponent() { super(HudComponent.HudCorner.BOTTOM_RIGHT, "ArrayList"); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(float partialTicks) {
/* 21 */     super.onRender(partialTicks);
/*    */ 
/*    */     
/* 24 */     if (lastArraylistSize != arraylist.size()) {
/* 25 */       ArrayList<Mod> temp = new ArrayList<Mod>();
/*    */       
/* 27 */       while (!arraylist.isEmpty()) {
/* 28 */         Mod best = null;
/* 29 */         int longest = Integer.MIN_VALUE;
/* 30 */         ArrayList<Mod> temp2 = new ArrayList<Mod>();
/* 31 */         temp2.addAll(arraylist);
/* 32 */         for (Mod module : temp2) {
/* 33 */           String name = module.name;
/* 34 */           if (module.getRenderNumber() != -1) {
/* 35 */             name = name + " [" + module.getRenderNumber() + "]";
/*    */           }
/*    */           
/* 38 */           int lenght = mc.field_71466_p.func_78256_a(name);
/* 39 */           if (lenght > longest) {
/* 40 */             best = module;
/* 41 */             longest = lenght;
/*    */           } 
/*    */         } 
/*    */         
/* 45 */         temp.add(best);
/* 46 */         arraylist.remove(best);
/*    */       } 
/*    */       
/* 49 */       arraylist = temp;
/*    */     } 
/*    */     
/* 52 */     int i = 0;
/* 53 */     rainbow.setSpeed(GuiSettings.arrayListRainbowSpeed.intValue());
/* 54 */     rainbow.onUpdate();
/*    */     
/* 56 */     int amount = 0;
/* 57 */     ArrayList<Mod> temp = new ArrayList<Mod>();
/* 58 */     temp.addAll(arraylist);
/* 59 */     for (Mod module : temp) {
/* 60 */       if (module.isOn() && !module.isHidden()) {
/* 61 */         String text = module.name;
/* 62 */         if (module.getRenderNumber() != -1) {
/* 63 */           text = text + " " + g + "[" + w + module.getRenderNumber() + g + "]";
/*    */         }
/*    */ 
/*    */         
/* 67 */         i += 20;
/* 68 */         if (!GuiSettings.arrayListRainbowStatic.booleanValue() && i >= 355) {
/* 69 */           i = 0;
/*    */         }
/*    */ 
/*    */         
/* 73 */         int color = -13309071;
/* 74 */         if (GuiSettings.arrayListColorMode.stringValue().equals("Rainbow")) {
/* 75 */           color = rainbow.getRainbowColorAt(i);
/*    */         }
/*    */         
/* 78 */         boolean shadow = GuiSettings.arrayListShadow.booleanValue();
/*    */         
/* 80 */         if (this.corner == HudComponent.HudCorner.BOTTOM_RIGHT || this.corner == HudComponent.HudCorner.BOTTOM_LEFT) {
/* 81 */           drawString(text, 0.0F, -(amount * 10), color, shadow);
/*    */         } else {
/* 83 */           drawString(text, 0.0F, (amount * 10), color, shadow);
/*    */         } 
/*    */         
/* 86 */         amount++;
/*    */       } 
/*    */     } 
/*    */     
/* 90 */     lastArraylistSize = arraylist.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 95 */   public boolean shouldRender() { return GuiSettings.arrayList.booleanValue(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\hud\components\ArrayListComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */