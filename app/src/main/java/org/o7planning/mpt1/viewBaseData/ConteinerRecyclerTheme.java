package org.o7planning.mpt1.viewBaseData;

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
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.MyDatabase;
import org.o7planning.mpt1.database.SinglDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConteinerRecyclerTheme extends Fragment {

    private RecyclerView collectRecyclerView;
    private AdapterCollectTheme mAdapterCollect;

    private MyDatabase database;
    private List<Assembling> mAssemblings;
    private List<List<String>> mCollect = new ArrayList<>();
    private List<String> collectTheme = new ArrayList<>();
    private long posic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.collect_recycler,container,false);
        collectRecyclerView = (RecyclerView) v.findViewById(R.id.collect_recycler);
        collectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        posic = (int)getActivity().getIntent().getLongExtra("posicia",0);//posicia Collect


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                database = SinglDatabase.getInstance(getContext()).getMyDatabase();
                mAssemblings = database.assemblingDao().getAll();
                for(int i = 0; i<mAssemblings.size(); i++) {
                    collectTheme = mAssemblings.get(i).theme;
                    mCollect.add(collectTheme);
                }
            }
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAdapterCollect = new AdapterCollectTheme(mCollect);
        collectRecyclerView.setAdapter(mAdapterCollect);
        return v;
    }

    private class CollectHolderTheme extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        private TextView title;
        private long pos;

        public CollectHolderTheme(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pridstavlenie_collect,parent,false));
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),QuestionActivity.class);
            intent.putExtra("posiciaCollect",posic);
            intent.putExtra("posiciaTheme", pos);
            startActivity(intent);
        }

        public void setPosicion(long pos) {
            this.pos = pos;
        }
    }

    private class AdapterCollectTheme extends RecyclerView.Adapter<CollectHolderTheme> {

        private List<String> collectThemeAd;

        public AdapterCollectTheme(List<List<String>> mAssemblingsAd) {

            if(mAssemblingsAd.size() != 0 ) {
                collectThemeAd = mAssemblingsAd.get((int)posic);
            } else {
                collectThemeAd = new ArrayList<>();
            }
        }

        @NonNull
        @Override
        public CollectHolderTheme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CollectHolderTheme(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectHolderTheme holder, int position) {
            String strings = collectThemeAd.get(position);
            holder.mTextView.setText(strings);
            holder.title.setText("Тема №" + position);
            holder.setPosicion((long)position);
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
