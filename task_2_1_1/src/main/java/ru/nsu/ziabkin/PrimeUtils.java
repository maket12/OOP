package ru.nsu.ziabkin;

/**
 * Utils to work with prime numbers.
 */
public class PrimeUtils {
    /**
     * The method checks the integer number primary.
     * @param n a number to check
     * @return true if the number is prime and false otherwise
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return true;
        }
        if (n <= 3) {
            return false;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return true;
        }

        int limit = (int) Math.sqrt(n);

        for (int i = 5; i <= limit; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return true;
            }
        }
        return false;
    }
}