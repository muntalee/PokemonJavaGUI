import java.util.*;
public class Pokedex {
  private ArrayList<Pokemon> pokedex;
  public Pokedex() {
    pokedex = new ArrayList<Pokemon>();
    // bunch of attacks
    Attack tackle = new Attack("Tackle", 35, 40, 100);
    Attack vineWhip = new Attack("Vine-Whip", 25, 45, 100);
    Attack doubleEdge = new Attack("Double-Edge", 15, 25, 95);
    Attack scratch = new Attack("Scratch", 35, 40, 100);
    Attack slash = new Attack("Slash", 20, 70, 100);
    Attack fireFang = new Attack("Fire-Fang", 15, 64, 95);
    Attack rapidSpin = new Attack("Rapid-Spin", 40, 50, 100);
    Attack bite = new Attack("Bite", 25, 60, 100);
    Attack nuzzle = new Attack("Nuzzle", 20, 20, 100);
    Attack feint = new Attack("Feint", 10, 30, 100);
    Attack spark = new Attack("Spark", 20, 64, 100);

    /* LIST OF ALL POKEMON*/

    // Bulbasaur
    Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49);
    ArrayList<Attack> bulbasaurAttack = new ArrayList<Attack>();
    bulbasaurAttack.add(tackle);
    bulbasaurAttack.add(vineWhip);
    bulbasaurAttack.add(doubleEdge);
    bulbasaur.addMoveSet(bulbasaurAttack);
    pokedex.add(bulbasaur);

    // Charmander
    Pokemon charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
    ArrayList<Attack> charmanderAttack = new ArrayList<Attack>();
    charmanderAttack.add(scratch);
    charmanderAttack.add(slash);
    charmanderAttack.add(fireFang);
    charmander.addMoveSet(charmanderAttack);
    pokedex.add(charmander);
    
    // Squirtle
    Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65);
    ArrayList<Attack> squirtleAttack = new ArrayList<Attack>();
    squirtleAttack.add(tackle);
    squirtleAttack.add(rapidSpin);
    squirtleAttack.add(bite);
    squirtle.addMoveSet(squirtleAttack);
    pokedex.add(squirtle);
    
    // Pikachu
    Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 40);
    ArrayList<Attack> pikachuAttack = new ArrayList<Attack>();
    pikachuAttack.add(nuzzle);
    pikachuAttack.add(feint);
    pikachuAttack.add(spark);
    pikachu.addMoveSet(pikachuAttack);
    pokedex.add(pikachu);
    
    // Eevee (Add image later)
    Pokemon eevee = new Pokemon("Eevee", "Normal", 55, 55, 50);
    ArrayList<Attack> eeveeAttack = new ArrayList<Attack>();
    eeveeAttack.add(tackle);
    eeveeAttack.add(bite);
    eeveeAttack.add(doubleEdge);
    eevee.addMoveSet(eeveeAttack);
    pokedex.add(eevee);
  }

  public Pokemon getPokemonByIndex(int n) {
    return pokedex.get(n);
  }

  public Pokemon getPokemon(String name) {
    for (int i = 0; i < pokedex.size(); i++) {
      if (pokedex.get(i).getName().toLowerCase().equals(name)) {
        return pokedex.get(i);
      }
    }
    return null;
  }

  public void printPokedex() {
    for (int i = 0; i < pokedex.size(); i++) {
      System.out.println(pokedex.get(i).displayHealth());
    }
  }

}