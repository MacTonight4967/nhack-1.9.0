/*     */ package me.bebeli555.cookieclient;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import me.bebeli555.cookieclient.events.bus.EventBus;
/*     */ import me.bebeli555.cookieclient.events.bus.EventManager;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.GuiNode;
/*     */ import me.bebeli555.cookieclient.gui.GuiSettings;
/*     */ import me.bebeli555.cookieclient.gui.Keybind;
/*     */ import me.bebeli555.cookieclient.gui.SetGuiNodes;
/*     */ import me.bebeli555.cookieclient.gui.Settings;
/*     */ import me.bebeli555.cookieclient.hud.HudEditor;
/*     */ import me.bebeli555.cookieclient.hud.HudSettings;
/*     */ import me.bebeli555.cookieclient.hud.components.ArrayListComponent;
/*     */ import me.bebeli555.cookieclient.mods.bots.ObbyBuilderBot;
/*     */ import me.bebeli555.cookieclient.mods.bots.elytrabot.ElytraBot;
/*     */ import me.bebeli555.cookieclient.mods.combat.AutoArmor;
/*     */ import me.bebeli555.cookieclient.mods.combat.AutoCrystal;
/*     */ import me.bebeli555.cookieclient.mods.combat.AutoLog;
/*     */ import me.bebeli555.cookieclient.mods.combat.AutoTotem;
/*     */ import me.bebeli555.cookieclient.mods.combat.AutoTrap;
/*     */ import me.bebeli555.cookieclient.mods.combat.Criticals;
/*     */ import me.bebeli555.cookieclient.mods.combat.HoleFiller;
/*     */ import me.bebeli555.cookieclient.mods.combat.KillAura;
/*     */ import me.bebeli555.cookieclient.mods.combat.NoKnockback;
/*     */ import me.bebeli555.cookieclient.mods.combat.Offhand;
/*     */ import me.bebeli555.cookieclient.mods.combat.PistonAura;
/*     */ import me.bebeli555.cookieclient.mods.combat.SelfWeb;
/*     */ import me.bebeli555.cookieclient.mods.combat.Surround;
/*     */ import me.bebeli555.cookieclient.mods.exploits.Burrow;
/*     */ import me.bebeli555.cookieclient.mods.exploits.LiquidInteract;
/*     */ import me.bebeli555.cookieclient.mods.exploits.MiningSpoof;
/*     */ import me.bebeli555.cookieclient.mods.exploits.NewChunks;
/*     */ import me.bebeli555.cookieclient.mods.exploits.PacketFly;
/*     */ import me.bebeli555.cookieclient.mods.exploits.PortalGodMode;
/*     */ import me.bebeli555.cookieclient.mods.exploits.Reach;
/*     */ import me.bebeli555.cookieclient.mods.games.Snake;
/*     */ import me.bebeli555.cookieclient.mods.games.tetris.Tetris;
/*     */ import me.bebeli555.cookieclient.mods.misc.AntiAFK;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoEat;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoFirework;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoHotbar;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoInventoryManager;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoMend;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoMessager;
/*     */ import me.bebeli555.cookieclient.mods.misc.AutoReconnect;
/*     */ import me.bebeli555.cookieclient.mods.misc.ChestSwap;
/*     */ import me.bebeli555.cookieclient.mods.misc.DiscordRPC;
/*     */ import me.bebeli555.cookieclient.mods.misc.FakePlayer;
/*     */ import me.bebeli555.cookieclient.mods.misc.Friends;
/*     */ import me.bebeli555.cookieclient.mods.misc.MiddleClickFriends;
/*     */ import me.bebeli555.cookieclient.mods.misc.NoSound;
/*     */ import me.bebeli555.cookieclient.mods.misc.PacketCanceller;
/*     */ import me.bebeli555.cookieclient.mods.misc.UpdateChecker;
/*     */ import me.bebeli555.cookieclient.mods.misc.VisualRange;
/*     */ import me.bebeli555.cookieclient.mods.misc.XCarry;
/*     */ import me.bebeli555.cookieclient.mods.movement.AntiHunger;
/*     */ import me.bebeli555.cookieclient.mods.movement.AntiLevitation;
/*     */ import me.bebeli555.cookieclient.mods.movement.AutoSprint;
/*     */ import me.bebeli555.cookieclient.mods.movement.AutoWalk;
/*     */ import me.bebeli555.cookieclient.mods.movement.Blink;
/*     */ import me.bebeli555.cookieclient.mods.movement.ElytraFly;
/*     */ import me.bebeli555.cookieclient.mods.movement.EntityControl;
/*     */ import me.bebeli555.cookieclient.mods.movement.EntitySpeed;
/*     */ import me.bebeli555.cookieclient.mods.movement.Flight;
/*     */ import me.bebeli555.cookieclient.mods.movement.HighJump;
/*     */ import me.bebeli555.cookieclient.mods.movement.IceSpeed;
/*     */ import me.bebeli555.cookieclient.mods.movement.InventoryMove;
/*     */ import me.bebeli555.cookieclient.mods.movement.Jesus;
/*     */ import me.bebeli555.cookieclient.mods.movement.LiquidSpeed;
/*     */ import me.bebeli555.cookieclient.mods.movement.NoFall;
/*     */ import me.bebeli555.cookieclient.mods.movement.NoRotate;
/*     */ import me.bebeli555.cookieclient.mods.movement.NoSlowDown;
/*     */ import me.bebeli555.cookieclient.mods.movement.SafeWalk;
/*     */ import me.bebeli555.cookieclient.mods.movement.Speed;
/*     */ import me.bebeli555.cookieclient.mods.movement.Step;
/*     */ import me.bebeli555.cookieclient.mods.movement.Strafe;
/*     */ import me.bebeli555.cookieclient.mods.render.AutoTrapIndicator;
/*     */ import me.bebeli555.cookieclient.mods.render.BlockVision;
/*     */ import me.bebeli555.cookieclient.mods.render.EntityESP;
/*     */ import me.bebeli555.cookieclient.mods.render.Freecam;
/*     */ import me.bebeli555.cookieclient.mods.render.FullBright;
/*     */ import me.bebeli555.cookieclient.mods.render.HoleESP;
/*     */ import me.bebeli555.cookieclient.mods.render.LiquidVision;
/*     */ import me.bebeli555.cookieclient.mods.render.NameTags;
/*     */ import me.bebeli555.cookieclient.mods.render.NoRender;
/*     */ import me.bebeli555.cookieclient.mods.render.Search;
/*     */ import me.bebeli555.cookieclient.mods.render.ShulkerPreview;
/*     */ import me.bebeli555.cookieclient.mods.render.Tracers;
/*     */ import me.bebeli555.cookieclient.mods.render.Trajectories;
/*     */ import me.bebeli555.cookieclient.mods.render.VoidESP;
/*     */ import me.bebeli555.cookieclient.mods.render.Waypoints;
/*     */ import me.bebeli555.cookieclient.mods.render.XRay;
/*     */ import me.bebeli555.cookieclient.mods.render.Zoom;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoBuilder;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoEnderChestMiner;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoEnderpearl;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoFish;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoRespawn;
/*     */ import me.bebeli555.cookieclient.mods.world.AutoTool;
/*     */ import me.bebeli555.cookieclient.mods.world.CrystalBlock;
/*     */ import me.bebeli555.cookieclient.mods.world.FastUse;
/*     */ import me.bebeli555.cookieclient.mods.world.NoEntityTrace;
/*     */ import me.bebeli555.cookieclient.mods.world.NoGlitchBlocks;
/*     */ import me.bebeli555.cookieclient.mods.world.PacketMine;
/*     */ import me.bebeli555.cookieclient.mods.world.Scaffold;
/*     */ import me.bebeli555.cookieclient.mods.world.SpeedMine;
/*     */ import me.bebeli555.cookieclient.mods.world.StashLogger;
/*     */ import me.bebeli555.cookieclient.mods.world.Timer;
/*     */ import me.bebeli555.cookieclient.rendering.Renderer;
/*     */ import me.bebeli555.cookieclient.utils.EatingUtil;
/*     */ import me.bebeli555.cookieclient.utils.InformationUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ @Mod(modid = "nhack", name = "nhack", version = "1.01")
/*     */ public class Mod
/*     */ {
/*     */   public static final String MODID = "nhack";
/*     */   public static final String NAME = "nhack";
/*     */   public static final String VERSION = "1.01";
/*     */   public static final String DISCORD = "github.com/christallinqq";
/* 141 */   public static Minecraft mc = Minecraft.func_71410_x();
/* 142 */   public static final EventBus EVENT_BUS = new EventManager();
/*     */   
/* 144 */   public String name = "";
/*     */   
/*     */   public String[] description;
/*     */   public Group group;
/*     */   private boolean toggled;
/*     */   private boolean hiddenOn;
/*     */   private boolean lastHiddenOn;
/* 151 */   private int renderNumber = -1; public boolean defaultOn; public boolean defaultHidden;
/* 152 */   public static ArrayList<Mod> modules = new ArrayList(); public boolean autoSubscribe = true;
/*     */   
/*     */   public Mod(Group group, String name, String... description) {
/* 155 */     this.group = group;
/* 156 */     this.name = name;
/* 157 */     this.description = description;
/* 158 */     modules.add(this);
/*     */   }
/*     */   
/*     */   public Mod(Group group) {
/* 162 */     this.group = group;
/* 163 */     modules.add(this);
/*     */   }
/*     */   private GuiNode guiNode;
/*     */   private GuiNode hiddenNode;
/*     */   
/*     */   public Mod() {}
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent event) {
/* 172 */     long ms = System.currentTimeMillis();
/*     */ 
/*     */     
/* 175 */     MinecraftForge.EVENT_BUS.register(new HelpMessage());
/* 176 */     MinecraftForge.EVENT_BUS.register(new Commands());
/* 177 */     MinecraftForge.EVENT_BUS.register(new Keybind());
/* 178 */     MinecraftForge.EVENT_BUS.register(new EatingUtil());
/* 179 */     MinecraftForge.EVENT_BUS.register(new Renderer());
/* 180 */     EVENT_BUS.subscribe(new Renderer());
/* 181 */     InformationUtil informationUtil = new InformationUtil();
/* 182 */     MinecraftForge.EVENT_BUS.register(informationUtil);
/* 183 */     EVENT_BUS.subscribe(informationUtil);
/*     */ 
/*     */     
/* 186 */     initMods();
/*     */ 
/*     */     
/* 189 */     (new File(Settings.path)).mkdir();
/* 190 */     Friends.loadFriends();
/* 191 */     SetGuiNodes.setGuiNodes();
/* 192 */     SetGuiNodes.setDefaults();
/* 193 */     Settings.loadSettings();
/* 194 */     Keybind.setKeybinds();
/* 195 */     HudSettings.loadSettings();
/*     */     
/* 197 */     for (Mod module : modules) {
/* 198 */       module.onPostInit();
/*     */     }
/*     */     
/* 201 */     System.out.println("CookieClient - Initialization took " + Math.abs(System.currentTimeMillis() - ms) + "ms");
/*     */   }
/*     */ 
/*     */   
/*     */   public void initMods() {
/* 206 */     new AutoArmor();
/* 207 */     new AutoCrystal();
/* 208 */     new AutoLog();
/* 209 */     new AutoTotem();
/* 210 */     new AutoTrap();
/* 211 */     new Criticals();
/* 212 */     new HoleFiller();
/* 213 */     new KillAura();
/* 214 */     new NoKnockback();
/* 215 */     new SelfWeb();
/* 216 */     new Surround();
/* 217 */     new Offhand();
/* 218 */     new PistonAura();
/*     */ 
/*     */     
/* 221 */     new Burrow();
/* 222 */     new MiningSpoof();
/* 223 */     new NewChunks();
/* 224 */     new PacketFly();
/* 225 */     new Reach();
/* 226 */     new PortalGodMode();
/* 227 */     new LiquidInteract();
/*     */ 
/*     */     
/* 230 */     new AntiAFK();
/* 231 */     new AutoEat();
/* 232 */     new AutoFirework();
/* 233 */     new AutoInventoryManager();
/* 234 */     new AutoMend();
/* 235 */     new AutoMessager();
/* 236 */     new AutoReconnect();
/* 237 */     new ChestSwap(); 
/* 238 */     try { new DiscordRPC(); } catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
/* 239 */     new FakePlayer();
/* 240 */     new Friends();
/* 241 */     new MiddleClickFriends();
/* 242 */     new PacketCanceller();
/* 243 */     new UpdateChecker();
/* 244 */     new VisualRange();
/* 245 */     new XCarry();
/* 246 */     new NoSound();
/* 247 */     new AutoHotbar();
/*     */ 
/*     */     
/* 250 */     new AntiHunger();
/* 251 */     new AntiLevitation();
/* 252 */     new AutoSprint();
/* 253 */     new AutoWalk();
/* 254 */     new Blink();
/* 255 */     new ElytraFly();
/* 256 */     new EntityControl();
/* 257 */     new EntitySpeed();
/* 258 */     new Flight();
/* 259 */     new HighJump();
/* 260 */     new IceSpeed();
/* 261 */     new InventoryMove();
/* 262 */     new Jesus();
/* 263 */     new NoFall();
/* 264 */     new NoRotate();
/* 265 */     new NoSlowDown();
/* 266 */     new SafeWalk();
/* 267 */     new Speed();
/* 268 */     new Step();
/* 269 */     new Strafe();
/* 270 */     new LiquidSpeed();
/*     */ 
/*     */     
/* 273 */     new AutoTrapIndicator();
/* 274 */     new BlockVision();
/* 275 */     new EntityESP();
/* 276 */     new Freecam();
/* 277 */     new FullBright();
/* 278 */     new HoleESP();
/* 279 */     new LiquidVision();
/* 280 */     new NameTags();
/* 281 */     new NoRender();
/* 282 */     new Search();
/* 283 */     new ShulkerPreview();
/* 284 */     new Tracers();
/* 285 */     new Trajectories();
/* 286 */     new VoidESP();
/* 287 */     new Waypoints();
/* 288 */     new XRay();
/* 289 */     new Zoom();
/*     */ 
/*     */     
/* 292 */     new AutoBuilder();
/* 293 */     new AutoEnderChestMiner();
/* 294 */     new AutoFish();
/* 295 */     new CrystalBlock();
/* 296 */     new FastUse();
/* 297 */     new NoEntityTrace();
/* 298 */     new NoGlitchBlocks();
/* 299 */     new PacketMine();
/* 300 */     new Scaffold();
/* 301 */     new SpeedMine();
/* 302 */     new Timer();
/* 303 */     new AutoTool();
/* 304 */     new AutoRespawn();
/* 305 */     new StashLogger();
/* 306 */     new AutoEnderpearl();
/*     */ 
/*     */     
/* 309 */     new Snake();
/* 310 */     new Tetris();
/*     */ 
/*     */     
/* 313 */     new ElytraBot();
/* 314 */     new ObbyBuilderBot();
/*     */ 
/*     */     
/* 317 */     List<String> names = new ArrayList<String>();
/* 318 */     for (Mod module : modules) {
/* 319 */       names.add(module.name);
/*     */     }
/*     */     
/* 322 */     String[] sortedNames = new String[names.size()];
/* 323 */     sortedNames = (String[])names.toArray(sortedNames);
/* 324 */     Arrays.sort(sortedNames);
/*     */     
/* 326 */     ArrayList<Mod> temp = new ArrayList<Mod>();
/* 327 */     for (String name : sortedNames) {
/* 328 */       for (Mod module : modules) {
/* 329 */         if (module.name.equals(name)) {
/* 330 */           temp.add(module);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 336 */     modules = temp;
/*     */ 
/*     */     
/* 339 */     new HudEditor();
/* 340 */     new GuiSettings();
/*     */   }
/*     */   
/*     */   public void onEnabled() {}
/*     */   
/*     */   public void onDisabled() {}
/*     */   
/* 347 */   public boolean onGuiClick(int x, int y, int button) { return false; }
/*     */   
/*     */   public void onPostInit() {}
/*     */   
/*     */   public void onGuiDrawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */   
/*     */   public void onGuiKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {}
/*     */   
/*     */   public void onRenderWorld(float partialTicks) {}
/*     */   
/*     */   public void sendMessage(String text, boolean red) {
/* 358 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 363 */     String module = "";
/* 364 */     ChatFormatting color = ChatFormatting.WHITE;
/* 365 */     if (red) {
/* 366 */       color = ChatFormatting.RED;
/*     */     }
/* 368 */     if (!this.name.isEmpty()) {
/* 369 */       module = "-" + this.name;
/*     */     }
/*     */     
/* 372 */     mc.field_71439_g.func_145747_a(new TextComponentString(ChatFormatting.GREEN + "[" + ChatFormatting.LIGHT_PURPLE + "nhack" + module + ChatFormatting.GREEN + "] " + color + text));
/*     */   }
/*     */ 
/*     */   
/* 376 */   public int getRenderNumber() { return this.renderNumber; }
/*     */ 
/*     */   
/*     */   public void setRenderNumber(int number) {
/* 380 */     if (this.renderNumber == -1) {
/* 381 */       ArrayListComponent.lastArraylistSize = -1;
/*     */     }
/*     */     
/* 384 */     this.renderNumber = number;
/*     */   }
/*     */   
/*     */   public void enable() {
/* 388 */     if (this.autoSubscribe) {
/* 389 */       MinecraftForge.EVENT_BUS.register(this);
/* 390 */       EVENT_BUS.subscribe(this);
/*     */     } 
/* 392 */     (getGuiNode()).toggled = true;
/* 393 */     ArrayListComponent.arraylist.add(this);
/* 394 */     this.toggled = true;
/* 395 */     onEnabled();
/*     */   }
/*     */   
/*     */   public void disable() {
/* 399 */     if (this.autoSubscribe) {
/* 400 */       MinecraftForge.EVENT_BUS.unregister(this);
/* 401 */       EVENT_BUS.unsubscribe(this);
/*     */     } 
/* 403 */     (getGuiNode()).toggled = false;
/* 404 */     ArrayListComponent.arraylist.remove(this);
/* 405 */     this.toggled = false;
/* 406 */     onDisabled();
/*     */   }
/*     */   
/*     */   public void toggle() {
/* 410 */     if (this.toggled) {
/* 411 */       disable();
/*     */     } else {
/* 413 */       enable();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHiddenOn(boolean value) {
/* 422 */     this.hiddenOn = value;
/*     */     
/* 424 */     if (this.hiddenOn != this.lastHiddenOn) {
/* 425 */       if (this.hiddenOn) {
/* 426 */         if (this.autoSubscribe) {
/* 427 */           MinecraftForge.EVENT_BUS.register(this);
/* 428 */           EVENT_BUS.subscribe(this);
/*     */         } 
/* 430 */         onEnabled();
/*     */       } else {
/* 432 */         if (this.autoSubscribe) {
/* 433 */           MinecraftForge.EVENT_BUS.unregister(this);
/* 434 */           EVENT_BUS.unsubscribe(this);
/*     */         } 
/* 436 */         onDisabled();
/*     */       } 
/*     */     }
/*     */     
/* 440 */     this.lastHiddenOn = this.hiddenOn;
/*     */   }
/*     */   
/*     */   public GuiNode getGuiNode() {
/* 444 */     if (this.guiNode == null) {
/* 445 */       this.guiNode = Settings.getGuiNodeFromId(this.name);
/* 446 */       return this.guiNode;
/*     */     } 
/* 448 */     return this.guiNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 453 */     if (this.hiddenOn) {
/* 454 */       return true;
/*     */     }
/*     */     
/* 457 */     if (this.hiddenNode == null) {
/* 458 */       this.hiddenNode = Settings.getGuiNodeFromId(this.name + "Hidden");
/*     */     }
/*     */     
/* 461 */     return this.hiddenNode.toggled;
/*     */   }
/*     */ 
/*     */   
/* 465 */   public boolean isOn() { return this.toggled; }
/*     */ 
/*     */   
/*     */   public static void toggleMod(String name, boolean on) {
/* 469 */     GuiNode node = Settings.getGuiNodeFromId(name);
/*     */     
/* 471 */     node.toggled = on;
/* 472 */     node.setSetting();
/*     */     
/* 474 */     for (Mod module : modules) {
/* 475 */       if (module.name.equals(name)) {
/* 476 */         if (on) {
/* 477 */           module.enable();
/*     */         } else {
/* 479 */           module.disable();
/*     */         } 
/*     */         
/* 482 */         module.toggled = on;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 492 */   public static boolean random(int i) { return ((new Random()).nextInt(i + 1) == 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 499 */   public static int random(int min, int max) { return (new Random()).nextInt(min + max) - min; }
/*     */ 
/*     */ 
/*     */   
/* 503 */   public void setStatus(String status) { setStatus(status, this.name); }
/*     */ 
/*     */   
/*     */   public static void setStatus(String status, String module) {
/* 507 */     if (!module.isEmpty()) {
/* 508 */       module = "-" + module;
/*     */     }
/*     */     
/* 511 */     Renderer.status = new String[10];
/* 512 */     Renderer.status[0] = ChatFormatting.GREEN + "[" + ChatFormatting.LIGHT_PURPLE + "nhack" + module + ChatFormatting.GREEN + "] " + ChatFormatting.WHITE + status;
/*     */   }
/*     */ 
/*     */   
/* 516 */   public void addToStatus(String status, int index) { addToStatus(status, index, this.name); }
/*     */ 
/*     */   
/*     */   public static void addToStatus(String status, int index, String module) {
/* 520 */     if (!module.isEmpty()) {
/* 521 */       module = "-" + module;
/*     */     }
/*     */     
/* 524 */     Renderer.status[index] = ChatFormatting.WHITE + status;
/*     */   }
/*     */ 
/*     */   
/* 528 */   public static void clearStatus() { Renderer.status = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 533 */   public static void suspend(Thread thread) { if (thread != null) thread.suspend();
/*     */      }
/*     */   
/*     */   public static Block getBlock(BlockPos pos) {
/*     */     try {
/* 538 */       return mc.field_71441_e.func_180495_p(pos).func_177230_c();
/* 539 */     } catch (NullPointerException e) {
/* 540 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSolid(BlockPos pos) {
/*     */     try {
/* 546 */       return mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76220_a();
/* 547 */     } catch (NullPointerException e) {
/* 548 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 553 */   public static BlockPos getPlayerPos() { return new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPotionActive(String name, EntityPlayer player) {
/* 560 */     for (PotionEffect effect : player.func_70651_bq()) {
/* 561 */       if (effect.func_76453_d().contains(name.toLowerCase())) {
/* 562 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 566 */     return false;
/*     */   }
/*     */   
/*     */   public static String[] addToArray(String[] myArray, String newItem) {
/* 570 */     int currentSize = myArray.length;
/* 571 */     int newSize = currentSize + 1;
/* 572 */     String[] tempArray = new String[newSize];
/* 573 */     for (int i = 0; i < currentSize; i++) {
/* 574 */       tempArray[i] = myArray[i];
/*     */     }
/* 576 */     tempArray[newSize - 1] = newItem;
/*     */     
/* 578 */     return tempArray;
/*     */   }
/*     */   
/*     */   public static void sleep(int ms) {
/*     */     try {
/* 583 */       Thread.sleep(ms);
/* 584 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sleepUntil(BooleanSupplier condition, int timeout) {
/* 590 */     long startTime = System.currentTimeMillis();
/*     */     
/* 592 */     while (!condition.getAsBoolean()) {
/*     */       
/* 594 */       if (timeout != -1 && System.currentTimeMillis() - startTime >= timeout) {
/*     */         break;
/*     */       }
/*     */       
/* 598 */       sleep(10);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sleepUntil(BooleanSupplier condition, int timeout, int amountToSleep) {
/* 603 */     long startTime = System.currentTimeMillis();
/*     */     
/* 605 */     while (!condition.getAsBoolean()) {
/*     */       
/* 607 */       if (timeout != -1 && System.currentTimeMillis() - startTime >= timeout) {
/*     */         break;
/*     */       }
/*     */       
/* 611 */       sleep(amountToSleep);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class HelpMessage
/*     */   {
/*     */     boolean check = false;
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onTick(TickEvent.ClientTickEvent e) {
/* 621 */       if (!this.check && Mod.mc.field_71439_g != null) {
/* 622 */         if (!Settings.settings.exists()) {
/* 623 */           (new Mod()).sendMessage("Welcome to " + ChatFormatting.GREEN + "CookieClient" + ChatFormatting.WHITE + " version " + ChatFormatting.GREEN + "1.01", false);
/* 624 */           (new Mod()).sendMessage("You can open the GUI by typing " + ChatFormatting.GREEN + GuiSettings.prefix.stringValue() + "gui" + ChatFormatting.WHITE + " on chat", false);
/* 625 */           Settings.saveSettings();
/*     */         } 
/*     */         
/* 628 */         this.check = true;
/* 629 */         MinecraftForge.EVENT_BUS.unregister(this);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\Mod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */