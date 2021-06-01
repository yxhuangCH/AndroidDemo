package com.dianyun.googleaddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*

class GoogleAdActivity : AppCompatActivity(), OnInitializationCompleteListener, GoogleAdListener {

    companion object {
        const val REWARD_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
        const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
        const val TAG = "GoogleAdActivity_"
    }


    private var mRewardedAd: RewardedAd? = null
    private var mGoogleAdLoader: GoogleRewardAndInterstitialAdLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val googleAdParams = RewardedAdParams(REWARD_AD_UNIT_ID)
        mGoogleAdLoader = GoogleRewardAndInterstitialAdLoader(this, googleAdParams, this)


//        MobileAds.initialize(this, this)


        btn_reward.setOnClickListener {
//            initRewardAd()
            mGoogleAdLoader?.showRewardAd(this)
        }

//        btn_banner.setOnClickListener {
//            banner_View.adListener = object : AdListener() {
//                override fun onAdFailedToLoad(error: LoadAdError?) {
//                    Log.i(TAG, "btn_banner onAdFailedToLoad $error")
//                }
//
//                override fun onAdImpression() {
//                    Log.i(TAG, "btn_banner onAdImpression")
//                }
//
//                override fun onAdClosed() {
//                    Log.i(TAG, "btn_banner onAdClosed")
//                }
//
//                override fun onAdLoaded() {
//                    Log.i(TAG, "btn_banner onAdLoaded")
//                }
//
//                override fun onAdOpened() {
//                    Log.i(TAG, "btn_banner onAdOpened")
//                }
//                override fun onAdClicked() {
//                    Log.i(TAG, "btn_banner onAdClicked")
//                }
//            }
////            banner_View.adUnitId = "ca-app-pub-3940256099942544/6300978111"
////            banner_View.adSize = AdSize.BANNER
//            val adRequest = AdManagerAdRequest.Builder().build()
//            banner_View.loadAd(adRequest)
//        }

        btn_full_screen.setOnClickListener {
//            initInterstitialAd()
            mGoogleAdLoader?.showInterstitialAd(this)
        }
    }

    private fun initRewardAd() {
        Log.d(TAG, "initRewardAd was start")
        if (mRewardedAd == null) {
            val adRequest = AdManagerAdRequest.Builder().build()
            val isTestDevice = adRequest.isTestDevice(this)
            Log.d(TAG, "initRewardAd was isTestDevice $isTestDevice")

            RewardedAd.load(this, REWARD_AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "initRewardAd was loaded.")
                    mRewardedAd = rewardedAd
                    configRewardAd()
                    showRewardAd()
                }
            })
        } else {
            configRewardAd()
            showRewardAd()
        }
    }


    override fun onInitializationComplete(intatus: InitializationStatus?) {
        intatus?.adapterStatusMap?.forEach {
            Log.i(TAG, "initialize it key ${it.key} value ${it.value.description} ")
        }
    }


    private fun configRewardAd() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                Log.i(TAG, "onAdShowedFullScreenContent")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.i(TAG, "onAdDismissedFullScreenContent")
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError?) {
                Log.i(TAG, "onAdFailedToShowFullScreenContent error $error")
            }

            override fun onAdImpression() {
                Log.i(TAG, "onAdImpression")
            }
        }

        mRewardedAd?.setOnPaidEventListener { it ->
            Log.i(TAG, "setOnPaidEventListener it $it")
        }
    }

    private fun showRewardAd() {
        mRewardedAd?.show(this) {
            Log.i(TAG, "showRewardAd ${it.amount} + ${it.type}")
        }
    }

    private fun initInterstitialAd() {
        Log.d(TAG, "initInterstitialAd start")
        val adRequest = AdManagerAdRequest.Builder().build()
        AdManagerInterstitialAd.load(
            this,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "initInterstitialAd ${adError?.message}")
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    Log.d(TAG, "InterstitialAd was loaded.")
                    configInterstitialAd(interstitialAd)
                }
            }
        )
    }

    private fun configInterstitialAd(interstitialAd: AdManagerInterstitialAd) {
        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "configInterstitialAd onAdDismissedFullScreenContent ")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "configInterstitialAd onAdFailedToShowFullScreenContent $adError ")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "configInterstitialAd onAdShowedFullScreenContent ")
            }

            override fun onAdImpression() {
                Log.i(TAG, "configInterstitialAd onAdImpression")
            }

        }
        interstitialAd.show(this)
    }

    override fun onPause() {
        super.onPause()
        banner_View.pause()
    }

    override fun onResume() {
        super.onResume()
        banner_View.resume()
    }

    override fun onDestroy() {
        banner_View.destroy()

        super.onDestroy()
    }


    override fun onAdLoadStart() {
        Log.i(TAG, "onAdLoadStart")
    }

    override fun onAdLoadComplete() {
        Log.i(TAG, "onAdLoadComplete")
    }

    override fun onAdFailedToLoad(errorMsg: String?) {
        Log.i(TAG, "onAdFailedToLoad errorMsg $errorMsg")
    }

    override fun onAdDismissed() {
        Log.i(TAG, "onAdDismissed")
    }

    override fun onUserEarnedReward(amount: Int, type: String) {
        Log.i(TAG, "onUserEarnedReward amount $amount type $type")
    }

}