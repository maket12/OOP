package ru.nsu.ziabkin.variants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SequentialPrimeCheckerTest {
    @Test
    public void testArrayWithOnlyPrimes() {
        int[] arr = {2, 3, 5, 7, 11, 13, 2147483647};
        Assertions.assertFalse(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithOneNonPrime() {
        int[] arr = {2, 3, 5, 8, 11};
        Assertions.assertTrue(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithNonPrimeAtStart() {
        int[] arr = {4, 2, 3, 5, 7};
        Assertions.assertTrue(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithNonPrimeAtEnd() {
        int[] arr = {2, 3, 5, 7, 9};
        Assertions.assertTrue(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Assertions.assertFalse(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithCompositeNumbersOnly() {
        int[] arr = {4, 6, 8, 10, 12};
        Assertions.assertTrue(SequentialPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithNegativeAndSpecialNumbers() {
        int[] arr = {2, 3, 0, 1, -5};
        Assertions.assertTrue(SequentialPrimeChecker.hasNonPrime(arr));
    }
}