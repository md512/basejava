package ru.javawebinar.basejava;

import java.util.ArrayList;
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
        int sum = integers.stream()
                .reduce(Integer::sum)
                .get();
        return integers.stream()
                .filter(x -> sum % 2 == 0 && x % 2 == 0 || sum % 2 != 0 && x % 2 != 0)
                .collect(Collectors.toList());
    }
}
