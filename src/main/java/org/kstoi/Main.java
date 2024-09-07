package org.kstoi;

import org.kstoi.scheduler.*;
import org.kstoi.scheduler2.EDFScheduler;
import org.kstoi.scheduler2.MultipleQueues;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //ProcessGenerator generator= new ProcessGenerator(new MultipleQueues());
        //generator.runExample();
        var processList= new ArrayList<EDFScheduler.ProcessEDF>();
        processList.add(new EDFScheduler.ProcessEDF(0,"user","notepad",1,800,100));
        processList.add(new EDFScheduler.ProcessEDF(0,"user","intellij",1,500,200));
        processList.add(new EDFScheduler.ProcessEDF(0,"user","game",1,1000,400));
        var scheduler = new EDFScheduler();
        try {
            System.out.println(scheduler.schedule(processList,2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}