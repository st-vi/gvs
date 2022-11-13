package org.ex1;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable{
    BlockingQueue<BigInteger> queue;
    PrimeFactorization primeFactorization;

    public Worker(BlockingQueue<BigInteger> queue){
        this.queue = queue;
        primeFactorization = new PrimeFactorization();
    }

    @Override
    public void run() {
        Tuple<BigInteger, BigInteger> result;
        while (true){
            try {
                BigInteger n = queue.take();
                result = primeFactorization.getPrimeFactors(n);
                System.out.printf("Result: n=%d, a=%d, b=%d%n", n, result.x, result.y);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
