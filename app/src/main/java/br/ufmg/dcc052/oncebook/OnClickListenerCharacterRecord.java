package br.ufmg.dcc052.oncebook;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import br.ufmg.dcc052.oncebook.book.RelationshipActivity;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnClickListenerCharacterRecord implements View.OnClickListener {
  private BookActivity ba;

  public OnClickListenerCharacterRecord() { }
  public OnClickListenerCharacterRecord(BookActivity ba) {
    this.ba = ba;
  }
  @Override
  public void onClick(View view) {
    final Context context = view.getContext();
    Intent intent = new Intent(this.ba, RelationshipActivity.class);
    String charId = view.getTag().toString();
    intent.putExtra(this.ba.CHARACTER_ID, charId);
    this.ba.startActivity(intent);
  }
}
