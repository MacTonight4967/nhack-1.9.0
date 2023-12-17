/*     */ package me.bebeli555.cookieclient;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import me.bebeli555.cookieclient.gui.Gui;
/*     */ import me.bebeli555.cookieclient.gui.GuiNode;
/*     */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.mods.render.Search;
/*     */ import me.bebeli555.cookieclient.mods.render.Waypoints;
/*     */ import me.bebeli555.cookieclient.mods.render.XRay;
/*     */ import me.bebeli555.cookieclient.utils.PlayerUtil;
/*     */ import net.minecraft.client.tutorial.TutorialSteps;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.ClientChatEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Commands
/*     */   extends Mod
/*     */ {
/*     */   public static boolean openGui;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onChat(ClientChatEvent e) {
/*  28 */     String messageReal = e.getMessage();
/*  29 */     String message = messageReal.toLowerCase();
/*  30 */     String prefix = GuiSettings.prefix.stringValue();
/*     */     
/*  32 */     if (message.startsWith(prefix)) {
/*  33 */       e.setCanceled(true);
/*  34 */       mc.field_71456_v.func_146158_b().func_146239_a(messageReal);
/*  35 */       message = message.substring(prefix.length());
/*     */ 
/*     */       
/*  38 */       if (message.equals("gui")) {
/*  39 */         openGui = true;
/*  40 */         MinecraftForge.EVENT_BUS.register(Gui.gui);
/*     */ 
/*     */       
/*     */       }
/*  44 */       else if (message.startsWith("set")) {
/*  45 */         String id = "";
/*  46 */         String value = "";
/*     */         
/*     */         try {
/*  49 */           String[] split = messageReal.split(" ");
/*  50 */           id = split[1].replace("_", " ");
/*  51 */           value = split[2];
/*  52 */         } catch (Exception e2) {
/*  53 */           sendMessage("Invalid arguments. Working example: ++set Tetris false", true);
/*     */           
/*     */           return;
/*     */         } 
/*  57 */         GuiNode guiNode = Settings.getGuiNodeFromId(id);
/*  58 */         if (guiNode == null) {
/*  59 */           sendMessage("Cant find setting with id: " + id, true);
/*     */         }
/*  61 */         else if (guiNode.isTypeable != Settings.isBoolean(value)) {
/*  62 */           if (!guiNode.isTypeable) {
/*  63 */             guiNode.toggled = Boolean.parseBoolean(value);
/*  64 */             guiNode.setSetting();
/*     */           } else {
/*     */             try {
/*  67 */               guiNode.stringValue = value;
/*  68 */               guiNode.setSetting();
/*  69 */             } catch (Exception ex) {
/*  70 */               sendMessage("Wrong input. This might be caused if u input a string value and the setting only accepts integer or double", true);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*  75 */           sendMessage("Set " + id + " to " + value, false);
/*     */           
/*  77 */           if (Settings.isBoolean(value)) {
/*     */             try {
/*  79 */               Mod.toggleMod(id, Boolean.parseBoolean(value));
/*  80 */             } catch (Exception exception) {}
/*     */           
/*     */           }
/*     */         
/*     */         }
/*  85 */         else if (guiNode.isTypeable) {
/*  86 */           sendMessage("This setting requires a boolean value", true);
/*     */         } else {
/*  88 */           sendMessage("This setting requires a string or integer value", true);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*  95 */       else if (message.equals("list")) {
/*  96 */         String list = "";
/*  97 */         for (GuiNode node : GuiNode.all) {
/*  98 */           list = list + node.id.replace(" ", "_") + ", ";
/*     */         }
/*     */         
/* 101 */         sendMessage(list, false);
/*     */ 
/*     */       
/*     */       }
/* 105 */       else if (message.startsWith("friend add")) {
/*     */         try {
/* 107 */           String name = message.split(" ")[2];
/* 108 */           if (!Friends.friends.contains(name)) {
/* 109 */             Friends.addFriend(name);
/* 110 */             sendMessage("Added " + name + " to friends", false);
/*     */           } else {
/* 112 */             sendMessage(name + " is already a friend", true);
/*     */           } 
/* 114 */         } catch (Exception e2) {
/* 115 */           sendMessage("Invalid arguments", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 120 */       else if (message.startsWith("friend del")) {
/*     */         try {
/* 122 */           String name = message.split(" ")[2];
/*     */           
/* 124 */           if (Friends.friends.contains(name)) {
/* 125 */             Friends.removeFriend(name);
/* 126 */             sendMessage("Removed " + name + " from friends", false);
/*     */           } else {
/* 128 */             sendMessage(name + " is not a friend", true);
/*     */           } 
/* 130 */         } catch (Exception e2) {
/* 131 */           sendMessage("Invalid arguments", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 136 */       else if (message.startsWith("xray")) {
/*     */         try {
/* 138 */           String id = message.split(" ")[2];
/*     */           
/* 140 */           if (message.split(" ")[1].equals("add")) {
/* 141 */             XRay.addBlock(Integer.parseInt(id));
/* 142 */             sendMessage("Added block to XRay", false);
/* 143 */           } else if (message.split(" ")[1].equals("remove")) {
/* 144 */             XRay.removeBlock(Integer.parseInt(id));
/* 145 */             sendMessage("Removed block from XRay", false);
/*     */           } else {
/* 147 */             sendMessage("Wrong arguments. Valid = add, remove", true);
/*     */           } 
/* 149 */         } catch (Exception e2) {
/* 150 */           sendMessage("Invalid arguments. Working example: xray add 69", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 155 */       else if (message.startsWith("search")) {
/*     */         try {
/* 157 */           String id = message.split(" ")[2];
/*     */           
/* 159 */           if (message.split(" ")[1].equals("add")) {
/* 160 */             Search.addBlock(Integer.parseInt(id));
/* 161 */             sendMessage("Added block to Search", false);
/* 162 */           } else if (message.split(" ")[1].equals("remove")) {
/* 163 */             Search.removeBlock(Integer.parseInt(id));
/* 164 */             sendMessage("Removed block from Search", false);
/*     */           } else {
/* 166 */             sendMessage("Wrong arguments. Valid = add, remove", true);
/*     */           } 
/* 168 */         } catch (Exception e2) {
/* 169 */           sendMessage("Invalid arguments. Working example: search add 69", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 174 */       else if (message.startsWith("waypoint add")) {
/*     */         try {
/* 176 */           message = message.toLowerCase();
/* 177 */           String[] split = message.split(" ");
/* 178 */           String dimension = "overworld";
/* 179 */           if (mc.field_71439_g.field_71093_bK == -1) {
/* 180 */             dimension = "nether";
/*     */           }
/*     */           
/* 183 */           Vec3d pos = null;
/* 184 */           if (split.length > 3) {
/*     */             
/*     */             try {
/* 187 */               dimension = split[6];
/* 188 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */             
/* 192 */             pos = new Vec3d(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
/*     */           } else {
/*     */             
/*     */             try {
/* 196 */               dimension = split[3];
/* 197 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */             
/* 201 */             pos = new Vec3d(mc.field_175622_Z.field_70165_t, mc.field_175622_Z.field_70163_u, mc.field_175622_Z.field_70161_v);
/*     */           } 
/*     */ 
/*     */           
/* 205 */           Waypoints.Waypoint existing = Waypoints.Waypoint.getWaypointFromName(split[2]);
/* 206 */           if (existing != null) {
/* 207 */             Waypoints.removeWaypoint(existing);
/*     */           }
/*     */           
/* 210 */           Waypoints.Waypoint waypoint = new Waypoints.Waypoint(split[2], pos);
/* 211 */           if (dimension.startsWith("o")) {
/* 212 */             waypoint.originalDimension = Waypoints.Dimension.OVERWORLD;
/*     */           } else {
/* 214 */             waypoint.originalDimension = Waypoints.Dimension.NETHER;
/*     */           } 
/* 216 */           waypoint.server = PlayerUtil.getServerIp();
/*     */           
/* 218 */           Waypoints.addWaypoint(waypoint);
/* 219 */           sendMessage("Waypoint added with name: " + split[2], false);
/* 220 */         } catch (Exception e2) {
/* 221 */           sendMessage("Invalid arguments. Working example: waypoint add name 0 0 0 Overworld", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 226 */       else if (message.startsWith("waypoint remove")) {
/*     */         try {
/* 228 */           String name = message.split(" ")[2];
/* 229 */           Waypoints.Waypoint waypoint = Waypoints.Waypoint.getWaypointFromName(name);
/*     */           
/* 231 */           if (waypoint != null && waypoint.server.equals(PlayerUtil.getServerIp())) {
/* 232 */             Waypoints.removeWaypoint(waypoint);
/* 233 */             sendMessage("Removed waypoint named: " + name, false);
/*     */           } else {
/* 235 */             sendMessage("No waypoint with name: " + name, true);
/*     */           } 
/* 237 */         } catch (Exception e2) {
/* 238 */           sendMessage("Invalid arguments. Working example: waypoint remove name", true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 243 */       else if (message.equals("waypoint list")) {
/* 244 */         for (Waypoints.Waypoint waypoint : Waypoints.waypoints) {
/* 245 */           if (waypoint.server.equals(PlayerUtil.getServerIp())) {
/* 246 */             sendMessage(waypoint.name + " X: " + (int)(waypoint.getOriginalPos()).field_72450_a + " Y: " + (int)(waypoint.getOriginalPos()).field_72448_b + " Z: " + (int)(waypoint.getOriginalPos()).field_72449_c + " Dimension: " + waypoint.originalDimension.name, false);
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 252 */       else if (message.equals("help")) {
/* 253 */         sendMessage(prefix + "gui - Opens the GUI", false);
/* 254 */         sendMessage(prefix + "set settingId value - sets setting with given id to given value", false);
/* 255 */         sendMessage(prefix + "list - Gives a list of all the settingIds", false);
/* 256 */         sendMessage(prefix + "friend add name - Add friend", false);
/* 257 */         sendMessage(prefix + "friend del name - Remove friend", false);
/* 258 */         sendMessage("Also theres more commands that are explained in the modules description if it has a command", false);
/*     */ 
/*     */       
/*     */       }
/* 262 */       else if (message.startsWith("renderdistance")) {
/* 263 */         int value = Integer.parseInt(message.split(" ")[1]);
/* 264 */         mc.field_71474_y.field_151451_c = value;
/* 265 */         sendMessage("Set render distance to " + value, false);
/*     */ 
/*     */       
/*     */       }
/* 269 */       else if (message.equals("disable tutorial")) {
/* 270 */         mc.field_71474_y.field_193631_S = TutorialSteps.NONE;
/*     */ 
/*     */       
/*     */       }
/* 274 */       else if (message.equals("nopause")) {
/* 275 */         mc.field_71474_y.field_82881_y = !mc.field_71474_y.field_82881_y;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 280 */         sendMessage("Unknown command. Type " + ChatFormatting.GREEN + prefix + "help" + ChatFormatting.RED + " for help", true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\Commands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */