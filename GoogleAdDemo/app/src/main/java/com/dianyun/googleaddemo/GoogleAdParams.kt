package com.dianyun.googleaddemo

/**
 * Created by yxhuang
 * Date: 2021/4/1
 * Description:
 */

/**
 * 谷歌广告参数
 */
sealed class GoogleAdParams {

    abstract val adUnitId:String
}

/**
 * 谷歌视频广告参数
 * @property adUnitId
 */
data class RewardedAdParams(override val adUnitId:String):GoogleAdParams()

/**
 * 全屏广告参数
 * @property adUnitId
 */
data class InterstitialAdParams(override val adUnitId:String):GoogleAdParams()