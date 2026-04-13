package ru.nsu.ziabkin.queues;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.nsu.ziabkin.models.Order;

/**
 * Universal order queue with support for capacity limiting.
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private final int capacity;
    private boolean isOpen = true;

    /**
     * Creates an unlimited queue. Typically used for incoming orders.
     */
    public OrderQueue() {
        this.capacity = -1;
    }

    /**
     * Creates a queue with a specific capacity limit. Typically used for the warehouse.
     *
     * @param capacity the maximum number of orders allowed in the queue.
     */
    public OrderQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds an order to the queue. Blocks if the queue is at full capacity.
     *
     * @param order the order object to be added.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized void addOrder(Order order) throws InterruptedException {
        while (capacity != -1 && queue.size() >= capacity && isOpen) {
            wait();
        }
        if (!isOpen) {
            return;
        }
        queue.add(order);
        notifyAll();
    }

    /**
     * Takes a single order from the queue. Blocks if the queue is empty.
     *
     * @return the polled order, or null if the queue is closed and empty.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty() && isOpen) {
            wait();
        }
        if (queue.isEmpty() && !isOpen) {
            return null;
        }
        Order order = queue.poll();
        notifyAll(); // Notify producers that space has become available.
        return order;
    }

    /**
     * Takes multiple orders at once. Typically used by couriers based on trunk capacity.
     *
     * @param maxCount the maximum number of orders to take.
     * @return a list of orders taken from the queue.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized List<Order> takeOrders(int maxCount) throws InterruptedException {
        while (queue.isEmpty() && isOpen) {
            wait();
        }
        if (queue.isEmpty() && !isOpen) {
            return null;
        }

        List<Order> taken = new ArrayList<>();
        int count = Math.min(maxCount, queue.size());
        for (int i = 0; i < count; i++) {
            taken.add(queue.poll());
        }
        notifyAll(); // Notify producers that space has become available.
        return taken;
    }

    /**
     * Closes the queue and notifies all waiting threads.
     */
    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
}
