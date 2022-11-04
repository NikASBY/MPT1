package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_test_activity);

        contCollect = getIntent().getIntExtra("contCollect", 0);
        contTheme = getIntent().getIntExtra("contTheme", 0);
        nameCollect = getIntent().getStringExtra( "nameCollect");
        nameTheme = getIntent().getStringExtra("nameTheme");

        allQuestionsBaseThread = new AllQuestionsBaseThread("AllQuestion",getApplicationContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questions = new ArrayList<>();
        listQuestion = new ArrayList<>();
        listAnswer = new ArrayList<>();
        questions = allQuestionsBaseThread.getQuestionsAll();

        for(int i = 0; i<questions.size(); i++) {
            if(questions.get(i).uidCollect == contCollect && questions.get(i).uidTheme == contTheme) {
                listQuestion.add(questions.get(i).questions);
                listAnswer.add(questions.get(i).answers);
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
        randomQuestions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkQuestion = b;
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
                startActivity(intentButtonOk);
            }
        });

        buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Integer getTotalQuestion() {
        return totalQuestion;
    }

    public static boolean isCheckQuestion() {
        return checkQuestion;
    }
}
