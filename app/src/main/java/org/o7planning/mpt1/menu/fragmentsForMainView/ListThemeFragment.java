package org.o7planning.mpt1.menu.viewMain;

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
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;

public class ListThemeFragment extends Fragment {

    private AllQuestionsBaseThread allQuestionsBaseThread;

    private RecyclerView collectRecyclerView;
    private AdapterCollectTheme mAdapterCollect;

    private static List<String> collectThemeAd;
    private List<Questions> questions = new ArrayList<>();

    private static int contCollectL;


    public static List<String> getThemeList(List<String> listTheme, int contCollect) {
        if(listTheme != null || listTheme.size() != 0) {
            collectThemeAd = listTheme;
            contCollectL = contCollect;
        }
        return collectThemeAd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.collect_recycler,container,false);
        collectRecyclerView = (RecyclerView) v.findViewById(R.id.collect_recycler);
        collectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterCollect = new AdapterCollectTheme(collectThemeAd);
        collectRecyclerView.setAdapter(mAdapterCollect);

        //Получаю список всех вопросов в таблице
        allQuestionsBaseThread = new AllQuestionsBaseThread("AllQuestion",getContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        questions = allQuestionsBaseThread.getQuestionsAll();

        return v;
    }

    private class CollectHolderTheme extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        private TextView title;
        private TextView countTheme;

        public CollectHolderTheme(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pridstavlenie_collect_v2,parent,false));
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.nameTheme);
            title = (TextView) itemView.findViewById(R.id.title);
            countTheme = (TextView) itemView.findViewById(R.id.countTheme);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class AdapterCollectTheme extends RecyclerView.Adapter<CollectHolderTheme> {

        private List<String> collectThemeAd;

        public AdapterCollectTheme(List<String> mAssemblingsAd) {
            collectThemeAd = mAssemblingsAd;
        }

        @NonNull
        @Override
        public CollectHolderTheme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CollectHolderTheme(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectHolderTheme holder, int position) {
            List<String> listQuestion = new ArrayList<>();
            if(collectThemeAd.size() > 0) {
                String strings = collectThemeAd.get(position);
                holder.mTextView.setText(strings);
                holder.title.setText("Тема №" + position);


                //Формирую список вопросов конкретной темы
                for(int i = 0; i<questions.size(); i++) {
                    if(questions.get(i).uidCollect == contCollectL && questions.get(i).uidTheme == position) {
                        listQuestion.add(questions.get(i).questions);

                    }
                }
                holder.countTheme.setText("Вопросов: " + listQuestion.size());
            }
        }

        @Override
        public int getItemCount() {
            if(collectThemeAd == null){
                return 0;
            } else {
                return collectThemeAd.size();
            }
        }
    }
}
