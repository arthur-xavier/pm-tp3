package br.ufmg.dcc052.oncebook.character;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.IBookRepository;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.storage.BitmapUtils;
import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;
import br.ufmg.dcc052.oncebook.storage.ICursorLoader;
import br.ufmg.dcc052.oncebook.storage.SQLiteRepository;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteCharacterRepository extends SQLiteRepository<Character>
                                       implements ICharacterRepository, ICursorLoader {

  private static final String TABLE_NAME = "characters";
  private static final String COLUMN_NAME_ID = "_id";
  private static final String COLUMN_NAME_NAME = "name";
  private static final String COLUMN_NAME_DESCRIPTION = "description";
  private static final String COLUMN_NAME_BOOK = "book_id";
  private static final String COLUMN_NAME_APPEARANCEPAGE = "appearancePage";
  private static final String COLUMN_NAME_PICTURE = "picture";

  private static final String[] ALL_COLUMNS = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
    COLUMN_NAME_DESCRIPTION, COLUMN_NAME_BOOK, COLUMN_NAME_APPEARANCEPAGE, COLUMN_NAME_PICTURE };
  private static final String[] ALL_COLUMNS_BUT_BOOK = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
    COLUMN_NAME_DESCRIPTION, COLUMN_NAME_APPEARANCEPAGE, COLUMN_NAME_PICTURE };

  private DatabaseHelper databaseHelper;
  private IBookRepository bookRepository;

  public SQLiteCharacterRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
    this.bookRepository = new SQLiteBookRepository(context);
  }

  @Override
  public Character getById(Integer id) {
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
  public List<Character> getAll() {
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
  public Cursor getAllByBookCursor(Book book) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_BOOK + "=" + book.getId();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS_BUT_BOOK, where, null, null, null, null);
    db.close();
    return cursor;
  }

  @Override
  public List<Character> findByName(String name) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + "LIKE" + name + "%";
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    db.close();
    return cursorToEntities(cursor);
  }

  @Override
  public void save(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, character.getName());
    values.put(COLUMN_NAME_DESCRIPTION, character.getDescription());
    values.put(COLUMN_NAME_APPEARANCEPAGE, character.getAppearancePage());
    values.put(COLUMN_NAME_PICTURE, BitmapUtils.getBytes(character.getPicture()));

    if (character.getId() != 0 && getById(character.getId()) != null) {
      String where = COLUMN_NAME_ID + "=" + character.getId();
      db.update(TABLE_NAME, values, where, null);
    } else {
      db.insert(TABLE_NAME, null, values);
    }

    db.close();
  }

  @Override
  public void delete(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + character.getId();
    db.delete(TABLE_NAME, where, null);
    db.close();
  }

  @Override
  public Character cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }

    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
    int appearancePage = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_APPEARANCEPAGE));
    Bitmap picture = BitmapUtils.getBitmap(cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_PICTURE)));
    try {
      int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_BOOK));
      Book book = this.bookRepository.getById(bookId);
      return new Character(id, name, description, book, appearancePage, picture);
    } catch(IllegalArgumentException e) {
      return new Character(id, name, description, null, appearancePage, picture);
    }
  }
}
