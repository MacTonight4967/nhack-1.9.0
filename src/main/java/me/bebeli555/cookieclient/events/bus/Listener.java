/*    */ package me.bebeli555.cookieclient.events.bus;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.jodah.typetools.TypeResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Listener<T>
/*    */   extends Object
/*    */   implements EventHook<T>
/*    */ {
/*    */   private final Class<T> target;
/*    */   private final EventHook<T> hook;
/*    */   private final Predicate<T>[] filters;
/*    */   private final byte priority;
/*    */   
/*    */   @SafeVarargs
/* 41 */   public Listener(EventHook<T> hook, Predicate... filters) { this(hook, (byte)3, filters); }
/*    */ 
/*    */ 
/*    */   
/*    */   @SafeVarargs
/*    */   public Listener(EventHook<T> hook, byte priority, Predicate... filters) {
/* 47 */     this.hook = hook;
/* 48 */     this.priority = priority;
/* 49 */     this.target = TypeResolver.resolveRawArgument(EventHook.class, hook.getClass());
/* 50 */     this.filters = filters;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public final Class<T> getTarget() { return this.target; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public final byte getPriority() { return this.priority; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void invoke(T event) {
/* 87 */     if (this.filters.length > 0)
/* 88 */       for (Predicate<T> filter : this.filters) {
/* 89 */         if (!filter.test(event))
/*    */           return; 
/*    */       }  
/* 92 */     this.hook.invoke(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\Listener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */