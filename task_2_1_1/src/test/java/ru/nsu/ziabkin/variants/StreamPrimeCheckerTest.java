package ru.nsu.ziabkin.variants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StreamPrimeCheckerTest {

    @Test
    public void testArrayWithOnlyPrimes() {
        int[] arr = {2, 3, 5, 7, 11, 13, 2147483647};
        Assertions.assertFalse(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithOneNonPrime() {
        int[] arr = {2, 3, 5, 8, 11};
        Assertions.assertTrue(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithNonPrimeAtStart() {
        int[] arr = {4, 2, 3, 5, 7};
        Assertions.assertTrue(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithNonPrimeAtEnd() {
        int[] arr = {2, 3, 5, 7, 9};
        Assertions.assertTrue(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Assertions.assertFalse(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testLargeArrayWithNonPrime() {
        int[] arr = new int[1000000];
        java.util.Arrays.fill(arr, 2147483647);
        arr[500000] = 100;
        Assertions.assertTrue(StreamPrimeChecker.hasNonPrime(arr));
    }

    @Test
    public void testArrayWithSpecialNumbers() {
        int[] arr = {2, 3, 1, 0, -5};
        Assertions.assertTrue(StreamPrimeChecker.hasNonPrime(arr));
    }
}