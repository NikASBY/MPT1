package org.o7planning.mpt1.database;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class SinglDatabase extends Application {

     private static SinglDatabase instance;
     private MyDatabase mMyDatabase;

     public MyDatabase getMyDatabase() {
         return mMyDatabase;
     }

     private SinglDatabase(Context context) {
         mMyDatabase = Room.databaseBuilder(context,MyDatabase.class,"MyDatabase").build();
     }

     public static synchronized SinglDatabase getInstance(Context context) {
         if(instance == null) {
             instance = new SinglDatabase(context);
         }
         return instance;
     }

}
