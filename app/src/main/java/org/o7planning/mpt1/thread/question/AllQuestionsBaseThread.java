package org.o7planning.mpt1.thread.question;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.database.SinglDatabase;

import java.util.List;

public class AllQuestionsBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private List<Questions> mQuestions;

    public AllQuestionsBaseThread(String threadName, Context context) {
        this.mContext = context;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    public List<Questions> getQuestionsAll() {
        return mQuestions;
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mQuestions = mMyDatabase.questionsDao().getAll();
    }
}
