package ru.nsu.ziabkin.employers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.models.OrderState;
import ru.nsu.ziabkin.queues.OrderQueue;
import ru.nsu.ziabkin.queues.Warehouse;

class BakerTest {
    private OrderQueue queue;
    private Warehouse warehouse;
    private final int speedMs = 100;

    @BeforeEach
    void setUp() {
        this.queue = new OrderQueue();
        this.warehouse = new Warehouse(5);
    }

    @Test
    void testBakerProcessesOrderSuccessfully() throws InterruptedException {
        Order order = new Order(1);
        this.queue.addOrder(order);
        this.queue.close();

        Baker baker = new Baker(this.speedMs, this.queue, this.warehouse);
        Thread bakerThread = new Thread(baker);
        bakerThread.start();
        bakerThread.join(2000);

        Assertions.assertEquals(OrderState.READY_FOR_DELIVERY, order.getState(),
                "Order status should be READY_FOR_DELIVERY after baking");
    }

    @Test
    void testBakerStopsWhenQueueClosed() throws InterruptedException {
        this.queue.close();
        Baker baker = new Baker(this.speedMs, this.queue, this.warehouse);
        Thread bakerThread = new Thread(baker);

        bakerThread.start();
        bakerThread.join(1000);

        Assertions.assertFalse(bakerThread.isAlive(),
                "Baker thread should terminate when queue is closed");
    }
}
