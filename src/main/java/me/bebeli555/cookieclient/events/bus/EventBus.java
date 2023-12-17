package me.bebeli555.cookieclient.events.bus;

public interface EventBus {
  void subscribe(Object paramObject);
  
  void subscribeAll(Object... paramVarArgs);
  
  void subscribeAll(Iterable<Object> paramIterable);
  
  void unsubscribe(Object paramObject);
  
  void unsubscribeAll(Object... paramVarArgs);
  
  void unsubscribeAll(Iterable<Object> paramIterable);
  
  void post(Object paramObject);
  
  void attach(EventBus paramEventBus);
  
  void detach(EventBus paramEventBus);
}


/* Location:              C:\Users\deadc\Downloads\nhack-1.0.2-dev-release.jar!\me\bebeli555\cookieclient\events\bus\EventBus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.0
 */