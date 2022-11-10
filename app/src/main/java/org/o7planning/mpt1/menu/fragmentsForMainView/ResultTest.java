package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.menu.activity.NewMainMenu;
import org.o7planning.mpt1.menu.activity.StartTestActivity;

public class ResultTest extends Fragment {

    private Button selectButtonTheme;
    private Button restartButtonTheme;

    private TextView nameTextTheme;
    private TextView totalTextQuestion;
    private TextView rightTextAnswer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.result_test_fragment,container,false);

        nameTextTheme = (TextView) view.findViewById(R.id.name_theme);
        nameTextTheme.setText("Tema: " + StartTestActivity.getNameTheme());

        totalTextQuestion = (TextView) view.findViewById(R.id.total_question);
        totalTextQuestion.setText("Всего вопросов: " + StartTestActivity.getTotalNumber());

        rightTextAnswer = (TextView) view.findViewById(R.id.right_answer);
        rightTextAnswer.setText("Правильных ответов: " + StartTestActivity.getRight());

        selectButtonTheme = (Button) view.findViewById(R.id.select_theme);
        selectButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewMainMenu.class);
                startActivity(intent);
            }
        });

        restartButtonTheme = (Button) view.findViewById(R.id.restart_theme);
        restartButtonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getContext(), StartTestActivity.class);
                startActivity(intent2);
            }
        });




        return view;
    }
}
