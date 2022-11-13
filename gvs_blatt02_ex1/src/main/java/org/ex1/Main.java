package org.ex1;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static final String URL = "gvs.lxd-vs.uni-ulm.de";
    public static final int PORT = 27378;

    public static final int WORKERCOUNT = 1;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(WORKERCOUNT+1);
        BlockingQueue<BigInteger> blockingQueue = new LinkedBlockingDeque<>(WORKERCOUNT*2);
        Controller controller = new Controller(URL, PORT, blockingQueue);
        Set<Worker> workerSet = new HashSet<>();
        for(int i=0;i<WORKERCOUNT;i++){
            workerSet.add(new Worker(blockingQueue));
        }

        executor.execute(controller);

        for(Worker worker : workerSet){
            executor.execute(worker);
        }



    }
}
