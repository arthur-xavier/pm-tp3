package br.ufmg.dcc052.oncebook.character;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

/**
 * Created by xavier on 6/9/16.
 */
public class CharacterRepositoryTest extends AndroidTestCase {

  private ICharacterRepository characterRepository;

  private Character character1;
  private Character character2;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    characterRepository = new SQLiteCharacterRepository(context);

    character1 = new Character("Character1", "This is test character 1.", null);
    character2 = new Character("Character2", "This is test character 2.", null);
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertCharacter() {
    characterRepository.save(character1);

    Character testCharacter = characterRepository.getById(1);
    assertEquals(character1.getName(), testCharacter.getName());
    assertEquals(character1.getDescription(), testCharacter.getDescription());
  }

  public void testUpdateCharacter() {
    characterRepository.save(character1);
    character1.setDescription("Lorem ipsum dolor sit amet.");
    characterRepository.save(character1);

    Character testCharacter = characterRepository.getById(character1.getId());
    assertEquals(character1.getDescription(), testCharacter.getDescription());
  }

  public void testGetCharacterById() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    Character testCharacter1 = characterRepository.getById(1);
    assertEquals(character1.getName(), testCharacter1.getName());
    assertEquals(character1.getDescription(), testCharacter1.getDescription());

    Character testCharacter2 = characterRepository.getById(2);
    assertEquals(character2.getName(), testCharacter2.getName());
    assertEquals(character2.getDescription(), testCharacter2.getDescription());
  }

  public void testGetAllCharacters() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    List<Character> allCharacters = characterRepository.getAll();
    assertEquals(2, allCharacters.size());
    assertEquals(character1.getName(), allCharacters.get(0).getName());
    assertEquals(character2.getName(), allCharacters.get(1).getName());
  }

  public void testFindCharacterByName() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    List<Character> characterCharacters = characterRepository.findByName("Character");
    assertEquals(2, characterCharacters.size());
    assertEquals(character1.getName(), characterCharacters.get(0).getName());
    assertEquals(character2.getName(), characterCharacters.get(1).getName());

    List<Character> character1Characters = characterRepository.findByName("Character1");
    assertEquals(1, character1Characters.size());
    assertEquals(character1.getName(), character1Characters.get(0).getName());
  }

  public void testDeleteCharacter() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    characterRepository.delete(character1);

    List<Character> allBooks = characterRepository.getAll();
    assertEquals(1, allBooks.size());
    assertEquals(character2.getName(), allBooks.get(0).getName());
  }
}
