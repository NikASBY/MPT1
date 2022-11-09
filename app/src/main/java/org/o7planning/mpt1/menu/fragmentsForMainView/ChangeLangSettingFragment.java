package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.thread.AllSettingsThread;
import org.o7planning.mpt1.thread.InsertSettingsThread;
import org.o7planning.mpt1.thread.UpdateSettingssThread;

import java.util.List;
import java.util.Locale;

public class ChangeLangSettingFragment extends Fragment {

    private TextView buttonSaveSetting;
    private RadioGroup radioGroup;
    private RadioButton radioButtonRu;
    private RadioButton radioButtonEn;

    private Locale locale;
    private Configuration configuration;

    private List<Settingss> settingssList;
    private Boolean changeLangRu;
    private Boolean changeLangEn;

    private AllSettingsThread allSettingsThread;
    private UpdateSettingssThread updateSettingssThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.setting_fragment,container,false);

        allSettingsThread = new AllSettingsThread("All_settings", getContext());
        try {
            allSettingsThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        settingssList = allSettingsThread.getSettingsAll();
        changeLangRu = settingssList.get(0).changeLangRu;
        changeLangEn = settingssList.get(0).changeLangEn;

        radioButtonRu = (RadioButton) view.findViewById(R.id.radioButtonRussian);
        if (changeLangRu) {
            radioButtonRu.setChecked(true);
        }

        radioButtonEn = (RadioButton) view.findViewById(R.id.radioButtonEnglish);
        if (changeLangEn) {
            radioButtonEn.setChecked(true);
        }

        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonRussian:
                        locale = new Locale("ru");
                        Locale.setDefault(locale);
                        configuration = new Configuration();
                        configuration.locale = locale;
                        updateSettingssThread = new UpdateSettingssThread("Update_changeLang", getContext(), true, false);
                        try {
                            updateSettingssThread.mThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().getBaseContext().getResources().updateConfiguration(configuration, null);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.list_tests, new ChangeLangSettingFragment()).commit();
                        break;
                    case R.id.radioButtonEnglish:
                        locale = new Locale("en");
                        Locale.setDefault(locale);
                        configuration = new Configuration();
                        configuration.locale = locale;
                        updateSettingssThread = new UpdateSettingssThread("Update_changeLang", getContext(), false, true);
                        try {
                            updateSettingssThread.mThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().getBaseContext().getResources().updateConfiguration(configuration, null);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.list_tests, new ChangeLangSettingFragment()).commit();
                        break;
                }
            }
        });

        buttonSaveSetting = (TextView) view.findViewById(R.id.save_setting);
        buttonSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
