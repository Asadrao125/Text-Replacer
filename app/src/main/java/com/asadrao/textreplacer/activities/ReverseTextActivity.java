package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ReverseTextActivity extends AppCompatActivity implements AdInterface {
    Button btnReverseText;
    EditText edtInput;
    TextView tvOutput;
    LinearLayout shareLayout, copyLayout, saveLayout;
    String result;
    GlobalFunction globalFunction;
    AdView adView;
    LinearLayout outputLayout;
    AdInterface adInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_text);

        setTitle("Reverse Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnReverseText = findViewById(R.id.btnReverseText);
        edtInput = findViewById(R.id.edtInput);
        tvOutput = findViewById(R.id.tvOutput);
        shareLayout = findViewById(R.id.shareLayout);
        copyLayout = findViewById(R.id.copyLayout);
        saveLayout = findViewById(R.id.saveLayout);
        adInterface = ReverseTextActivity.this;
        outputLayout = findViewById(R.id.outputLayout);
        globalFunction = new GlobalFunction(this);

        adView = findViewById(R.id.adView);
        AdUtil adUtil = new AdUtil(this, adInterface);
        adUtil.loadBannerAd(adView);

        btnReverseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(ReverseTextActivity.this);
                String input = edtInput.getText().toString().trim();
                if (!input.isEmpty()) {
                    outputLayout.setVisibility(View.VISIBLE);
                    StringBuffer buffer = new StringBuffer(input);
                    tvOutput.setText(buffer.reverse());
                    result = "" + buffer.reverse();
                } else {
                    outputLayout.setVisibility(View.GONE);
                    Toast.makeText(ReverseTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReverseTextActivity.this, adInterface, "SHARE");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReverseTextActivity.this, adInterface, "COPY");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReverseTextActivity.this, adInterface, "SAVE");
                    rewardAdUtil.showRewardAd();
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
        if (msg.equals("ENABLE_Button")) {
            handleButton(true);
        } else {
            if (buttonClicked.equals("SHARE")) {
                globalFunction.shareMsg(tvOutput.getText().toString().trim());
            } else if (buttonClicked.equals("COPY")) {
                globalFunction.copyOutput(tvOutput.getText().toString().trim());
            } else if (buttonClicked.equals("SAVE")) {
                globalFunction.saveOutput(tvOutput.getText().toString().trim());
            }
            handleButton(true);
        }
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