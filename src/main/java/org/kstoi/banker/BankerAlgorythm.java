package org.kstoi.banker;

import lombok.Data;
import lombok.ToString;

@ToString
public class BankerAlgorythm {
    private int numberOfProcesses;
    private int numberOfResources;
    private int[][] needs;
    private int[][] maximum;
    private int[][] allocation;
    private int []available;
    int safeSequence[];

    public BankerAlgorythm(int numberOfProcesses, int numberOfResources, int[][] allocation, int[] available, int[][] maximum) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfResources = numberOfResources;
        this.allocation = allocation;
        this.available = available;
        this.maximum = maximum;

        needs=new int[numberOfProcesses][numberOfResources];
    }
    public boolean isSafeState(){
        boolean[] finished = new boolean[numberOfProcesses];
        int[] work = available.clone();
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfProcesses; j++) {
               if(!finished[j]){
                   boolean canFinish = true;
                   for(int k=0;k<numberOfResources;k++){
                       if(needs[j][k] > work[k]){
                           canFinish = false;
                           break;
                       }
                   }
                   if(canFinish){
                       for (int k=0;k<numberOfResources; k++){
                           work[k] += allocation[j][k];
                       }
                       finished[j] = true;
                       i = -1;
                   }
               }
            }

        }
        for (boolean f:finished){
            if(!f){
                return false;
            }
        }
        return true;
    }
    public boolean requestResources(int process,int[] request){
        for (int i = 0; i < numberOfResources; i++) {
            if(request[i] > needs[process][i]){
                System.out.println("process "+process+" requests more resources than needed");
                return false;
            }
            if (request[i] > available[i]){
                System.out.println("process "+process+" requests more resources than available");
            }
        }
        for (int i = 0; i < numberOfResources; i++) {
            available[i] -= request[i];
            allocation[process][i] += request[i];
            needs[process][i] -= request[i];
        }
        if(isSafeState()){
            System.out.println("system is safe and request can be fulfilled");
            return true;
        } else {
            for (int i = 0; i < numberOfResources; i++) {
                available[i] += request[i];
                allocation[process][i] -= request[i];
                needs[process][i] += request[i];

            }
            System.out.println("request cannot be fulfilled");
            return false;
        }
    }
    public void runRequest(Request request){
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                needs[i][j] =maximum[i][j] - allocation[i][j];
            }
        }
        System.out.println(request);
        requestResources(request.getProcess(),request.getRequest());

    }
    @Data
    public static class Request{
        private int process;//id
        private int[] request;

        public Request(int process, int[] request) {
            this.process = process;
            this.request = request;
        }
    }
}
