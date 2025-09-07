import java.util.Scanner;


public class HeapSort {
    private static void heapify(int[] arr, int n, int i) {
        if (i == arr.length) {
            return;
        }

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            HeapSort.swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private static void buildHeap(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, arr.length, i);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;

        buildHeap(arr);

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 50; i++) {
            System.out.print("#");
        }

        System.out.println();
        String custom = "####" + "         " + "HeapSort program script" + "          " + "####";
        System.out.println(custom);

        for (int i = 0; i < 50; i++) {
            System.out.print("#");
        }
        System.out.println();

        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.print("Enter array's values: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        heapSort(arr);

        System.out.println("Result of sorting: ");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}