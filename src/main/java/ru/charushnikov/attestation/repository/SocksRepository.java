package ru.charushnikov.attestation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.charushnikov.attestation.entity.Socks;

/**
 * Интерфейс SocksRepository
 * для работы с БД
 */

public interface SocksRepository extends JpaRepository<Socks, Long> {

    /**
     * Поиск носков по цвету и содержанию хлопка
     */
    Socks findByColorIgnoreCaseAndCottonPart(String color, Integer cottonPart);

    /**
     * Выводит сумму колонок quantity, где совадает цвет и содержание хлопка больше, чем переданный параметр
     */
    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color AND s.cottonPart > :cottonPart")
    Integer getTotalQuantityByColorAndMoreThanCottonPart(String color, Integer cottonPart);

    /**
     * Выводит сумму колонок quantity, где совадает цвет и содержание хлопка меньше, чем переданный параметр
     */
    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color AND s.cottonPart < :cottonPart")
    Integer getTotalQuantityByColorAndLessThanCottonPart(String color, Integer cottonPart);

    /**
     * Выводит значение quantity, где совадает цвет и содержание хлопка переданные в параметре запроса
     */
    @Query("SELECT s.quantity FROM Socks s WHERE s.color = :color AND s.cottonPart = :cottonPart")
    Integer getTotalQuantityByColorAndEqualCottonPart(String color, Integer cottonPart);
}
