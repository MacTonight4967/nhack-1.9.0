/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerDamageBlockEvent;
/*    */ import me.bebeli555.cookieclient.events.player.PlayerMotionUpdateEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.rendering.RenderUtil;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class PacketMine
/*    */   extends Mod {
/*    */   public BlockPos broken;
/*    */   public long startMs;
/* 21 */   public static Setting renderBroken = new Setting(Mode.BOOLEAN, "RenderBroken", Boolean.valueOf(true), new String[] { "Renders the block that its currently breaking" });
/* 22 */   public static Setting color = new Setting(renderBroken, Mode.TEXT, "Color", "0x36b84e40", new String[] { "Hex" });
/*    */   
/*    */   public PacketMine() {
/* 25 */     super(Group.WORLD, "PacketMine", new String[] { "Mine using packets" });
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     this.playerDamageBlockEvent = new Listener(event -> 
/*    */         
/* 55 */         event.cancel(), new java.util.function.Predicate[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRenderWorld(float partialTicks) {
/* 60 */     if (this.broken != null && renderBroken.booleanValue()) {
/* 61 */       RenderUtil.drawFilledBox(RenderUtil.getBB(this.broken, 1), color.intValue());
/*    */     }
/*    */     
/* 64 */     if (getBlock(this.broken) == Blocks.field_150350_a)
/* 65 */       this.broken = null; 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<PlayerMotionUpdateEvent> onMotionUpdate = new Listener(event -> {
/*    */         if (mc.field_71439_g == null || mc.field_71476_x == null || mc.field_71476_x.func_178782_a() == null || mc.field_71476_x.field_178784_b == null)
/*    */           return; 
/*    */         if (mc.field_71474_y.field_74312_F.func_151470_d()) {
/*    */           if (mc.field_71441_e.func_180495_p(mc.field_71476_x.func_178782_a()).func_185887_b(mc.field_71441_e, mc.field_71476_x.func_178782_a()) == -1.0F)
/*    */             return; 
/*    */           if (getBlock(mc.field_71476_x.func_178782_a()) != Blocks.field_150350_a) {
/*    */             if (this.broken == null || getBlock(this.broken) == Blocks.field_150350_a || Math.abs(this.startMs - System.currentTimeMillis()) > 15000L) {
/*    */               this.broken = mc.field_71476_x.func_178782_a();
/*    */               this.startMs = System.currentTimeMillis();
/*    */             } 
/*    */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, mc.field_71476_x.func_178782_a(), mc.field_71476_x.field_178784_b));
/*    */             mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, mc.field_71476_x.func_178782_a(), mc.field_71476_x.field_178784_b));
/*    */             mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*    */           } 
/*    */         } 
/*    */       }new java.util.function.Predicate[0]);
/*    */   @EventHandler
/*    */   private Listener<PlayerDamageBlockEvent> playerDamageBlockEvent;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\PacketMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */