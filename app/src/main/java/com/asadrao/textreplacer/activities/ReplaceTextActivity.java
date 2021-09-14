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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.SaveModel;
import com.asadrao.textreplacer.ads.AdInterface;
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.asadrao.textreplacer.utils.Database;

public class ReplaceTextActivity extends AppCompatActivity implements AdInterface {
    EditText edtInput, edtFindText, edtReplaceWith;
    Button btnReplaceText;
    TextView tvOutput, tvInputWord, tvOutputWord, tvInputLetter, tvOutputLetter;
    CheckBox cbLowercase, cbCountWord, cbCountLetter, cbReverseText;
    LinearLayout shareLayout, copyLayout, saveLayout;
    String result;
    AdInterface adInterface;

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

        btnReplaceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(ReplaceTextActivity.this);
                String input = edtInput.getText().toString().trim();
                String temp = input;
                if (cbLowercase.isChecked()) {
                    tvOutput.setText("Output: " + input.toLowerCase());
                    result = input.toLowerCase();
                }

                if (cbReverseText.isChecked()) {
                    StringBuffer buffer = new StringBuffer(input);
                    tvOutput.setText("Output: " + buffer.reverse());
                    result = "" + buffer.reverse();
                }

                if (cbCountWord.isChecked()) {
                    input = input.replaceAll("\\s{2,}", " ").trim();
                    String words[] = input.trim().split(" ");
                    if (words.length > 0) {
                        tvOutputWord.setText("Output Words: " + words.length);
                        String wordsNew[] = input.trim().split(" ");
                        tvInputWord.setText("Input Words: " + wordsNew.length);
                    }
                }

                if (cbCountLetter.isChecked()) {
                    int spaces = input == null ? 0 : input.replaceAll("[^ ]", "").length();
                    int res = input.length() - spaces;
                    tvOutputLetter.setText("Output Letters: " + input.trim().length());
                    tvInputLetter.setText("Input Letters: " + res);
                }

                if (!edtFindText.getText().toString().trim().isEmpty() && edtReplaceWith.getText().toString().isEmpty()) {
                    input = input.replace(edtFindText.getText().toString().trim(), "");
                    tvOutput.setText("Output: " + input);
                    result = input;
                } else if (edtFindText.getText().toString().isEmpty() && !edtReplaceWith.getText().toString().isEmpty()) {
                    Toast.makeText(ReplaceTextActivity.this, "Please enter Find Text", Toast.LENGTH_SHORT).show();
                } else {
                    tvOutput.setText("Output: " + input.replace(edtFindText.getText().toString().trim(),
                            edtReplaceWith.getText().toString().trim()));
                    result = input.replace(edtFindText.getText().toString().trim(),
                            edtReplaceWith.getText().toString().trim());
                }

            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReplaceTextActivity.this, adInterface, "SHARE");
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReplaceTextActivity.this, adInterface, "COPY");
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReplaceTextActivity.this, adInterface, "SAVE");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void rewardAdLoaded(String msg, String buttonClicked) {
        if (msg.equals("DONE")) {
            if (buttonClicked.equals("SHARE")) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            } else if (buttonClicked.equals("COPY")) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", result);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ReplaceTextActivity.this, "Output Copied", Toast.LENGTH_SHORT).show();
            } else if (buttonClicked.equals("SAVE")) {
                Database database = new Database(ReplaceTextActivity.this);
                long res = database.saveData(new SaveModel(0, result));
                if (res != -1) {
                    Toast.makeText(ReplaceTextActivity.this, "Output Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReplaceTextActivity.this, "Failed To Save Output", Toast.LENGTH_SHORT).show();
                }
            }
        }
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