package br.ufmg.dcc052.oncebook;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.book.Book;
import java.util.List;
public class MainActivity extends ActionBarActivity {

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;
  public final static String BOOK_ID = "br.ufmg.dcc052.oncebook.BOOK_ID";

  public void countRecords() {
    SQLiteBookRepository sbr = new SQLiteBookRepository(this);
    int count = sbr.count();
    TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
    textViewRecordCount.setText(count + " records found.");
  }

  public void readRecords() {
    LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
    linearLayoutRecords.removeAllViews();
    SQLiteBookRepository sbr = new SQLiteBookRepository(this);
    List<Book> books = sbr.getAll();

    if (books.size() > 0) {
      for (Book book: books) {
        int id = book.getId();
        String name = book.getName();
        TextView tv = new TextView(this);
        tv.setPadding(4, 10, 4, 10);
        tv.setText(name);
        tv.setTag(Integer.toString(id));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setOnLongClickListener(new OnLongClickListenerBookRecord(this));
        tv.setOnClickListener(new OnClickListenerBookRecord(this));
        linearLayoutRecords.addView(tv);
      }
    } else {
      TextView locationItem = new TextView(this);
      locationItem.setPadding(8, 8, 8, 8);
      locationItem.setText("No books yet.");
      linearLayoutRecords.addView(locationItem);
    }
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button buttonCreateBookLocation = (Button) findViewById(R.id.buttonCreateBook);
    buttonCreateBookLocation.setOnClickListener(new OnClickListenerCreateBook(this));
    mTitle = getTitle();
    readRecords();
    countRecords();
  }

  public void onSectionAttached(int number) {
    switch (number) {
      case 1:
        mTitle = getString(R.string.title_section1);
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        break;
      case 3:
        mTitle = getString(R.string.title_section3);
        break;
    }
  }

  public void restoreActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      ((MainActivity) activity).onSectionAttached(
              getArguments().getInt(ARG_SECTION_NUMBER));
    }
  }

}
