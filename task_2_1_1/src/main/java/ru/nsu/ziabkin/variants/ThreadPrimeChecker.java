package ru.nsu.ziabkin.variants;

import ru.nsu.ziabkin.PrimeUtils;

public class ThreadPrimeChecker {
    private static volatile boolean found = false;

    public static boolean hasNonPrime(int[] arr, int numThreads) throws InterruptedException {
        found = false;
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (arr.length + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, arr.length);

            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !found; j++) {
                    if (Thread.currentThread().isInterrupted()) return;

                    if (!PrimeUtils.isPrime(arr[j])) {
                        found = true;
                        interruptAll(threads);
                        return;
                    }
                }
            });
        }

        for (Thread t : threads) t.start();

        for (Thread t : threads) {
            t.join();
        }
        return found;
    }

    private static void interruptAll(Thread[] threads) {
        for (Thread t : threads) {
            if (t != null) t.interrupt();
        }
    }
}