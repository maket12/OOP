package ru.nsu.ziabkin.variants;

import ru.nsu.ziabkin.PrimeUtils;

/**
 * Sequential prime checker realisation
 */
public class SequentialPrimeChecker {
    /**
     * Check if the given array contains non-prime numbers
     * @param arr: array of integer numbers
     * @return true if arr contains at least one non-prime number and false otherwise
     */
    public static boolean hasNonPrime(int[] arr) {
        for (int num : arr) {
            if (PrimeUtils.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
