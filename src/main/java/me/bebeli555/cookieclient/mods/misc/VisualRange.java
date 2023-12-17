/*     */ package me.bebeli555.cookieclient.mods.misc;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.entity.EntityAddedEvent;
/*     */ import me.bebeli555.cookieclient.events.entity.EntityRemovedEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ 
/*     */ 
/*     */ public class VisualRange
/*     */   extends Mod
/*     */ {
/*  21 */   private static ArrayList<String> entities = new ArrayList();
/*     */   
/*  23 */   public static Setting mode = new Setting(null, "Mode", "Message", new String[][] { { "Message", "Sends a message in chat" }, { "Sound", "Plays xp sound" }, { "Both", "Message and sound" } });
/*  24 */   public static Setting friends = new Setting(Mode.BOOLEAN, "Friends", Boolean.valueOf(true), new String[] { "Notifies if the player is friend" });
/*  25 */   public static Setting enter = new Setting(Mode.BOOLEAN, "Enter", Boolean.valueOf(true), new String[] { "Notifies when they enter visual range" });
/*  26 */   public static Setting leave = new Setting(Mode.BOOLEAN, "Leave", Boolean.valueOf(true), new String[] { "Notifies when they leave visual range" });
/*     */   
/*     */   public VisualRange() {
/*  29 */     super(Group.MISC, "VisualRange", new String[] { "Notifies you when people", "Enter and leave ur visual distance" });
/*     */ 
/*     */     
/*  32 */     this.onEntityAdded = new Listener(event -> {
/*     */           
/*  34 */           if (!isValid(event.entity)) {
/*     */             return;
/*     */           }
/*     */           
/*  38 */           if (!entities.contains(event.entity.func_70005_c_())) {
/*  39 */             entities.add(event.entity.func_70005_c_());
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */           
/*  44 */           if (enter.booleanValue()) {
/*  45 */             notify(event.entity, true);
/*     */           }
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/*  49 */     this.onEntityRemoved = new Listener(event -> {
/*     */           
/*  51 */           if (!isValid(event.entity)) {
/*     */             return;
/*     */           }
/*     */           
/*  55 */           if (entities.contains(event.entity.func_70005_c_())) {
/*  56 */             entities.remove(event.entity.func_70005_c_());
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */           
/*  61 */           if (leave.booleanValue())
/*  62 */             notify(event.entity, false); 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   public void notify(Entity entity, boolean enter) {
/*  67 */     String message = "";
/*  68 */     if (Friends.isFriend(entity)) {
/*  69 */       message = ChatFormatting.AQUA + entity.func_70005_c_();
/*     */     } else {
/*  71 */       message = ChatFormatting.GRAY + entity.func_70005_c_();
/*     */     } 
/*     */     
/*  74 */     if (enter) {
/*  75 */       message = message + ChatFormatting.GREEN + " entered";
/*     */     } else {
/*  77 */       message = message + ChatFormatting.RED + " left";
/*     */     } 
/*     */     
/*  80 */     if (mode.stringValue().equals("Message") || mode.stringValue().equals("Both")) {
/*  81 */       sendMessage(message, false);
/*     */     }
/*     */     
/*  84 */     if (mode.stringValue().equals("Sound") || mode.stringValue().equals("Both")) {
/*     */       try {
/*  86 */         if (enter) {
/*  87 */           mc.field_71441_e.func_184156_a(getPlayerPos(), SoundEvents.field_187802_ec, SoundCategory.AMBIENT, 150.0F, 10.0F, true);
/*     */         } else {
/*  89 */           mc.field_71441_e.func_184156_a(getPlayerPos(), SoundEvents.field_187604_bf, SoundCategory.NEUTRAL, 150.0F, 10.0F, true);
/*     */         } 
/*  91 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<EntityAddedEvent> onEntityAdded;
/*     */   
/*     */   public boolean isValid(Entity entity) {
/*  99 */     if (mc.field_71439_g == null || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     if (entity.func_70028_i(mc.field_71439_g) || (Friends.isFriend(entity) && !friends.booleanValue()) || entity.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
/* 104 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (entity.func_145782_y() == -100) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     return true;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<EntityRemovedEvent> onEntityRemoved;
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\VisualRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */