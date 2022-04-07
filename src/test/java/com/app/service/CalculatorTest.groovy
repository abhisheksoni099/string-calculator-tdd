package com.app.service

import com.app.dto.CalculatorType

import static com.app.util.CalculatorUtils.createCalculator


class CalculatorTest extends GroovyTestCase {
    private static Calculator calculator

    static {
        calculator = createCalculator(CalculatorType.DEFAULT)
    }

    void testAddNullInput() {
        shouldFail(RuntimeException) {
            calculator.add(null)
        }
    }

    void testAddEmptyInput() {
        assertEquals(0, calculator.add(""))
    }

    void testAddHandleSingleNumber() {
        assertEquals(1, calculator.add("1"))
        assertEquals(10, calculator.add("10"))
    }

    void testAddHandleTwoNumbers() {
        assertEquals(3, calculator.add("2,1"))
        assertEquals(10, calculator.add("5,5"))
    }

    void testAddMoreThanTwoNumbers() {
        assertEquals(7, calculator.add("1,2,4"))
        assertEquals(16, calculator.add("1,2,4,1,8"))
    }

    void testAddHandleNextLineDelimiter() {
        assertEquals(7, calculator.add("1\n2,4"))
        assertEquals(7, calculator.add("1\n2,4"))
        assertEquals(16, calculator.add("1,2\n4\n1,8"))
        assertEquals(6, calculator.add("1,2\n\n3"))
        shouldFail(RuntimeException) {
            assertEquals(3, calculator.add("1,2,\n"))
        }
        shouldFail(RuntimeException) {
            assertEquals(3, calculator.add("1,\n2,\n"))
        }
    }

    void testAddHandleCustomDelimiter() {
        assertEquals(3, calculator.add("1,2"))
        assertEquals(3, calculator.add("//;\n1;2"))
        assertEquals(13, calculator.add("//&\n1&2&10"))
        assertEquals(13, calculator.add("//&\n1\n2&10"))
        shouldFail (RuntimeException) {
            assertEquals(13, calculator.add("//&\n1&\n2&10"))
        }
        shouldFail {
            assertEquals(3, calculator.add("1;2"))
        }
    }

    void testAddHandleNegativeNumbers() {
        shouldFail(RuntimeException) {
            assertEquals(-1, calculator.add("7,-8"))
        }
        shouldFail(RuntimeException) {
            assertEquals(-1, calculator.add("//&\n7&-8"))
        }
        shouldFail(RuntimeException) {
            assertEquals(5, calculator.add("//&\n7&-1&-1"))
        }
    }

    void testAddHandleBiggerNumbers() {
        assertEquals(7, calculator.add("7,1001"))
        assertEquals(8, calculator.add("7,1001,1,2000"))
    }
}
