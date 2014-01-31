package de.macbury.rukh.core.parell;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import de.macbury.rukh.core.utils.MethodInvoker;

public abstract class Job<T> extends Thread {
  private Class<T>          type;
  private Reference<Object> whandler;
  private String            callback;
  private JobListener       listener;
  
  public Job(Class<T> type) {
    this.type = type;
  }
  
  public abstract T perform();
  
  public void setCallback(Object handler, String callback) {
    this.callback = callback;
    this.whandler = new WeakReference<Object>(handler);
  }
  
  @Override
  public void run() {
    synchronized (listener) { listener.onJobStart(this); }

    try {
      T result = this.perform();
      
      if (whandler != null && callback != null && whandler.get() != null) {
        synchronized (whandler.get()) {
          Class<?>[] performSig = { type, getClass() }; 
          Class<?>[] fallbackSig = { Object.class, getClass() }; 
          MethodInvoker.invokeHandler(whandler.get(), callback, true, true, performSig, fallbackSig, result, this);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      synchronized (listener) { listener.onJobError(this, e); }
    }

    synchronized (listener) { listener.onJobFinish(this); }
  }
  
  public JobListener getListener() {
    return listener;
  }

  public void setListener(JobListener listener) {
    this.listener = listener;
  }
  
  public interface JobListener {
    public void onJobStart(Job job);
    public void onJobError(Job job, Exception e);
    public void onJobFinish(Job job);
  }
}
