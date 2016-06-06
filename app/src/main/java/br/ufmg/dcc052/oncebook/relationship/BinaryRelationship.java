package br.ufmg.dcc052.oncebook.relationship;

import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class BinaryRelationship extends Relationship {

  private br.ufmg.dcc052.oncebook.character.Character[] characters;

  public BinaryRelationship(String name) {
    super(name);
    this.characters = new Character[2];
  }

  @Override
  public Character[] getCharacters() {
    return this.characters;
  }
  public void setCharacters(Character first, Character second) {
    this.characters[0] = first  != null ? first  : this.characters[0];
    this.characters[1] = second != null ? second : this.characters[1];
  }
}
