package org.o7planning.mpt1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.o7planning.mpt1.database.dao.AssemblingDao;
import org.o7planning.mpt1.database.dao.ProgressDao;
import org.o7planning.mpt1.database.dao.QuestionDao;
import org.o7planning.mpt1.database.dao.SettingsDao;

@Database(entities = {Assembling.class,Progress.class,Settings.class,Questions.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AssemblingDao assemblingDao();
    public abstract ProgressDao progressDao();
    public abstract SettingsDao settingsDao();
    public abstract QuestionDao questionsDao();
}
