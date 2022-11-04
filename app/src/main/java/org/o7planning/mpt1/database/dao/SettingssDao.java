package org.o7planning.mpt1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.o7planning.mpt1.database.Settingss;

import java.util.List;

@Dao
public interface SettingssDao {

    @Query("SELECT * FROM Settingss")
    List<Settingss> getAll();

    @Query("SELECT * FROM Settingss WHERE id =:id")
    Settingss getById(long id);

    @Insert
    void insert(Settingss settingss);

    @Update
    void update(Settingss settingss);

    @Delete
    void delete(Settingss settingss);

}
