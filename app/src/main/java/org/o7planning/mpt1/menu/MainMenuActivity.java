package org.o7planning.mpt1.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.AddCollectActivity;
import org.o7planning.mpt1.R;

public class MainMenuActivity extends AppCompatActivity {

    private Button mStartTest;
    private Button mCreateBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        mCreateBase = (Button) findViewById(R.id.createBase);
        mCreateBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, AddCollectActivity.class);
                startActivity(intent);
            }
        });

        mStartTest = (Button) findViewById(R.id.startTest);
        mStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainMenuActivity.this, StartTest.class);
                startActivity(intent2);
            }
        });
    }
}
