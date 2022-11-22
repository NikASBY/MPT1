package org.o7planning.mpt1.viewBaseData;

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
import org.o7planning.mpt1.thread.question.AllQuestionsBaseThread;

import java.util.ArrayList;
import java.util.List;

public class ConteinerRecyclerQuestion extends Fragment {

    private RecyclerView questionRecyclerView;
    private QuestionAdapter mAdapterQuestion;

    private AllQuestionsBaseThread allQuestionsBaseThread;
    private List<Questions> questions = new ArrayList<>();
    private List<String> listQuestion = new ArrayList<>();
    private List<String> listAnswer = new ArrayList<>();

    private int posicCollect;
    private int posicTheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.collect_recycler,container,false);
        questionRecyclerView = (RecyclerView) v.findViewById(R.id.collect_recycler);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        posicCollect = (int)getActivity().getIntent().getLongExtra("posiciaCollect",0);
        posicTheme = (int)getActivity().getIntent().getLongExtra("posiciaTheme",0);

        allQuestionsBaseThread = new AllQuestionsBaseThread("AllQuestion",getContext());
        try {
            allQuestionsBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        questions = allQuestionsBaseThread.getQuestionsAll();
        for(int i = 0; i<questions.size(); i++) {
            if(questions.get(i).uidCollect == posicCollect && questions.get(i).uidTheme == posicTheme) {
                listQuestion.add(questions.get(i).questions);
                listAnswer.add(questions.get(i).answers);
            }
        }
        
        mAdapterQuestion = new QuestionAdapter(listQuestion,listAnswer);
        questionRecyclerView.setAdapter(mAdapterQuestion);
        return v;
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView mTextQuestion;
        private TextView mTextAnswer;
        private TextView titleQuestion;
        private TextView titleAnswer;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.predstavlenie_question, parent, false));
            mTextQuestion = (TextView) itemView.findViewById(R.id.textQuestion);
            mTextAnswer = (TextView) itemView.findViewById(R.id.textAnswer);
            titleQuestion = (TextView) itemView.findViewById(R.id.titleQuestion);
            titleAnswer = (TextView) itemView.findViewById(R.id.titleAnswer);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

        private List<String> listQuestion;
        private List<String> listAnswer;

        public QuestionAdapter(List<String> listQuestion, List<String> listAnswer) {
            this.listQuestion = listQuestion;
            this.listAnswer = listAnswer;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestionHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            String question = listQuestion.get(position);
            String answer = listAnswer.get(position);
            holder.mTextAnswer.setText(answer);
            holder.mTextQuestion.setText(question);
            holder.titleQuestion.setText("Вопрос №" + position);
            holder.titleAnswer.setText("Ответ №" + position);
        }

        @Override
        public int getItemCount() {
            return listAnswer.size();
        }
    }
}
