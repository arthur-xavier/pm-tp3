package br.ufmg.dcc052.oncebook.book.view;

import br.ufmg.dcc052.oncebook.OnItemsListener;
import br.ufmg.dcc052.oncebook.book.domain.Book;

/**
 * Created by xavier on 6/14/16.
 */
public interface BookView extends OnItemsListener<Book> {
  void showSaveBookDialog();
  void showBookSelectedDialog();
  void showBookDeletedToast();
}
