package org.o7planning.mpt1.thread.setting;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.SettingssDao;

public class UpdateSettingssThread implements Runnable{

    public Thread mThread;
    private MyDatabase myDatabase;
    private Settingss settingss;
    private SettingssDao settingssDao;
    private Context mContext;

    private String threadName;
    private Boolean changeLangRu;
    private Boolean changeLangEn;

    public UpdateSettingssThread(String threadName, Context context, Boolean changeLangRu, Boolean changeLangEn) {
        this.mContext = context;
        this.threadName = threadName;
        this.changeLangRu = changeLangRu;
        this.changeLangEn = changeLangEn;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    @Override
    public void run() {
        myDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        settingssDao = myDatabase.settingsDao();
        settingss = settingssDao.getById(0);
        if(settingss != null) {
            settingss.changeLangRu = changeLangRu;
        }
        if(settingss != null) {
            settingss.changeLangEn = changeLangEn;
        }
        settingssDao.update(settingss);
    }
}
