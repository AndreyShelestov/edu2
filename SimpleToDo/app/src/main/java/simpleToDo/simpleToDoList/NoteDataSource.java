package simpleToDo.simpleToDoList;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] notesAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NOTE
    };

    public NoteDbScheme addNote(String note) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOTE, note);
        long insertId = database.insert(DatabaseHelper.TABLE_NOTES, null,
                values);
        NoteDbScheme newNote = new NoteDbScheme();
        newNote.setNote(note);
        newNote.setId(insertId);
        return newNote;
    }

    public void editNote(long id, String note) {
        ContentValues editedNote = new ContentValues();
        editedNote.put(dbHelper.COLUMN_ID, id);
        editedNote.put(dbHelper.COLUMN_NOTE, note);

        database.update(dbHelper.TABLE_NOTES, editedNote, dbHelper.COLUMN_ID + "=" + id, null);
    }

    public void deleteNote(NoteDbScheme note) {
        long id = note.getId();
        database.delete(DatabaseHelper.TABLE_NOTES, DatabaseHelper.COLUMN_ID + "=" + id, null);
    }

    public void deleteAllNotes() {
        database.delete(DatabaseHelper.TABLE_NOTES, null, null);
    }

    public List<NoteDbScheme> getAllNotes() {
        List<NoteDbScheme> notes = new ArrayList<>();
        Cursor c = database.query(DatabaseHelper.TABLE_NOTES, notesAllColumn, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            NoteDbScheme note = cursorToNote(c);
            notes.add(note);
            c.moveToNext();
        }
        c.close();
        return notes;
    }

    private NoteDbScheme cursorToNote(Cursor c) {
        NoteDbScheme note = new NoteDbScheme();
        note.setId(c.getLong(0));
        note.setNote(c.getString(1));
        return note;
    }

}
