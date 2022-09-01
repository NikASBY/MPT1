package org.o7planning.mpt1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.o7planning.mpt1.database.Assembling;

import java.util.List;

@Dao
public interface AssemblingDao {

    @Query("SELECT * FROM assembling")
    public List<Assembling> getAll();

    @Query("SELECT * FROM assembling WHERE id = :id")
    public Assembling getById(long id);

    @Insert
    public void insert(Assembling assembling);

    @Update
    public void update(Assembling assembling);

    @Delete
    public void delete(Assembling assembling);
}
