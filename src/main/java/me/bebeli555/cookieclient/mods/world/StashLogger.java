/*     */ package me.bebeli555.cookieclient.mods.world;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.SystemTray;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.TrayIcon;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StashLogger
/*     */   extends Mod
/*     */ {
/*  35 */   private Timer timer = new Timer();
/*  36 */   private HashMap<Chunk, ArrayList<TileEntity>> map = new HashMap();
/*  37 */   private ArrayList<Chunk> loggedChunks = new ArrayList();
/*     */   
/*  39 */   public static Setting amount = new Setting(Mode.INTEGER, "Amount", Integer.valueOf(15), new String[] { "If the chunk has this many allowed container blocks", "It will log it" });
/*  40 */   public static Setting chests = new Setting(Mode.BOOLEAN, "Chests", Boolean.valueOf(true), new String[] { "Checks for chests also trapped chests" });
/*  41 */   public static Setting droppers = new Setting(Mode.BOOLEAN, "Droppers", Boolean.valueOf(true), new String[] { "Checks for droppers" });
/*  42 */   public static Setting dispensers = new Setting(Mode.BOOLEAN, "Dispensers", Boolean.valueOf(true), new String[] { "Checks for dispensers" });
/*  43 */   public static Setting shulkers = new Setting(Mode.BOOLEAN, "Shulkers", Boolean.valueOf(true), new String[] { "Checks for shulker boxes" });
/*  44 */   public static Setting hoppers = new Setting(Mode.BOOLEAN, "Hoppers", Boolean.valueOf(true), new String[] { "Checks for hoppers" });
/*  45 */   public static Setting chatMessage = new Setting(Mode.BOOLEAN, "ChatMessage", Boolean.valueOf(true), new String[] { "Also sends a message in ingame chat when it found a chunk" });
/*  46 */   public static Setting sound = new Setting(Mode.BOOLEAN, "Sound", Boolean.valueOf(true), new String[] { "Also playes xp sound when it found a chunk" });
/*  47 */   public static Setting windowsAlert = new Setting(Mode.BOOLEAN, "WindowsAlert", Boolean.valueOf(false), new String[] { "Sends a windows alert message when it found a chunk" });
/*     */ 
/*     */   
/*  50 */   public StashLogger() { super(Group.WORLD, "StashLogger", new String[] { "Logs chunks that have many container blocks in them", "It logs them to .minecraft/CookieClient/StashLogger.txt" }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public void onDisabled() { this.loggedChunks.clear(); }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent e) {
/*  60 */     if (this.timer.hasPassed(500) && mc.field_71439_g != null && mc.field_71441_e != null && mc.field_71441_e.field_72996_f != null) {
/*  61 */       this.timer.reset();
/*     */       
/*  63 */       this.map.clear();
/*  64 */       for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
/*  65 */         if (isValid(tileEntity)) {
/*  66 */           Chunk chunk = mc.field_71441_e.func_175726_f(tileEntity.func_174877_v());
/*     */           
/*  68 */           ArrayList<TileEntity> list = new ArrayList<TileEntity>();
/*  69 */           if (this.map.containsKey(chunk)) list = (ArrayList)this.map.get(chunk); 
/*  70 */           list.add(tileEntity);
/*  71 */           this.map.put(chunk, list);
/*     */         } 
/*     */       } 
/*     */       
/*  75 */       for (Chunk chunk : this.map.keySet()) {
/*  76 */         if (((ArrayList)this.map.get(chunk)).size() >= amount.intValue() && 
/*  77 */           !this.loggedChunks.contains(chunk)) {
/*  78 */           this.loggedChunks.add(chunk);
/*  79 */           log(chunk, (ArrayList)this.map.get(chunk));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(Chunk chunk, final ArrayList<TileEntity> list) {
/*  88 */     if (list.size() <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     final int x = ((TileEntity)list.get(0)).func_174877_v().func_177958_n();
/*  93 */     final int z = ((TileEntity)list.get(0)).func_174877_v().func_177952_p();
/*     */ 
/*     */     
/*  96 */     if (chatMessage.booleanValue()) {
/*  97 */       sendMessage("Found chunk with " + list.size() + " container blocks at X: " + x + " Z: " + z, false);
/*     */     }
/*     */ 
/*     */     
/* 101 */     if (sound.booleanValue()) {
/* 102 */       mc.field_71441_e.func_184156_a(getPlayerPos(), SoundEvents.field_187802_ec, SoundCategory.AMBIENT, 100.0F, 18.0F, true);
/*     */     }
/*     */ 
/*     */     
/* 106 */     if (windowsAlert.booleanValue()) {
/* 107 */       sendWindowsAlert("Found a stash chunk!");
/*     */     }
/*     */ 
/*     */     
/* 111 */     (new Thread() {
/*     */         public void run() {
/*     */           try {
/* 114 */             File file = new File(Settings.path + "/StashLogger.txt");
/* 115 */             if (!file.exists()) file.createNewFile();
/*     */             
/* 117 */             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
/* 118 */             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
/* 119 */             LocalDateTime now = LocalDateTime.now();
/*     */             
/* 121 */             bw.write("X: " + x + " Z: " + z + " Found " + list.size() + " container blocks - " + dtf.format(now));
/* 122 */             bw.newLine();
/* 123 */             bw.close();
/* 124 */           } catch (Exception e) {
/* 125 */             System.out.println("CookieClient - Error logging chunk. StashLogger");
/* 126 */             e.printStackTrace();
/*     */           } 
/*     */         }
/* 129 */       }).start();
/*     */   }
/*     */   
/*     */   public static void sendWindowsAlert(String message) {
/*     */     try {
/* 134 */       SystemTray tray = SystemTray.getSystemTray();
/* 135 */       Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
/* 136 */       TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
/* 137 */       trayIcon.setImageAutoSize(true);
/* 138 */       trayIcon.setToolTip("System tray icon demo");
/* 139 */       tray.add(trayIcon);
/*     */       
/* 141 */       trayIcon.displayMessage("CookieClient", message, TrayIcon.MessageType.INFO);
/* 142 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(TileEntity tileEntity) {
/* 148 */     return ((chests.booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityChest) || (droppers
/* 149 */       .booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityDropper) || (dispensers
/* 150 */       .booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser) || (shulkers
/* 151 */       .booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) || (hoppers
/* 152 */       .booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityDropper));
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\world\StashLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */