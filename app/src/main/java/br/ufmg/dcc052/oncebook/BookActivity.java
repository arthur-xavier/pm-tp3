package br.ufmg.dcc052.oncebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

public class BookActivity extends ActionBarActivity {
  public final static String CHARACTER_ID = "br.ufmg.dcc052.oncebook.CHARACTER_ID";
  public int bookId;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book);
    Intent intent = getIntent();
    int bookId = Integer.parseInt(intent.getStringExtra(MainActivity.BOOK_ID));
    this.bookId = bookId;
    Button buttonCreateCharacter = (Button) findViewById(R.id.buttonCreateCharacter);
    buttonCreateCharacter.setOnClickListener(new OnClickListenerCreateCharacter(this));
    readRecords();
    countRecords();
  }

  public void countRecords() {
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(this);
    int count = scr.getAllCursor().getCount();
    TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
    textViewRecordCount.setText(count + " characters found.");
  }

  public void readRecords() {
    LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
    linearLayoutRecords.removeAllViews();
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(this);
    List<Character> chars = scr.getAll();

    if (chars.size() > 0) {
      for (Character c: chars) {
        int id = c.getId();
        String name = c.getName();
        String description = c.getDescription();
        TextView tv = new TextView(this);
        tv.setPadding(4, 10, 4, 10);
        tv.setText("Name: " + name+"\r\nDescription: "+description+"\r\n");

        tv.setTag(Integer.toString(id));
        tv.setOnLongClickListener(new OnLongClickListenerCharacterRecord(this));
        tv.setOnClickListener(new OnClickListenerCharacterRecord(this));
        linearLayoutRecords.addView(tv);
      }
    } else {
      TextView locationItem = new TextView(this);
      locationItem.setPadding(8, 8, 8, 8);
      locationItem.setText("No characters yet.");
      linearLayoutRecords.addView(locationItem);
    }
  }
}
