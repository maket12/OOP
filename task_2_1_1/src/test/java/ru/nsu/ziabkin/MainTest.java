package ru.nsu.ziabkin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.nsu.ziabkin.variants.SequentialPrimeChecker;
import ru.nsu.ziabkin.variants.StreamPrimeChecker;
import ru.nsu.ziabkin.variants.ThreadPrimeChecker;

/**
 * Integration test of main class.
 */
public class MainTest {
    @Test
    public void testMainMethodExecution() {
        Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    public void testConsistencyBetweenVariants() throws InterruptedException {
        int[] data = {2, 3, 5, 7, 11, 13, 17, 19, 23, 24};

        boolean seqRes = SequentialPrimeChecker.hasNonPrime(data);
        boolean threadRes = ThreadPrimeChecker.hasNonPrime(data, 4);
        boolean streamRes = StreamPrimeChecker.hasNonPrime(data);

        Assertions.assertEquals(seqRes, threadRes);
        Assertions.assertEquals(seqRes, streamRes);
        Assertions.assertTrue(seqRes);
    }

    @Test
    public void testAllVariantsWithOnlyPrimes() throws InterruptedException {
        int[] data = {2, 3, 5, 7, 11};

        boolean seqRes = SequentialPrimeChecker.hasNonPrime(data);
        boolean threadRes = ThreadPrimeChecker.hasNonPrime(data, 2);
        boolean streamRes = StreamPrimeChecker.hasNonPrime(data);

        Assertions.assertFalse(seqRes);
        Assertions.assertFalse(threadRes);
        Assertions.assertFalse(streamRes);
    }
}