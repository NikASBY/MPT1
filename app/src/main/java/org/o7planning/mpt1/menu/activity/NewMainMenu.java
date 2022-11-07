package org.o7planning.mpt1.menu.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.o7planning.mpt1.Dialog.AboutFragment;
import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.menu.fragmentsForMainView.ChangeLangSettingFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.CreateCollectFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.HomePanelFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.ListThemeFragment;
import org.o7planning.mpt1.menu.fragmentsForMainView.SelectCollectFragment;
import org.o7planning.mpt1.thread.AllCleanAssemblingBaseThread;
import org.o7planning.mpt1.thread.AllCleanQuestionBaseThread;
import org.o7planning.mpt1.thread.AllCollectBaseThread;
import org.o7planning.mpt1.thread.AllSettingsThread;
import org.o7planning.mpt1.thread.InsertCollectBaseThread;
import org.o7planning.mpt1.thread.InsertQuestionsBaseThread;
import org.o7planning.mpt1.thread.InsertSettingsThread;
import org.o7planning.mpt1.thread.UpdateCollectBaseThread;
import org.o7planning.mpt1.viewBaseData.SinglAbstractFragmentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class NewMainMenu extends SinglAbstractFragmentActivity {

    private List<Assembling> listAssembling = new ArrayList<>();
    private File directory;
    private File directory2;
    private Intent intent;
    private Fragment fragment;
    private Fragment fragment2;
    private FragmentManager fragmentManager;

    private Locale locale;
    private Configuration configuration;

    private AllSettingsThread allSettingsThread;
    private Boolean changeLangRu;
    private Boolean changeLangEn;
    private List<Settingss> settingssList;

    private Fragment listTests;
    private Fragment createCollect;
    private Fragment LangSetting;
    private Fragment selectUser;
    private FragmentManager fragmentManager2;


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


        fragmentManager2 = getSupportFragmentManager();
        listTests = new ListThemeFragment();
        createCollect = new CreateCollectFragment();
        LangSetting = new ChangeLangSettingFragment();
        selectUser = new SelectCollectFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.in_memu1:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<File> listFile = Arrays.asList(directory.listFiles());
                            for (int i = 0; i < listFile.size(); i++) {
                                Boolean flag = true;
                                AllCollectBaseThread allCollectBaseThread = new AllCollectBaseThread("All", getApplicationContext());
                                allCollectBaseThread.mThread.join();
                                listAssembling = allCollectBaseThread.getAssemblingsAll();
                                int count = listAssembling.size();
                                for (int h = 0; h < listAssembling.size(); h++) {
                                    if (listAssembling.get(h).assembling.equals(listFile.get(i).getName().replace(".xls", ""))) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    File filePath = listFile.get(i).getAbsoluteFile();
                                    FileInputStream fis = new FileInputStream(filePath);
                                    HSSFWorkbook myExcelBook = new HSSFWorkbook(fis);
                                    InsertCollectBaseThread insertCollectBaseThread = new InsertCollectBaseThread("InsertCollect", getApplicationContext(), null, filePath.getName().replace(".xls", ""), null);
                                    insertCollectBaseThread.mThread.join();
                                    Iterator<Sheet> sheetIterator = myExcelBook.sheetIterator();
                                    while (sheetIterator.hasNext()) {
                                        Sheet mySheet = sheetIterator.next();
                                        UpdateCollectBaseThread updateCollectBaseThread = new UpdateCollectBaseThread("Update", getApplicationContext(), (long) count, null, null, mySheet.getSheetName());
                                        updateCollectBaseThread.mThread.join();
                                        Iterator<Row> rowIterator = mySheet.rowIterator();
                                        while (rowIterator.hasNext()) {
                                            Row myRow = rowIterator.next();
                                            if (myRow.getCell(0).getCellType() == CellType.STRING) {
                                                InsertQuestionsBaseThread insertQuestionsBaseThread1 = new InsertQuestionsBaseThread("InsertQuestion", getApplicationContext(), (long) count, (long) myExcelBook.getSheetIndex(mySheet.getSheetName()), myRow.getCell(0).getStringCellValue(), myRow.getCell(1).getStringCellValue());
                                                insertQuestionsBaseThread1.mThread.join();
                                            } else {
                                                InsertQuestionsBaseThread insertQuestionsBaseThread1 = new InsertQuestionsBaseThread("InsertQuestion", getApplicationContext(), (long) count, (long) myExcelBook.getSheetIndex(mySheet.getSheetName()), String.valueOf(myRow.getCell(0).getNumericCellValue()), String.valueOf(myRow.getCell(1).getNumericCellValue()));
                                                insertQuestionsBaseThread1.mThread.join();
                                            }
                                        }
                                    }
                                    myExcelBook.close();
                                }
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();

                        }

                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.list_tests);
                        Log.i("Fragment: " , fragment.toString());
                        Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.select_user);
                        FragmentManager fragmentManager= getSupportFragmentManager();
                        if(fragment2 != null) {
                            fragmentManager.beginTransaction().remove(fragment2).add(R.id.select_user,new SelectCollectFragment()).commit();
                            fragmentManager.beginTransaction().remove(fragment).add(R.id.list_tests,new ListThemeFragment()).commit();//здесь обновляется
                        } else {
                            fragmentManager.beginTransaction().remove(fragment).add(R.id.list_tests,new CreateCollectFragment()).commit();
                        }
                    }
                });
                thread.start();
                break;
            case R.id.in_menu2:
                AllCleanAssemblingBaseThread AllCleanAssemblingBaseThread = new AllCleanAssemblingBaseThread("CleanAssembling", getApplicationContext());
                AllCleanQuestionBaseThread allCleanQuestionBaseThread = new AllCleanQuestionBaseThread("CleanQuestion", getApplicationContext());

                Fragment fragment11= getSupportFragmentManager().findFragmentById(R.id.list_tests);
                Log.i("Fragment11 : " , fragment11.toString());
                Fragment fragment22= getSupportFragmentManager().findFragmentById(R.id.select_user);
                FragmentManager fragmentManager1= getSupportFragmentManager();
                if(fragment22 != null) {
                    fragmentManager1.beginTransaction().remove(fragment11).commit();
                    Log.i("Fragment11 (remove): " , getSupportFragmentManager().findFragmentById(R.id.list_tests).toString());
                    fragmentManager1.beginTransaction().replace(R.id.select_user,new SelectCollectFragment()).commit();
                    Log.i("Fragment22 (remove): " , getSupportFragmentManager().findFragmentById(R.id.select_user).toString());
                    fragmentManager1.beginTransaction().add(R.id.list_tests,new ListThemeFragment()).commit();
                }  else {
                    fragmentManager1.beginTransaction().remove(fragment11).add(R.id.list_tests,new CreateCollectFragment()).commit();
                }

                break;
            case R.id.in_menu3:
                FragmentManager fragmentManager33= getSupportFragmentManager();
                AboutFragment aboutFragment = new AboutFragment();
                aboutFragment.show(fragmentManager33, "About");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
