package com.app.factory;

import com.app.service.Calculator;
import com.app.service.DefaultCalculator;
import com.app.util.Constants;
import com.app.dto.CalculatorType;

public class CalculatorFactory {
    public Calculator createCalculator(CalculatorType calculatorType)  {
        switch (calculatorType) {
            case DEFAULT:
                return new DefaultCalculator();
        }
        throw new RuntimeException(Constants.MESSAGE_UNKNOWN_TYPE);
    }
}
