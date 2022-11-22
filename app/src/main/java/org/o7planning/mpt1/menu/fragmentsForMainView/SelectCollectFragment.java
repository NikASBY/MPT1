package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.thread.assembling.AllCollectBaseThread;

import java.util.ArrayList;
import java.util.List;

public class SelectCollectFragment extends Fragment {

    private Spinner selectCollect;
    private AllCollectBaseThread allCollectBaseThread;

    private List<Assembling> mAssemblingList;
    private String[] sCollect;
    private List<String> collectTheme = new ArrayList<>();
    private List<List<String>> mCollect = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_collect_fragment,container,false);
        selectCollect = (Spinner) view.findViewById(R.id.spinner);

        allCollectBaseThread = new AllCollectBaseThread("All",getContext());
        try {
            allCollectBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAssemblingList = allCollectBaseThread.getAssemblingsAll();
        Log.i("SelectCollect (mAssemblingList) = " + mAssemblingList.size(), " ");
        if(mAssemblingList != null) {
            sCollect = new String[mAssemblingList.size()];
            for(int i = 0; i<sCollect.length; i++) {
                Log.i("mAssemblings = " + i + " - ", mAssemblingList.get(i).assembling);
                sCollect[i] = mAssemblingList.get(i).assembling;
            }
            for(int i = 0; i<mAssemblingList.size(); i++) {
                collectTheme = mAssemblingList.get(i).theme;
                mCollect.add(collectTheme);
            }
        }

        if(mAssemblingList.size() == 0) {
            collectTheme.clear();
            ListThemeFragment.getThemeList(collectTheme, 0, "");//Передать sThrme в ListThemeFragment
        }

        ArrayAdapter<String> adapterCollect = new ArrayAdapter<String>(getContext(),R.layout.row,R.id.textView1,sCollect);
        selectCollect.setAdapter(adapterCollect);
        selectCollect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mCollect.get(i).size() != 0) {
                    ListThemeFragment.getThemeList(mCollect.get(i), i, mAssemblingList.get(i).assembling);//Передать sThrme в ListThemeFragment
                    Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                    FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                    fragmentManager1.beginTransaction().remove(fragment1).add(R.id.list_tests,new ListThemeFragment()).commit();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }
}
