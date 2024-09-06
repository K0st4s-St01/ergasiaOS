package org.kstoi.scheduler2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kstoi.entities.Process;
import org.kstoi.scheduler.Scheduler;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class MultipleQueues extends Scheduler {
    @Setter
    public Long quantum=20L;

    private Queue<ProcessWrapper> queuePriority_1=new ArrayDeque<>();
    private Queue<ProcessWrapper> queuePriority_2=new PriorityQueue<>();
    private Queue<ProcessWrapper> queuePriority_3=new PriorityQueue<>();
    private Queue<ProcessWrapper> queuePriority_4=new PriorityQueue<>();

    private ProcessWrapper currentProcess=null;

    @Override
    public void insert(Process process) {
        System.out.println(queuePriority_1);
        System.out.println(queuePriority_2);
        System.out.println(queuePriority_3);
        System.out.println(queuePriority_4);
        switch (process.getPriority()){
            case 1:
                queuePriority_1.add(new ProcessWrapper(process));
                break;
            case 2:
                queuePriority_2.add(new ProcessWrapper(process));
                break;
            case 3:
                queuePriority_3.add(new ProcessWrapper(process));
                break;
            case 4:
                queuePriority_4.add(new ProcessWrapper(process));
                break;
        }
    }

    @Override
    public void schedule() {
        if(currentProcess!=null && (currentProcess.getProcess().isExited() || currentProcess.getProcess().getCounterLimit()-currentProcess.getProcess().getCounter()==0)){
            currentProcess=null;
        }
        System.out.println("current -> "+currentProcess);
        if(!queuePriority_1.isEmpty()){
            if(currentProcess==null){
                currentProcess=queuePriority_1.poll();
                Long age = (System.currentTimeMillis()-currentProcess.getTimeStarted())/1000;//in seconds
                currentProcess.setAssignedQuantum((1+(age/20))*quantum*4);//*4 because highest priority
                currentProcess.getProcess().start();
            }else{
                if(currentProcess.getProcess().getCounter()>= currentProcess.getAssignedQuantum()){
                    if(currentProcess.getProcess().isExited()){
                        currentProcess=null;
                    }else{
                        currentProcess.getProcess().sleepProcess();
                        currentProcess.getProcess().setCounterLimit(currentProcess.getProcess().getCounterLimit()-currentProcess.getProcess().getCounter());
                        currentProcess.getProcess().setCounter(0);
                        queuePriority_1.add(currentProcess);
                        currentProcess=null;
                    }
                }
            }
        } else if (!queuePriority_2.isEmpty()) {
            if(currentProcess==null){
                currentProcess=queuePriority_2.poll();
                Long age = (System.currentTimeMillis()-currentProcess.getTimeStarted())/1000;//in seconds
                currentProcess.setAssignedQuantum((1+(age/20))*quantum*3);//*3 because priority
                currentProcess.getProcess().start();
            }else{
                if(currentProcess.getProcess().getCounter()>= currentProcess.getAssignedQuantum()){
                    if(currentProcess.getProcess().isExited()){
                        currentProcess=null;
                    }else {
                        currentProcess.getProcess().sleepProcess();
                        currentProcess.getProcess().setCounterLimit(currentProcess.getProcess().getCounterLimit()-currentProcess.getProcess().getCounter());
                        currentProcess.getProcess().setCounter(0);
                        queuePriority_1.add(currentProcess);
                        currentProcess=null;
                    }
                }
            }
        }else if(!queuePriority_3.isEmpty()){
            if(currentProcess==null){
                currentProcess=queuePriority_3.poll();
                Long age = (System.currentTimeMillis()-currentProcess.getTimeStarted())/1000;//in seconds
                currentProcess.setAssignedQuantum((1+(age/20))*quantum*2);//*3 because priority
                currentProcess.getProcess().start();
            }else{
                if(currentProcess.getProcess().getCounter()>= currentProcess.getAssignedQuantum()){
                    if(currentProcess.getProcess().isExited()){
                        currentProcess=null;
                    }else {
                        currentProcess.getProcess().sleepProcess();
                        currentProcess.getProcess().setCounterLimit(currentProcess.getProcess().getCounterLimit()-currentProcess.getProcess().getCounter());
                        currentProcess.getProcess().setCounter(0);
                        queuePriority_2.add(currentProcess);
                        currentProcess=null;
                    }
                }
            }
        } else if (!queuePriority_4.isEmpty()) {
            if(currentProcess==null){
                currentProcess=queuePriority_4.poll();
                Long age = (System.currentTimeMillis()-currentProcess.getTimeStarted())/1000;//in seconds
                currentProcess.setAssignedQuantum((1+(age/20))*quantum*1);//*3 because priority
                currentProcess.getProcess().start();
            }else{
                if(currentProcess.getProcess().getCounter()>= currentProcess.getAssignedQuantum()){
                    if(currentProcess.getProcess().isExited()){
                        currentProcess=null;
                    }else {
                        currentProcess.getProcess().sleepProcess();
                        currentProcess.getProcess().setCounterLimit(currentProcess.getProcess().getCounterLimit()-currentProcess.getProcess().getCounter());
                        currentProcess.getProcess().setCounter(0);
                        queuePriority_3.add(currentProcess);
                        currentProcess=null;
                    }
                }
            }
        }

    }

    @Setter
    @Getter
    @ToString
    private class ProcessWrapper implements Comparable<ProcessWrapper>{
        private org.kstoi.entities.Process process;
        private Long assignedQuantum;
        private Long timeStarted;

        public ProcessWrapper(org.kstoi.entities.Process process) {
            this.process = process;
            timeStarted=System.currentTimeMillis();
        }

        @Override
        public int compareTo(ProcessWrapper o) {
            if(this.getProcess().getCounterLimit()
                    == o.getProcess().getCounterLimit() ) {
                return 0;
            }
            return this.getProcess().getCounterLimit()
                    < o.getProcess().getCounterLimit()
                    ?-1:1;
        }

    }
}
