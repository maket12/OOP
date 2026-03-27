package ru.nsu.ziabkin.models;

import ru.nsu.ziabkin.utils.Logger;

public class Order {
    private final int id;
    private OrderState state;

    public Order(int id) {
        this.id = id;
        this.state = OrderState.PENDING;
        Logger.log(id, state);
    }

    public int getId() {
        return id;
    }

    public void setState(OrderState state) {
        this.state = state;
        Logger.log(id, state);
    }

    public OrderState getState() {
        return this.state;
    }
}
