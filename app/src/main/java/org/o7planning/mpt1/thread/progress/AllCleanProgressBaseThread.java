package org.o7planning.mpt1.thread.progress;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;

public class AllCleanProgressBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase myDatabase;
    private Context mContext;

    public AllCleanProgressBaseThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this, threadName);
        mThread.start();
    }

    @Override
    public void run() {
        myDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        myDatabase.progressDao().deleteAllFromTable();
    }
}
