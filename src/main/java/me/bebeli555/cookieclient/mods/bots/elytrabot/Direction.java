/*    */ package me.bebeli555.cookieclient.mods.bots.elytrabot;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum Direction
/*    */ {
/* 12 */   XP("X-Plus"),
/* 13 */   XM("X-Minus"),
/* 14 */   ZP("Z-Plus"),
/* 15 */   ZM("Z-Minus"),
/* 16 */   XP_ZP("X-Plus, Z-Plus"),
/* 17 */   XM_ZP("X-Minus, Z-Plus"),
/* 18 */   XM_ZM("X-Minus, Z-Minus"),
/* 19 */   XP_ZM("X-Plus, Z-Minus");
/*    */   
/*    */   public String name;
/*    */   
/* 23 */   Direction(String name) { this.name = name; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Direction getDirection() {
/* 30 */     facing = (Minecraft.func_71410_x()).field_71439_g.func_174811_aO();
/* 31 */     return (facing == EnumFacing.NORTH) ? ZM : ((facing == EnumFacing.WEST) ? XM : ((facing == EnumFacing.SOUTH) ? ZP : XP));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Direction getDiagonalDirection() {
/* 38 */     facing = (Minecraft.func_71410_x()).field_71439_g.func_174811_aO();
/*    */     
/* 40 */     if (facing.equals(EnumFacing.NORTH)) {
/* 41 */       double closest = getClosest(135.0D, -135.0D);
/* 42 */       return (closest == -135.0D) ? XP_ZM : XM_ZM;
/* 43 */     }  if (facing.equals(EnumFacing.WEST)) {
/* 44 */       double closest = getClosest(135.0D, 45.0D);
/* 45 */       return (closest == 135.0D) ? XM_ZM : XM_ZP;
/* 46 */     }  if (facing.equals(EnumFacing.EAST)) {
/* 47 */       double closest = getClosest(-45.0D, -135.0D);
/* 48 */       return (closest == -135.0D) ? XP_ZM : XP_ZP;
/*    */     } 
/* 50 */     double closest = getClosest(45.0D, -45.0D);
/* 51 */     return (closest == 45.0D) ? XM_ZP : XP_ZP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static double getClosest(double a, double b) {
/* 57 */     double yaw = (Minecraft.func_71410_x()).field_71439_g.field_70177_z;
/* 58 */     yaw = (yaw < -180.0D) ? (yaw += 360.0D) : ((yaw > 180.0D) ? (yaw -= 360.0D) : yaw);
/*    */     
/* 60 */     if (Math.abs(yaw - a) < Math.abs(yaw - b)) {
/* 61 */       return a;
/*    */     }
/* 63 */     return b;
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\bots\elytrabot\Direction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */