package Minesweeper.time;

import java.util.TimerTask;

public class Timer
{
    private long seconds = 0;
    private int milliseconds = 0;
    private Runnable runnable;
    private TimerTask task;
    private java.util.Timer timer;
    private boolean running = false;

    public Timer(){
        milliseconds = 0;
        seconds = 0;
    }

    private void makeTask()
    {
        task = new TimerTask()
        {
            @Override
            public void run()
            {
                milliseconds++;
                seconds += milliseconds / 10;
                if(seconds + milliseconds / 10 > seconds)
                    runnable.run();
                milliseconds %= 10;
            }
        };
        timer = new java.util.Timer();
    }

    public void start(Runnable r)
    {
        if(running)
            return;

        milliseconds = 0;
        seconds = 0;
        runnable = r;
        running = true;
        makeTask();
        schedule();
    }

    public void stop()
    {
        if(timer == null || !running)
            return;
        running = false;
        timer.cancel();
        timer.purge();
    }

    public void on()
    {
        if(running)
            return;
        running = true;
        makeTask();
        schedule();
    }

    public void clearTime()
    {
        milliseconds = 0;
        seconds = 0;
    }

    public boolean isRunning()
    {
        return running;
    }

    public long getSeconds()
    {
        return seconds;
    }

    private void schedule()
    {
        timer.schedule(task,0,100);
    }
}
