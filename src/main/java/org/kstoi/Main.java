package org.kstoi;

import org.kstoi.banker.BankerAlgorythm;
import org.kstoi.entities.Page;
import org.kstoi.scheduler.*;
import org.kstoi.scheduler2.EDFScheduler;
import org.kstoi.scheduler2.MultipleQueues;
import org.kstoi.ws.WSClock;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Random random = new Random();
    public static void main(String[] args) {
        //uncomment example and rerun

//        ProcessGenerator generator= new ProcessGenerator(new FirstComeFirstServedScheduler());
        //generator.runExample();

//        ProcessGenerator generator= new ProcessGenerator(new ShortestJobFirstScheduler());
        //generator.runExample();

//        ProcessGenerator generator= new ProcessGenerator(new ShortestRemainingJobFirstScheduler());
        //generator.runExample();

        //Round Robin
        //var roundrobin = new RoundRobinWithVaryingTimeQuantum();
        //roundrobin.setQuantum(100L);
        //ProcessGenerator generator= new ProcessGenerator(roundrobin);

        //generator.runExample();

        //Multiple Queues
        //ProcessGenerator generator= new ProcessGenerator(new MultipleQueues());
        //generator.runExample();

        /**
         //EDF
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
         **/
        /*
        //Banker
        while(true){
            var allocation = new int[3][3];
            var available =new int[3];
            var maximum = new int[3][3];
            for(int i=0;i<3;i++){
                available[i]=random.nextInt(10,20);
                for(int j=0;j<3;j++){
                    allocation[i][j]= random.nextInt(1,10);
                    maximum[i][j]= random.nextInt(10,20);
                }
            }
            var banker=new BankerAlgorythm(3,3,allocation,available,maximum);
            System.out.println(banker);
            var req = new BankerAlgorythm.Request(1,new int[]{random.nextInt(10),random.nextInt(10), random.nextInt(10)});
            banker.runRequest(req);
        }
        */
        /*
        //WsClock
        var ws = new WSClock(5,1000);

        ws.addPage(new Page(1,false,System.currentTimeMillis()));
        System.out.println(ws);
        ws.addPage(new Page(2,true,System.currentTimeMillis()));
        System.out.println(ws);
        ws.addPage(new Page(3,false,System.currentTimeMillis()));
        System.out.println(ws);
        ws.addPage(new Page(4,true,System.currentTimeMillis()));
        System.out.println(ws);
        ws.addPage(new Page(5,false,System.currentTimeMillis()));
        System.out.println(ws);
        ws.addPage(new Page(6,true,System.currentTimeMillis()));
        System.out.println(ws);
        */

    }
}