package com.asadrao.textreplacer.ads;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.utils.SharedPref;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Arrays;

public class RewardAdUtil {
    Activity activity;
    AdInterface adInterface;
    String buttonClicked;

    public RewardAdUtil(Activity activity, AdInterface adInterface, String buttonClicked) {
        this.activity = activity;
        this.adInterface = adInterface;
        this.buttonClicked = buttonClicked;
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("B821E335E67314C017FEEA34B874A108")).build());
    }

    public void showRewardAd() {
        SharedPref.init(activity);
        if (!SharedPref.read("isAdInProcess", "").equals("true")) {
            SharedPref.write("isAdInProcess", "true");
        } else {
            adInterface.rewardAdLoaded("NOTDONE", buttonClicked);
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, activity.getString(R.string.reward_ad), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                adInterface.rewardAdLoaded("ENABLE_Button", buttonClicked);

                super.onAdLoaded(rewardedAd);
                rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        adInterface.rewardAdLoaded("DONE", buttonClicked);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                SharedPref.write("isAdInProcess", "false");
                adInterface.rewardAdLoaded("DONE", buttonClicked);
            }
        });
    }
}