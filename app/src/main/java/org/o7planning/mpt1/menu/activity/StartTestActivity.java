package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.menu.fragmentsForMainView.ProgressBarFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.ResultTest;
import org.o7planning.mpt1.menu.fragmentsForMainView.StartTest_fragment;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;
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

    private static Integer right = 0;
    private static Integer totalNumber = 0;

    @Override
    public Fragment getFragment1() {
        return null;
    }

    @Override
    public Fragment getFragment2() {
        return new StartTest_fragment();
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
    public Integer getConteiner1() {
        return R.id.main_test_container1;
    }

    @Override
    public Integer getConteiner2() {
        return R.id.main_test_container2;
    }

    @Override
    public Integer getConteiner3() {
        return R.id.progressBar_test_container;
    }

    @Override
    public Integer getConteiner4() {
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exit_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_test:
                if(!getCheckQuestion()) {
                    Intent intent1 = new Intent(StartTestActivity.this, ResultTestActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(StartTestActivity.this, ResultTestActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }

        }
        return super.onOptionsItemSelected(item);
    }*/
}
