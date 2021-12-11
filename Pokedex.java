import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Pokedex {
  private ArrayList<Pokemon> pokedex;
  Font font = new Font("PKMN RBYGSC", Font.PLAIN, 28);
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
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

    // 0. Bulbasaur
    Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49);
    ArrayList<Attack> bulbasaurAttack = new ArrayList<Attack>();
    bulbasaurAttack.add(tackle);
    bulbasaurAttack.add(vineWhip);
    bulbasaurAttack.add(doubleEdge);
    bulbasaur.addMoveSet(bulbasaurAttack);
    pokedex.add(bulbasaur);

    // 1. Charmander
    Pokemon charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
    ArrayList<Attack> charmanderAttack = new ArrayList<Attack>();
    charmanderAttack.add(scratch);
    charmanderAttack.add(slash);
    charmanderAttack.add(fireFang);
    charmander.addMoveSet(charmanderAttack);
    pokedex.add(charmander);
    
    // 2. Squirtle
    Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65);
    ArrayList<Attack> squirtleAttack = new ArrayList<Attack>();
    squirtleAttack.add(tackle);
    squirtleAttack.add(rapidSpin);
    squirtleAttack.add(bite);
    squirtle.addMoveSet(squirtleAttack);
    pokedex.add(squirtle);
    
    // 3. Pikachu
    Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 40);
    ArrayList<Attack> pikachuAttack = new ArrayList<Attack>();
    pikachuAttack.add(nuzzle);
    pikachuAttack.add(feint);
    pikachuAttack.add(spark);
    pikachu.addMoveSet(pikachuAttack);
    pokedex.add(pikachu);
    
    // 4. Eevee (Add image later)
    Pokemon eevee = new Pokemon("Eevee", "Normal", 55, 55, 50);
    ArrayList<Attack> eeveeAttack = new ArrayList<Attack>();
    eeveeAttack.add(tackle);
    eeveeAttack.add(bite);
    eeveeAttack.add(doubleEdge);
    eevee.addMoveSet(eeveeAttack);
    pokedex.add(eevee);

    // 5. Jigglypuff
    Pokemon jigglypuff = new Pokemon("Jigglypuff", "Normal", 70, 45, 20);
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
    String pokemonName = name.toLowerCase();
    pokemonName = pokemonName.substring(0, 1).toUpperCase() + pokemonName.substring(1);
    for (int i = 0; i < pokedex.size(); i++) {
      if (pokedex.get(i).getName().toLowerCase().equals(pokemonName)) {
        return pokedex.get(i);
      }
    }
    return null;
  }

  public JButton newPokemonButton(String name, int x, int y, int width, int height) {
    JButton pokemonButton = new JButton(name);
    pokemonButton.setBounds(x,y,width,height);
    pokemonButton.setFont(font);
    pokemonButton.setOpaque(false);
    pokemonButton.setContentAreaFilled(false);
    pokemonButton.setBorderPainted(false);
    pokemonButton.setForeground(Color.black);
    pokemonButton.setVisible(true);
    return pokemonButton;
  }

  public JLabel newPlayerNameDisplay(String name, int x, int y, int width, int height) {
    JLabel playerLabel = new JLabel(name);
    playerLabel.setBounds(x,y,width,height);
    playerLabel.setFont(font);
    playerLabel.setOpaque(false);
    playerLabel.setForeground(Color.black);
    playerLabel.setVisible(true);
    return playerLabel;
  }

  public JLayeredPane newSelectionPanel(Player p, Main m) {
    JLayeredPane selection = new JLayeredPane();
    selection.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    selection.setBackground(Color.white);
    selection.setLayout(null);

    // Background
    JLabel wp = new JLabel(new ImageIcon("assets/ui/pokemon_selection.png"));
    wp.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    selection.add(wp, Integer.valueOf(0));

    // Player name on Top
    JLabel playerName = newPlayerNameDisplay(p.getName(), 320, 35, 296, 116);
    selection.add(playerName, Integer.valueOf(1));

    // 1. PIKACHU
    JButton pikachuButton = newPokemonButton("Pikachu", 82, 150, 296, 116);
    pikachuButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pikachuButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(3));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);                           
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(pikachuButton, Integer.valueOf(2));

    // 2. BULBASAUR
    JButton bulbasaurButton = newPokemonButton("Bulbasaur", 82, 305, 296, 116);
    bulbasaurButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        bulbasaurButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(0));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(bulbasaurButton, Integer.valueOf(3));

    // 3. SQUIRTLE
    JButton squirtleButton = newPokemonButton("Squirtle", 82, 460, 296, 116);
    squirtleButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        squirtleButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(2));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(squirtleButton, Integer.valueOf(4));

    // 4. CHARMANDER
    JButton charmanderButton = newPokemonButton("Charmander", 412, 150, 296, 116);
    charmanderButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        charmanderButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(1));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(charmanderButton, Integer.valueOf(5));

    // 5. EEVEE
    JButton eeveeButton = newPokemonButton("Eevee", 412, 305, 296, 116);
    eeveeButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        eeveeButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(4));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(eeveeButton, Integer.valueOf(6));

    // 6. JIGGLYPUFF
    JButton jigglypuffButton = newPokemonButton("Jigglypuff", 412, 460, 296, 116);
    jigglypuffButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        jigglypuffButton.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(getPokemonByIndex(5));
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex = new Pokedex();
            JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
            m.addNewPanel(newPanel);
          }
          else {
            try {
              m.startBattle();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
      } 
    });
    selection.add(jigglypuffButton, Integer.valueOf(7));
    selection.setVisible(true);
    return selection;
  }

  public ArrayList<Pokemon> getPokedex() {
    return pokedex;
  }

  public void printPokedex() {
    for (int i = 0; i < pokedex.size(); i++) {
      System.out.println(pokedex.get(i).displayHealth());
    }
  }
}