package org.o7planning.mpt1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.o7planning.mpt1.database.dao.AssemblingDao;
import org.o7planning.mpt1.database.dao.ProgressDao;
import org.o7planning.mpt1.database.dao.QuestionDao;
import org.o7planning.mpt1.database.dao.SettingssDao;

@Database(entities = {Assembling.class,Progress.class, Settingss.class,Questions.class},version = 7)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AssemblingDao assemblingDao();
    public abstract ProgressDao progressDao();
    public abstract SettingssDao settingsDao();
    public abstract QuestionDao questionsDao();
}
