package com.dianyun.googleaddemo

/**
 * Created by yxhuang
 * Date: 2021/4/1
 * Description:
 */
interface GoogleAdListener {

    /**
     * 开始加载广告资源
     */
    fun onAdLoadStart()

    /**
     * 广告资源加载完成
     */
    fun onAdLoadComplete()

    /**
     * 广告资源加载失败
     * @param errorMsg
     */
    fun onAdFailedToLoad(errorMsg:String?)

    /**
     * 关闭广告
     */
    fun onAdDismissed()

    /**
     * 用户获取的奖励，只有视频广告才有
     */
    fun onUserEarnedReward(amount: Int, type:String)
}