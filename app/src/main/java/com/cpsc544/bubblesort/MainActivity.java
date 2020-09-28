package com.cpsc544.bubblesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.cpsc544.bubblesort.algorithm.BubbleSort;
import com.cpsc544.bubblesort.algorithm.SortAlgorithm;
import com.cpsc544.bubblesort.animation.AnimationController;
import com.cpsc544.bubblesort.validation.ValidationRule;
import com.cpsc544.bubblesort.validation.ValidationService;

public class MainActivity extends AppCompatActivity {
    private EditText etInput;
    private TextView tvErrorMsg;
    private LinearLayout llSortingResult;
    private SortAlgorithm sortAlgorithm;
    private AnimationController animationController;
    private ValidationService validationService;
    private ValidationRule valueRule;
    private ValidationRule lengthRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextWatcher inputTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                tvErrorMsg.setText(validateValue(input));
            }
        };
        etInput = findViewById(R.id.numsInput);
        etInput.addTextChangedListener(inputTextWatcher);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);
        llSortingResult = findViewById(R.id.llSortingResult);

        sortAlgorithm = new BubbleSort();
        animationController = new AnimationController(this);
        validationService = new ValidationService();
        valueRule = new ValidationRule(value -> value >= 0 && value <= 9, "Input must be numbers between 0 and 9, and separated by spaces.");
        lengthRule = new ValidationRule(length -> length >= 3 && length <= 8, "The numbers of input must be between 3 and 8, and separated by spaces.");
    }

    public void sort(View vew) {
        final String input = etInput.getText().toString();
        if (!validate(input)) return;
        sortAlgorithm.sort(convertToIntArray(input));
        animationController.runAnimation(convertToIntArray(input),
                sortAlgorithm.getSortedAfterEachIteration(),
                sortAlgorithm.getAnimationScenarios(),
                llSortingResult);
    }

    public void exit(View view) {
        this.finish();
        System.exit(0);
    }

    private boolean validate(String input) {
        List<String> errorMsgs = new ArrayList<>();
        String errorMsg = "";

        errorMsg = validateLength(input);
        if (!errorMsg.equals("")) {
            errorMsgs.add(errorMsg);
        }
        errorMsg = validateValue(input);
        if (!errorMsg.equals("")) {
            errorMsgs.add(errorMsg);
        }

        if (errorMsgs.size() > 0) {
            tvErrorMsg.setText(String.join(" \n", errorMsgs));
            return false;
        }
        return true;
    }

    private String validateValue(String input) {
        String errorMsg = "";
        try {
            int[] numbers = Arrays.stream(input.split("\\s+")).
                    mapToInt(Integer::parseInt).toArray();
            if (!validationService.validate(numbers, valueRule)) {
                errorMsg = valueRule.getErrorMessage();
            }
        } catch (Exception ex) {
            errorMsg = valueRule.getErrorMessage();
        }
        return errorMsg;
    }

    private String validateLength(String input) {
        String errorMsg = "";
        try {
            if (!validationService.validate(convertToIntArray(input).length, lengthRule)) {
                errorMsg = lengthRule.getErrorMessage();
            }
        } catch (Exception ex) {
            errorMsg = lengthRule.getErrorMessage();
        }
        return errorMsg;
    }

    private int[] convertToIntArray(String input) {
        return Arrays.stream(input.split("\\s+")).
                mapToInt(Integer::parseInt).toArray();
    }

    private void sort(int[] numbers) {
        int length = numbers.length;
        for (int i = 0; i < length; i++) {
            for (int j = length - 1; j > i; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j, j - 1);
                }
            }
        }
    }

    private void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
}