package ru.nsu.ziabkin.queues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.models.Order;
import java.util.List;

class WarehouseTest {
    @Test
    void testWarehouseCapacityAndTake() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));

        List<Order> taken = warehouse.take(5);
        Assertions.assertEquals(2, taken.size(),
                "The courier was supposed to pick up all two pizzas.");
    }

    @Test
    void testWaitingWhenEmpty() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);

        Thread t = new Thread(() -> {
            try { warehouse.take(1); } catch (InterruptedException ignored) {}
        });

        t.start();

        int attempts = 0;
        while (t.getState() != Thread.State.WAITING && attempts < 10) {
            Thread.sleep(50);
            attempts++;
        }

        Assertions.assertEquals(Thread.State.WAITING, t.getState(),
                "The flow must wait if the warehouse is empty.");
        t.interrupt();
    }
}