package ru.nsu.ziabkin;

import ru.nsu.ziabkin.utils.PrimeUtils;

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
