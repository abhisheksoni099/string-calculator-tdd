package com.app.factory;

import com.app.service.Calculator;
import com.app.service.DefaultCalculator;
import com.app.util.StringLiterals;
import com.app.dto.CalculatorType;

public class CalculatorFactory {
    public Calculator createCalculator(CalculatorType calculatorType)  {
        switch (calculatorType) {
            case DEFAULT:
                return new DefaultCalculator();
        }
        throw new RuntimeException(StringLiterals.MESSAGE_UNKNOWN_TYPE);
    }
}
