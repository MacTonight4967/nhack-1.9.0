/*    */ package me.bebeli555.cookieclient.events.render;
/*    */ 
/*    */ import me.bebeli555.cookieclient.events.bus.Cancellable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RenderTooltipEvent
/*    */   extends Cancellable {
/*    */   public int x;
/*    */   
/*    */   public RenderTooltipEvent(ItemStack itemStack, int x, int y) {
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.itemStack = itemStack;
/*    */   }
/*    */   
/*    */   public int y;
/*    */   public ItemStack itemStack;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\render\RenderTooltipEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */