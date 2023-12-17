/*     */ package net.jodah.typetools;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeResolver
/*     */ {
/*  51 */   private static final Map<Class<?>, Reference<Map<TypeVariable<?>, Type>>> TYPE_VARIABLE_CACHE = Collections.synchronizedMap(new WeakHashMap());
/*     */   
/*     */   private static boolean RESOLVES_LAMBDAS;
/*     */   private static Method GET_CONSTANT_POOL;
/*     */   private static Method GET_CONSTANT_POOL_SIZE;
/*     */   private static Method GET_CONSTANT_POOL_METHOD_AT;
/*  57 */   private static final Map<String, Method> OBJECT_METHODS = new HashMap();
/*     */ 
/*     */   
/*     */   private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;
/*     */   
/*  62 */   private static final Double JAVA_VERSION = Double.valueOf(Double.parseDouble(System.getProperty("java.specification.version", "0")));
/*     */   static  {
/*     */     try {
/*  65 */       unsafe = (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>()
/*     */           {
/*     */             public Unsafe run() throws Exception {
/*  68 */               Field f = Unsafe.class.getDeclaredField("theUnsafe");
/*  69 */               f.setAccessible(true);
/*     */               
/*  71 */               return (Unsafe)f.get(null);
/*     */             }
/*     */           });
/*     */       
/*  75 */       GET_CONSTANT_POOL = Class.class.getDeclaredMethod("getConstantPool", new Class[0]);
/*  76 */       String constantPoolName = (JAVA_VERSION.doubleValue() < 9.0D) ? "sun.reflect.ConstantPool" : "jdk.internal.reflect.ConstantPool";
/*  77 */       Class<?> constantPoolClass = Class.forName(constantPoolName);
/*  78 */       GET_CONSTANT_POOL_SIZE = constantPoolClass.getDeclaredMethod("getSize", new Class[0]);
/*  79 */       GET_CONSTANT_POOL_METHOD_AT = constantPoolClass.getDeclaredMethod("getMethodAt", new Class[] { int.class });
/*     */ 
/*     */       
/*  82 */       Field overrideField = java.lang.reflect.AccessibleObject.class.getDeclaredField("override");
/*  83 */       long overrideFieldOffset = unsafe.objectFieldOffset(overrideField);
/*  84 */       unsafe.putBoolean(GET_CONSTANT_POOL, overrideFieldOffset, true);
/*  85 */       unsafe.putBoolean(GET_CONSTANT_POOL_SIZE, overrideFieldOffset, true);
/*  86 */       unsafe.putBoolean(GET_CONSTANT_POOL_METHOD_AT, overrideFieldOffset, true);
/*     */ 
/*     */ 
/*     */       
/*  90 */       Object constantPool = GET_CONSTANT_POOL.invoke(Object.class, new Object[0]);
/*  91 */       GET_CONSTANT_POOL_SIZE.invoke(constantPool, new Object[0]);
/*     */       
/*  93 */       for (Method method : Object.class.getDeclaredMethods()) {
/*  94 */         OBJECT_METHODS.put(method.getName(), method);
/*     */       }
/*  96 */       RESOLVES_LAMBDAS = true;
/*  97 */     } catch (Exception types) {}
/*     */ 
/*     */     
/* 100 */     types = new HashMap();
/* 101 */     types.put(boolean.class, Boolean.class);
/* 102 */     types.put(byte.class, Byte.class);
/* 103 */     types.put(char.class, Character.class);
/* 104 */     types.put(double.class, Double.class);
/* 105 */     types.put(float.class, Float.class);
/* 106 */     types.put(int.class, Integer.class);
/* 107 */     types.put(long.class, Long.class);
/* 108 */     types.put(short.class, Short.class);
/* 109 */     types.put(void.class, Void.class);
/* 110 */     PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(types);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Unknown {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static void enableCache() { CACHE_ENABLED = true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disableCache() {
/* 133 */     TYPE_VARIABLE_CACHE.clear();
/* 134 */     CACHE_ENABLED = false;
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
/*     */ 
/*     */   
/* 147 */   public static <T, S extends T> Class<?> resolveRawArgument(Class<T> type, Class<S> subType) { return resolveRawArgument(resolveGenericType(type, subType), subType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> resolveRawArgument(Type genericType, Class<?> subType) {
/* 161 */     Class[] arguments = resolveRawArguments(genericType, subType);
/* 162 */     if (arguments == null) {
/* 163 */       return Unknown.class;
/*     */     }
/* 165 */     if (arguments.length != 1) {
/* 166 */       throw new IllegalArgumentException("Expected 1 argument for generic type " + genericType + " but found " + arguments.length);
/*     */     }
/*     */     
/* 169 */     return arguments[0];
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
/*     */ 
/*     */ 
/*     */   
/* 183 */   public static <T, S extends T> Class<?>[] resolveRawArguments(Class<T> type, Class<S> subType) { return resolveRawArguments(resolveGenericType(type, subType), subType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public static <T, S extends T> Type reify(Class<T> type, Class<S> context) { return reify(resolveGenericType(type, context), getTypeVariableMap(context, null)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public static Type reify(Type type, Class<?> context) { return reify(type, getTypeVariableMap(context, null)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 284 */   public static Type reify(Type type) { return reify(type, new HashMap(false)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?>[] resolveRawArguments(Type genericType, Class<?> subType) {
/* 298 */     Class[] result = null;
/* 299 */     Class<?> functionalInterface = null;
/*     */ 
/*     */     
/* 302 */     if (RESOLVES_LAMBDAS && subType.isSynthetic()) {
/*     */ 
/*     */       
/* 305 */       Class<?> fi = (genericType instanceof ParameterizedType && ((ParameterizedType)genericType).getRawType() instanceof Class) ? (Class)((ParameterizedType)genericType).getRawType() : ((genericType instanceof Class) ? (Class)genericType : null);
/*     */       
/* 307 */       if (fi != null && fi.isInterface()) {
/* 308 */         functionalInterface = fi;
/*     */       }
/*     */     } 
/* 311 */     if (genericType instanceof ParameterizedType) {
/* 312 */       ParameterizedType paramType = (ParameterizedType)genericType;
/* 313 */       Type[] arguments = paramType.getActualTypeArguments();
/* 314 */       result = new Class[arguments.length];
/* 315 */       for (int i = 0; i < arguments.length; i++)
/* 316 */         result[i] = resolveRawClass(arguments[i], subType, functionalInterface); 
/* 317 */     } else if (genericType instanceof TypeVariable) {
/* 318 */       result = new Class[1];
/* 319 */       result[0] = resolveRawClass(genericType, subType, functionalInterface);
/* 320 */     } else if (genericType instanceof Class) {
/* 321 */       TypeVariable[] typeParams = ((Class)genericType).getTypeParameters();
/* 322 */       result = new Class[typeParams.length];
/* 323 */       for (int i = 0; i < typeParams.length; i++) {
/* 324 */         result[i] = resolveRawClass(typeParams[i], subType, functionalInterface);
/*     */       }
/*     */     } 
/* 327 */     return result;
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
/*     */   public static Type resolveGenericType(Class<?> type, Type subType) {
/*     */     Class<?> rawType;
/* 340 */     if (subType instanceof ParameterizedType) {
/* 341 */       rawType = (Class)((ParameterizedType)subType).getRawType();
/*     */     } else {
/* 343 */       rawType = (Class)subType;
/*     */     } 
/* 345 */     if (type.equals(rawType)) {
/* 346 */       return subType;
/*     */     }
/*     */     
/* 349 */     if (type.isInterface())
/* 350 */       for (Type superInterface : rawType.getGenericInterfaces()) {
/* 351 */         Type result; if (superInterface != null && !superInterface.equals(Object.class) && (
/* 352 */           result = resolveGenericType(type, superInterface)) != null) {
/* 353 */           return result;
/*     */         }
/*     */       }  
/* 356 */     Type superClass = rawType.getGenericSuperclass(); Type result;
/* 357 */     if (superClass != null && !superClass.equals(Object.class) && (
/* 358 */       result = resolveGenericType(type, superClass)) != null) {
/* 359 */       return result;
/*     */     }
/* 361 */     return null;
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
/*     */   
/* 373 */   public static Class<?> resolveRawClass(Type genericType, Class<?> subType) { return resolveRawClass(genericType, subType, null); }
/*     */ 
/*     */   
/*     */   private static Class<?> resolveRawClass(Type genericType, Class<?> subType, Class<?> functionalInterface) {
/* 377 */     if (genericType instanceof Class)
/* 378 */       return (Class)genericType; 
/* 379 */     if (genericType instanceof ParameterizedType)
/* 380 */       return resolveRawClass(((ParameterizedType)genericType).getRawType(), subType, functionalInterface); 
/* 381 */     if (genericType instanceof GenericArrayType) {
/* 382 */       GenericArrayType arrayType = (GenericArrayType)genericType;
/* 383 */       Class<?> component = resolveRawClass(arrayType.getGenericComponentType(), subType, functionalInterface);
/* 384 */       return Array.newInstance(component, 0).getClass();
/* 385 */     }  if (genericType instanceof TypeVariable) {
/* 386 */       TypeVariable<?> variable = (TypeVariable)genericType;
/* 387 */       genericType = (Type)getTypeVariableMap(subType, functionalInterface).get(variable);
/*     */       
/* 389 */       genericType = (genericType == null) ? resolveBound(variable) : resolveRawClass(genericType, subType, functionalInterface);
/*     */     } 
/*     */     
/* 392 */     return (genericType instanceof Class) ? (Class)genericType : Unknown.class;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Type reify(Type genericType, Map<TypeVariable<?>, Type> typeVariableTypeMap) {
/* 397 */     if (genericType == null)
/* 398 */       return null; 
/* 399 */     if (genericType instanceof Class) {
/* 400 */       return genericType;
/*     */     }
/* 402 */     return reify(genericType, typeVariableTypeMap, new HashMap());
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
/*     */   private static Type reify(Type genericType, Map<TypeVariable<?>, Type> typeVariableMap, Map<ParameterizedType, ReifiedParameterizedType> partial) {
/* 414 */     if (genericType instanceof Class) {
/* 415 */       return genericType;
/*     */     }
/*     */     
/* 418 */     if (genericType instanceof ParameterizedType) {
/* 419 */       ParameterizedType parameterizedType = (ParameterizedType)genericType;
/*     */       
/* 421 */       if (partial.containsKey(parameterizedType)) {
/* 422 */         ReifiedParameterizedType res = (ReifiedParameterizedType)partial.get(genericType);
/* 423 */         res.addReifiedTypeArgument(res);
/* 424 */         return res;
/*     */       } 
/* 426 */       Type[] genericTypeArguments = parameterizedType.getActualTypeArguments();
/* 427 */       ReifiedParameterizedType result = new ReifiedParameterizedType(parameterizedType);
/* 428 */       partial.put(parameterizedType, result);
/* 429 */       for (Type genericTypeArgument : genericTypeArguments) {
/* 430 */         Type reified = reify(genericTypeArgument, typeVariableMap, partial);
/*     */ 
/*     */         
/* 433 */         if (reified != result) {
/* 434 */           result.addReifiedTypeArgument(reified);
/*     */         }
/*     */       } 
/* 437 */       return result;
/* 438 */     }  if (genericType instanceof GenericArrayType) {
/* 439 */       GenericArrayType genericArrayType = (GenericArrayType)genericType;
/* 440 */       Type genericComponentType = genericArrayType.getGenericComponentType();
/* 441 */       Type reifiedComponentType = reify(genericArrayType.getGenericComponentType(), typeVariableMap, partial);
/*     */       
/* 443 */       if (genericComponentType == reifiedComponentType) {
/* 444 */         return genericComponentType;
/*     */       }
/* 446 */       if (reifiedComponentType instanceof Class) {
/* 447 */         return Array.newInstance((Class)reifiedComponentType, 0).getClass();
/*     */       }
/* 449 */       throw new UnsupportedOperationException("Attempted to reify generic array type, whose generic component type could not be reified to some Class<?>. Handling for this case is not implemented");
/*     */     } 
/*     */     
/* 452 */     if (genericType instanceof TypeVariable) {
/* 453 */       TypeVariable<?> typeVariable = (TypeVariable)genericType;
/* 454 */       Type mapping = (Type)typeVariableMap.get(typeVariable);
/* 455 */       if (mapping != null) {
/* 456 */         return reify(mapping, typeVariableMap, partial);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       return reify(typeVariable.getBounds()[0], typeVariableMap, partial);
/* 463 */     }  if (genericType instanceof WildcardType) {
/* 464 */       WildcardType wildcardType = (WildcardType)genericType;
/* 465 */       Type[] upperBounds = wildcardType.getUpperBounds();
/* 466 */       Type[] lowerBounds = wildcardType.getLowerBounds();
/* 467 */       if (upperBounds.length == 1 && lowerBounds.length == 0) {
/* 468 */         return reify(upperBounds[0], typeVariableMap, partial);
/*     */       }
/* 470 */       throw new UnsupportedOperationException("Attempted to reify wildcard type with name '" + wildcardType
/* 471 */           .getTypeName() + "' which has " + upperBounds.length + " upper bounds and " + lowerBounds.length + " lower bounds. Reification of wildcard types is only supported for the trivial case of exactly 1 upper bound and 0 lower bounds.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 476 */     throw new UnsupportedOperationException("Reification of type with name '" + genericType
/* 477 */         .getTypeName() + "' and class name '" + genericType
/* 478 */         .getClass().getName() + "' is not implemented.");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<TypeVariable<?>, Type> getTypeVariableMap(Class<?> targetType, Class<?> functionalInterface) {
/* 483 */     Reference<Map<TypeVariable<?>, Type>> ref = (Reference)TYPE_VARIABLE_CACHE.get(targetType);
/* 484 */     Map<TypeVariable<?>, Type> map = (ref != null) ? (Map)ref.get() : null;
/*     */     
/* 486 */     if (map == null) {
/* 487 */       map = new HashMap<TypeVariable<?>, Type>();
/*     */ 
/*     */       
/* 490 */       if (functionalInterface != null) {
/* 491 */         populateLambdaArgs(functionalInterface, targetType, map);
/*     */       }
/*     */       
/* 494 */       populateSuperTypeArgs(targetType.getGenericInterfaces(), map, (functionalInterface != null));
/*     */ 
/*     */       
/* 497 */       Type genericType = targetType.getGenericSuperclass();
/* 498 */       Class<?> type = targetType.getSuperclass();
/* 499 */       while (type != null && !Object.class.equals(type)) {
/* 500 */         if (genericType instanceof ParameterizedType)
/* 501 */           populateTypeArgs((ParameterizedType)genericType, map, false); 
/* 502 */         populateSuperTypeArgs(type.getGenericInterfaces(), map, false);
/*     */         
/* 504 */         genericType = type.getGenericSuperclass();
/* 505 */         type = type.getSuperclass();
/*     */       } 
/*     */ 
/*     */       
/* 509 */       type = targetType;
/* 510 */       while (type.isMemberClass()) {
/* 511 */         genericType = type.getGenericSuperclass();
/* 512 */         if (genericType instanceof ParameterizedType) {
/* 513 */           populateTypeArgs((ParameterizedType)genericType, map, (functionalInterface != null));
/*     */         }
/* 515 */         type = type.getEnclosingClass();
/*     */       } 
/*     */       
/* 518 */       if (CACHE_ENABLED) {
/* 519 */         TYPE_VARIABLE_CACHE.put(targetType, new WeakReference(map));
/*     */       }
/*     */     } 
/* 522 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populateSuperTypeArgs(Type[] types, Map<TypeVariable<?>, Type> map, boolean depthFirst) {
/* 530 */     for (Type type : types) {
/* 531 */       if (type instanceof ParameterizedType) {
/* 532 */         ParameterizedType parameterizedType = (ParameterizedType)type;
/* 533 */         if (!depthFirst)
/* 534 */           populateTypeArgs(parameterizedType, map, depthFirst); 
/* 535 */         Type rawType = parameterizedType.getRawType();
/* 536 */         if (rawType instanceof Class)
/* 537 */           populateSuperTypeArgs(((Class)rawType).getGenericInterfaces(), map, depthFirst); 
/* 538 */         if (depthFirst)
/* 539 */           populateTypeArgs(parameterizedType, map, depthFirst); 
/* 540 */       } else if (type instanceof Class) {
/* 541 */         populateSuperTypeArgs(((Class)type).getGenericInterfaces(), map, depthFirst);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populateTypeArgs(ParameterizedType type, Map<TypeVariable<?>, Type> map, boolean depthFirst) {
/* 550 */     if (type.getRawType() instanceof Class) {
/* 551 */       TypeVariable[] typeVariables = ((Class)type.getRawType()).getTypeParameters();
/* 552 */       Type[] typeArguments = type.getActualTypeArguments();
/*     */       
/* 554 */       if (type.getOwnerType() != null) {
/* 555 */         Type owner = type.getOwnerType();
/* 556 */         if (owner instanceof ParameterizedType) {
/* 557 */           populateTypeArgs((ParameterizedType)owner, map, depthFirst);
/*     */         }
/*     */       } 
/* 560 */       for (int i = 0; i < typeArguments.length; ) {
/* 561 */         TypeVariable<?> variable = typeVariables[i];
/* 562 */         Type typeArgument = typeArguments[i];
/*     */         
/* 564 */         if (typeArgument instanceof Class) {
/* 565 */           map.put(variable, typeArgument); continue;
/* 566 */         }  if (typeArgument instanceof GenericArrayType) {
/* 567 */           map.put(variable, typeArgument); continue;
/* 568 */         }  if (typeArgument instanceof ParameterizedType) {
/* 569 */           map.put(variable, typeArgument); continue;
/* 570 */         }  if (typeArgument instanceof TypeVariable) {
/* 571 */           TypeVariable<?> typeVariableArgument = (TypeVariable)typeArgument;
/* 572 */           if (depthFirst) {
/* 573 */             Type existingType = (Type)map.get(variable);
/* 574 */             if (existingType != null) {
/* 575 */               map.put(typeVariableArgument, existingType);
/*     */               
/*     */               continue;
/*     */             } 
/*     */           } 
/* 580 */           Type resolvedType = (Type)map.get(typeVariableArgument);
/* 581 */           if (resolvedType == null)
/* 582 */             resolvedType = resolveBound(typeVariableArgument); 
/* 583 */           map.put(variable, resolvedType);
/*     */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type resolveBound(TypeVariable<?> typeVariable) {
/* 593 */     Type[] bounds = typeVariable.getBounds();
/* 594 */     if (bounds.length == 0) {
/* 595 */       return Unknown.class;
/*     */     }
/* 597 */     Type bound = bounds[0];
/* 598 */     if (bound instanceof TypeVariable) {
/* 599 */       bound = resolveBound((TypeVariable)bound);
/*     */     }
/* 601 */     return (bound == Object.class) ? Unknown.class : bound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populateLambdaArgs(Class<?> functionalInterface, Class<?> lambdaType, Map<TypeVariable<?>, Type> map) {
/* 609 */     if (RESOLVES_LAMBDAS)
/*     */     {
/* 611 */       for (Method m : functionalInterface.getMethods()) {
/* 612 */         if (!isDefaultMethod(m) && !Modifier.isStatic(m.getModifiers()) && !m.isBridge()) {
/*     */           
/* 614 */           Method objectMethod = (Method)OBJECT_METHODS.get(m.getName());
/* 615 */           if (objectMethod == null || !Arrays.equals(m.getTypeParameters(), objectMethod.getTypeParameters())) {
/*     */ 
/*     */ 
/*     */             
/* 619 */             Type returnTypeVar = m.getGenericReturnType();
/* 620 */             Type[] paramTypeVars = m.getGenericParameterTypes();
/*     */             
/* 622 */             Member member = getMemberRef(lambdaType);
/* 623 */             if (member == null) {
/*     */               return;
/*     */             }
/*     */             
/* 627 */             if (returnTypeVar instanceof TypeVariable) {
/*     */               
/* 629 */               Class<?> returnType = (member instanceof Method) ? ((Method)member).getReturnType() : ((Constructor)member).getDeclaringClass();
/* 630 */               returnType = wrapPrimitives(returnType);
/* 631 */               if (!returnType.equals(Void.class)) {
/* 632 */                 map.put((TypeVariable)returnTypeVar, returnType);
/*     */               }
/*     */             } 
/*     */             
/* 636 */             Class[] arguments = (member instanceof Method) ? ((Method)member).getParameterTypes() : ((Constructor)member).getParameterTypes();
/*     */ 
/*     */             
/* 639 */             int paramOffset = 0;
/* 640 */             if (paramTypeVars.length > 0 && paramTypeVars[0] instanceof TypeVariable && paramTypeVars.length == arguments.length + 1) {
/*     */               
/* 642 */               Class<?> instanceType = member.getDeclaringClass();
/* 643 */               map.put((TypeVariable)paramTypeVars[0], instanceType);
/* 644 */               paramOffset = 1;
/*     */             } 
/*     */ 
/*     */             
/* 648 */             int argOffset = 0;
/* 649 */             if (paramTypeVars.length < arguments.length) {
/* 650 */               argOffset = arguments.length - paramTypeVars.length;
/*     */             }
/*     */ 
/*     */             
/* 654 */             for (int i = 0; i + argOffset < arguments.length; i++) {
/* 655 */               if (paramTypeVars[i] instanceof TypeVariable) {
/* 656 */                 map.put((TypeVariable)paramTypeVars[i + paramOffset], wrapPrimitives(arguments[i + argOffset]));
/*     */               }
/*     */             } 
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 666 */   private static boolean isDefaultMethod(Method m) { return (JAVA_VERSION.doubleValue() >= 1.8D && m.isDefault()); }
/*     */ 
/*     */   
/*     */   private static Member getMemberRef(Class<?> type) {
/*     */     Object constantPool;
/*     */     try {
/* 672 */       constantPool = GET_CONSTANT_POOL.invoke(type, new Object[0]);
/* 673 */     } catch (Exception ignore) {
/* 674 */       return null;
/*     */     } 
/*     */     
/* 677 */     Member result = null;
/* 678 */     for (int i = getConstantPoolSize(constantPool) - 1; i >= 0; i--) {
/* 679 */       Member member = getConstantPoolMethodAt(constantPool, i);
/*     */       
/* 681 */       if (member != null && (!(member instanceof Constructor) || 
/*     */         
/* 683 */         !member.getDeclaringClass().getName().equals("java.lang.invoke.SerializedLambda")) && 
/* 684 */         !member.getDeclaringClass().isAssignableFrom(type)) {
/*     */ 
/*     */         
/* 687 */         result = member;
/*     */ 
/*     */         
/* 690 */         if (!(member instanceof Method) || !isAutoBoxingMethod((Method)member))
/*     */           break; 
/*     */       } 
/*     */     } 
/* 694 */     return result;
/*     */   }
/*     */   
/*     */   private static boolean isAutoBoxingMethod(Method method) {
/* 698 */     Class[] parameters = method.getParameterTypes();
/* 699 */     return (method.getName().equals("valueOf") && parameters.length == 1 && parameters[0].isPrimitive() && 
/* 700 */       wrapPrimitives(parameters[0]).equals(method.getDeclaringClass()));
/*     */   }
/*     */ 
/*     */   
/* 704 */   private static Class<?> wrapPrimitives(Class<?> clazz) { return clazz.isPrimitive() ? (Class)PRIMITIVE_WRAPPERS.get(clazz) : clazz; }
/*     */ 
/*     */   
/*     */   private static int getConstantPoolSize(Object constantPool) {
/*     */     try {
/* 709 */       return ((Integer)GET_CONSTANT_POOL_SIZE.invoke(constantPool, new Object[0])).intValue();
/* 710 */     } catch (Exception ignore) {
/* 711 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Member getConstantPoolMethodAt(Object constantPool, int i) {
/*     */     try {
/* 717 */       return (Member)GET_CONSTANT_POOL_METHOD_AT.invoke(constantPool, new Object[] { Integer.valueOf(i) });
/* 718 */     } catch (Exception ignore) {
/* 719 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\net\jodah\typetools\TypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */