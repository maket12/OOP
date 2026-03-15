package ru.nsu.ziabkin.variants;

import java.util.Arrays;
import ru.nsu.ziabkin.PrimeUtils;

/**
 * Parallel stream prime checker realisation.
 */
public class StreamPrimeChecker {
    /**
     * Check if the given array contains non-prime numbers.
     *
     * @param arr array of integer numbers
     * @return true if arr contains at least one non-prime number and false otherwise
     */
    public static boolean hasNonPrime(int[] arr) {
        return Arrays.stream(arr)
                .parallel()
                .anyMatch(PrimeUtils::isPrime);
    }
}
