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
public class OnLongClickListenerRelationshipRecord implements View.OnLongClickListener {
  Context context;
  int firstCharacter;
  int secondCharacter;
  String name;
  private RelationshipActivity ra;

  public OnLongClickListenerRelationshipRecord() {}
  public OnLongClickListenerRelationshipRecord(RelationshipActivity ra) {
    this.ra = ra;
  }

  @Override
  public boolean onLongClick(View view) {
    context = view.getContext();
    firstCharacter = Integer.parseInt(view.getTag(R.id.first_character_tag).toString());
    secondCharacter = Integer.parseInt(view.getTag(R.id.second_character_tag).toString());
    name = view.getTag(R.id.relationship_name_tag).toString();

    final CharSequence[] options = {"Edit", "Delete"};
    new AlertDialog.Builder(context).setTitle("Relationship Record")
      .setItems(options,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            if (item == 0) {
              editRecord(firstCharacter, secondCharacter, name);
            } else if (item == 1) {
              deleteRecord(firstCharacter, secondCharacter, name);
            }
            dialog.dismiss();
          }
        }).show();
    return false;
  }

  public void editRecord(final int firstCharacter, final int secondCharacter, final String name) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View formElementsView = inflater.inflate(R.layout.relationship_input_form, null, false);
    final EditText editTextRelationshipName = (EditText) formElementsView.findViewById(R.id.editTextRelationshipName);
    editTextRelationshipName.setText(name);
    final Spinner spinner = (Spinner) formElementsView.findViewById(R.id.spinner);
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
    Character originalSecond = scr.getById(secondCharacter);
    Character originalFirst = scr.getById(firstCharacter);
    final Relationship oldRelationship = new Relationship(name, originalFirst, originalSecond);
    List<Character> charactersFromSameBook = scr.getCharactersFromSameBook(this.ra.charId);
    String secondCharacterName = scr.getById(secondCharacter).getName();
    ArrayList<String> items = new ArrayList<String>();

    for (Character c: charactersFromSameBook) {
      items.add(c.getName());
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.ra, android.R.layout.simple_spinner_dropdown_item, items);
    spinner.setAdapter(adapter);
    spinner.setSelection(adapter.getPosition(secondCharacterName));

    final Context context1 = context;

    new AlertDialog.Builder(context)
      .setView(formElementsView)
      .setTitle("Edit relationship")
      .setPositiveButton("Save",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context1);
            SQLiteRelationshipRepository srr = new SQLiteRelationshipRepository(context1);
            Character first = scr.getById(firstCharacter);
            Character second = scr.findExactName(spinner.getSelectedItem().toString());
            Relationship newRelationship = new Relationship(editTextRelationshipName.getText().toString(), first, second);
            srr.save(oldRelationship, newRelationship);

            if (ra != null) {
              ra.readRecords();
            }
            dialog.cancel();
          }
        }).show();
  }

  public void deleteRecord(final int firstCharacter, final int secondCharacter, final String name) {
    SQLiteRelationshipRepository srr = new SQLiteRelationshipRepository(context);
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(context);
    Character first = scr.getById(firstCharacter);
    Character second = scr.getById(secondCharacter);
    Relationship relationship = new Relationship(name, first, second);
    srr.delete(relationship);
    Toast.makeText(context, "Character was deleted successfully.", Toast.LENGTH_SHORT).show();
    this.ra.readRecords();
  }
}
