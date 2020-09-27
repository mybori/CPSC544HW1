package com.cpsc544.bubblesort;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText etInput;
    private TextView tvErrorMsg;
    private TextView tvSortResult;

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
                tvErrorMsg.setText(validate(input));
            }
        };
        etInput = findViewById(R.id.numsInput);
        etInput.addTextChangedListener(inputTextWatcher);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);
        tvSortResult = findViewById(R.id.tvSortResult);
    }

    public void sort(View vew) {
        final String input = etInput.getText().toString();
        String errorMsg = validate(input);
        if (!errorMsg.equals("")) {
            tvErrorMsg.setText(errorMsg);
            return;
        };
        int[] sortedNumbers = bubbleSort(convertToIntArray(input));
        tvSortResult.setText(convertToString(sortedNumbers));
    }

    private String validate(String input) {
        String errorMsg = "";
        try {
            int[] numbers = convertToIntArray(input);
            for (int number : numbers) {
                if (number < 0 || number > 9) {
                    errorMsg = "Invalid input.";
                    break;
                }
            }
        } catch (Exception ex) {
            errorMsg = "Invalid input.";
        }
        return errorMsg;
    }

    private int[] bubbleSort(int[] numbers) {
        int length = numbers.length;
        for (int i = 0; i <= length; i++) {
            for (int j = length - 1; j > i; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j, j - 1);
                }
            }
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