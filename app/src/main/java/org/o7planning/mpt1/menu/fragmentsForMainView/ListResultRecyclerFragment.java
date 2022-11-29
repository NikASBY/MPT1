package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Progress;
import org.o7planning.mpt1.thread.progress.AllProgressBaseThread;

import java.util.ArrayList;
import java.util.List;

public class ListResultRecyclerFragment extends Fragment {

    private RecyclerView collectRecyclerView;
    private AdapterCollectTheme mAdapterCollect;

    private List<Progress> progressList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.result_test_recycler,container,false);

        AllProgressBaseThread allProgressBaseThread = new AllProgressBaseThread("AllProgress", getContext());
        try {
            allProgressBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressList = allProgressBaseThread.getProgressAll();

        collectRecyclerView = (RecyclerView) view.findViewById(R.id.result_test_recycler);
        collectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterCollect = new AdapterCollectTheme(progressList);
        collectRecyclerView.setAdapter(mAdapterCollect);
        return view;
    }

    private class CollectHolderTheme extends RecyclerView.ViewHolder {

        private TextView numberQuestionH;
        private TextView nameQuestionH;
        private TextView failAnswerH;

        public CollectHolderTheme(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.holder_count_table_result,parent,false));
            numberQuestionH = (TextView) itemView.findViewById(R.id.numberQuestionH);
            nameQuestionH = (TextView) itemView.findViewById(R.id.nameQuestionH);
            failAnswerH = (TextView) itemView.findViewById(R.id.failAnswerH);
        }
    }

    private class AdapterCollectTheme extends RecyclerView.Adapter<CollectHolderTheme> {

        private List<Progress> progress = new ArrayList<>();

        public AdapterCollectTheme(List<Progress> progress) {
            this.progress = progress;
        }

        @NonNull
        @Override
        public CollectHolderTheme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CollectHolderTheme(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectHolderTheme holder, int position) {
            holder.numberQuestionH.setTextSize(12);
            holder.numberQuestionH.setText(Integer.valueOf(position+1).toString());
            holder.nameQuestionH.setTextSize(12);
            holder.nameQuestionH.setText(progress.get(position).questions);
            if (progress.get(position).answers.equals(progress.get(position).answerFail)) {
                holder.failAnswerH.setTextSize(12);
                holder.failAnswerH.setTextColor(getResources().getColor(R.color.Green, getActivity().getTheme()));
                holder.failAnswerH.setText(progress.get(position).answerFail);
            } else {
                holder.failAnswerH.setTextSize(12);
                holder.failAnswerH.setTextColor(getResources().getColor(R.color.Red, getActivity().getTheme()));
                holder.failAnswerH.setText(progress.get(position).answerFail);

            }
        }

        @Override
        public int getItemCount() {
            return progress.size();
        }
    }
}
