package me.bebeli555.cookieclient.events.bus;

@FunctionalInterface
public interface EventHook<T> {
  void invoke(T paramT);
}


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\EventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */