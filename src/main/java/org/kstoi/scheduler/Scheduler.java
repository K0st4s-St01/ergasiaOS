package org.kstoi.scheduler;

public abstract class Scheduler {
    public abstract void insert(org.kstoi.entities.Process process);
    public abstract void schedule();
}