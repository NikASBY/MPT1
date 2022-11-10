package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.menu.fragmentsForMainView.HomePanelFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.ListThemeFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.SelectCollectFragment;
import org.o7planning.mpt1.thread.AllSettingsThread;
import org.o7planning.mpt1.thread.InsertSettingsThread;
import org.o7planning.mpt1.viewBaseData.SinglAbstractFragmentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewMainMenu extends SinglAbstractFragmentActivity {

    private List<Assembling> listAssembling = new ArrayList<>();

    public static File getDirectory() {
        return directory;
    }

    public static File getDirectory2() {
        return directory2;
    }

    private static File directory;
    private static File directory2;
    private Intent intent;

    private Locale locale;
    private Configuration configuration;

    private AllSettingsThread allSettingsThread;
    private Boolean changeLangRu;
    private Boolean changeLangEn;
    private List<Settingss> settingssList;

    @Override
    public Integer getLayout1() {
        return R.layout.new_layout_app_start;
    }

    @Override
    public Fragment getFragment1() {
        return new HomePanelFragment();
    }

    @Override
    public Fragment getFragment2() {
        return new ListThemeFragment();
    }

    @Override
    public Fragment getFragment3() {
        return new SelectCollectFragment();
    }

    @Override
    public Fragment getFragment4() {
        return null;
    }

    @Override
    public Integer getConteiner1() {
        return R.id.home_panel_conteiner;
    }

    @Override
    public Integer getConteiner2() {
        return R.id.list_tests;
    }

    @Override
    public Integer getConteiner3() {
        return R.id.select_user;
    }

    @Override
    public Integer getConteiner4() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout_app_start);
        if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }
        directory = new File(Environment.getExternalStorageDirectory()+ "/MPT1/CreateIsFile/");
        directory.mkdirs();
        directory2 = new File(Environment.getExternalStorageDirectory()+ "/MPT1/UserCollect/");
        directory2.mkdirs();
        intent = new Intent(getApplicationContext(), NewMainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        allSettingsThread = new AllSettingsThread("All_settings", getApplicationContext());
        try {
            allSettingsThread.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        settingssList = allSettingsThread.getSettingsAll();
        if(settingssList.size() == 1) {
            changeLangRu = settingssList.get(0).changeLangRu;
            changeLangEn = settingssList.get(0).changeLangEn;
            if(changeLangRu) {
                InsertSettingsThread insertSettingsThread = new InsertSettingsThread("insert_base_lang", getApplicationContext(), true, false);
                locale = new Locale("ru");
                Locale.setDefault(locale);
                configuration = new Configuration();
                configuration.setLocale(locale);
                getBaseContext().getResources().updateConfiguration(configuration, null);
                getSupportFragmentManager().beginTransaction().replace(R.id.list_tests, new ListThemeFragment()).replace(R.id.select_user, new SelectCollectFragment()).commit();
                Log.i("(NewMainMenu) Lang: ", locale.getLanguage());
            } else {
                InsertSettingsThread insertSettingsThread = new InsertSettingsThread("insert_base_lang", getApplicationContext(), false, true);
                locale = new Locale("en");
                Locale.setDefault(locale);
                configuration = new Configuration();
                configuration.setLocale(locale);
                getBaseContext().getResources().updateConfiguration(configuration, null);
                getSupportFragmentManager().beginTransaction().replace(R.id.list_tests, new ListThemeFragment()).replace(R.id.select_user, new SelectCollectFragment()).commit();
                Log.i("(NewMainMenu) Lang: ", locale.getLanguage());
            }
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
    }
}
