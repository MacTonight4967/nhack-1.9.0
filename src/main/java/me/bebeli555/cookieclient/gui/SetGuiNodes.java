/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetGuiNodes
/*     */ {
/*     */   public static void setGuiNodes() {
/*     */     try {
/*  13 */       for (Mod module : Mod.modules) {
/*     */         GuiNode mainNode;
/*  15 */         Setting s = null;
/*     */         
/*  17 */         if (module.name.isEmpty()) {
/*  18 */           mainNode = new GuiNode(true);
/*  19 */           mainNode.group = module.group;
/*     */         } else {
/*  21 */           mainNode = new GuiNode();
/*  22 */           mainNode.group = module.group;
/*  23 */           mainNode.name = module.name;
/*  24 */           mainNode.description = module.description;
/*  25 */           mainNode.group = module.group;
/*  26 */           mainNode.isVisible = true;
/*  27 */           mainNode.setId();
/*  28 */           s = new Setting(Mode.BOOLEAN, mainNode.name, Boolean.valueOf(false), mainNode.description);
/*     */         } 
/*     */         
/*  31 */         for (Field field : module.getClass().getFields()) {
/*  32 */           Class<?> myType = Setting.class;
/*     */           
/*  34 */           if (field.getType().isAssignableFrom(myType)) {
/*  35 */             Setting setting = (Setting)field.get(module);
/*  36 */             if (!mainNode.id.isEmpty()) {
/*  37 */               setting.id = mainNode.id + setting.id;
/*     */             }
/*     */             
/*  40 */             GuiNode node = new GuiNode();
/*  41 */             node.name = setting.name;
/*  42 */             node.description = setting.description;
/*  43 */             node.defaultValue = String.valueOf(setting.defaultValue);
/*  44 */             node.group = mainNode.group;
/*  45 */             node.modeName = setting.modeName;
/*     */             
/*  47 */             if (setting.parent != null) {
/*  48 */               GuiNode p = Settings.getGuiNodeFromId(setting.parent.id);
/*  49 */               node.parent = p;
/*  50 */               p.parentedNodes.add(node);
/*  51 */             } else if (!mainNode.id.isEmpty()) {
/*  52 */               node.parent = mainNode;
/*  53 */               mainNode.parentedNodes.add(node);
/*     */             } else {
/*  55 */               node.isVisible = true;
/*     */             } 
/*     */             
/*  58 */             if (setting.mode == Mode.TEXT) {
/*  59 */               node.isTypeable = true;
/*  60 */             } else if (setting.mode == Mode.INTEGER) {
/*  61 */               node.isTypeable = true;
/*  62 */               node.onlyNumbers = true;
/*  63 */             } else if (setting.mode == Mode.DOUBLE) {
/*  64 */               node.isTypeable = true;
/*  65 */               node.onlyNumbers = true;
/*  66 */               node.acceptDoubleValues = true;
/*  67 */             } else if (setting.mode == Mode.LABEL) {
/*  68 */               node.isLabel = true;
/*  69 */             } else if (setting.modes.size() != 0) {
/*  70 */               node.modes = setting.modes;
/*  71 */               node.modeDescriptions = setting.modeDescriptions;
/*     */             } 
/*     */             
/*  74 */             if (node.isTypeable || node.modes.size() != 0) {
/*  75 */               node.defaultValue = setting.stringValue();
/*  76 */               node.stringValue = setting.stringValue();
/*     */             } else {
/*     */               try {
/*  79 */                 node.toggled = setting.booleanValue();
/*  80 */               } catch (Exception e) {
/*  81 */                 node.defaultValue = setting.stringValue();
/*  82 */                 node.stringValue = setting.stringValue();
/*     */               } 
/*     */             } 
/*     */             
/*  86 */             node.setId();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*  91 */         GuiNode keybind = new GuiNode();
/*  92 */         keybind.isVisible = true;
/*  93 */         if (s != null) {
/*  94 */           mainNode.parentedNodes.add(keybind);
/*  95 */           keybind.description = new String[] { "Keybind for " + mainNode.name };
/*  96 */           keybind.parent = mainNode;
/*  97 */           keybind.isVisible = false;
/*     */         } else {
/*  99 */           keybind.description = new String[] { "Keybind for " + module.group.name };
/*     */         } 
/* 101 */         keybind.group = module.group;
/* 102 */         keybind.isTypeable = true;
/* 103 */         keybind.isKeybind = true;
/* 104 */         keybind.name = "Keybind";
/* 105 */         keybind.setId();
/*     */ 
/*     */         
/* 108 */         if (s == null) {
/*     */           continue;
/*     */         }
/* 111 */         GuiNode hidden = new GuiNode();
/* 112 */         hidden.isVisible = true;
/* 113 */         mainNode.parentedNodes.add(hidden);
/* 114 */         hidden.description = new String[] { "Hides the module in the HUD arraylist" };
/* 115 */         hidden.parent = mainNode;
/* 116 */         hidden.isVisible = false;
/* 117 */         hidden.group = module.group;
/* 118 */         hidden.name = "Hidden";
/* 119 */         hidden.setId();
/*     */       }
/*     */     
/* 122 */     } catch (Exception e) {
/* 123 */       System.out.println("CookieClient - Exception setting gui nodes");
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaults() {
/* 131 */     if (!Settings.settings.exists())
/* 132 */       for (Mod module : Mod.modules) {
/* 133 */         if (module.defaultOn) {
/* 134 */           Settings.getGuiNodeFromId(module.name).click();
/*     */         }
/*     */         
/* 137 */         if (module.defaultHidden)
/* 138 */           Settings.getGuiNodeFromId(module.name + "Hidden").click(); 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\SetGuiNodes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */