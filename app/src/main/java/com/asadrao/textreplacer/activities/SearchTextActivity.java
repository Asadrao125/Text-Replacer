package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.asadrao.textreplacer.R;

public class SearchTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_text);

        setTitle("Search Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}