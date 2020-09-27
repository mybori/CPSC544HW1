package com.cpsc544.bubblesort;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpsc544.bubblesort.validation.ValidationRule;
import com.cpsc544.bubblesort.validation.ValidationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etInput;
    private TextView tvErrorMsg;
    private LinearLayout llSortingResult;
    private List<int[]> numbersAfterSort;
    private ValidationService validationService;

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
                validate(input);
            }
        };
        etInput = findViewById(R.id.numsInput);
        etInput.addTextChangedListener(inputTextWatcher);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);
        llSortingResult = findViewById(R.id.llSortingResult);

        validationService = new ValidationService();
    }

    public void sort(View vew) {
        final String input = etInput.getText().toString();
        if (!validate(input)) return;

        bubbleSort(convertToIntArray(input));
        for (int[] numbers : numbersAfterSort) {
            TextView tv = new TextView(this);
            tv.setText(convertToString(numbers));
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llSortingResult.addView(tv);
        }
    }

    private boolean validate(String input) {
        try {
            int[] numbers = convertToIntArray(input);
            List<String> errorMsgs = new ArrayList<>();
            List<ValidationRule> rules = new ArrayList<>();
            ValidationRule valueRule = new ValidationRule(
                    value -> value >= 0 && value <= 9, "Input must be numbers between 0 and 9, and separated by spaces.");
            ValidationRule lengthRule = new ValidationRule(
                    length -> length >= 3 && length <= 8, "The numbers of input must be between 3 and 8, and separated by spaces.");
            rules.add(valueRule);
            rules.add(lengthRule);

            if (!validationService.validate(numbers, rules)) {
                errorMsgs = validationService.getErrorMessages();
            }

            if (errorMsgs.size() > 0) {
                tvErrorMsg.setText("");
                tvErrorMsg.setText(String.join(" \n", errorMsgs));
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private int[] bubbleSort(int[] numbers) {
        int length = numbers.length;
        numbersAfterSort = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = length - 1; j > i; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j, j - 1);
                }
            }
            numbersAfterSort.add(numbers.clone());
        }
        return numbers;
    }

    private void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    private int[] convertToIntArray(String input) {
        return Arrays.stream(input.split("\\s+")).
                mapToInt(Integer::parseInt).toArray();
    }

    private String convertToString(int[] numbers) {
        return Arrays.toString(numbers);
    }
}