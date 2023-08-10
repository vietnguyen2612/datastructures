import java.util.Arrays;

public class Sort {

    //method sorts input array arr using the insertion sort algorithm
    public static void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){ //iterates through entire array
            if(arr[i] < arr[i-1]){ //compares if the current index is less than the previous index
                int toInsert = arr[i]; // a copy of element i
                int j = i; // j takes on the index i
                while(j > 0 && toInsert < arr[j-1]){ //runs comparison through all elements prior to index i
                    arr[j] = arr[j-1]; //shifts elements over one to the right to make room for insertion
                    j = j-1;
                }
                arr[j] = toInsert; //element at index j is now the open space, inserted with toInsert
            }
        }
    }

    //method sorts input array arr using the quick sort algorithm
    public static void quickSort(int[] arr){
        qSort(arr, 0, arr.length - 1); //uses qSort helper method
    }

    //helper recursive method to sort elements using quicksort algorithm
    private static void qSort(int[] arr, int first, int last){
        if(first >= last){ //if first element is greater or equal to last element in parameters
            return;
        }
        int split = partition(arr, first, last); //split is defined through partitioning the array from partition method

        qSort(arr, first, split-1); //recursively calls qSort for first half, from first till split
        qSort(arr, split+1, last); //recursively calls qSort for second half, from split till end
    }

    //secondary helper method to rearrange the elements, moving elements left and right of a specified pivot
    private static int partition(int[] arr, int first, int last){
        //creates the pivot value that will be used in helper method partitioning the array, end of array
        int pivot = arr[last]; //pivot represents last element of index
        int i = first-1; //index traversing left to pivot

        for(int j = first; j <= last-1; j++){
            if(arr[j] < pivot){ //if element j is less than the pivot
                i++; //increment i

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp; //swaps i with j
            }
        }

        int temp = arr[i+1];
        arr[i+1] = arr[last];
        arr[last] = temp; //swaps following element with last element

        return i+1; //returns partition
    }

    //method sorts input array arr using the merge sort algorithm, wrapper method
    public static void mergeSort(int[] arr){
        int[] temp = new int[arr.length]; //temporary array that will hold values of arr is it is sorted
        mSort(arr, temp, 0, arr.length-1); //calls recursive method mSort to sort elements from start to end of array
    }

    //secondary helper recursive method to sort elements using quicksort algorithm
    private static void mSort(int[] arr, int[] temp, int first, int last){
        if(first >= last){ //base case in which first element equals the last element, array already has been sorted
            return;
        }
        int midpt = (first+last)/2; //midpt the array into two where midpt is the middle index

        mSort(arr, temp, first, midpt); //recursively calls mSort where new left array is half of original array, using midpt as the last element
        mSort(arr, temp, midpt+1, last); //recursively makes right array, using midpt as the beginning of the array until the end of the original array

        merge(arr, temp, first, midpt, midpt+1, last); //implements merge method to combine two subarrays into one and sort them using the merge algorithm

    }

    //helper method merge that effective sorts the array arr by using a temporary array to stuff the elements of two subarrays from least to greatest
    private static void merge(int[] arr, int[] temp, int leftStart, int leftEnd, int rightStart, int rightEnd){
        int i = leftStart; //index into left subarray
        int j = rightStart; //index of right subarray
        int k = leftStart; //index into temp array

        while(i <= leftEnd && j <= rightEnd){ //as long as i is not done traversing through left subarray and j is not done trav for right subarray
            if(arr[i] >= arr[j]){ //if current index in left subarray is more or equal than that of right subarray
                temp[k++] = arr[j++]; //element at index j gets added into temporary array, both indices j and k get incremented
            }
            else{
                temp[k++] = arr[i++]; //element at index i gets added into temporary array, both indices i and k get incremented
            }
        }

        while(i <= leftEnd){ //right subarray was completely run through, now copying all of LEFT subarray
            temp[k++] = arr[i++];
        }
        while(j <= rightEnd){ //left subarray was completely run through, now copying all of RIGHT subarray
            temp[k++] = arr[j++];
        }

        for(int l = leftStart; l <= rightEnd; l++){ //copies all elements back from temp to arr array, having effectively sorted the portion given
            arr[l] = temp[l];
        }
    }

    //returns an array of n random integers in the interval [a,b]
    public static int[] randomArray(int n, int a, int b){
        int[] arr = new int[n]; //instantiate array of size n

        for(int i = 0; i < n; i++){
            arr[i] = (int)((Math.random() * ((b+1)-a) + a)); //puts random number from a to b inclusive into current index i
        }

        return arr;
    }

    //method that sorts array using bubble sort algorithm, EXTRA CREDIT
    public static void bubbleSort(int[] arr){
        for(int i = arr.length-1; i > 0; i--){ //iterates through array from back to front
            for(int j = 0; j < i; j++){ //inner loop iterates from front until it meets i, making a single pass each time i is decremented
                if(arr[j] > arr[j+1]){ //if current element j is greater than following element
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp; //swaps current element j with following element, effectively creating bubble at the end
                }
            }
        }
    }

    //benchmarking for insertion sort
    public int BenchmarkingInsertionSort(int n, int a, int b){
        int[] arr = this.randomArray(n, a, b);
        long time1 = System.nanoTime();
        this.insertionSort(arr);
        long time2 = System.nanoTime();
        return (int)(time2 - time1);
    }

    //benchmarking for quick sort
    public int BenchmarkingQuickSort(int n, int a, int b){
        int[] arr = this.randomArray(n, a, b);
        long time1 = System.nanoTime();
        this.quickSort(arr);
        long time2 = System.nanoTime();
        return (int)(time2 - time1);
    }

    //benchmarking for merge sort
    public int BenchmarkingMergeSort(int n, int a, int b){
        int[] arr = this.randomArray(n, a, b);
        long time1 = System.nanoTime();
        this.mergeSort(arr);
        long time2 = System.nanoTime();
        return (int)(time2 - time1);
    }

    //benchmarking for bubble sort
    public int BenchmarkingBubbleSort(int n, int a, int b){
        int[] arr = this.randomArray(n, a, b);
        long time1 = System.nanoTime();
        this.bubbleSort(arr);
        long time2 = System.nanoTime();
        return (int)(time2 - time1);
    }

    //benchmarking for java sort
    public int BenchmarkingJavaSort(int n, int a, int b){
        int[] arr = this.randomArray(n, a, b);
        long time1 = System.nanoTime();
        Arrays.sort(arr);
        long time2 = System.nanoTime();
        return (int)(time2 - time1);
    }

    //Function to print an array
    private static void printArray(int[] arr) {
        for(int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");

        System.out.println();
    }

    public static void main(String[] args) {

        int[] arr = randomArray(8, 1, 10);
        System.out.println("Given Array of size 8 (using randomArray method)");
        printArray(arr);
        insertionSort(arr);
        System.out.println("Sorted array using insertion sort: ");
        printArray(arr);
        System.out.println();

        int[] arrTwo = randomArray(8, 1, 10);
        System.out.println("Given Array of size 8 (using randomArray method)");
        printArray(arrTwo);
        quickSort(arrTwo);
        System.out.println("Sorted array using quick sort: ");
        printArray(arrTwo);
        System.out.println();

        int arrThree[] = randomArray(8, 1, 10);
        System.out.println("Given Array of size 8 (using randomArray method)");
        printArray(arrThree);
        mergeSort(arrThree);
        System.out.println("Sorted array using merge sort: ");
        printArray(arrThree);
        System.out.println();

        int[] arrFour = randomArray(8, 1, 10);
        System.out.println("Given Array of size 8 (using randomArray method)");
        printArray(arrFour);
        bubbleSort(arrFour);
        System.out.println("Sorted array using bubble sort: ");
        printArray(arrFour);
        System.out.println();


        /*
        Sort sample1 = new Sort();
        System.out.println("Insertion Sort");
        System.out.println(sample1.BenchmarkingInsertionSort(10, 0, 10));
        System.out.println(sample1.BenchmarkingInsertionSort(20, 0, 20));
        System.out.println(sample1.BenchmarkingInsertionSort(50, 0, 50));
        System.out.println(sample1.BenchmarkingInsertionSort(100, 0, 100));
        System.out.println(sample1.BenchmarkingInsertionSort(200, 0, 200));
        System.out.println(sample1.BenchmarkingInsertionSort(500, 0, 500));
        System.out.println(sample1.BenchmarkingInsertionSort(1000, 0, 1000));
        System.out.println(sample1.BenchmarkingInsertionSort(2000, 0, 2000));
        System.out.println(sample1.BenchmarkingInsertionSort(5000, 0, 5000));
        System.out.println();

        System.out.println("Quick Sort");
        System.out.println(sample1.BenchmarkingQuickSort(10, 0, 10));
        System.out.println(sample1.BenchmarkingQuickSort(20, 0, 20));
        System.out.println(sample1.BenchmarkingQuickSort(50, 0, 50));
        System.out.println(sample1.BenchmarkingQuickSort(100, 0, 100));
        System.out.println(sample1.BenchmarkingQuickSort(200, 0, 200));
        System.out.println(sample1.BenchmarkingQuickSort(500, 0, 500));
        System.out.println(sample1.BenchmarkingQuickSort(1000, 0, 1000));
        System.out.println(sample1.BenchmarkingQuickSort(2000, 0, 2000));
        System.out.println(sample1.BenchmarkingQuickSort(5000, 0, 5000));
        System.out.println();

        System.out.println("Merge Sort");
        System.out.println(sample1.BenchmarkingMergeSort(10, 0, 10));
        System.out.println(sample1.BenchmarkingMergeSort(20, 0, 20));
        System.out.println(sample1.BenchmarkingMergeSort(50, 0, 50));
        System.out.println(sample1.BenchmarkingMergeSort(100, 0, 100));
        System.out.println(sample1.BenchmarkingMergeSort(200, 0, 200));
        System.out.println(sample1.BenchmarkingMergeSort(500, 0, 500));
        System.out.println(sample1.BenchmarkingMergeSort(1000, 0, 1000));
        System.out.println(sample1.BenchmarkingMergeSort(2000, 0, 2000));
        System.out.println(sample1.BenchmarkingMergeSort(5000, 0, 5000));
        System.out.println();

        System.out.println("Java's Sort");
        System.out.println(sample1.BenchmarkingJavaSort(10, 0, 10));
        System.out.println(sample1.BenchmarkingJavaSort(20, 0, 20));
        System.out.println(sample1.BenchmarkingJavaSort(50, 0, 50));
        System.out.println(sample1.BenchmarkingJavaSort(100, 0, 100));
        System.out.println(sample1.BenchmarkingJavaSort(200, 0, 200));
        System.out.println(sample1.BenchmarkingJavaSort(500, 0, 500));
        System.out.println(sample1.BenchmarkingJavaSort(1000, 0, 1000));
        System.out.println(sample1.BenchmarkingJavaSort(2000, 0, 2000));
        System.out.println(sample1.BenchmarkingJavaSort(5000, 0, 5000));
        System.out.println();

        System.out.println("Bubble Sort");
        System.out.println(sample1.BenchmarkingBubbleSort(10, 0, 10));
        System.out.println(sample1.BenchmarkingBubbleSort(20, 0, 20));
        System.out.println(sample1.BenchmarkingBubbleSort(50, 0, 50));
        System.out.println(sample1.BenchmarkingBubbleSort(100, 0, 100));
        System.out.println(sample1.BenchmarkingBubbleSort(200, 0, 200));
        System.out.println(sample1.BenchmarkingBubbleSort(500, 0, 500));
        System.out.println(sample1.BenchmarkingBubbleSort(1000, 0, 1000));
        System.out.println(sample1.BenchmarkingBubbleSort(2000, 0, 2000));
        System.out.println(sample1.BenchmarkingBubbleSort(5000, 0, 5000));
         */
    }
}
