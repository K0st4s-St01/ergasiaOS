package org.kstoi.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Random;
@Setter
@Getter
public class Process implements Runnable{
    private Integer pid;
    private String uid;
    private String name;
    private Integer priority;
    private State state;
    private Thread t;


    private static Random rand;
    public static Long sleepAmount=100L;
    private boolean exited =false;
    private boolean sleep=false;

    {
        rand = new Random();
    }
    private int counter=0;
    private int counterLimit;

    public Process(Integer pid, String uid, String name, Integer priority) {
        this.pid = pid;
        this.uid = uid;
        this.name = name;
        this.priority = priority;
        this.counterLimit = rand.nextInt(10,1000);
        this.state=State.SUSPENDED;



    }
    public void changeStateTORUN(){
        System.out.println("state torun name:"+name+" pid: "+pid);
        state=state.TORUN;
    }

    public void start(){
        sleep=false;
        state=state.RUNNING;
        System.out.println("state run name:"+name+" pid: "+pid);
        t= new Thread(this);
        t.start();
    }
    public synchronized void sleepProcess(){
        System.out.println("sleep -> "+this);
        state=state.SUSPENDED;
        sleep=true;
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public synchronized void wake(){
        System.out.println("wake -> "+this);
        sleep=false;
        state=state.TORUN;
        state=state.RUNNING;
        this.start();
    }

    public synchronized void exit() throws InterruptedException {
        System.out.println("exit name:"+name+" pid: "+pid);
        state=State.SUSPENDED;
        exited=true;
        t.join();
    }

    @SneakyThrows
    @Override
    public void run() {
        while(state == State.RUNNING) {
            Thread.sleep(sleepAmount);
            System.out.println(" running -> pid : "+pid+" user : "+uid+" name : " + name +" - "+ counter);
            counter++;
            if (counter >= counterLimit) {
                try {
                    exit();
                } catch (InterruptedException e) {
                    System.out.println("exited with error " + e.getMessage());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                " total time -> "+this.counterLimit+
                " remaining -> "+(this.counterLimit - this.counter)+
                "priority -> "+this.getPriority()+
                '}';
    }
}
