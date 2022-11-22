package org.o7planning.mpt1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Progress {

    @PrimaryKey
    public long id;

    public String nameCollect;

    public String nameTheme;

    public String questions;

    public String answers;

    public String answerFail;

    public boolean status;

}
