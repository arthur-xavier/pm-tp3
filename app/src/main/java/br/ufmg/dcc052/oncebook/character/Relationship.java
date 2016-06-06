package br.ufmg.dcc052.oncebook.character;

/**
 * Created by xavier on 6/6/16.
 */
public abstract class Relationship {

  private String name;

  public Relationship(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public abstract Character[] getCharacters();
}
