package ru.nsu.ziabkin;

import ru.nsu.ziabkin.utils.PrimeUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPrimeChecker {
    public static boolean hasNonPrime(int[] arr, int numThreads) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        AtomicBoolean found = new AtomicBoolean(false);
        int chunkSize = (arr.length + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, arr.length);

            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !found.get(); j++) {
                    if (!PrimeUtils.isPrime(arr[j])) {
                        found.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
        return found.get();
    }
}
