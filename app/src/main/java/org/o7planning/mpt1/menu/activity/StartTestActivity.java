package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.menu.fragmentsForMainView.ProgressBarFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.StartTest_fragment_v2;
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;
import org.o7planning.mpt1.viewBaseData.SinglAbstractFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class StartTestActivity extends SinglAbstractFragmentActivity {

    private AllQuestionsBaseThread allQuestionsBaseThread;

    private List<Questions> questionsList;
    private List<String> qStrings = new ArrayList<>();
    private List<String> aStrings = new ArrayList<>();

    private static boolean checkQuestion;
    private static int contCollect;

    private static int contTheme;
    private static int line;
    private static String nameTheme;

    public static String getNameCollect() {
        return nameCollect;
    }

    private static String nameCollect;

    private static Integer right = 0;
    private static Integer totalNumber = 0;

    public static Integer getClockMaxTimer() {
        return clockMaxTimer;
    }

    public static void setClockMaxTimer(Integer clockMaxTimer1) {
        clockMaxTimer = clockMaxTimer1;
    }

    private static Integer clockMaxTimer;

    @Override
    public Fragment getFragment1() {
        return null;
    }

    @Override
    public Fragment getFragment2() {
        return new StartTest_fragment_v2();
    }

    @Override
    public Fragment getFragment3() {
        return new ProgressBarFragment();
    }

    @Override
    public Fragment getFragment4() {
        return null;
    }

    @Override
    public Integer getLayout1() {
        return R.layout.start_test_container;
    }

    @Override
    public Integer getContainer1() {
        return R.id.main_test_container1;
    }

    @Override
    public Integer getContainer2() {
        return R.id.main_test_container2;
    }

    @Override
    public Integer getContainer3() {
        return R.id.progressBar_test_container;
    }

    @Override
    public Integer getContainer4() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allQuestionsBaseThread = new AllQuestionsBaseThread("Question2", getApplicationContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questionsList = allQuestionsBaseThread.getQuestionsAll();

        contCollect = getIntent().getIntExtra("contCollect2", 0);
        contTheme = getIntent().getIntExtra("contTheme2", 0);
        checkQuestion = getIntent().getBooleanExtra("checkQuestion", false);
        line = getIntent().getIntExtra("line", 0);
        nameTheme = getIntent().getStringExtra("nameTheme");
        nameCollect = getIntent().getStringExtra("nameCollect");
        clockMaxTimer = getIntent().getIntExtra("clockTimer",0);
    }

    public static String getNameTheme() {
        return nameTheme;
    }

    public static int getContCollect() {
        return contCollect;
    }

    public static int getContTheme() {
        return contTheme;
    }

    public static boolean getCheckQuestion() {
        return checkQuestion;
    }

    public static int getLine() {
        return line;
    }

    public static void setCheckQuestion(boolean checkQuestion) {
        StartTestActivity.checkQuestion = checkQuestion;
    }

    public static void setContCollect(int contCollect) {
        StartTestActivity.contCollect = contCollect;
    }

    public static void setContTheme(int contTheme) {
        StartTestActivity.contTheme = contTheme;
    }

    public static void setLine(int line) {
        StartTestActivity.line = line;
    }

    public static Integer getRight() {
        return right;
    }

    public static void setRight(Integer right) {
        StartTestActivity.right = right;
    }

    public static Integer getTotalNumber() {
        return totalNumber;
    }

    public static void setTotalNumber(Integer totalNumber) {
        StartTestActivity.totalNumber = totalNumber;
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(StartTestActivity.this, SettingTestActivity.class);
        intent1.putExtra("totalNumber", totalNumber);
        intent1.putExtra("contTheme", StartTestActivity.getContTheme());
        intent1.putExtra("contCollect", StartTestActivity.getContCollect());
        intent1.putExtra("checkQuestion1", checkQuestion);
        intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
        intent1.putExtra("nameTheme", nameTheme);
        startActivity(intent1);
    }
}
