package com.cpsc544.bubblesort.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {

    private List<String> errorMessages;

    public ValidationService() {
        errorMessages = new ArrayList<>();
    }

    public boolean validate(int[] inputs, ValidationRule rule) {
        errorMessages.clear();
        for (int input : inputs) {
            if (!rule.getValidationExpress().validate(input)) {
                String errorMessage = rule.getErrorMessage();
                if (!errorMessages.contains(errorMessage)) {
                    errorMessages.add(errorMessage);
                }
            }
        }
        return errorMessages.size() == 0;
    }

    public boolean validate(int[] inputs, List<ValidationRule> rules) {
        errorMessages.clear();
        for (int input : inputs) {
            for (ValidationRule rule : rules) {
                if (!rule.getValidationExpress().validate(input)) {
                    String errorMessage = rule.getErrorMessage();
                    if (!errorMessages.contains(errorMessage)) {
                        errorMessages.add(errorMessage);
                    }
                }
            }
        }
        return errorMessages.size() == 0;
    }

    public boolean validate(int input, ValidationRule rule) {
        errorMessages.clear();
        if (!rule.getValidationExpress().validate(input)) {
            errorMessages.add(rule.getErrorMessage());
        }
        return errorMessages.size() == 0;
    }

    public boolean validate(int input, List<ValidationRule> rules) {
        errorMessages.clear();
        for (ValidationRule rule : rules) {
            if (!rule.getValidationExpress().validate(input)) {
                errorMessages.add(rule.getErrorMessage());
            }
        }
        return errorMessages.size() == 0;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
