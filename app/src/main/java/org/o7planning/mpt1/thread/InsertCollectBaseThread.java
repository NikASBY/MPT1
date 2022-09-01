package org.o7planning.mpt1.thread;

import android.content.Context;
import android.util.Log;

import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.AssemblingDao;

import java.util.List;

public class InsertCollectBaseThread implements Runnable{

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private Assembling mAssembling;
    private AssemblingDao mAssemblingDao;

    private Long uid;
    private String assembling;
    private String theme;
    private String threadName;

    public InsertCollectBaseThread(String threadName, Context context, Long uid, String assembling, String theme) {
        this.mContext = context;
        this.threadName = threadName;
        this.uid = uid;
        this.assembling = assembling;
        this.theme = theme;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mAssemblingDao = mMyDatabase.assemblingDao();
        mAssembling = new Assembling();
        mAssembling.id = mMyDatabase.assemblingDao().getAll().size();
        if(uid != null) {
            mAssembling.uid = uid;
        } else {
            mAssembling.uid = mMyDatabase.assemblingDao().getAll().size();
        }
        if(assembling != null) {
            mAssembling.assembling = assembling;
        }
        if(theme != null) {
            Log.i("theme = " , theme);
            mAssembling.theme.add(theme);
        }
        mAssemblingDao.insert(mAssembling);
    }
}
