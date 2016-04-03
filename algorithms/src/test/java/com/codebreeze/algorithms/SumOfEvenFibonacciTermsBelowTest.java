package com.codebreeze.algorithms;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Even Fibonacci numbers
 Problem 2
 Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will be:

 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...

 By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms.
 */
public class SumOfEvenFibonacciTermsBelowTest {
    @Test
    public void examples(){
        //1, 2, 3, 5, 8 (sum evens) = 2 + 8 = 10
        assertThat(SumOfEvenFibonacciTermsBelow.calculate(10)).isEqualTo(10);
        assertThat(SumOfEvenFibonacciTermsBelow.calculate(0)).isEqualTo(0);
        assertThat(SumOfEvenFibonacciTermsBelow.calculate(1)).isEqualTo(0);
        assertThat(SumOfEvenFibonacciTermsBelow.calculate(2)).isEqualTo(0);
        assertThat(SumOfEvenFibonacciTermsBelow.calculate(4000000)).isEqualTo(4613732);
    }
}
