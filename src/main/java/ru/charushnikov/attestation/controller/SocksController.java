package ru.charushnikov.attestation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.charushnikov.attestation.entity.Socks;
import ru.charushnikov.attestation.service.SocksService;

import javax.validation.Valid;

/**
 * Контроллер для обработки REST-запросов: приход, отпуск носков,
 * получение количества носков на складе, учитывая цвет и содержание хлопка
 */

@RestController
@RequestMapping("/api/socks")
@Validated
@AllArgsConstructor
public class SocksController {

    /**
     * Поле сервиса носков
     */
    private final SocksService socksService;

    /**
     * Регистрирует приход носков на склад
     * @param socks носки, добавляемые на склад
     * @return добавленные носки
     */
    @Operation(
            summary = "Регистрация прихода носков",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class)
                            )
                    )
            }
    )

    @PostMapping("/income")
    public ResponseEntity<Socks> income(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.income(socks));
    }

    /**
     * Регистрирует отпуск носков со склада
     * @param socks носки удаляемые со склада
     * @return удаленные носки
     */
    @Operation(
            summary = "Регистрация отпуска носков",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class)
                            )
                    )
            }
    )

    @PostMapping("/outcome")
    public ResponseEntity<Socks> outcome(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.outcome(socks));
    }

    /**
     * Возвращает общее количество носков на складе,
     * соответствующих переданным в параметрах критериям запроса.
     * @param color цвет носков
     * @param cottonPart содержание хлопка в носках
     * @param operation операция поиска
     * @return количество найденных носков
     */
    @Operation(
            summary = "Количество носков на складе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Integer.class)
                            )
                    )
            }
    )

    @GetMapping
    public ResponseEntity<Integer> getSocks(@RequestParam String color, @RequestParam String operation, @RequestParam int cottonPart) {
        return ResponseEntity.ok(socksService.getSocks(color, operation, cottonPart));
    }
}
