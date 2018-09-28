package org.solo.skarbnik.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {

    public static <T> List<T> toList(Iterable<T> iterable) {
        return toStream(iterable)
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
