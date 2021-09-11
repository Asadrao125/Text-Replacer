package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;

public class ReverseTextActivity extends AppCompatActivity {
    Button btnReverseText;
    EditText edtInput;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_text);

        setTitle("Reverse Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnReverseText = findViewById(R.id.btnReverseText);
        edtInput = findViewById(R.id.edtInput);
        tvOutput = findViewById(R.id.tvOutput);

        btnReverseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtInput.getText().toString().trim();
                if (!input.isEmpty()) {
                    StringBuffer buffer = new StringBuffer(input);
                    tvOutput.setText(buffer.reverse());
                } else {
                    Toast.makeText(ReverseTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
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