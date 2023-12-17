/*    */ package me.bebeli555.cookieclient.gui;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ 
/*    */ public class GuiSettings
/*    */   extends Mod {
/*  7 */   public GuiSettings() { super(Group.GUI); }
/*    */ 
/*    */   
/* 10 */   public static Setting hud = new Setting(Mode.BOOLEAN, "HUD", Boolean.valueOf(true), new String[] { "Settings about the HUD" });
/* 11 */   public static Setting arrayList = new Setting(hud, Mode.BOOLEAN, "ArrayList", Boolean.valueOf(true), new String[] { "Shows the toggled modules" });
/* 12 */   public static Setting arrayListColorMode = new Setting(arrayList, "Color", "Rainbow", new String[][] { { "Green" }, { "Rainbow" } });
/* 13 */   public static Setting arrayListRainbowStatic = new Setting(arrayListColorMode, "Rainbow", Mode.BOOLEAN, "Static", Boolean.valueOf(false), new String[] { "All modules change to same rainbow", "If false then they change differently" });
/* 14 */   public static Setting arrayListRainbowSpeed = new Setting(arrayListColorMode, "Rainbow", Mode.INTEGER, "Speed", Integer.valueOf(9), new String[] { "Speed for rainbow change" });
/* 15 */   public static Setting arrayListShadow = new Setting(arrayList, Mode.BOOLEAN, "Shadow", Boolean.valueOf(true), new String[] { "Draws the string with shadow" });
/* 16 */   public static Setting waterMark = new Setting(hud, Mode.BOOLEAN, "Watermark", Boolean.valueOf(true), new String[] { "Shows nhack watermark" });
/* 17 */   public static Setting direction = new Setting(hud, Mode.BOOLEAN, "Direction", Boolean.valueOf(true), new String[] { "Shows the direction u are looking at" });
/* 18 */   public static Setting armor = new Setting(hud, Mode.BOOLEAN, "Armor", Boolean.valueOf(true), new String[] { "Shows ur armor above ur hotbar" });
/* 19 */   public static Setting lagNotifier = new Setting(hud, Mode.BOOLEAN, "LagNotifier", Boolean.valueOf(true), new String[] { "Shows when the server is not responding" });
/* 20 */   public static Setting tps = new Setting(hud, Mode.BOOLEAN, "TPS", Boolean.valueOf(true), new String[] { "Shows server tps" });
/* 21 */   public static Setting fps = new Setting(hud, Mode.BOOLEAN, "FPS", Boolean.valueOf(true), new String[] { "Shows ur fps" });
/* 22 */   public static Setting speed = new Setting(hud, Mode.BOOLEAN, "Speed", Boolean.valueOf(true), new String[] { "Shows ur speed in blocks per second" });
/* 23 */   public static Setting ping = new Setting(hud, Mode.BOOLEAN, "Ping", Boolean.valueOf(true), new String[] { "Shows ur ping" });
/* 24 */   public static Setting coords = new Setting(hud, Mode.BOOLEAN, "Coords", Boolean.valueOf(true), new String[] { "Shows ur coords" });
/* 25 */   public static Setting netherCoords = new Setting(coords, Mode.BOOLEAN, "NetherCoords", Boolean.valueOf(true), new String[] { "Also renders nether coords", "Or overworld if ur in nether" });
/* 26 */   public static Setting durability = new Setting(hud, Mode.BOOLEAN, "Durability", Boolean.valueOf(true), new String[] { "Shows durability for ur item" });
/* 27 */   public static Setting potions = new Setting(hud, Mode.BOOLEAN, "Potions", Boolean.valueOf(true), new String[] { "Shows potion effects and doesnt render the vanilla hud overlays" });
/* 28 */   public static Setting infoShadow = new Setting(hud, Mode.BOOLEAN, "InfoShadow", Boolean.valueOf(true), new String[] { "Draws the strings with shadow that are in", "The top right corner at default" });
/* 29 */   public static Setting portalGui = new Setting(hud, Mode.BOOLEAN, "PortalGui", Boolean.valueOf(true), new String[] { "Allows you to open guis in portals" });
/* 30 */   public static Setting guiSettings = new Setting(Mode.LABEL, "GUI", Boolean.valueOf(true), new String[] { "Settings about the GUI design" });
/* 31 */   public static Setting width = new Setting(guiSettings, Mode.INTEGER, "Width", Integer.valueOf(90), new String[] { "Gui node width" });
/* 32 */   public static Setting height = new Setting(guiSettings, Mode.INTEGER, "Height", Integer.valueOf(17), new String[] { "Gui node height" });
/* 33 */   public static Setting borderColor = new Setting(guiSettings, Mode.TEXT, "Border color", "0xFF32a86d", new String[] { "Color of the border in hex and with 0xAA" });
/* 34 */   public static Setting borderSize = new Setting(guiSettings, Mode.DOUBLE, "Border size", Integer.valueOf(0), new String[] { "The size of the border in the node" });
/* 35 */   public static Setting backgroundColor = new Setting(guiSettings, Mode.TEXT, "Color", "0x36325bc2", new String[] { "The background color" });
/* 36 */   public static Setting textColor = new Setting(guiSettings, Mode.TEXT, "Text Color", "0xFF00ff00", new String[] { "Text color when module is toggled on" });
/* 37 */   public static Setting textColorOff = new Setting(guiSettings, Mode.TEXT, "Text Color Off", "0xFFff0000", new String[] { "Text color when module is toggled off" });
/* 38 */   public static Setting labelColor = new Setting(guiSettings, Mode.TEXT, "Label color", "0xFF6b6b6b", new String[] { "The color of the label text which is an toggleable module" });
/* 39 */   public static Setting extendMove = new Setting(guiSettings, Mode.INTEGER, "Extend Move", Integer.valueOf(8), new String[] { "How much to move in x coordinates when parent is extended" });
/* 40 */   public static Setting groupTextColor = new Setting(guiSettings, Mode.TEXT, "Group color", "0xFFe3a520", new String[] { "The text color of the group" });
/* 41 */   public static Setting groupScale = new Setting(guiSettings, Mode.DOUBLE, "Group scale", Double.valueOf(1.25D), new String[] { "The group text scale" });
/* 42 */   public static Setting groupBackground = new Setting(guiSettings, Mode.TEXT, "Group background", "0x3650b57c", new String[] { "The group background color" });
/* 43 */   public static Setting scrollAmount = new Setting(guiSettings, Mode.INTEGER, "ScrollAmount", Integer.valueOf(15), new String[] { "How many things to scroll with one wheel scroll" });
/* 44 */   public static Setting scale = new Setting(guiSettings, Mode.DOUBLE, "Scale", Integer.valueOf(0), new String[] { "How much more to scale it than default", "Higher = bigger", "You should only change this if the default scale is messed up" });
/* 45 */   public static Setting scrollSave = new Setting(guiSettings, Mode.BOOLEAN, "ScrollSave", Boolean.valueOf(false), new String[] { "Doesnt reset mouse scrolled position if true" });
/* 46 */   public static Setting infoBox = new Setting(guiSettings, Mode.BOOLEAN, "InfoBox", Boolean.valueOf(true), new String[] { "Shows the left top info box thing" });
/* 47 */   public static Setting changelog = new Setting(guiSettings, Mode.BOOLEAN, "Changelog", Boolean.valueOf(true), new String[] { "Shows changelog in gui" });
/* 48 */   public static Setting prefix = new Setting(Mode.TEXT, "Prefix", "++", new String[] { "The prefix for commands" });
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\GuiSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */