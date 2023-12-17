/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.event.ClickEvent;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ public class UpdateChecker
/*    */   extends Mod
/*    */ {
/*    */   public static UpdateChecker instance;
/* 21 */   public static String link = "https://raw.githubusercontent.com/bebeli555/CookieClient/main/UpdateChecker.txt";
/* 22 */   private String newVersion = null;
/*    */   
/*    */   public UpdateChecker() {
/* 25 */     super(Group.MISC, "UpdateChecker", new String[] { "Checks if theres a new version of nhack in startup", "And notifies you in chat if you are running an outdated version" });
/* 26 */     this.defaultOn = true;
/* 27 */     this.defaultHidden = true;
/* 28 */     this.autoSubscribe = false;
/* 29 */     instance = this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void onEnabled() { (new Thread() {
/*    */         public void run() {
/*    */           try {
/* 37 */             URL url = new URL(UpdateChecker.link);
/* 38 */             BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
/*    */             
/*    */             String inputLine;
/* 41 */             while ((inputLine = in.readLine()) != null) {
/* 42 */               if (inputLine.contains("1.12.2")) {
/* 43 */                 UpdateChecker.access$002(UpdateChecker.this, inputLine.replace("1.12.2=", ""));
/* 44 */                 MinecraftForge.EVENT_BUS.register(UpdateChecker.instance);
/*    */                 
/*    */                 break;
/*    */               } 
/*    */             } 
/* 49 */             in.close();
/* 50 */           } catch (Exception exception) {}
/*    */         }
/* 54 */       }).start(); }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.ClientTickEvent e) {
/* 59 */     if (mc.field_71439_g != null) {
/* 60 */       if (this.newVersion != null && !this.newVersion.equals("1.01")) {
/* 61 */         String link = "https://github.com/bebeli555/CookieClient/releases/v" + this.newVersion;
/*    */         
/* 63 */         ITextComponent[] messages = { new TextComponentString(ChatFormatting.YELLOW + "New version of " + ChatFormatting.GREEN + "CookieClient" + ChatFormatting.YELLOW + " is available!"), new TextComponentString(ChatFormatting.YELLOW + "New version: " + ChatFormatting.GREEN + this.newVersion + ChatFormatting.YELLOW + " Your version: " + ChatFormatting.GREEN + "1.01"), new TextComponentString(ChatFormatting.YELLOW + "Download it from " + ChatFormatting.AQUA + "" + ChatFormatting.UNDERLINE + "Github") };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 69 */         for (ITextComponent message : messages) {
/* 70 */           message.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
/* 71 */           mc.field_71439_g.func_145747_a(message);
/*    */         } 
/*    */       } 
/*    */       
/* 75 */       MinecraftForge.EVENT_BUS.unregister(instance);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\UpdateChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */