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

  public static final String TABLE_NAME = "characters";
  public static final String COLUMN_NAME_ID = "_id";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_DESCRIPTION = "description";
  public static final String COLUMN_NAME_BOOK = "book_id";
  public static final String COLUMN_NAME_APPEARANCEPAGE = "appearancePage";
  public static final String COLUMN_NAME_PICTURE = "picture";

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
    Character character;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_ID + "=" + id;
    try {
      Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
      if (cursor != null) {
        cursor.moveToFirst();
      }
      character = cursorToEntity(cursor);
      cursor.close();
    } finally {
      closeDatabase(db);
    }
    return character;
  }

  @Override
  public List<Character> getAll() {
    List<Character> characters;
    Cursor cursor = getAllCursor();
    try {
      characters = cursorToEntities(cursor);
    } finally {
      cursor.close();
    }
    return characters;
  }

  @Override
  public Cursor getAllCursor() {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    return db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
  }

  @Override
  public Cursor getAllByBookCursor(Book book) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_BOOK + "=" + book.getId();
    return db.query(TABLE_NAME, ALL_COLUMNS_BUT_BOOK, where, null, null, null, null);
  }

  @Override
  public List<Character> findByName(String name) {
    List<Character> characters;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + " LIKE '" + name + "%'";
    try {
      Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
      characters = cursorToEntities(cursor);
      cursor.close();
    } finally {
      db.close();
    }
    return characters;
  }

  @Override
  public synchronized void save(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, character.getName());
    values.put(COLUMN_NAME_DESCRIPTION, character.getDescription());
    values.put(COLUMN_NAME_APPEARANCEPAGE, character.getAppearancePage());
    values.put(COLUMN_NAME_PICTURE, BitmapUtils.getBytes(character.getPicture()));

    try {
      String where = COLUMN_NAME_ID + "=" + character.getId();
      if (character.getId() != 0) {
        if (db.update(TABLE_NAME, values, where, null) == 0) {
          character.setId((int) db.insert(TABLE_NAME, null, values));
        }
      } else {
        character.setId((int) db.insert(TABLE_NAME, null, values));
      }
    } finally {
      closeDatabase(db);
    }
  }

  @Override
  public synchronized void delete(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + character.getId();
    try {
      db.delete(TABLE_NAME, where, null);
    } finally {
      closeDatabase(db);
    }
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

    // has book_id column ==> return character with book
    try {
      int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_BOOK));
      Book book = this.bookRepository.getById(bookId);
      return new Character(id, name, description, book, appearancePage, picture);
    }
    // else ==> return character without book
    catch(IllegalArgumentException e) {
      return new Character(id, name, description, null, appearancePage, picture);
    }
  }

  private static void closeDatabase(SQLiteDatabase db) {
    if (db != null && db.isOpen()) {
      db.close();
    }
  }

  public int count() {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String sql = "SELECT * FROM characters";
    int count = db.rawQuery(sql, null).getCount();
    db.close();
    return count;
  }
}
