package br.ufmg.dcc052.oncebook.relationship.data;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.domain.Character;
import br.ufmg.dcc052.oncebook.data.Repository;
import br.ufmg.dcc052.oncebook.relationship.domain.Relationship;

/**
 * Created by xavier on 6/6/16.
 */
public interface RelationshipRepository extends Repository<Relationship, Integer> {
  List<Relationship> getAllByCharacter(Character character);
}

