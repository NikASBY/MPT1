package org.o7planning.mpt1.thread.question;

import android.content.Context;

import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.database.SinglDatabase;
import org.o7planning.mpt1.database.dao.QuestionDao;

public class UpdateQuestionsBaseThread implements Runnable {

    public Thread mThread;
    private MyDatabase mMyDatabase;
    private Context mContext;
    private Questions mQuestions;
    private QuestionDao mQuestionDao;

    private Long id;
    private Long uidCollect;
    private Long uidTheme;
    private String question;
    private String answer;
    private String threadName;



    public UpdateQuestionsBaseThread(String threadName, Context context, Long id, Long uidCollect, Long uidTheme, String question, String answer) {
        this.mContext = context;
        this.threadName = threadName;
        this.id = id;
        this.uidCollect = uidCollect;
        this.uidTheme = uidTheme;
        this.question = question;
        this.answer = answer;
        mThread = new Thread(this,threadName);
        mThread.start();
    }

    @Override
    public void run() {
        mMyDatabase = SinglDatabase.getInstance(mContext).getMyDatabase();
        mQuestionDao = mMyDatabase.questionsDao();
        mQuestions = mQuestionDao.getById(id);
        if(uidCollect != null) {
            mQuestions.uidCollect = uidCollect;
        }
        if(uidTheme != null) {
            mQuestions.uidTheme = uidTheme;
        }
        if(question != null) {
            mQuestions.questions = question;
        }
        if(answer != null) {
            mQuestions.answers = answer;
        }
        mQuestionDao.update(mQuestions);
    }
}
