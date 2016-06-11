package br.ufmg.dcc052.oncebook.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.ufmg.dcc052.oncebook.BookActivity;
import br.ufmg.dcc052.oncebook.OnClickListenerCreateRelationship;
import br.ufmg.dcc052.oncebook.OnLongClickListenerRelationshipRecord;
import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;
import br.ufmg.dcc052.oncebook.relationship.Relationship;
import br.ufmg.dcc052.oncebook.relationship.SQLiteRelationshipRepository;
import br.ufmg.dcc052.oncebook.character.Character;

public class RelationshipActivity extends ActionBarActivity {
  public int charId;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_relationship);
    Intent intent = getIntent();
    int charId = Integer.parseInt(intent.getStringExtra(BookActivity.CHARACTER_ID));
    this.charId = charId;
    Button buttonCreateRelationship = (Button) findViewById(R.id.buttonCreateRelationship);
    buttonCreateRelationship.setOnClickListener(new OnClickListenerCreateRelationship(this));
    readRecords();
  }
  public void readRecords() {
    LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
    linearLayoutRecords.removeAllViews();
    SQLiteRelationshipRepository srr = new SQLiteRelationshipRepository(this);
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(this);
    Character firstCharacter = scr.getById(this.charId);
    List<Relationship> rels = srr.getAllByCharacter(firstCharacter);


    if (rels.size() > 0) {
      for (Relationship r: rels) {
        String secondCharacter = r.getSecondCharacter().getName();
        String name = r.getName();
        TextView tv = new TextView(this);
        tv.setPadding(4, 10, 4, 10);
        tv.setText(firstCharacter.getName() + " -> " + secondCharacter + ": " + name);

        tv.setTag(R.id.first_character_tag, this.charId);
        tv.setTag(R.id.second_character_tag, r.getSecondCharacter().getId());
        tv.setTag(R.id.relationship_name_tag, name);
        tv.setOnLongClickListener(new OnLongClickListenerRelationshipRecord(this));
        linearLayoutRecords.addView(tv);
      }
    } else {
      TextView locationItem = new TextView(this);
      locationItem.setPadding(8, 8, 8, 8);
      locationItem.setText("No relations yet.");
      linearLayoutRecords.addView(locationItem);
    }
  }
}
