package com.app.service

import com.app.dto.CalculatorType

import static com.app.util.CalculatorUtils.createCalculator


class CalculatorTest extends GroovyTestCase {
    private static Calculator calculator;

    static {
        calculator = createCalculator(CalculatorType.DEFAULT);
    }

    void testAddNullInput() {
        shouldFail(RuntimeException) {
            calculator.add(null);
        }
    }

    void testAddEmptyInput() {
        assertEquals(0, calculator.add(""));
    }

    void testAddHandleSingleNumber() {
        assertEquals(1, calculator.add("1"));
    }

    void testAddHandleTwoNumbers() {
        assertEquals(3, calculator.add("2,1"));
    }

    void testAddMoreThanTwoNumbers() {
        assertEquals(7, calculator.add("1,2,4"))
        assertEquals(16, calculator.add("1,2,4,1,8"))
        assertEquals(-1, calculator.add("7,-8"))
    }
}
