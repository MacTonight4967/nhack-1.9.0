/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class IceSpeed
/*    */   extends Mod {
/* 13 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Integer.valueOf(1), new String[] { "How slippery it will make the ice", "Vanilla value = 0.97" });
/*    */ 
/*    */   
/* 16 */   public IceSpeed() { super(Group.MOVEMENT, "IceSpeed", new String[] { "Move faster on ice" }); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void onDisabled() { Blocks.field_185778_de.field_149765_K = 0.97F; }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/* 26 */   public void onTick(TickEvent.ClientTickEvent e) { Blocks.field_185778_de.field_149765_K = (float)speed.doubleValue(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\IceSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */