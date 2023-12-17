/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ 
/*    */ public class Speed extends Mod {
/* 12 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Integer.valueOf(1), new String[] { "How fast it goes" }); @EventHandler
/*    */   private Listener<PlayerUpdateEvent> onPlayerUpdate;
/*    */   public Speed() {
/* 15 */     super(Group.MOVEMENT, "Speed", new String[] { "Go faster on ground" });
/*    */ 
/*    */     
/* 18 */     this.onPlayerUpdate = new Listener(event -> {
/*    */           
/* 20 */           if (!EntitySpeed.isInputting()) {
/*    */             return;
/*    */           }
/*    */           
/* 24 */           float yaw = calculateYaw();
/* 25 */           mc.field_175622_Z.field_70159_w = -(Math.sin(yaw) * speed.doubleValue());
/* 26 */           mc.field_175622_Z.field_70179_y = Math.cos(yaw) * speed.doubleValue();
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   public static float calculateYaw() {
/* 30 */     rotationYaw = mc.field_71439_g.field_70177_z;
/* 31 */     if (mc.field_71439_g.field_191988_bg < 0.0F) {
/* 32 */       rotationYaw += 180.0F;
/*    */     }
/*    */     
/* 35 */     float n = 1.0F;
/* 36 */     if (mc.field_71439_g.field_191988_bg < 0.0F) {
/* 37 */       n = -0.5F;
/* 38 */     } else if (mc.field_71439_g.field_191988_bg > 0.0F) {
/* 39 */       n = 0.5F;
/*    */     } 
/*    */     
/* 42 */     if (mc.field_71439_g.field_70702_br > 0.0F) {
/* 43 */       rotationYaw -= 90.0F * n;
/*    */     }
/*    */     
/* 46 */     if (mc.field_71439_g.field_70702_br < 0.0F) {
/* 47 */       rotationYaw += 90.0F * n;
/*    */     }
/*    */     
/* 50 */     return rotationYaw * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\Speed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */