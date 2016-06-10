package br.ufmg.dcc052.oncebook;
import android.app.AlertDialog;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.EditText;
import android.content.DialogInterface;
import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.MainActivity;
/**
 * Created by jotajunior on 6/9/16.
 */
public class OnClickListenerCreateBook implements View.OnClickListener {
  private MainActivity ma;
  public OnClickListenerCreateBook() {}

  public OnClickListenerCreateBook(MainActivity ma) {
    this.ma = ma;
  }
  @Override
  public void onClick(View view) {
    final Context context = view.getContext();
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.book_input_form, null, false);
    final EditText editBook = (EditText) formElementsView.findViewById(R.id.editTextBookName);
    final EditText editBookDesc = (EditText) formElementsView.findViewById(R.id.editTextBookDescription);

    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Create book")
      .setPositiveButton("Create",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            Book book = new Book(editBook.getText().toString(), editBookDesc.getText().toString());
            SQLiteBookRepository sbr = new SQLiteBookRepository(context);
            sbr.save(book);
            if (ma != null) {
              ma.readRecords();
              ma.countRecords();
            }
            dialog.cancel();
          }
        }).show();

  }
}
