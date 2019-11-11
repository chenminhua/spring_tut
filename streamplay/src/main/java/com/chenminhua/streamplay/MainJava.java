package com.chenminhua.streamplay;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MainJava {

    public static void main(String[] args) {
        RandomStream integers = new RandomStream();
        integers.stream().limit(10).forEach(System.out::println);

    }
}

class RandomStream implements Iterable<Integer> {

    public Stream<Integer> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Iterator<Integer> iterator() {
        final Random random = new Random();
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return random.nextInt();
            }
        };
    }
}

