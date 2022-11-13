package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.math.BigInteger;

public class Worker implements Runnable{
    ZMQ.Socket pullSocket;
    ZMQ.Socket pushSocket;
    PrimeFactorization primeFactorization;


    public Worker(String pullUrl, int pullPort, int pushPort){
        primeFactorization = new PrimeFactorization();
        ZContext context = new ZContext();
        pullSocket = context.createSocket(SocketType.PULL);
        pullSocket.connect("tcp://" + pullUrl + ":" + pullPort);
        pushSocket = context.createSocket(SocketType.PUSH);
        pushSocket.connect("tcp://" + pullUrl + ":" + pushPort);
    }

    @Override
    public void run() {
        Tuple<BigInteger, BigInteger> result;
        while (true){
            String message = new String(pullSocket.recv(0), ZMQ.CHARSET).trim();
            BigInteger n = new BigInteger(message);
            result = primeFactorization.getPrimeFactors(n);
            pushSocket.send(String.format("Result: n=%d, a=%d, b=%d%n", n, result.x, result.y), 0);
            //System.out.printf("Result: n=%d, a=%d, b=%d%n", n, result.x, result.y);
        }
    }
}
