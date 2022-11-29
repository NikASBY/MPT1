package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.progress.AllCleanProgressBaseThread;
import org.o7planning.mpt1.thread.progress.InsertProgressBaseThread;
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;

public class SettingTestActivity extends AppCompatActivity {

    private Button buttonOk;
    private Button buttonBack;
    private TextView titleNameCollect;
    private TextView titleNameTheme;
    private TextView titleListQuestion;
    private TextView titleListAnswer;
    private CheckBox randomQuestions;
    private Spinner clockTest;


    private String nameCollect;
    private String nameTheme;
    private List<String> listQuestion;
    private List<String> listAnswer;

    private int contCollect;
    private int contTheme;

    private static boolean checkQuestion;
    private int lineNext = 0;
    private static int totalQuestion;

    private AllQuestionsBaseThread allQuestionsBaseThread;
    private List<Questions> questions;

    private Integer clockMaxTimer;

    private Integer[] timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_test_activity);

        AllCleanProgressBaseThread allCleanProgressBaseThread = new AllCleanProgressBaseThread("AllClearProgress", getApplicationContext());

        contCollect = getIntent().getIntExtra("contCollect", 0);
        contTheme = getIntent().getIntExtra("contTheme", 0);
        nameCollect = getIntent().getStringExtra( "nameCollect");
        nameTheme = getIntent().getStringExtra("nameTheme");

        checkQuestion = getIntent().getBooleanExtra("checkQuestion", false);

        allQuestionsBaseThread = new AllQuestionsBaseThread("AllQuestion",getApplicationContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questions = new ArrayList<>();
        questions = allQuestionsBaseThread.getQuestionsAll();

        //************************
        listQuestion = new ArrayList<>();
        listAnswer = new ArrayList<>();
        //************************

        for(int i = 0; i<questions.size(); i++) {
            if(questions.get(i).uidCollect == contCollect && questions.get(i).uidTheme == contTheme) {

                //************************
                listQuestion.add(questions.get(i).questions);
                listAnswer.add(questions.get(i).answers);
                //************************

                InsertProgressBaseThread insertProgressBaseThread = new InsertProgressBaseThread("ProgressBase", getApplicationContext(), nameCollect, nameTheme, questions.get(i).questions, questions.get(i).answers, null, false);
                try {
                    insertProgressBaseThread.mThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        totalQuestion = listQuestion.size();

        titleListQuestion = (TextView) findViewById(R.id.title_list_question_test);
        titleListQuestion.setText(listQuestion.toString());

        titleListAnswer = (TextView) findViewById(R.id.title_list_answer_test);
        titleListAnswer.setText(listAnswer.toString());

        titleNameCollect = (TextView) findViewById(R.id.title_name_collect_test);
        titleNameCollect.setText(nameCollect);

        titleNameTheme = (TextView) findViewById(R.id.title_name_theme_test);
        titleNameTheme.setText(nameTheme);

        randomQuestions = (CheckBox) findViewById(R.id.random_drop_of_questions);
        if(checkQuestion == true) {
            randomQuestions.setChecked(true);
        }
        randomQuestions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkQuestion = b;
            }
        });
        timer = new Integer[]{10,20,30};
        clockTest = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.row,R.id.textView1,timer);
        clockTest.setAdapter(integerArrayAdapter);
        clockTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (timer != null) {
                    clockMaxTimer = timer[i];
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonOk = (Button) findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentButtonOk = new Intent(SettingTestActivity.this, StartTestActivity.class);
                intentButtonOk.putExtra("contCollect2", contCollect);
                intentButtonOk.putExtra("contTheme2", contTheme);
                intentButtonOk.putExtra("checkQuestion", checkQuestion);
                intentButtonOk.putExtra("line", lineNext);
                intentButtonOk.putExtra("nameTheme", nameTheme);
                intentButtonOk.putExtra("nameCollect", nameCollect);
                intentButtonOk.putExtra("clockTimer", clockMaxTimer);
                startActivity(intentButtonOk);
            }
        });

        buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentButtonBack = new Intent(SettingTestActivity.this, NewMainMenu.class);
                startActivity(intentButtonBack);
            }
        });

    }

    public static Integer getTotalQuestion() {
        return totalQuestion;
    }

    public static boolean isCheckQuestion() {
        return checkQuestion;
    }

    @Override
    public void onBackPressed() {
        Intent intentButtonBack = new Intent(SettingTestActivity.this, NewMainMenu.class);
        startActivity(intentButtonBack);
    }
}
