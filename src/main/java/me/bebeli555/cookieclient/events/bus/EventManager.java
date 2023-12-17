/*     */ package me.bebeli555.cookieclient.events.bus;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
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
/*     */ public class EventManager
/*     */   implements EventBus
/*     */ {
/*  26 */   private final Map<Object, List<Listener>> SUBSCRIPTION_CACHE = new ConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private final Map<Class<?>, List<Listener>> SUBSCRIPTION_MAP = new ConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private final List<EventBus> ATTACHED_BUSES = new ArrayList();
/*     */ 
/*     */   
/*     */   public void subscribe(Object object) {
/*     */   }
/*     */ 
/*     */   
/*  56 */   public void subscribeAll(Object... objects) { Arrays.stream(objects).forEach(this::subscribe); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public void subscribeAll(Iterable<Object> objects) { objects.forEach(this::subscribe); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsubscribe(Object object) {
/*  66 */     List<Listener> objectListeners = (List)this.SUBSCRIPTION_CACHE.get(object);
/*  67 */     if (objectListeners == null) {
/*     */       return;
/*     */     }
/*  70 */     this.SUBSCRIPTION_MAP.values().forEach(listeners -> listeners.removeIf(objectListeners::contains));
/*     */ 
/*     */     
/*  73 */     if (!this.ATTACHED_BUSES.isEmpty()) {
/*  74 */       this.ATTACHED_BUSES.forEach(bus -> bus.unsubscribe(object));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  79 */   public void unsubscribeAll(Object... objects) { Arrays.stream(objects).forEach(this::unsubscribe); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void unsubscribeAll(Iterable<Object> objects) { objects.forEach(this::unsubscribe); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void post(Object event) {
/*  90 */     List<Listener> listeners = (List)this.SUBSCRIPTION_MAP.get(event.getClass());
/*  91 */     if (listeners != null) {
/*  92 */       listeners.forEach(listener -> listener.invoke(event));
/*     */     }
/*     */     
/*  95 */     if (!this.ATTACHED_BUSES.isEmpty()) {
/*  96 */       this.ATTACHED_BUSES.forEach(bus -> bus.post(event));
/*     */     }
/*     */   }
/*     */   
/*     */   public void attach(EventBus bus) {
/* 101 */     if (!this.ATTACHED_BUSES.contains(bus)) {
/* 102 */       this.ATTACHED_BUSES.add(bus);
/*     */     }
/*     */   }
/*     */   
/*     */   public void detach(EventBus bus) {
/* 107 */     if (this.ATTACHED_BUSES.contains(bus)) {
/* 108 */       this.ATTACHED_BUSES.remove(bus);
/*     */     }
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
/* 122 */   private static boolean isValidField(Field field) { return (field.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(field.getType())); }
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
/*     */   private static Listener asListener(Object object, Field field) {
/*     */     try {
/* 137 */       boolean accessible = field.isAccessible();
/* 138 */       field.setAccessible(true);
/* 139 */       Listener listener = (Listener)field.get(object);
/* 140 */       field.setAccessible(accessible);
/*     */       
/* 142 */       if (listener == null) {
/* 143 */         return null;
/*     */       }
/* 145 */       if (listener.getPriority() > 5 || listener.getPriority() < 1) {
/* 146 */         throw new RuntimeException("Event Priority out of bounds! %s");
/*     */       }
/* 148 */       return listener;
/* 149 */     } catch (IllegalAccessException e) {
/* 150 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void subscribe(Listener listener) {
/* 160 */     List<Listener> listeners = (List)this.SUBSCRIPTION_MAP.computeIfAbsent(listener.getTarget(), target -> new CopyOnWriteArrayList());
/*     */     
/* 162 */     int index = 0;
/* 163 */     for (; index < listeners.size() && 
/* 164 */       listener.getPriority() >= ((Listener)listeners.get(index)).getPriority(); index++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     listeners.add(index, listener);
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */