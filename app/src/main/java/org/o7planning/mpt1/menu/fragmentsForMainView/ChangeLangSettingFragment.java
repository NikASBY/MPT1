package org.o7planning.mpt1.menu.fragmentsForMainView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.o7planning.mpt1.R;
import org.o7planning.mpt1.database.Assembling;
import org.o7planning.mpt1.database.Settingss;
import org.o7planning.mpt1.menu.activity.NewMainMenu;
import org.o7planning.mpt1.thread.AllCleanAssemblingBaseThread;
import org.o7planning.mpt1.thread.AllCleanQuestionBaseThread;
import org.o7planning.mpt1.thread.AllCollectBaseThread;
import org.o7planning.mpt1.thread.AllSettingsThread;
import org.o7planning.mpt1.thread.InsertCollectBaseThread;
import org.o7planning.mpt1.thread.InsertQuestionsBaseThread;
import org.o7planning.mpt1.thread.UpdateCollectBaseThread;
import org.o7planning.mpt1.thread.UpdateSettingssThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ChangeLangSettingFragment extends Fragment {

    private List<Assembling> listAssembling = new ArrayList<>();

    private TextView buttonLoadFile;
    private TextView buttonClearBase;
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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_panel_conteiner, new HomePanelFragment()).commit();
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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_panel_conteiner, new HomePanelFragment()).commit();
                        break;
                }
            }
        });

        buttonLoadFile = (TextView) view.findViewById(R.id.load_base);
        buttonLoadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<File> listFile = Arrays.asList(NewMainMenu.getDirectory().listFiles());
                            for (int i = 0; i < listFile.size(); i++) {
                                Boolean flag = true;
                                AllCollectBaseThread allCollectBaseThread = new AllCollectBaseThread("All", getContext());
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
                                    InsertCollectBaseThread insertCollectBaseThread = new InsertCollectBaseThread("InsertCollect", getContext(), null, filePath.getName().replace(".xls", ""), null);
                                    insertCollectBaseThread.mThread.join();
                                    Iterator<Sheet> sheetIterator = myExcelBook.sheetIterator();
                                    while (sheetIterator.hasNext()) {
                                        Sheet mySheet = sheetIterator.next();
                                        UpdateCollectBaseThread updateCollectBaseThread = new UpdateCollectBaseThread("Update", getContext(), (long) count, null, null, mySheet.getSheetName());
                                        updateCollectBaseThread.mThread.join();
                                        Iterator<Row> rowIterator = mySheet.rowIterator();
                                        while (rowIterator.hasNext()) {
                                            Row myRow = rowIterator.next();
                                            if (myRow.getCell(0).getCellType() == CellType.STRING) {
                                                InsertQuestionsBaseThread insertQuestionsBaseThread1 = new InsertQuestionsBaseThread("InsertQuestion", getContext(), (long) count, (long) myExcelBook.getSheetIndex(mySheet.getSheetName()), myRow.getCell(0).getStringCellValue(), myRow.getCell(1).getStringCellValue());
                                                insertQuestionsBaseThread1.mThread.join();
                                            } else {
                                                InsertQuestionsBaseThread insertQuestionsBaseThread1 = new InsertQuestionsBaseThread("InsertQuestion", getContext(), (long) count, (long) myExcelBook.getSheetIndex(mySheet.getSheetName()), String.valueOf(myRow.getCell(0).getNumericCellValue()), String.valueOf(myRow.getCell(1).getNumericCellValue()));
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

                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                        Log.i("Fragment: " , fragment.toString());
                        Fragment fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        if(fragment2 != null) {
                            fragmentManager.beginTransaction().remove(fragment2).add(R.id.select_user,new SelectCollectFragment()).commit();
                            fragmentManager.beginTransaction().remove(fragment).add(R.id.list_tests,new ListThemeFragment()).commit();//здесь обновляется
                        } else {
                            fragmentManager.beginTransaction().add(R.id.select_user,new SelectCollectFragment()).commit();
                            fragmentManager.beginTransaction().remove(fragment).add(R.id.list_tests,new ListThemeFragment()).commit();
                        }
                    }
                });
                thread.start();
            }
        });

        buttonClearBase = (TextView) view.findViewById(R.id.clear_base);
        buttonClearBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCleanAssemblingBaseThread AllCleanAssemblingBaseThread = new AllCleanAssemblingBaseThread("CleanAssembling", getContext());
                AllCleanQuestionBaseThread allCleanQuestionBaseThread = new AllCleanQuestionBaseThread("CleanQuestion", getContext());

                Fragment fragment11 = getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests);
                Log.i("Fragment11 : " , fragment11.toString());
                Fragment fragment22 = getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                if(fragment22 != null) {
                    fragmentManager1.beginTransaction().remove(fragment11).commit();
                    Log.i("Fragment11 (remove): " , getActivity().getSupportFragmentManager().findFragmentById(R.id.list_tests).toString());
                    fragmentManager1.beginTransaction().replace(R.id.select_user,new SelectCollectFragment()).commit();
                    Log.i("Fragment22 (remove): " , getActivity().getSupportFragmentManager().findFragmentById(R.id.select_user).toString());
                    fragmentManager1.beginTransaction().add(R.id.list_tests,new ListThemeFragment()).commit();
                }  else {
                    fragmentManager1.beginTransaction().add(R.id.select_user,new SelectCollectFragment()).commit();
                    fragmentManager1.beginTransaction().remove(fragment11).add(R.id.list_tests,new ListThemeFragment()).commit();
                }
            }
        });

        return view;
    }
}
