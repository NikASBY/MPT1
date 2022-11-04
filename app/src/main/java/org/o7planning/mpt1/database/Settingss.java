package org.o7planning.mpt1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {

    @PrimaryKey
    public long id;

    public Boolean changeLang;

}