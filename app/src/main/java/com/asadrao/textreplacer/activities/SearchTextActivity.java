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
import android.util.Log;
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

public class SearchTextActivity extends AppCompatActivity implements AdInterface {
    EditText edtInput, edtSearchFor;
    TextView tvOutput;
    LinearLayout outputLayout;
    Button btnSearchText;
    CheckBox cbLowercase, cbCountWithSpaces;
    LinearLayout shareLayout, copyLayout, saveLayout;
    String result;
    AdView adView;
    GlobalFunction globalFunction;
    AdInterface adInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_text);

        setTitle("Search Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtInput = findViewById(R.id.edtInput);
        tvOutput = findViewById(R.id.tvOutput);
        btnSearchText = findViewById(R.id.btnSearchText);
        cbLowercase = findViewById(R.id.cbLowercase);
        cbCountWithSpaces = findViewById(R.id.cbCountWithSpaces);
        edtSearchFor = findViewById(R.id.edtSearchFor);
        shareLayout = findViewById(R.id.shareLayout);
        copyLayout = findViewById(R.id.copyLayout);
        saveLayout = findViewById(R.id.saveLayout);
        adInterface = SearchTextActivity.this;
        outputLayout = findViewById(R.id.outputLayout);
        globalFunction = new GlobalFunction(this);

        adView = findViewById(R.id.adView);
        AdUtil adUtil = new AdUtil(this, adInterface);
        adUtil.loadBannerAd(adView);

        btnSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(SearchTextActivity.this);
                String input = edtInput.getText().toString().trim();
                String searchFor = edtSearchFor.getText().toString().trim();
                String temp = input;
                if (!input.isEmpty() && !searchFor.isEmpty()) {
                    outputLayout.setVisibility(View.VISIBLE);
                    if (cbLowercase.isChecked()) {
                        input = input.toLowerCase();
                        tvOutput.setText(input);
                        result = input;
                    } else {
                        tvOutput.setText(input);
                        result = input;
                    }

                    if (cbCountWithSpaces.isChecked()) {
                        Log.d("lkjhg", "onClick: " + removeSpace(temp));
                        tvOutput.setText(removeSpace(temp));
                        result = removeSpace(temp);
                    } else {
                        tvOutput.setText(input);
                        result = input;
                    }

                    if (input.contains(searchFor)) {
                        input = input.replaceAll(searchFor, "<font color='#EE0000'>" + searchFor + "</font>");
                        tvOutput.setText(Html.fromHtml(input));
                        result = "" + Html.fromHtml(input);
                    }

                } else {
                    outputLayout.setVisibility(View.GONE);
                    Toast.makeText(SearchTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
                    handleButton(false);
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(SearchTextActivity.this, adInterface, "SHARE");
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(SearchTextActivity.this, adInterface, "COPY");
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(SearchTextActivity.this, adInterface, "SAVE");
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
                globalFunction.shareMsg(result);
            } else if (buttonClicked.equals("COPY")) {
                globalFunction.copyOutput(result);
            } else if (buttonClicked.equals("SAVE")) {
                globalFunction.saveOutput(result);
            }
            handleButton(true);
        }
    }

    public static String removeSpace(String s) {
        String withoutspaces = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ')
                withoutspaces += s.charAt(i);

        }
        return withoutspaces;
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