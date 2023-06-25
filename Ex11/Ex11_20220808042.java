import java.util.*;

public class Ex11_20220808042 {
    public static void main(String[] args) {

    }


    public static int numOfTriplets(int[] arr, int sum){
        bubbleSort(arr);
        int count = 0;
        int n = arr.length;

        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int tripletSum = arr[i] + arr[left] + arr[right];
                if (tripletSum < sum) {
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }

        return count;
    }

    public static void bubbleSort(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static int kthSmallest(int[] arr,int k){
        mergeSort(arr,0,arr.length-1);
        return arr[k];
    }

    public static void merging(int arr[], int l, int m, int r){

        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i){
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j){
            R[j] = arr[m + 1 + j];
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int arr[], int l, int r){
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merging(arr, l, m, r);
        }
    }

    public static String subSequence(String str){
        if (str == null || str.length() <= 1) {
            System.out.println("O(1)");
            return str;
        }

        String result = str.substring(0, 1);

        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) < str.charAt(i + 1)) {
                result += str.charAt(i + 1);
            }
        }

        System.out.println("O(n)");
        return result;
    }

    public static int isSubString(String str1,String str2){
        int str1Index = 0;
        int str2Index = 0;

        while (str1Index < str1.length() && str2Index < str2.length()) {
            if (str1.charAt(str1Index) == str2.charAt(str2Index)) {
                str1Index++;
                str2Index++;
            } else {
                str1Index = str1Index - str2Index + 1;
                str2Index = 0;
            }
        }
        if (str2Index == str2.length()) {
            return str1Index - str2Index;
        } else {
            return -1;
        }
    }

    public static void findRepeats(int [] arr,int n) {
        bubbleSort(arr);
        Map<Integer, Integer> mapCounter = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int count = mapCounter.getOrDefault(arr[i], 0);
            mapCounter.put(arr[i], count + 1);
        }

        System.out.println("Elements repeated more times than" + n + " are: ");

        for (Map.Entry<Integer, Integer> entry : mapCounter.entrySet()) {
            if (entry.getValue() > n) {
                System.out.println(entry.getKey());
            }
        }
    }


}