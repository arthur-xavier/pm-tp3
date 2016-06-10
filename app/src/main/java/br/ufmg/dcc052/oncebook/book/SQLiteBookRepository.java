package br.ufmg.dcc052.oncebook.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.ArrayList;
import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;
import br.ufmg.dcc052.oncebook.storage.ICursorLoader;
import br.ufmg.dcc052.oncebook.storage.SQLiteRepository;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteBookRepository extends SQLiteRepository<Book>
                                  implements IBookRepository, ICursorLoader {

  public static final String TABLE_NAME = "books";
  public static final String COLUMN_NAME_ID = "_id";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_DESCRIPTION = "description";
  public static final String[] ALL_COLUMNS = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
                                                COLUMN_NAME_DESCRIPTION };

  private DatabaseHelper databaseHelper;

  public SQLiteBookRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
  }

  @Override
  public Book getById(Integer id) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_ID + "=" + id;
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    if(cursor != null) {
      cursor.moveToFirst();
    }
    db.close();
    return cursorToEntity(cursor);
  }

  @Override
  public List<Book> getAll() {
    List<Book> books = new ArrayList<Book>();
    String sql = "SELECT * FROM books ORDER BY _id DESC";
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        Book book = new Book(id, name, description);
        books.add(book);

      } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return books;
  }

  @Override
  public Cursor getAllCursor() {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
    db.close();
    return cursor;
  }

  @Override
  public List<Book> findByName(String name) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + "LIKE" + name + "%";
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    db.close();
    return cursorToEntities(cursor);
  }

  @Override
  public void save(Book book) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, book.getName());
    values.put(COLUMN_NAME_DESCRIPTION, book.getDescription());

    if (book.getId() != 0 && getById(book.getId()) != null) {
      SQLiteDatabase db = databaseHelper.getWritableDatabase();
      String where = COLUMN_NAME_ID + "=" + book.getId();
      db.update(TABLE_NAME, values, where, null);
      db.close();
    } else {
      SQLiteDatabase db = databaseHelper.getWritableDatabase();
      db.insert(TABLE_NAME, null, values);
      db.close();
    }
  }

  @Override
  public void delete(Book book) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + book.getId();
    db.delete(TABLE_NAME, where, null);
    db.close();
  }

  public int count() {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String sql = "SELECT * FROM books";
    int count = db.rawQuery(sql, null).getCount();
    db.close();
    return count;
  }

  @Override
  public Book cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }

    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
    return new Book(id, name, description);
  }
}
