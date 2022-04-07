package com.app.service;

import com.app.dto.NumberInput;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.app.util.StringLiterals.*;

public class DefaultCalculator implements Calculator {
    public int add(String input) {
        validateInput(input);
        return addNumbers(input);
    }

    private void validateInput(String input) {
        if (input == null) {
            throw new RuntimeException(MESSAGE_INVALID_INPUT_NULL);
        }
        for (int index = 0; index < input.length() - 1; index++) {
            if (input.charAt(index) == ',' && input.charAt(index + 1) == '\n') {
                throw new RuntimeException(MESSAGE_INVALID_INPUT_DELIMITER);
            }
        }
    }

    private int addNumbers(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        List<Integer> numberInput = parseInputData(input);
        return calculateSum(numberInput);
    }

    private List<Integer> parseInputData(String input) {
        List<Integer> numbers = new ArrayList<>();
        StringTokenizer stringTokenizerOuter = new StringTokenizer(input, ",");
        while (stringTokenizerOuter.hasMoreTokens()) {
            String inputSection = stringTokenizerOuter.nextToken();
            StringTokenizer stringTokenizerInner = new StringTokenizer(inputSection, "\n");
            while (stringTokenizerInner.hasMoreTokens()) {
                numbers.add(Integer.parseInt(stringTokenizerInner.nextToken()));
            }
        }
        return numbers;
    }

    private int calculateSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum;
    }

    private int calculateSum(NumberInput numberInput) {
        return numberInput.getNumber1() + numberInput.getNumber2();
    }

    private NumberInput parseInputDataForTwoNumbers(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
        NumberInput numberInput = new NumberInput();
        if (stringTokenizer.hasMoreTokens()) {
            numberInput.setNumber1(Integer.parseInt(stringTokenizer.nextToken()));
        }
        if (stringTokenizer.hasMoreTokens()) {
            numberInput.setNumber2(Integer.parseInt(stringTokenizer.nextToken()));
        }
        if (stringTokenizer.hasMoreTokens()) {
            throw new RuntimeException(MESSAGE_INVALID_INPUT_NUMBER_LIMIT_EXCEEDED);
        }
        return numberInput;
    }
}
