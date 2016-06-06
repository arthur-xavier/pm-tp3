package br.ufmg.dcc052.oncebook.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class Book implements Serializable {

  private long id;
  private String name;
  @Nullable
  private String description;
  @Nullable
  private List<Character> characters;

  public Book(String name) {
    this(name, "");
  }

  public Book(String name, String description) {
    this.name = name;
    this.description = description;
    this.characters = new ArrayList<Character>();
  }

  public Book(long id, String name, String description) {
    this(name, description);
    this.id = id;
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
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
