package br.ufmg.dcc052.oncebook.relationship;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by xavier on 6/6/16.
 */
public class RelationshipAdapter extends CursorAdapter {

  public RelationshipAdapter(Context context) {
    super(context, new SQLiteRelationshipRepository(context).getAllCursor(), 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return null;
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {

  }
}
