package org.kstoi.scheduler;

import org.kstoi.entities.Process;
import org.kstoi.entities.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShortestJobFirstScheduler extends Scheduler{
    private Process current=null;
    private List<Process> processes=new ArrayList<>();

    @Override
    public void insert(Process process) {
        if(current == null){
            current=process;
        }else{
            processes.add(process);
            Collections.sort(processes, (p1, p2) -> {
                if(p1.getCounterLimit() == p2.getCounterLimit()) {
                    return 0;
                }
                return p1.getCounterLimit()<p2.getCounterLimit()?-1:1;
            });
        }
        System.out.println(processes);
    }

    @Override
    public void schedule() {
        if(current.isExited()){
            current = null;
        }
        if(current!=null && current.getState() == State.TORUN){
            current.start();
        }
        if(current == null){
            if(!processes.isEmpty()) {
                current = processes.get(0);
                processes.remove(0);
            }
        }
        if(current!=null && current.getState()== State.SUSPENDED && !current.isExited()){
            current.changeStateTORUN();
        }
    }
}
