package ru.nsu.ziabkin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PrimeUtilsTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 104729, 2147483647})
    public void testIsPrimeWithPrimes(int n) {
        Assertions.assertTrue(PrimeUtils.isPrime(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0, 1, 4, 6, 8, 9, 10, 15, 21, 25, 100, 1000, 2147483646})
    public void testIsPrimeWithNonPrimes(int n) {
        Assertions.assertFalse(PrimeUtils.isPrime(n));
    }

    @Test
    public void testSmallestPrime() {
        Assertions.assertTrue(PrimeUtils.isPrime(2));
    }

    @Test
    public void testEdgeCaseOne() {
        Assertions.assertFalse(PrimeUtils.isPrime(1));
    }

    @Test
    public void testEdgeCaseZero() {
        Assertions.assertFalse(PrimeUtils.isPrime(0));
    }

    @Test
    public void testNegativeNumbers() {
        Assertions.assertFalse(PrimeUtils.isPrime(-7));
    }
}