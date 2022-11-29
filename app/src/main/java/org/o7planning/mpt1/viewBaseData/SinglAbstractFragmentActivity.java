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
    public abstract Integer getContainer1();
    public abstract Integer getContainer2();
    public abstract Integer getContainer3();
    public abstract Integer getContainer4();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout1());
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(getContainer1() != null && getFragment1() != null) {
            Fragment fragment1 = fragmentManager.findFragmentById(getContainer1());
            if(fragment1 == null) {
                fragment1 = getFragment1();
                fragmentManager.beginTransaction().add(getContainer1(),fragment1).commit();
            }
        }

        if(getContainer2() != null && getFragment2() != null) {
            Fragment fragment2 = fragmentManager.findFragmentById(getContainer2());
            if(fragment2 == null) {
                fragment2 = getFragment2();
                fragmentManager.beginTransaction().add(getContainer2(),fragment2).commit();
            }
        }

        if(getContainer3() != null && getFragment3() != null) {
            Fragment fragment3 = fragmentManager.findFragmentById(getContainer3());
            if(fragment3 == null) {
                fragment3 = getFragment3();
                fragmentManager.beginTransaction().add(getContainer3(),fragment3).commit();
            }
        }

        if(getContainer4() != null && getFragment4() != null) {
            Fragment fragment4 = fragmentManager.findFragmentById(getContainer4());
            if(fragment4 == null) {
                fragment4 = getFragment4();
                fragmentManager.beginTransaction().add(getContainer4(),fragment4).commit();
            }
        }
    }
}
