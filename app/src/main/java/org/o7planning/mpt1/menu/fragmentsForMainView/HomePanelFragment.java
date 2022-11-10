package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.o7planning.mpt1.Dialog.AboutFragment;
import org.o7planning.mpt1.R;

public class HomePanelFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Fragment listTests;
    private Fragment createCollect;
    private Fragment LangSetting;
    private Fragment selectUser;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_panel_v2,container,false);

        fragmentManager = getActivity().getSupportFragmentManager();
        listTests = new ListThemeFragment();
        createCollect = new CreateCollectFragment();
        LangSetting = new ChangeLangSettingFragment();
        selectUser = new SelectCollectFragment();

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.home_panel_v2);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   case R.id.button_home:
                       fragmentManager.beginTransaction().replace(R.id.select_user, selectUser).replace(R.id.list_tests, listTests).commit();
                       break;
                   case R.id.button_create:
                       fragmentManager.beginTransaction().replace(R.id.list_tests, createCollect).commit();
                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user) != null) {
                            fragmentManager.beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user)).commit();
                        }
                       break;
                   case R.id.button_setting:
                       fragmentManager.beginTransaction().replace(R.id.list_tests, LangSetting).commit();
                       if(getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user) != null) {
                           fragmentManager.beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user)).commit();
                       }
                       break;
                   case R.id.button_about:
                       FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                       AboutFragment aboutFragment = new AboutFragment();
                       aboutFragment.show(fragmentManager3, "About");
                       break;
                   }
                   return true;
               }
           });
        return view;
    }
}
