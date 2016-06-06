package br.ufmg.dcc052.oncebook.storage;

import android.database.Cursor;

import java.util.List;

/**
 * Created by xavier on 6/6/16.
 */
public interface SQLiteRepository<T> {
  T cursorToEntity(Cursor cursor);
  List<T> cursorToEntities(Cursor cursor);
}
