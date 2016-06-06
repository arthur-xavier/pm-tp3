package br.ufmg.dcc052.oncebook.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;
import br.ufmg.dcc052.oncebook.storage.ICursorLoader;
import br.ufmg.dcc052.oncebook.storage.SQLiteRepository;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteBookRepository implements IBookRepository, SQLiteRepository<Book>, ICursorLoader {

  private static final String TABLE_NAME = "books";
  private static final String COLUMN_NAME_ID = "_id";
  private static final String COLUMN_NAME_NAME = "name";
  private static final String COLUMN_NAME_DESCRIPTION = "description";
  private static final String[] ALL_COLUMNS = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
                                                COLUMN_NAME_DESCRIPTION };

  private DatabaseHelper databaseHelper;

  public SQLiteBookRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
  }

  @Override
  public Book getById(Long id) {
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
    return cursorToEntities(getAllCursor());
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
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, book.getName());
    values.put(COLUMN_NAME_DESCRIPTION, book.getDescription());

    if (book.getId() != 0 && getById(book.getId()) != null) {
      String where = COLUMN_NAME_ID + "=" + book.getId();
      db.update(TABLE_NAME, values, where, null);
    } else {
      db.insert(TABLE_NAME, null, values);
    }

    db.close();
  }

  @Override
  public void delete(Book book) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + book.getId();
    db.delete(TABLE_NAME, where, null);
    db.close();
  }

  @Override
  public Book cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }

    long id = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID));
    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
    return new Book(id, name, description);
  }

  @Override
  public List<Book> cursorToEntities(Cursor cursor) {
    if(cursor == null) {
      return null;
    }

    List<Book> books = new ArrayList<>();
    cursor.moveToFirst();
    while(!cursor.isAfterLast()) {
      books.add(this.cursorToEntity(cursor));
      cursor.moveToNext();
    }
    return books;
  }
}
