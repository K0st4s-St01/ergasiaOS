package org.kstoi.scheduler;

import org.kstoi.entities.Process;
import org.kstoi.entities.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestRemainingJobFirstScheduler extends Scheduler{
    private List<Process> processes=new ArrayList<>();

    @Override
    public void insert(Process process) {
        processes.add(process);
        Collections.sort(processes, (p1, p2) -> {
            if(p1.getCounterLimit()-p1.getCounter() == p2.getCounterLimit()-p2.getCounter()) {
                return 0;
            }
            return p1.getCounterLimit()- p1.getCounter()<p2.getCounterLimit()-p2.getCounter()?-1:1;
        });
        if(processes.get(0).getState() != State.RUNNING){
            for (Process p : processes) {
                if (p.getState() == State.RUNNING && processes.get(0) != p) {
                    System.out.println(processes);
                    p.sleepProcess();
                }
            }
        }
        System.out.println(processes);
    }

    @Override
    public void schedule() {
        if (!processes.isEmpty()) {
            if (processes.get(0).getCounterLimit()-processes.get(0).getCounter() <= 0) {
                processes.remove(0);
            }
            if (processes.get(0).isExited()) {
                processes.remove(0);
            }
            if (processes.get(0).isSleep()) {
                processes.get(0).wake();
            }
            if (!processes.get(0).isSleep() && !processes.get(0).isExited())
                processes.get(0).start();
        }
    }

    }

