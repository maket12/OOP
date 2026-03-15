package ru.nsu.ziabkin.variants;

import ru.nsu.ziabkin.PrimeUtils;

public class SequentialPrimeChecker {
    public static boolean hasNonPrime(int[] arr) {
        for (int num : arr) {
            if (!PrimeUtils.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
