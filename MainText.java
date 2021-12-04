import java.util.*;
public class MainText {
  public static void main(String[] args) {
    boolean hasDuplicate = false;
    Scanner in = new Scanner(System.in);
    Pokedex pokedex = new Pokedex();
    Player p1 = new Player("Player 1");
    Player p2 = new Player("Player 2");

    System.out.println();
    System.out.println("Welcome Players to Munta's Pokemon Showdown!");
    System.out.println("Here is the current Pokedex: ");
    pokedex.printPokedex();
    System.out.println();
    System.out.println("Player 1, Please select a Pokemon!");
    for (int i = 0; i < 3; i++) {
      System.out.println("Enter your Pokemon's below:");
      System.out.print("> ");
      String selected = in.next();
      System.out.println();
      Pokemon selectedPokemon = pokedex.getPokemon(selected.toLowerCase());
      if (selectedPokemon == null) {
        System.out.println("Invalid Pokemon, enter again!");
        i--;
        System.out.println();
      }
      else {
        for (int j = 0; j < p1.getBackpack().size(); j++) {
          if (p1.getBackpack().get(j).getName().toLowerCase().equals(selected.toLowerCase())) {
            hasDuplicate = true;
          }
        }
        if (!hasDuplicate) {
          System.out.println("Added " + selectedPokemon.getName() + " to Player 1's backpack.");
          p1.addPokemon(selectedPokemon);
          System.out.println();
        }
        else {
          System.out.println("Duplicate Pokemon! Enter a different Pokemon.");
          i--;
          System.out.println();
        }
        hasDuplicate = false;
      }
    }
    System.out.println();
    System.out.println("Player 2, Please select a Pokemon!");
    for (int i = 0; i < 3; i++) {
      System.out.println("Enter your Pokemon's below:");
      System.out.print("> ");
      String selected = in.next();
      System.out.println();
      Pokemon selectedPokemon = pokedex.getPokemon(selected);
      if (selectedPokemon == null) {
        System.out.println("Invalid Pokemon, enter again!");
        i--;
        System.out.println();
      }
      else {
        for (int j = 0; j < p2.getBackpack().size(); j++) {
          if (p2.getBackpack().get(j).getName().toLowerCase().equals(selected.toLowerCase())) {
            hasDuplicate = true;
          }
        }
        if (!hasDuplicate) {
          System.out.println("Added " + selectedPokemon.getName() + " to Player 1's backpack.");
          p2.addPokemon(selectedPokemon);
          System.out.println();
        }
        else {
          System.out.println("Duplicate Pokemon! Enter a different Pokemon.");
          i--;
          System.out.println();
        }
        hasDuplicate = false;
      }
    }

    System.out.println("P1's size: " + p1.getBackpack().size());
    System.out.println("P2's size: " + p2.getBackpack().size());

    // p1.addPokemon(pokedex.getPokemon("Pikachu"));
    // p1.addPokemon(pokedex.getPokemon("Bulbasaur"));
    // p2.addPokemon(pokedex.getPokemon("Eevee"));
    // p2.addPokemon(pokedex.getPokemon("Squirtle"));
    Battle battle = new Battle(p1, p2);
    battle.startBattle(p1.getBackpack().get(0), p2.getBackpack().get(0));
    in.close();
  }
}
