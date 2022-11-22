package org.o7planning.mpt1.thread.progress;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.database.SinglDatabase;

import java.util.List;

public class AllProgressBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private List<Progress> mProgress;

    public AllProgressBaseThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    public List<Progress> getProgressAll() {
        return mProgress;
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mProgress = mMyDatabase.progressDao().getAll();
    }
}
