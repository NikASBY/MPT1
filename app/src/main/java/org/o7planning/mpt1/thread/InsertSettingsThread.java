package org.o7planning.mpt1.thread;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.SettingssDao;

public class InsertSettingsThread implements Runnable{

    public Thread mThread;
    private MyDatabase myDatabase;
    private Settingss settingss;
    private SettingssDao settingssDao;
    private Context mContext;

    private String threadName;
    private Boolean changeLangRu;
    private Boolean changeLangEn;

    public InsertSettingsThread(String threadName, Context context, Boolean changeLangRu, Boolean changeLangEn) {
        this.mContext = context;
        this.threadName = threadName;
        this.changeLangRu = changeLangRu;
        this.changeLangEn = changeLangEn;
        mThread = new Thread(this, threadName);
        mThread.start();
    }

    @Override
    public void run() {
        myDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        settingssDao = myDatabase.settingsDao();
        settingss = new Settingss();
        settingss.id = myDatabase.settingsDao().getAll().size();
        if(changeLangRu != null) {
            settingss.changeLangRu = changeLangRu;
        }
        if(changeLangEn != null) {
            settingss.changeLangEn = changeLangEn;
        }
        if(settingss.id == 0) {
            settingssDao.insert(settingss);
        }
    }
}
