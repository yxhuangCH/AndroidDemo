package com.dianyun.googleaddemo

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.lang.RuntimeException

/**
 * Created by yxhuang
 * Date: 2021/4/1
 * Description:
 */
class GoogleRewardAndInterstitialAdLoader(
    activity: Activity,
    adParams: GoogleAdParams,
    private var adListener: GoogleAdListener? = null
) {

    companion object {
        private const val TAG = "GoogleAdLoader"
    }

    private var mRewardedAd: RewardedAd? = null
    private var mInterstitialAd: AdManagerInterstitialAd? = null

    init {
        MobileAds.initialize(activity)

        when (adParams) {
            is RewardedAdParams -> {
                initRewardAd(adParams, activity)
            }
            is InterstitialAdParams -> {
                initInterstitialAd(adParams, activity)
            }
        }
    }

    private fun initRewardAd(rewardedAdParams: RewardedAdParams, activity: Activity) {
        Log.d(TAG, "initRewardAd was start")
        checkInitedRewardAd()
        val adRequest = AdManagerAdRequest.Builder().build()

        adListener?.onAdLoadStart()
        RewardedAd.load(
            activity,
            rewardedAdParams.adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    mRewardedAd = null
                    adListener?.onAdFailedToLoad(adError?.message)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(GoogleAdActivity.TAG, "initRewardAd was loaded.")
                    mRewardedAd = rewardedAd
                    adListener?.onAdLoadComplete()
                    configRewardAd()
                }
            })
    }

    private fun checkInitedRewardAd() {
        if (mRewardedAd != null) {
            throw RuntimeException("RewardedAd  has already init")
        }
    }

    private fun configRewardAd() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                Log.i(TAG, "onAdShowedFullScreenContent")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.i(TAG, "onAdDismissedFullScreenContent")
                adListener?.onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError?) {
                Log.i(TAG, "onAdFailedToShowFullScreenContent error $error")
                adListener?.onAdFailedToLoad(error?.message)
            }

            override fun onAdImpression() {
                Log.i(TAG, "onAdImpression")
            }
        }
    }


    private fun initInterstitialAd(interstitialAdParams: InterstitialAdParams, activity: Activity) {
        Log.d(TAG, "initInterstitialAd start")
        val adRequest = AdManagerAdRequest.Builder().build()

        adListener?.onAdLoadStart()

        AdManagerInterstitialAd.load(activity,
            interstitialAdParams.adUnitId,
            adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "initInterstitialAd ${adError?.message}")
                    mInterstitialAd = null
                    adListener?.onAdFailedToLoad(adError?.message)
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    Log.d(TAG, "InterstitialAd was loaded.")
                    mInterstitialAd = interstitialAd
                    adListener?.onAdLoadComplete()
                    configInterstitialAd(interstitialAd)
                }
            })
    }

    private fun configInterstitialAd(interstitialAd: AdManagerInterstitialAd) {
        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "configInterstitialAd onAdDismissedFullScreenContent ")
                adListener?.onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "configInterstitialAd onAdFailedToShowFullScreenContent $adError ")
                adListener?.onAdFailedToLoad(adError?.message)
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "configInterstitialAd onAdShowedFullScreenContent ")
            }

            override fun onAdImpression() {
                Log.i(TAG, "configInterstitialAd onAdImpression")
            }
        }
    }


    /**
     * 显示插页广告
     * @param activity
     */
    fun showInterstitialAd(activity: Activity) {
        Log.d(TAG, "showInterstitialAd")
        mInterstitialAd?.show(activity)
    }

    /**
     * 显示激励广告
     * @param activity
     */
    fun showRewardAd(activity: Activity) {
        Log.d(TAG, "showRewardAd")
        mRewardedAd?.show(activity) {
            adListener?.onUserEarnedReward(it.amount, it.type)
        }
    }

    fun destroy() {
        adListener = null
    }

}