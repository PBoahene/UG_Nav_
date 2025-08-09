package com.ug.javafx;

import java.util.*;

public class Sorter {

    public static void mergeSort(List<Route> routes) {
        if (routes.size() <= 1) return;

        int mid = routes.size() / 2;
        List<Route> left = new ArrayList<>(routes.subList(0, mid));
        List<Route> right = new ArrayList<>(routes.subList(mid, routes.size()));

        mergeSort(left);
        mergeSort(right);
        merge(routes, left, right);
    }

    private static void merge(List<Route> list, List<Route> left, List<Route> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).distance <= right.get(j).distance) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) list.set(k++, left.get(i++));
        while (j < right.size()) list.set(k++, right.get(j++));
    }
}
// This class provides a static method to sort a list of Route objects using the merge sort algorithm.
// It sorts the routes based on their distance in ascending order.