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
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.asadrao.textreplacer.utils.Database;

public class ReverseTextActivity extends AppCompatActivity implements AdInterface {
    Button btnReverseText;
    EditText edtInput;
    TextView tvOutput;
    LinearLayout shareLayout, copyLayout, saveLayout;
    String result;
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

        btnReverseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(ReverseTextActivity.this);
                String input = edtInput.getText().toString().trim();
                if (!input.isEmpty()) {
                    StringBuffer buffer = new StringBuffer(input);
                    tvOutput.setText(buffer.reverse());
                    result = "" + buffer.reverse();
                } else {
                    Toast.makeText(ReverseTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(ReverseTextActivity.this, adInterface, "SAVE");
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
                Toast.makeText(ReverseTextActivity.this, "Output Copied", Toast.LENGTH_SHORT).show();
            } else if (buttonClicked.equals("SAVE")) {
                Database database = new Database(ReverseTextActivity.this);
                long res = database.saveData(new SaveModel(0, result));
                if (res != -1) {
                    Toast.makeText(ReverseTextActivity.this, "Output Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReverseTextActivity.this, "Failed To Save Output", Toast.LENGTH_SHORT).show();
                }
            }
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