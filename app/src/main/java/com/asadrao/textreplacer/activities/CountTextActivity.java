package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;

public class CountTextActivity extends AppCompatActivity {
    EditText edtInput;
    CheckBox checkBox;
    Button btnCountText;
    TextView tvTotalWords, tvTotalLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_text);

        setTitle("Count Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtInput = findViewById(R.id.edtInput);
        btnCountText = findViewById(R.id.btnCountText);
        tvTotalWords = findViewById(R.id.tvTotalWords);
        tvTotalLetters = findViewById(R.id.tvTotalLetters);
        checkBox = findViewById(R.id.checkBox);

        btnCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtInput.getText().toString();
                String temp = input;
                if (!input.isEmpty()) {

                    input = input.replaceAll("\\s{2,}", " ").trim();
                    String words[] = input.trim().split(" ");
                    if (words.length > 0) {
                        tvTotalWords.setText("Total Words: " + words.length);
                    }

                    /* String words[] = input.trim().split(" ");
                        if (words.length > 0) {
                            tvTotalWords.setText("Total Words: " + words.length);
                        } */

                    if (checkBox.isChecked()) {
                        tvTotalLetters.setText("Total Letters: " + temp.length());
                    } else {
                        int spaces = input == null ? 0 : input.replaceAll("[^ ]", "").length();
                        int res = input.length() - spaces;
                        tvTotalLetters.setText("Total Letters: " + res);
                    }
                } else {
                    Toast.makeText(CountTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });
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