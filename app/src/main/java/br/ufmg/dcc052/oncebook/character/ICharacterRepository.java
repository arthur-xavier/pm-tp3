package br.ufmg.dcc052.oncebook.character;

import java.util.List;

import br.ufmg.dcc052.oncebook.storage.IRepository;

/**
 * Created by xavier on 6/6/16.
 */
public interface ICharacterRepository extends IRepository<Character, Long> {
  @Override
  public Character getById(Long id);
  @Override
  public List<Character> getAll();
  public List<Character> findByName(String name);
  @Override
  public void save(Character character);
  @Override
  public void delete(Character character);
}

