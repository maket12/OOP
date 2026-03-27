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

    /**
     * Adds an order to the queue and notifies waiting threads.
     *
     * @param order the order to be added.
     */
    public synchronized void addOrder(Order order) {
        if (!isOpen) {
            return;
        }
        queue.add(order);
        notifyAll();
    }

    /**
     * Takes an order from the queue, blocking if the queue is empty.
     *
     * @return the next order, or null if the queue is closed and empty.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty() && isOpen) {
            wait();
        }
        if (queue.isEmpty() && !isOpen) {
            return null;
        }
        return queue.poll();
    }

    /**
     * Closes the queue for new orders and notifies all waiting threads.
     */
    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
}
