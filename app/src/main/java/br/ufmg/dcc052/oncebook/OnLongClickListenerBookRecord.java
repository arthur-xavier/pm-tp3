package br.ufmg.dcc052.oncebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.content.Context;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.book.Book;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnLongClickListenerBookRecord implements View.OnLongClickListener {
  Context context;
  String id;
  private MainActivity ma;

  public OnLongClickListenerBookRecord() { }
  public OnLongClickListenerBookRecord(MainActivity ma) {
    this.ma = ma;
  }
  @Override
  public boolean onLongClick(View view) {
    context = view.getContext();
    id = view.getTag().toString();

    final CharSequence[] options = {"Edit", "Delete"};
    new AlertDialog.Builder(context).setTitle("Book Record")
      .setItems(options,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            if (item == 0) {
              editRecord(Integer.parseInt(id));
            }
            dialog.dismiss();
          }
        }).show();

    return false;
  }

  private void editRecord(final int bookId) {
    SQLiteBookRepository sbr = new SQLiteBookRepository(context);
    final Book book = sbr.getById(bookId);
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.book_input_form, null, false);
    final EditText editTextName = (EditText) formElementsView.findViewById(R.id.editTextBookName);
    final EditText editTextDescription = (EditText) formElementsView.findViewById(R.id.editTextBookDescription);
    editTextName.setText(book.getName());
    editTextDescription.setText(book.getDescription());
    final Context context1 = context;

    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Edit book")
      .setPositiveButton("Save",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            book.setId(bookId);
            book.setName(editTextName.getText().toString());
            book.setDescription(editTextDescription.getText().toString());
            SQLiteBookRepository sbr2 = new SQLiteBookRepository(context1);
            sbr2.save(book);
            /*if (ma != null) {
              ma.readRecords();
            }*/
            dialog.cancel();
          }
        }).show();
  }
}
