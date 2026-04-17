package com.example.util;

import java.time.LocalDate;
import java.util.UUID;

public class OrderNumberGenerator {

    public static String generate() {
        return "ORD-" + LocalDate.now() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
