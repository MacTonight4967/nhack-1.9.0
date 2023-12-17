/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraftforge.client.event.MouseEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MiddleClickFriends
/*    */   extends Mod
/*    */ {
/*    */   private boolean clicked;
/*    */   
/* 19 */   public MiddleClickFriends() { super(Group.MISC, "MiddleClickFriends", new String[] { "Add and remove friends by middleclicking", "On their players" }); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onClick(MouseEvent e) {
/* 24 */     if (mc.field_71462_r != null) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     if (!Mouse.isButtonDown(2)) {
/* 29 */       this.clicked = false;
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     if (!this.clicked) {
/* 34 */       this.clicked = true;
/*    */       
/* 36 */       if (!Friends.toggled) {
/*    */         return;
/*    */       }
/*    */       
/* 40 */       RayTraceResult result = mc.field_71476_x;
/*    */       
/* 42 */       if (result == null || result.field_72313_a != RayTraceResult.Type.ENTITY) {
/*    */         return;
/*    */       }
/*    */       
/* 46 */       Entity entity = result.field_72308_g;
/*    */       
/* 48 */       if (entity == null || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/*    */         return;
/*    */       }
/*    */       
/* 52 */       if (Friends.friends.contains(entity.func_70005_c_())) {
/* 53 */         Friends.removeFriend(entity.func_70005_c_());
/* 54 */         sendMessage(ChatFormatting.RED + "Removed " + entity.func_70005_c_(), false);
/*    */       } else {
/* 56 */         Friends.addFriend(entity.func_70005_c_());
/* 57 */         sendMessage(ChatFormatting.GREEN + "Added " + entity.func_70005_c_(), false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\MiddleClickFriends.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */