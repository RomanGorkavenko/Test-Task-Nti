package ru.nti.team.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.nti.team.client.model.ConfigClientRequest;
import ru.nti.team.client.model.ConfigClientResponse;
import ru.nti.team.client.model.entity.Cursor;

import java.util.List;

@Tag(name = "Client Controller", description = "Client API")
public interface ClientController {

    @Operation(summary = "Конфигурация клиента.",
            description = "Необходимо указать настройки для подключения к серверу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент сконфигурирован.", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfigClientResponse.class))
            })
    })
    ResponseEntity<ConfigClientResponse> config(ConfigClientRequest config);

    @Operation(summary = "Запуск сервера",
            description = "Сервер начинает работу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "true", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "true"))
            }),
            @ApiResponse(responseCode = "404",
                    description = "Необходимо сконфигурировать клиент для работы с сервером.", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Config client is not configured"))
            })
    })
    ResponseEntity<String> start();

    @Operation(summary = "Остановка сервера.",
              description = "Сервер заканчивает работу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "true", content = {
                    @Content(mediaType = "application/boolean",
                            schema = @Schema(implementation = Boolean.class))
            })
    })
    ResponseEntity<Boolean> stop();

    @Operation(summary = "Записать logs в файл.",
            description = "Записать logs в файл с сортировкой по выбранному параметру.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Первые пять записей.", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cursor.class))
            })
    })
    ResponseEntity<List<Cursor>> log(@Parameter(name = "sortParameter", description = "Параметр сортировки.",
            examples = {@ExampleObject(name = "Сортировать по времени", value = "T",
                    description = "Записать logs в файл с сортировкой по времени."),
                    @ExampleObject(name = "Сортировать по координате Х", value = "X",
                            description = "Записать logs в файл с сортировкой по по координате Х."),
                    @ExampleObject(name = "Сортировать по координате Y", value = "Y",
                            description = "Записать logs в файл с сортировкой по по координате Y.")})
                     String sortParameter);
}
