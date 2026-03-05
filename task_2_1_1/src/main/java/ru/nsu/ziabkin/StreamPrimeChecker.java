package ru.nsu.ziabkin;

import ru.nsu.ziabkin.utils.PrimeUtils;

import java.util.Arrays;

public class StreamPrimeChecker {
    public static boolean hasNonPrime(int[] arr) {
        return Arrays.stream(arr)
                .parallel()
                .anyMatch(n -> !PrimeUtils.isPrime(n));
    }
}
