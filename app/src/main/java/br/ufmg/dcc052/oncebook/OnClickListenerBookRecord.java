package br.ufmg.dcc052.oncebook;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnClickListenerBookRecord implements View.OnClickListener {
  private MainActivity ma;

  public OnClickListenerBookRecord() {}
  public OnClickListenerBookRecord(MainActivity ma) {
    this.ma = ma;
  }

  @Override
  public void onClick(View view) {
    final Context context = view.getContext();
    Intent intent = new Intent(this.ma, BookActivity.class);
    String bookId = view.getTag().toString();
    intent.putExtra(this.ma.BOOK_ID, bookId);
    this.ma.startActivity(intent);
  }
}
