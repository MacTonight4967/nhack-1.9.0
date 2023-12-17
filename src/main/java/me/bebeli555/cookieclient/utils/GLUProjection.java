/*     */ package me.bebeli555.cookieclient.utils;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import me.bebeli555.cookieclient.Mod;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public final class GLUProjection
/*     */ {
/*     */   private static GLUProjection instance;
/*     */   private IntBuffer viewport;
/*     */   private FloatBuffer modelview;
/*     */   private FloatBuffer projection;
/*     */   
/*     */   public static class Line {
/*  19 */     public GLUProjection.Vector3D sourcePoint = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*  20 */     public GLUProjection.Vector3D direction = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*     */     
/*     */     public Line(double sx, double sy, double sz, double dx, double dy, double dz) {
/*  23 */       this.sourcePoint.x = sx;
/*  24 */       this.sourcePoint.y = sy;
/*  25 */       this.sourcePoint.z = sz;
/*  26 */       this.direction.x = dx;
/*  27 */       this.direction.y = dy;
/*  28 */       this.direction.z = dz;
/*     */     }
/*     */     
/*     */     public GLUProjection.Vector3D intersect(Line line) {
/*  32 */       double a = this.sourcePoint.x;
/*  33 */       double b = this.direction.x;
/*  34 */       double c = line.sourcePoint.x;
/*  35 */       double d = line.direction.x;
/*  36 */       double e = this.sourcePoint.y;
/*  37 */       double f = this.direction.y;
/*  38 */       double g = line.sourcePoint.y;
/*  39 */       double h = line.direction.y;
/*  40 */       double te = -(a * h - c * h - d * (e - g));
/*  41 */       double be = b * h - d * f;
/*  42 */       if (be == 0.0D) {
/*  43 */         return intersectXZ(line);
/*     */       }
/*  45 */       double t = te / be;
/*  46 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*  47 */       this.sourcePoint.x += this.direction.x * t;
/*  48 */       this.sourcePoint.y += this.direction.y * t;
/*  49 */       this.sourcePoint.z += this.direction.z * t;
/*  50 */       return result;
/*     */     }
/*     */     
/*     */     private GLUProjection.Vector3D intersectXZ(Line line) {
/*  54 */       double a = this.sourcePoint.x;
/*  55 */       double b = this.direction.x;
/*  56 */       double c = line.sourcePoint.x;
/*  57 */       double d = line.direction.x;
/*  58 */       double e = this.sourcePoint.z;
/*  59 */       double f = this.direction.z;
/*  60 */       double g = line.sourcePoint.z;
/*  61 */       double h = line.direction.z;
/*  62 */       double te = -(a * h - c * h - d * (e - g));
/*  63 */       double be = b * h - d * f;
/*  64 */       if (be == 0.0D) {
/*  65 */         return intersectYZ(line);
/*     */       }
/*  67 */       double t = te / be;
/*  68 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*  69 */       this.sourcePoint.x += this.direction.x * t;
/*  70 */       this.sourcePoint.y += this.direction.y * t;
/*  71 */       this.sourcePoint.z += this.direction.z * t;
/*  72 */       return result;
/*     */     }
/*     */     
/*     */     private GLUProjection.Vector3D intersectYZ(Line line) {
/*  76 */       double a = this.sourcePoint.y;
/*  77 */       double b = this.direction.y;
/*  78 */       double c = line.sourcePoint.y;
/*  79 */       double d = line.direction.y;
/*  80 */       double e = this.sourcePoint.z;
/*  81 */       double f = this.direction.z;
/*  82 */       double g = line.sourcePoint.z;
/*  83 */       double h = line.direction.z;
/*  84 */       double te = -(a * h - c * h - d * (e - g));
/*  85 */       double be = b * h - d * f;
/*  86 */       if (be == 0.0D) {
/*  87 */         return null;
/*     */       }
/*  89 */       double t = te / be;
/*  90 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*  91 */       this.sourcePoint.x += this.direction.x * t;
/*  92 */       this.sourcePoint.y += this.direction.y * t;
/*  93 */       this.sourcePoint.z += this.direction.z * t;
/*  94 */       return result;
/*     */     }
/*     */     
/*     */     public GLUProjection.Vector3D intersectPlane(GLUProjection.Vector3D pointOnPlane, GLUProjection.Vector3D planeNormal) {
/*  98 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(this.sourcePoint.x, this.sourcePoint.y, this.sourcePoint.z);
/*  99 */       double d = pointOnPlane.sub(this.sourcePoint).dot(planeNormal) / this.direction.dot(planeNormal);
/* 100 */       result.sadd(this.direction.mul(d));
/* 101 */       if (this.direction.dot(planeNormal) == 0.0D) {
/* 102 */         return null;
/*     */       }
/* 104 */       return result;
/*     */     } }
/*     */   
/*     */   public static class Vector3D { public double x;
/*     */     public double y;
/*     */     public double z;
/*     */     
/*     */     public Vector3D(double x, double y, double z) {
/* 112 */       this.x = x;
/* 113 */       this.y = y;
/* 114 */       this.z = z;
/*     */     }
/*     */ 
/*     */     
/* 118 */     public Vector3D add(Vector3D v) { return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z); }
/*     */ 
/*     */ 
/*     */     
/* 122 */     public Vector3D add(double x, double y, double z) { return new Vector3D(this.x + x, this.y + y, this.z + z); }
/*     */ 
/*     */ 
/*     */     
/* 126 */     public Vector3D sub(Vector3D v) { return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z); }
/*     */ 
/*     */ 
/*     */     
/* 130 */     public Vector3D sub(double x, double y, double z) { return new Vector3D(this.x - x, this.y - y, this.z - z); }
/*     */ 
/*     */     
/*     */     public Vector3D normalized() {
/* 134 */       double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 135 */       return new Vector3D(this.x / len, this.y / len, this.z / len);
/*     */     }
/*     */ 
/*     */     
/* 139 */     public double dot(Vector3D v) { return this.x * v.x + this.y * v.y + this.z * v.z; }
/*     */ 
/*     */ 
/*     */     
/* 143 */     public Vector3D cross(Vector3D v) { return new Vector3D(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x); }
/*     */ 
/*     */ 
/*     */     
/* 147 */     public Vector3D mul(double m) { return new Vector3D(this.x * m, this.y * m, this.z * m); }
/*     */ 
/*     */ 
/*     */     
/* 151 */     public Vector3D div(double d) { return new Vector3D(this.x / d, this.y / d, this.z / d); }
/*     */ 
/*     */ 
/*     */     
/* 155 */     public double length() { return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z); }
/*     */ 
/*     */     
/*     */     public Vector3D sadd(Vector3D v) {
/* 159 */       this.x += v.x;
/* 160 */       this.y += v.y;
/* 161 */       this.z += v.z;
/* 162 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D sadd(double x, double y, double z) {
/* 166 */       this.x += x;
/* 167 */       this.y += y;
/* 168 */       this.z += z;
/* 169 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D ssub(Vector3D v) {
/* 173 */       this.x -= v.x;
/* 174 */       this.y -= v.y;
/* 175 */       this.z -= v.z;
/* 176 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D ssub(double x, double y, double z) {
/* 180 */       this.x -= x;
/* 181 */       this.y -= y;
/* 182 */       this.z -= z;
/* 183 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D snormalize() {
/* 187 */       double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 188 */       this.x /= len;
/* 189 */       this.y /= len;
/* 190 */       this.z /= len;
/* 191 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D scross(Vector3D v) {
/* 195 */       this.x = this.y * v.z - this.z * v.y;
/* 196 */       this.y = this.z * v.x - this.x * v.z;
/* 197 */       this.z = this.x * v.y - this.y * v.x;
/* 198 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D smul(double m) {
/* 202 */       this.x *= m;
/* 203 */       this.y *= m;
/* 204 */       this.z *= m;
/* 205 */       return this;
/*     */     }
/*     */     
/*     */     public Vector3D sdiv(double d) {
/* 209 */       this.x /= d;
/* 210 */       this.y /= d;
/* 211 */       this.z /= d;
/* 212 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     public String toString() { return "(X: " + this.x + " Y: " + this.y + " Z: " + this.z + ")"; } }
/*     */   
/*     */   public static class Projection { private final double x;
/*     */     private final double y;
/*     */     private final Type t;
/*     */     
/* 223 */     public enum Type { INSIDE, OUTSIDE, INVERTED, FAIL; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Projection(double x, double y, Type t) {
/* 231 */       this.x = x;
/* 232 */       this.y = y;
/* 233 */       this.t = t;
/*     */     }
/*     */ 
/*     */     
/* 237 */     public double getX() { return this.x; }
/*     */ 
/*     */ 
/*     */     
/* 241 */     public double getY() { return this.y; }
/*     */ 
/*     */ 
/*     */     
/* 245 */     public Type getType() { return this.t; }
/*     */ 
/*     */ 
/*     */     
/* 249 */     public boolean isType(Type type) { return (this.t == type); } }
/*     */   
/*     */   public enum Type { INSIDE, OUTSIDE, INVERTED, FAIL; }
/*     */   
/* 253 */   public enum ClampMode { ORTHOGONAL, DIRECT, NONE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GLUProjection getInstance() {
/* 261 */     if (instance == null) {
/* 262 */       instance = new GLUProjection();
/*     */     }
/* 264 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   private FloatBuffer coords = BufferUtils.createFloatBuffer(3);
/*     */   
/*     */   private Vector3D frustumPos;
/*     */   
/*     */   private Vector3D[] frustum;
/*     */   
/*     */   private Vector3D[] invFrustum;
/*     */   private Vector3D viewVec;
/*     */   private double displayWidth;
/*     */   private double displayHeight;
/*     */   private double widthScale;
/*     */   private double heightScale;
/*     */   private double bra;
/*     */   private double bla;
/*     */   private double tra;
/*     */   private double tla;
/*     */   private Line tb;
/*     */   private Line bb;
/*     */   private Line lb;
/*     */   private Line rb;
/*     */   private float fovY;
/*     */   private float fovX;
/*     */   private Vector3D lookVec;
/*     */   
/*     */   public void updateMatrices(IntBuffer viewport, FloatBuffer modelview, FloatBuffer projection, double widthScale, double heightScale) {
/* 295 */     this.viewport = viewport;
/* 296 */     this.modelview = modelview;
/* 297 */     this.projection = projection;
/* 298 */     this.widthScale = widthScale;
/* 299 */     this.heightScale = heightScale;
/*     */ 
/*     */     
/* 302 */     float fov = (float)Math.toDegrees(Math.atan(1.0D / this.projection.get(5)) * 2.0D);
/* 303 */     this.fovY = fov;
/* 304 */     this.displayWidth = this.viewport.get(2);
/* 305 */     this.displayHeight = this.viewport.get(3);
/* 306 */     this.fovX = (float)Math.toDegrees(2.0D * Math.atan(this.displayWidth / this.displayHeight * Math.tan(Math.toRadians(this.fovY) / 2.0D)));
/*     */     
/* 308 */     Vector3D lv = new Vector3D(this.modelview.get(0), this.modelview.get(1), this.modelview.get(2));
/* 309 */     Vector3D uv = new Vector3D(this.modelview.get(4), this.modelview.get(5), this.modelview.get(6));
/* 310 */     Vector3D fv = new Vector3D(this.modelview.get(8), this.modelview.get(9), this.modelview.get(10));
/*     */     
/* 312 */     Vector3D nuv = new Vector3D(0.0D, 1.0D, 0.0D);
/* 313 */     Vector3D nlv = new Vector3D(1.0D, 0.0D, 0.0D);
/*     */     
/* 315 */     double yaw = Math.toDegrees(Math.atan2(nlv.cross(lv).length(), nlv.dot(lv))) + 180.0D;
/* 316 */     if (fv.x < 0.0D) {
/* 317 */       yaw = 360.0D - yaw;
/*     */     }
/* 319 */     double pitch = 0.0D;
/* 320 */     if ((-fv.y > 0.0D && yaw >= 90.0D && yaw < 270.0D) || (fv.y > 0.0D && (yaw < 90.0D || yaw >= 270.0D))) {
/* 321 */       pitch = Math.toDegrees(Math.atan2(nuv.cross(uv).length(), nuv.dot(uv)));
/*     */     } else {
/* 323 */       pitch = -Math.toDegrees(Math.atan2(nuv.cross(uv).length(), nuv.dot(uv)));
/*     */     } 
/* 325 */     this.lookVec = getRotationVector(yaw, pitch);
/*     */     
/* 327 */     Matrix4f modelviewMatrix = new Matrix4f();
/* 328 */     modelviewMatrix.load(this.modelview.asReadOnlyBuffer());
/* 329 */     modelviewMatrix.invert();
/*     */     
/* 331 */     this.frustumPos = new Vector3D(modelviewMatrix.m30, modelviewMatrix.m31, modelviewMatrix.m32);
/* 332 */     this.frustum = getFrustum(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z, yaw, pitch, fov, 1.0D, this.displayWidth / this.displayHeight);
/* 333 */     this.invFrustum = getFrustum(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z, yaw - 180.0D, -pitch, fov, 1.0D, this.displayWidth / this.displayHeight);
/*     */     
/* 335 */     this.viewVec = getRotationVector(yaw, pitch).normalized();
/*     */     
/* 337 */     this.bra = Math.toDegrees(Math.acos(this.displayHeight * heightScale / Math.sqrt(this.displayWidth * widthScale * this.displayWidth * widthScale + this.displayHeight * heightScale * this.displayHeight * heightScale)));
/* 338 */     this.bla = 360.0D - this.bra;
/* 339 */     this.tra = this.bla - 180.0D;
/* 340 */     this.tla = this.bra + 180.0D;
/*     */     
/* 342 */     this.rb = new Line(this.displayWidth * this.widthScale, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
/* 343 */     this.tb = new Line(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D);
/* 344 */     this.lb = new Line(0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
/* 345 */     this.bb = new Line(0.0D, this.displayHeight * this.heightScale, 0.0D, 1.0D, 0.0D, 0.0D);
/*     */   }
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
/*     */   public Projection project(double x, double y, double z, ClampMode clampModeOutside, boolean extrudeInverted) {
/* 362 */     if (this.viewport != null && this.modelview != null && this.projection != null) {
/* 363 */       Vector3D posVec = new Vector3D(x, y, z);
/* 364 */       boolean[] frustum = doFrustumCheck(this.frustum, this.frustumPos, x, y, z);
/* 365 */       boolean outsideFrustum = (frustum[0] || frustum[1] || frustum[2] || frustum[3]);
/*     */       
/* 367 */       if (outsideFrustum) {
/*     */         
/* 369 */         boolean opposite = (posVec.sub(this.frustumPos).dot(this.viewVec) <= 0.0D);
/* 370 */         if (opposite) {
/* 371 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 375 */         boolean[] invFrustum = doFrustumCheck(this.invFrustum, this.frustumPos, x, y, z);
/* 376 */         boolean outsideInvertedFrustum = (invFrustum[0] || invFrustum[1] || invFrustum[2] || invFrustum[3]);
/* 377 */         if ((extrudeInverted && !outsideInvertedFrustum) || (outsideInvertedFrustum && clampModeOutside != ClampMode.NONE)) {
/* 378 */           if ((extrudeInverted && !outsideInvertedFrustum) || (clampModeOutside == ClampMode.DIRECT && outsideInvertedFrustum)) {
/*     */ 
/*     */             
/* 381 */             double vecX = 0.0D;
/* 382 */             double vecY = 0.0D;
/* 383 */             if (GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*     */               
/* 385 */               if (opposite) {
/*     */                 
/* 387 */                 vecX = this.displayWidth * this.widthScale - this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
/* 388 */                 vecY = this.displayHeight * this.heightScale - (this.displayHeight - this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
/*     */               } else {
/* 390 */                 vecX = this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
/* 391 */                 vecY = (this.displayHeight - this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
/*     */               } 
/*     */             } else {
/* 394 */               return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */             } 
/*     */             
/* 397 */             Vector3D vec = (new Vector3D(vecX, vecY, 0.0D)).snormalize();
/* 398 */             vecX = vec.x;
/* 399 */             vecY = vec.y;
/*     */             
/* 401 */             Line vectorLine = new Line(this.displayWidth * this.widthScale / 2.0D, this.displayHeight * this.heightScale / 2.0D, 0.0D, vecX, vecY, 0.0D);
/*     */             
/* 403 */             double angle = Math.toDegrees(Math.acos(vec.y / Math.sqrt(vec.x * vec.x + vec.y * vec.y)));
/* 404 */             if (vecX < 0.0D) {
/* 405 */               angle = 360.0D - angle;
/*     */             }
/*     */             
/* 408 */             Vector3D intersect = new Vector3D(0.0D, 0.0D, 0.0D);
/*     */             
/* 410 */             if (angle >= this.bra && angle < this.tra) {
/*     */               
/* 412 */               intersect = this.rb.intersect(vectorLine);
/* 413 */             } else if (angle >= this.tra && angle < this.tla) {
/*     */               
/* 415 */               intersect = this.tb.intersect(vectorLine);
/* 416 */             } else if (angle >= this.tla && angle < this.bla) {
/*     */               
/* 418 */               intersect = this.lb.intersect(vectorLine);
/*     */             } else {
/*     */               
/* 421 */               intersect = this.bb.intersect(vectorLine);
/*     */             } 
/* 423 */             return new Projection(intersect.x, intersect.y, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/* 424 */           }  if (clampModeOutside == ClampMode.ORTHOGONAL && outsideInvertedFrustum) {
/* 425 */             if (GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*     */               
/* 427 */               double guiX = this.coords.get(0) * this.widthScale;
/* 428 */               double guiY = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/* 429 */               if (opposite) {
/*     */                 
/* 431 */                 guiX = this.displayWidth * this.widthScale - guiX;
/* 432 */                 guiY = this.displayHeight * this.heightScale - guiY;
/*     */               } 
/* 434 */               if (guiX < 0.0D) {
/* 435 */                 guiX = 0.0D;
/* 436 */               } else if (guiX > this.displayWidth * this.widthScale) {
/* 437 */                 guiX = this.displayWidth * this.widthScale;
/*     */               } 
/* 439 */               if (guiY < 0.0D) {
/* 440 */                 guiY = 0.0D;
/* 441 */               } else if (guiY > this.displayHeight * this.heightScale) {
/* 442 */                 guiY = this.displayHeight * this.heightScale;
/*     */               } 
/* 444 */               return new Projection(guiX, guiY, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */             } 
/* 446 */             return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 451 */           if (GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*     */             
/* 453 */             double guiX = this.coords.get(0) * this.widthScale;
/* 454 */             double guiY = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/* 455 */             if (opposite) {
/*     */               
/* 457 */               guiX = this.displayWidth * this.widthScale - guiX;
/* 458 */               guiY = this.displayHeight * this.heightScale - guiY;
/*     */             } 
/* 460 */             return new Projection(guiX, guiY, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */           } 
/* 462 */           return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 467 */         if (GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*     */           
/* 469 */           double guiX = this.coords.get(0) * this.widthScale;
/* 470 */           double guiY = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/* 471 */           return new Projection(guiX, guiY, Projection.Type.INSIDE);
/*     */         } 
/* 473 */         return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */       } 
/*     */     } 
/*     */     
/* 477 */     return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */   }
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
/*     */   public boolean[] doFrustumCheck(Vector3D[] frustumCorners, Vector3D frustumPos, double x, double y, double z) {
/* 491 */     Vector3D point = new Vector3D(x, y, z);
/* 492 */     boolean c1 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[3], frustumCorners[0] }, point);
/* 493 */     boolean c2 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[0], frustumCorners[1] }, point);
/* 494 */     boolean c3 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[1], frustumCorners[2] }, point);
/* 495 */     boolean c4 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[2], frustumCorners[3] }, point);
/* 496 */     return new boolean[] { c1, c2, c3, c4 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean crossPlane(Vector3D[] plane, Vector3D point) {
/* 507 */     Vector3D z = new Vector3D(0.0D, 0.0D, 0.0D);
/* 508 */     Vector3D e0 = plane[1].sub(plane[0]);
/* 509 */     Vector3D e1 = plane[2].sub(plane[0]);
/* 510 */     Vector3D normal = e0.cross(e1).snormalize();
/* 511 */     double D = z.sub(normal).dot(plane[2]);
/* 512 */     double dist = normal.dot(point) + D;
/* 513 */     return (dist >= 0.0D);
/*     */   }
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
/*     */   public Vector3D[] getFrustum(double x, double y, double z, double rotationYaw, double rotationPitch, double fov, double farDistance, double aspectRatio) {
/* 534 */     double hFar = 2.0D * Math.tan(Math.toRadians(fov / 2.0D)) * farDistance;
/* 535 */     double wFar = hFar * aspectRatio;
/* 536 */     Vector3D view = getRotationVector(rotationYaw, rotationPitch).snormalize();
/* 537 */     Vector3D up = getRotationVector(rotationYaw, rotationPitch - 90.0D).snormalize();
/* 538 */     Vector3D right = getRotationVector(rotationYaw + 90.0D, 0.0D).snormalize();
/* 539 */     Vector3D camPos = new Vector3D(x, y, z);
/* 540 */     Vector3D view_camPos_product = view.add(camPos);
/* 541 */     Vector3D fc = new Vector3D(view_camPos_product.x * farDistance, view_camPos_product.y * farDistance, view_camPos_product.z * farDistance);
/* 542 */     Vector3D topLeftfrustum = new Vector3D(fc.x + up.x * hFar / 2.0D - right.x * wFar / 2.0D, fc.y + up.y * hFar / 2.0D - right.y * wFar / 2.0D, fc.z + up.z * hFar / 2.0D - right.z * wFar / 2.0D);
/* 543 */     Vector3D downLeftfrustum = new Vector3D(fc.x - up.x * hFar / 2.0D - right.x * wFar / 2.0D, fc.y - up.y * hFar / 2.0D - right.y * wFar / 2.0D, fc.z - up.z * hFar / 2.0D - right.z * wFar / 2.0D);
/* 544 */     Vector3D topRightfrustum = new Vector3D(fc.x + up.x * hFar / 2.0D + right.x * wFar / 2.0D, fc.y + up.y * hFar / 2.0D + right.y * wFar / 2.0D, fc.z + up.z * hFar / 2.0D + right.z * wFar / 2.0D);
/* 545 */     Vector3D downRightfrustum = new Vector3D(fc.x - up.x * hFar / 2.0D + right.x * wFar / 2.0D, fc.y - up.y * hFar / 2.0D + right.y * wFar / 2.0D, fc.z - up.z * hFar / 2.0D + right.z * wFar / 2.0D);
/* 546 */     return new Vector3D[] { topLeftfrustum, downLeftfrustum, downRightfrustum, topRightfrustum };
/*     */   }
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
/* 559 */   public Vector3D[] getFrustum() { return this.frustum; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 568 */   public float getFovX() { return this.fovX; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 577 */   public float getFovY() { return this.fovY; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 586 */   public Vector3D getLookVector() { return this.lookVec; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getRotationVector(double rotYaw, double rotPitch) {
/* 597 */     double c = Math.cos(-rotYaw * 0.01745329238474369D - Math.PI);
/* 598 */     double s = Math.sin(-rotYaw * 0.01745329238474369D - Math.PI);
/* 599 */     double nc = -Math.cos(-rotPitch * 0.01745329238474369D);
/* 600 */     double ns = Math.sin(-rotPitch * 0.01745329238474369D);
/* 601 */     return new Vector3D(s * nc, ns, c * nc);
/*     */   }
/*     */   
/*     */   public static float[] convertBounds(Vec3d pos, float partialTicks, int width, int height) {
/* 605 */     float x = -1.0F;
/* 606 */     float y = -1.0F;
/* 607 */     float w = (width + 1);
/* 608 */     float h = (height + 1);
/*     */     
/* 610 */     Projection projection = getInstance().project(pos.field_72450_a - (Mod.mc.func_175598_ae()).field_78730_l, pos.field_72448_b - (Mod.mc.func_175598_ae()).field_78731_m, pos.field_72449_c - (Mod.mc.func_175598_ae()).field_78728_n, ClampMode.NONE, true);
/* 611 */     if (projection == null) {
/* 612 */       return null;
/*     */     }
/*     */     
/* 615 */     x = Math.max(x, (float)projection.getX());
/* 616 */     y = Math.max(y, (float)projection.getY());
/*     */     
/* 618 */     w = Math.min(w, (float)projection.getX());
/* 619 */     h = Math.min(h, (float)projection.getY());
/*     */     
/* 621 */     if (x != -1.0F && y != -1.0F && w != (width + 1) && h != (height + 1)) {
/* 622 */       return new float[] { x, y, w, h };
/*     */     }
/*     */     
/* 625 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclien\\utils\GLUProjection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */