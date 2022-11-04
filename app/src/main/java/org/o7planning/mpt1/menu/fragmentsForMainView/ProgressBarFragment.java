package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.menu.activity.SettingTestActivity;
import org.o7planning.mpt1.menu.activity.StartTestActivity;

public class ProgressBarFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvProgressHorizontal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.progress_bar_fragment,container,false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        tvProgressHorizontal = (TextView) view.findViewById(R.id.tv_progress_bar);

        boolean checkQuestion;
        checkQuestion = SettingTestActivity.isCheckQuestion();
        if(!checkQuestion) {
            Integer totalNumber = SettingTestActivity.getTotalQuestion();
            double item = totalNumber / 100.0;
            int line = StartTestActivity.getLine();
            progressBar.setMax(totalNumber);
            int progress = 0;
            progress = line + (int) item;
            String strProgress = "Вопрос №" + String.valueOf(line) + " из " + totalNumber;
            progressBar.setProgress(progress);
            tvProgressHorizontal.setText(strProgress);
        }
        return view;
    }
}
