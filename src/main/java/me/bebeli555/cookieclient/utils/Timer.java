/*    */ package me.bebeli555.cookieclient.utils;
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   public long ms;
/*    */   
/*  7 */   public Timer() { this.ms = 0L; }
/*    */ 
/*    */ 
/*    */   
/* 11 */   public boolean hasPassed(int ms) { return (System.currentTimeMillis() - this.ms >= ms); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public void reset() { this.ms = System.currentTimeMillis(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */