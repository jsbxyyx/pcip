package org.op;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public record Connect(String address, int port, int timeout, String arg) implements Callable<Result> {
    @Override
    public Result call() {
        return switch (arg) {
            case "ip" -> {
                try (Socket socket = new Socket()) {
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);
                    long currentTimeMillis = System.currentTimeMillis();
                    socket.connect(inetSocketAddress, timeout);
                    currentTimeMillis = System.currentTimeMillis() - currentTimeMillis;
                    yield new Result(address, currentTimeMillis);
                } catch (IOException ignored) {
                }
                yield null;
            }
            default -> null;
        };
    }
}
