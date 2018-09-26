package org.solo.skarbnik.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Utils {

    public static <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
