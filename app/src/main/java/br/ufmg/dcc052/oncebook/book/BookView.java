package br.ufmg.dcc052.oncebook.book;

import br.ufmg.dcc052.oncebook.OnItemsListener;

/**
 * Created by xavier on 6/14/16.
 */
public interface BookView extends OnItemsListener<Book> {
  void showSaveBookDialog();
  void showBookSelectedDialog();
  void showBookDeletedToast();
}
