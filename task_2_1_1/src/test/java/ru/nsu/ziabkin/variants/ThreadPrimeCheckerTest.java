package ru.nsu.ziabkin.variants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadPrimeCheckerTest {

    @Test
    public void testArrayWithOnlyPrimes() throws InterruptedException {
        int[] arr = {2, 3, 5, 7, 11, 13, 2147483647};
        Assertions.assertFalse(ThreadPrimeChecker.hasNonPrime(arr, 4));
    }

    @Test
    public void testArrayWithOneNonPrime() throws InterruptedException {
        int[] arr = {2, 3, 5, 8, 11};
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 2));
    }

    @Test
    public void testArrayWithNonPrimeAtStart() throws InterruptedException {
        int[] arr = {4, 2, 3, 5, 7};
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 8));
    }

    @Test
    public void testArrayWithNonPrimeAtEnd() throws InterruptedException {
        int[] arr = {2, 3, 5, 7, 9};
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 3));
    }

    @Test
    public void testEmptyArray() throws InterruptedException {
        int[] arr = {};
        Assertions.assertFalse(ThreadPrimeChecker.hasNonPrime(arr, 1));
    }

    @Test
    public void testLargeArrayWithNonPrime() throws InterruptedException {
        int[] arr = new int[1000000];
        java.util.Arrays.fill(arr, 2147483647);
        arr[999999] = 10;
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 10));
    }

    @Test
    public void testSingleThread() throws InterruptedException {
        int[] arr = {2, 3, 4, 5};
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 1));
    }

    @Test
    public void testArrayWithSpecialNumbers() throws InterruptedException {
        int[] arr = {2, 3, 1, 0, -5};
        Assertions.assertTrue(ThreadPrimeChecker.hasNonPrime(arr, 4));
    }
}