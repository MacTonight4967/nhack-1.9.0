/*    */ package me.bebeli555.cookieclient.mods.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class AntiAFK
/*    */   extends Mod {
/*    */   private static Thread thread;
/* 14 */   public static Setting delay = new Setting(Mode.DOUBLE, "Delay", Integer.valueOf(2), new String[] { "How often it does the things", "In seconds" });
/* 15 */   public static Setting rotate = new Setting(Mode.BOOLEAN, "Rotate", Boolean.valueOf(true), new String[] { "Rotates ur head" });
/* 16 */   public static Setting punch = new Setting(Mode.BOOLEAN, "Punch", Boolean.valueOf(true), new String[] { "Swings ur hand" });
/* 17 */   public static Setting jump = new Setting(Mode.BOOLEAN, "Jump", Boolean.valueOf(true), new String[] { "Jumps" });
/* 18 */   public static Setting random = new Setting(Mode.BOOLEAN, "Random", Boolean.valueOf(true), new String[] { "Chooses one random action to do from the allowed actions", "If false then it does all of them at the same time" });
/*    */ 
/*    */   
/* 21 */   public AntiAFK() { super(Group.MISC, "AntiAFK", new String[] { "Tries to prevent you from", "Getting kicked from servers if you afk" }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnabled() {
/* 26 */     thread = new Thread() {
/*    */         public void run() {
/* 28 */           while (AntiAFK.access$000() != null && AntiAFK.access$000().equals(this)) {
/* 29 */             AntiAFK.this.loop();
/*    */             
/* 31 */             Mod.sleep((int)(AntiAFK.delay.doubleValue() * 1000.0D));
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 36 */     thread.start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void onDisabled() { thread = null; }
/*    */ 
/*    */   
/*    */   public void loop() {
/* 45 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */     
/* 49 */     ArrayList<Integer> actions = new ArrayList<Integer>();
/* 50 */     if (rotate.booleanValue()) actions.add(Integer.valueOf(1)); 
/* 51 */     if (punch.booleanValue()) actions.add(Integer.valueOf(2)); 
/* 52 */     if (jump.booleanValue()) actions.add(Integer.valueOf(3));
/*    */     
/* 54 */     if (!actions.isEmpty()) {
/* 55 */       if (random.booleanValue()) {
/* 56 */         int action = ((Integer)actions.get(random(0, actions.size()))).intValue();
/* 57 */         doAction(action);
/*    */       } else {
/* 59 */         for (Iterator iterator = actions.iterator(); iterator.hasNext(); ) { int action = ((Integer)iterator.next()).intValue();
/* 60 */           doAction(action); }
/*    */       
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static void doAction(int id) {
/* 67 */     if (id == 1) {
/* 68 */       mc.field_71439_g.field_70177_z = random(0, 170);
/* 69 */       mc.field_71439_g.field_70125_A = random(0, 80);
/* 70 */     } else if (id == 2) {
/* 71 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 72 */     } else if (id == 3) {
/* 73 */       mc.field_71439_g.func_70664_aZ();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\AntiAFK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */