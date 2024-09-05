package org.kstoi.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.kstoi.entities.Process;
import org.kstoi.entities.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundRobinWithVaryingTimeQuantum extends Scheduler{
    @Setter
    private Long quantum=10L;
    private List<ProcessWrapper> processes=new ArrayList<>();
    @Override
    public void insert(Process process) {
        processes.add(new ProcessWrapper(process));
        System.out.println(processes);
    }

    @Override
    public void schedule() {
        if(!processes.isEmpty()) {

            Process p = processes.get(0).getProcess();

            if(p.isExited()){
                processes.remove(0);
            }

            if (p != null) {
                if (p.getState() == State.SUSPENDED) {
                    long age = (System.currentTimeMillis() - processes.get(0).getInitTime()) / 1000;//in seconds
                    processes.get(0).setAssignedQuantum(((age / 100)+1) * quantum);//100 seconds age
                    p.setState(State.TORUN);
                }
                if (p.getState() == State.TORUN) {
                    if (p.isSleep()) {
                        p.wake();
                    } else {
                        p.start();
                    }
                }
                if (p.getState() == State.RUNNING) {
                    if (p.getCounter() >= processes.get(0).getAssignedQuantum()) {
                        if (p.isExited()) {
                            processes.remove(0);
                        } else {
                            p.sleepProcess();
                            p.setCounterLimit(p.getCounterLimit() - p.getCounter());
                            p.setCounter(0);
                            processes.remove(0);
                            processes.add(new ProcessWrapper(p));
                        }
                    }
                }
            }
        }
    }
    @Setter
    @Getter
    @ToString
    private class ProcessWrapper{
        private Process process;
        private Long assignedQuantum=0L;
        private long initTime;

        public ProcessWrapper(Process process) {
           initTime=System.currentTimeMillis();
           this.process=process;
        }

    }
}
