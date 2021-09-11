package com.asadrao.textreplacer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asadrao.textreplacer.R;

public class HomeActivity extends AppCompatActivity {
    Button btnReplaceText, btnSearchText, btnCountText, btnCloneText, btnReverseText, btnMarkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home");
        btnReplaceText = findViewById(R.id.btnReplaceText);
        btnSearchText = findViewById(R.id.btnSearchText);
        btnCountText = findViewById(R.id.btnCountText);
        btnCloneText = findViewById(R.id.btnCloneText);
        btnReverseText = findViewById(R.id.btnReverseText);
        btnMarkText = findViewById(R.id.btnMarkText);

        btnReplaceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReplaceTextActivity.class));
            }
        });

        btnSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchTextActivity.class));
            }
        });

        btnCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CountTextActivity.class));
            }
        });

        btnCloneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CloneTextActivity.class));
            }
        });

        btnReverseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReverseTextActivity.class));
            }
        });

        btnMarkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MarkTextActivity.class));
            }
        });

    }
}