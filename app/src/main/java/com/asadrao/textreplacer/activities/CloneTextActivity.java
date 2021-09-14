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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asadrao.textreplacer.BuildConfig;
import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.SaveModel;
import com.asadrao.textreplacer.ads.AdInterface;
import com.asadrao.textreplacer.ads.AdUtil;
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.asadrao.textreplacer.utils.Database;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardedAd;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class CloneTextActivity extends AppCompatActivity implements AdInterface {
    AdView adView;
    String result;
    TextView tvOutput;
    Button btnCloneText;
    AdInterface adInterface;
    EditText edtInput, edtCloneLimit;
    LinearLayout shareLayout, copyLayout, saveLayout;

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

        shareLayout = findViewById(R.id.shareLayout);
        copyLayout = findViewById(R.id.copyLayout);
        saveLayout = findViewById(R.id.saveLayout);

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

        shareLayout.setOnClickListener(new View.OnClickListener() {
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

        copyLayout.setOnClickListener(new View.OnClickListener() {
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

        saveLayout.setOnClickListener(new View.OnClickListener() {
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
                Database database = new Database(CloneTextActivity.this);
                long res = database.saveData(new SaveModel(0, result));
                if (res != -1) {
                    Toast.makeText(CloneTextActivity.this, "Output Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CloneTextActivity.this, "Failed To Save Output", Toast.LENGTH_SHORT).show();
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