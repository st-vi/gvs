package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static final String SUB_URL = "gvs.lxd-vs.uni-ulm.de";
    public static final int SUB_PORT = 27378;
    public static final String CONTROLLER_URL = "localhost";
    public static final int CONTROLLER_PORT = 5587;

    public static final int WORKER_PORT = 5588;

    public static final int WORKER_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(WORKER_COUNT +1);
        Controller controller = new Controller(SUB_URL, SUB_PORT, CONTROLLER_PORT, WORKER_PORT);
        Set<Worker> workerSet = new HashSet<>();
        for(int i = 0; i< WORKER_COUNT; i++){
            workerSet.add(new Worker(CONTROLLER_URL, CONTROLLER_PORT, WORKER_PORT));
        }

        executor.execute(controller);

        for(Worker worker : workerSet){
            executor.execute(worker);
        }



    }
}
