/*    */ package club.minnced.discord.rpc;
/*    */ 
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscordUser
/*    */   extends Structure
/*    */ {
/* 40 */   private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList(new String[] { "userId", "username", "discriminator", "avatar" }));
/*    */   
/*    */   public String userId;
/*    */   
/*    */   public String username;
/*    */   
/*    */   public String discriminator;
/*    */   public String avatar;
/*    */   
/* 49 */   public DiscordUser(String encoding) { setStringEncoding(encoding); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public DiscordUser() { this("UTF-8"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 79 */     if (this == o)
/* 80 */       return true; 
/* 81 */     if (!(o instanceof DiscordUser))
/* 82 */       return false; 
/* 83 */     DiscordUser that = (DiscordUser)o;
/* 84 */     return (Objects.equals(this.userId, that.userId) && 
/* 85 */       Objects.equals(this.username, that.username) && 
/* 86 */       Objects.equals(this.discriminator, that.discriminator) && 
/* 87 */       Objects.equals(this.avatar, that.avatar));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 93 */   public int hashCode() { return Objects.hash(new Object[] { this.userId, this.username, this.discriminator, this.avatar }); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 99 */   protected List<String> getFieldOrder() { return FIELD_ORDER; }
/*    */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\club\minnced\discord\rpc\DiscordUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */