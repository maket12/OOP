package ru.nsu.ziabkin.utils;

/**
 * Class represents pizzeria's config.
 */
public class PizzeriaConfig {
    public int[] bakerSpeeds;
    public PizzeriaConfig.CourierConfig[] couriers;
    public int warehouseCapacity;

    /**
     * Initializes a courier config
     */
    public static class CourierConfig {
        public int trunkSize;
        public int speedMs;
    }
}
