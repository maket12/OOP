package ru.nsu.ziabkin;

import java.util.Arrays;
import ru.nsu.ziabkin.variants.SequentialPrimeChecker;
import ru.nsu.ziabkin.variants.StreamPrimeChecker;
import ru.nsu.ziabkin.variants.ThreadPrimeChecker;

/**
 * Demonstration of program.
 */
public class Main {
    /**
     * Demonstration method which launch 3 variants of searching for prime numbers.
     * For each variant it prints amount of time(ms) of execution and result of the search.
     */
    public static void main(String[] args) throws InterruptedException {
        int size = 10_000_000;
        int[] data = new int[size];
        Arrays.fill(data, 2147483647);

        System.out.println("--- Begin to analyze ---");

        long start = System.currentTimeMillis();
        boolean res = SequentialPrimeChecker.hasNonPrime(data);
        System.out.println("Sequentially: " + (System.currentTimeMillis() - start)
                + " ms" + " | " + res);

        int[] threadsToTest = {2, 4, 8};
        for (int t : threadsToTest) {
            start = System.currentTimeMillis();
            res = ThreadPrimeChecker.hasNonPrime(data, t);
            System.out.println("Threads (" + t + "): " + (System.currentTimeMillis() - start)
                    + " ms" + " | " + res);
        }

        start = System.currentTimeMillis();
        res = StreamPrimeChecker.hasNonPrime(data);
        System.out.println("Parallel Stream: " + (System.currentTimeMillis() - start)
                + " ms" + " | " + res);
    }
}