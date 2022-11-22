package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.thread.assembling.AllCollectBaseThread;
import org.o7planning.mpt1.thread.assembling.UpdateCollectBaseThread;
import org.o7planning.mpt1.viewBaseData.ThemeRecycler;

import java.util.List;

public class CreateThemeFragment extends Fragment {

    private Spinner mSpinner;
    private List<Assembling> mAssemblings;
    private String[] s;
    private EditText enterThemeET;

    private TextView addThemeB;
    private TextView viewCollect;
    private TextView addQuestions;

    private AllCollectBaseThread mAllCollectBaseThread;
    private UpdateCollectBaseThread mUpdateCollectBaseThread;

    private TextView mVersion;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_theme, container, false);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        mAllCollectBaseThread = new AllCollectBaseThread("All",getContext());
        try {
            mAllCollectBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAssemblings = mAllCollectBaseThread.getAssemblingsAll();
        if(mAssemblings != null) {
            s = new String[mAssemblings.size()];
            for(int i = 0; i<s.length; i++) {
                Log.i("mAssemblings = " , mAssemblings.get(i).assembling);
                s[i] = mAssemblings.get(i).assembling;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.row,R.id.textView1,s);
        mSpinner.setAdapter(adapter);
        enterThemeET = (EditText) view.findViewById(R.id.enter_theme);
        addThemeB = (TextView) view.findViewById(R.id.add_theme);
        addThemeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSpinner.getSelectedItem() !=null && !enterThemeET.getText().toString().equals("") ){
                    mUpdateCollectBaseThread = new UpdateCollectBaseThread("Update",getContext(),
                            mSpinner.getSelectedItemId(),null,null,enterThemeET.getText().toString());
                    try {
                        mUpdateCollectBaseThread.mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                enterThemeET.setText("");
            }
        });
        viewCollect = (TextView) view.findViewById(R.id.view_theme);
        viewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThemeRecycler.class);
                intent.putExtra("posicia",mSpinner.getSelectedItemId());
                startActivity(intent);
            }
        });
        addQuestions = (TextView) view.findViewById(R.id.next_add_questions);
        addQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().remove(fragment1).add(R.id.list_tests,new CreateQuestionFragment()).commit();
            }
        });
        return view;
    }
}
