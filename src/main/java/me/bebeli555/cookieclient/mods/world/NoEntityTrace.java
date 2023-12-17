/*    */ package me.bebeli555.cookieclient.mods.world;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.entity.GetEntitiesEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class NoEntityTrace
/*    */   extends Mod {
/* 14 */   public static Setting toolsOnly = new Setting(Mode.BOOLEAN, "ToolsOnly", Boolean.valueOf(true), new String[] { "Only works if ur holding a tool", "(Pickaxe, Axe, Shovel)" });
/*    */   
/*    */   public NoEntityTrace() {
/* 17 */     super(Group.WORLD, "NoEntityTrace", new String[] { "Allows you to mine through entities" });
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
/* 30 */     this.getEntities = new Listener(event -> {
/*    */           
/* 32 */           Item item = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 33 */           if (toolsOnly.booleanValue() && !(item instanceof net.minecraft.item.ItemTool)) {
/*    */             return;
/*    */           }
/*    */           
/* 37 */           event.cancel();
/*    */         }new java.util.function.Predicate[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private Listener<GetEntitiesEvent> getEntities;
/*    */   
/*    */   public void onEnabled() { Mod.EVENT_BUS.subscribe(this); }
/*    */   
/*    */   public void onDisabled() { Mod.EVENT_BUS.unsubscribe(this); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\NoEntityTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */