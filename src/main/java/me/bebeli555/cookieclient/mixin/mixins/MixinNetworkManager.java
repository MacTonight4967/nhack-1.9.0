/*    */ package me.bebeli555.cookieclient.mixin.mixins;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import me.bebeli555.cookieclient.Mod;
/*    */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*    */ import me.bebeli555.cookieclient.events.other.PacketPostEvent;
/*    */ import me.bebeli555.cookieclient.events.other.PacketServerEvent;
/*    */ import me.bebeli555.cookieclient.gui.Settings;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.network.NetworkManager.class})
/*    */ public class MixinNetworkManager
/*    */ {
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onPacketSend(Packet<?> packet, CallbackInfo callbackInfo) {
/* 23 */     PacketEvent event = new PacketEvent(packet);
/* 24 */     Mod.EVENT_BUS.post(event);
/*    */     
/* 26 */     if (event.isCancelled()) {
/* 27 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
/* 33 */     PacketEvent event = new PacketEvent(packet);
/* 34 */     Mod.EVENT_BUS.post(event);
/* 35 */     Mod.EVENT_BUS.post(new PacketServerEvent(packet));
/*    */     
/* 37 */     if (event.isCancelled()) {
/* 38 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("RETURN")})
/*    */   private void onPostSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
/* 44 */     PacketPostEvent event = new PacketPostEvent(packet);
/* 45 */     Mod.EVENT_BUS.post(event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"channelRead0"}, at = {@At("RETURN")})
/*    */   private void onPostChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
/* 50 */     PacketPostEvent event = new PacketPostEvent(packet);
/* 51 */     Mod.EVENT_BUS.post(event);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"closeChannel"}, at = {@At("RETURN")})
/* 58 */   private void closeChannel(ITextComponent message, CallbackInfo callbackInfo) { Settings.saveSettings(); }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mixin\mixins\MixinNetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */