package org.o7planning.mpt1.menu.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.menu.fragmentsForMainView.StartTest_fragment;
import org.o7planning.mpt1.viewBaseData.SinglAbstractFragmentActivity;

public class StratTestActivity extends SinglAbstractFragmentActivity {


    @Override
    public Fragment getFragment1() {
        return null;
    }

    @Override
    public Fragment getFragment2() {
        return new StartTest_fragment();
    }

    @Override
    public Fragment getFragment3() {
        return null;
    }

    @Override
    public Fragment getFragment4() {
        return null;
    }

    @Override
    public Integer getLayout1() {
        return R.layout.setting_test_activity;
    }

    @Override
    public Integer getConteiner1() {
        return R.id.main_test_container1;
    }

    @Override
    public Integer getConteiner2() {
        return R.id.main_test_container2;
    }

    @Override
    public Integer getConteiner3() {
        return R.id.progressBar_test_container;
    }

    @Override
    public Integer getConteiner4() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
