package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
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
import org.o7planning.mpt1.menu.activity.SettingTestActivity;
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;

public class ListThemeFragment extends Fragment {

    private AllQuestionsBaseThread allQuestionsBaseThread;

    private RecyclerView collectRecyclerView;
    private AdapterCollectTheme mAdapterCollect;

    private static List<String> collectThemeAd;
    private List<Questions> questions = new ArrayList<>();

    private static int contCollectL;
    private static String nameCollectL;
    //private String nameTheme;


    public static List<String> getThemeList(List<String> listTheme, int contCollect, String nameCollect) {
        if(listTheme != null || listTheme.size() != 0) {
            collectThemeAd = listTheme;
            contCollectL = contCollect;
            nameCollectL = nameCollect;
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

        private int contCollect;
        private int contTheme;
        private String nameTheme;

        public CollectHolderTheme(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pridstavlenie_collect_v2,parent,false));
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.nameTheme);
            title = (TextView) itemView.findViewById(R.id.title);
            countTheme = (TextView) itemView.findViewById(R.id.countTheme);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), SettingTestActivity.class);
            intent.putExtra("contCollect", contCollect);
            intent.putExtra("contTheme", contTheme);
            intent.putExtra( "nameCollect", nameCollectL);
            intent.putExtra("nameTheme", nameTheme);
            startActivity(intent);
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
                String nameTheme = collectThemeAd.get(position);
                holder.mTextView.setText(nameTheme);
                holder.title.setText("Тема №" + position);
                holder.nameTheme = nameTheme;
                //Формирую список вопросов конкретной темы
                for(int i = 0; i<questions.size(); i++) {
                    if(questions.get(i).uidCollect == contCollectL && questions.get(i).uidTheme == position) {
                        listQuestion.add(questions.get(i).questions);
                    }
                }
                holder.countTheme.setText("Вопросов: " + listQuestion.size());
            }
            holder.contCollect = contCollectL;
            int pos = position;
            holder.contTheme = pos;
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
