package com.asadrao.textreplacer.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.asadrao.textreplacer.SaveModel;
import com.asadrao.textreplacer.activities.CloneTextActivity;

public class GlobalFunction {
    Activity activity;

    public GlobalFunction(Activity activity) {
        this.activity = activity;
    }

    public void saveOutput(String result) {
        Database database = new Database(activity);
        long res = database.saveData(new SaveModel(0, result));
        if (res != -1) {
            Toast.makeText(activity, "Output Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Failed To Save Output", Toast.LENGTH_SHORT).show();
        }
    }

    public void copyOutput(String result) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", result);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity, "Output Copied", Toast.LENGTH_SHORT).show();
    }

    public void shareMsg(String msg) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }
}
