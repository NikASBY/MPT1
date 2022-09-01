package org.o7planning.mpt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.thread.InsertCollectBaseThread;
import org.o7planning.mpt1.viewBaseData.CollectActivity;

public class AddCollectActivity extends AppCompatActivity {

    private EditText addCollectET;
    private Button addCollectB;
    private Button nextAddThemeB;
    private Button viewCollect;
    private InsertCollectBaseThread mInsertCollectBaseThread;
    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_collect);

        nextAddThemeB = (Button) findViewById(R.id.next_add_Theme);
        nextAddThemeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCollectActivity.this, AddThemeActivity.class);
                startActivity(intent);
            }
        });

        addCollectET = (EditText) findViewById(R.id.enterCollect);
        addCollectB = (Button) findViewById(R.id.addCollect);
        addCollectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addCollectET.getText().toString().equals("")) {
                    mInsertCollectBaseThread = new InsertCollectBaseThread("Insert",getApplicationContext(),
                            null,addCollectET.getText().toString(),null);
                }
            }
        });

        viewCollect = (Button)findViewById(R.id.viewCollect);
        viewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CollectActivity.class);
                startActivity(intent);
            }
        });

        mVersion = (TextView) findViewById(R.id.version);
        mVersion.setText(BuildConfig.VERSION_NAME);
    }
}