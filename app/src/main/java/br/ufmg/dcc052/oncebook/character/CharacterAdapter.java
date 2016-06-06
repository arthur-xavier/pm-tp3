package br.ufmg.dcc052.oncebook.character;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by xavier on 6/6/16.
 */
public class CharacterAdapter extends CursorAdapter {

  public CharacterAdapter(Context context) {
    super(context, new SQLiteCharacterRepository(context).getAllCursor(), 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return null;
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {

  }
}
