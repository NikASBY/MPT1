package org.o7planning.mpt1.menu.viewMain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.o7planning.mpt1.BuildConfig;
import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.Questions;
import org.o7planning.mpt1.thread.AllCollectBaseThread;
import org.o7planning.mpt1.thread.AllQuestionsBaseThread;
import org.o7planning.mpt1.thread.InsertQuestionsBaseThread;
import org.o7planning.mpt1.viewBaseData.QuestionRecycler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateQuestionFragment extends Fragment {

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
    private Button saveBase;

    private File filePath;
    private List<Questions> questions = new ArrayList<>();
    private List<String> listQuestion = new ArrayList<>();
    private List<String> listAnswer = new ArrayList<>();

    private HSSFCell cell1;
    private HSSFCell cell2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_question, container, false);

        /*if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }*/

        allCollectBaseThread = new AllCollectBaseThread("All",getContext());
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

        selectTheme = (Spinner) view.findViewById(R.id.spinner_theme);
        selectCollect = (Spinner) view.findViewById(R.id.spinner_collect);
        ArrayAdapter<String> adapterCollect = new ArrayAdapter<>(getContext(),R.layout.row,R.id.textView1,sCollect);
        selectCollect.setAdapter(adapterCollect);
        selectCollect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collectTheme = mCollect.get(i);
                sTheme = new String[collectTheme.size()];
                for(int j = 0; j<sTheme.length; j++) {
                    sTheme[j] = collectTheme.get(j);
                }

                ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(getContext(),R.layout.row,R.id.textView1,sTheme);
                selectTheme.setAdapter(adapterTheme);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        enterAnswer = (EditText) view.findViewById(R.id.enter_answer);
        enterQuestion = (EditText) view.findViewById(R.id.enter_question);
        addQuestion = (Button) view.findViewById(R.id.add_question);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectCollect.getSelectedItem() != null) {
                    if(!mCollect.get(0).get(0).equals("")) {
                        if(!enterQuestion.getText().toString().equals("")){
                            if(!enterAnswer.getText().toString().equals("")) {
                                mInsertQuestionsBaseThread = new InsertQuestionsBaseThread("Insert2", getContext(),selectCollect.getSelectedItemId(),
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
                enterAnswer.setText("");
                enterQuestion.setText("");
            }
        });

        mVersion = (TextView) view.findViewById(R.id.version);
        mVersion.setText(BuildConfig.VERSION_NAME);
        viewQuestion = (Button)  view.findViewById(R.id.view_questions);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAssemblingList.size() != 0) {
                    Intent intent = new Intent(getContext(), QuestionRecycler.class);
                    intent.putExtra("posiciaTheme",selectTheme.getSelectedItemId());
                    intent.putExtra("posiciaCollect",selectCollect.getSelectedItemId());
                    startActivity(intent);
                }
            }
        });

        saveBase = (Button)  view.findViewById(R.id.save_base);
        saveBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cont = 0;
                try {
                    for(int i = 0; i<mAssemblingList.size(); i++) {
                        filePath = new File(Environment.getExternalStorageDirectory() + "/MPT1/UserCollect/" + mAssemblingList.get(i).assembling + ".xls");
                        HSSFWorkbook work = new HSSFWorkbook();
                        for(int j = 0; j<mCollect.get(i).size(); j++) {
                            cont = 0;
                            HSSFSheet sheet1;
                            Log.i("Theme =", mCollect.get(i).get(j));
                            if(!mCollect.get(i).get(j).equals("")) {
                                sheet1 = work.createSheet(mCollect.get(i).get(j));
                                AllQuestionsBaseThread allQuestionsBaseThread = new AllQuestionsBaseThread("AllQuestion",getContext());
                                try {
                                    allQuestionsBaseThread.mThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                questions = allQuestionsBaseThread.getQuestionsAll();
                                for(int q = 0; q<questions.size(); q++) {
                                    if(questions.get(q).uidCollect == i && questions.get(q).uidTheme == j) {
                                        HSSFRow row1 = sheet1.createRow(cont);
                                        cell1 = row1.createCell(0);
                                        cell2 = row1.createCell(1);
                                        cell1.setCellValue(questions.get(q).questions);
                                        cell2.setCellValue(questions.get(q).answers);
                                        cont +=1;
                                    }
                                    if(!filePath.exists()) {
                                        filePath.createNewFile();
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                                    work.write(fileOutputStream);
                                    if(fileOutputStream!=null) {
                                        fileOutputStream.flush();
                                        fileOutputStream.close();
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
