package ru.nsu.ziabkin;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import ru.nsu.ziabkin.employers.Baker;
import ru.nsu.ziabkin.employers.Courier;
import ru.nsu.ziabkin.models.Order;
import ru.nsu.ziabkin.queues.OrderQueue;
import ru.nsu.ziabkin.queues.Warehouse;
import ru.nsu.ziabkin.utils.PizzeriaConfig;

/**
 * Main class.
 */
public class Main {
    /**
     * Main method
     */
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        PizzeriaConfig config = gson.fromJson(new FileReader("config.json"), PizzeriaConfig.class);

        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(config.warehouseCapacity);

        List<Thread> bakerThreads = new ArrayList<>();
        List<Thread> courierThreads = new ArrayList<>();

        for (int speed : config.bakerSpeeds) {
            Thread t = new Thread(new Baker(speed, queue, warehouse));
            t.start();
            bakerThreads.add(t);
        }

        for (PizzeriaConfig.CourierConfig cc : config.couriers) {
            Thread t = new Thread(new Courier(cc.trunkSize, cc.speedMs, warehouse));
            t.start();
            courierThreads.add(t);
        }

        for (int i = 1; i <= 15; i++) {
            queue.addOrder(new Order(i));
            Thread.sleep(200);
        }

        System.out.println("--- PIZZERIA STOPS ACCEPTANCE OF NEW ORDERS ---");

        queue.close();

        for (Thread t : bakerThreads) {
            t.join();
        }

        System.out.println("--- ALL BAKERS HAVE FINISHED WORK, WAREHOUSE CLOSING ---");

        warehouse.close();

        for (Thread t : courierThreads) {
            t.join();
        }

        System.out.println("--- THE WORKING DAY IS ENDED ---");
    }
}
