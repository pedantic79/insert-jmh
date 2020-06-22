package com.github.pedantic79.insert.jhm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Insert {
  static void insert(int numbers[], List<Integer> input, Blackhole blackhole) {
    for (int i = 0; i < numbers.length; i++) {
      ListIterator<Integer> litr = input.listIterator();
      while (litr.hasNext()) {
        if (litr.next() > numbers[i]) {
          // Since we've gone past the position where we want to insert
          // we have to move back one
          litr.previous();
          break;
        }
      }

      litr.add(numbers[i]);
    }

    // guarantee the optimizer won't consider everything a no-op
    blackhole.consume(input);
  }

  static final int random[] = {
      77, 43, 82, 84, 53, 36, 52, 72, 8,  19, 46, 39, 81, 11, 51, 30, 70,
      24, 49, 60, 37, 98, 95, 18, 59, 54, 40, 44, 86, 5,  12, 35, 99, 88,
      58, 14, 15, 63, 73, 96, 48, 42, 33, 64, 45, 16, 85, 23, 71, 92, 7,
      67, 2,  74, 76, 56, 47, 10, 94, 32, 55, 62, 68, 34, 6,  26, 0,  57,
      69, 22, 93, 78, 89, 1,  91, 75, 61, 80, 65, 83, 97, 27, 50, 25, 66,
      20, 41, 3,  90, 28, 87, 31, 4,  79, 21, 17, 13, 9,  29, 38};

  static final int reverse[] = {
      99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83,
      82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66,
      65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49,
      48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32,
      31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15,
      14, 13, 12, 11, 10, 9,  8,  7,  6,  5,  4,  3,  2,  1,  0};

  static final int ordered[] = {
      0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  10, 11, 12, 13, 14, 15, 16,
      17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
      34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
      51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67,
      68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84,
      85, 86, 86, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99};

  @Benchmark
  public void testRandomArrayList(Blackhole blackhole) {
    insert(random, new ArrayList<Integer>(), blackhole);
  }

  @Benchmark
  public void testRandomLinkedList(Blackhole blackhole) {
    insert(random, new LinkedList<Integer>(), blackhole);
  }

  @Benchmark
  public void testBestArrayList(Blackhole blackhole) {
    insert(reverse, new ArrayList<Integer>(), blackhole);
  }

  @Benchmark
  public void testWorstArrayList(Blackhole blackhole) {
    insert(ordered, new ArrayList<Integer>(), blackhole);
  }

  @Benchmark
  public void testBestLinkedList(Blackhole blackhole) {
    insert(reverse, new LinkedList<Integer>(), blackhole);
  }

  @Benchmark
  public void testWorstLinkedList(Blackhole blackhole) {
    insert(ordered, new LinkedList<Integer>(), blackhole);
  }
}
