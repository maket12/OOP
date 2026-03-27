package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Test
    void testOrderInitialState() {
        Order order = new Order(1);
        Assertions.assertEquals(OrderState.PENDING, order.getState(),
                "Initial state must be PENDING");
    }

    @Test
    void testSetState() {
        Order order = new Order(1);
        order.setState(OrderState.COOKING);
        Assertions.assertEquals(OrderState.COOKING, order.getState(),
                "State should be updated to COOKING");
    }

    @Test
    void testGetId() {
        Order order = new Order(500);
        Assertions.assertEquals(500, order.getId(),
                "Order ID must match the value passed in constructor");
    }
}