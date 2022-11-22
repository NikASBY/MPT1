package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.menu.activity.ResultTestActivity;
import org.o7planning.mpt1.menu.activity.SettingTestActivity;
import org.o7planning.mpt1.menu.activity.StartTestActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
        } else {
            Integer totalTimer = StartTestActivity.getClockMaxTimer();
            progressBar.setMax(totalTimer);
            Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    progressBar.setProgress(message.what);
                    return false;
                }
            });
            Handler handler1 = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    tvProgressHorizontal.setText(String.valueOf(message.what) + "c");
                    return false;
                }
            });
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            Thread thread = new Thread(new Runnable() {
                int start = 0;
                @Override
                public void run() {
                    try {
                        while (start < totalTimer) {
                            Thread.sleep(1000);
                            start++;
                            handler.sendEmptyMessage(start);
                            handler1.sendEmptyMessage(start);
                        }
                        if (start == totalTimer && fragmentManager.findFragmentById(R.id.main_test_container2) != null) {
                            Intent intent1 = new Intent(getContext(), ResultTestActivity.class);
                            intent1.putExtra("totalNumber", StartTest_fragment_v2.getTotalNumber());
                            intent1.putExtra("right_answer", StartTest_fragment_v2.getRight());
                            intent1.putExtra("contTheme", StartTest_fragment_v2.getContTheme());
                            intent1.putExtra("contCollect", StartTest_fragment_v2.getContCollect());
                            intent1.putExtra("checkQuestion1", StartTest_fragment_v2.isCheckQuestion());
                            intent1.putExtra("nameCollect", StartTestActivity.getNameCollect());
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        return view;
    }
}
