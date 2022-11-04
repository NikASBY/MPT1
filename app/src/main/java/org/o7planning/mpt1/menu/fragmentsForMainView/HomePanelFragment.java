package org.o7planning.mpt1.menu.viewMain;

import android.content.Intent;
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
import org.o7planning.mpt1.menu.StartTest;

public class HomePanelFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_panel_v2,container,false);

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.home_panel_v2);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   case R.id.button_home:

                       FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                       Fragment listTests2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                       Fragment selectUser2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user);
                       if(selectUser2 == null) {
                           fragmentManager1.beginTransaction().remove(listTests2).add(R.id.select_user,new SelectCollectFragment()).add(R.id.list_tests,new ListThemeFragment()).commit();
                       }

                       break;
                   case R.id.button_create:

                       FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                       Fragment listTests = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                       Fragment selectUser = getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user);
                       if(selectUser != null) {
                           fragmentManager2.beginTransaction().remove(selectUser).remove(listTests).add(R.id.list_tests,new CreateCollectFragment()).commit();
                       }

                       break;
                   case R.id.button_setting:
                       Intent intent3 = new Intent(getContext(), StartTest.class);
                       startActivity(intent3);
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
