package ru.charushnikov.attestation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.charushnikov.attestation.entity.Socks;
import ru.charushnikov.attestation.exception.WrongRequestException;
import ru.charushnikov.attestation.repository.SocksRepository;
import ru.charushnikov.attestation.service.SocksService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocksServiceImpl implements SocksService {

    /**
     * Поле репозитория носков
     */
    private final SocksRepository socksRepository;

    /**
     * Метод, позволяющий сохранить новые носки или увеличить количество носков, если они уже есть в БД
     */
    @Override
    public Socks income(Socks socks) {
        if (socks == null) {
            throw new WrongRequestException("Parameter empty!");
        }

        log.info("Вызван метод регистрации прихода носков");
        Socks findSocks = socksRepository.findByColorIgnoreCaseAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (findSocks == null) {
            return socksRepository.save(socks);
        } else {
            int newQuantity = findSocks.getQuantity() + socks.getQuantity();
            findSocks.setQuantity(newQuantity);
            return socksRepository.save(findSocks);
        }
    }

    /**
     * Метод, позволяющий уменьшить количество носков, если они уже есть в БД
     */
    @Override
    public Socks outcome(Socks socks) {
        log.info("Вызван метод отпуска носков");
        Socks findSocks = socksRepository.findByColorIgnoreCaseAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (findSocks == null) {
            throw new WrongRequestException("These socks are out of stock");
        } else {
            int newQuantity = findSocks.getQuantity() - socks.getQuantity();
            findSocks.setQuantity(newQuantity);
            return socksRepository.save(findSocks);
        }
    }

    /**
     * Метод, позволяющий узнать какое колтчество носков есть в БД,
     * учитывая цвет и процентное содержание хлопка
     */
    @Override
    public Integer getSocks(String color, String operation, int cottonPart) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(color);
        if (cottonPart < 0 || cottonPart > 100 || !matcher.find()) {
            throw new WrongRequestException("Indicated the wrong color or percentage");
        }

        log.info("Вызван метод поиска носков");
        if (operation.equalsIgnoreCase("moreThan")) {
            return socksRepository.getTotalQuantityByColorAndMoreThanCottonPart(color, cottonPart);
        } else if (operation.equalsIgnoreCase("lessThan")) {
            return socksRepository.getTotalQuantityByColorAndLessThanCottonPart(color, cottonPart);
        } else if (operation.equalsIgnoreCase("equal")) {
            return socksRepository.getTotalQuantityByColorAndEqualCottonPart(color, cottonPart);
        } else {
            throw new WrongRequestException("Wrong operation");
        }
    }

}
