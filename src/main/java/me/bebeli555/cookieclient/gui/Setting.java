/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ 
/*     */ public class Setting
/*     */ {
/*     */   public Mode mode;
/*     */   public String name;
/*     */   public String[] description;
/*     */   private Object value;
/*     */   public Object defaultValue;
/*     */   public Setting parent;
/*     */   public String id;
/*  15 */   public String modeName = "";
/*  16 */   public ArrayList<String> modes = new ArrayList();
/*  17 */   public ArrayList<ArrayList<String>> modeDescriptions = new ArrayList();
/*  18 */   public ArrayList<ValueChangedListener> listeners = new ArrayList();
/*     */   
/*  20 */   public static ArrayList<Setting> all = new ArrayList();
/*     */   
/*     */   public Setting(Mode mode, String name, Object defaultValue, String... description) {
/*  23 */     this.mode = mode;
/*  24 */     this.name = name;
/*  25 */     this.defaultValue = defaultValue;
/*  26 */     this.value = defaultValue;
/*  27 */     this.description = description;
/*  28 */     setId();
/*  29 */     all.add(this);
/*     */   }
/*     */   
/*     */   public Setting(Setting parent, Mode mode, String name, Object defaultValue, String... description) {
/*  33 */     this(mode, name, defaultValue, description);
/*  34 */     this.parent = parent;
/*  35 */     setId();
/*     */   }
/*     */   
/*     */   public Setting(Setting parent, String modeName, Mode mode, String name, Object defaultValue, String... description) {
/*  39 */     this(mode, name, defaultValue, description);
/*  40 */     this.parent = parent;
/*  41 */     this.modeName = modeName;
/*  42 */     setId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Setting(Setting parent, String name, String defaultValue, String[]... modes) {
/*  50 */     for (String[] mode : modes) {
/*  51 */       this.modes.add(mode[0]);
/*     */       
/*  53 */       ArrayList<String> descriptions = new ArrayList<String>();
/*  54 */       for (int i = 1; i < mode.length; i++) {
/*  55 */         descriptions.add(mode[i]);
/*     */       }
/*     */       
/*  58 */       this.modeDescriptions.add(descriptions);
/*     */     } 
/*     */     
/*  61 */     this.defaultValue = defaultValue;
/*  62 */     this.value = defaultValue;
/*  63 */     this.parent = parent;
/*  64 */     this.name = name;
/*  65 */     setId();
/*  66 */     all.add(this);
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/*  70 */     if (!this.value.equals(value)) {
/*  71 */       Object oldValue = this.value;
/*  72 */       this.value = value;
/*     */       
/*  74 */       for (ValueChangedListener listener : this.listeners) {
/*  75 */         if (!listener.onlyIfModuleIsOn || listener.module.isOn()) {
/*  76 */           listener.valueChanged();
/*     */           
/*  78 */           if (listener.cancelled) {
/*  79 */             this.value = oldValue;
/*  80 */             listener.cancelled = false;
/*  81 */             updateGuiNode();
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  90 */   public boolean booleanValue() { return ((Boolean)this.value).booleanValue(); }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  94 */     if (stringValue().isEmpty()) {
/*  95 */       return -1;
/*     */     }
/*     */     
/*     */     try {
/*  99 */       return ((Integer)this.value).intValue();
/* 100 */     } catch (ClassCastException e) {
/*     */       try {
/* 102 */         return Integer.parseUnsignedInt(((String)this.value).replace("0x", ""), 16);
/* 103 */       } catch (Exception e2) {
/*     */         try {
/* 105 */           return Integer.parseUnsignedInt(((String)this.defaultValue).replace("0x", ""), 16);
/* 106 */         } catch (Exception e3) {
/* 107 */           return -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateGuiNode() {
/* 114 */     if (this.mode == Mode.BOOLEAN) {
/* 115 */       (Settings.getGuiNodeFromId(this.id)).toggled = booleanValue();
/*     */     } else {
/* 117 */       (Settings.getGuiNodeFromId(this.id)).stringValue = stringValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public double doubleValue() {
/* 122 */     if (stringValue().isEmpty()) {
/* 123 */       return -1.0D;
/*     */     }
/*     */     
/*     */     try {
/* 127 */       return ((Double)this.value).doubleValue();
/* 128 */     } catch (Exception e) {
/*     */       try {
/* 130 */         return ((Integer)this.value).intValue();
/* 131 */       } catch (Exception e2) {
/*     */         try {
/* 133 */           return ((Double)this.defaultValue).doubleValue();
/* 134 */         } catch (Exception e3) {
/* 135 */           return -1.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 142 */   public String stringValue() { return String.valueOf(this.value); }
/*     */ 
/*     */   
/*     */   public void setId() {
/* 146 */     this.id = "";
/*     */     
/* 148 */     if (this.parent != null) {
/* 149 */       ArrayList<Setting> parents = getSettingParents();
/*     */       
/* 151 */       for (int i = parents.size(); i-- > 0;) {
/* 152 */         this.id += ((Setting)parents.get(i)).name;
/*     */       }
/*     */       
/* 155 */       this.id += this.name;
/*     */     } else {
/* 157 */       this.id = this.name;
/*     */     } 
/*     */     
/* 160 */     this.id += this.modeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Setting> getSettingParents() {
/* 167 */     ArrayList<Setting> parents = new ArrayList<Setting>();
/*     */     
/* 169 */     Setting parent = this.parent;
/*     */     
/* 171 */     while (parent != null) {
/* 172 */       parents.add(parent);
/*     */       
/* 174 */       if (parent.parent != null) {
/* 175 */         parent = parent.parent;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     return parents;
/*     */   }
/*     */ 
/*     */   
/* 187 */   public void addValueChangedListener(ValueChangedListener listener) { this.listeners.add(listener); }
/*     */ 
/*     */   
/*     */   public static Setting getSettingWithId(String id) {
/* 191 */     for (Setting setting : all) {
/* 192 */       if (setting.id.equals(id)) {
/* 193 */         return setting;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   public static class ValueChangedListener {
/*     */     public Mod module;
/*     */     public boolean onlyIfModuleIsOn = true;
/*     */     public boolean cancelled;
/*     */     
/*     */     public ValueChangedListener(Mod module, boolean onlyIfModuleIsOn) {
/* 206 */       this.module = module;
/* 207 */       this.onlyIfModuleIsOn = onlyIfModuleIsOn;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void valueChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     public void cancel() { this.cancelled = true; }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */