package ru.charushnikov.attestation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое, если передан неправильный запрос
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongRequestException extends RuntimeException {
    public WrongRequestException(String message) {
        super(message
        );
    }
}
