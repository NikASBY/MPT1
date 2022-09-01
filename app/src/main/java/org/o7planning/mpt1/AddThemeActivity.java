package org.o7planning.mpt1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.thread.AllCollectBaseThread;
import org.o7planning.mpt1.thread.UpdateCollectBaseThread;
import org.o7planning.mpt1.viewBaseData.ThemeActivity;

import java.util.List;

public class AddThemeActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private List<Assembling> mAssemblings;
    private String[] s;
    private EditText enterThemeET;
    private Button addThemeB;
    private Button viewCollect;
    private AllCollectBaseThread mAllCollectBaseThread;
    private UpdateCollectBaseThread mUpdateCollectBaseThread;
    private Button addQuestions;
    private TextView mVersion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_theme);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        mAllCollectBaseThread = new AllCollectBaseThread("All",getApplicationContext());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row,R.id.textView1,s);
        mSpinner.setAdapter(adapter);

        enterThemeET = (EditText) findViewById(R.id.enter_theme);

        addThemeB = (Button) findViewById(R.id.add_theme);
        addThemeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSpinner.getSelectedItem() !=null && !enterThemeET.getText().toString().equals("") ){
                    mUpdateCollectBaseThread = new UpdateCollectBaseThread("Update",getApplicationContext(),
                            mSpinner.getSelectedItemId(),null,null,enterThemeET.getText().toString());
                    try {
                        mUpdateCollectBaseThread.mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        viewCollect = (Button)findViewById(R.id.view_theme);
        viewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemeActivity.class);
                intent.putExtra("posicia",mSpinner.getSelectedItemId());
                startActivity(intent);
            }
        });

        addQuestions = (Button) findViewById(R.id.next_add_questions);
        addQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        mVersion = (TextView) findViewById(R.id.version);
        mVersion.setText(BuildConfig.VERSION_NAME);
    }
}
