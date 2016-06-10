package br.ufmg.dcc052.oncebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnLongClickListenerCharacterRecord implements View.OnLongClickListener {
  Context context;
  String id;
  private BookActivity ba;

  public OnLongClickListenerCharacterRecord() { }
  public OnLongClickListenerCharacterRecord(BookActivity ba) {
    this.ba = ba;
  }

  @Override
  public boolean onLongClick(View view) {
    context = view.getContext();
    id = view.getTag().toString();

    final CharSequence[] options = {"Edit", "Delete"};
    new AlertDialog.Builder(context).setTitle("Character Record")
      .setItems(options,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            if (item == 0) {
              editRecord(Integer.parseInt(id));
            } else if (item == 1) {
              deleteRecord(Integer.parseInt(id));
            }
            dialog.dismiss();
          }
        }).show();
    return false;
  }

  private void editRecord(final int bookId) {

  }

  private void deleteRecord(final int bookId) {

  }
}
