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
import org.o7planning.mpt1.thread.AllCollectBaseThread;

import java.util.ArrayList;
import java.util.List;

public class ConteinerRecyclerCollects extends Fragment {

    private RecyclerView collectRecyclerView;
    private AdapterCollect mAdapterCollect;

    private MyDatabase database;
    private List<Assembling> mAssemblings;
    private List<String> mCollect = new ArrayList<>();
    private String collect;
    private int posic;
    private AllCollectBaseThread mAllCollectBaseThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.collect_recycler,container,false);
        collectRecyclerView = (RecyclerView) v.findViewById(R.id.collect_recycler);
        collectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAllCollectBaseThread = new AllCollectBaseThread("All",getContext());
        try {
            mAllCollectBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAssemblings = mAllCollectBaseThread.getAssemblingsAll();
        for(int i = 0; i<mAssemblings.size(); i++) {
            collect = mAssemblings.get(i).assembling;
            mCollect.add(collect);
        }

        mAdapterCollect = new AdapterCollect(mCollect);
        collectRecyclerView.setAdapter(mAdapterCollect);
        return v;
    }

    private class CollectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        private TextView titleCollect;
        private long pos;

        public CollectHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pridstavlenie_collect,parent,false));
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.nameTheme);
            titleCollect = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), ThemeRecycler.class);
            intent.putExtra("posicia", pos);
            startActivity(intent);
        }

        public void setPosicion(long pos) {
            this.pos = pos;
        }
    }

    private class AdapterCollect extends RecyclerView.Adapter<CollectHolder> {

        private List<String> mAssemblingsAd;

        public AdapterCollect(List<String> mAssemblingsAd) {
            this.mAssemblingsAd = mAssemblingsAd;
        }

        @NonNull
        @Override
        public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CollectHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectHolder holder, int position) {
            String strings = mAssemblingsAd.get(position);
            holder.mTextView.setText(strings);
            holder.titleCollect.setText("Коллекция №" + position);
            holder.setPosicion((long)position);
        }

        @Override
        public int getItemCount() {
            return mAssemblingsAd.size();
        }
    }
}
