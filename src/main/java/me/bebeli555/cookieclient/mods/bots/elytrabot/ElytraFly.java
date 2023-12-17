/*     */ package me.bebeli555.cookieclient.mods.bots.elytrabot;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.player.TravelEvent;
/*     */ import net.minecraft.util.math.BlockPos;
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
/*     */ public class ElytraFly
/*     */   extends Mod
/*     */ {
/* 107 */   public ElytraFly() { this.onTravel = new Listener(event -> 
/*     */         
/* 109 */         event.cancel(), new java.util.function.Predicate[0]); }
/*     */   
/*     */   public static ElytraFly elytraFly = new ElytraFly();
/*     */   @EventHandler
/*     */   private Listener<TravelEvent> onTravel;
/*     */   
/*     */   public static void toggle(boolean on) {
/*     */     if (on) {
/*     */       Mod.EVENT_BUS.subscribe(elytraFly);
/*     */     } else {
/*     */       Mod.EVENT_BUS.unsubscribe(elytraFly);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setMotion(BlockPos pos, BlockPos next, BlockPos previous) {
/*     */     double x = 0.0D, y = 0.0D, z = 0.0D;
/*     */     double xDiff = pos.func_177958_n() + 0.5D - mc.field_71439_g.field_70165_t;
/*     */     double yDiff = pos.func_177956_o() + 0.4D - mc.field_71439_g.field_70163_u;
/*     */     double zDiff = pos.func_177952_p() + 0.5D - mc.field_71439_g.field_70161_v;
/*     */     double speed = ElytraBot.elytraFlySpeed.doubleValue();
/*     */     int amount = 0;
/*     */     try {
/*     */       if (Math.abs(next.func_177958_n() - previous.func_177958_n()) > 0)
/*     */         amount++; 
/*     */       if (Math.abs(next.func_177956_o() - previous.func_177956_o()) > 0)
/*     */         amount++; 
/*     */       if (Math.abs(next.func_177952_p() - previous.func_177952_p()) > 0)
/*     */         amount++; 
/*     */       if (amount > 1) {
/*     */         speed = ElytraBot.elytraFlyManuverSpeed.doubleValue();
/*     */         if (next.func_177958_n() - previous.func_177958_n() == next.func_177952_p() - previous.func_177952_p() && next.func_177956_o() - previous.func_177956_o() == 0 && ((xDiff >= 1.0D && zDiff >= 1.0D) || (xDiff <= -1.0D && zDiff <= -1.0D)))
/*     */           speed = ElytraBot.elytraFlySpeed.doubleValue(); 
/*     */       } 
/*     */     } catch (Exception nullPointerProbablyIdk) {
/*     */       speed = ElytraBot.elytraFlyManuverSpeed.doubleValue();
/*     */     } 
/*     */     if ((int)xDiff > 0) {
/*     */       x = speed;
/*     */     } else if ((int)xDiff < 0) {
/*     */       x = -speed;
/*     */     } 
/*     */     if ((int)yDiff > 0) {
/*     */       y = ElytraBot.elytraFlyManuverSpeed.doubleValue();
/*     */     } else if ((int)yDiff < 0) {
/*     */       y = -ElytraBot.elytraFlyManuverSpeed.doubleValue();
/*     */     } 
/*     */     if ((int)zDiff > 0) {
/*     */       z = speed;
/*     */     } else if ((int)zDiff < 0) {
/*     */       z = -speed;
/*     */     } 
/*     */     mc.field_71439_g.field_70159_w = x;
/*     */     mc.field_71439_g.field_70181_x = y;
/*     */     mc.field_71439_g.field_70179_y = z;
/*     */     double centerSpeed = 0.2D;
/*     */     double centerCheck = 0.1D;
/*     */     if (x == 0.0D)
/*     */       if (xDiff > centerCheck) {
/*     */         mc.field_71439_g.field_70159_w = centerSpeed;
/*     */       } else if (xDiff < -centerCheck) {
/*     */         mc.field_71439_g.field_70159_w = -centerSpeed;
/*     */       } else {
/*     */         mc.field_71439_g.field_70159_w = 0.0D;
/*     */       }  
/*     */     if (y == 0.0D)
/*     */       if (yDiff > centerCheck) {
/*     */         mc.field_71439_g.field_70181_x = centerSpeed;
/*     */       } else if (yDiff < -centerCheck) {
/*     */         mc.field_71439_g.field_70181_x = -centerSpeed;
/*     */       } else {
/*     */         mc.field_71439_g.field_70181_x = 0.0D;
/*     */       }  
/*     */     if (z == 0.0D)
/*     */       if (zDiff > centerCheck) {
/*     */         mc.field_71439_g.field_70179_y = centerSpeed;
/*     */       } else if (zDiff < -centerCheck) {
/*     */         mc.field_71439_g.field_70179_y = -centerSpeed;
/*     */       } else {
/*     */         mc.field_71439_g.field_70179_y = 0.0D;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\elytrabot\ElytraFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */