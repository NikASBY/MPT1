package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.BuildConfig;
import org.o7planning.mpt1.R;
import org.o7planning.mpt1.thread.InsertCollectBaseThread;
import org.o7planning.mpt1.viewBaseData.CollectRecycler;

public class CreateCollectFragment extends Fragment {

    private EditText addCollectET;
    private TextView addCollectB;
    private TextView nextAddThemeB;
    private TextView viewCollect;
    private InsertCollectBaseThread mInsertCollectBaseThread;
    private TextView mVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_collect,container,false);
        nextAddThemeB = (TextView) view.findViewById(R.id.next_add_Theme);
        nextAddThemeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().remove(fragment1).add(R.id.list_tests,new CreateThemeFragment()).commit();
            }
        });

        addCollectET = (EditText) view.findViewById(R.id.enterCollect);
        addCollectB = (TextView) view.findViewById(R.id.addCollect);
        addCollectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addCollectET.getText().toString().equals("")) {
                    mInsertCollectBaseThread = new InsertCollectBaseThread("Insert",getContext(),
                            null,addCollectET.getText().toString(),null);
                }
                addCollectET.setText("");
            }
        });

        viewCollect = (TextView) view.findViewById(R.id.viewCollect);
        viewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CollectRecycler.class);
                startActivity(intent);
            }
        });
        return view;
    }
}