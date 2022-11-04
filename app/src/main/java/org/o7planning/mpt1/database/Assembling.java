package org.o7planning.mpt1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.o7planning.mpt1.database.Converter.ThemeConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Assembling {

    @PrimaryKey
    public long id;

    public long uid;

    public String assembling;

    @TypeConverters({ThemeConverter.class})
    public List<String> theme = new ArrayList<>();
}
