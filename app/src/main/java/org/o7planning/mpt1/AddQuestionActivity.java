package org.o7planning.mpt1;

import android.content.Intent;
import android.icu.util.VersionInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.thread.AllCollectBaseThread;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;
import org.o7planning.mpt1.thread.InsertQuestionsBaseThread;
import org.o7planning.mpt1.thread.UpdateCollectBaseThread;
import org.o7planning.mpt1.thread.UpdateQuestionsBaseThread;
import org.o7planning.mpt1.viewBaseData.QuestionActivity;
import org.o7planning.mpt1.viewBaseData.ThemeActivity;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    private Button addQuestion;

    private EditText enterQuestion;
    private EditText enterAnswer;

    private Spinner selectCollect;
    private Spinner selectTheme;

    private AllCollectBaseThread allCollectBaseThread;

    private InsertQuestionsBaseThread mInsertQuestionsBaseThread;

    private List<Assembling> mAssemblingList;
    private String[] sCollect;
    private String[] sTheme;
    private List<String> collectTheme = new ArrayList<>();
    private List<List<String>> mCollect = new ArrayList<>();
    private TextView mVersion;

    private Button viewQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question);

        allCollectBaseThread = new AllCollectBaseThread("All",getApplicationContext());
        try {
            allCollectBaseThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAssemblingList = allCollectBaseThread.getAssemblingsAll();
        if(mAssemblingList != null) {
            sCollect = new String[mAssemblingList.size()];
            for(int i = 0; i<sCollect.length; i++) {
                Log.i("mAssemblings = " , mAssemblingList.get(i).assembling);
                sCollect[i] = mAssemblingList.get(i).assembling;
            }

            for(int i = 0; i<mAssemblingList.size(); i++) {
                collectTheme = mAssemblingList.get(i).theme;
                mCollect.add(collectTheme);
            }
        }

        selectCollect = (Spinner) findViewById(R.id.spinner_collect);
        ArrayAdapter<String> adapterCollect = new ArrayAdapter<>(this,R.layout.row,R.id.textView1,sCollect);
        selectCollect.setAdapter(adapterCollect);
        selectCollect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collectTheme = mCollect.get(i);
                sTheme = new String[collectTheme.size()];
                for(int j = 0; j<sTheme.length; j++) {
                    sTheme[j] = collectTheme.get(j);
                }

                selectTheme = (Spinner) findViewById(R.id.spinner_theme);
                ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(getApplicationContext(),R.layout.row,R.id.textView1,sTheme);
                selectTheme.setAdapter(adapterTheme);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        enterAnswer = (EditText) findViewById(R.id.enter_answer);
        enterQuestion = (EditText) findViewById(R.id.enter_question);
        addQuestion = (Button) findViewById(R.id.add_question);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectCollect.getSelectedItem() != null) {
                    if(!mCollect.get(0).get(0).equals("")) {
                        if(!enterQuestion.getText().toString().equals("")){
                            if(!enterAnswer.getText().toString().equals("")) {
                                mInsertQuestionsBaseThread = new InsertQuestionsBaseThread("Insert2", getApplicationContext(),selectCollect.getSelectedItemId(),
                                        selectTheme.getSelectedItemId(),enterQuestion.getText().toString(),
                                        enterAnswer.getText().toString());
                                try {
                                    mInsertQuestionsBaseThread.mThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });

        mVersion = (TextView) findViewById(R.id.version);
        mVersion.setText(BuildConfig.VERSION_NAME);

        viewQuestion = (Button) findViewById(R.id.view_questions);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAssemblingList.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                    intent.putExtra("posiciaTheme",selectTheme.getSelectedItemId());
                    intent.putExtra("posiciaCollect",selectCollect.getSelectedItemId());
                    startActivity(intent);
                }
            }
        });
    }
}
