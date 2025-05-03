package boki.tobyspring;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {

    public static void main(String[] args) {
        // List<Integer> scores = Arrays.asList(5, 7, 1, 9, 2, 8);
        // scores.sort();

        List<String> scores = Arrays.asList("z", "x", "spring", "java");
        scores.sort((s1, s2) -> s2.length() - s1.length());

        scores.forEach(System.out::println);
    }
}
