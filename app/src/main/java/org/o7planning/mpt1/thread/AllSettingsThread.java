package org.o7planning.mpt1.thread;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.database.SinglDatabase;

import java.util.List;

public class AllSettingsThread implements Runnable{

    public Thread mThread;
    private MyDatabase myDatabase;
    private Context mContext;
    private List<Settingss> settingssList;

    public AllSettingsThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this, threadName);
        mThread.start();
    }

    @Override
    public void run() {
        myDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        settingssList = myDatabase.settingsDao().getAll();
    }

    public List<Settingss> getSettingsAll() {
        return settingssList;
    }
}
