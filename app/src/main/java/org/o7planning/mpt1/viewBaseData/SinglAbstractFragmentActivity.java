package org.o7planning.mpt1.viewBaseData;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SinglAbstractFragmentActivity extends AppCompatActivity {

    public abstract Fragment getFragment1();
    public abstract Fragment getFragment2();
    public abstract Fragment getFragment3();
    public abstract Fragment getFragment4();
    public abstract Integer getLayout1();
    public abstract Integer getConteiner1();
    public abstract Integer getConteiner2();
    public abstract Integer getConteiner3();
    public abstract Integer getConteiner4();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout1());
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(getConteiner1() != null && getFragment1() != null) {
            Fragment fragment1 = fragmentManager.findFragmentById(getConteiner1());
            if(fragment1 == null) {
                fragment1 = getFragment1();
                fragmentManager.beginTransaction().add(getConteiner1(),fragment1).commit();
            }
        }

        if(getConteiner2() != null && getFragment2() != null) {
            Fragment fragment2 = fragmentManager.findFragmentById(getConteiner2());
            if(fragment2 == null) {
                fragment2 = getFragment2();
                fragmentManager.beginTransaction().add(getConteiner2(),fragment2).commit();
            }
        }

        if(getConteiner3() != null && getFragment3() != null) {
            Fragment fragment3 = fragmentManager.findFragmentById(getConteiner3());
            if(fragment3 == null) {
                fragment3 = getFragment3();
                fragmentManager.beginTransaction().add(getConteiner3(),fragment3).commit();
            }
        }

        if(getConteiner4() != null && getFragment4() != null) {
            Fragment fragment4 = fragmentManager.findFragmentById(getConteiner4());
            if(fragment4 == null) {
                fragment4 = getFragment4();
                fragmentManager.beginTransaction().add(getConteiner4(),fragment4).commit();
            }
        }
    }
}
