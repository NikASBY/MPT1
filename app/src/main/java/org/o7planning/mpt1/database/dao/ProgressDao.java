package org.o7planning.mpt1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.o7planning.mpt1.database.Progress;

import java.util.List;

@Dao
public interface ProgressDao {

    @Query("SELECT * FROM Progress")
    List<Progress> getAll();

    @Query("SELECT * FROM progress WHERE id = :id")
    Progress getById(long id);

    @Insert
    void insert(Progress progress);

    @Update
    void update(Progress progress);

    @Delete
    void delete(Progress progress);


}
