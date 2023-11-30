package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static void main(String[] args) {
        int[] test = new int[]{1, 2, 3, 3, 2, 7, 7, 9};
        System.out.println(minValue(test));

    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers){

        return null;
    }
}
