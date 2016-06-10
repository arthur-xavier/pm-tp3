package br.ufmg.dcc052.oncebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

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

  private void editRecord(final int charId) {
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
    final Character character = scr.getById(charId);
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.character_input_form, null, false);
    final EditText editTextName = (EditText) formElementsView.findViewById(R.id.editTextCharacterName);
    final EditText editTextDescription = (EditText) formElementsView.findViewById(R.id.editTextCharacterDescription);
    editTextName.setText(character.getName());
    editTextDescription.setText(character.getDescription());
    final Context context1 = context;

    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Edit character")
      .setPositiveButton("Save",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            character.setId(charId);
            character.setName(editTextName.getText().toString());
            character.setDescription(editTextDescription.getText().toString());
            SQLiteCharacterRepository sbr2 = new SQLiteCharacterRepository(context1);
            sbr2.save(character);
            if (ba != null) {
              ba.readRecords();
            }
            dialog.cancel();
          }
        }).show();
  }

  private void deleteRecord(final int charId) {
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
    Character character = scr.getById(charId);
    scr.delete(character);
    Toast.makeText(context, "Character was deleted successfully.", Toast.LENGTH_SHORT).show();
    this.ba.readRecords();
    this.ba.countRecords();
  }
}
