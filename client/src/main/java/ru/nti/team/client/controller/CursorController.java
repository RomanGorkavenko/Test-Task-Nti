package ru.nti.team.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.nti.team.client.service.ClientService;

@Controller
@RequiredArgsConstructor
@Log
public class CursorController {

    private final ClientService clientService;

    @MessageMapping("/cursor")
    public void processMessage(byte[] message) {
        clientService.writeValueCursor(message);
    }

}
