package br.ufmg.dcc052.oncebook.book;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class Book {

  private String name;
  private String description;
  private List<Character> characters;

  public Book(String name) {
    this(name, "");
  }

  public Book(String name, String description) {
    this.name = name;
    this.description = description;
    this.characters = new ArrayList<Character>();
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public List<Character> getCharacters() {
    return characters;
  }
  public void setCharacters(List<Character> characters) {
    this.characters = characters;
  }
}
