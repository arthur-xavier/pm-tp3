package br.ufmg.dcc052.oncebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

public class BookActivity extends ActionBarActivity {
  private int bookId;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book);
    Intent intent = getIntent();
    int bookId = Integer.parseInt(intent.getStringExtra(MainActivity.BOOK_ID));
    this.bookId = bookId;
    Button buttonCreateCharacter = (Button) findViewById(R.id.buttonCreateCharacter);
    buttonCreateCharacter.setOnClickListener(new OnClickListenerCreateCharacter(this));
  }

  public void countRecords() {
    SQLiteCharacterRepository scr = new SQLiteCharacterRepository(this);
    int count = scr.count();
    TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
    textViewRecordCount.setText(count + " characters found.");
  }
}
