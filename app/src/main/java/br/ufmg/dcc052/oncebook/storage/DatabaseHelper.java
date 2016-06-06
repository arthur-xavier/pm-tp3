package br.ufmg.dcc052.oncebook.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xavier on 6/6/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "oncebook.db";
  private static final int DATABASE_VERSION = 1;

  private final Context context;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
