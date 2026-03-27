package ru.nsu.ziabkin.employers;

import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;
import ru.nsu.ziabkin.queues.Warehouse;

import java.util.List;

/**
 * Class represents a courier entity.
 */
public class Courier implements Runnable {
    private final int trunkCapacity;
    private final int deliveryTimeMs;
    private final Warehouse warehouse;

    public Courier(int trunkCapacity, int deliveryTimeMs, Warehouse warehouse) {
        this.trunkCapacity = trunkCapacity;
        this.deliveryTimeMs = deliveryTimeMs;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = warehouse.take(trunkCapacity);
                if (orders == null) {
                    break;
                }

                for (Order o : orders) {
                    o.setState(OrderState.DELIVERING);
                }

                Thread.sleep(deliveryTimeMs * orders.size());

                for (Order o : orders) {
                    o.setState(OrderState.DELIVERED);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
