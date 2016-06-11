package br.ufmg.dcc052.oncebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.book.RelationshipActivity;
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;
import br.ufmg.dcc052.oncebook.relationship.Relationship;
import br.ufmg.dcc052.oncebook.relationship.SQLiteRelationshipRepository;

/**
 * Created by jotajunior on 6/10/16.
 */
public class OnClickListenerCreateRelationship implements View.OnClickListener {
  private RelationshipActivity ra;

  public OnClickListenerCreateRelationship() { }
  public OnClickListenerCreateRelationship(RelationshipActivity ra) {
    this.ra = ra;
  }

  @Override
  public void onClick(View view) {
    final Context context = view.getContext();
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.relationship_input_form, null, false);
    final EditText editTextRelationshipName = (EditText) formElementsView.findViewById(R.id.editTextRelationshipName);
    final Spinner spinner = (Spinner) formElementsView.findViewById(R.id.spinner);
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
    List<Character> charactersFromSameBook = scr.getCharactersFromSameBook(this.ra.charId);
    ArrayList<String> items = new ArrayList<String>();

    if (charactersFromSameBook == null) {
      Toast.makeText(context, "You can't create a relationship with just one character for the book.", Toast.LENGTH_SHORT).show();
      return;
    }
    for (Character c: charactersFromSameBook) {
      items.add(c.getName());
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.ra, android.R.layout.simple_spinner_dropdown_item, items);
    spinner.setAdapter(adapter);

    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Create relationship")
      .setPositiveButton("Create",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            SQLiteRelationshipRepository srr = new SQLiteRelationshipRepository(context);
            SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
            Character firstCharacter = scr.getById(ra.charId);
            Character secondCharacter = scr.findExactName(spinner.getSelectedItem().toString());
            String relationshipName = editTextRelationshipName.getText().toString();
            Relationship relationship = new Relationship(relationshipName, firstCharacter, secondCharacter);
            srr.save(relationship);
            if (ra != null) {
              ra.readRecords();
            }
            dialog.cancel();
          }
        }).show();
  }
}
