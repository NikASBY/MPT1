package org.o7planning.mpt1.viewBaseData;

import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;

public class QuestionRecycler extends SinglAbstractFragmentActivity{
    @Override
    public Fragment getFragment1() {
        return new ConteinerRecyclerQuestion();
    }

    @Override
    public Fragment getFragment2() {
        return null;
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
        return R.layout.conteiner_main;
    }

    @Override
    public Integer getContainer1() {
        return R.id.conteiner_main;
    }

    @Override
    public Integer getContainer2() {
        return null;
    }

    @Override
    public Integer getContainer3() {
        return null;
    }

    @Override
    public Integer getContainer4() {
        return null;
    }
}
