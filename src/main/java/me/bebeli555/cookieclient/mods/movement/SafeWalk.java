/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMoveEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ 
/*    */ public class SafeWalk extends Mod {
/*    */   public static SafeWalk instance;
/*    */   
/*    */   public SafeWalk() {
/* 13 */     super(Group.MOVEMENT, "SafeWalk", new String[] { "Stops you from walking off ledges", "Acts like u would be sneaking" });
/*    */ 
/*    */ 
/*    */     
/* 17 */     this.onPlayerMove = new Listener(event -> {
/*    */           
/* 19 */           double x = event.x;
/* 20 */           double y = event.y;
/* 21 */           double z = event.z;
/*    */           
/* 23 */           if (mc.field_71439_g.field_70122_E && !mc.field_71439_g.field_70145_X) {
/*    */             double increment;
/* 25 */             for (increment = 0.05D; x != 0.0D && isOffsetBBEmpty(x, -1.0D, 0.0D); ) {
/* 26 */               if (x < increment && x >= -increment) {
/* 27 */                 x = 0.0D; continue;
/* 28 */               }  if (x > 0.0D) {
/* 29 */                 x -= increment; continue;
/*    */               } 
/* 31 */               x += increment;
/*    */             } 
/*    */ 
/*    */             
/* 35 */             while (z != 0.0D && isOffsetBBEmpty(0.0D, -1.0D, z)) {
/* 36 */               if (z < increment && z >= -increment) {
/* 37 */                 z = 0.0D; continue;
/* 38 */               }  if (z > 0.0D) {
/* 39 */                 z -= increment; continue;
/*    */               } 
/* 41 */               z += increment;
/*    */             } 
/*    */ 
/*    */             
/* 45 */             while (x != 0.0D && z != 0.0D && isOffsetBBEmpty(x, -1.0D, z)) {
/* 46 */               if (x < increment && x >= -increment) {
/* 47 */                 x = 0.0D;
/* 48 */               } else if (x > 0.0D) {
/* 49 */                 x -= increment;
/*    */               } else {
/* 51 */                 x += increment;
/*    */               } 
/*    */               
/* 54 */               if (z < increment && z >= -increment) {
/* 55 */                 z = 0.0D; continue;
/* 56 */               }  if (z > 0.0D) {
/* 57 */                 z -= increment; continue;
/*    */               } 
/* 59 */               z += increment;
/*    */             } 
/*    */           } 
/*    */ 
/*    */           
/* 64 */           event.x = x;
/* 65 */           event.y = y;
/* 66 */           event.z = z;
/* 67 */           event.cancel();
/*    */         }new java.util.function.Predicate[0]);
/*    */     instance = this;
/*    */   }
/* 71 */   public static boolean isOffsetBBEmpty(double x, double y, double z) { return mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(x, y, z)).isEmpty(); }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerMoveEvent> onPlayerMove;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\SafeWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */