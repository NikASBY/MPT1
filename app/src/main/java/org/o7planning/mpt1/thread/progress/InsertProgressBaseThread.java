package org.o7planning.mpt1.thread.progress;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.ProgressDao;
import org.o7planning.mpt1.database.dao.QuestionDao;

public class InsertProgressBaseThread implements Runnable {

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

    public InsertProgressBaseThread(String threadName, Context context, String nameCollect, String nameTheme, String question, String answer, String answerFail, boolean status) {
        this.mContext = context;
        this.threadName = threadName;
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
        mProgress = new Progress();
        mProgress.id = mMyDatabase.progressDao().getAll().size();
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
        mProgressDao.insert(mProgress);
    }
}
