/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.other.IsPotionEffectActiveEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.init.MobEffects;
/*    */ 
/*    */ public class AntiLevitation extends Mod {
/*    */   public AntiLevitation() {
/* 12 */     super(Group.MOVEMENT, "AntiLevitation", new String[] { "Prevents you from levitating" });
/*    */ 
/*    */     
/* 15 */     this.IsPotionActive = new Listener(event -> {
/*    */           
/* 17 */           if (event.potion == MobEffects.field_188424_y)
/* 18 */             event.cancel(); 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<IsPotionEffectActiveEvent> IsPotionActive;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\AntiLevitation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */