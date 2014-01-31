package de.macbury.rukh.core.parell;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.Gdx;

import de.macbury.rukh.core.parell.Job.JobListener;

public class ParellJobsManager extends Thread implements JobListener {
  private static final String TAG = "ParellJobsManager";
  private Stack<Job> pendingJobs;
  private ArrayList<Job> runningJobs;
  private int maxRunningJobs = 10;
  private boolean running;
  
  public ParellJobsManager(int maxRunningJobs) {
    this.maxRunningJobs = maxRunningJobs;
    this.pendingJobs    = new Stack<Job>();
    this.runningJobs    = new ArrayList<Job>();
    this.start();
  }

  public void enqueue(Job job) {
    Gdx.app.log(TAG, "Adding job");
    synchronized (pendingJobs) {
      pendingJobs.push(job);
    }
  }
  
  @Override
  public void run() {
    this.running = true;
    while(running) {
      try {
        if (processJobs()) {
          Thread.sleep(50);
        } else {
          Thread.sleep(250);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean processJobs() {
    boolean pushedJobs = false;
    while(this.runningJobs.size() < maxRunningJobs && !pendingJobs.isEmpty()) {
      pushedJobs = true;
      
      Job job = pendingJobs.pop();
      job.setListener(this);
      runningJobs.add(job);
      job.start();
    }
    
    if (pushedJobs) {
      Gdx.app.log(TAG, "Running jobs: " + this.runningJobs.size());
    }
    
    return pushedJobs;
  }

  public void onJobStart(Job job) {
    Gdx.app.log(TAG, "Starting job: " + job.getClass().getSimpleName() );
  }

  public void onJobError(Job job, Exception e) {
    Gdx.app.log(TAG, "Job failed: " + job.getClass().getSimpleName() );
  }

  public void onJobFinish(Job job) {
    this.runningJobs.remove(job);
    Gdx.app.log(TAG, "Finished job: " + job.getClass().getSimpleName() );
    job = null;
  }
}
