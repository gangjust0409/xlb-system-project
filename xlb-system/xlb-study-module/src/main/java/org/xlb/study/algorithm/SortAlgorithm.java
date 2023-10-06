package org.xlb.study.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortAlgorithm {

    //冒泡排序
    public static List<Integer> bubblingSort(List<Integer> list) {
        Integer[] numbers = listToArray(list);
        //比对多少次 i
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - i - 1; j++) { //每次遍历的次数
                int temp;
                if (numbers[j] > numbers[j + 1]) {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        List<Integer> newList = Arrays.asList(numbers);
        return newList;
    }

    //快速排序
    public static List<Integer> quickSort(List<Integer> list){
//        Arrays.sort();
        Integer[] numbers = listToArray(list);


        return Stream.of(numbers).collect(Collectors.toList());
    }

    //将集合转为数组
    private static <T> T[] listToArray(List<?> list){
        Integer[] numbers = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (numbers[i] == null) {
                numbers[i] = (Integer) list.get(i);
            }
        }
        return (T[]) numbers;
    }


}
