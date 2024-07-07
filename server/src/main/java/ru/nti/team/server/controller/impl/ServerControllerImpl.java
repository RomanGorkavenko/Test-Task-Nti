package ru.nti.team.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nti.team.server.controller.ServerController;
import ru.nti.team.server.model.ConfigServer;
import ru.nti.team.server.model.ConfigServerRequest;
import ru.nti.team.server.model.ConfigServerResponse;
import ru.nti.team.server.service.ServerService;

import java.io.IOException;

@RestController
@RequestMapping("/v1/server")
@RequiredArgsConstructor
@Log
public class ServerControllerImpl implements ServerController {

    private final ServerService serverService;

    @Override
    @PostMapping("/start")
    public ResponseEntity<String> start(@RequestBody Integer frequencyX) {
        if (ConfigServer.getPortClient()  == null) {
            return new ResponseEntity<>("Config server is not configured", HttpStatus.NOT_FOUND);
        } else {
            serverService.start(frequencyX);
            return ResponseEntity.ok("true");
        }
    }

    @Override
    @PostMapping("/stop")
    public void stop(@RequestBody boolean flag) {
        serverService.stop(flag);
    }

    @PostMapping("/config")
    public ResponseEntity<ConfigServerResponse> config(@RequestBody ConfigServerRequest config) {
        return ResponseEntity.ok(serverService.config(config));
    }

    @Override
    @PostMapping("/launching")
    public ResponseEntity<String> launching() throws IOException {
        if (ConfigServer.getPortClient() == null) {
            return new ResponseEntity<>("Config server is not configured", HttpStatus.NOT_FOUND);
        } else {
            serverService.launching();
            return ResponseEntity.ok("true");
        }
    }

    @Override
    @PostMapping("/disabling")
    public ResponseEntity<Boolean> disabling() throws IOException {
        serverService.disabling();
        return ResponseEntity.ok(true);
    }
}
