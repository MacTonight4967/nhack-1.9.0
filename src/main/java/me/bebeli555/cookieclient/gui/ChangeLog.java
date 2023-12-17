/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ 
/*     */ 
/*     */ public class ChangeLog
/*     */   extends Mod
/*     */ {
/*     */   public static int textAddY;
/*     */   
/*  14 */   public static String[] getChangeLog() { return new String[] { "version: v1.01", "- Added AutoEnderpearl", "- Added Hit to NoSlowDown", "- Added ToggleOnSneak to Surround", "- Added ToggleOnJump to Surround", "- Added RenderBroken to PacketMine", "- Added EnderChest setting to Burrow", "- Added Potion amplifier to HUD-Potions", "- Fixed AutoHotbar bug in GUIs", "- Fixed NoRotate Desyncing", "", "version: v1.0", "- Release!", "- Added PistonAura", "- Added AutoHotbar", "- Improved Step", "- Fixed some bugs", "", "version: v1.02-beta", "- Added StashLogger", "- Added AutoRespawn", "- Added NoSound", "- Added LiquidInteract", "- Added Toggle mode to Surround", "- Added Portal to NoRender", "- Improved AutoCrystal", "- Other stuff", "", "version: v1.01-beta", "- Added PortalGodMode", "- Added LiquidSpeed", "- Added AutoTool", "- Added Offhand", "- Improved Scaffold", "- Nametags now ignore dead people", "- Fixed OnlyOwn breakmode in AutoCrystal", "- Other stuff", "", "version: v1.0-beta", "- Beta release!" }; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawChangelog(int mouseX, int mouseY) {
/*  62 */     mc.field_71466_p.func_175063_a("nhack changelog", (getX() + 26), (getY() + 4), -1);
/*     */ 
/*     */     
/*  65 */     Gui.GuiClick guiClick = new Gui.GuiClick(getX(), getY(), getX2(), getY2(), null);
/*  66 */     Gui.drawBorder(true, true, true, true, -16777216, guiClick, 2.0D);
/*     */ 
/*     */     
/*  69 */     GuiScreen.func_73734_a(getX(), getY(), getX2(), getY2(), -2147483648);
/*     */ 
/*     */     
/*  72 */     GlStateManager.func_179094_E();
/*  73 */     GlStateManager.func_179139_a(0.75D, 0.75D, 0.75D);
/*  74 */     mc.field_71466_p.func_175063_a("You can disable this at GUI > GUI > Changelog", (getX() + 3) / 0.75F, (getY2() + 3) / 0.75F, -1);
/*  75 */     GlStateManager.func_179121_F();
/*     */ 
/*     */     
/*  78 */     GlStateManager.func_179094_E();
/*  79 */     float scale = 0.8F;
/*  80 */     GlStateManager.func_179152_a(scale, scale, scale);
/*     */     
/*  82 */     double amount = 2.25D / scale;
/*  83 */     for (String string : getChangeLog()) {
/*  84 */       ChatFormatting color = ChatFormatting.WHITE;
/*  85 */       if (string.contains("version: ")) {
/*  86 */         color = ChatFormatting.GREEN;
/*  87 */         string = string.replace("version: ", "");
/*     */       } 
/*     */       
/*  90 */       float x = (getX() + 5.0F * scale) / scale;
/*  91 */       float y = (getY() + (int)(amount * 8.0D)) / scale + textAddY;
/*  92 */       if (y < (getY2() - 5) / scale && y > (getY() + 10) / scale) {
/*  93 */         mc.field_71466_p.func_175063_a(color + string, x, y, -1);
/*     */       }
/*     */       
/*  96 */       amount++;
/*     */     } 
/*     */     
/*  99 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void scroll(boolean down, int multiplier) {
/* 103 */     if (down) {
/* 104 */       textAddY -= 5 * multiplier;
/*     */     } else {
/* 106 */       textAddY += 5 * multiplier;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 111 */   public static boolean isMouseOver(int mouseX, int mouseY) { return (getX() < mouseX && getX2() > mouseX && getY() < mouseY && getY2() > mouseY); }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static int getX() { return 725; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static int getX2() { return 900; }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static int getY() { return 285; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static int getY2() { return 500; }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\ChangeLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */