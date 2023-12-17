/*    */ package me.bebeli555.cookieclient.mods.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.rendering.RenderBlock;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import me.bebeli555.cookieclient.utils.BlockUtil;
/*    */ import me.bebeli555.cookieclient.utils.Timer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class HoleESP
/*    */   extends Mod {
/* 21 */   private static Timer timer = new Timer();
/* 22 */   private static ArrayList<RenderBlock.BlockColor> holes = new ArrayList();
/*    */   
/* 24 */   public static Setting renderMode = new Setting(null, "RenderMode", "Flat", new String[][] { { "Flat" }, { "Rectangle" } });
/* 25 */   public static Setting bedrock = new Setting(Mode.BOOLEAN, "Bedrock", Boolean.valueOf(true), new String[] { "Renders full bedrock holes" });
/* 26 */   public static Setting obbyBedrock = new Setting(Mode.BOOLEAN, "ObbyBedrock", Boolean.valueOf(true), new String[] { "Renders Obsidian & bedrock holes" });
/* 27 */   public static Setting obsidian = new Setting(Mode.BOOLEAN, "Obsidian", Boolean.valueOf(true), new String[] { "Renders full obdisian holes" });
/* 28 */   public static Setting radius = new Setting(Mode.INTEGER, "Radius", Integer.valueOf(10), new String[] { "Radius around the player to search", "For holes" });
/* 29 */   public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", Integer.valueOf(255), new String[] { "RBG" });
/* 30 */   public static Setting width = new Setting(Mode.DOUBLE, "Width", Integer.valueOf(1), new String[] { "Line width" }); @EventHandler
/*    */   private Listener<PlayerUpdateEvent> onPlayerUpdate;
/*    */   public HoleESP() {
/* 33 */     super(Group.RENDER, "HoleESP", new String[] { "Render holes that u can go to", "To avoid crystal damage" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     this.onPlayerUpdate = new Listener(event -> {
/*    */           
/* 43 */           if (timer.hasPassed(100) && mc.field_71439_g.field_70173_aa > 10) {
/* 44 */             timer.reset();
/* 45 */             holes.clear();
/*    */             
/* 47 */             label42: for (BlockPos pos : BlockUtil.getAll(radius.intValue())) {
/* 48 */               if (isSolid(pos) || isSolid(pos.func_177982_a(0, 1, 0)) || !isSolid(pos.func_177982_a(0, -1, 0))) {
/*    */                 continue;
/*    */               }
/*    */               
/* 52 */               BlockPos[] list = { pos.func_177982_a(1, 0, 0), pos.func_177982_a(-1, 0, 0), pos.func_177982_a(0, 0, 1), pos.func_177982_a(0, 0, -1) };
/* 53 */               for (BlockPos check : list) {
/* 54 */                 if (getBlock(check) != Blocks.field_150343_Z && getBlock(check) != Blocks.field_150357_h) {
/*    */                   continue label42;
/*    */                 }
/*    */               } 
/*    */               
/* 59 */               Color color = Color.RED;
/* 60 */               boolean allBedrock = true;
/* 61 */               for (BlockPos check : list) {
/* 62 */                 if (getBlock(check) != Blocks.field_150357_h) {
/* 63 */                   allBedrock = false;
/*    */                 } else {
/* 65 */                   color = Color.YELLOW;
/*    */                 } 
/*    */               } 
/*    */               
/* 69 */               if (allBedrock) {
/* 70 */                 color = Color.GREEN;
/*    */               }
/*    */               
/* 73 */               if ((color == Color.GREEN && !bedrock.booleanValue()) || (color == Color.YELLOW && !obbyBedrock.booleanValue()) || (color == Color.RED && !obsidian.booleanValue())) {
/*    */                 continue;
/*    */               }
/*    */               
/* 77 */               holes.add(new RenderBlock.BlockColor(pos, color, 1.0F));
/*    */             } 
/*    */           } 
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   public void onDisabled() { holes.clear(); }
/*    */   public void onRenderWorld(float partialTicks) {
/* 84 */     for (RenderBlock.BlockColor b : holes) {
/* 85 */       if (renderMode.stringValue().equals("Flat")) {
/* 86 */         RenderUtil.draw2DRec(RenderUtil.getBB(b.pos.func_177982_a(0, -1, 0), 1), (float)width.doubleValue(), b.color.getRed() / 255.0F, b.color.getGreen() / 255.0F, b.color.getBlue() / 255.0F, alpha.intValue() / 255.0F); continue;
/* 87 */       }  if (renderMode.stringValue().equals("Rectangle"))
/* 88 */         RenderUtil.drawBoundingBox(RenderUtil.getBB(b.pos, 1), (float)width.doubleValue(), b.color.getRed() / 255.0F, b.color.getGreen() / 255.0F, b.color.getBlue() / 255.0F, alpha.intValue() / 255.0F); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\HoleESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */