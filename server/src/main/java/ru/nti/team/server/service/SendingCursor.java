package ru.nti.team.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.java.Log;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.nti.team.server.model.ConfigServer;
import ru.nti.team.server.model.Cursor;

import java.awt.*;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log
public class SendingCursor extends TimerTask {

    public static final Object MONITOR = new Object();
    private static final int DELAY = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .registerModule(new JavaTimeModule());

    private static boolean flag = true;

    private final int period;

    public SendingCursor(int frequencyX) {
        this.period = 60000 / frequencyX;
    }

    public static void setFlag(boolean flag) {
        log.info(String.valueOf(flag));
        SendingCursor.flag = flag;
    }

    public static void sendingCursor(int frequencyX) {
        Thread threadSendingCursor = new Thread(() -> {

            try {
                Timer timer = new Timer();
                SendingCursor sendingCursor  = new SendingCursor(frequencyX);
                log.info(String.valueOf(sendingCursor.period));
                timer.schedule(sendingCursor, DELAY, sendingCursor.period);

                synchronized (MONITOR) {
                    MONITOR.wait();
                    timer.cancel();
                }
            } catch (InterruptedException e) {
                log.warning(e.getMessage());
            }
        });
        threadSendingCursor.start();
    }

    @Override
    public void run() {

        if (!flag) {
            synchronized (MONITOR){
                MONITOR.notify();
            }
        }

        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        double x = b.getX();
        double y = b.getY();
        log.info(String.valueOf(y));
        log.info(String.valueOf(x));

        Cursor cursor = new Cursor();
        cursor.setTime(LocalTime.now());
        cursor.setX(x);
        cursor.setY(y);

        b.setLocation(x + 10, y +10);

        CompletableFuture<StompSession> stompSession = getStompSessionCompletableFuture();

        try {
            byte[] cursorString = OBJECT_MAPPER.writeValueAsBytes(cursor);

            StompSession session = stompSession.get();
            session.send("/app/cursor", cursorString);

        } catch (ExecutionException | JsonProcessingException | InterruptedException e) {
            log.info(e.getMessage());
        }
    }

    private CompletableFuture<StompSession> getStompSessionCompletableFuture() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        StompSessionHandler myStompSessionHandler = new MyStompSessionHandler();

        WebSocketHttpHeaders websocketHeaders = new WebSocketHttpHeaders();
        websocketHeaders.set("Content-Type", "text/css;charset=UTF-8");

        CompletableFuture<StompSession> stompSession = stompClient
                .connectAsync("ws://localhost:" + ConfigServer.getPortClient() + "/websocket", myStompSessionHandler);

        return stompSession;
    }
}
