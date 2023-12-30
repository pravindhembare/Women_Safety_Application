package com.example.womensafety;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "ID";
    public static final String COL2 ="NAME";
    public static final String COL3 ="ITEM";
     public static String id;

    public DatabaseHandler(Context context) {super(context,DATABASE_NAME,null,1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
              " NAME TEXT," + " ITEM TEXT)";
        db.execSQL( createTable );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String a = "DROP TABLE IF EXISTS " +TABLE_NAME;
        db.execSQL( a );
        onCreate( db );
    }
    public  boolean addData (String item1,String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues( );
        contentValues.put(COL2,item1 );
        contentValues.put(COL3,item2 );
        long result = db.insert( TABLE_NAME,null,contentValues );

        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getListContent(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " +TABLE_NAME,null );
        return data;
    }

    public Cursor getNumber(String num) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {num};
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = ?", selectionArgs);
        return data;
    }


    public int deleteData(String nameToDelete) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "ITEM = ?";
        String[] whereArgs = {nameToDelete};

        int rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);
        return rowsDeleted;
    }


    public Cursor getItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COL2, COL3}; // Include both columns
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }


    public int updateData(String name,String num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.COL2, name);
        values.put(DatabaseHandler.COL3, num);
//        Cursor data = getListContent();
//        while (data.moveToNext()) {
//            if(data.getString(2)==num){
//               id =data.getString(0);
//            }
//        }
        String whereClause = "ITEM = ?";
        String[] whereArgs = {num};
        int rowUpdate = db.update(TABLE_NAME,values,whereClause,whereArgs);
        return rowUpdate;
    }
}

