package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;

public class SearchTextActivity extends AppCompatActivity {
    EditText edtInput, edtSearchFor;
    TextView tvOutput;
    Button btnSearchText;
    CheckBox cbLowercase, cbCountWithSpaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_text);

        setTitle("Search Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtInput = findViewById(R.id.edtInput);
        tvOutput = findViewById(R.id.tvOutput);
        btnSearchText = findViewById(R.id.btnSearchText);
        cbLowercase = findViewById(R.id.cbLowercase);
        cbCountWithSpaces = findViewById(R.id.cbCountWithSpaces);
        edtSearchFor = findViewById(R.id.edtSearchFor);

        btnSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(SearchTextActivity.this);
                String input = edtInput.getText().toString().trim();
                String searchFor = edtSearchFor.getText().toString().trim();
                String temp = input;
                if (!input.isEmpty() && !searchFor.isEmpty()) {

                    if (cbLowercase.isChecked()) {
                        input = input.toLowerCase();
                        tvOutput.setText(input);
                    } else {
                        tvOutput.setText(input);
                    }

                    if (cbCountWithSpaces.isChecked()) {
                        temp = temp.trim().replace(" ", "");
                        tvOutput.setText(temp);
                    } else {
                        tvOutput.setText(input.trim());
                    }

                    if (input.contains(searchFor)) {
                        input = input.replaceAll(searchFor, "<font color='#EE0000'>" + searchFor + "</font>");
                        tvOutput.setText(Html.fromHtml(input));
                    }

                } else {
                    Toast.makeText(SearchTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*  String val = input.substring(0, totalWord);
                            Spannable spannable = new SpannableString(input);
                            int index = input.indexOf(val);
                            while (index >= 0) {
                                spannable.setSpan(new ForegroundColorSpan(Color.BLUE), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                index = input.indexOf(val, index + 1);
                                tvOutput.setText(val);
                            } */

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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}