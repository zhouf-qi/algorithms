package com.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
 * - 基数排序 -- 又称卡片排序
 * - 桶排序
 * - 计数排序
 */
public class Sort {

    public static void main(String[] args) {
        String[] arr = new String[]{"bb", "aa", "ac", "ad", "ed", "g", "e", "z", "xx", "xt", "xy", "c", "f", "d" };
//        bubbleSort(arr);
//        insertSort(arr);
//        shellSort(arr);
//        quickSort(arr, 0, arr.length - 1);
//        selectSort(arr);
//        heapSort(arr);
//        mergeSort(arr, new String[arr.length], 0, arr.length - 1);
        int[] arr2 = new int[]{1, 3, 1, 4, 5, 2, 6, 8, 9, 11, 2, 3};
//        countSort(arr2);
        bucketSort(arr2, 3);
        System.out.println(Arrays.toString(arr2));
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
     * 2、取枢纽元pivot 三数中值分割法 分割时调整下位置 保证left<mid<right 交换 mid和right-1 要分割的数据left-1~right-2
     * 3、按照小于pivot和大于pivot，将S划分为两个不相交的子集合S1、S2 i,j位置相错时停止，重复元素停止
     * 4、返回quickSort(S1)后跟pivot，继而返回quickSort(S2)
     * <p>
     * 1、判断是否需要继续拆分
     * 2、选择枢纽元 三数中值分割法
     * 3、分割策略
     * -- 3.1、将pivot和最右元素交换，离开要分割的数据端
     * -- 3.2、i = left,j = right -1; i<pivot向右滑动,j>pivot向左滑动；停止时比较i,j是否交错，否则互换
     * -- 3.3、交错时i>=pivot 将i与pivot所在位置交换 这样p<i的均为小元素，p<i的均为小元素，p>i的均为大元素
     * 4、递归 S1 pivot S2
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

    /**
     * 快速选择
     * |S1| = i
     * 如果|S1| >= k，即i>=k, 则说明k在|S1|中，对S1进行递归
     * 如果|S1| < k-1，即i<k-1, 则说明k在|S2|中，对S2进行递归
     * 否则|S1| = k-1 此时第arr[k-1]即为升序第k个元素
     * 复杂度 O(N+klogN)
     */
    public static <T extends Comparable<? super T>> void quickSelect(T[] arr, int left, int right, int k) {
        System.out.println("left = " + left + ", rigth = " + right);
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
            if (k <= i) {
                quickSelect(arr, left, i - 1, k);
            } else if (k > i + 1) {
                quickSelect(arr, i + 1, right, k);
            }
            /**
             * |S1| = i
             * S1 0 ~ i-1
             * pivot i
             * S2 i+1 ~ arr.length -1
             *
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

    /**
     * 选择排序，每次循环选出最小的值放在最前面；
     * 第二次循环从剩下的队列中选出最小的值
     */
    private static <T extends Comparable<? super T>> void selectSort(T[] arr) {
        int min;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    /**
     * 堆排序 完全二叉树：除最后一层，其余层的节点是满的，且最后一层的节点都集中在左边
     * 父节点a[i] 则子节点a[2i+1] a[2i+2]
     * 大顶堆 a[i]>=a[2i+1] && a[i]>=a[2i+2]
     * 构建大顶堆时，从最后一个父节点开始调整 lastParent = arr.length/2 - 1
     * 升序排序
     * 1、将无序区构建为大顶堆a[0]~a[n-1]
     * 2、将堆顶元素与堆尾元素交换，得到新的无序区a[0]~a[n-2]和有序区a[n-1]
     * 3、对无序区调整满足大顶堆
     * 4、递归
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] arr) {
        // 1、从最后一个父节点调整
        for (int i = arr.length - 1; i >= 0; i--) {
            adjustMaxHeap(arr, i, arr.length);
        }
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            adjustMaxHeap(arr, 0, i);
        }
    }

    /**
     * 1、判断子节点 2i+1 2i+2是否存在，获取子节点大值；如果子节点大值大于父节点 则与父节点交换， 比较交换的节点 是否大于其子节点的值
     */
    private static <T extends Comparable<? super T>> void adjustMaxHeap(T[] arr, int i, int length) {
        int child;
        T tmp;
        for (tmp = arr[i]; i * 2 + 1 < length; i = child) {
            child = i * 2 + 1;
            if (child + 1 < length && arr[child].compareTo(arr[child + 1]) < 0) {
                child = child + 1;
            }
            if (arr[child].compareTo(tmp) > 0) {
                arr[i] = arr[child];
            } else {
                break;
            }
        }
        arr[i] = tmp;
    }

    /**
     * 归并排序 适合合并两个有序队列
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, tmp, left, mid);
            mergeSort(arr, tmp, mid + 1, right);
            merge(arr, tmp, left, mid, right);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int leftPos, int leftEnd, int rightEnd) {
        int rightPos = leftEnd + 1;
        int tmpPos = leftPos;
        int len = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (arr[leftPos].compareTo(arr[rightPos]) < 0) {
                tmp[tmpPos++] = arr[leftPos++];
            } else {
                tmp[tmpPos++] = arr[rightPos++];
            }
        }

        while (leftPos <= leftEnd) {
            tmp[tmpPos++] = arr[leftPos++];
        }
        while (rightPos <= rightEnd) {
            tmp[tmpPos++] = arr[rightPos++];
        }

        for (int i = 0; i < len; i++) {
            arr[rightEnd] = tmp[rightEnd--];
        }
    }

    /**
     * 计数排序，将序列元素作为key，统计各个key出现的次数
     */
    public static void countSort(int[] arr) {
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            } else if (arr[i] < min) {
                min = arr[i];
            }
        }

        int[] tmp = new int[max - min + 1];
        Arrays.fill(tmp, 0);

        for (int i = 0; i < arr.length; i++) {
            tmp[arr[i] - min]++;
        }

        int j = 0;
        for (int i = 0; i < tmp.length; i++) {
            while (tmp[i]-- > 0) {
                arr[j++] = i + min;
            }
        }
    }

    /**
     * 桶排序
     * 1、遍历，将序列映射到各个桶中 (要求数据划分比较均匀)
     * 2、各个桶进行排序
     * 3、将各个桶数据合并
     */
    public static void bucketSort(int[] arr, int bucketSize) {
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            } else if (arr[i] < min) {
                min = arr[i];
            }
        }

        int gap = (max - min) / bucketSize + 1;
        List<Integer>[] buckets = new List[bucketSize];
        for (int i = 0; i < bucketSize; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int i = 0; i < arr.length; i++) {
            buckets[(arr[i] - min) / gap].add(arr[i]);
        }

        int pos = 0;
        for (int i = 0; i < bucketSize; i++) {
            buckets[i].sort(Comparator.comparingInt(x -> x));
            System.out.println(buckets[i]);
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[pos++] = buckets[i].get(j);
            }
        }

    }

    /**
     * 需要桶
     * 基数排序：按照低位排序，然后收集；再按照高位排序，然后再收集，继续递推，直至最高位。
     * 有优先级的属性，先排序低优先级的。再排序高优先级的
     * <p>
     * 需要一个同样大小的tmp数组
     */
    public static <T extends Comparable<? super T>> void baseSort(T[] arr, T[] tmp, int left, int right) {
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
