package org.kstoi.scheduler2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kstoi.entities.Process;
import org.kstoi.entities.State;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class EDFScheduler  {
    private Integer clock=0;
    public boolean canSchedule(List<ProcessEDF> processes,int numberOfCores){
        double totalUtilization=0;
        for(ProcessEDF p : processes){
            totalUtilization+=p.utilization();
        }
        return totalUtilization<=numberOfCores;
    }
    public Map<Queue<ProcessEDF>, String> schedule(List<ProcessEDF> processes, int numberOfCores) throws Exception {
        if(canSchedule(processes,numberOfCores)){
            Queue<ProcessEDF> processesQueue=new PriorityQueue<>();
            Queue<ProcessEDF> processesQueueResult=new PriorityQueue<>();
            processesQueue.addAll(processes);
            processesQueueResult.addAll(processes);
            StringBuilder execution=new StringBuilder("");
            //execution
            ProcessEDF[] cores = new ProcessEDF[numberOfCores];
            MASTERLOOP:while (!processesQueue.isEmpty()){
                for (int i = 0; i < numberOfCores; i++) {
                    if(processesQueue.isEmpty()){
                        break MASTERLOOP;
                    }
                    if(cores[i]!=null && cores[i].isSleep()){
                        cores[i].setDeadline(cores[i].getPeriod()+cores[i].getDeadline());
                        cores[i].setCounterLimit(cores[i].getCounterLimit()-cores[i].getCounter());
                        cores[i].setCounter(0);
                        processesQueue.add(cores[i]);
                        processesQueueResult.add(cores[i]);
                        cores[i]=null;

                    }
                    if(cores[i] == null) {
                        cores[i] = processesQueue.poll();
                        cores[i].start();
                    }


                }
            }
            return Map.of(processesQueueResult,execution.toString());
        }


        throw new Exception("cannot be scheduled");
    }


    @Getter
    @Setter
    public static class ProcessEDF extends Process implements Comparable<ProcessEDF> {
        private Integer period;
        private Integer deadline;
        private Integer executionTime;

        @Override
        public String toString() {
            return "ProcessEDF{" + super.toString()+
                    "period=" + period +
                    ", deadline=" + deadline +
                    ", executionTime=" + executionTime +
                    '}';
        }

        public ProcessEDF(Integer pid, String uid, String name, Integer priority, Integer period, Integer executionTime) {
            super(pid, uid, name, priority);
            this.period = period;
            this.deadline=period;
            this.executionTime = executionTime;
        }

        public double utilization(){
            return (double) executionTime / period;
        }

        @Override
        public void run() {
           while (this.getState() == State.RUNNING){
               try {
                   Thread.sleep(sleepAmount);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               setCounter(getCounter()+1);
               System.out.println(this.getName()+" "+this.getCounter());
               if(getCounter() == executionTime){
                   sleepProcess();
               }
               if(getCounter()==getCounterLimit()){
                   try {
                       exit();
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
           }
        }

        @Override
        public int compareTo(ProcessEDF processWrapper) {
            return Integer.compare(this.deadline,processWrapper.deadline);
        }
    }
}
