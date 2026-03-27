package ru.nsu.ziabkin.utils;

/**
 * Class represents pizzeria's config.
 */
public class PizzeriaConfig {
    public int[] bakerSpeeds;
    public PizzeriaConfig.CourierConfig[] couriers;
    public int warehouseCapacity;

    public static class CourierConfig {
        public int trunkSize;
        public int speedMs;
    }
}
