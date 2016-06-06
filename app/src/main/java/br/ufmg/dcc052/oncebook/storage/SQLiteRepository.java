package br.ufmg.dcc052.oncebook.storage;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavier on 6/6/16.
 */
public abstract class SQLiteRepository<T> {

  public abstract T cursorToEntity(Cursor cursor);

  public List<T> cursorToEntities(Cursor cursor) {
    if(cursor == null) {
      return null;
    }

    List<T> ts = new ArrayList<>();

    cursor.moveToFirst();

    while(!cursor.isAfterLast()) {
      ts.add(this.cursorToEntity(cursor));
      cursor.moveToNext();
    }

    return ts;
  }
}
