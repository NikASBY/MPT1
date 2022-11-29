package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.menu.activity.ResultTestActivity;
import org.o7planning.mpt1.menu.activity.ResultTestLineActivity;
import org.o7planning.mpt1.menu.activity.StartTestActivity;
import org.o7planning.mpt1.thread.progress.AllProgressBaseThread;
import org.o7planning.mpt1.thread.progress.UpdateProgressBaseThread;
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartTest_fragment_v2 extends Fragment {

    private TextView radioButton1;
    private TextView radioButton2;
    private TextView radioButton3;
    private TextView titleText;
    private TextView totalText;
    private TextView rightText;
    private ImageButton buttonBack;

    private List<Progress> progressList;
    private AllProgressBaseThread allProgressBaseThread;
    private int line;

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
    private static int totalQuestion;

    public static boolean isCheckQuestion() {
        return checkQuestion;
    }

    public static int getContCollect() {
        return contCollect;
    }

    public static int getContTheme() {
        return contTheme;
    }

    public static Integer getRight() {
        return right;
    }

    public static Integer getTotalNumber() {
        return totalNumber;
    }

    private static boolean checkQuestion;
    private static int contCollect;
    private static int contTheme;
    private static Integer right = 0;
    private static Integer totalNumber = 0;

    public static Integer getTotalQuestion() {
        return totalQuestion;
    }

    private int progressStatus = 0;
    private int rightLine = 0;
    private int totalNumberLine = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.form_test_fragment_v2,container,false);

        radioButton1 = (TextView) view.findViewById(R.id.radioButton);
        radioButton2 = (TextView) view.findViewById(R.id.radioButton2);
        radioButton3 = (TextView) view.findViewById(R.id.radioButton3);
        totalText = (TextView) view.findViewById(R.id.totalNumber);
        rightText = (TextView) view.findViewById(R.id.right);

        allProgressBaseThread = new AllProgressBaseThread("AllProgress", getContext());
        try {
            allProgressBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressList = allProgressBaseThread.getProgressAll();

        checkQuestion = StartTestActivity.getCheckQuestion();

        if(checkQuestion == true) {
            if(progressList.size() != 0) {
                    for(int i = 0; i < progressList.size(); i++) {
                        qStrings.add(progressList.get(i).questions);
                        aStrings.add(progressList.get(i).answers);
                    }

                    totalQuestion = qStrings.size();
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

                    radioButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(radioButton1.getText().equals(answer)){
                                right = right + 1;
                            }
                            totalNumber = totalNumber + 1;
                            StartTestActivity.setTotalNumber(totalNumber);
                            StartTestActivity.setCheckQuestion(checkQuestion);
                            StartTestActivity.setRight(right);
                            FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                            fragmentManager1.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        }
                    });

                    radioButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(radioButton2.getText().equals(answer)){
                                right = right + 1;
                            }
                            totalNumber = totalNumber + 1;
                            StartTestActivity.setCheckQuestion(checkQuestion);
                            StartTestActivity.setTotalNumber(totalNumber);
                            StartTestActivity.setRight(right);
                            FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                            fragmentManager2.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        }
                    });

                    radioButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(radioButton3.getText().equals(answer)){
                                right = right + 1;
                            }
                            totalNumber = totalNumber + 1;
                            StartTestActivity.setCheckQuestion(checkQuestion);
                            StartTestActivity.setTotalNumber(totalNumber);
                            StartTestActivity.setRight(right);
                            FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                            fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        }
                    });
            }
        } else {
            for(int i = 0; i < progressList.size(); i++) {
                qStrings.add(progressList.get(i).questions);
                aStrings.add(progressList.get(i).answers);
            }

            for (int i = 0; i < progressList.size(); i++) {
                if (progressList.get(i).answerFail == null) {
                    questions = progressList.get(i).questions;
                    answer = progressList.get(i).answers;
                    line = (int)progressList.get(i).id;
                    break;
                }
            }
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

            for (int i = 0; i < progressList.size(); i++) {
                if (progressList.get(i).answerFail != null) {
                    totalNumberLine++;
                }
                if (progressList.get(i).status == true) {
                    rightLine++;
                }
            }


            totalText.setText("Total answer = " + totalNumberLine);
            rightText.setText("Right answer = " + rightLine);

            int lineNext = line + 1;
            StartTestActivity.setLine(line);
            radioButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int time = 0;
                    if(lineNext != qStrings.size()) {
                        if (radioButton1.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton1.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton1.getText().toString(), false);
                        }
                        StartTestActivity.setTotalNumber(totalNumberLine);
                        FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                    } else {
                        if (radioButton1.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton1.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton1.getText().toString(), false);
                        }
                        Intent intent1 = new Intent(getContext(), ResultTestLineActivity.class);
                        intent1.putExtra("totalNumber", totalNumberLine + 1);
                        intent1.putExtra("right_answer", rightLine);
                        intent1.putExtra("contTheme", StartTestActivity.getContTheme());
                        intent1.putExtra("contCollect", StartTestActivity.getContCollect());
                        intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }
                }
            });

            radioButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lineNext != qStrings.size()) {
                        if (radioButton2.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton2.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton2.getText().toString(), false);
                        }
                        StartTestActivity.setTotalNumber(totalNumberLine);
                        FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                    } else {
                        if (radioButton2.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton2.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton2.getText().toString(), false);
                        }
                        Intent intent1 = new Intent(getContext(), ResultTestLineActivity.class);
                        intent1.putExtra("totalNumber", totalNumberLine + 1);
                        intent1.putExtra("right_answer", rightLine);
                        intent1.putExtra("contTheme", StartTestActivity.getContTheme());
                        intent1.putExtra("contCollect", StartTestActivity.getContCollect());
                        intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }
                }
            });

            radioButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lineNext != qStrings.size()) {
                        if (radioButton3.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton3.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton3.getText().toString(), false);
                        }
                        StartTestActivity.setTotalNumber(totalNumberLine);
                        FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.main_test_container2,new StartTest_fragment_v2()).commit();
                        fragmentManager3.beginTransaction().replace(R.id.progressBar_test_container, new ProgressBarFragment()).commit();
                    } else {
                        if (radioButton3.getText().equals(answer)) {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton3.getText().toString(), true);
                        } else {
                            UpdateProgressBaseThread updateProgressBaseThread = new UpdateProgressBaseThread("UpdateProgress",
                                    getContext(), progressList.get(line).id ,null,null,null,
                                    null, radioButton3.getText().toString(), false);
                        }
                        Intent intent1 = new Intent(getContext(), ResultTestLineActivity.class);
                        intent1.putExtra("totalNumber", totalNumberLine + 1);
                        intent1.putExtra("right_answer", rightLine);
                        intent1.putExtra("contTheme", StartTestActivity.getContTheme());
                        intent1.putExtra("contCollect", StartTestActivity.getContCollect());
                        intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }
                }
            });
        }
        buttonBack = (ImageButton) view.findViewById(R.id.button_back_test);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkQuestion == true) {
                    Intent intent1 = new Intent(getContext(), ResultTestActivity.class);
                    intent1.putExtra("totalNumber", totalNumber);
                    intent1.putExtra("right_answer", right);
                    intent1.putExtra("contTheme", StartTestActivity.getContTheme());
                    intent1.putExtra("contCollect", StartTestActivity.getContCollect());
                    intent1.putExtra("checkQuestion1", checkQuestion);
                    intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                } else {
                    right = rightLine;
                    totalNumber =totalNumberLine;
                    Intent intent1 = new Intent(getContext(), ResultTestLineActivity.class);
                    intent1.putExtra("totalNumber", totalNumber);
                    intent1.putExtra("right_answer", right);
                    intent1.putExtra("contTheme", StartTestActivity.getContTheme());
                    intent1.putExtra("contCollect", StartTestActivity.getContCollect());
                    intent1.putExtra("checkQuestion1", checkQuestion);
                    intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        StartTestActivity.setTotalNumber(0);
        StartTestActivity.setRight(0);
    }
}
