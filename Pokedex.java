import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Pokedex {
  private ArrayList<Pokemon> pokedex;
  Font font;
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  public Pokedex() throws FileNotFoundException, IOException{

    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/font.ttf")).deriveFont(28f);
    } catch (IOException | FontFormatException e) {}

    pokedex = new ArrayList<Pokemon>();

    String filename = "data.txt";
    Scanner file = new Scanner(new File(filename));
    while (file.hasNextLine()) {
      String name = file.next();
      String type = file.next();
      int hp = file.nextInt();
      int dmg = file.nextInt();
      int defense = file.nextInt();
      Pokemon p = new Pokemon(name, type, hp, dmg, defense);
      ArrayList<Attack> pMoves = new ArrayList<Attack>();
      String attk1Name = file.next();
      int pp1 = file.nextInt(); 
      int power1 = file.nextInt();
      int acc1 = file.nextInt();
      Attack attk1 = new Attack(attk1Name, pp1, power1, acc1);
      String attk2Name = file.next();
      int pp2 = file.nextInt(); 
      int power2 = file.nextInt();
      int acc2 = file.nextInt();
      Attack attk2 = new Attack(attk2Name, pp2, power2, acc2);
      String attk3Name = file.next();
      int pp3 = file.nextInt(); 
      int power3 = file.nextInt();
      int acc3 = file.nextInt();
      Attack attk3 = new Attack(attk3Name, pp3, power3, acc3); 
      pMoves.add(attk1);
      pMoves.add(attk2);
      pMoves.add(attk3);
      p.addMoveSet(pMoves);
      pokedex.add(p);
    }
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

  public int getPokemonIndex(Pokemon p) {
    for (int i = 0; i < pokedex.size(); i++) {
      if (pokedex.get(i).getName().equals(p.getName())){
        return i;
      }
    }
    return 0;
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
    m.getFrame().setTitle(p.getName() + "'s Turn");
    int[] layerPos = new int[1];
    layerPos[0] = 0;
    // Background
    JLabel wp = new JLabel(new ImageIcon("assets/ui/pokemon_selection.png"));
    wp.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    selection.add(wp, Integer.valueOf(layerPos[0]));
    layerPos[0]++;

    // Player name on Top
    JLabel playerName = newPlayerNameDisplay("Select 3 Pokemon",225, 75, 600, 50);
    selection.add(playerName, Integer.valueOf(layerPos[0]));
    layerPos[0]++;

    ArrayList<Integer> pokemonSlots = new ArrayList<Integer>();

    for (int i = 0; i < pokedex.size(); i++) {
      pokemonSlots.add(i);
    }

    Random rand = new Random();

    int pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon1 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon2 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon3 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon4 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon5 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    pokemonNum = rand.nextInt(pokemonSlots.size()+1);
    Pokemon pokemon6 = pokedex.get(pokemonNum);
    pokemonSlots.remove(pokemonNum);
    JButton pokemonButton1 = newPokemonButton(pokemon1.getName(), 82, 150, 296, 116);
    pokemonButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton1.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon1);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton1, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
    JButton pokemonButton2 = newPokemonButton(pokemon2.getName(), 82, 305, 296, 116);
    pokemonButton2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton2.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon2);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton2, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
    JButton pokemonButton3 = newPokemonButton(pokemon3.getName(), 82, 460, 296, 116);
    pokemonButton3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton3.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon3);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton3, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
    JButton pokemonButton4 = newPokemonButton(pokemon4.getName(), 412, 150, 296, 116);
    pokemonButton4.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton4.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon4);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton4, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
    JButton pokemonButton5 = newPokemonButton(pokemon5.getName(), 412, 305, 296, 116);
    pokemonButton5.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton5.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon5);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton5, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
    JButton pokemonButton6 = newPokemonButton(pokemon6.getName(), 412, 460, 296, 116);
    pokemonButton6.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        pokemonButton6.setVisible(false);
        if (p.getBackpack().size() < 3) {
          p.addPokemon(pokemon6);
        }
        if (p.getBackpack().size() == 3) {
          selection.setVisible(false);
          if (p.getName().equals("Player 1")) {
            Pokedex pokedex;
            try {
              pokedex = new Pokedex();
              JLayeredPane newPanel = pokedex.newSelectionPanel(m.getPlayer2(), m);
              m.addNewPanel(newPanel);
            } catch (IOException e1) {
              e1.printStackTrace();
            }    
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
    selection.add(pokemonButton6, Integer.valueOf(layerPos[0]));
    layerPos[0]++;
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