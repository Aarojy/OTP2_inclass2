package org.example.otp2_inclass2;

import javafx.fxml.FXML;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BmiControllerTest {

    @Test
    void calculatorTest() {
        assertEquals(27.70, BmiController.calculateBmi(1.90, 100.0), 0.01);
    }

    @Test
    void calculatorTest_False() {
        assertNotEquals(25.00, BmiController.calculateBmi(1.90, 100.0), 0.01);
    }
}