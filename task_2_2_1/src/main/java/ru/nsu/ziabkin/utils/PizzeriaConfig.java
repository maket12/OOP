package ru.nsu.ziabkin.utils;

public class PizzeriaConfig {
    public int[] bakerSpeeds;
    public PizzeriaConfig.CourierConfig[] couriers;
    public int warehouseCapacity;

    public static class CourierConfig {
        public int trunkSize;
        public int speedMs;
    }
}
