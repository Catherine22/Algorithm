package com.catherine.java8_updates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : Catherine
 * @created : 28/06/2021
 * <p>
 * Read more: https://www.youtube.com/watch?v=t1-YZ6bF-g0&ab_channel=JoeJames
 */
public class JavaStreams {
    public static void main(String[] args) {

        // (1) print integer 1-9
        IntStream.range(1, 10).forEach(System.out::print);
        System.out.println();

        // (2) print 6-9
        IntStream.range(1, 10).skip(5).forEach(
                n -> System.out.print(n + " ")
        );
        System.out.println();

        // (3) print the sum of 1-9
        System.out.println(IntStream.range(1, 10).sum());

        // (4) sort and find the first element
        Stream.of("Henry Reid", "Ayan Pearson", "Adrian Wallace")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);

        // (5) sort and find all names that start from "F"
        String[] names = {"Henry Reid", "Ayan Pearson", "Adrian Wallace", "Fletcher Burke", "Francis Robertson", "Riley Wells"};
        Arrays.stream(names)
                .filter(name -> name.startsWith("F"))
                .sorted()
                .forEach(name -> System.out.print(name + " / "));
        System.out.println();

        // (6) the average of squares of an int array
        int[] array = {1, 2, 5, 4, 9, 16, 23, 24, 36};
        Arrays.stream(array)
                .map(num -> num * num)
                .average()
                .ifPresent(System.out::println);

        // (7) print names start from "A" in lowercase
        Arrays.stream(names)
                .filter(name -> name.startsWith("A"))
                .map(name -> name.toLowerCase(Locale.ROOT))
                .forEach(name -> System.out.print(name + " / "));
        System.out.println();

        // (8) print bands that are longer than 10 characters from a txt file
        String BANDS_PATH = "res/bands.txt";
        try {
            Stream<String> bands = Files.lines(Paths.get(BANDS_PATH));
            bands.filter(band -> band.length() > 10)
                    .forEach(band -> System.out.print(band + " / "));
            bands.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        // (9) It's similar to (8), use a list this time
        try {
            List<String> bands2 = Files.lines(Paths.get(BANDS_PATH))
                    .filter(band -> band.length() > 10)
                    .collect(Collectors.toList());
            bands2.forEach(band -> System.out.print(band + " / "));
            bands2.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        // (10) read data.txt file and count the numbers of rows that have 4 elements
        String CVS_PATH = "res/data.txt";
        Stream<String> cvs;
        try {
            cvs = Files.lines(Paths.get(CVS_PATH));
            int rows = (int) cvs.map(row -> row.split(","))
                    .filter(row -> row.length == 3)
                    .count();
            System.out.println(rows);
            cvs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // (11) read data.txt file and print the numbers after the alphabet in each row
        try {
            cvs = Files.lines(Paths.get(CVS_PATH));
            int rows = (int) cvs.map(row -> row.split(","))
                    .filter(row -> row.length == 3)
                    .count();
            System.out.println(rows);
            cvs.close();


            cvs = Files.lines(Paths.get(CVS_PATH));
            cvs.map(row -> row.split(","))
                    .forEach(row -> System.out.printf("%s: %s%n", row[0],
                            Arrays.toString(Arrays.copyOfRange(row, 1, row.length))));


            cvs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // (12) read data.txt file and store the data into a HashMap.
        // Your key should be the alphabet and the value is the first number.
        // Skip rows without numbers.
        try {
            cvs = Files.lines(Paths.get(CVS_PATH));
            Map<String, Integer> hashMap = new HashMap<>();
            cvs.filter(row -> row.length() >= 3)
                    .map(row -> row.split(","))
                    .forEach(row -> hashMap.put(row[0], Integer.parseInt(row[1])));

            hashMap.forEach((key, value) -> System.out.printf("%s: %d%n",
                    key,
                    value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
