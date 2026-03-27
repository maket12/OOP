package ru.nsu.ziabkin.queues;

import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class represents a warehouse.
 */
public class Warehouse {
    private final Queue<Order> pizzas = new LinkedList<>();
    private final int capacity;
    private boolean isOpen = true;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

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

    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
}
