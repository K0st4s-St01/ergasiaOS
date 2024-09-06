package org.kstoi.scheduler;

import org.kstoi.entities.Process;

import java.io.File;
import java.sql.Time;
import java.util.Random;
import java.util.Timer;

public class ProcessGenerator {
    private Scheduler scheduler;
    private static Random rand = new Random();
    private static int pid=0;

    public ProcessGenerator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    private Process generateProcess(){
        String[] uids = new String[]{"kostas","root","hacker","anonymous"};
        String[] names=new String[]{"chrome","notepad","terminal","printer","intellij","game","fileManager","javaProgramme","steam"};
        return new Process(++pid,uids[rand.nextInt(4)],names[rand.nextInt(9)], rand.nextInt(1,4));
    }
    public void runExample() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(rand.nextLong(9000, 13000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    scheduler.insert(generateProcess());

                }
            }
        });

        t.start();
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            scheduler.schedule();
        }
    }
}
