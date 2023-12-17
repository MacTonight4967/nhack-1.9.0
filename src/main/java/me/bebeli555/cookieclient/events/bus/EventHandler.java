package me.bebeli555.cookieclient.events.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EventHandler {}


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\EventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */