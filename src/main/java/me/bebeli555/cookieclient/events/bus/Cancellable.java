/*    */ package me.bebeli555.cookieclient.events.bus;
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
/*    */ public class Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/* 22 */   public final void cancel() { this.cancelled = true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public final boolean isCancelled() { return this.cancelled; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\Cancellable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */