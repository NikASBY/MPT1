package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.poi.ss.formula.functions.T;
import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.menu.fragmentsForMainView.ListResultRecyclerFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.ProgressBarFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.StartTest_fragment_v2;
import org.o7planning.mpt1.thread.progress.AllProgressBaseThread;
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;
import org.o7planning.mpt1.viewBaseData.SinglAbstractFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultTestLineActivity extends SinglAbstractFragmentActivity {

    private TextView titleResultText;
    private TextView nameThemeTitleText;
    private TextView nameThemeText;
    private TextView countQuestionTitleText;
    private TextView countQuestionText;
    private TextView countRightTitleText;
    private TextView countRightText;
    private TextView titleTableResultText;
    private TextView numberQuestionText;
    private TextView nameQuestionText;
    private TextView failAnswerText;

    private Button selectButtonTheme;
    private Button restartButtonTheme;

    private boolean checkQuestion;
    private String nameTheme;
    private int totalNumber;
    private int rightNumber;

    private List<Progress> progressList;

    @Override
    public Fragment getFragment1() {
        return new ListResultRecyclerFragment();
    }

    @Override
    public Fragment getFragment2() {
        return null;
    }

    @Override
    public Fragment getFragment3() {
        return null;
    }

    @Override
    public Fragment getFragment4() {
        return null;
    }

    @Override
    public Integer getLayout1() {
        return R.layout.conteiner_result_test;
    }

    @Override
    public Integer getContainer1() {
        return R.id.containerTableResult;
    }

    @Override
    public Integer getContainer2() {
        return null;
    }

    @Override
    public Integer getContainer3() {
        return null;
    }

    @Override
    public Integer getContainer4() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AllProgressBaseThread allProgressBaseThread = new AllProgressBaseThread("AllProgress", getApplicationContext());
        try {
            allProgressBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressList = allProgressBaseThread.getProgressAll();

        checkQuestion = getIntent().getBooleanExtra("checkQuestion1", false);
        totalNumber = getIntent().getIntExtra("totalNumber", 0);
        rightNumber = getIntent().getIntExtra("right_answer", 0);
        nameTheme = progressList.get(0).nameTheme;

        titleResultText = (TextView) findViewById(R.id.titleResult);
        titleResultText.setText(R.string.title_result);

        nameThemeTitleText = (TextView) findViewById(R.id.nameThemeTitle);
        nameThemeTitleText.setText(R.string.name_theme);

        nameThemeText = (TextView) findViewById(R.id.nameTheme);
        nameThemeText.setText(nameTheme);

        countRightTitleText = (TextView) findViewById(R.id.countRightTitle);
        countRightTitleText.setText(R.string.number_of_correct_answers);

        countRightText = (TextView) findViewById(R.id.countRight);
        countRightText.setText(Integer.valueOf(rightNumber).toString());


        titleTableResultText = (TextView) findViewById(R.id.titleTableResult);
        titleTableResultText.setText(R.string.title_table_result_text);

        numberQuestionText = (TextView) findViewById(R.id.numberQuestion);
        numberQuestionText.setText(R.string.n);

        nameQuestionText = (TextView) findViewById(R.id.nameQuestion);
        nameQuestionText.setText(R.string.name_question);

        failAnswerText = (TextView) findViewById(R.id.failAnswer);
        failAnswerText.setText(R.string.name_answer);

        countQuestionTitleText = (TextView) findViewById(R.id.countQuestionTitle);
        countQuestionTitleText.setText(R.string.count_question);

        countQuestionText = (TextView) findViewById(R.id.countQuestion);
        countQuestionText.setText(Integer.valueOf(totalNumber).toString());

        selectButtonTheme = (Button) findViewById(R.id.selectTheme);
        selectButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultTestLineActivity.this, NewMainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        restartButtonTheme = (Button) findViewById(R.id.restartTheme);
        restartButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ResultTestLineActivity.this, SettingTestActivity.class);
                intent2.putExtra("nameTheme", nameTheme);
                intent2.putExtra("contTheme", getIntent().getIntExtra("contTheme",0));
                intent2.putExtra("contCollect", getIntent().getIntExtra("contCollect",0));
                intent2.putExtra("checkQuestion", checkQuestion);
                intent2.putExtra("nameCollect", getIntent().getStringExtra("nameCollect"));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(ResultTestLineActivity.this, SettingTestActivity.class);
        intent1.putExtra("totalNumber", totalNumber);
        intent1.putExtra("contTheme", StartTestActivity.getContTheme());
        intent1.putExtra("contCollect", StartTestActivity.getContCollect());
        intent1.putExtra("checkQuestion1", checkQuestion);
        intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
        intent1.putExtra("nameTheme", nameTheme);
        startActivity(intent1);

    }
}
