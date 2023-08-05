package ru.charushnikov.attestation.service;

import ru.charushnikov.attestation.entity.Socks;

public interface SocksService {
    Socks income(Socks socks);

    Socks outcome(Socks socks);

    Integer getSocks(String color, String operation, int cottonPart);
}
