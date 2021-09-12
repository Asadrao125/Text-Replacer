package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;

public class MarkTextActivity extends AppCompatActivity {
    TextView tvOutput;
    Button btnMarkText;
    EditText edtInput, edtTotalWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_text);

        setTitle("Mark Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvOutput = findViewById(R.id.tvOutput);
        edtInput = findViewById(R.id.edtInput);
        edtTotalWord = findViewById(R.id.edtTotalWord);
        btnMarkText = findViewById(R.id.btnMarkText);

        btnMarkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(MarkTextActivity.this);
                String input = edtInput.getText().toString();
                String num = edtTotalWord.getText().toString().trim();
                if (!input.isEmpty() && !num.isEmpty()) {
                    int totalWord = Integer.parseInt(num);
                    if (totalWord <= input.length()) {
                        String val = input.substring(0, totalWord);
                        String newS = input.replace(val, "");
                        String next = "<font color='#EE0000'>" + val + "</font>";
                        tvOutput.setText(Html.fromHtml(next + newS));
                    } else {
                        Toast.makeText(MarkTextActivity.this, "Please donot enter limit " +
                                "greater than input length", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MarkTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}