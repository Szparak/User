package com.pernal.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCalculatorTest {

    @Test
    public void shouldCalculate(){
        double calculate = UserCalculator.calculate(12, 2);

        assertEquals(2, calculate);
    }

    @Test
    public void shouldThrowOnDivideByZero(){
        assertThrows(IllegalArgumentException.class, () -> UserCalculator.calculate(0, 2));
    }
}
