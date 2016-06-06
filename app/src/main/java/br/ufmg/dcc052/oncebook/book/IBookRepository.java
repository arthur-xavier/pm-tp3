package br.ufmg.dcc052.oncebook.book;

import java.util.List;

import br.ufmg.dcc052.oncebook.storage.IRepository;

/**
 * Created by xavier on 6/6/16.
 */
public interface IBookRepository extends IRepository<Book, Long> {
  @Override
  public Book getById(Long id);
  @Override
  public List<Book> getAll();
  public List<Book> findByName(String name);
  @Override
  public void save(Book book);
  @Override
  public void delete(Book book);
}
