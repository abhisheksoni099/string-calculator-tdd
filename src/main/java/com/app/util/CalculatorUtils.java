package com.app.util;

import com.app.dto.CalculatorType;
import com.app.service.Calculator;
import com.app.factory.CalculatorFactory;

public class CalculatorUtils {
    public static Calculator createCalculator(CalculatorType calculatorType) {
        CalculatorFactory calculatorFactory = new CalculatorFactory();
        return calculatorFactory.createCalculator(calculatorType);
    }
}
