package com.cpsc544.bubblesort;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText etInput;
    private TextView tvErrorMsg;

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
    }

    private String validate(String input) {
        String errorMsg = "";
        try {
            int[] numbers = Arrays.stream(input.split("\\s+")).
                    mapToInt(Integer::parseInt).toArray();
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
}