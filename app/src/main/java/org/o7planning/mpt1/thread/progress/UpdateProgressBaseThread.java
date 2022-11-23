package org.o7planning.mpt1.thread.progress;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.ProgressDao;

public class UpdateProgressBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private Progress mProgress;
    private ProgressDao mProgressDao;

    private String nameCollect;
    private String nameTheme;
    private String question;
    private String answer;
    private String threadName;
    private boolean status;
    private String answerFail;
    private Long id;

    public UpdateProgressBaseThread(String threadName, Context context, Long id, String nameCollect, String nameTheme, String question, String answer, String answerFail, boolean status) {
        this.mContext = context;
        this.threadName = threadName;
        this.id = id;
        this.nameCollect = nameCollect;
        this.nameTheme = nameTheme;
        this.question = question;
        this.answer = answer;
        this.answerFail = answerFail;
        this.status = status;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mProgressDao = mMyDatabase.progressDao();
        mProgress = mProgressDao.getById(id);
        if(nameCollect != null) {
            mProgress.nameCollect = nameCollect;
        }
        if(nameTheme != null) {
            mProgress.nameTheme = nameTheme;
        }
        if(question != null) {
            mProgress.questions = question;
        }
        if(answer != null) {
            mProgress.answers = answer;
        }
        if(answerFail != null) {
            mProgress.answerFail = answerFail;
        }
        mProgress.status = status;
        mProgressDao.update(mProgress);
    }
}
