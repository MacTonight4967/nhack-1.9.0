/*    */ package me.bebeli555.cookieclient.mods.combat;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.InventoryUtil;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ import me.bebeli555.cookieclient.utils.RotationUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class SelfWeb extends Mod {
/* 17 */   public static Setting toggle = new Setting(Mode.BOOLEAN, "Toggle", Boolean.valueOf(false), new String[] { "Toggle the module off after the place" });
/* 18 */   public static Setting autoDetect = new Setting(Mode.BOOLEAN, "AutoDetect", Boolean.valueOf(false), new String[] { "Detects when a nearby player is trying", "To enter your hole and then places the web" });
/*    */ 
/*    */   
/* 21 */   public SelfWeb() { super(Group.COMBAT, "SelfWeb", new String[] { "Places a web inside you" }); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void onDisabled() { RotationUtil.stopRotating(); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 31 */     if (mc.field_71439_g == null || getBlock(getPlayerPos()) == Blocks.field_150321_G) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     if (!InventoryUtil.hasBlock(Blocks.field_150321_G)) {
/* 36 */       sendMessage("You have no webs", true);
/* 37 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 42 */     if (autoDetect.booleanValue()) {
/* 43 */       EntityPlayer player = PlayerUtil.getClosest();
/*    */       
/* 45 */       if (player == null || player.field_70163_u - mc.field_71439_g.field_70163_u < 0.25D || Math.abs(mc.field_71439_g.field_70165_t - player.field_70165_t) > 2.0D || Math.abs(mc.field_71439_g.field_70161_v - player.field_70161_v) > 2.0D) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 51 */     BlockUtil.placeBlockOnThisThread(Blocks.field_150321_G, getPlayerPos(), true);
/* 52 */     RotationUtil.stopRotating();
/*    */ 
/*    */     
/* 55 */     if (toggle.booleanValue())
/* 56 */       disable(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\combat\SelfWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */