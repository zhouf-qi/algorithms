package com.algorithm.sort;

import java.util.Arrays;

/**
 * 排序算法
 * 稳定性：
 * 内排序/外排序：
 * 时间复杂度/空间复杂度
 * <p>
 * 10大排序算法
 * 比较类排序：通过比较来决定元素的相对顺序，由于其时间复杂度不能突破O(nlogn)，因此也称为非线性时间比较类排序
 * 非比较类排序：不通过比较来决定元素间的相对顺序，他可以突破比较排序的时间下限，以线性时间运行，因此也称为线性时间非比较类排序
 * 只使用元素间比较的任何排序算法在最坏情况下需要O(NlogN)次比较
 * <p>
 * 比较类排序
 * - 交换排序
 * -- 冒泡排序
 * -- 快速排序
 * - 插入排序
 * -- 简单插入排序
 * -- 希尔排序
 * - 选择排序
 * -- 简单选择排序
 * -- 堆排序
 * - 归并排序
 * -- 二路归并排序
 * -- 多路归并排序
 * <p>
 * 非比较类排序
 * - 基数排序
 * - 桶排序
 * - 计数排序
 */
public class Sort {

    public static void main(String[] args) {
        String[] arr = new String[]{"bb", "aa", "ac", "ad", "ed", "g", "e", "z", "xx", "xt", "xy", "c", "f", "d"};
//        bubbleSort(arr);
//        insertSort(arr);
//        shellSort(arr);
//        [aa, ac, ad, bb, c, d, e, ed, f, g, xt, xx, xy, z]
//        quickSort(arr, 0, arr.length - 1);
        int k = 4;
        quickSelect(arr, 0, args.length -1, k);
        System.out.println(arr[k]);
    }

    public static <T extends Comparable<? super T>> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 冒泡排序，两两比较如果第一个值大于第二个值,则交换；一层循环能将最大值移到末尾
     * 稳定/空间复杂度O(1)/时间复杂度最坏O(N2)，最好O(N) 通过swapped来判断外层循环是否需要继续，
     * 如果是有序数组外层循环进行一次后就可以跳出
     */
    public static <T extends Comparable<? super T>> void bubbleSort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * 外层N-1趟(1~N-1)，对于p=n,对于0~n-1均为有序
     * 比较tmp和0~p-1 比tmp大的元素右移，直至将tmp放到合适的位置
     */
    public static <T extends Comparable<? super T>> void insertSort(T[] arr) {
        int j;
        for (int i = 1; i < arr.length; i++) {
            T tmp = arr[i];
            for (j = i; j > 0 && tmp.compareTo(arr[j - 1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    /**
     * 希尔排序(缩减增量排序)
     * 增量gap
     * 增量序列arr[i],arr[i+gap]....arr[]
     * for p = gap~n 比较 p和p-gap  N3/2
     */
    public static <T extends Comparable<? super T>> void shellSort(T[] arr) {
        int j;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                T tmp = arr[i];
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
        }
    }

    /**
     * 1、如果S中元素足够下时，直接排序，可选择插入排序；不再拆分
     * 2、取枢纽元pivot 三数中值分割法 将
     * 3、按照小于pivot和大于pivot，将S划分为两个不相交的子集合S1、S2 i,j位置相错时停止，重复元素停止
     * 4、返回quickSort(S1)后跟pivot，继而返回quickSort(S2)
     * <p>
     * 1、判断是否需要继续拆分
     * 2、选择枢纽元 三数中值分割法
     * 3、分割策略
     * -- 3.1、将pivot和最右元素交换，离开要分割的数据端
     * -- 3.2、i = left,j = right -1; i<pivot向右滑动,j>pivot向左滑动；停止时比较i,j是否交错，否则互换
     * -- 3.3、交错时i>=pivot 将i与pivot所在位置交换 这样p<i的均为小元素，p<i的均为小元素，p>i的均为大元素
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] arr, int left, int right) {
        if (left + 2 <= right) {
            T pivot = median3(arr, left, right);
            int i = left;
            int j = right - 1;
            for (; ; ) {
                while (arr[++i].compareTo(pivot) < 0) {
                }
                while (arr[--j].compareTo(pivot) > 0) {
                }
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    break;
                }
            }
            swap(arr, i, right - 1);
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        } else {
            insertionSort(arr, left, right);
        }
    }

    public static <T extends Comparable<? super T>> void quickSelect(T[] arr, int left, int right, int k) {
        if (left + 2 < right) {
            T pivot = median3(arr, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (arr[++i].compareTo(pivot) < 0) {
                }
                while (arr[--j].compareTo(pivot) > 0) {
                }
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    break;
                }
            }
            swap(arr, i, right - 1);
            if (i >= k) {
                quickSort(arr, left, i - 1);
            } else if (k > i + 1) {
                quickSort(arr, i+1, right);
            }
            /**
             * arr[k-1]
             * k = i+1
             * k = arr.length - k
             */
        } else {
            insertionSort(arr, left, right);
        }
    }

    private static <T extends Comparable<? super T>> T median3(T[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[mid].compareTo(arr[left]) < 0) {
            swap(arr, left, mid);
        }
        if (arr[right].compareTo(arr[left]) < 0) {
            swap(arr, left, right);
        }
        if (arr[right].compareTo(arr[mid]) < 0) {
            swap(arr, mid, right);
        }
        swap(arr, mid, right - 1);
        return arr[right - 1];
    }

    private static <T extends Comparable<? super T>> void insertionSort(T[] arr, int left, int right) {
        int j;
        for (int i = left + 1; i <= right; i++) {
            T tmp = arr[i];
            for (j = i; j > left && tmp.compareTo(arr[j - 1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

}

/**
 * 深度为d的二叉树最多有2^d个叶子
 * 有L个叶子的二叉树深度至少是logL
 * n个数排序的可能性即叶节点个数 n!
 * log(n!) ~ O(NlogN)
 * <p>
 * 基于比较的排序算法最坏情况需要比较 O(NlogN)次
 * <p>
 * 前n个大值 选择排序
 * 第n个大值，快速选择
 */
