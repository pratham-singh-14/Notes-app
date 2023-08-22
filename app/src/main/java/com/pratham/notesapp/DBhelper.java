package com.pratham.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME="Notes";
    public static final String TABLE_NAME="notes";
    public static final int VERSION=4;

    public DBhelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+"Title text,"+"Content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
       onCreate(sqLiteDatabase);
    }

    public void noteadd(String t,String c){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Title",t);
        values.put("Content",c);
        sqLiteDatabase.insert(TABLE_NAME,null,values);

    }
public ArrayList<Datamodel> fetchnotes(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
    Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    ArrayList<Datamodel> arrayList=new ArrayList<>();

    while (cursor.moveToNext()){
        Datamodel datamodel=new Datamodel();
        datamodel.id=cursor.getInt(0);
        datamodel.title=cursor.getString(1);
        datamodel.content=cursor.getString(2);
        arrayList.add(datamodel);
    }
        return arrayList;
}
public void Update(Datamodel datamodel) {
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("Title", datamodel.title);
    values.put("Content", datamodel.content);
  //  long raw =
            sqLiteDatabase.update(TABLE_NAME, values, "id" + "=" + datamodel.id, null);
   // if (raw <= -1) {
    //    return false;
   // } else
      //  return true;
}
public void Delete(Datamodel datamodel){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"id"+"="+datamodel.id,null);
}
}
