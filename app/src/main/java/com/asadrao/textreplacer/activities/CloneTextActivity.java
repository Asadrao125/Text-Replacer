package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.BuildConfig;
import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.ads.AdInterface;
import com.asadrao.textreplacer.ads.AdUtil;
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardedAd;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class CloneTextActivity extends AppCompatActivity implements AdInterface {
    AdView adView;
    Button btnCloneText;
    TextView tvOutput;
    String result;
    AdInterface adInterface;
    EditText edtInput, edtCloneLimit;
    Button btnShare, btnCopy, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clone_text);

        setTitle("Clone Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCloneText = findViewById(R.id.btnCloneText);
        tvOutput = findViewById(R.id.tvOutput);
        edtInput = findViewById(R.id.edtInput);
        edtCloneLimit = findViewById(R.id.edtCloneLimit);
        adInterface = CloneTextActivity.this;

        btnShare = findViewById(R.id.btnShare);
        btnCopy = findViewById(R.id.btnCopy);
        btnSave = findViewById(R.id.btnSave);

        adView = findViewById(R.id.adView);
        AdUtil adUtil = new AdUtil(this, adInterface);
        adUtil.loadBannerAd(adView);
        adUtil.showInterstial();

        btnCloneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(CloneTextActivity.this);
                String input = edtInput.getText().toString().trim();
                String num = edtCloneLimit.getText().toString().trim();
                if (!input.isEmpty() && !num.isEmpty()) {
                    int limit = Integer.parseInt(num);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        tvOutput.setText(String.join("", Collections.nCopies(limit, input)));
                        result = String.join("", Collections.nCopies(limit, input));
                    } else {
                        result = String.format("%0" + limit + "d", 0).replace("0", input);
                        tvOutput.setText(String.format("%0" + limit + "d", 0).replace("0", input));
                    }
                } else {
                    Toast.makeText(CloneTextActivity.this, "Please enter input and limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(CloneTextActivity.this, adInterface, "SHARE");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(CloneTextActivity.this, adInterface, "COPY");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(CloneTextActivity.this, adInterface, "SAVE");
                    rewardAdUtil.showRewardAd();
                } else {
                    Toast.makeText(getApplicationContext(), "No Output To Share", Toast.LENGTH_SHORT).show();
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                Toast.makeText(CloneTextActivity.this, "Output Copied", Toast.LENGTH_SHORT).show();
            } else if (buttonClicked.equals("SAVE")) {
                Toast.makeText(getApplicationContext(), "" + buttonClicked, Toast.LENGTH_SHORT).show();
            }
        }
    }
}