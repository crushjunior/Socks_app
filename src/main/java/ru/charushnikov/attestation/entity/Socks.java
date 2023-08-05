package ru.charushnikov.attestation.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 * Класс Socks, представляет сущность объявления
 */

@Entity
@Data
public class Socks {

    /**
     * Идентификационный номер (id) носков
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Цвет носков
     */
    @NotNull(message = "Color cannot be null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Color should only contain letters")
    private String color;

    /**
     * Процентное содержание хлопка в составе носков
     */
    @Min(value = 0, message = "Value should be greater than or equal to 0")
    @Max(value = 100, message = "Value should be less than or equal to 100")
    @Digits(integer = 3, fraction = 0, message = "Value should be an integer with up to 2 digits")
    private Integer cottonPart;

    /**
     * Количество пар носков
     */
    @Positive(message = "Value should be a positive integer")
    @Min(value = 1, message = "Value should be greater than or equal to 1")
    private Integer quantity;
}
