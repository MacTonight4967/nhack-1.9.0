/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import me.bebeli555.cookieclient.utils.EntityUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class EntityESP
/*    */   extends Mod
/*    */ {
/* 14 */   public static Setting players = new Setting(Mode.BOOLEAN, "Players", Boolean.valueOf(true), new String[0]);
/* 15 */   public static Setting monsters = new Setting(Mode.BOOLEAN, "Monsters", Boolean.valueOf(true), new String[0]);
/* 16 */   public static Setting neutrals = new Setting(Mode.BOOLEAN, "Neutrals", Boolean.valueOf(true), new String[0]);
/* 17 */   public static Setting passive = new Setting(Mode.BOOLEAN, "Passive", Boolean.valueOf(true), new String[0]);
/* 18 */   public static Setting items = new Setting(Mode.BOOLEAN, "Items", Boolean.valueOf(true), new String[0]);
/* 19 */   public static Setting everything = new Setting(Mode.BOOLEAN, "Everything", Boolean.valueOf(false), new String[] { "Highlight every entity" });
/* 20 */   public static Setting red = new Setting(Mode.INTEGER, "Red", Integer.valueOf(66), new String[] { "RBG" });
/* 21 */   public static Setting green = new Setting(Mode.INTEGER, "Green", Integer.valueOf(245), new String[] { "RBG" });
/* 22 */   public static Setting blue = new Setting(Mode.INTEGER, "Blue", Integer.valueOf(185), new String[] { "RBG" });
/* 23 */   public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", Integer.valueOf(255), new String[] { "RBG" });
/* 24 */   public static Setting width = new Setting(Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "The width of the rendered lines" });
/*    */ 
/*    */   
/* 27 */   public EntityESP() { super(Group.RENDER, "EntityESP", new String[] { "Highlight entities hitboxes" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRenderWorld(float partialTicks) {
/* 32 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 33 */       if (isValid(entity)) {
/* 34 */         RenderUtil.renderHitBox(entity, red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F, (float)width.doubleValue(), partialTicks);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isValid(Entity entity) {
/* 40 */     if (entity.equals(mc.field_175622_Z)) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     if (everything.booleanValue()) {
/* 45 */       return true;
/*    */     }
/*    */     
/* 48 */     if (players.booleanValue() && entity instanceof net.minecraft.entity.player.EntityPlayer)
/* 49 */       return true; 
/* 50 */     if (monsters.booleanValue() && EntityUtil.isHostileMob(entity))
/* 51 */       return true; 
/* 52 */     if (neutrals.booleanValue() && EntityUtil.isNeutralMob(entity))
/* 53 */       return true; 
/* 54 */     if (passive.booleanValue() && EntityUtil.isPassive(entity))
/* 55 */       return true; 
/* 56 */     if (items.booleanValue() && entity instanceof net.minecraft.entity.item.EntityItem) {
/* 57 */       return true;
/*    */     }
/*    */     
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\EntityESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */