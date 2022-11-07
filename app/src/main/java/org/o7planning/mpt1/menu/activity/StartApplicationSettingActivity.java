package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.thread.AllSettingsThread;
import org.o7planning.mpt1.thread.InsertSettingsThread;

import java.util.List;
import java.util.Locale;

public class StartApplicationSettingActivity extends AppCompatActivity {

    private Locale locale;
    private Configuration configuration;

    private AllSettingsThread allSettingsThread;
    private Boolean changeLangRu;
    private Boolean changeLangEn;
    private List<Settingss> settingssList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_load_fragment);

        allSettingsThread = new AllSettingsThread("All_settings", getApplicationContext());
        try {
            allSettingsThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        settingssList = allSettingsThread.getSettingsAll();
        Log.i("settingssList", settingssList.toString());
        if(settingssList.size() == 1) {
            changeLangRu = settingssList.get(0).changeLangRu;
            changeLangEn = settingssList.get(0).changeLangEn;
        }


        if(changeLangRu == null && changeLangEn == null) {
            InsertSettingsThread insertSettingsThread = new InsertSettingsThread("insert_base_lang", getApplicationContext(), false, true);
            locale = new Locale("en");
            Locale.setDefault(locale);
            configuration = new Configuration();
            configuration.setLocale(locale);
            getBaseContext().getResources().updateConfiguration(configuration, null);
            Log.i("(StartAppSetting) Lang: ", locale.getLanguage());
        }

        Intent intent = new Intent(this, NewMainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
