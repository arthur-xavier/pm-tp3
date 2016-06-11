package br.ufmg.dcc052.oncebook.relationship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.ICharacterRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;
import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;
import br.ufmg.dcc052.oncebook.storage.ICursorLoader;
import br.ufmg.dcc052.oncebook.storage.SQLiteRepository;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteRelationshipRepository extends SQLiteRepository<Relationship>
                                       implements IRelationshipRepository, ICursorLoader {

  public static final String TABLE_NAME = "relationships";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_FISRTCHARACTER = "firstCharacter_id";
  public static final String COLUMN_NAME_SECONDCHARACTER = "secondCharacter_id";
  public static final String[] ALL_COLUMNS = { COLUMN_NAME_NAME, COLUMN_NAME_FISRTCHARACTER,
    COLUMN_NAME_SECONDCHARACTER };

  private DatabaseHelper databaseHelper;
  private ICharacterRepository characterRepository;

  public SQLiteRelationshipRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
    this.characterRepository = new SQLiteCharacterRepository(context);
  }

  @Override
  public Relationship getById(Integer id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Relationship> getAll() {
    List<Relationship> relationships;
    Cursor cursor = getAllCursor();
    try {
      relationships = cursorToEntities(cursor);
    } finally {
      cursor.close();
    }
    return relationships;
  }

  public List<Relationship> getAllByCharacter(Character c) {
    List<Relationship> relationships;
    Cursor cursor = getAllByCharacterCursor(c);
    try {
      relationships = cursorToEntities(cursor);
    } finally {
      cursor.close();
    }
    return relationships;
  }
  @Override
  public Cursor getAllCursor() {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    return db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
  }

  public Cursor getAllByCharacterCursor(Character c) {
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_FISRTCHARACTER + "=" + c.getId();
    return db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
  }
  @Override
  public void save(Relationship relationship) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, relationship.getName());
    values.put(COLUMN_NAME_FISRTCHARACTER, relationship.getFirstCharacter().getId());
    values.put(COLUMN_NAME_SECONDCHARACTER, relationship.getSecondCharacter().getId());

    try {
      String where = COLUMN_NAME_FISRTCHARACTER + "=" + relationship.getFirstCharacter().getId() + " AND " +
        COLUMN_NAME_SECONDCHARACTER + "=" + relationship.getSecondCharacter().getId();
      if (db.update(TABLE_NAME, values, where, null) == 0) {
        db.insert(TABLE_NAME, null, values);
      }
    } finally {
      closeDatabase(db);
    }
  }

  public void save(Relationship oldRelationship, Relationship newRelationship) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues newValues = new ContentValues();
    newValues.put(COLUMN_NAME_NAME, newRelationship.getName());
    newValues.put(COLUMN_NAME_FISRTCHARACTER, newRelationship.getFirstCharacter().getId());
    newValues.put(COLUMN_NAME_SECONDCHARACTER, newRelationship.getSecondCharacter().getId());

    try {
      String where = COLUMN_NAME_FISRTCHARACTER + "=" + oldRelationship.getFirstCharacter().getId() + " AND " +
        COLUMN_NAME_SECONDCHARACTER + "=" + oldRelationship.getSecondCharacter().getId();
      if (db.update(TABLE_NAME, newValues, where, null) == 0) {
        db.insert(TABLE_NAME, null, newValues);
      }
    } finally {
      closeDatabase(db);
    }
  }

  @Override
  public void delete(Relationship relationship) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_FISRTCHARACTER + "=" + relationship.getFirstCharacter().getId() + " AND " +
                   COLUMN_NAME_SECONDCHARACTER + "=" + relationship.getSecondCharacter().getId();
    try {
      db.delete(TABLE_NAME, where, null);
    } finally {
      closeDatabase(db);
    }
  }

  @Override
  public Relationship cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }

    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    int firstCharacterId = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FISRTCHARACTER));
    int secondCharacterId = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SECONDCHARACTER));

    Character firstCharacter = this.characterRepository.getById(firstCharacterId);
    Character secondCharacter = this.characterRepository.getById(secondCharacterId);

    return new Relationship(name, firstCharacter, secondCharacter);
  }

  private static void closeDatabase(SQLiteDatabase db) {
    if (db != null && db.isOpen()) {
      db.close();
    }
  }
}
