package br.ufmg.dcc052.oncebook.character;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.relationship.Relationship;

/**
 * Created by xavier on 6/6/16.
 */
public class Character {

  private long id;
  private String name;
  private String description;
  private boolean isAlive;
  private int appearancePage;
  private Bitmap picture;
  private List<Relationship> relationships;

  public Character(String name) {
    this(name, "", true, 0, null);
  }

  public Character(String name, String description, boolean isAlive, int appearancePage, Bitmap picture) {
    this.name = name;
    this.description = description;
    this.isAlive = isAlive;
    this.appearancePage = appearancePage;
    this.picture = picture;
    this.relationships = new ArrayList<Relationship>();
  }

  public Character(long id, String name, String description, boolean isAlive, int appearancePage, Bitmap picture) {
    this(name, description, isAlive, appearancePage, picture);
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

  public boolean isAlive() {
    return isAlive;
  }
  public void setIsAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  public int getAppearancePage() {
    return appearancePage;
  }
  public void setAppearancePage(int appearancePage) {
    this.appearancePage = appearancePage;
  }

  public Bitmap getPicture() {
    return picture;
  }
  public void setPicture(Bitmap picture) {
    this.picture = picture;
  }

  public List<Relationship> getRelationships() {
    return relationships;
  }
}
