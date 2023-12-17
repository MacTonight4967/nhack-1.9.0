/*     */ package net.jodah.typetools;
/*     */ 
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ class ReifiedParameterizedType implements ParameterizedType {
/*     */   private final ParameterizedType original;
/*     */   private final Type[] reifiedTypeArguments;
/*     */   private final boolean[] loop;
/*  10 */   private int reified = 0;
/*     */   
/*     */   ReifiedParameterizedType(ParameterizedType original) {
/*  13 */     this.original = original;
/*  14 */     this.reifiedTypeArguments = new Type[original.getActualTypeArguments().length];
/*  15 */     this.loop = new boolean[original.getActualTypeArguments().length];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addReifiedTypeArgument(Type type) {
/*  27 */     if (this.reified >= this.reifiedTypeArguments.length) {
/*     */       return;
/*     */     }
/*  30 */     if (type == this) {
/*  31 */       this.loop[this.reified] = true;
/*     */     }
/*  33 */     this.reifiedTypeArguments[this.reified++] = type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public Type[] getActualTypeArguments() { return this.reifiedTypeArguments; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public Type getRawType() { return this.original.getRawType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public Type getOwnerType() { return this.original.getOwnerType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  56 */     Type ownerType = getOwnerType();
/*  57 */     Type rawType = getRawType();
/*  58 */     Type[] actualTypeArguments = getActualTypeArguments();
/*     */     
/*  60 */     StringBuilder sb = new StringBuilder();
/*     */     
/*  62 */     if (ownerType != null) {
/*  63 */       if (ownerType instanceof Class) {
/*  64 */         sb.append(((Class)ownerType).getName());
/*     */       } else {
/*  66 */         sb.append(ownerType.toString());
/*     */       } 
/*     */       
/*  69 */       sb.append("$");
/*     */       
/*  71 */       if (ownerType instanceof ParameterizedType) {
/*     */ 
/*     */         
/*  74 */         sb.append(rawType.getTypeName()
/*  75 */             .replace(((ParameterizedType)ownerType).getRawType().getTypeName() + "$", ""));
/*  76 */       } else if (rawType instanceof Class) {
/*  77 */         sb.append(((Class)rawType).getSimpleName());
/*     */       } else {
/*  79 */         sb.append(rawType.getTypeName());
/*     */       } 
/*     */     } else {
/*  82 */       sb.append(rawType.getTypeName());
/*     */     } 
/*     */     
/*  85 */     if (actualTypeArguments != null && actualTypeArguments.length > 0) {
/*  86 */       sb.append("<");
/*     */       
/*  88 */       for (int i = 0; i < actualTypeArguments.length; i++) {
/*  89 */         if (i != 0) {
/*  90 */           sb.append(", ");
/*     */         }
/*     */         
/*  93 */         Type t = actualTypeArguments[i];
/*     */         
/*  95 */         if (i >= this.reified) {
/*  96 */           sb.append("?");
/*  97 */         } else if (t == null) {
/*  98 */           sb.append("null");
/*  99 */         } else if (this.loop[i]) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 105 */           sb.append("...");
/*     */         } else {
/* 107 */           sb.append(t.getTypeName());
/*     */         } 
/*     */       } 
/* 110 */       sb.append(">");
/*     */     } 
/*     */     
/* 113 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 118 */     if (this == o) {
/* 119 */       return true;
/*     */     }
/* 121 */     if (o == null || getClass() != o.getClass()) {
/* 122 */       return false;
/*     */     }
/*     */     
/* 125 */     ReifiedParameterizedType that = (ReifiedParameterizedType)o;
/* 126 */     if (!this.original.equals(that.original)) {
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     if (this.reifiedTypeArguments.length != that.reifiedTypeArguments.length) {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     for (int i = 0; i < this.reifiedTypeArguments.length; i++) {
/* 135 */       if (this.loop[i] != that.loop[i]) {
/* 136 */         return false;
/*     */       }
/* 138 */       if (!this.loop[i])
/*     */       {
/*     */         
/* 141 */         if (this.reifiedTypeArguments[i] != that.reifiedTypeArguments[i])
/* 142 */           return false; 
/*     */       }
/*     */     } 
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 150 */     int result = this.original.hashCode();
/* 151 */     for (int i = 0; i < this.reifiedTypeArguments.length; i++) {
/* 152 */       if (!this.loop[i])
/*     */       {
/*     */         
/* 155 */         if (this.reifiedTypeArguments[i] instanceof ReifiedParameterizedType)
/* 156 */           result = 31 * result + this.reifiedTypeArguments[i].hashCode(); 
/*     */       }
/*     */     } 
/* 159 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\net\jodah\typetools\ReifiedParameterizedType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */