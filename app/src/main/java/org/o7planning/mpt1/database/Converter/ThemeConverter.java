package org.o7planning.mpt1.database.Converter;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThemeConverter {
    @TypeConverter
    public String fromThemes(List<String> themes) {
        StringBuilder s = new StringBuilder();
        if(themes!=null) {
            for(int i = 0; i<themes.size(); i++) {
                if(!themes.get(i).equals("")){
                    s.append(themes.get(i)+",");
                }
            }
        }
        return s.toString();
    }

    @TypeConverter
    public List<String> toThemes(String data) {
        List<String> strings = new ArrayList<>();
        if(data !=null) {
            String[] strings1 = data.split(",");
            for(int i = 0; i<strings1.length; i++) {
                strings.add(strings1[i]);
            }
        }
        return strings;
    }
}
