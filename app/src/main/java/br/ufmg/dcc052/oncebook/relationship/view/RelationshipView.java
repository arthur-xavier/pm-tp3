package br.ufmg.dcc052.oncebook.relationship.view;

import br.ufmg.dcc052.oncebook.OnItemsListener;
import br.ufmg.dcc052.oncebook.relationship.domain.Relationship;

/**
 * Created by xavier on 6/14/16.
 */
public interface RelationshipView extends OnItemsListener<Relationship> {
  void showSaveRelationshipDialog();
  void showRelationshipSelectedDialog();
  void showRelationshipDeletedToast();
}
