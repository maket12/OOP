package ru.nsu.ziabkin.queues;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;

/**
 * Class represents a warehouse.
 */
public class Warehouse {
    private final Queue<Order> pizzas = new LinkedList<>();
    private final int capacity;
    private boolean isOpen = true;

    /**
     * Initializes a warehouse.
     *
     * @param capacity the maximum number of pizzas the warehouse can store.
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Puts an order into the warehouse, blocking if it is full.
     *
     * @param order the order to store.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized void put(Order order) throws InterruptedException {
        while (pizzas.size() >= capacity && isOpen) {
            wait();
        }
        if (!isOpen) {
            return;
        }
        pizzas.add(order);
        order.setState(OrderState.READY_FOR_DELIVERY);
        notifyAll();
    }

    /**
     * Takes multiple orders from the warehouse up to the specified capacity.
     *
     * @param maxCapacity the maximum number of orders to take.
     * @return a list of orders, or null if the warehouse is closed and empty.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized List<Order> take(int maxCapacity) throws InterruptedException {
        while (pizzas.isEmpty() && isOpen) {
            wait();
        }
        if (pizzas.isEmpty() && !isOpen) {
            return null;
        }

        List<Order> taken = new ArrayList<>();
        int count = Math.min(maxCapacity, pizzas.size());
        for (int i = 0; i < count; i++) {
            taken.add(pizzas.poll());
        }
        notifyAll();
        return taken;
    }

    /**
     * Closes the warehouse and notifies all waiting threads.
     */
    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
}
