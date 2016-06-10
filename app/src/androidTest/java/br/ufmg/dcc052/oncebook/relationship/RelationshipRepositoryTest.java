package br.ufmg.dcc052.oncebook.relationship;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.ICharacterRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

/**
 * Created by xavier on 6/9/16.
 */
public class RelationshipRepositoryTest extends AndroidTestCase {

  private IRelationshipRepository relationshipRepository;
  private ICharacterRepository characterRepository;

  private Character character1;
  private Character character2;
  private Character character3;

  private Relationship relationship1;
  private Relationship relationship2;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    relationshipRepository = new SQLiteRelationshipRepository(context);
    characterRepository = new SQLiteCharacterRepository(context);

    character1 = new Character("Character1", "This is test character 1.", null);
    character2 = new Character("Character2", "This is test character 2.", null);
    character3 = new Character("Character3", "This is test character 3.", null);

    relationship1 = new Relationship("Relationship1", character1, character2);
    relationship2 = new Relationship("Relationship2", character2, character3);
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertRelationship() {
    insertCharacters();

    relationshipRepository.save(relationship1);

    List<Relationship> relationships = relationshipRepository.getAll();
    assertEquals(1, relationships.size());
    assertEquals(relationship1.getName(), relationships.get(0).getName());
    assertEquals(relationship1.getFirstCharacter().getName(),
      relationships.get(0).getFirstCharacter().getName());
    assertEquals(relationship1.getSecondCharacter().getName(),
      relationships.get(0).getSecondCharacter().getName());
  }

  public void testUpdateRelationship() {
    insertCharacters();

    relationshipRepository.save(relationship1);
    relationship1.setName("Lorem ipsum");
    relationshipRepository.save(relationship1);

    List<Relationship> relationships = relationshipRepository.getAll();
    assertEquals(1, relationships.size());
    assertEquals(relationship1.getFirstCharacter().getName(),
      relationships.get(0).getFirstCharacter().getName());
    assertEquals(relationship1.getSecondCharacter().getName(),
      relationships.get(0).getSecondCharacter().getName());
    assertEquals(relationship1.getName(), relationships.get(0).getName());
  }

  public void testGetAllRelationships() {
    insertCharacters();

    relationshipRepository.save(relationship1);
    relationshipRepository.save(relationship2);

    List<Relationship> allRelationships = relationshipRepository.getAll();
    assertEquals(2, allRelationships.size());
    assertEquals(relationship1.getName(), allRelationships.get(0).getName());
    assertEquals(relationship2.getName(), allRelationships.get(1).getName());
  }

  public void testDeleteRelationship() {
    insertCharacters();

    relationshipRepository.save(relationship1);
    relationshipRepository.save(relationship2);

    relationshipRepository.delete(relationship1);

    List<Relationship> allRelationships = relationshipRepository.getAll();
    assertEquals(1, allRelationships.size());
    assertEquals(relationship2.getName(), allRelationships.get(0).getName());
  }

  private void insertCharacters() {
    characterRepository.save(character1);
    characterRepository.save(character2);
    characterRepository.save(character3);
  }
}
