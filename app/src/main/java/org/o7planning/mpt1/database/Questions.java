package org.o7planning.mpt1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Questions {

    @PrimaryKey
    public long id;

    public long uidCollect;

    public long uidTheme;

    public String questions;

    public String answers;
}
