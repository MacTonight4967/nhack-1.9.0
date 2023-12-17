/*    */ package me.bebeli555.cookieclient.mods.movement;
/*    */ 
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*    */ import me.bebeli555.cookieclient.events.bus.Listener;
/*    */ import me.bebeli555.cookieclient.events.player.TravelEvent;
/*    */ import me.bebeli555.cookieclient.gui.Group;
/*    */ import me.bebeli555.cookieclient.gui.Mode;
/*    */ import me.bebeli555.cookieclient.gui.Setting;
/*    */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class EntitySpeed
/*    */   extends Mod
/*    */ {
/* 17 */   public static Setting speed = new Setting(Mode.DOUBLE, "Speed", Double.valueOf(0.5D), new String[] { "How fast it will go", "Higher = faster" });
/* 18 */   public static Setting antiStuck = new Setting(Mode.BOOLEAN, "AntiStuck", Boolean.valueOf(true), new String[] { "Prevents getting stuck" });
/* 19 */   public static Setting fly = new Setting(Mode.BOOLEAN, "Fly", Boolean.valueOf(false), new String[] { "Makes ur entity fly when holding jump key" });
/* 20 */   public static Setting glideSpeed = new Setting(fly, Mode.DOUBLE, "GlideSpeed", Double.valueOf(0.1D), new String[] { "How fast it glides down" });
/* 21 */   public static Setting upSpeed = new Setting(fly, Mode.DOUBLE, "UpSpeed", Integer.valueOf(1), new String[] { "How fast to go up when holding jump key" });
/*    */ 
/*    */   
/* 24 */   public EntitySpeed() { super(Group.MOVEMENT, "EntitySpeed", new String[] { "Make entities go faster" }); }
/*    */   
/*    */   @EventHandler
/* 27 */   private Listener<TravelEvent> onTravel = new Listener(event -> {
/*    */         
/* 29 */         if (mc.field_71439_g == null || mc.field_71439_g.field_184239_as == null) {
/*    */           return;
/*    */         }
/*    */         
/* 33 */         if (mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.EntityPig || (mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.AbstractHorse && mc.field_71439_g.field_184239_as.func_184179_bs().equals(mc.field_71439_g))) {
/* 34 */           moveEntity(mc.field_71439_g.field_184239_as, speed.doubleValue(), antiStuck.booleanValue());
/*    */           
/* 36 */           if (mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.AbstractHorse) {
/* 37 */             mc.field_71439_g.field_184239_as.field_70177_z = mc.field_71439_g.field_70177_z;
/*    */           }
/*    */           
/* 40 */           if (fly.booleanValue()) {
/* 41 */             fly(mc.field_71439_g.field_184239_as);
/*    */           }
/*    */         } 
/*    */       }new java.util.function.Predicate[0]);
/*    */   
/*    */   public static void moveEntity(Entity entity, double speed, boolean antiStuck) {
/* 47 */     double yawRad = Math.toRadians(mc.field_71439_g.field_70177_z - Freecam.getRotationFromVec(new Vec3d(-mc.field_71439_g.field_70702_br, 0.0D, mc.field_71439_g.field_191988_bg))[0]);
/*    */     
/* 49 */     if (isInputting()) {
/* 50 */       entity.field_70159_w = -Math.sin(yawRad) * speed;
/* 51 */       entity.field_70179_y = Math.cos(yawRad) * speed;
/*    */     } else {
/* 53 */       entity.field_70159_w = 0.0D;
/* 54 */       entity.field_70179_y = 0.0D;
/*    */     } 
/*    */     
/* 57 */     if (antiStuck && entity.field_70163_u > entity.field_70137_T) {
/* 58 */       entity.field_70159_w = -Math.sin(yawRad) * 0.1D;
/* 59 */       entity.field_70179_y = Math.cos(yawRad) * 0.1D;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 64 */   public static boolean isInputting() { return (mc.field_71474_y.field_74351_w.func_151470_d() || mc.field_71474_y.field_74368_y.func_151470_d() || mc.field_71474_y.field_74366_z.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d()); }
/*    */ 
/*    */   
/*    */   public static void fly(Entity entity) {
/* 68 */     if (!entity.func_70090_H()) {
/* 69 */       entity.field_70181_x = -glideSpeed.doubleValue();
/*    */     }
/*    */     
/* 72 */     if (mc.field_71474_y.field_74314_A.func_151470_d())
/* 73 */       entity.field_70181_x += upSpeed.doubleValue(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\movement\EntitySpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */