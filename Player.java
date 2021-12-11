import java.util.*;

public class Player {
  private String name;
  private ArrayList<Pokemon> backpack;

  public Player(String name) {
    this.name = name;
    backpack = new ArrayList<Pokemon>();
  }

  public String getName() {
    return name;
  }

  public int numPokemon() {
    return backpack.size();
  }

  public void addPokemon(Pokemon p) {
    backpack.add(p);
  }
  
  public Pokemon throwOut(String pokemon) {
    for (int i = 0; i < backpack.size(); i++) {
      if (backpack.get(i).getName().toLowerCase().equals(pokemon)) {
        return backpack.get(i);
      }
    }
    return null;
  }

  public void printBackpack() {
    for (int i = 0; i < backpack.size(); i++) {
      System.out.println(backpack.get(i));
    }
  }
  
  public void printBackpackBattle() {
    for (int i = 0; i < backpack.size(); i++) {
      System.out.println(backpack.get(i).displayHealth());
    }
  }

  public ArrayList<Pokemon> getBackpack() {
    return backpack;
  }

  public String toString() {
    String finals = "";
    finals += name + "'s Pokemons:\n";
    for (int i = 0; i < backpack.size(); i++) {
      finals += backpack.get(i) + "\n";
    }
    return finals;
  }
}
