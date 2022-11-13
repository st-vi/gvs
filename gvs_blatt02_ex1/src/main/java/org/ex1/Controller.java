package org.ex1;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable{
    BlockingQueue<BigInteger> queue;
    private final String URL;
    private final int PORT;
    public Controller(String url, int port, BlockingQueue<BigInteger> queue) throws InterruptedException {
        this.queue = queue;
        URL = url;
        PORT = port;
    }
    @Override
    public void run() {
        try {
            connect(URL, PORT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(String url, int port) throws InterruptedException {
        ZContext context = new ZContext();
        ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
        subscriber.connect("tcp://" + url + ":" + port);
        String subscription = "";
        subscriber.subscribe(subscription.getBytes(ZMQ.CHARSET));
        while(true){
            String message = subscriber.recvStr(0).trim();
            BigInteger n = new BigInteger(message);
            queue.put(n);
        }
    }
}
