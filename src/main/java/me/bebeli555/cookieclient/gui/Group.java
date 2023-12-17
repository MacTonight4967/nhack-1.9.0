/*    */ package me.bebeli555.cookieclient.gui;
/*    */ 
/*    */ public enum Group {
/*  4 */   BOTS("Bots", '̼', 60, new String[] { "The bots" }),
/*  5 */   GUI("GUI", 'ɮ', 60, new String[] { "Stuff about the GUI and HUD" }),
/*  6 */   GAMES("Games", '˕', 60, new String[] { "Fun games to play" }),
/*  7 */   COMBAT("Combat", 4, 60, new String[] { "Combat modules for pvp and stuff" }),
/*  8 */   EXPLOITS("Exploits", 107, 60, new String[] { "Useful exploit modules" }),
/*  9 */   MISC("Misc", 'Ò', 60, new String[] { "Other modules" }),
/* 10 */   MOVEMENT("Movement", 'Ĺ', 60, new String[] { "Movement modules" }),
/* 11 */   RENDER("Render", 'Ơ', 60, new String[] { "Client-sided render modules" }),
/* 12 */   WORLD("World", 'ȇ', 60, new String[] { "Some other modules that like do something" });
/*    */   
/*    */   public String name;
/*    */   public int x;
/*    */   
/*    */   Group(String name, int x, int y, String... description) {
/* 18 */     this.name = name;
/* 19 */     this.x = x;
/* 20 */     this.y = y;
/* 21 */     this.description = description;
/*    */   }
/*    */   
/*    */   public int y;
/*    */   public String[] description;
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\gui\Group.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */