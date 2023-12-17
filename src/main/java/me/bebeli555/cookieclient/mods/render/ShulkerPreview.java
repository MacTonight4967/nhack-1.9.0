/*     */ package me.bebeli555.cookieclient.mods.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import me.bebeli555.cookieclient.events.bus.EventHandler;
/*     */ import me.bebeli555.cookieclient.events.bus.Listener;
/*     */ import me.bebeli555.cookieclient.events.other.PacketEvent;
/*     */ import me.bebeli555.cookieclient.events.render.RenderTooltipEvent;
/*     */ import me.bebeli555.cookieclient.gui.Group;
/*     */ import me.bebeli555.cookieclient.gui.Mode;
/*     */ import me.bebeli555.cookieclient.gui.Setting;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class ShulkerPreview
/*     */   extends Mod
/*     */ {
/*  35 */   public static Setting enderChest = new Setting(Mode.BOOLEAN, "EnderChest", Boolean.valueOf(true), new String[] { "Allows you to see ur enderchest content", "(You need to open it once before)" });
/*     */   
/*  37 */   private ArrayList<ItemStack> enderChestItems = new ArrayList();
/*  38 */   private HashMap<String, List<ItemStack>> savedShulkerItems = new HashMap();
/*  39 */   private int enderChestWindowId = -1;
/*  40 */   private int shulkerWindowId = -1;
/*  41 */   private Timer timer = new Timer();
/*  42 */   private String lastWindowTitle = "";
/*     */   
/*     */   public ShulkerPreview() {
/*  45 */     super(Group.RENDER, "ShulkerPreview", new String[] { "See shulker and enderchest content", "By hovering ur mouse over them" });
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
/*  87 */     this.packetEvent = new Listener(event -> {
/*     */           
/*  89 */           if (event.packet instanceof SPacketWindowItems) {
/*  90 */             SPacketWindowItems packet = (SPacketWindowItems)event.packet;
/*     */             
/*  92 */             if (packet.func_148911_c() == this.enderChestWindowId) {
/*  93 */               this.enderChestItems.clear();
/*     */               
/*  95 */               for (int i = 0; i < packet.func_148910_d().size(); i++) {
/*  96 */                 ItemStack itemStack = (ItemStack)packet.func_148910_d().get(i);
/*  97 */                 if (itemStack != null)
/*     */                 
/*     */                 { 
/*     */                   
/* 101 */                   if (i > 26) {
/*     */                     break;
/*     */                   }
/*     */                   
/* 105 */                   this.enderChestItems.add(itemStack); } 
/*     */               } 
/* 107 */             } else if (packet.func_148911_c() == this.shulkerWindowId) {
/* 108 */               if (this.savedShulkerItems.containsKey(this.lastWindowTitle)) {
/* 109 */                 this.savedShulkerItems.remove(this.lastWindowTitle);
/*     */               }
/*     */               
/* 112 */               ArrayList<ItemStack> list = new ArrayList<ItemStack>();
/*     */               
/* 114 */               for (int i = 0; i < packet.func_148910_d().size(); i++) {
/* 115 */                 ItemStack itemStack = (ItemStack)packet.func_148910_d().get(i);
/* 116 */                 if (itemStack != null) {
/*     */ 
/*     */ 
/*     */                   
/* 120 */                   if (i > 26) {
/*     */                     break;
/*     */                   }
/*     */                   
/* 124 */                   list.add(itemStack);
/*     */                 } 
/*     */               } 
/* 127 */               this.savedShulkerItems.put(this.lastWindowTitle, list);
/*     */             } 
/* 129 */           } else if (event.packet instanceof SPacketOpenWindow) {
/* 130 */             SPacketOpenWindow packet = (SPacketOpenWindow)event.packet;
/*     */             
/* 132 */             if (packet.func_179840_c().func_150254_d().startsWith("Ender")) {
/* 133 */               this.enderChestWindowId = packet.func_148901_c();
/*     */             } else {
/* 135 */               this.shulkerWindowId = packet.func_148901_c();
/* 136 */               this.lastWindowTitle = packet.func_179840_c().func_150260_c();
/*     */             } 
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */     
/* 141 */     this.renderTooltip = new Listener(event -> {
/*     */           
/* 143 */           if (event.itemStack == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 148 */           if (enderChest.booleanValue() && Item.func_150891_b(event.itemStack.func_77973_b()) == 130) {
/* 149 */             int x = event.x;
/* 150 */             int y = event.y;
/*     */             
/* 152 */             GlStateManager.func_179109_b((x + 10), (y - 5), 0.0F);
/* 153 */             GlStateManager.func_179140_f();
/* 154 */             GlStateManager.func_179097_i();
/*     */             
/* 156 */             GuiScreen.func_73734_a(-3, -mc.field_71466_p.field_78288_b - 4, 147, 51, -1727000560);
/* 157 */             GuiScreen.func_73734_a(-2, -mc.field_71466_p.field_78288_b - 3, 146, 50, -14671840);
/* 158 */             GuiScreen.func_73734_a(0, 0, 144, 48, -15724528);
/*     */             
/* 160 */             mc.field_71466_p.func_175063_a("EnderChest Preview", 0.0F, (-mc.field_71466_p.field_78288_b - 1), -1);
/*     */             
/* 162 */             GlStateManager.func_179126_j();
/* 163 */             (mc.func_175599_af()).field_77023_b = 150.0F;
/* 164 */             RenderHelper.func_74520_c();
/*     */             
/* 166 */             for (int i = 0; i < this.enderChestItems.size(); i++) {
/* 167 */               ItemStack itemStack = (ItemStack)this.enderChestItems.get(i);
/* 168 */               if (itemStack != null) {
/*     */ 
/*     */ 
/*     */                 
/* 172 */                 int offsetX = i % 9 * 16;
/* 173 */                 int offsetY = i / 9 * 16;
/* 174 */                 mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
/* 175 */                 mc.func_175599_af().func_180453_a(mc.field_71466_p, itemStack, offsetX, offsetY, null);
/*     */               } 
/*     */             } 
/* 178 */             event.cancel();
/*     */             
/* 180 */             RenderHelper.func_74518_a();
/* 181 */             (mc.func_175599_af()).field_77023_b = 0.0F;
/* 182 */             GlStateManager.func_179145_e();
/* 183 */             GlStateManager.func_179109_b(-(x + 10), -(y - 5), 0.0F);
/*     */ 
/*     */           
/*     */           }
/* 187 */           else if (event.itemStack.func_77973_b() instanceof net.minecraft.item.ItemShulkerBox) {
/* 188 */             ItemStack shulker = event.itemStack;
/* 189 */             NBTTagCompound tagCompound = shulker.func_77978_p();
/*     */             
/* 191 */             if (tagCompound != null && tagCompound.func_150297_b("BlockEntityTag", 10)) {
/* 192 */               NBTTagCompound blockEntityTag = tagCompound.func_74775_l("BlockEntityTag");
/*     */               
/* 194 */               if (blockEntityTag.func_150297_b("Items", 9)) {
/* 195 */                 event.cancel();
/*     */                 
/* 197 */                 NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/* 198 */                 ItemStackHelper.func_191283_b(blockEntityTag, nonnulllist);
/*     */                 
/* 200 */                 int x = event.x;
/* 201 */                 int y = event.y;
/*     */                 
/* 203 */                 GlStateManager.func_179109_b((x + 10), (y - 5), 0.0F);
/* 204 */                 GlStateManager.func_179140_f();
/* 205 */                 GlStateManager.func_179097_i();
/*     */                 
/* 207 */                 GuiScreen.func_73734_a(-3, -mc.field_71466_p.field_78288_b - 4, 147, 51, -1727000560);
/* 208 */                 GuiScreen.func_73734_a(-2, -mc.field_71466_p.field_78288_b - 3, 146, 50, -14671840);
/* 209 */                 GuiScreen.func_73734_a(0, 0, 144, 48, -15724528);
/*     */                 
/* 211 */                 mc.field_71466_p.func_175063_a(shulker.func_82833_r(), 0.0F, (-mc.field_71466_p.field_78288_b - 1), -1);
/*     */                 
/* 213 */                 GlStateManager.func_179126_j();
/* 214 */                 (mc.func_175599_af()).field_77023_b = 150.0F;
/* 215 */                 RenderHelper.func_74520_c();
/*     */                 
/* 217 */                 for (int i = 0; i < nonnulllist.size(); i++) {
/* 218 */                   ItemStack itemStack = (ItemStack)nonnulllist.get(i);
/* 219 */                   int offsetX = i % 9 * 16;
/* 220 */                   int offsetY = i / 9 * 16;
/* 221 */                   mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
/* 222 */                   mc.func_175599_af().func_180453_a(mc.field_71466_p, itemStack, offsetX, offsetY, null);
/*     */                 } 
/*     */                 
/* 225 */                 RenderHelper.func_74518_a();
/* 226 */                 (mc.func_175599_af()).field_77023_b = 0.0F;
/* 227 */                 GlStateManager.func_179145_e();
/* 228 */                 GlStateManager.func_179109_b(-(x + 10), -(y - 5), 0.0F);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }new java.util.function.Predicate[0]);
/*     */   }
/*     */   
/*     */   public NBTTagCompound getShulkerNBT(ItemStack stack) {
/* 236 */     NBTTagCompound compound = stack.func_77978_p();
/* 237 */     if (compound != null && compound.func_150297_b("BlockEntityTag", 10)) {
/* 238 */       NBTTagCompound tags = compound.func_74775_l("BlockEntityTag");
/* 239 */       if (tags.func_150297_b("Items", 9)) {
/* 240 */         return tags;
/*     */       }
/*     */     } 
/*     */     
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private Listener<PacketEvent> packetEvent;
/*     */   @EventHandler
/*     */   private Listener<RenderTooltipEvent> renderTooltip;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
/*     */     if (event.getEntity() == null || !(event.getEntity() instanceof EntityItem))
/*     */       return; 
/*     */     this.timer.schedule(new TimerTask() {
/*     */           public void run() {
/*     */             EntityItem item = (EntityItem)event.getEntity();
/*     */             if (!(item.func_92059_d().func_77973_b() instanceof net.minecraft.item.ItemShulkerBox))
/*     */               return; 
/*     */             ItemStack shulker = item.func_92059_d();
/*     */             NBTTagCompound shulkerNBT = ShulkerPreview.this.getShulkerNBT(shulker);
/*     */             if (shulkerNBT != null) {
/*     */               TileEntityShulkerBox fakeShulker = new TileEntityShulkerBox();
/*     */               fakeShulker.func_190586_e(shulkerNBT);
/*     */               String customName = shulker.func_82833_r();
/*     */               ArrayList<ItemStack> items = new ArrayList<ItemStack>();
/*     */               for (int i = 0; i < 27; i++)
/*     */                 items.add(fakeShulker.func_70301_a(i)); 
/*     */               if (ShulkerPreview.access$000(ShulkerPreview.this).containsKey(customName))
/*     */                 ShulkerPreview.access$000(ShulkerPreview.this).remove(customName); 
/*     */               ShulkerPreview.access$000(ShulkerPreview.this).put(customName, items);
/*     */             } 
/*     */           }
/*     */         }5000L);
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\mods\render\ShulkerPreview.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */