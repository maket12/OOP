package ru.nsu.ziabkin.models;

import ru.nsu.ziabkin.utils.Logger;

/**
 * Class represents an order entity.
 */
public class Order {
    private final int id;
    private OrderState state;

    /**
     * Creates a new order with the given ID.
     *
     * @param id the unique identifier of the order.
     */
    public Order(int id) {
        this.id = id;
        this.state = OrderState.PENDING;
        Logger.log(id, state);
    }

    /**
     * Returns the order ID.
     *
     * @return the unique ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the new state of the order and logs the change.
     *
     * @param state the new order state.
     */
    public void setState(OrderState state) {
        this.state = state;
        Logger.log(id, state);
    }

    /**
     * Returns the current state of the order.
     *
     * @return the current state.
     */
    public OrderState getState() {
        return this.state;
    }
}
