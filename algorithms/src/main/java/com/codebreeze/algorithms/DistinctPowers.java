package com.codebreeze.algorithms;

import com.codebreeze.algorithms.Math2.Exponent;

import java.util.*;

import static com.codebreeze.algorithms.Math2.findPerfectPower;
import static java.lang.Math.log10;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

/*
Distinct powers
Problem 29
Consider all integer combinations of ab for 2 ≤ a ≤ 5 and 2 ≤ b ≤ 5:

2^2=4, 2^3=8, 2^4=16, 2^5=32
3^2=9, 3^3=27, 3^4=81, 3^5=243
4^2=16, 4^3=64, 4^4=256, 4^5=1024
5^2=25, 5^3=125, 5^4=625, 5^5=3125
If they are then placed in numerical order, with any repeats removed, we get the following sequence of 15 distinct terms:

4, 8, 9, 16, 25, 27, 32, 64, 81, 125, 243, 256, 625, 1024, 3125

How many distinct terms are in the sequence generated by ab for 2 ≤ a ≤ 100 and 2 ≤ b ≤ 100?
 */
public class DistinctPowers
{

    /*
    we can create buckets of pairs
    remember that (a^b)^c = a^(b*c), so our job is to find factors of the power
    so for x^y, find all factors of y, such that you can find y = a*b, and then if a is less than max
    and greater than min, then this is a dupe. Count how many dupes you find
    Then, count how many total combinations you have, and subtract them from them.
     */
    public static long calculateBrute(final long min, final long max)
    {
        final Map<Long, List<List<Long>>> results = new HashMap<>();
        final Set<List<Long>> pairs = new HashSet<>();
        for(long i = min; i <= max; i++)
        {
            for(long j = min; j <= max; j++)
            {
                final long result = Power.apply(i, j);
                if(results.keySet().contains(result))
                {
                    results.get(result).add(asList(i, j));
                }
                else
                {
                    final List<List<Long>> values = new ArrayList<>(2);
                    pairs.add(asList(i, j));
                    values.add(asList(i, j));
                    results.put(result, values);
                }
            }
        }
        results.entrySet()
               .stream()
//               .filter( entry -> entry.getValue().size() > 1)
               .forEach(System.out::println);
        System.out.println(pairs);
        return results.size();
    }

    /*
the reason this works is that the log should neutralize any 'power' arrangement within the numbers. for example
2^6 = 4^3, but how would you know without calculating them? use the logs
 log (2^6) = (log 4^3)
 i.e.
  6 * log(2) = 3 * log(4). This works for very large numbers because the log is very slow function
     */
    public static long calculate3(final long min, final long max)
    {
        final Set<Double> results = new HashSet<>();
        for(long i = min; i <= max; i++)
        {
            for(long j = min; j <= max; j++)
            {
                //note, this value is just log_2(i)
                final double e = j *  log10(i) / log10(2);
//                System.out.println(String.format("handling [%d, %d] => [%f]", i, j, e));
                results.add(e);
            }
        }
        return results.size();
    }




    /*
    what we are trying to do here is to simplify every number to its base a^b, by finding out if a is perfect power,
    then finding the smallest base and updating the power accordingly, this way we can identify duplicates. For example:
    9^2 = (3^2)^2 = 3 ^ 4
    8^2 = (2^3)^2 = 2 ^ 6
    4^3 = (2^2)^3 = 2 ^ 6
    you can see that the last two are duplicates, while we did not know that earlier!
     */
    public static long calculate(final long min, final long max)
    {
        final Map<Long, Set<Long>> values = new HashMap<>();
        for(long i = min; i <= max; i++)
        {
            final Exponent perfectPower = findPerfectPower(i);
            if (perfectPower == null) // non perfect powers add unique combinations, so just add them
            {
                for (long j = min; j <= max; j++)
                {
                    if(values.get(i) == null)
                    {
                        values.put(i, new HashSet<>(singleton(j)));
                    }
                    else
                    {
                        values.get(i).add(j);
                    }
                }
            }
            else
            {
                System.out.println(String.format("%d is perfect power, with powers %s", i, perfectPower));
                for (long j = min; j <= max; j++)
                {
                    if(values.get(perfectPower.base) == null)
                    {
                        values.put(perfectPower.base, new HashSet<>(singleton(j * perfectPower.power)));
                    }
                    else
                    {
                        values.get(perfectPower.base).add(j * perfectPower.power);
                    }
                }
            }
        }
        return values.values() // sets of distinct powers associated with each basic base
                     .stream()
                     .mapToInt(set -> set.size()) //count those distinct powers
                     .sum(); // sum those counts
    }
}
