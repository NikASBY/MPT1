package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;

public class ProgressBarLoad extends Fragment {

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.progress_bar_load_fragment,container,false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_load);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        return view;
    }
}
