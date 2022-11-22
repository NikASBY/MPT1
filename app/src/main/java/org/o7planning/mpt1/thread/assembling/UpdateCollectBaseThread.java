package org.o7planning.mpt1.thread.assembling;

import android.content.Context;
import android.util.Log;

import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.AssemblingDao;

public class UpdateCollectBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private Assembling mAssembling;
    private AssemblingDao mAssemblingDao;

    private Long id;
    private Long uid;
    private String assembling;
    private String theme;
    private String threadName;



    public UpdateCollectBaseThread(String threadName, Context context, Long id, Long uid, String assembling, String theme) {
        this.mContext = context;
        this.threadName = threadName;
        this.id = id;
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
        mAssembling = mAssemblingDao.getById(id);
        if(uid != null) {
            mAssembling.uid = uid;
        }
        if(assembling != null) {
            mAssembling.assembling = assembling;
        }
        if(theme != null) {
            Log.i("theme = " , theme);
            mAssembling.theme.add(theme);
        }
        mAssemblingDao.update(mAssembling);
    }
}
