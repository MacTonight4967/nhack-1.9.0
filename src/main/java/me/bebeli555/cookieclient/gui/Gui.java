/*     */ package me.bebeli555.cookieclient.gui;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import me.bebeli555.cookieclient.Commands;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.mods.games.Snake;
/*     */ import me.bebeli555.cookieclient.mods.games.tetris.Tetris;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiConfirmOpenLink;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public class Gui
/*     */   extends GuiScreen
/*     */ {
/*  36 */   public static ArrayList<Runnable> renderList = new ArrayList();
/*  37 */   public static Timer timer = new Timer();
/*  39 */   public static ArrayList<GuiClick> visibleNodes = new ArrayList();
/*     */   public static boolean isOpen;
/*     */   public static GuiClick selected;
/*     */   public static GuiClick description;
/*     */   public static Group dragging;
/*     */   public static int lastMouseX;
/*     */   public static int lastMouseY;
/*  46 */   public static Gui gui = new Gui();
/*  47 */   public static ArrayList<Point> groupCoords = new ArrayList();
/*     */   
/*     */   public static int oldScale;
/*     */
/*     */   
/*     */   public static boolean pasting;
/*     */   public static char pasteChar;
/*     */   
/*  56 */   public boolean func_73868_f() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX2, int mouseY2, float partialTicks) {
/*  62 */     Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();

/*     */
/*     */
/*     */
/*     */
/*  74 */     if (!timer.hasPassed(25)) {
/*  75 */       for (Runnable render : renderList) {
/*  76 */         render.run();
/*     */       }
/*     */
/*  79 */       renderGames(mouseX, mouseY, partialTicks);
/*     */
/*     */       return;
/*     */     }
/*  84 */     renderList.clear();
/*  85 */     timer.reset();
/*     */
/*     */
/*  88 */     drawRect(0, 0, 2000, 2000, -1728053248);
/*  89 */     drawRect(0, 0, 2000, 2000, 1157627904);
/*     */
/*  91 */     visibleNodes.clear();
/*  92 */     Gui.description = null;
/*     */
/*     */
/*  95 */     if (GuiSettings.changelog.booleanValue()) {
/*     */     }
/*     */
/*     */
/* 100 */     if (dragging != null) {
/* 101 */       if (Mouse.isButtonDown(1) || Mouse.isButtonDown(0)) {
/* 102 */         if (mouseY > 10) {
/* 105 */           updateGuiGroups();
/*     */         }
/*     */       } else {
/* 108 */         dragging = null;
/*     */       }
/*     */     }
/*     */
/*     */
/* 113 */     if (GuiSettings.infoBox.booleanValue()) {
/   drawStringWithShadow(ChatFormatting.RED + "nhack" + ChatFormatting.GREEN + " v" + "1.01", 2.0F, 2.0F, -16777216);
/* 121 */       drawStringWithShadow(ChatFormatting.RED + "Made by: " + ChatFormatting.GREEN + "christallinqq", 2.0F, 12.0F, -16777216);
/* 122 */       drawStringWithShadow(ChatFormatting.RED + "Discord: " + ChatFormatting.GREEN + "discord.gg/niggas", 2.0F, 22.0F, -16777216);
/* 123 */       drawStringWithShadow(ChatFormatting.RED + "Github: " + ChatFormatting.GREEN + "christallinqq", 2.0F, 32.0F, -16777216);
/*     */     }
/*     */
/*     */
/* 128 */     for (Group group : Group.values()) {
/*     */
/* 130 */       int nodes = 0;
/* 131 */       for (GuiNode node : GuiNode.all) {
/* 132 */         if (node.isVisible && node.group == group) {
/* 133 */           nodes++;
/*     */         }
/*     */       }
/*     */
/* 137 */       int count = 0;
/* 138 */       for (GuiNode node : GuiNode.all) {
/* 139 */         if (node.group == group && node.isVisible) {
/* 140 */           drawGuiNode(mouseX, mouseY, node, count, nodes);
/* 141 */           count++;
/*     */         }
/*     */       }
/*     */
/*     */
/* 146 */       int x = group.x;
/* 147 */       int x2 = group.x + GuiSettings.width.intValue();
/* 148 */       int y = group.y - GuiSettings.height.intValue();
/* 149 */       int y2 = group.y;
/* 150 */       GuiNode guiNode = new GuiNode(true);
/* 151 */       guiNode.description = group.description;
/* 152 */       GuiClick guiClick = new GuiClick(x, y, x2, y2, guiNode);
/*     */
/* 154 */       drawRect(x, y, x2, y2, GuiSettings.groupBackground.intValue());
/* 155 */       drawBorder(true, true, true, true, GuiSettings.borderColor.intValue(), guiClick, GuiSettings.borderSize.doubleValue());
/* 157 */       float scale = (float)GuiSettings.groupScale.doubleValue();
/* 158 */       scale(scale);
/* 159 */       drawStringWithShadow(group.name, (x2 / scale - x / scale) / 2.0F + x / scale - (mc.field_71466_p.func_78256_a(group.name) / 2), (y + 5.0F / scale) / scale, GuiSettings.groupTextColor.intValue());
/* 160 */       renderList.add(() -> GlStateManager.func_179121_F());
/*     */
/* 162 */       if (x < mouseX && x2 > mouseX && y < mouseY && y2 > mouseY) {
/* 163 */         Gui.description = guiClick;
/*     */
/*     */
/* 166 */         if ((Mouse.isButtonDown(1) || Mouse.isButtonDown(0)) &&
/* 167 */           dragging == null) {
/* 168 */           dragging = group;
/*     */         }
/*     */       }
/*     */     }
/*     */
/*     */
/*     */
/* 175 */     for (GuiClick g : new GuiClick[] { selected, Gui.description }) {
/* 176 */       if (g != null) {
/* 177 */         String[] description = g.guiNode.description;
/*     */
/* 179 */         if (g.guiNode.modes.size() != 0) {
/* 180 */           Object[] array = null;
/*     */           try {
/* 182 */             array = ((ArrayList)g.guiNode.modeDescriptions.get(g.guiNode.modes.indexOf(g.guiNode.stringValue))).toArray();
/* 183 */           } catch (Exception e) {
/*     */
/*     */
/* 186 */             g.guiNode.stringValue = (String)g.guiNode.modes.get(0);
/*     */             break;
/*     */           }
/* 189 */           description = (String[])Arrays.copyOf(array, array.length, String[].class);
/* 190 */           description = Mod.addToArray(description, ChatFormatting.GREEN + "Click to switch modes");
/*     */         }
/*     */
/* 193 */         for (GuiNode node : g.guiNode.parentedNodes) {
/* 194 */           if (!node.modeName.isEmpty() && !node.modeName.equals(g.guiNode.stringValue)) {
/*     */             continue;
/*     */           }
/*     */
/* 198 */           if (description == null || description.length == 0) {
/* 199 */             description = new String[] { ChatFormatting.GREEN + "Right click to extend" }; break;
/*     */           }
/* 201 */           description = Mod.addToArray(description, ChatFormatting.GREEN + "Right click to extend");
/*     */         }
/*     */
/*     */
/*     */
/* 206 */         if (selected != null && selected.equals(g)) {
/* 207 */           if (g.guiNode.onlyNumbers) {
/* 208 */             description = new String[] { ChatFormatting.GOLD + "Type numbers in your keyboard to set this" };
/* 209 */           } else if (g.guiNode.isKeybind) {
/* 210 */             description = new String[] { ChatFormatting.GOLD + "Click a key to set the keybind" };
/*     */           } else {
/* 212 */             description = new String[] { ChatFormatting.GOLD + "Type with your keyboard to set this" };
/*     */           }
/*     */         }
/*     */
/* 216 */         if (description != null) {
/* 217 */           int longestWidth = 0;
/* 218 */           boolean left = false;
/* 219 */           for (String s : description) {
/* 221 */             width += Math.abs(g.x - g.x2) + 6;
/*     */
/* 223 */             if (width > longestWidth) {
/* 224 */               longestWidth = width;
/*     */
/* 226 */               if (g.x + width > mc.field_71443_c / 2) {
/* 227 */                 left = true;
/*     */               }
/*     */             }
/*     */           }
/*     */
/* 232 */           for (int i = 0; i < description.length; i++) {
/* 233 */             int y = g.y + 6 + i * 10;
/* 234 */             int width = mc.field_71466_p.func_78256_a(description[i]);
/*     */
/* 236 */             if (left) {
/* 237 */               drawRect(g.x2 - longestWidth, y - 2, g.x - 2, y + 10, -16777216);
/* 238 */               drawStringWithShadow(description[i], (g.x2 - longestWidth + 2), (g.y + 6 + i * 10), 65535);
/*     */             } else {
/* 240 */               drawRect(g.x2 + 8, y - 2, g.x2 + width + 12, y + 10, -16777216);
/* 241 */               drawStringWithShadow(description[i], (g.x2 + 10), (g.y + 6 + i * 10), 65535);
/*     */             }
/*     */           }
/*     */         }
/*     */
/*     */         break;
/*     */       }
/*     */     }
/*     */
/* 250 */     for (Runnable render : renderList) {
/* 251 */       render.run();
/*     */     }
/*     */
/* 254 */     renderGames(mouseX, mouseY, partialTicks);
/* 255 */     lastMouseX = mouseX;
/* 256 */     lastMouseY = mouseY;
/* 257 */     GlStateManager.func_179121_F();
/*     */   }
/*     */
/*     */
/*     */   public void drawGuiNode(int mouseX, int mouseY, GuiNode node, int aboveNodes, int nodes) {
/* 262 */     int extendMoveMultiplier = node.getAllParents().size() * GuiSettings.extendMove.intValue();
/*     */ 
/*     */
/*     */
/* 266 */     GuiClick g = new GuiClick(node.group.x + extendMoveMultiplier, node.group.y + aboveNodes * GuiSettings.height.intValue(), node.group.x + GuiSettings.width.intValue() + extendMoveMultiplier, node.group.y + GuiSettings.height.intValue() + aboveNodes * GuiSettings.height.intValue(), node);
/*     */
/*     */
/* 269 */     drawRect(g.x, g.y, g.x2, g.y2, GuiSettings.backgroundColor.intValue());
/*     */
/*     */
/* 272 */     String text = g.guiNode.name;
/* 273 */     if (g.guiNode.isTypeable) {
/* 274 */       ChatFormatting color = ChatFormatting.AQUA;
/* 275 */       if (g.guiNode.isKeybind) {
/* 276 */         color = ChatFormatting.LIGHT_PURPLE;
/*     */       }
/*     */
/* 279 */       if (g.guiNode.stringValue.isEmpty()) {
/* 280 */         g.guiNode.stringValue = "";
/* 281 */         text = color + g.guiNode.name + ": " + ChatFormatting.RED + "NONE";
/*     */       } else {
/* 283 */         text = color + g.guiNode.name + ": " + ChatFormatting.GOLD + g.guiNode.stringValue;
/*     */       }
/* 285 */     } else if (g.guiNode.modes.size() != 0) {
/* 286 */       text = ChatFormatting.GREEN + g.guiNode.name + ": " + ChatFormatting.WHITE + g.guiNode.stringValue;
/*     */     }
/*     */
/* 289 */     float scale = 1.0F;
/* 290 */     if (mc.field_71466_p.func_78256_a(text) > g.x2 - g.x) {
/* 291 */       renderList.add(() -> GlStateManager.func_179094_E());
/*     */
/* 293 */       int width = mc.field_71466_p.func_78256_a(text);
/* 294 */       while (width * scale > (g.x2 - g.x)) {
/* 295 */         scale = (float)(scale - 0.03D);
/*     */       }
/*     */
/* 298 */       scale(scale);
/*     */     }
/*     */
/*     */
/* 304 */     if (scale != 1.0F) {
/*     */     }
/*     */
/*     */
/* 309 */     drawBorder(true, true, true, true, GuiSettings.borderColor.intValue(), g, GuiSettings.borderSize.doubleValue());
/*     */
/*     */
/* 312 */     if (!visibleNodes.isEmpty()) {
/* 313 */       GuiClick last = (GuiClick)visibleNodes.get(visibleNodes.size() - 1);
/*     */
/* 315 */       if (last.guiNode.group == node.group)
/*     */       {
/* 317 */         if (last.x < g.x) {
/* 318 */           drawRectDouble(last.x, g.y, last.x + GuiSettings.borderSize.doubleValue(), g.y + GuiSettings.borderSize.doubleValue(), GuiSettings.borderColor.intValue());
/* 319 */           drawRectDouble(last.x2, g.y, (last.x2 + GuiSettings.extendMove.intValue()), g.y + GuiSettings.borderSize.doubleValue(), GuiSettings.borderColor.intValue());
/*     */
/*     */
/*     */         }
/* 323 */         else if (last.x > g.x) {
/* 324 */           drawRectDouble(last.x, g.y, (last.x - GuiSettings.extendMove.intValue()), g.y + GuiSettings.borderSize.doubleValue(), GuiSettings.borderColor.intValue());
/* 325 */           drawRectDouble(last.x2, g.y, (last.x2 - GuiSettings.extendMove.intValue()), g.y + GuiSettings.borderSize.doubleValue(), GuiSettings.borderColor.intValue());
/*     */         }
/*     */       }
/*     */     }
/*     */
/*     */
/* 331 */     if (g.x < mouseX && g.x2 > mouseX && g.y < mouseY && g.y2 > mouseY) {
/* 332 */       description = g;
/*     */     }
/*     */
/*     */
/* 336 */     visibleNodes.add(g);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 342 */     x = lastMouseX;
/* 343 */     y = lastMouseY;
/*     */
/* 345 */     for (Mod module : Mod.modules) {
/* 346 */       if (module.isOn() &&
/* 347 */         module.onGuiClick(x, y, button)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 353 */     if (selected != null) {
/* 354 */       selected.guiNode.setSetting();
/*     */     }
/* 356 */     selected = null;
/*     */ 
/*     */     
/* 359 */     if (GuiSettings.infoBox.booleanValue() && 0 < x && 150 > x && 0 < y && 34 > y) {
/*     */       try {
/* 361 */         URI link = new URI("https://discord.gg/xSukBcyd8m");
/* 362 */         ReflectionHelper.setPrivateValue(GuiScreen.class, this, link, new String[] { "clickedLinkURI", "field_175286_t" });
/* 363 */         mc.func_147108_a(new GuiConfirmOpenLink(this, link.toString(), 31102009, true));
/* 364 */       } catch (Exception e) {
/* 365 */         e.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 371 */     for (GuiClick guiClick : visibleNodes) {
/* 372 */       if (guiClick.x < x && guiClick.x2 > x && guiClick.y < y && guiClick.y2 > y) {
/* 373 */         if (button == 1) {
/* 374 */           if (!guiClick.guiNode.parentedNodes.isEmpty()) {
/* 375 */             int index = 0;
/* 376 */             if (!guiClick.guiNode.modes.isEmpty()) {
/* 377 */               for (int i = 0; i < guiClick.guiNode.parentedNodes.size(); i++) {
/* 378 */                 GuiNode node = (GuiNode)guiClick.guiNode.parentedNodes.get(i);
/* 379 */                 if (!node.modeName.isEmpty() && node.modeName.equals(guiClick.guiNode.stringValue)) {
/* 380 */                   index = i;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/* 386 */             guiClick.guiNode.extend(!((GuiNode)guiClick.guiNode.parentedNodes.get(index)).isVisible);
/*     */           }  continue;
/*     */         } 
/* 389 */         if (guiClick.guiNode.isTypeable) {
/* 390 */           selected = guiClick;
/*     */         }
/*     */         
/* 393 */         guiClick.guiNode.click();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
/* 401 */     if (selected != null && Keyboard.isKeyDown(Keyboard.getEventKey())) {
/* 402 */       char acceptedKeys[], key = Keyboard.getEventCharacter();
/* 403 */       if (pasting) {
/* 404 */         key = pasteChar;
/*     */       }
/*     */ 
/*     */       
/* 408 */       if (!pasting && Keyboard.isKeyDown(47) && Keyboard.isKeyDown(29)) {
/* 409 */         pasting = true;

/*     */ 
/*     */         

/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 427 */       if (selected.guiNode.isKeybind) {
/* 428 */         if (Keyboard.getEventKey() != 14) {
/* 429 */           selected.guiNode.stringValue = Keyboard.getKeyName(Keyboard.getEventKey());
/*     */         } else {
/* 431 */           selected.guiNode.stringValue = "";
/*     */         } 
/*     */         
/* 434 */         Keybind.setKeybinds();
/* 435 */         selected.guiNode.notifyKeyListeners();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 440 */       if (Keyboard.getEventKey() == 42 || Keyboard.getEventKey() == 54) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 445 */       if (Keyboard.isKeyDown(14) || Keyboard.isKeyDown(211)) {
/* 446 */         if (!selected.guiNode.stringValue.isEmpty()) {
/* 447 */           selected.guiNode.stringValue = selected.guiNode.stringValue.substring(0, selected.guiNode.stringValue.length() - 1);
/*     */         } else {
/* 449 */           selected.guiNode.stringValue = selected.guiNode.defaultValue;
/*     */         } 
/*     */         
/* 452 */         selected.guiNode.notifyKeyListeners();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 457 */       if (selected.guiNode.onlyNumbers) {
/* 458 */         if (selected.guiNode.acceptDoubleValues) {
/* 459 */           acceptedKeys = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.' };
/*     */         } else {
/* 461 */           acceptedKeys = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-' };
/*     */         } 
/*     */       } else {
/* 464 */         if (key != '\000') {
/* 465 */           selected.guiNode.stringValue += key;
/* 466 */           selected.guiNode.notifyKeyListeners();
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 472 */       for (char accept : acceptedKeys) {
/* 473 */         if (accept == key) {
/* 474 */           if (key == '-') {
/* 475 */             selected.guiNode.stringValue = "";
/*     */           }
/*     */           
/* 478 */           if (key != '\000') {
/* 479 */             selected.guiNode.stringValue += key;
/* 480 */             selected.guiNode.notifyKeyListeners();
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 487 */     for (Mod module : Mod.modules) {
/* 488 */       if (module.isOn()) {
/* 489 */         module.onGuiKeyPress(e);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMouseEvent(GuiScreenEvent.MouseInputEvent event) {
/* 496 */     int wheel = Mouse.getDWheel();
/* 497 */     int amount = GuiSettings.scrollAmount.intValue() * Math.abs(wheel) / 120;
/*     */ 
/*     */     
/* 500 */     if (wheel > 0) {
/* 501 */       int multiplier = 1;
/* 502 */       if (wheel > 120) {
/* 503 */         multiplier++;
/*     */       }
/*     */       
/* 506 */       if (GuiSettings.changelog.booleanValue() && ChangeLog.isMouseOver(lastMouseX, lastMouseY)) {
/* 507 */         ChangeLog.scroll(false, multiplier);
/*     */         
/*     */         return;
/*     */       } 
/* 511 */       for (Group group : Group.values()) {
/* 512 */         group.y += amount * multiplier;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 517 */     else if (wheel < 0) {
/* 518 */       int multiplier = 1;
/* 519 */       if (wheel < -120) {
/* 520 */         multiplier++;
/*     */       }
/*     */       
/* 523 */       if (GuiSettings.changelog.booleanValue() && ChangeLog.isMouseOver(lastMouseX, lastMouseY)) {
/* 524 */         ChangeLog.scroll(true, multiplier);
/*     */         
/*     */         return;
/*     */       } 
/* 528 */       for (Group group : Group.values()) {
/* 529 */         group.y += -amount * multiplier;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/* 537 */     if (Commands.openGui) {
/*     */       
/* 539 */       oldScale = mc.field_71474_y.field_74335_Z;
/* 540 */       if (!mc.func_71372_G() && (
/* 541 */         mc.field_71474_y.field_74335_Z == 3 || mc.field_71474_y.field_74335_Z == 0)) {
/* 542 */         mc.field_71474_y.field_74335_Z = 2;
/*     */       }
/*     */ 
/*     */       
/* 546 */       mc.func_147108_a(new Gui());
/* 547 */       Commands.openGui = false;
/* 548 */       isOpen = true;
/* 549 */       updateGuiGroups();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 554 */     if (isOpen && mc.field_71462_r == null) {
/* 555 */       if (!GuiSettings.scrollSave.booleanValue()) {
/* 556 */         for (int i = 0; i < Group.values().length; i++) {
/* 557 */           (Group.values()[i]).x = ((Point)groupCoords.get(i)).x;
/* 558 */           (Group.values()[i]).y = ((Point)groupCoords.get(i)).y;
/*     */         } 
/*     */       }
/*     */       
/* 562 */       Settings.saveSettings();
/* 563 */       mc.field_71474_y.field_74335_Z = oldScale;
/* 564 */       isOpen = false;
/* 565 */       selected = null;
/* 566 */       pasting = false;
/* 567 */       dragging = null;
/* 568 */       description = null;
/* 569 */       Tetris.instance.disable();
/* 570 */       Snake.instance.disable();
/* 571 */       MinecraftForge.EVENT_BUS.unregister(gui);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getGuiScale(float start) {
/* 576 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 577 */     if (scaledResolution.func_78325_e() == 4) {
/* 578 */       start = (float)(start + -0.5D * start);
/* 579 */     } else if (scaledResolution.func_78325_e() == 1) {
/* 580 */       start += 1.0F * start;
/* 581 */     } else if (scaledResolution.func_78325_e() == 3) {
/* 582 */       start = (float)(start + -0.3D * start);
/*     */     } 
/*     */     
/* 585 */     return start;
/*     */   }
/*     */   
/*     */   public static void drawBorder(boolean right, boolean left, boolean up, boolean down, int color, GuiClick n, double borderSize) {
/* 589 */     if (up) drawRectDouble(n.x, n.y, n.x2, n.y + borderSize, color); 
/* 590 */     if (down) drawRectDouble(n.x, n.y2, n.x2, n.y2 + borderSize, color); 
/* 591 */     if (left) drawRectDouble(n.x, n.y, n.x + borderSize, n.y2, color); 
/* 592 */     if (right) drawRectDouble(n.x2, n.y, n.x2 + borderSize, n.y2 + borderSize, color); 
/*     */   }
/*     */   
/*     */   public static void drawRectDouble(double left, double top, double right, double bottom, int color) {
/* 596 */     if (left < right) {
/* 597 */       double i = left;
/* 598 */       left = right;
/* 599 */       right = i;
/*     */     } 
/*     */     
/* 602 */     if (top < bottom) {
/* 603 */       double j = top;
/* 604 */       top = bottom;
/* 605 */       bottom = j;
/*     */     } 
/*     */     
/* 608 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 609 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 610 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 611 */     float f2 = (color & 0xFF) / 255.0F;
/* 612 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 613 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 614 */     GlStateManager.func_179147_l();
/* 615 */     GlStateManager.func_179090_x();
/* 616 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 617 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 618 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 619 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 620 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 621 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/* 622 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/* 623 */     tessellator.func_78381_a();
/* 624 */     GlStateManager.func_179098_w();
/* 625 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateGuiGroups() {
/* 630 */     groupCoords.clear();
/*     */     
/* 632 */     for (Group group : Group.values()) {
/* 633 */       groupCoords.add(new Point(group.x, group.y));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 638 */   public static void drawRect(int x, int y, int x2, int y2, int color) { renderList.add(() -> GuiScreen.func_73734_a(x, y, x2, y2, color)); }
/*     */ 
/*     */ 
/*     */   
/* 642 */   public static void drawStringWithShadow(String text, float x, float y, int color) { renderList.add(() -> mc.field_71466_p.func_175063_a(text, x, y, color)); }
/*     */ 
/*     */ 
/*     */   
/* 646 */   public static void scale(float scale) { renderList.add(() -> GlStateManager.func_179152_a(scale, scale, scale)); }
/*     */ 
/*     */   
/*     */   public static void renderGames(int mouseX, int mouseY, float partialTicks) {
/* 650 */     for (Mod module : Mod.modules) {
/* 651 */       if (module.isOn())
/* 652 */         module.onGuiDrawScreen(mouseX, mouseY, partialTicks); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class GuiClick
/*     */   {
/*     */     public int x;
/*     */     public int y;
/*     */     
/*     */     public GuiClick(int x, int y, int x2, int y2, GuiNode guiNode) {
/* 662 */       this.x = x;
/* 663 */       this.y = y;
/* 664 */       this.x2 = x2;
/* 665 */       this.y2 = y2;
/* 666 */       this.guiNode = guiNode;
/*     */     }
/*     */     
/*     */     public int x2;
/*     */     public int y2;
/*     */     public GuiNode guiNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */