package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.SaveModel;
import com.asadrao.textreplacer.ads.AdInterface;
import com.asadrao.textreplacer.ads.AdUtil;
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.asadrao.textreplacer.utils.Database;
import com.asadrao.textreplacer.utils.GlobalFunction;
import com.google.android.gms.ads.AdView;

public class MarkTextActivity extends AppCompatActivity implements AdInterface {
    TextView tvOutput;
    String result;
    AdView adView;
    Button btnMarkText;
    AdInterface adInterface;
    LinearLayout outputLayout;
    EditText edtInput, edtTotalWord;
    GlobalFunction globalFunction;
    LinearLayout shareLayout, copyLayout, saveLayout;

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
        shareLayout = findViewById(R.id.shareLayout);
        copyLayout = findViewById(R.id.copyLayout);
        saveLayout = findViewById(R.id.saveLayout);
        adInterface = MarkTextActivity.this;
        outputLayout = findViewById(R.id.outputLayout);
        globalFunction = new GlobalFunction(this);

        adView = findViewById(R.id.adView);
        AdUtil adUtil = new AdUtil(this, adInterface);
        adUtil.loadBannerAd(adView);

        btnMarkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(MarkTextActivity.this);
                String input = edtInput.getText().toString();
                String num = edtTotalWord.getText().toString().trim();
                if (!input.isEmpty() && !num.isEmpty()) {
                    outputLayout.setVisibility(View.VISIBLE);

                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(MarkTextActivity.this, adInterface, "SHARE");
                    rewardAdUtil.showRewardAd();

                    int totalWord = Integer.parseInt(num);
                    if (totalWord <= input.length()) {
                        String val = input.substring(0, totalWord);
                        String newS = input.replace(val, "");
                        String next = "<font color='#EE0000'>" + val + "</font>";
                        tvOutput.setText(Html.fromHtml(next + newS));
                        result = String.valueOf(Html.fromHtml(next + newS));
                    } else {
                        outputLayout.setVisibility(View.GONE);
                        Toast.makeText(MarkTextActivity.this, "Please donot enter limit " +
                                "greater than input length", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    outputLayout.setVisibility(View.GONE);
                    Toast.makeText(MarkTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.shareMsg(tvOutput.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.copyOutput(tvOutput.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.saveOutput(tvOutput.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void handleButton(boolean enableButton) {
        shareLayout.setEnabled(enableButton);
        copyLayout.setEnabled(enableButton);
        saveLayout.setEnabled(enableButton);
    }

    @Override
    public void rewardAdLoaded(String msg, String buttonClicked) {
        handleButton(true);
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