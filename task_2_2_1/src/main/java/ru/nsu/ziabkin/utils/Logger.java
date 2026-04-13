package ru.nsu.ziabkin.utils;

import ru.nsu.ziabkin.models.OrderState;

/**
 * Logger for a thread-safe output.
 */
public class Logger {
    public static synchronized void log(int orderId, OrderState state) {
        System.out.printf("[%d] [%s]%n", orderId, state.getDescription());
    }
}
