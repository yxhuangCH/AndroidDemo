package com.yxhuang.androiddailydemo.poll;

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
public interface IPollMonitor {

    void run();

    void stop();

    int pollInterval();

}
