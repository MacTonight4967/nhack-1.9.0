/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.rendering.RenderBlock;
/*    */ import me.bebeli555.cookieclient.rendering.Renderer;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ public class AutoTrapIndicator
/*    */   extends Mod {
/* 19 */   public static Setting lineWidth = new Setting(Mode.INTEGER, "LineWidth", Integer.valueOf(2), new String[] { "How thicc the rendered rectangles lines are" });
/* 20 */   public static Setting radius = new Setting(Mode.INTEGER, "Radius", Integer.valueOf(30), new String[] { "The max distance players can be", "For this to work on them" });
/*    */ 
/*    */   
/* 23 */   public AutoTrapIndicator() { super(Group.RENDER, "AutoTrapIndicator", new String[] { "Indicates if players are standing", "In the middle of the block so they can be trapped", "It draws a red rectangle below them" }); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void onDisabled() { Renderer.rectangles.clear(); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 33 */     Renderer.rectangles.clear();
/*    */     
/* 35 */     label19: for (EntityPlayer player : PlayerUtil.getAll()) {
/* 36 */       if (mc.field_71439_g.func_70032_d(player) > radius.intValue()) {
/*    */         continue;
/*    */       }
/*    */       
/* 40 */       BlockPos p = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
/* 41 */       for (BlockPos pos : new BlockPos[] { p.func_177982_a(1, 0, 0), p.func_177982_a(-1, 0, 0), p.func_177982_a(0, 0, 1), p.func_177982_a(0, 0, -1) }) {
/* 42 */         if (!BlockUtil.canPlaceBlock(pos) && !isSolid(pos)) {
/*    */           continue label19;
/*    */         }
/*    */       } 
/*    */       
/* 47 */       Renderer.rectangles.add(new RenderBlock.BlockColor(p.func_177982_a(0, -1, 0), Color.RED, lineWidth.intValue()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\AutoTrapIndicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */