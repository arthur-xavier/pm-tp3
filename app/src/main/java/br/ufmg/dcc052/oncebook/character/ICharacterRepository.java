package br.ufmg.dcc052.oncebook.character;

import android.database.Cursor;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.storage.IRepository;

/**
 * Created by xavier on 6/6/16.
 */
public interface ICharacterRepository extends IRepository<Character, Integer> {
  @Override
  public Character getById(Integer id);
  @Override
  public List<Character> getAll();
  public Cursor getAllByBookCursor(Book book);
  public List<Character> findByName(String name);
  @Override
  public void save(Character character);
  @Override
  public void delete(Character character);
}

