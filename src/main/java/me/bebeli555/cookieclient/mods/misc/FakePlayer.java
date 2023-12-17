/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ 
/*    */ 
/*    */ public class FakePlayer
/*    */   extends Mod
/*    */ {
/*    */   public static EntityOtherPlayerMP fakePlayer;
/*    */   
/* 15 */   public FakePlayer() { super(Group.MISC, "FakePlayer", new String[] { "Creates a fakeplayer at ur location" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 20 */     if (mc.field_71439_g == null) {
/* 21 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/* 25 */     fakePlayer = new EntityOtherPlayerMP(mc.field_71441_e, new GameProfile(UUID.fromString("6ab32213-179a-4c41-8ab9-66789121e051"), "bebeli555"));
/* 26 */     fakePlayer.func_82149_j(mc.field_71439_g);
/* 27 */     fakePlayer.field_70759_as = mc.field_71439_g.field_70759_as;
/* 28 */     mc.field_71441_e.func_73027_a(-100, fakePlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisabled() {
/* 33 */     if (mc.field_71441_e != null && fakePlayer != null)
/* 34 */       mc.field_71441_e.func_72900_e(fakePlayer); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\FakePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */