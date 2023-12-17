/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class VoidESP
/*    */   extends Mod {
/* 19 */   private static Timer timer = new Timer();
/* 20 */   private static ArrayList<BlockPos> voidBlocks = new ArrayList();
/*    */   
/* 22 */   public static Setting radius = new Setting(Mode.INTEGER, "Radius", Integer.valueOf(15), new String[] { "Radius around the player to search for them" });
/* 23 */   public static Setting red = new Setting(Mode.INTEGER, "Red", Integer.valueOf(66), new String[] { "RBG" });
/* 24 */   public static Setting green = new Setting(Mode.INTEGER, "Green", Integer.valueOf(245), new String[] { "RBG" });
/* 25 */   public static Setting blue = new Setting(Mode.INTEGER, "Blue", Integer.valueOf(185), new String[] { "RBG" });
/* 26 */   public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", Integer.valueOf(255), new String[] { "RBG" });
/* 27 */   public static Setting width = new Setting(Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "The width of the rendered lines" });
/*    */   
/*    */   public VoidESP() {
/* 30 */     super(Group.RENDER, "VoidESP", new String[] { "Highlighst void holes" });
/*    */ 
/*    */     
/* 33 */     this.onPlayerUpdate = new Listener(event -> {
/*    */           
/* 35 */           if (timer.hasPassed(250) && mc.field_71439_g.field_70173_aa > 10) {
/* 36 */             timer.reset();
/*    */             
/* 38 */             voidBlocks.clear();
/* 39 */             for (BlockPos pos : BlockUtil.getAll(radius.intValue())) {
/* 40 */               if (isVoidHole(pos)) {
/* 41 */                 voidBlocks.add(pos);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   public void onRenderWorld(float partialTicks) {
/* 49 */     for (BlockPos pos : voidBlocks) {
/* 50 */       RenderUtil.drawBoundingBox(RenderUtil.getBB(pos, 1), (float)width.doubleValue(), red.intValue() / 255.0F, green.intValue() / 255.0F, blue.intValue() / 255.0F, alpha.intValue() / 255.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean isVoidHole(BlockPos pos) {
/* 55 */     if (pos.func_177956_o() > 4 || pos.func_177956_o() <= 0) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     BlockPos pos2 = pos;
/* 60 */     for (int i = pos.func_177956_o(); i >= 0; i--) {
/* 61 */       if (mc.field_71441_e.func_180495_p(pos2).func_177230_c() != Blocks.field_150350_a) {
/* 62 */         return false;
/*    */       }
/*    */       
/* 65 */       pos2 = pos2.func_177977_b();
/*    */     } 
/*    */     
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerUpdateEvent> onPlayerUpdate;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\VoidESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */