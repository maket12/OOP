package ru.nsu.ziabkin.employers;

import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;
import ru.nsu.ziabkin.queues.OrderQueue;
import ru.nsu.ziabkin.queues.Warehouse;

/**
 * Class represents a baker entity.
 */
public class Baker implements Runnable {
    private final int speedMs;
    private final OrderQueue queue;
    private final Warehouse warehouse;

    /**
     * Initializes a baker
     *
     */
    public Baker(int speedMs, OrderQueue queue, Warehouse warehouse) {
        this.speedMs = speedMs;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = queue.takeOrder();
                if (order == null) {
                    break;
                }

                order.setState(OrderState.COOKING);
                Thread.sleep(speedMs);

                order.setState(OrderState.WAITING_FOR_WAREHOUSE);
                warehouse.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
