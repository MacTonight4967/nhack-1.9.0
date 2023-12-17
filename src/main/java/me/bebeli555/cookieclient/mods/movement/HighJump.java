/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraftforge.event.entity.living.LivingEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class HighJump extends Mod {
/* 11 */   public static Setting heightAdd = new Setting(Mode.DOUBLE, "HeightAdd", Double.valueOf(0.1D), new String[] { "How much higher to jump than normal" });
/*    */ 
/*    */   
/* 14 */   public HighJump() { super(Group.MOVEMENT, "HighJump", new String[] { "Jump higher than normal" }); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
/* 19 */     if (!event.getEntity().equals(mc.field_71439_g)) {
/*    */       return;
/*    */     }
/*    */     
/* 23 */     mc.field_71439_g.field_70181_x += heightAdd.doubleValue();
/* 24 */     mc.field_71439_g.field_70133_I = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\HighJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */