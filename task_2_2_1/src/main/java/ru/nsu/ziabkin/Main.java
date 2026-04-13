package ru.nsu.ziabkin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
     * Main method.
     */
    public static void main(String[] args) {
        try {
            runPizzeria();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Configuration file 'config.json' not found.");
        } catch (JsonSyntaxException e) {
            System.err.println("Error: Failed to parse JSON configuration. Check the file format.");
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runPizzeria() throws IOException, InterruptedException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("config.json")) {
            PizzeriaConfig config = gson.fromJson(reader, PizzeriaConfig.class);

            if (config == null) {
                throw new IOException("Config file is empty");
            }

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
}