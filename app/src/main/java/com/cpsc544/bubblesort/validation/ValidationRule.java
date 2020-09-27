package com.cpsc544.bubblesort.validation;

public class ValidationRule {

    private ValidationExpress _validationExpress;
    private String _errorMessage;

    public ValidationRule(ValidationExpress validationExpress, String errorMessage) {
        _validationExpress = validationExpress;
        _errorMessage = errorMessage;
    }

    public ValidationExpress getValidationExpress() {
        return _validationExpress;
    }

    public String getErrorMessage() {
        return _errorMessage;
    }
}
