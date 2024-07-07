package ru.nti.team.server.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.nti.team.server.model.ConfigServerRequest;
import ru.nti.team.server.model.ConfigServerResponse;

import java.io.IOException;

@Tag(name = "Server Controller", description = "Server API")
public interface ServerController {

    @Hidden
    ResponseEntity<String> start(Integer frequencyX);

    @Hidden
    void stop(boolean flag);

    @Operation(summary = "Конфигурация сервера.", description = "Необходимо указать порт клиента.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сервер сконфигурирован.", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfigServerResponse.class))
            })
    })
    ResponseEntity<ConfigServerResponse> config(ConfigServerRequest config);

    @Operation(summary = "Запуск прослушивания порта клиента",
            description = "Сервер начинает прослушивать порта клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "true", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "true"))
            }),
            @ApiResponse(responseCode = "404", description = "Config server is not configured", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Config server is not configured"))
            })
    })
    ResponseEntity<String> launching() throws IOException;

    @Operation(summary = "Остановка прослушивания порта клиента",
            description = "Сервер заканчивает прослушивать порта клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "true", content = {
                    @Content(mediaType = "application/boolean",
                            schema = @Schema(implementation = Boolean.class))
            })
    })
    ResponseEntity<Boolean> disabling() throws IOException;
}
