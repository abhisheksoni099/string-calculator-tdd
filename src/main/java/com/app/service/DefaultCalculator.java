package com.app.service;

import com.app.dto.NumberInput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import static com.app.util.Constants.*;

public class DefaultCalculator implements Calculator {
    public int add(String input) {
        validateUserInput(input);
        return addNumbers(input);
    }

    private void validateUserInput(String input) {
        if (input == null) {
            throw new RuntimeException(MESSAGE_INVALID_INPUT_NULL);
        }
        String delimiter = determineDelimiter(input);
        input = determineEffectiveInput(input, delimiter);
        for (int index = 0; index < input.length() - 1; index++) {
            if (delimiter.equals(String.valueOf(input.charAt(index))) && input.charAt(index + 1) == '\n') {
                throw new RuntimeException(MESSAGE_INVALID_INPUT_DELIMITER);
            }
        }
    }

    private String determineEffectiveInput(String input, String delimiter) {
        if (input.startsWith(String.format("%s%s", CUSTOM_DELIMITER_IDENTIFIER, CUSTOM_DELIMITER_MULTI_LENGTH_START_IDENTIFIER))) {
            int inputLengthToSkip = 5 + delimiter.length();
            input = input.substring(inputLengthToSkip);
        } else if (input.startsWith(CUSTOM_DELIMITER_IDENTIFIER)) {
            input = input.substring(4);
        }
        return input;
    }

    private int addNumbers(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        List<Integer> numbers = parseInputData(input);
        validateInputNumbers(numbers);
        return calculateSum(numbers);
    }

    private List<Integer> parseInputData(String input) {
        String delimiter = determineDelimiter(input);
        input = determineEffectiveInput(input, delimiter);
        List<Integer> numbers = new ArrayList<>();
        StringTokenizer stringTokenizerOuter = new StringTokenizer(input, delimiter);
        while (stringTokenizerOuter.hasMoreTokens()) {
            String inputSection = stringTokenizerOuter.nextToken();
            StringTokenizer stringTokenizerInner = new StringTokenizer(inputSection, DELIMITER_NEXT_LINE);
            while (stringTokenizerInner.hasMoreTokens()) {
                numbers.add(Integer.parseInt(stringTokenizerInner.nextToken()));
            }
        }
        return numbers;
    }

    private void validateInputNumbers(List<Integer> numbers) {
        List<Integer> negativeNumbers = new ArrayList<>();
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            int number = iterator.next();
            if (number < 0) {
                negativeNumbers.add(number);
            } else if (number > INPUT_NUMBER_MAX_LIMIT) {
                iterator.remove();
            }
        }
        if (negativeNumbers.size() > 0) {
            throw new RuntimeException(String.format("%s: %s", MESSAGE_INVALID_INPUT_NEGATIVE_NUMBER, negativeNumbers));
        }
    }

    private String determineDelimiter(String input) {
        String delimiter;
        if (input.startsWith(CUSTOM_DELIMITER_IDENTIFIER)) {
            if (input.charAt(2) == CUSTOM_DELIMITER_MULTI_LENGTH_START_IDENTIFIER.charAt(0)) {
                delimiter = determineMultiLengthDelimiter(input);
            } else {
                delimiter = String.valueOf(input.charAt(2));
            }
        } else {
            delimiter = ",";
        }
        return delimiter;
    }

    private String determineMultiLengthDelimiter(String input) {
        StringBuilder delimiter = new StringBuilder();
        char delimiterCharacter = input.charAt(3);
        int index = 4;
        char currentCharacter;
        do {
            currentCharacter = input.charAt(index++);
            delimiter.append(delimiterCharacter);
        } while (currentCharacter != CUSTOM_DELIMITER_MULTI_LENGTH_END_IDENTIFIER.charAt(0));
        return delimiter.toString();
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
        StringTokenizer stringTokenizer = new StringTokenizer(input, DEFAULT_DELIMITER);
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
