package com.asadrao.textreplacer.ads;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.asadrao.textreplacer.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Arrays;

public class AdUtil {
    Activity activity;
    AdInterface adInterface;

    public AdUtil(Activity activity, AdInterface adInterface) {
        this.activity = activity;
        this.adInterface = adInterface;
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("B821E335E67314C017FEEA34B874A108")).build());
    }

    public void loadBannerAd(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        //AdSize adSize = getAdSize();
        //adView.setAdSize(adSize);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.loadAd(adRequest);
    }

    public void showInterstial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, activity.getResources().getString(R.string.interstitial_ads), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Log.i("onAdLoaded", "onAdLoaded");
                        interstitialAd.show(activity);

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i("onAdFailedToLoad", loadAdError.getMessage());
                    }
                });
    }

    /*public void showRewardAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, activity.getString(R.string.reward_ad), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        adInterface.rewardAdLoaded("DONE");
                    }
                });
            }
        });
    }*/

    private AdSize getAdSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

}
