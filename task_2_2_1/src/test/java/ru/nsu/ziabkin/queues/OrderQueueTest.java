package ru.nsu.ziabkin.queues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.models.Order;


class OrderQueueTest {
    @Test
    void testAddAndTakeOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        queue.addOrder(order);
        Order taken = queue.takeOrder();

        Assertions.assertEquals(1, taken.getId());
    }

    @Test
    void testCloseQueueReturnsNull() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        queue.close();

        Order taken = queue.takeOrder();
        Assertions.assertNull(
            taken,
            "After the queue is closed, takeOrder should return null."
        );
    }
}
