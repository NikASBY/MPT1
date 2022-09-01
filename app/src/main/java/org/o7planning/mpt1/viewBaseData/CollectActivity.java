package org.o7planning.mpt1.viewBaseData;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.o7planning.mpt1.R;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.conteiner_main);
        if(fragment == null) {
            fragment = new ConteinerRecyclerCollects();
            fm.beginTransaction().add(R.id.conteiner_main,fragment).commit();
        }
    }
}
