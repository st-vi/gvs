package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.math.BigInteger;

public class Controller implements Runnable{
    private final String URL;
    private final int SUB_PORT;
    private final int PUSH_PORT;
    private final int PULL_PORT;
    public Controller(String url, int port, int pushPort, int pullPort) throws InterruptedException {
        URL = url;
        SUB_PORT = port;
        PUSH_PORT = pushPort;
        PULL_PORT = pullPort;
    }
    @Override
    public void run() {
        try {
            connect(URL, SUB_PORT);
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

        ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
        sender.bind("tcp://*:" + PUSH_PORT);

        ZMQ.Socket sink = context.createSocket(SocketType.PULL);
        sink.bind("tcp://*:" + PULL_PORT);

        while(true){
            String message = subscriber.recvStr(0).trim();
            BigInteger n = new BigInteger(message);
            sender.send(n.toString(), 0);
            System.out.println(new String(sink.recv(0), ZMQ.CHARSET));
        }
    }
}
