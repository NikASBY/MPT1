package org.o7planning.mpt1.thread.assembling;

import android.content.Context;

import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;

import java.util.List;

public class AllCollectBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private List<Assembling> mAssemblings;

    public AllCollectBaseThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    public List<Assembling> getAssemblingsAll() {
        return mAssemblings;
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mAssemblings = mMyDatabase.assemblingDao().getAll();
    }
}
