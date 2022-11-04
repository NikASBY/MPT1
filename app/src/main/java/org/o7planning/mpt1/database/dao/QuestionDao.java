package org.o7planning.mpt1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.o7planning.mpt1.database.Questions;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    public List<Questions> getAll();

    @Query("SELECT * FROM questions WHERE id = :id")
    public Questions getById(long id);

    @Insert
    public void insert(Questions questions);

    @Update
    public void update(Questions questions);

    @Delete
    public void delete(Questions questions);

    @Query("DELETE FROM questions")
    public void deleteAllFromTable();
}
