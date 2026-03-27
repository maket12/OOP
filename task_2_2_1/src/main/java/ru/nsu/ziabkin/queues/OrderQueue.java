package ru.nsu.ziabkin.queues;

import java.util.LinkedList;
import java.util.Queue;
import ru.nsu.ziabkin.models.Order;

/**
 * Class represents a queue of orders.
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private boolean isOpen = true;

    public synchronized void addOrder(Order order) {
        if (!isOpen) {
            return;
        }
        queue.add(order);
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty() && isOpen) {
            wait();
        }
        if (queue.isEmpty() && !isOpen) {
            return null;
        }
        return queue.poll();
    }

    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
}
