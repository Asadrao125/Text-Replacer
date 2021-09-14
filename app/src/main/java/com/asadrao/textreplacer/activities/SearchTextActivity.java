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
import com.asadrao.textreplacer.ads.RewardAdUtil;
import com.asadrao.textreplacer.utils.Database;

public class SearchTextActivity extends AppCompatActivity implements AdInterface {
    EditText edtInput, edtSearchFor;
    TextView tvOutput;
    Button btnSearchText;
    CheckBox cbLowercase, cbCountWithSpaces;
    LinearLayout shareLayout, copyLayout, saveLayout;
    String result;
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

        btnSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(SearchTextActivity.this);
                String input = edtInput.getText().toString().trim();
                String searchFor = edtSearchFor.getText().toString().trim();
                String temp = input;
                if (!input.isEmpty() && !searchFor.isEmpty()) {

                    if (cbLowercase.isChecked()) {
                        input = input.toLowerCase();
                        tvOutput.setText(input);
                        result = input;
                    } else {
                        tvOutput.setText(input);
                        result = input;
                    }

                    if (cbCountWithSpaces.isChecked()) {
                        temp = temp.trim().replace(" ", "");
                        tvOutput.setText(temp);
                        result = temp;
                    } else {
                        tvOutput.setText(input.trim());
                        result = input.trim();
                    }

                    if (input.contains(searchFor)) {
                        input = input.replaceAll(searchFor, "<font color='#EE0000'>" + searchFor + "</font>");
                        tvOutput.setText(Html.fromHtml(input));
                        result = "" + Html.fromHtml(input);
                    }

                } else {
                    Toast.makeText(SearchTextActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOutput.getText().toString().equals("Output")) {
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
                    RewardAdUtil rewardAdUtil = new RewardAdUtil(SearchTextActivity.this, adInterface, "SAVE");
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
                Toast.makeText(SearchTextActivity.this, "Output Copied", Toast.LENGTH_SHORT).show();
            } else if (buttonClicked.equals("SAVE")) {
                Database database = new Database(SearchTextActivity.this);
                long res = database.saveData(new SaveModel(0, result));
                if (res != -1) {
                    Toast.makeText(SearchTextActivity.this, "Output Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchTextActivity.this, "Failed To Save Output", Toast.LENGTH_SHORT).show();
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