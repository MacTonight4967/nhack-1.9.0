/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraftforge.client.event.sound.PlaySoundEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoSound extends Mod {
/* 11 */   public static Setting portal = new Setting(Mode.BOOLEAN, "Portal", Boolean.valueOf(false), new String[] { "Doesnt play the nether portal sounds" });
/*    */ 
/*    */   
/* 14 */   public NoSound() { super(Group.MISC, "NoSound", new String[] { "Prevents some sounds from playing" }); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSound(PlaySoundEvent event) {
/* 19 */     if ((portal.booleanValue() && event.getName().equals("block.portal.ambient")) || (portal.booleanValue() && event.getName().equals("block.portal.travel")) || (portal
/* 20 */       .booleanValue() && event.getName().equals("block.portal.trigger")))
/* 21 */       event.setResultSound(null); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\NoSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */