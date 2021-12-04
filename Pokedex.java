import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
    Attack pound = new Attack("Pound", 35, 40, 100);
    Attack covet = new Attack("Covet", 25, 60, 100);

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

    // Jigglypuff
    Pokemon jigglypuff = new Pokemon("Jiggly", "Normal", 70, 45, 20);
    ArrayList<Attack> jigglyAttack = new ArrayList<Attack>();
    jigglyAttack.add(pound);
    jigglyAttack.add(covet);
    jigglyAttack.add(doubleEdge);
    jigglypuff.addMoveSet(eeveeAttack);
    pokedex.add(jigglypuff);
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

  public ArrayList<Pokemon> getPokedex() {
    return pokedex;
  }

  public ArrayList<JPanel> getPokemonLabels() {
    ArrayList<JLabel> pokemonLabels = new ArrayList<JLabel>();
    ArrayList<JPanel> pokemonPanels = new ArrayList<JPanel>();
    Font font = new Font("PKMN RBYGSC", Font.PLAIN, 30);
    for (int i = 0; i < pokedex.size(); i++) {
      JLabel pokeJLabel = new JLabel(pokedex.get(i).getName());
      pokeJLabel.setHorizontalAlignment(JLabel.CENTER);
      pokeJLabel.setForeground(Color.black);
      pokeJLabel.setFont(font);
      pokeJLabel.setVisible(true);
      pokemonLabels.add(pokeJLabel);
    }
    int x = 82, y = 150;
    for (int i = 0; i < pokemonLabels.size(); i++) {
      JPanel pokeJPanel = new JPanel();
      pokeJPanel.setLayout(new GridBagLayout());
      pokeJPanel.setBounds(x,y, 296, 116);
      LineBorder line = new LineBorder(Color.black, 5, true);
      pokeJPanel.setBorder(line);
      pokeJPanel.setBackground(Color.white);
      if (y == 460) {
        x = 412;
        y = 150;
      }
      else {
        y += 155;
      }
      pokeJPanel.add(pokemonLabels.get(i));
      pokeJPanel.setVisible(true);
      pokemonPanels.add(pokeJPanel);
    }
    return pokemonPanels;
  }

  // public Pokemon getPokemonByJPanel(JPanel p, Container con) {
  //   for (int i = 0; i < pokedex.size(); i++) {
  //     if (con.getName())
  //   }

  // }

  public void printPokedex() {
    for (int i = 0; i < pokedex.size(); i++) {
      System.out.println(pokedex.get(i).displayHealth());
    }
  }
}