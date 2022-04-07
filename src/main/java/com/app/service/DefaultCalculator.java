package com.app.service;

import com.app.dto.NumberInput;
import com.app.util.StringLiterals;

import java.util.StringTokenizer;

public class DefaultCalculator implements Calculator {
    public int add(String input) {
        validateInput(input);
        if (input.isEmpty()) {
            return 0;
        }
        return addNumbers(input);
    }

    private int addNumbers(String input) {
        NumberInput numberInput = parseInputData(input);
        return calculateSum(numberInput);
    }

    private int calculateSum(NumberInput numberInput) {
        return numberInput.getNumber1() + numberInput.getNumber2();
    }

    private void validateInput(String input) {
        if (input == null) {
            throw new RuntimeException(StringLiterals.MESSAGE_INVALID_INPUT_NULL);
        }
    }

    private NumberInput parseInputData(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
        NumberInput numberInput = new NumberInput();
        if (stringTokenizer.hasMoreTokens()) {
            numberInput.setNumber1(Integer.parseInt(stringTokenizer.nextToken()));
        }
        if (stringTokenizer.hasMoreTokens()) {
            numberInput.setNumber2(Integer.parseInt(stringTokenizer.nextToken()));
        }
        return numberInput;
    }
}
