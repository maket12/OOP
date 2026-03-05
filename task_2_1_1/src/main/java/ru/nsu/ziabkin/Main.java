package ru.nsu.ziabkin;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Подготовка данных: 10 млн больших простых чисел
        int size = 10_000_000;
        int[] data = new int[size];
        Arrays.fill(data, 2147483647); // Максимальное простое число int

        System.out.println("--- Begin to analyze ---");

        // 1. Последовательно
        long start = System.currentTimeMillis();
        SequentialPrimeChecker.hasNonPrime(data);
        System.out.println("Sequentially: " + (System.currentTimeMillis() - start) + " ms");

        // 2. Thread (от 2 до 8 потоков)
        int[] threadsToTest = {2, 4, 8};
        for (int t : threadsToTest) {
            start = System.currentTimeMillis();
            ThreadPrimeChecker.hasNonPrime(data, t);
            System.out.println("Threads (" + t + "): " + (System.currentTimeMillis() - start) + " ms");
        }

        // 3. Parallel Stream
        start = System.currentTimeMillis();
        StreamPrimeChecker.hasNonPrime(data);
        System.out.println("Parallel Stream: " + (System.currentTimeMillis() - start) + " ms");
    }
}