package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;

public class ResultTestActivity extends AppCompatActivity {

    private Button selectButtonTheme;
    private Button restartButtonTheme;

    private TextView nameTextTheme;
    private TextView totalTextQuestion;
    private TextView rightTextAnswer;

    private String nameTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_test_fragment);

        nameTextTheme = (TextView) findViewById(R.id.name_theme);
        nameTheme = StartTestActivity.getNameTheme();
        nameTextTheme.setText("Tema: " + nameTheme);

        totalTextQuestion = (TextView) findViewById(R.id.total_question);
        totalTextQuestion.setText("Всего вопросов: " + getIntent().getIntExtra("totalNumber", 0));

        rightTextAnswer = (TextView) findViewById(R.id.right_answer);
        rightTextAnswer.setText("Правильных ответов: " + getIntent().getIntExtra("right_answer", 0));

        selectButtonTheme = (Button) findViewById(R.id.select_theme);
        selectButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultTestActivity.this, NewMainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        restartButtonTheme = (Button) findViewById(R.id.restart_theme);
        restartButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ResultTestActivity.this, SettingTestActivity.class);
                intent2.putExtra("nameTheme", nameTheme);
                intent2.putExtra("contTheme", getIntent().getIntExtra("contTheme",0));
                intent2.putExtra("contCollect", getIntent().getIntExtra("contCollect",0));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
            }
        });
    }


}
