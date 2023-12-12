package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] test = new int[]{1, 2, 3, 3, 2, 3};
        System.out.println(minValue(test));

        List<Integer> testList = Arrays.stream(test).boxed().collect(Collectors.toList());
        System.out.println(oddOrEven(testList));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        boolean oddOrEvenSum = integers.stream()
                .reduce(0, Integer::sum) % 2 == 0;
        return integers.stream()
                .filter(x -> oddOrEvenSum && x % 2 == 0 || !oddOrEvenSum && x % 2 != 0)
                .collect(Collectors.toList());
    }
}
