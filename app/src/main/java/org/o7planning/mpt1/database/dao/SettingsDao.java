package org.o7planning.mpt1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.o7planning.mpt1.database.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM Settings")
    List<Settings> getAll();

    @Query("SELECT * FROM Settings WHERE id =:id")
    Settings getById(long id);

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Delete
    void delete(Settings settings);

}
