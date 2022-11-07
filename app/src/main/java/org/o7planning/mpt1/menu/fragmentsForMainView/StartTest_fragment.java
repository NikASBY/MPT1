package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.menu.activity.ResultTestActivity;
import org.o7planning.mpt1.menu.activity.StartTestActivity;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StartTest_fragment extends Fragment {

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
    private static int totalQuestion;

    private int contCollect;
    private int contTheme;

    private Integer right = 0;

    public static Integer getTotalQuestion() {
        return totalQuestion;
    }

    private static Integer totalNumber = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.form_test_fragment,container,false);

        radioGroup1 = (RadioGroup) view.findViewById(R.id.radio_group);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        totalText = (TextView) view.findViewById(R.id.totalNumber);
        rightText = (TextView) view.findViewById(R.id.right);

        //List<Questions> questionsList
        allQuestionsBaseThread = new AllQuestionsBaseThread("Question2", getContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questionsList = allQuestionsBaseThread.getQuestionsAll();

        contCollect = StartTestActivity.getContCollect();
        contTheme = StartTestActivity.getContTheme();
        checkQuestion = StartTestActivity.getCheckQuestion();

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
                totalQuestion = qStrings.size();
                //Questions questions
                int rand = (int) (Math.random()*qStrings.size());

                questions = qStrings.get(rand);
                answer = aStrings.get(rand);
                titleText = (TextView) view.findViewById(R.id.question);
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
                //radioButton1.setBackground(getResources().getDrawable(R.drawable.right_qnswer));

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

                right = StartTestActivity.getRight();
                totalNumber = StartTestActivity.getTotalNumber();

                totalText.setText("Total answer = " + totalNumber.toString());
                rightText.setText("Right answer = " + right.toString());

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case -1:
                                Toast.makeText(getContext(), "Ничего не выбрано",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radioButton:
                                if(radioButton1.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setCheckQuestion(checkQuestion);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                                fragmentManager1.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
                                break;
                            case R.id.radioButton2:
                                if(radioButton2.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setCheckQuestion(checkQuestion);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                fragmentManager2.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
                                break;
                            case R.id.radioButton3:
                                if(radioButton3.getText().equals(answer)){
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setCheckQuestion(checkQuestion);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
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
            int line = StartTestActivity.getLine();

            questions = qStrings.get(line);
            answer = aStrings.get(line);
            titleText = (TextView) view.findViewById(R.id.question);
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

            right = StartTestActivity.getRight();
            totalNumber = StartTestActivity.getTotalNumber();

            totalText.setText("Total answer = " + totalNumber.toString());
            rightText.setText("Right answer = " + right.toString());

            int lineNext = line + 1;
            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case -1:
                            Toast.makeText(getContext(), "Ничего не выбрано",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radioButton:
                            if(lineNext != qStrings.size()) {
                                if (radioButton1.getText().equals(answer)) {
                                    right = right + 1;

                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setLine(lineNext);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
                                fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                            } else {
                                Intent intent1 = new Intent(getContext(), ResultTestActivity.class);
                                intent1.putExtra("totalNumber", totalNumber + 1);
                                intent1.putExtra("right_answer", right);
                                intent1.putExtra("contTheme", contTheme);
                                intent1.putExtra("contCollect", contCollect);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                            }
                            break;
                        case R.id.radioButton2:
                            if(lineNext != qStrings.size()) {
                                if (radioButton2.getText().equals(answer)) {
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setLine(lineNext);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
                                fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                            } else {
                                Intent intent1 = new Intent(getContext(), ResultTestActivity.class);
                                intent1.putExtra("totalNumber", totalNumber + 1);
                                intent1.putExtra("right_answer", right);
                                intent1.putExtra("contTheme", contTheme);
                                intent1.putExtra("contCollect", contCollect);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                            }
                            break;
                        case R.id.radioButton3:
                            if(lineNext != qStrings.size()) {
                                if (radioButton3.getText().equals(answer)) {
                                    right = right + 1;
                                }
                                totalNumber = totalNumber + 1;
                                StartTestActivity.setContCollect(contCollect);
                                StartTestActivity.setContTheme(contTheme);
                                StartTestActivity.setLine(lineNext);
                                StartTestActivity.setTotalNumber(totalNumber);
                                StartTestActivity.setRight(right);
                                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment()).commit();
                                fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                            } else {
                                Intent intent1 = new Intent(getContext(), ResultTestActivity.class);
                                intent1.putExtra("totalNumber", totalNumber + 1);
                                intent1.putExtra("right_answer", right);
                                intent1.putExtra("contTheme", contTheme);
                                intent1.putExtra("contCollect", contCollect);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        StartTestActivity.setTotalNumber(0);
        StartTestActivity.setRight(0);
    }
}
