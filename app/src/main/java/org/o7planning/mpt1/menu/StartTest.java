package org.o7planning.mpt1.menu;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartTest extends AppCompatActivity {

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup1;
    private TextView titleText;

    private AllQuestionsBaseThread allQuestionsBaseThread;

    private List<Questions> questionsList;
    private List<String> qStrings = new ArrayList<>();
    private List<String> aStrings = new ArrayList<>();

    private Questions questions;

    private int i1;
    private int i2;
    private int i3;
    private int randomQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_test);

        radioGroup1 = (RadioGroup) findViewById(R.id.radio_group);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        //List<Questions> questionsList
        allQuestionsBaseThread = new AllQuestionsBaseThread("Question", getApplicationContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questionsList = allQuestionsBaseThread.getQuestionsAll();

        if(questionsList.size() != 0) {
            //List<String> qStrings
            //List<String> aStrings
            for(int i = 0; i<questionsList.size(); i++) {
                qStrings.add(questionsList.get(i).questions);
                aStrings.add(questionsList.get(i).answers);
            }

            //Questions questions
            int rand = (int) (Math.random()*questionsList.size());

            questions = questionsList.get(rand);
            titleText = (TextView) findViewById(R.id.question);
            titleText.setText(questions.questions);

            do {
                do {
                    i1 = new Random().nextInt(aStrings.size());
                    i2 = new Random().nextInt(aStrings.size());
                    i3 = new Random().nextInt(aStrings.size());
                } while (!(i1 != i2 && i1 != i3 && i2 != i3));
            } while (!(rand != i1 && rand != i2 && rand != i3));
            radioButton1.setText(aStrings.get(i1));
            radioButton2.setText(aStrings.get(i2));
            radioButton3.setText(aStrings.get(i3));

            randomQ = new Random().nextInt(3);
            switch (randomQ) {
                case 0:
                    radioButton1.setText(questions.answers);
                    break;
                case 1:
                    radioButton2.setText(questions.answers);
                    break;
                case 2:
                    radioButton3.setText(questions.answers);
                    break;
            }

            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case -1:
                            Toast.makeText(getApplicationContext(), "Ничего не выбрано",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radioButton:
                            if(radioButton1.getText().equals(questions.answers)){
                                Toast.makeText(getApplicationContext(), "Правильно",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Не правильно",
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case R.id.radioButton2:
                            if(radioButton2.getText().equals(questions.answers)){
                                Toast.makeText(getApplicationContext(), "Правильно",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Не правильно",
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case R.id.radioButton3:
                            if(radioButton3.getText().equals(questions.answers)){
                                Toast.makeText(getApplicationContext(), "Правильно",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Не правильно",
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
