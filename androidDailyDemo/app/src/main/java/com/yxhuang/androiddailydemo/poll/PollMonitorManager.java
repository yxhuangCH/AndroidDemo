package com.yxhuang.androiddailydemo.poll;

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
public class PollMonitorManager {

    private IPollMonitor mPollMonitor;
    private MonitorThread mMonitorThread;

    public PollMonitorManager(IPollMonitor pollMonitor) {
        mPollMonitor = pollMonitor;
        mMonitorThread = new MonitorThread();
    }

    public void start(){
        mMonitorThread.start(mPollMonitor);
    }

    public void stop(){
        mMonitorThread.stop();
    }

    public void resume(){
        mMonitorThread.resume();
    }

    public void pause(){
        mMonitorThread.pause();
    }
}
