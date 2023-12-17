/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.entity.AttackEntityEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateMoveStatePostEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.combat.KillAura;
/*    */ 
/*    */ public class NoSlowDown extends Mod {
/* 14 */   public static Setting items = new Setting(Mode.BOOLEAN, "Items", Boolean.valueOf(true), new String[] { "Doesnt slow u down when using an item" });
/* 15 */   public static Setting itemsSpeed = new Setting(items, Mode.DOUBLE, "Speed", Double.valueOf(0.2D), new String[] { "Higher = slower", "0.2 = no slow down" });
/* 16 */   public static Setting hit = new Setting(Mode.BOOLEAN, "Hit", Boolean.valueOf(true), new String[] { "Doesn't stop u from sprinting when u hit an entity" });
/*    */   
/*    */   public NoSlowDown() {
/* 19 */     super(Group.MOVEMENT, "NoSlowDown", new String[] { "Doesnt slow u down for certain things", "Also allows you to set the speed so you can", "Play with the values and make them work for different servers" });
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
/* 30 */     this.onAttackEntity = new Listener(event -> {
/*    */           
/* 32 */           if (hit.booleanValue()) {
/* 33 */             event.cancel();
/* 34 */             KillAura.attackTargetEntityWithCurrentItem(event.target);
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerUpdateMoveStatePostEvent> onUpdateMoveState = new Listener(event -> {
/*    */         if (items.booleanValue() && mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH() && !mc.field_71439_g.func_184613_cA()) {
/*    */           mc.field_71439_g.field_71158_b.field_192832_b = (float)(mc.field_71439_g.field_71158_b.field_192832_b / itemsSpeed.doubleValue());
/*    */           mc.field_71439_g.field_71158_b.field_78902_a = (float)(mc.field_71439_g.field_71158_b.field_78902_a / itemsSpeed.doubleValue());
/*    */         } 
/*    */       }new java.util.function.Predicate[0]);
/*    */   @EventHandler
/*    */   private Listener<AttackEntityEvent> onAttackEntity;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\NoSlowDown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */