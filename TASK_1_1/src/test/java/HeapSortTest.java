import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void emptyArrayTest() {
        int[] arr = {};
        HeapSort.heapSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void singleElementTest() {
        int[] arr = {42};
        HeapSort.heapSort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void sortedArrayTest() {
        int[] arr = {1, 2, 3, 4, 5};
        HeapSort.heapSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void reverseArrayTest() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort.heapSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void handlesDuplicatesAndNegatives() {
        int[] arr = {3, -1, 3, 0, -1, 2};
        int[] expected = arr.clone();
        Arrays.sort(expected);
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void randomStressTest() {
        Random rnd = new Random(123);
        int[] arr = rnd.ints(1000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }
}
