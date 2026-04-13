package ru.nsu.ziabkin.queues;

import java.util.List;
import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;

/**
 * Class represents a warehouse for ready products.
 * It uses an internal OrderQueue to manage storage and synchronization.
 */
public class Warehouse {
    private final OrderQueue internalQueue;

    /**
     * Initializes the warehouse with a given capacity.
     *
     * @param capacity the maximum number of pizzas the warehouse can hold.
     */
    public Warehouse(int capacity) {
        this.internalQueue = new OrderQueue(capacity);
    }

    /**
     * Puts a finished order into the warehouse and updates its state.
     *
     * @param order the order to be stored.
     * @throws InterruptedException if the thread is interrupted while waiting for space.
     */
    public void put(Order order) throws InterruptedException {
        internalQueue.addOrder(order);
        order.setState(OrderState.READY_FOR_DELIVERY);
    }

    /**
     * Takes orders from the warehouse for delivery.
     *
     * @param maxCapacity the maximum number of orders the courier can carry.
     * @return a list of orders to be delivered.
     * @throws InterruptedException if the thread is interrupted while waiting for orders.
     */
    public List<Order> take(int maxCapacity) throws InterruptedException {
        return internalQueue.takeOrders(maxCapacity);
    }

    /**
     * Closes the warehouse.
     */
    public void close() {
        internalQueue.close();
    }
}
