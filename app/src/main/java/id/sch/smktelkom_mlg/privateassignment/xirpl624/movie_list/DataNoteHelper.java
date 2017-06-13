package id.sch.smktelkom_mlg.privateassignment.xirpl624.movie_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


public class DataNoteHelper {
    private static final String TABLE_NAME = "tb_note";
    private static final String INSERT = "insert into " + TABLE_NAME + "(title, content) values (?,?)";
    private SQLiteDatabase mSqliteDatabase;
    private SQLiteStatement insertStmt;

    public DataNoteHelper(Context context) {
        NoteHelper noteHelper = new NoteHelper(context);
        mSqliteDatabase = noteHelper.getWritableDatabase();
        insertStmt = mSqliteDatabase.compileStatement(INSERT);
    }

    public long insert(String title, String content) {
        insertStmt.bindString(1, title);
        insertStmt.bindString(2, content);
        return insertStmt.executeInsert();
    }

    public Cursor getAll() {
        return mSqliteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllByRawQuery() {
        return mSqliteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getById(int id) {
        return mSqliteDatabase.query(TABLE_NAME, null, "_id=" + id, null, null, null,
                "_id DESC", null);
    }

    public Cursor getByIdRawQuery(int id) {
        return mSqliteDatabase.rawQuery("SELECT * from " + TABLE_NAME +
                " WHERE _id = " + id + " ORDER BY _id DESC", null);
    }

    public int deleteById(int id) {
        return mSqliteDatabase.delete(TABLE_NAME, "_id =" + id, null);
    }

    public void deleteRawQueryById(int id) {
        mSqliteDatabase.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE _id=" + id, null);
    }

    public int updateById(int id, String title, String content) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        return mSqliteDatabase.update(TABLE_NAME, cv, "_id = " + id, null);
    }

    public void updateByIdRawQuery(int id, String title, String content) {
        mSqliteDatabase.execSQL("UPDATE " + TABLE_NAME + " SET title='" + title + "'," +
                "content ='" + content + "' WHERE _id = " + id);
    }

    public void close() {
        mSqliteDatabase.close();
    }
}