package br.ufmg.dcc052.oncebook.character.view;

import br.ufmg.dcc052.oncebook.OnItemsListener;

/**
 * Created by xavier on 6/14/16.
 */
public interface CharacterView extends OnItemsListener<br.ufmg.dcc052.oncebook.character.domain.Character> {
  void showSaveCharacterDialog();
  void showCharacterSelectedDialog();
  void showCharacterDeletedToast();
}
