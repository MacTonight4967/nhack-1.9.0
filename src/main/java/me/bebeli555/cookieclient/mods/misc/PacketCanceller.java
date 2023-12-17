/*     */ package me.bebeli555.cookieclient.mods.misc;
/*     */ 
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import me.bebeli555.cookieclient.utils.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketCanceller
/*     */   extends Mod
/*     */ {
/* 119 */   private static Timer timer = new Timer();
/*     */   
/* 121 */   public static Setting serverPackets = new Setting(Mode.LABEL, "ServerPackets", Boolean.valueOf(true), new String[] { "Server packets (S)", "Attempts to skip the queue server and automatically send you to 2b2t" });
/* 122 */   public static Setting sPacketAdvancementInfo = new Setting(serverPackets, Mode.BOOLEAN, "SPacketAdvancementInfo", Boolean.valueOf(false), new String[0]);
/* 123 */   public static Setting sPacketAnimation = new Setting(serverPackets, Mode.BOOLEAN, "SPacketAnimation", Boolean.valueOf(false), new String[0]);
/* 124 */   public static Setting sPacketBlockAction = new Setting(serverPackets, Mode.BOOLEAN, "SPacketBlockAction", Boolean.valueOf(false), new String[0]);
/* 125 */   public static Setting sPacketBlockBreakAnim = new Setting(serverPackets, Mode.BOOLEAN, "SPacketBlockBreakAnim", Boolean.valueOf(false), new String[0]);
/* 126 */   public static Setting sPacketBlockChange = new Setting(serverPackets, Mode.BOOLEAN, "SPacketBlockChange", Boolean.valueOf(false), new String[0]);
/* 127 */   public static Setting sPacketCamera = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCamera", Boolean.valueOf(false), new String[0]);
/* 128 */   public static Setting sPacketChangeGameState = new Setting(serverPackets, Mode.BOOLEAN, "SPacketChangeGameState", Boolean.valueOf(false), new String[0]);
/* 129 */   public static Setting sPacketChat = new Setting(serverPackets, Mode.BOOLEAN, "SPacketChat", Boolean.valueOf(false), new String[0]);
/* 130 */   public static Setting sPacketChunkData = new Setting(serverPackets, Mode.BOOLEAN, "SPacketChunkData", Boolean.valueOf(false), new String[0]);
/* 131 */   public static Setting sPacketCloseWindow = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCloseWindow", Boolean.valueOf(false), new String[0]);
/* 132 */   public static Setting sPacketCollectItem = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCollectItem", Boolean.valueOf(false), new String[0]);
/* 133 */   public static Setting sPacketCombatEvent = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCombatEvent", Boolean.valueOf(false), new String[0]);
/* 134 */   public static Setting sPacketConfirmTransaction = new Setting(serverPackets, Mode.BOOLEAN, "SPacketConfirmTransaction", Boolean.valueOf(false), new String[0]);
/* 135 */   public static Setting sPacketCooldown = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCooldown", Boolean.valueOf(false), new String[0]);
/* 136 */   public static Setting sPacketCustomPayload = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCustomPayload", Boolean.valueOf(false), new String[0]);
/* 137 */   public static Setting sPacketCustomSound = new Setting(serverPackets, Mode.BOOLEAN, "SPacketCustomSound", Boolean.valueOf(false), new String[0]);
/* 138 */   public static Setting sPacketDestroyEntities = new Setting(serverPackets, Mode.BOOLEAN, "SPacketDestroyEntities", Boolean.valueOf(false), new String[0]);
/* 139 */   public static Setting sPacketDisconnect = new Setting(serverPackets, Mode.BOOLEAN, "SPacketDisconnect", Boolean.valueOf(false), new String[0]);
/* 140 */   public static Setting sPacketDisplayObjective = new Setting(serverPackets, Mode.BOOLEAN, "SPacketDisplayObjective", Boolean.valueOf(false), new String[0]);
/* 141 */   public static Setting sPacketEffect = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEffect", Boolean.valueOf(false), new String[0]);
/* 142 */   public static Setting sPacketEntity = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntity", Boolean.valueOf(false), new String[0]);
/* 143 */   public static Setting s15PacketEntityRelMove = new Setting(sPacketEntity, Mode.BOOLEAN, "S15PacketEntityRelMove", Boolean.valueOf(false), new String[0]);
/* 144 */   public static Setting s16PacketEntityLook = new Setting(sPacketEntity, Mode.BOOLEAN, "S16PacketEntityLook", Boolean.valueOf(false), new String[0]);
/* 145 */   public static Setting s17PacketEntityLookMove = new Setting(sPacketEntity, Mode.BOOLEAN, "S17PacketEntityLookMove", Boolean.valueOf(false), new String[0]);
/* 146 */   public static Setting sPacketEntityAttach = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityAttach", Boolean.valueOf(false), new String[0]);
/* 147 */   public static Setting sPacketEntityEffect = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityEffect", Boolean.valueOf(false), new String[0]);
/* 148 */   public static Setting sPacketEntityEquipment = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityEquipment", Boolean.valueOf(false), new String[0]);
/* 149 */   public static Setting sPacketEntityHeadLook = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityHeadLook", Boolean.valueOf(false), new String[0]);
/* 150 */   public static Setting sPacketEntityMetadata = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityMetadata", Boolean.valueOf(false), new String[0]);
/* 151 */   public static Setting sPacketEntityProperties = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityProperties", Boolean.valueOf(false), new String[0]);
/* 152 */   public static Setting sPacketEntityStatus = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityStatus", Boolean.valueOf(false), new String[0]);
/* 153 */   public static Setting sPacketEntityTeleport = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityTeleport", Boolean.valueOf(false), new String[0]);
/* 154 */   public static Setting sPacketEntityVelocity = new Setting(serverPackets, Mode.BOOLEAN, "SPacketEntityVelocity", Boolean.valueOf(false), new String[0]);
/* 155 */   public static Setting sPacketExplosion = new Setting(serverPackets, Mode.BOOLEAN, "SPacketExplosion", Boolean.valueOf(false), new String[0]);
/* 156 */   public static Setting sPacketHeldItemChange = new Setting(serverPackets, Mode.BOOLEAN, "SPacketHeldItemChange", Boolean.valueOf(false), new String[0]);
/* 157 */   public static Setting sPacketJoinGame = new Setting(serverPackets, Mode.BOOLEAN, "SPacketJoinGame", Boolean.valueOf(false), new String[0]);
/* 158 */   public static Setting sPacketKeepAlive = new Setting(serverPackets, Mode.BOOLEAN, "SPacketKeepAlive", Boolean.valueOf(false), new String[0]);
/* 159 */   public static Setting sPacketMaps = new Setting(serverPackets, Mode.BOOLEAN, "SPacketMaps", Boolean.valueOf(false), new String[0]);
/* 160 */   public static Setting sPacketMoveVehicle = new Setting(serverPackets, Mode.BOOLEAN, "SPacketMoveVehicle", Boolean.valueOf(false), new String[0]);
/* 161 */   public static Setting sPacketMultiBlockChange = new Setting(serverPackets, Mode.BOOLEAN, "SPacketMultiBlockChange", Boolean.valueOf(false), new String[0]);
/* 162 */   public static Setting sPacketOpenWindow = new Setting(serverPackets, Mode.BOOLEAN, "SPacketOpenWindow", Boolean.valueOf(false), new String[0]);
/* 163 */   public static Setting sPacketParticles = new Setting(serverPackets, Mode.BOOLEAN, "SPacketParticles", Boolean.valueOf(false), new String[0]);
/* 164 */   public static Setting sPacketPlayerAbilities = new Setting(serverPackets, Mode.BOOLEAN, "SPacketPlayerAbilities", Boolean.valueOf(false), new String[0]);
/* 165 */   public static Setting sPacketPlayerListHeaderFooter = new Setting(serverPackets, Mode.BOOLEAN, "SPacketPlayerListHeaderFooter", Boolean.valueOf(false), new String[0]);
/* 166 */   public static Setting sPacketPlayerListItem = new Setting(serverPackets, Mode.BOOLEAN, "SPacketPlayerListItem", Boolean.valueOf(false), new String[0]);
/* 167 */   public static Setting sPacketPlayerPosLook = new Setting(serverPackets, Mode.BOOLEAN, "SPacketPlayerPosLook", Boolean.valueOf(false), new String[0]);
/* 168 */   public static Setting sPacketRecipeBook = new Setting(serverPackets, Mode.BOOLEAN, "SPacketRecipeBook", Boolean.valueOf(false), new String[0]);
/* 169 */   public static Setting sPacketRemoveEntityEffect = new Setting(serverPackets, Mode.BOOLEAN, "SPacketRemoveEntityEffect", Boolean.valueOf(false), new String[0]);
/* 170 */   public static Setting sPacketResourcePackSend = new Setting(serverPackets, Mode.BOOLEAN, "SPacketResourcePackSend", Boolean.valueOf(false), new String[0]);
/* 171 */   public static Setting sPacketRespawn = new Setting(serverPackets, Mode.BOOLEAN, "SPacketRespawn", Boolean.valueOf(false), new String[0]);
/* 172 */   public static Setting sPacketScoreboardObjective = new Setting(serverPackets, Mode.BOOLEAN, "SPacketScoreboardObjective", Boolean.valueOf(false), new String[0]);
/* 173 */   public static Setting sPacketSelectAdvancementsTab = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSelectAdvancementsTab", Boolean.valueOf(false), new String[0]);
/* 174 */   public static Setting sPacketServerDifficulty = new Setting(serverPackets, Mode.BOOLEAN, "SPacketServerDifficulty", Boolean.valueOf(false), new String[0]);
/* 175 */   public static Setting sPacketSetExperience = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSetExperience", Boolean.valueOf(false), new String[0]);
/* 176 */   public static Setting sPacketSetPassengers = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSetPassengers", Boolean.valueOf(false), new String[0]);
/* 177 */   public static Setting sPacketSetSlot = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSetSlot", Boolean.valueOf(false), new String[0]);
/* 178 */   public static Setting sPacketSignEditorOpen = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSignEditorOpen", Boolean.valueOf(false), new String[0]);
/* 179 */   public static Setting sPacketSoundEffect = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSoundEffect", Boolean.valueOf(false), new String[0]);
/* 180 */   public static Setting sPacketSpawnExperienceOrb = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnExperienceOrb", Boolean.valueOf(false), new String[0]);
/* 181 */   public static Setting sPacketSpawnGlobalEntity = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnGlobalEntity", Boolean.valueOf(false), new String[0]);
/* 182 */   public static Setting sPacketSpawnMob = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnMob", Boolean.valueOf(false), new String[0]);
/* 183 */   public static Setting sPacketSpawnObject = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnObject", Boolean.valueOf(false), new String[0]);
/* 184 */   public static Setting sPacketSpawnPainting = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnPainting", Boolean.valueOf(false), new String[0]);
/* 185 */   public static Setting sPacketSpawnPlayer = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnPlayer", Boolean.valueOf(false), new String[0]);
/* 186 */   public static Setting sPacketSpawnPosition = new Setting(serverPackets, Mode.BOOLEAN, "SPacketSpawnPosition", Boolean.valueOf(false), new String[0]);
/* 187 */   public static Setting sPacketStatistics = new Setting(serverPackets, Mode.BOOLEAN, "SPacketStatistics", Boolean.valueOf(false), new String[0]);
/* 188 */   public static Setting sPacketTabComplete = new Setting(serverPackets, Mode.BOOLEAN, "SPacketTabComplete", Boolean.valueOf(false), new String[0]);
/* 189 */   public static Setting sPacketTeams = new Setting(serverPackets, Mode.BOOLEAN, "SPacketTeams", Boolean.valueOf(false), new String[0]);
/* 190 */   public static Setting sPacketTimeUpdate = new Setting(serverPackets, Mode.BOOLEAN, "SPacketTimeUpdate", Boolean.valueOf(false), new String[0]);
/* 191 */   public static Setting sPacketTitle = new Setting(serverPackets, Mode.BOOLEAN, "SPacketTitle", Boolean.valueOf(false), new String[0]);
/* 192 */   public static Setting sPacketUnloadChunk = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUnloadChunk", Boolean.valueOf(false), new String[0]);
/* 193 */   public static Setting sPacketUpdateBossInfo = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUpdateBossInfo", Boolean.valueOf(false), new String[0]);
/* 194 */   public static Setting sPacketUpdateHealth = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUpdateHealth", Boolean.valueOf(false), new String[0]);
/* 195 */   public static Setting sPacketUpdateScore = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUpdateScore", Boolean.valueOf(false), new String[0]);
/* 196 */   public static Setting sPacketUpdateTileEntity = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUpdateTileEntity", Boolean.valueOf(false), new String[0]);
/* 197 */   public static Setting sPacketUseBed = new Setting(serverPackets, Mode.BOOLEAN, "SPacketUseBed", Boolean.valueOf(false), new String[0]);
/* 198 */   public static Setting sPacketWindowItems = new Setting(serverPackets, Mode.BOOLEAN, "SPacketWindowItems", Boolean.valueOf(false), new String[0]);
/* 199 */   public static Setting sPacketWindowProperty = new Setting(serverPackets, Mode.BOOLEAN, "SPacketWindowProperty", Boolean.valueOf(false), new String[0]);
/* 200 */   public static Setting sPacketWorldBorder = new Setting(serverPackets, Mode.BOOLEAN, "SPacketWorldBorder", Boolean.valueOf(false), new String[0]);
/* 201 */   public static Setting clientPackets = new Setting(Mode.LABEL, "ClientPackets", Boolean.valueOf(true), new String[] { "Client packets (C)", "Cancelling these will make the client not send them to server" });
/* 202 */   public static Setting cPacketAnimation = new Setting(clientPackets, Mode.BOOLEAN, "CPacketAnimation", Boolean.valueOf(false), new String[0]);
/* 203 */   public static Setting cPacketChatMessage = new Setting(clientPackets, Mode.BOOLEAN, "CPacketChatMessage", Boolean.valueOf(false), new String[0]);
/* 204 */   public static Setting cPacketClickWindow = new Setting(clientPackets, Mode.BOOLEAN, "CPacketClickWindow", Boolean.valueOf(false), new String[0]);
/* 205 */   public static Setting cPacketClientSettings = new Setting(clientPackets, Mode.BOOLEAN, "CPacketClientSettings", Boolean.valueOf(false), new String[0]);
/* 206 */   public static Setting cPacketClientStatus = new Setting(clientPackets, Mode.BOOLEAN, "CPacketClientStatus", Boolean.valueOf(false), new String[0]);
/* 207 */   public static Setting cPacketCloseWindow = new Setting(clientPackets, Mode.BOOLEAN, "CPacketCloseWindow", Boolean.valueOf(false), new String[0]);
/* 208 */   public static Setting cPacketConfirmTeleport = new Setting(clientPackets, Mode.BOOLEAN, "CPacketConfirmTeleport", Boolean.valueOf(false), new String[0]);
/* 209 */   public static Setting cPacketConfirmTransaction = new Setting(clientPackets, Mode.BOOLEAN, "CPacketConfirmTransaction", Boolean.valueOf(false), new String[0]);
/* 210 */   public static Setting cPacketCreativeInventoryAction = new Setting(clientPackets, Mode.BOOLEAN, "CPacketCreativeInventoryAction", Boolean.valueOf(false), new String[0]);
/* 211 */   public static Setting cPacketCustomPayload = new Setting(clientPackets, Mode.BOOLEAN, "CPacketCustomPayload", Boolean.valueOf(false), new String[0]);
/* 212 */   public static Setting cPacketEnchantItem = new Setting(clientPackets, Mode.BOOLEAN, "CPacketEnchantItem", Boolean.valueOf(false), new String[0]);
/* 213 */   public static Setting cPacketEntityAction = new Setting(clientPackets, Mode.BOOLEAN, "CPacketEntityAction", Boolean.valueOf(false), new String[0]);
/* 214 */   public static Setting cPacketHeldItemChange = new Setting(clientPackets, Mode.BOOLEAN, "CPacketHeldItemChange", Boolean.valueOf(false), new String[0]);
/* 215 */   public static Setting cPacketInput = new Setting(clientPackets, Mode.BOOLEAN, "CPacketInput", Boolean.valueOf(false), new String[0]);
/* 216 */   public static Setting cPacketKeepAlive = new Setting(clientPackets, Mode.BOOLEAN, "CPacketKeepAlive", Boolean.valueOf(false), new String[0]);
/* 217 */   public static Setting cPacketPlayer = new Setting(clientPackets, Mode.BOOLEAN, "CPacketPlayer", Boolean.valueOf(false), new String[0]);
/* 218 */   public static Setting cPacketPlayerPosition = new Setting(cPacketPlayer, Mode.BOOLEAN, "CPacketPlayer.Position", Boolean.valueOf(false), new String[0]);
/* 219 */   public static Setting cPacketPlayerPositionRotation = new Setting(cPacketPlayer, Mode.BOOLEAN, "CPacketPlayer.PositionRotation", Boolean.valueOf(false), new String[0]);
/* 220 */   public static Setting cPacketPlayerRotation = new Setting(cPacketPlayer, Mode.BOOLEAN, "CPacketPlayer.Rotation", Boolean.valueOf(false), new String[0]);
/* 221 */   public static Setting cPacketPlayerAbilities = new Setting(clientPackets, Mode.BOOLEAN, "CPacketPlayerAbilities", Boolean.valueOf(false), new String[0]);
/* 222 */   public static Setting cPacketPlayerDigging = new Setting(clientPackets, Mode.BOOLEAN, "CPacketPlayerDigging", Boolean.valueOf(false), new String[0]);
/* 223 */   public static Setting cPacketPlayerTryUseItem = new Setting(clientPackets, Mode.BOOLEAN, "CPacketPlayerTryUseItem", Boolean.valueOf(false), new String[0]);
/* 224 */   public static Setting cPacketPlayerTryUseItemOnBlock = new Setting(clientPackets, Mode.BOOLEAN, "CPacketPlayerTryUseItemOnBlock", Boolean.valueOf(false), new String[0]);
/* 225 */   public static Setting cPacketRecipeInfo = new Setting(clientPackets, Mode.BOOLEAN, "CPacketRecipeInfo", Boolean.valueOf(false), new String[0]);
/* 226 */   public static Setting cPacketResourcePackStatus = new Setting(clientPackets, Mode.BOOLEAN, "CPacketResourcePackStatus", Boolean.valueOf(false), new String[0]);
/* 227 */   public static Setting cPacketSeenAdvancements = new Setting(clientPackets, Mode.BOOLEAN, "CPacketSeenAdvancements", Boolean.valueOf(false), new String[0]);
/* 228 */   public static Setting cPacketSpectate = new Setting(clientPackets, Mode.BOOLEAN, "CPacketSpectate", Boolean.valueOf(false), new String[0]);
/* 229 */   public static Setting cPacketSteerBoat = new Setting(clientPackets, Mode.BOOLEAN, "CPacketSteerBoat", Boolean.valueOf(false), new String[0]);
/* 230 */   public static Setting cPacketTabComplete = new Setting(clientPackets, Mode.BOOLEAN, "CPacketTabComplete", Boolean.valueOf(false), new String[0]);
/* 231 */   public static Setting cPacketUpdateSign = new Setting(clientPackets, Mode.BOOLEAN, "CPacketUpdateSign", Boolean.valueOf(false), new String[0]);
/* 232 */   public static Setting cPacketUseEntity = new Setting(clientPackets, Mode.BOOLEAN, "CPacketUseEntity", Boolean.valueOf(false), new String[0]);
/* 233 */   public static Setting cPacketVehicleMove = new Setting(clientPackets, Mode.BOOLEAN, "CPacketVehicleMove", Boolean.valueOf(false), new String[0]);
/* 234 */   public static Setting logPackets = new Setting(Mode.BOOLEAN, "LogPackets", Boolean.valueOf(false), new String[] { "Logs the received and sent packets", "In console or mc chat" });
/* 235 */   public static Setting logPacketsMode = new Setting(logPackets, "Mode", "Chat", new String[][] { { "Chat", "Sends them in mc-chat", "Only works every x seconds or spam would be too high", "Set the amount below" }, { "Console", "Sends them in console with System.out.println()" } });
/* 236 */   public static Setting chatDelay = new Setting(logPacketsMode, "Chat", Mode.INTEGER, "Delay", Integer.valueOf(250), new String[] { "Timer delay for waiting to send another packet message" });
/*     */   
/*     */   public PacketCanceller() {
/* 239 */     super(Group.MISC, "QueueSkip", new String[] { "Skips 2b2t's queue using a bunch of different methods and a proxy server", "Also allows you to see received and sent packets to the queue server" });
/*     */ 
/*     */     
/* 242 */     this.packetEvent = new Listener(event -> {
/*     */ 
/*     */           
/* 245 */           if (cPacketAnimation.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketAnimation) {
/* 246 */             event.cancel();
/* 247 */           } else if (cPacketChatMessage.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketChatMessage) {
/* 248 */             event.cancel();
/* 249 */           } else if (cPacketClickWindow.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketClickWindow) {
/* 250 */             event.cancel();
/* 251 */           } else if (cPacketClientSettings.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketClientSettings) {
/* 252 */             event.cancel();
/* 253 */           } else if (cPacketClientStatus.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketClientStatus) {
/* 254 */             event.cancel();
/* 255 */           } else if (cPacketCloseWindow.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketCloseWindow) {
/* 256 */             event.cancel();
/* 257 */           } else if (cPacketConfirmTeleport.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport) {
/* 258 */             event.cancel();
/* 259 */           } else if (cPacketConfirmTransaction.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketConfirmTransaction) {
/* 260 */             event.cancel();
/* 261 */           } else if (cPacketCreativeInventoryAction.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketCreativeInventoryAction) {
/* 262 */             event.cancel();
/* 263 */           } else if (cPacketCustomPayload.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketCustomPayload) {
/* 264 */             event.cancel();
/* 265 */           } else if (cPacketEnchantItem.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketEnchantItem) {
/* 266 */             event.cancel();
/* 267 */           } else if (cPacketEntityAction.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketEntityAction) {
/* 268 */             event.cancel();
/* 269 */           } else if (cPacketHeldItemChange.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketHeldItemChange) {
/* 270 */             event.cancel();
/* 271 */           } else if (cPacketInput.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketInput) {
/* 272 */             event.cancel();
/* 273 */           } else if (cPacketKeepAlive.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketKeepAlive) {
/* 274 */             event.cancel();
/* 275 */           } else if (cPacketPlayer.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
/* 276 */             event.cancel();
/* 277 */           } else if (cPacketPlayerPosition.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/* 278 */             event.cancel();
/* 279 */           } else if (cPacketPlayerPositionRotation.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/* 280 */             event.cancel();
/* 281 */           } else if (cPacketPlayerRotation.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/* 282 */             event.cancel();
/* 283 */           } else if (cPacketPlayerAbilities.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayerAbilities) {
/* 284 */             event.cancel();
/* 285 */           } else if (cPacketPlayerDigging.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayerDigging) {
/* 286 */             event.cancel();
/* 287 */           } else if (cPacketPlayerTryUseItem.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem) {
/* 288 */             event.cancel();
/* 289 */           } else if (cPacketPlayerTryUseItemOnBlock.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock) {
/* 290 */             event.cancel();
/* 291 */           } else if (cPacketRecipeInfo.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketRecipeInfo) {
/* 292 */             event.cancel();
/* 293 */           } else if (cPacketResourcePackStatus.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketResourcePackStatus) {
/* 294 */             event.cancel();
/* 295 */           } else if (cPacketSeenAdvancements.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketSeenAdvancements) {
/* 296 */             event.cancel();
/* 297 */           } else if (cPacketSpectate.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketSpectate) {
/* 298 */             event.cancel();
/* 299 */           } else if (cPacketSteerBoat.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketSteerBoat) {
/* 300 */             event.cancel();
/* 301 */           } else if (cPacketTabComplete.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketTabComplete) {
/* 302 */             event.cancel();
/* 303 */           } else if (cPacketUpdateSign.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketUpdateSign) {
/* 304 */             event.cancel();
/* 305 */           } else if (cPacketUseEntity.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketUseEntity) {
/* 306 */             event.cancel();
/* 307 */           } else if (cPacketVehicleMove.booleanValue() && event.packet instanceof net.minecraft.network.play.client.CPacketVehicleMove) {
/* 308 */             event.cancel();
/* 309 */           } else if (sPacketAdvancementInfo.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketAdvancementInfo) {
/* 310 */             event.cancel();
/* 311 */           } else if (sPacketAnimation.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketAnimation) {
/* 312 */             event.cancel();
/* 313 */           } else if (sPacketBlockAction.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketBlockAction) {
/* 314 */             event.cancel();
/* 315 */           } else if (sPacketBlockBreakAnim.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketBlockBreakAnim) {
/* 316 */             event.cancel();
/* 317 */           } else if (sPacketBlockChange.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketBlockChange) {
/* 318 */             event.cancel();
/* 319 */           } else if (sPacketCamera.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCamera) {
/* 320 */             event.cancel();
/* 321 */           } else if (sPacketChangeGameState.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketChangeGameState) {
/* 322 */             event.cancel();
/* 323 */           } else if (sPacketChat.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketChat) {
/* 324 */             event.cancel();
/* 325 */           } else if (sPacketChunkData.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketChunkData) {
/* 326 */             event.cancel();
/* 327 */           } else if (sPacketCloseWindow.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCloseWindow) {
/* 328 */             event.cancel();
/* 329 */           } else if (sPacketCollectItem.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCollectItem) {
/* 330 */             event.cancel();
/* 331 */           } else if (sPacketCombatEvent.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCombatEvent) {
/* 332 */             event.cancel();
/* 333 */           } else if (sPacketConfirmTransaction.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketConfirmTransaction) {
/* 334 */             event.cancel();
/* 335 */           } else if (sPacketCooldown.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCooldown) {
/* 336 */             event.cancel();
/* 337 */           } else if (sPacketCustomPayload.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCustomPayload) {
/* 338 */             event.cancel();
/* 339 */           } else if (sPacketCustomSound.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketCustomSound) {
/* 340 */             event.cancel();
/* 341 */           } else if (sPacketDestroyEntities.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketDestroyEntities) {
/* 342 */             event.cancel();
/* 343 */           } else if (sPacketDisconnect.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketDisconnect) {
/* 344 */             event.cancel();
/* 345 */           } else if (sPacketDisplayObjective.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketDisplayObjective) {
/* 346 */             event.cancel();
/* 347 */           } else if (sPacketEffect.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEffect) {
/* 348 */             event.cancel();
/* 349 */           } else if (sPacketEntity.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntity) {
/* 350 */             event.cancel();
/* 351 */           } else if (s15PacketEntityRelMove.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntity.S15PacketEntityRelMove) {
/* 352 */             event.cancel();
/* 353 */           } else if (s16PacketEntityLook.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntity.S16PacketEntityLook) {
/* 354 */             event.cancel();
/* 355 */           } else if (s17PacketEntityLookMove.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntity.S17PacketEntityLookMove) {
/* 356 */             event.cancel();
/* 357 */           } else if (sPacketEntityAttach.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityAttach) {
/* 358 */             event.cancel();
/* 359 */           } else if (sPacketEntityEffect.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityEffect) {
/* 360 */             event.cancel();
/* 361 */           } else if (sPacketEntityEquipment.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityEquipment) {
/* 362 */             event.cancel();
/* 363 */           } else if (sPacketEntityHeadLook.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityHeadLook) {
/* 364 */             event.cancel();
/* 365 */           } else if (sPacketEntityMetadata.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityMetadata) {
/* 366 */             event.cancel();
/* 367 */           } else if (sPacketEntityProperties.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityProperties) {
/* 368 */             event.cancel();
/* 369 */           } else if (sPacketEntityStatus.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityStatus) {
/* 370 */             event.cancel();
/* 371 */           } else if (sPacketEntityTeleport.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityTeleport) {
/* 372 */             event.cancel();
/* 373 */           } else if (sPacketEntityVelocity.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketEntityVelocity) {
/* 374 */             event.cancel();
/* 375 */           } else if (sPacketExplosion.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketExplosion) {
/* 376 */             event.cancel();
/* 377 */           } else if (sPacketHeldItemChange.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketHeldItemChange) {
/* 378 */             event.cancel();
/* 379 */           } else if (sPacketJoinGame.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketJoinGame) {
/* 380 */             event.cancel();
/* 381 */           } else if (sPacketKeepAlive.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketKeepAlive) {
/* 382 */             event.cancel();
/* 383 */           } else if (sPacketMaps.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketMaps) {
/* 384 */             event.cancel();
/* 385 */           } else if (sPacketMoveVehicle.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketMoveVehicle) {
/* 386 */             event.cancel();
/* 387 */           } else if (sPacketMultiBlockChange.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketMultiBlockChange) {
/* 388 */             event.cancel();
/* 389 */           } else if (sPacketOpenWindow.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketOpenWindow) {
/* 390 */             event.cancel();
/* 391 */           } else if (sPacketParticles.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketParticles) {
/* 392 */             event.cancel();
/* 393 */           } else if (sPacketPlayerAbilities.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketPlayerAbilities) {
/* 394 */             event.cancel();
/* 395 */           } else if (sPacketPlayerListHeaderFooter.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketPlayerListHeaderFooter) {
/* 396 */             event.cancel();
/* 397 */           } else if (sPacketPlayerListItem.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketPlayerListItem) {
/* 398 */             event.cancel();
/* 399 */           } else if (sPacketPlayerPosLook.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 400 */             event.cancel();
/* 401 */           } else if (sPacketRecipeBook.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketRecipeBook) {
/* 402 */             event.cancel();
/* 403 */           } else if (sPacketRemoveEntityEffect.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketRemoveEntityEffect) {
/* 404 */             event.cancel();
/* 405 */           } else if (sPacketResourcePackSend.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketResourcePackSend) {
/* 406 */             event.cancel();
/* 407 */           } else if (sPacketRespawn.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketRespawn) {
/* 408 */             event.cancel();
/* 409 */           } else if (sPacketScoreboardObjective.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketScoreboardObjective) {
/* 410 */             event.cancel();
/* 411 */           } else if (sPacketSelectAdvancementsTab.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSelectAdvancementsTab) {
/* 412 */             event.cancel();
/* 413 */           } else if (sPacketServerDifficulty.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketServerDifficulty) {
/* 414 */             event.cancel();
/* 415 */           } else if (sPacketSetExperience.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSetExperience) {
/* 416 */             event.cancel();
/* 417 */           } else if (sPacketSetPassengers.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSetPassengers) {
/* 418 */             event.cancel();
/* 419 */           } else if (sPacketSetSlot.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSetSlot) {
/* 420 */             event.cancel();
/* 421 */           } else if (sPacketSignEditorOpen.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSignEditorOpen) {
/* 422 */             event.cancel();
/* 423 */           } else if (sPacketSoundEffect.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSoundEffect) {
/* 424 */             event.cancel();
/* 425 */           } else if (sPacketSpawnExperienceOrb.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnExperienceOrb) {
/* 426 */             event.cancel();
/* 427 */           } else if (sPacketSpawnGlobalEntity.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnGlobalEntity) {
/* 428 */             event.cancel();
/* 429 */           } else if (sPacketSpawnMob.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnMob) {
/* 430 */             event.cancel();
/* 431 */           } else if (sPacketSpawnObject.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnObject) {
/* 432 */             event.cancel();
/* 433 */           } else if (sPacketSpawnPainting.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnPainting) {
/* 434 */             event.cancel();
/* 435 */           } else if (sPacketSpawnPlayer.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnPlayer) {
/* 436 */             event.cancel();
/* 437 */           } else if (sPacketSpawnPosition.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketSpawnPosition) {
/* 438 */             event.cancel();
/* 439 */           } else if (sPacketStatistics.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketStatistics) {
/* 440 */             event.cancel();
/* 441 */           } else if (sPacketTabComplete.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketTabComplete) {
/* 442 */             event.cancel();
/* 443 */           } else if (sPacketTeams.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketTeams) {
/* 444 */             event.cancel();
/* 445 */           } else if (sPacketTimeUpdate.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
/* 446 */             event.cancel();
/* 447 */           } else if (sPacketTitle.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketTitle) {
/* 448 */             event.cancel();
/* 449 */           } else if (sPacketUnloadChunk.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUnloadChunk) {
/* 450 */             event.cancel();
/* 451 */           } else if (sPacketUpdateBossInfo.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUpdateBossInfo) {
/* 452 */             event.cancel();
/* 453 */           } else if (sPacketUpdateHealth.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUpdateHealth) {
/* 454 */             event.cancel();
/* 455 */           } else if (sPacketUpdateScore.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUpdateScore) {
/* 456 */             event.cancel();
/* 457 */           } else if (sPacketUpdateTileEntity.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUpdateTileEntity) {
/* 458 */             event.cancel();
/* 459 */           } else if (sPacketUseBed.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketUseBed) {
/* 460 */             event.cancel();
/* 461 */           } else if (sPacketWindowItems.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketWindowItems) {
/* 462 */             event.cancel();
/* 463 */           } else if (sPacketWindowProperty.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketWindowProperty) {
/* 464 */             event.cancel();
/* 465 */           } else if (sPacketWorldBorder.booleanValue() && event.packet instanceof net.minecraft.network.play.server.SPacketWorldBorder) {
/* 466 */             event.cancel();
/*     */           } 
/*     */ 
/*     */           
/* 470 */           if (logPackets.booleanValue()) {
/* 471 */             String packetName = event.packet.getClass().getName();
/* 472 */             if (packetName.contains(".S")) {
/* 473 */               packetName = packetName.substring(packetName.indexOf(".S") + 1);
/* 474 */             } else if (packetName.contains(".C")) {
/* 475 */               packetName = packetName.substring(packetName.indexOf(".C") + 1);
/*     */             } 
/*     */             
/* 478 */             packetName = packetName.replace("$", ".");
/*     */             
/* 480 */             if (event.isCancelled()) {
/* 481 */               packetName = "Cancelled: " + packetName;
/*     */             }
/*     */             
/* 484 */             if (logPacketsMode.stringValue().equals("Chat") && timer.hasPassed(chatDelay.intValue())) {
/* 485 */               sendMessage(packetName, false);
/* 486 */               timer.reset();
/*     */             } else {
/* 488 */               System.out.println(packetName);
/*     */             } 
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<PacketEvent> packetEvent;
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\misc\PacketCanceller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */