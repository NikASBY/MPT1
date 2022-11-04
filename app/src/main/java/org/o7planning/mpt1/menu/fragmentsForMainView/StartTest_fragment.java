package org.o7planning.mpt1.menu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartTest_v2 extends AppCompatActivity {

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup1;
    private TextView titleText;
    private TextView totalText;
    private TextView rightText;


    private AllQuestionsBaseThread allQuestionsBaseThread;

    private List<Questions> questionsList;
    private List<String> qStrings = new ArrayList<>();
    private List<String> aStrings = new ArrayList<>();

    private String questions;
    private String answer;

    private int i1;
    private int i2;
    private int i3;
    private int randomQ;
    private boolean checkQuestion;

    private int contCollect;
    private int contTheme;

    private Integer right = 0;
    private Integer totalNumber = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_test);

        radioGroup1 = (RadioGroup) findViewById(R.id.radio_group);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        totalText = (TextView) findViewById(R.id.totalNumber);
        rightText = (TextView) findViewById(R.id.right);

        //List<Questions> questionsList
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

        if(checkQuestion == true) {
            if(questionsList.size() != 0) {
                //Формирую листы вопросов и ответов по выбранной теме
                //List<String> qStrings
                //List<String> aStrings
                for(int i = 0; i<questionsList.size(); i++) {
                    if(questionsList.get(i).uidCollect == contCollect && questionsList.get(i).uidTheme == contTheme) {
                        qStrings.add(questionsList.get(i).questions);
                        aStrings.add(questionsList.get(i).answers);
                    }
                }

                //Questions questions
                int rand = (int) (Math.random()*qStrings.size());

                questions = qStrings.get(rand);
                answer = aStrings.get(rand);
                titleText = (TextView) findViewById(R.id.question);
                titleText.setText(questions);

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
                        radioButton1.setText(answer);
                        break;
                    case 1:
                        radioButton2.setText(answer);
                        break;
                    case 2:
                        radioButton3.setText(answer);
                        break;
                }

                right = getIntent().getIntExtra("right",0);
                totalNumber = getIntent().getIntExtra("totalNumber", 0);
                totalText.setText("Total answer = " + totalNumber.toString());
                rightText.setText("Right answer = " + right.toString());

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case -1:
                                Toast.makeText(getApplicationContext(), "Ничего не выбрано",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radioButton:
                                if(radioButton1.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent.putExtra("right",right);
                                intent.putExtra("totalNumber",totalNumber);
                                intent.putExtra("contCollect2", contCollect);
                                intent.putExtra("contTheme2", contTheme);
                                intent.putExtra("checkQuestion", checkQuestion);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.radioButton2:
                                if(radioButton2.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent2 = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent2.putExtra("right",right);
                                intent2.putExtra("totalNumber",totalNumber);
                                intent2.putExtra("contCollect2", contCollect);
                                intent2.putExtra("contTheme2", contTheme);
                                intent2.putExtra("checkQuestion", checkQuestion);
                                startActivity(intent2);
                                finish();
                                break;
                            case R.id.radioButton3:
                                if(radioButton3.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent3 = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent3.putExtra("right",right);
                                intent3.putExtra("totalNumber",totalNumber);
                                intent3.putExtra("contCollect2", contCollect);
                                intent3.putExtra("contTheme2", contTheme);
                                intent3.putExtra("checkQuestion", checkQuestion);
                                startActivity(intent3);
                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        } else {
            //Формирую листы вопросов и ответов по выбранной теме
            //List<String> qStrings
            //List<String> aStrings
            for(int i = 0; i<questionsList.size(); i++) {
                if(questionsList.get(i).uidCollect == contCollect && questionsList.get(i).uidTheme == contTheme) {
                    qStrings.add(questionsList.get(i).questions);
                    aStrings.add(questionsList.get(i).answers);
                }
            }

            //Questions questions
            int line = getIntent().getIntExtra("line", 0);

            questions = qStrings.get(line);
            answer = aStrings.get(line);
            titleText = (TextView) findViewById(R.id.question);
            titleText.setText(questions);

            do {
                do {
                    i1 = new Random().nextInt(aStrings.size());
                    i2 = new Random().nextInt(aStrings.size());
                    i3 = new Random().nextInt(aStrings.size());
                } while (!(i1 != i2 && i1 != i3 && i2 != i3));
            } while (!(line != i1 && line != i2 && line != i3));


            radioButton1.setText(aStrings.get(i1));
            radioButton2.setText(aStrings.get(i2));
            radioButton3.setText(aStrings.get(i3));

            randomQ = new Random().nextInt(3);
            switch (randomQ) {
                case 0:
                    radioButton1.setText(answer);
                    break;
                case 1:
                    radioButton2.setText(answer);
                    break;
                case 2:
                    radioButton3.setText(answer);
                    break;
            }

            right = getIntent().getIntExtra("right",0);
            totalNumber = getIntent().getIntExtra("totalNumber", 0);
            totalText.setText("Total answer = " + totalNumber.toString());
            rightText.setText("Right answer = " + right.toString());
            int lineNext = line + 1;
            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case -1:
                            Toast.makeText(getApplicationContext(), "Ничего не выбрано",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radioButton:
                            if(lineNext != qStrings.size()) {
                                if (radioButton1.getText().equals(answer)) {
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent.putExtra("right", right);
                                intent.putExtra("totalNumber", totalNumber);
                                intent.putExtra("contCollect2", contCollect);
                                intent.putExtra("contTheme2", contTheme);
                                intent.putExtra("line", lineNext);
                                startActivity(intent);
                                finish();
                            } else {
                                finish();
                            }
                            break;
                        case R.id.radioButton2:
                            if(lineNext != qStrings.size()) {
                                if (radioButton2.getText().equals(answer)) {
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent2 = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent2.putExtra("right", right);
                                intent2.putExtra("totalNumber", totalNumber);
                                intent2.putExtra("contCollect2", contCollect);
                                intent2.putExtra("contTheme2", contTheme);
                                intent2.putExtra("line", lineNext);
                                startActivity(intent2);
                                finish();
                            } else {
                                finish();
                            }
                            break;
                        case R.id.radioButton3:
                            if(lineNext != qStrings.size()) {
                                if (radioButton3.getText().equals(answer)) {
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                Intent intent3 = new Intent(StartTest_v2.this, StartTest_v2.class);
                                intent3.putExtra("right", right);
                                intent3.putExtra("totalNumber", totalNumber);
                                intent3.putExtra("contCollect2", contCollect);
                                intent3.putExtra("contTheme2", contTheme);
                                intent3.putExtra("line", lineNext);
                                startActivity(intent3);
                                finish();
                            } else {
                                finish();
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exit_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_test:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
