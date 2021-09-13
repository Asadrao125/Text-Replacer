package com.asadrao.textreplacer.ads;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.asadrao.textreplacer.R;
import com.google.android.gms.ads.AdRequest;
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
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, activity.getString(R.string.reward_ad), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        adInterface.rewardAdLoaded("DONE", buttonClicked);
                    }
                });
            }
        });
    }

}
