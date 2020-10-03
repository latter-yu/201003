import java.util.Arrays;

public class Main {
    static class MregeSortByLoop {
        public static void byLoop(int[] array) {
            // gap 表示当前每个组中的元素个数
            for (int gap = 1; gap < array.length; gap *= 2) {
                for (int i = 0; i < array.length; i += 2*gap) {
                    //每进行一遍循环体，相当于把两个长度为 gap 的数组合并
                    // [i, i + gap) [i + gap, i + 2*gap)
                    int left = i;
                    int mid = i +gap;
                    int right = i + 2*gap;
                    if (mid > array.length) {
                        mid = array.length;
                    }
                    if (right > array.length) {
                        right = array.length;
                    }
                    merge(array, left, mid, right);
                }
            }
        }
        private static void merge(int[] array, int left, int mid, int right) {
            int cur1 = left;
            int cur2 = mid;
            //临时空间需要容纳下两个数组 数量之和
            int[] output = new int[right - left];
            int outputIndex = 0;
            // 当前 output 中被插入了几个元素

            while (cur1 < mid && cur2 < right) {
                if (array[cur1] <= array[cur2]) {
                    // 如果是 < ，无法保证稳定性
                    // 把 cur1 位置的元素插入到 output 中
                    output[outputIndex] = array[cur1];
                    cur1++;
                    outputIndex++;
                }else {
                    output[outputIndex] = array[cur2];
                    cur2++;
                    outputIndex++;
                }
            }
            while (cur1 < mid) {
                output[outputIndex] = array[cur1];
                cur1++;
                outputIndex++;
            }
            while (cur2 < right) {
                output[outputIndex] = array[cur2];
                cur2++;
                outputIndex++;
            }
            //把数据从临时空间中拷贝回原来的数组中
            for (int i = 0; i < right - left; i++) {
                array[left + i] = output[i];
            }
        }

        public static void main(String[] args) {
            int[] array = {4, 9, 1, 5, 6, 3, 8, 2};
            byLoop(array);
            System.out.println(Arrays.toString(array));
        }
    }

    static class SeclctShell {
        //选择排序(循环找最小值放在前面（升序排序为例）)
        //时间复杂度：O(N^2) 空间复杂度：O(1)
        //稳定性：不稳定排序
        public static void seclctShell(int[] array) {
            for (int bound = 0; bound < array.length; bound++) {
                //此时数组区间为：
                //已排序：[0, bound) 未排序：[bound, size)
                for (int cur = bound; cur < array.length; cur++) {
                    if(array[cur] < array[bound]) {
                        //找最小值
                        swap(array, cur, bound);
                    }
                }
            }
        }
        public static void swap(int[] array, int i, int j) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        public static void main(String[] args) {
            int[] array = {4, 7, 9, 1, 5, 6, 3, 8};
            seclctShell(array);
            System.out.println(Arrays.toString(array));
        }
    }

    static class ShellSort {
        //希尔排序：
        //若合理选择 gap 序列，希尔排序的时间复杂度可以达到O(N^1.3) （最高效）
        //实现 希尔排序 时，使用希尔序列 O(N^2):size/2, size/4, size/8 ... 1
        //gap = 1 时，希尔排序代码 = 直接插入排序代码
        public static void shellSort(int[] array) {
            int gap = array.length / 2;
            while(gap > 1) {
                insertSortGap(array, gap);
                gap = gap / 2;
            }
            //当 gap = 1 时，最终插排一次
            insertSortGap(array, 1);
        }
        private static void insertSortGap(int[] array, int gap) {
            //从每组下标为 1 的元素开始
            for (int bound = gap; bound < array.length; bound++) {
                int tmp = array[bound];
                int cur = bound - gap;
                // bound 位置中相邻的前一个元素下标
                for (; cur >= 0 ; cur -= gap) {
                    //分组情况下，同组的相邻元素下标差 gap
                    // 把 cur 位置的元素搬到 cur +gap 位置
                    if (array[cur] > tmp) {
                        array[cur + gap] = array[cur];
                    } else {
                        break;
                    }
                }
                array[cur + gap] = tmp;
            }
        }

        public static void main(String[] args) {
            int[] array = {5, 7, 2, 9, 4, 3, 6, 8};
            shellSort(array);
            System.out.println(Arrays.toString(array));
        }
    }
}
