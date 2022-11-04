package org.o7planning.mpt1.thread;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;

public class AllCleanQuestionBaseThread implements Runnable{

    public Thread mThread;
    private MyDatabase myDatabase;
    private Context mContext;

    public AllCleanQuestionBaseThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this, threadName);
        mThread.start();
    }

    @Override
    public void run() {
        myDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        myDatabase.questionsDao().deleteAllFromTable();
    }
}
