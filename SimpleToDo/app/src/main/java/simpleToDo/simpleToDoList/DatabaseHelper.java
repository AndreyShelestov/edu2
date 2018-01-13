package simpleToDo.simpleToDoList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "toDoNotes.db";
    public static final int DATABASE_VERSION = 1;
    static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "note";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NOTES + " (" + COLUMN_ID + " integer primary key autoincrement," + COLUMN_NOTE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NOTES);
        onCreate(db);
    }
}
