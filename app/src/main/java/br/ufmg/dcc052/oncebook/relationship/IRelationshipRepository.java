package br.ufmg.dcc052.oncebook.relationship;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.storage.IRepository;

/**
 * Created by xavier on 6/6/16.
 */
public interface IRelationshipRepository extends IRepository<Relationship, Integer> {
  @Override
  public Relationship getById(Integer id);
  @Override
  public List<Relationship> getAll();
  @Override
  public void save(Relationship relationship);
  @Override
  public void delete(Relationship relationship);
}

