/**
 *
 * Reference: Java heap code adapted from http://www.javacodex.com/Sorting/Heap-Sort
 *
 */

public class HeapSort implements SortInterface {
    private int count = 0;
    private long time = 0;

    @Override
    public void recursiveSort(int[] list) {
        long startTime = System.currentTimeMillis();
        buildHeapRecursive(list);
        recursiveSort(list, list.length-1);
        time = System.currentTimeMillis() - startTime;
    }

    @Override
    public void iterativeSort(int[] list) {
        long startTime = System.currentTimeMillis();
        int N = list.length-1;
        buildHeapIterative(list);
        for (int i = N; i > 0; i--) {
            exchange(list,0, i);
            --N;
            maxHeapIterative(list, N);
        }
        time = System.currentTimeMillis() - startTime;
    }

    private void recursiveSort(int[] list, int N) {
        if(N > 0) {
            exchange(list, 0, N);
            maxHeapRecursive(list, 0, --N);
            recursiveSort(list, N);
        }
    }

    private void buildHeapRecursive(int list[]) {
        for (int i = (list.length-1)/2; i >= 0; i--)
            maxHeapRecursive(list, i, list.length-1);
    }

    private void maxHeapRecursive(int arr[], int i, int N) {
        ++count;
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && arr[left] > arr[i])
            max = left;
        if (right <= N && arr[right] > arr[max])
            max = right;
        if (max != i) {
            exchange(arr, i, max);
            maxHeapRecursive(arr, max, N);
        }
    }

    private void buildHeapIterative(int list[]) {
        for (int i = (list.length-1)/2; i >= 0; i--)
            maxHeapIterative(list, list.length-1);
    }

    private void maxHeapIterative(int arr[], int N) {
        for(int i = 0; i < N; i++) {
            ++count;
            int left = 2*i ;
            int right = 2*i + 1;
            int max = i;
            if (left <= N && arr[left] > arr[i])
                max = left;
            if (right <= N && arr[right] > arr[max])
                max = right;
            if (max != i) {
                exchange(arr, i, max);
            }
        }
    }

    private static void exchange(int list[], int i, int j) {
        int tmp = list[i];
        list[i] = list[j];
        list[j] = tmp;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    void reset() {
        this.time = 0;
        this.count = 0;
    }

}//end HeapSort
