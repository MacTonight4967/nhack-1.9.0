/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ 
/*     */ public class GuiNode
/*     */   extends Mod {
/*   8 */   public static ArrayList<GuiNode> all = new ArrayList();
/*     */   
/*     */   public GuiNode parent;
/*     */   public String name;
/*  12 */   public String id = "";
/*  13 */   public String stringValue = "";
/*  14 */   public String defaultValue = "";
/*  15 */   public String modeName = "";
/*     */   public boolean isTypeable;
/*     */   public boolean onlyNumbers;
/*     */   public boolean acceptDoubleValues;
/*     */   public boolean isVisible;
/*     */   public boolean toggled;
/*     */   public boolean isLabel;
/*     */   public boolean isKeybind;
/*     */   public boolean isExtended;
/*     */   public Group group;
/*     */   public String[] description;
/*  26 */   public ArrayList<String> modes = new ArrayList();
/*  27 */   public ArrayList<ArrayList<String>> modeDescriptions = new ArrayList();
/*  28 */   public ArrayList<GuiNode> parentedNodes = new ArrayList();
/*  29 */   public ArrayList<ClickListener> clickListeners = new ArrayList();
/*  30 */   public ArrayList<KeyListener> keyListeners = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiNode(boolean dontAdd) {}
/*     */ 
/*     */   
/*  37 */   public GuiNode() { all.add(this); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSetting() {
/*  42 */     if (this.isTypeable || this.modes.size() != 0) {
/*  43 */       if (this.acceptDoubleValues) {
/*     */         try {
/*  45 */           Setting.getSettingWithId(this.id).setValue(Double.valueOf(Double.parseDouble(this.stringValue)));
/*  46 */         } catch (Exception e) {
/*  47 */           this.stringValue = "";
/*  48 */           Setting.getSettingWithId(this.id).setValue(Integer.valueOf(-1));
/*     */         } 
/*  50 */       } else if (this.onlyNumbers) {
/*     */         try {
/*  52 */           Setting.getSettingWithId(this.id).setValue(Integer.valueOf(Integer.parseInt(this.stringValue)));
/*  53 */         } catch (Exception e) {
/*  54 */           this.stringValue = "";
/*  55 */           Setting.getSettingWithId(this.id).setValue(Integer.valueOf(-1));
/*     */         }
/*     */       
/*  58 */       } else if (!this.isKeybind) {
/*  59 */         Setting.getSettingWithId(this.id).setValue(this.stringValue);
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/*  64 */         Setting.getSettingWithId(this.id).setValue(Boolean.valueOf(this.toggled));
/*  65 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void addClickListener(ClickListener listener) { this.clickListeners.add(listener); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void addKeyListener(KeyListener listener) { this.keyListeners.add(listener); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultValue() {
/*  83 */     if (this.isTypeable) {
/*  84 */       this.stringValue = this.defaultValue;
/*     */     } else {
/*  86 */       this.toggled = Boolean.parseBoolean(this.defaultValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void click() {
/*  92 */     if (this.isLabel) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  97 */     if (this.modes.size() != 0) {
/*  98 */       extend(false);
/*     */       
/*     */       try {
/* 101 */         this.stringValue = (String)this.modes.get(this.modes.indexOf(this.stringValue) + 1);
/* 102 */       } catch (IndexOutOfBoundsException e) {
/* 103 */         this.stringValue = (String)this.modes.get(0);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 108 */     if (!this.isTypeable && this.modes.size() == 0) {
/* 109 */       this.toggled = !this.toggled;
/*     */       
/* 111 */       for (Mod module : modules) {
/* 112 */         if (module.name.equals(this.name)) {
/* 113 */           if (this.toggled) {
/* 114 */             module.enable(); break;
/*     */           } 
/* 116 */           module.disable();
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 125 */     setSetting();
/* 126 */     notifyClickListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void extend(boolean extend) {
/* 134 */     for (GuiNode node : this.parentedNodes) {
/* 135 */       if (!node.modeName.isEmpty() && !node.modeName.equals(this.stringValue)) {
/*     */         continue;
/*     */       }
/*     */       
/* 139 */       node.isVisible = extend;
/* 140 */       this.isExtended = extend;
/*     */ 
/*     */       
/* 143 */       if (!extend) {
/* 144 */         for (GuiNode n : all) {
/* 145 */           if (n.id.contains(node.id)) {
/* 146 */             n.isVisible = false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId() {
/* 155 */     if (this.parent != null) {
/* 156 */       ArrayList<GuiNode> parents = getAllParents();
/*     */       
/* 158 */       for (int i = parents.size(); i-- > 0;) {
/* 159 */         this.id += ((GuiNode)parents.get(i)).name;
/*     */       }
/*     */       
/* 162 */       this.id += this.name;
/*     */     } else {
/* 164 */       this.id = this.name;
/*     */     } 
/*     */     
/* 167 */     this.id += this.modeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextColor() {
/* 172 */     if (this.isLabel)
/* 173 */       return GuiSettings.labelColor.intValue(); 
/* 174 */     if (this.toggled) {
/* 175 */       return GuiSettings.textColor.intValue();
/*     */     }
/* 177 */     return GuiSettings.textColorOff.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiNode getTopParent() {
/* 183 */     ArrayList<GuiNode> parents = getAllParents();
/* 184 */     return (GuiNode)parents.get(parents.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<GuiNode> getAllParents() {
/* 189 */     ArrayList<GuiNode> parents = new ArrayList<GuiNode>();
/*     */     
/* 191 */     GuiNode parent = this.parent;
/*     */     
/* 193 */     while (parent != null) {
/* 194 */       parents.add(parent);
/*     */       
/* 196 */       if (parent.parent != null) {
/* 197 */         parent = parent.parent;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     return parents;
/*     */   }
/*     */   
/*     */   public void notifyClickListeners() {
/* 209 */     for (ClickListener listener : this.clickListeners) {
/* 210 */       listener.clicked();
/*     */     }
/*     */   }
/*     */   
/*     */   public void notifyKeyListeners() {
/* 215 */     for (KeyListener listener : this.keyListeners)
/* 216 */       listener.pressed(); 
/*     */   }
/*     */   
/*     */   public static class ClickListener {
/*     */     public void clicked() {}
/*     */   }
/*     */   
/*     */   public static class KeyListener {
/*     */     public void pressed() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\GuiNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */