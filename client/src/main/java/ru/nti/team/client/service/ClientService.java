package ru.nti.team.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.nti.team.client.model.ConfigClient;
import ru.nti.team.client.model.ConfigClientRequest;
import ru.nti.team.client.model.ConfigClientResponse;
import ru.nti.team.client.model.entity.Cursor;
import ru.nti.team.client.repository.ClientRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class ClientService {

    private final ClientRepository clientRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .registerModule(new JavaTimeModule());

    public void writeValueCursor(byte[] message) {
        try {
            Cursor cursor = objectMapper.readValue(message, Cursor.class);
            log.info("Cursor  " + cursor);
            clientRepository.save(cursor);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public Flux<Boolean> start() {
        return WebClient.create(String.format("http://%s:%s/v1/server/",
                        ConfigClient.getIpServer(),
                        ConfigClient.getPortServer()))
                .post()
                .uri("start")
                .bodyValue(ConfigClient.getFrequencyX())
                .retrieve()
                .bodyToFlux(Boolean.class);
    }

    public Flux<Boolean> stop() {
        return WebClient.create(String.format("http://%s:%s/v1/server/",
                        ConfigClient.getIpServer(),
                        ConfigClient.getPortServer()))
                .post()
                .uri("stop")
                .bodyValue(false)
                .retrieve()
                .bodyToFlux(Boolean.class);
    }

    public ConfigClientResponse config(ConfigClientRequest config) {
        ConfigClient.getInstance(
                config.getIpServer(),
                config.getPortServer(),
                config.getFrequencyX()
        );

        ConfigClientResponse response = new ConfigClientResponse();
        response.setIpServer(ConfigClient.getIpServer());
        response.setPortServer(ConfigClient.getPortServer());
        response.setFrequencyX(ConfigClient.getFrequencyX());
        return response;
    }

    public List<Cursor> log(String sortParameter) {
        switch (sortParameter)  {
            case "T": sortParameter = "time"; break;
            case "X": sortParameter = "x"; break;
            case "Y": sortParameter = "y"; break;
        }
        List<Cursor> all = clientRepository.findAll(Sort.by(sortParameter).ascending());
        try(FileWriter writer = new FileWriter("log.txt", false)) {
            for(Cursor cursor : all) {
                writer.write(cursor.toString());
                writer.append('\n');
            }
            writer.flush();
        }
        catch(IOException e){
            log.info(e.getMessage());
        }
        return all.stream().limit(5).toList();
    }
}
