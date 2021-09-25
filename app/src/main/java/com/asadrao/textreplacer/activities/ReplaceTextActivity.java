package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ReplaceTextActivity extends AppCompatActivity implements AdInterface {
    EditText edtInput, edtFindText, edtReplaceWith;
    Button btnReplaceText;
    AdView adView;
    GlobalFunction globalFunction;
    TextView tvOutput, tvInputWord, tvOutputWord, tvInputLetter, tvOutputLetter;
    CheckBox cbLowercase, cbCountWord, cbCountLetter, cbReverseText;
    LinearLayout shareLayout, copyLayout, saveLayout;
    LinearLayout inputWordLayout, outputWordLayout, inputLetterLayout, outputLetterLayout;
    String result;
    AdInterface adInterface;
    LinearLayout outputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_text);

        setTitle("Replace Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtInput = findViewById(R.id.edtInput);
        edtFindText = findViewById(R.id.edtFindText);
        edtReplaceWith = findViewById(R.id.edtReplaceWith);
        btnReplaceText = findViewById(R.id.btnReplaceText);
        cbLowercase = findViewById(R.id.cbLowercase);
        cbCountWord = findViewById(R.id.cbCountWord);
        cbCountLetter = findViewById(R.id.cbCountLetter);
        cbReverseText = findViewById(R.id.cbReverseText);
        tvOutput = findViewById(R.id.tvOutput);
        tvInputLetter = findViewById(R.id.tvInputLetter);
        tvOutputLetter = findViewById(R.id.tvOutputLetter);
        tvInputWord = findViewById(R.id.tvInputWord);
        tvOutputWord = findViewById(R.id.tvOutputWord);
        adInterface = ReplaceTextActivity.this;
        shareLayout = findViewById(R.id.shareLayout);
        copyLayout = findViewById(R.id.copyLayout);
        saveLayout = findViewById(R.id.saveLayout);
        outputLayout = findViewById(R.id.outputLayout);
        inputWordLayout = findViewById(R.id.inputWordLayout);
        outputWordLayout = findViewById(R.id.outputWordLayout);
        inputLetterLayout = findViewById(R.id.inputLetterLayout);
        outputLetterLayout = findViewById(R.id.outputLetterLayout);
        globalFunction = new GlobalFunction(this);

        adView = findViewById(R.id.adView);
        AdUtil adUtil = new AdUtil(this, adInterface);
        adUtil.loadBannerAd(adView);

        btnReplaceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(ReplaceTextActivity.this);
                if (!edtInput.getText().toString().trim().isEmpty()) {
                    processText();
                    outputLayout.setVisibility(View.VISIBLE);
                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReplaceTextActivity.this, adInterface, "SHARE");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.shareMsg(result);
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.copyOutput(result);
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    globalFunction.saveOutput(result);
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void processText() {
        String originalText = edtInput.getText().toString().trim();
        String findText = edtFindText.getText().toString().trim();
        String replaceText = edtReplaceWith.getText().toString().trim();

        String outputText = "";
        String inputText = originalText;

        if (cbLowercase.isChecked()) {
            inputText = inputText.toLowerCase();
        }

        if (cbReverseText.isChecked()) {
            StringBuffer buffer = new StringBuffer(inputText);
            inputText = buffer.reverse().toString();
        }

        outputText = inputText.replaceAll(findText, replaceText);

        if (cbCountWord.isChecked()) {
            inputWordLayout.setVisibility(View.VISIBLE);
            outputWordLayout.setVisibility(View.VISIBLE);
        } else {
            inputWordLayout.setVisibility(View.GONE);
            outputWordLayout.setVisibility(View.GONE);
        }

        if (cbCountLetter.isChecked()) {
            inputLetterLayout.setVisibility(View.VISIBLE);
            outputLetterLayout.setVisibility(View.VISIBLE);
        } else {
            inputLetterLayout.setVisibility(View.GONE);
            outputLetterLayout.setVisibility(View.GONE);
        }

        tvOutput.setText(outputText);
        tvOutputLetter.setText("" + outputText.replaceAll(" ", "").length());
        tvInputLetter.setText("" + inputText.replaceAll(" ", "").length());
        tvOutputWord.setText("" + countWords(outputText));
        tvInputWord.setText("" + countWords(originalText));
        result = outputText;
    }

    public int countWords(String text) {
        text = text.replaceAll("\\s{2,}", " ").trim();
        String words[] = text.trim().split(" ");
        return words.length;
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