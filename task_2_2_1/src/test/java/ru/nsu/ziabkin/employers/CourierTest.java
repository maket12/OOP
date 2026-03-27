package ru.nsu.ziabkin.employers;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;
import ru.nsu.ziabkin.queues.Warehouse;

class CourierTest {
    private Warehouse warehouse;
    private final int trunkCapacity = 2;
    private final int deliveryTimeMs = 100;

    @BeforeEach
    void setUp() {
        this.warehouse = new Warehouse(5);
    }

    @Test
    void testCourierDeliversOrders() throws InterruptedException {
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        this.warehouse.put(order1);
        this.warehouse.put(order2);
        this.warehouse.close();

        Courier courier = new Courier(this.trunkCapacity, this.deliveryTimeMs, this.warehouse);
        Thread courierThread = new Thread(courier);
        courierThread.start();
        courierThread.join(3000);

        Assertions.assertEquals(OrderState.DELIVERED, order1.getState(),
                "Order 1 should be DELIVERED");
        Assertions.assertEquals(OrderState.DELIVERED, order2.getState(),
                "Order 2 should be DELIVERED");
    }

    @Test
    void testCourierRespectsTrunkCapacity() throws InterruptedException {
        this.warehouse.put(new Order(1));
        this.warehouse.put(new Order(2));
        this.warehouse.put(new Order(3));

        List<Order> taken = this.warehouse.take(this.trunkCapacity);

        Assertions.assertEquals(2, taken.size(),
                "Courier must not take more than trunk capacity");
    }

    @Test
    void testCourierStopsOnInterruption() throws InterruptedException {
        Courier courier = new Courier(this.trunkCapacity, this.deliveryTimeMs, this.warehouse);
        Thread courierThread = new Thread(courier);

        courierThread.start();
        Thread.sleep(100);
        courierThread.interrupt();
        courierThread.join(1000);

        Assertions.assertFalse(courierThread.isAlive(),
                "Courier thread should stop on interruption");
    }
}