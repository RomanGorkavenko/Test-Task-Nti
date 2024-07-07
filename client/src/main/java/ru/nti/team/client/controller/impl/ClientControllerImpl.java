package ru.nti.team.client.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nti.team.client.controller.ClientController;
import ru.nti.team.client.model.ConfigClient;
import ru.nti.team.client.model.ConfigClientRequest;
import ru.nti.team.client.model.ConfigClientResponse;
import ru.nti.team.client.model.entity.Cursor;
import ru.nti.team.client.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
@Log(topic = "ClientController")
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;

    @Override
    @PostMapping("/config")
    public ResponseEntity<ConfigClientResponse> config(@RequestBody ConfigClientRequest config) {
        return ResponseEntity.ok(clientService.config(config));
    }

    @Override
    @PostMapping("/start")
    public ResponseEntity<String> start() {
        if (ConfigClient.getIpServer() == null ||
                ConfigClient.getPortServer() == null ||
                ConfigClient.getFrequencyX() == null) {
            return new ResponseEntity<>("Config client is not configured", HttpStatus.NOT_FOUND);
        }else {
            clientService.start().subscribe(System.out::println);
            return ResponseEntity.ok("true");
        }
    }

    @Override
    @PostMapping("/stop")
    public ResponseEntity<Boolean> stop() {
        clientService.stop().subscribe(System.out::println);
        return ResponseEntity.ok(true);
    }

    @Override
    @PostMapping("/log")
    public ResponseEntity<List<Cursor>> log(@RequestParam String sortParameter) {
        return ResponseEntity.ok(clientService.log(sortParameter));
    }
}
