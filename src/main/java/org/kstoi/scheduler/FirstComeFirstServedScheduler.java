package org.kstoi.scheduler;

import org.kstoi.entities.Process;
import org.kstoi.entities.State;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class FirstComeFirstServedScheduler extends Scheduler{
    private Queue<Process> queue = new ArrayDeque<>();

    @Override
    public void insert(Process process) {
        queue.add(process);

    }

    @Override
    public void schedule() {
        System.out.println(queue);
        if(queue.peek() !=null && queue.peek().getState()!= State.RUNNING && !queue.peek().isExited()){
            queue.peek().changeStateTORUN();
        }
        if(queue.peek() !=null && queue.peek().getState()== State.TORUN){
            queue.peek().start();
        }
        if(queue.peek()!=null && queue.peek().isExited()){
            System.out.println("Exiting "+queue.poll());
        }

    }
}
