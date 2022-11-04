package org.o7planning.mpt1.viewBaseData;

import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;

public class CollectRecycler extends SinglAbstractFragmentActivity{
    @Override
    public Fragment getFragment1() {
        return new ConteinerRecyclerCollects();
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
    public Integer getConteiner1() {
        return R.id.conteiner_main;
    }

    @Override
    public Integer getConteiner2() {
        return null;
    }

    @Override
    public Integer getConteiner3() {
        return null;
    }

    @Override
    public Integer getConteiner4() {
        return null;
    }
}
