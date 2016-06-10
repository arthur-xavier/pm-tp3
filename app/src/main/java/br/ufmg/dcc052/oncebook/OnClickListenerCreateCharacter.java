package br.ufmg.dcc052.oncebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnClickListenerCreateCharacter implements View.OnClickListener {
  private BookActivity ba;

  public OnClickListenerCreateCharacter() { }
  public OnClickListenerCreateCharacter(BookActivity ba) {
    this.ba = ba;
  }

  @Override
  public void onClick(View view) {
    final Context context = view.getContext();
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.character_input_form, null, false);
    final EditText editChar = (EditText) formElementsView.findViewById(R.id.editTextCharacterName);
    final EditText editCharDesc = (EditText) formElementsView.findViewById(R.id.editTextCharacterDescription);


    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Create character")
      .setPositiveButton("Create",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            Character character = new Character(editChar.getText().toString(), editCharDesc.getText().toString());
            SQLiteCharacterRepository sbr = new SQLiteCharacterRepository(context);
            sbr.save(character);
            if (ba != null) {
              //ba.readRecords();
              ba.countRecords();
            }
            dialog.cancel();
          }
        }).show();
  }
}
