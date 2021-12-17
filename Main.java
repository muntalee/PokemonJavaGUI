import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Main {
  JFrame frame = new JFrame();
  Player p1 = new Player("Player 1");
  Player p2 = new Player("Player 2");
  Item p1Items = new Item(p1);
  Item p2Items = new Item(p2);
  Container con = frame.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  final int POKE_OPP_DIM = 240;
  final int POKE_PLA_DIM = 350;
  boolean isPlayer1Turn = true;
  Font font;
  public static void main(String[] args) {
    new Main(true);
  }

  public Player getPlayer1() {
    return p1;
  }

  public Player getPlayer2() {
    return p2;
  }

  public Main(boolean ex) {

    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/font.ttf")).deriveFont(28f); 
    } catch (IOException | FontFormatException e) {}

    if (ex == true) {
      try {
        startGame();
      } catch(IOException e) {
        e.printStackTrace(); 
      }
    }
  }

  public void startGame() throws IOException {
    Pokedex pokedex = new Pokedex();
    frame.setTitle("Bootleg Pokemon Java Edition");
    frame.setSize(818, 740);
    frame.setResizable(false);
    con.setLayout(null);
    con.setBackground(new Color(255,255,255));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel title = new JPanel();
    title.setBounds(0, 0, WIDTH_PANEL, HEIGHT_PANEL);
    title.setBackground(Color.white);
    String path = "assets/ui/main_menu.png";
    File file = new File(path);
    BufferedImage titleImage = ImageIO.read(file);
    JLabel titleLabel = new JLabel(new ImageIcon(titleImage));
    title.add(titleLabel);
    con.add(title);
    JLayeredPane selection = pokedex.newSelectionPanel(p1, this);

    titleLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        title.setVisible(false);
        con.add(selection);
      }
    });

    con.revalidate();
    frame.setVisible(true); 
  }

  public void addNewPanel(JLayeredPane p) {
    con.add(p);
    con.revalidate();
  }

  JLayeredPane startNewBattle;

  public void startBattle() throws IOException {
    startNewBattle = mainBattleMenu(p1.getBackpack().get(0),p2.getBackpack().get(0), p1, p2);
    con.add(startNewBattle);
  }

  public JFrame getFrame() {
    return frame;
  }

  public JLayeredPane mainBattleMenu(Pokemon p1,Pokemon p2, Player ply1, Player ply2) throws IOException {
    // Show who's turn it is with the window title
    frame.setTitle(ply1.getName() + "'s Turn");
    // Pokemons
    Pokedex pokedex = new Pokedex();
    Pokemon p1Pokemon = p1;
    Pokemon p2Pokemon = p2;
    ArrayList<Integer> layerPos = new ArrayList<Integer>();

    // Background setup
    JLayeredPane menuPanel = new JLayeredPane();
    menuPanel.setLayout(null);
    menuPanel.setBounds(0, 0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.setBackground(Color.white);

    int i = 0;
    layerPos.add(i);
    // Brackets
    JLabel bracketLabel = new JLabel(new ImageIcon("assets/ui/name_bracket.png"));
    bracketLabel.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.add(bracketLabel, Integer.valueOf(Integer.valueOf(layerPos.size()-1)));
    layerPos.add(i);

    // Menu
    JLabel menuLabel = new JLabel(new ImageIcon("assets/ui/selection_menu.png"));
    menuLabel.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.add(menuLabel, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 1 Pokemon
    String p1PokemonFile = "assets/player/" + pokedex.getPokemonIndex(p1) + ".png";
    JLabel p1PokemonLabel = new JLabel(new ImageIcon(p1PokemonFile));
    p1PokemonLabel.setBounds(40,150, POKE_PLA_DIM, POKE_PLA_DIM);
    menuPanel.add(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 2 Pokemon
    String p2PokemonFile = "assets/opponent/" + pokedex.getPokemonIndex(p2) + ".png";
    JLabel p2PokemonLabel = new JLabel(new ImageIcon(p2PokemonFile));
    p2PokemonLabel.setBounds(523,10, POKE_OPP_DIM, POKE_OPP_DIM);
    menuPanel.add(p2PokemonLabel, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 1 Pokemon Name and Health
    JLabel p1PokemonName = newText(p1Pokemon.getName(), 410, 355, 350, 50);
    menuPanel.add(p1PokemonName, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    JLabel p1PokemonHealth = newText(p1Pokemon.getHealthBattle(), 410, 400, 350, 50);
    menuPanel.add(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 2 Pokemon Name and Health
    JLabel p2PokemonName = newText(p2Pokemon.getName(), 75, 45, 350, 50);
    menuPanel.add(p2PokemonName, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    JLabel p2PokemonHealth = newText(p2Pokemon.getHealthBattle(), 75, 90, 350, 50);
    menuPanel.add(p2PokemonHealth, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Buttons
    JButton fightButton = newButton("FIGHT", 360, 510, 180, 40);
    menuPanel.add(fightButton, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    JButton itemButton = newButton("ITEM", 360, 590, 180, 40);
    menuPanel.add(itemButton, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    JButton pkmnButton = newButton("PKMN", 580, 510, 180, 40);
    menuPanel.add(pkmnButton, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    JButton runButton = newButton("RUN", 580, 590, 180, 40);
    menuPanel.add(runButton, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    if (ply1.getName().equals("Player 1")) {
      if (p1Items.canHeal() && p1Items.canRevive()) {
        itemButton.setVisible(true);
      }
      else {
        itemButton.setVisible(false);
      }
    }
    else if (ply1.getName().equals("Player 2")) {
      if (p2Items.canHeal() && p2Items.canRevive()) {
        itemButton.setVisible(true);
      }
      else {
        itemButton.setVisible(false);
      }
    }

    // Check if all pokemon are dead
    int health1 = ply1.getBackpack().get(0).getHealth();
    int health2 = ply1.getBackpack().get(1).getHealth();
    int health3 = ply1.getBackpack().get(2).getHealth();

    if (health1 <= 0 && health2 <= 0 && health3 <= 0) {
      frame.setTitle("WINNER!!!");
      menuPanel.setVisible(false); 
      if (ply1.getName().equals("Player 1")) {
        JLayeredPane winnerPane = winner("player2Win");
        con.add(winnerPane);
        winnerPane.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            frame.dispose();
          }
        });
      }
      else {
        JLayeredPane winnerPane = winner("player1Win");
        con.add(winnerPane);
        winnerPane.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            frame.dispose();
          }
        });
      }
      menuPanel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e2) {
          frame.dispose();
        }
      });
    }
    // Check if Pokemon is dead (kinda buggy but works)
    else if (p1.getHealth() <= 0) {
      fightButton.setVisible(false);
      itemButton.setVisible(false);
      pkmnButton.setVisible(false);
      runButton.setVisible(false);
      p1PokemonName.setVisible(false);
      p1PokemonHealth.setVisible(false);
      p1PokemonLabel.setVisible(false);
      menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
      menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
      layerPos.add(i);
      JLabel msgLabel = newText("Your " + p1.getName() + " has died!", 80, 550, 600, 100);
      msgLabel.setVerticalAlignment(JLabel.TOP);
      menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
      layerPos.add(i);
      menuPanel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e2) {
          menuLabel.setIcon(new ImageIcon("assets/ui/pokemon_selection_battle.png"));
          menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          // All Pokemon Buttons
          JButton pokemonButton1 = newButton(ply1.getBackpack().get(0).getNameBattle(), 285, 200, 500, 40);
          pokemonButton1.setHorizontalAlignment(SwingConstants.LEFT);
          JButton pokemonButton2 = newButton(ply1.getBackpack().get(1).getNameBattle(), 285, 200+75, 500, 40);
          pokemonButton2.setHorizontalAlignment(SwingConstants.LEFT);
          JButton pokemonButton3 = newButton(ply1.getBackpack().get(2).getNameBattle(), 285, 200+75+75, 500, 40);
          pokemonButton3.setHorizontalAlignment(SwingConstants.LEFT);
          menuPanel.add(pokemonButton1, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          menuPanel.add(pokemonButton2, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          menuPanel.add(pokemonButton3, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          if (ply1.getBackpack().get(0).getHealth() <= 0) {
            pokemonButton1.setVisible(false);
          }
          if (ply1.getBackpack().get(1).getHealth() <= 0) {
            pokemonButton2.setVisible(false);
          }
          if (ply1.getBackpack().get(2).getHealth() <= 0) {
            pokemonButton3.setVisible(false);
          }
          // Dialog text
          JLabel msgLabel = newText("Select a Pokemon", 80, 550, 600, 100);
          msgLabel.setVerticalAlignment(JLabel.TOP);
          menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          pokemonButton1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(0).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                String newPokemonString = "assets/player/" + ply1.getBackpack().get(0).getName().toLowerCase() + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(0).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(0).getName());
                p1PokemonLabel.setVisible(true);
                p1PokemonHealth.setVisible(true);
                p1PokemonName.setVisible(true);
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(0).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(0), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
          pokemonButton2.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(1).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                String newPokemonString = "assets/player/" + ply1.getBackpack().get(1).getName().toLowerCase() + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(1).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(1).getName());
                p1PokemonLabel.setVisible(true);
                p1PokemonHealth.setVisible(true);
                p1PokemonName.setVisible(true);
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(1).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(1), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
          pokemonButton3.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(2).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                String newPokemonString = "assets/player/" + ply1.getBackpack().get(2).getName().toLowerCase() + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(2).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(2).getName());
                p1PokemonLabel.setVisible(true);
                p1PokemonHealth.setVisible(true);
                p1PokemonName.setVisible(true);
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(2).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(2), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
        }
      });

    }
    // Button Listener for Fight Button (Completed with bugs)
    fightButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        // Back button
        JButton backButton = backButton();
        backButton.setVisible(true);
        menuPanel.add(backButton, Integer.valueOf(100));
        layerPos.add(i);
        backButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            try {
              menuPanel.setVisible(false);
              con.add(mainBattleMenu(p1, p2, ply1, ply2));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
        if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1) {
          // Set Visibility for previous buttons off and changing menu
          fightButton.setVisible(false);
          itemButton.setVisible(false);
          pkmnButton.setVisible(false);
          runButton.setVisible(false);
          menuLabel.setIcon(new ImageIcon("assets/ui/attack_list.png"));
          menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);


          // First move button
          JButton p1Move1 = newButton(p1.getAttack(0).getName(), 200, 500, 500, 40);
          p1Move1.setHorizontalAlignment(SwingConstants.LEFT);
          menuPanel.add(p1Move1, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          // Second move button
          JButton p1Move2 = newButton(p1.getAttack(1).getName(), 200, 550, 500, 40);
          p1Move2.setHorizontalAlignment(SwingConstants.LEFT);
          menuPanel.add(p1Move2, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          // Third move button
          JButton p1Move3 = newButton(p1.getAttack(2).getName(), 200, 600, 500, 40);
          menuPanel.add(p1Move3, Integer.valueOf(layerPos.size()-1));
          p1Move3.setHorizontalAlignment(SwingConstants.LEFT);
          layerPos.add(i);
        
          // PP Display Move 1
          JLabel ppDisplay1 = newText(p1.getAttack(0).getPPBattle(), 60, 400, 200, 50);
          menuPanel.add(ppDisplay1, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppDisplay1.setVisible(false);
          // PP Display Move 2
          JLabel ppDisplay2 = newText(p1.getAttack(1).getPPBattle(), 60, 400, 200, 50);
          menuPanel.add(ppDisplay2, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppDisplay2.setVisible(false);
          // PP Display Move 3
          JLabel ppDisplay3 = newText(p1.getAttack(2).getPPBattle(), 60, 400, 350, 50);
          menuPanel.add(ppDisplay3, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppDisplay3.setVisible(false);
          // Text for PP Display 1
          JLabel ppText1 = newText(p1.getAttack(0).getName().toUpperCase(), 60, 350, 350, 50);
          menuPanel.add(ppText1, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppText1.setVisible(false);
          // Text for PP Display 2
          JLabel ppText2 = newText(p1.getAttack(1).getName().toUpperCase(), 60, 350, 350, 50);
          menuPanel.add(ppText2, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppText2.setVisible(false);
          // Text for PP Display 3
          JLabel ppText3 = newText(p1.getAttack(2).getName().toUpperCase(), 60, 350, 350, 50);
          menuPanel.add(ppText3, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          ppText3.setVisible(false);

          // First move button listener
          p1Move1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                ppText1.setVisible(true);
                ppText2.setVisible(false);
                ppText3.setVisible(false);
                ppDisplay1.setVisible(true);
                ppDisplay2.setVisible(false);
                ppDisplay3.setVisible(false);
              }
              else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                if (p1.getAttack(0).getPp() > 0) {
                  ppText1.setVisible(false);
                  ppText2.setVisible(false);
                  ppText3.setVisible(false);
                  ppDisplay1.setVisible(false);
                  ppDisplay2.setVisible(false);
                  ppDisplay3.setVisible(false);
                  p1Move1.setVisible(false);
                  p1Move2.setVisible(false);
                  p1Move3.setVisible(false);
                  backButton.setVisible(false);
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(0));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 100);
                  attkMsgLabel.setVerticalAlignment(JLabel.TOP);
                  animateText(attkMsgLabel);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1, ply2, ply1);
                        con.add(menu);
                      } catch (IOException e1) {
                        e1.printStackTrace();
                      }
                    }
                  });
                }
              }
            }
          });

        
          // Second move button listener
          p1Move2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                ppText1.setVisible(false);
                ppText2.setVisible(true);
                ppText3.setVisible(false);
                ppDisplay1.setVisible(false);;
                ppDisplay2.setVisible(true);
                ppDisplay3.setVisible(false);
              }
              else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                if (p1.getAttack(0).getPp() > 0) {
                  ppText1.setVisible(false);
                  ppText2.setVisible(false);
                  ppText3.setVisible(false);
                  ppDisplay1.setVisible(false);
                  ppDisplay2.setVisible(false);
                  ppDisplay3.setVisible(false);
                  p1Move1.setVisible(false);
                  p1Move2.setVisible(false);
                  p1Move3.setVisible(false);
                  backButton.setVisible(false);
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(1));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 100);
                  attkMsgLabel.setVerticalAlignment(JLabel.TOP);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  animateText(attkMsgLabel);
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1, ply2, ply1);
                        con.add(menu);
                      } catch (IOException e1) {
                        e1.printStackTrace();
                      }
                    }
                  });
                }
              }
            }
          });


        
          // Third move button listener
          p1Move3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                ppText1.setVisible(false);
                ppText2.setVisible(false);
                ppText3.setVisible(true);
                ppDisplay1.setVisible(false);;
                ppDisplay2.setVisible(false);
                ppDisplay3.setVisible(true);
              }
              else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                if (p1.getAttack(0).getPp() > 0) {
                  ppText1.setVisible(false);
                  ppText2.setVisible(false);
                  ppText3.setVisible(false);
                  ppDisplay1.setVisible(false);
                  ppDisplay2.setVisible(false);
                  ppDisplay3.setVisible(false);
                  p1Move1.setVisible(false);
                  p1Move2.setVisible(false);
                  p1Move3.setVisible(false);
                  backButton.setVisible(false);
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(2));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 100);
                  attkMsgLabel.setVerticalAlignment(JLabel.TOP);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  animateText(attkMsgLabel);
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1, ply2, ply1);
                        con.add(menu);
                      } catch (IOException e1) {
                        e1.printStackTrace();
                      }
                    }
                  });
                }
              }
            }
          });
        }
      } 
    });
    // Button Listener for Item Button
    itemButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        // Back button

        JButton backButton = backButton();
        backButton.setVisible(true);
        menuPanel.add(backButton, Integer.valueOf(100));
        layerPos.add(i);
        backButton.setVisible(true);
        backButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            try {
              menuPanel.setVisible(false);
              con.add(mainBattleMenu(p1, p2, ply1, ply2));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });

        // Set Visibility for previous buttons off and changing menu
        fightButton.setVisible(false);
        itemButton.setVisible(false);
        pkmnButton.setVisible(false);
        runButton.setVisible(false);
        menuLabel.setIcon(new ImageIcon("assets/ui/attack_list.png"));
        menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);

        // Heal Button
        JButton healButton = newButton("Heal", 200, 500, 500, 40);
        healButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuPanel.add(healButton, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);

        // Revive Button
        JButton reviveButton = newButton("Revive", 200, 550, 500, 40);
        reviveButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuPanel.add(reviveButton, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);
        
        // Heal Text 1
        JLabel healText = newText("HEAL", 60, 350, 350, 50);
        menuPanel.add(healText, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);
        healText.setVisible(false);

        // Revive Text 2
        JLabel reviveText = newText("REVIVE", 60, 350, 350, 50);
        menuPanel.add(reviveText, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);
        reviveText.setVisible(false);

        // Heal Count Text
        String healCountTextString = "";
        if (ply1.getName().equals("Player 1")) {
          healCountTextString = p1Items.getCountHeal();
        }
        else {
          healCountTextString = p2Items.getCountHeal();
        }
        JLabel healCountText = newText(healCountTextString, 60, 400, 350, 50);
        menuPanel.add(healCountText, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);
        healCountText.setVisible(false);

        // Revive Count Text
        String reviveCountTextString = "";
        if (ply1.getName().equals("Player 1")) {
          reviveCountTextString = p1Items.getReviveStatus();
        }
        else {
          reviveCountTextString = p2Items.getReviveStatus();
        }

        JLabel reviveCountText = newText(reviveCountTextString, 60, 400, 350, 50);
        menuPanel.add(reviveCountText, Integer.valueOf(layerPos.size()-1));
        layerPos.add(i);
        reviveCountText.setVisible(false);

        if (ply1.getName().equals("Player 1")) {
          if (p1Items.canHeal()) {
            healButton.setVisible(true);
          }
          else {
            healButton.setVisible(false);
          }
          if (p1Items.canRevive()) {
            reviveButton.setVisible(true);
          }
          else {
            reviveButton.setVisible(false);
          }

        }
        else if (ply1.getName().equals("Player 2")){
          if (p2Items.canHeal()) {
            healButton.setVisible(true);
          }
          else {
            healButton.setVisible(false);
          }
          if (p2Items.canRevive()) {
            reviveButton.setVisible(true);
          }
          else {
            reviveButton.setVisible(false);
          }
        }

        // Heal Button listener
        healButton.addMouseListener( new MouseAdapter() {
          public void mouseClicked( MouseEvent evt){
            if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1){
              healText.setVisible(true);
              healCountText.setVisible(true);
              reviveText.setVisible(false);
              reviveCountText.setVisible(false);
            }
            else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
              healText.setVisible(false);
              healCountText.setVisible(false);
              reviveText.setVisible(false);
              reviveCountText.setVisible(false);
              healButton.setVisible(false);
              reviveButton.setVisible(false);
              menuLabel.setIcon(new ImageIcon("assets/ui/pokemon_selection_battle.png"));
              menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              // All Pokemon Buttons
              JButton pokemonButton1 = newButton(ply1.getBackpack().get(0).getNameBattle(), 285, 200, 500, 40);
              pokemonButton1.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(0).getHealth() <= 0) {
                pokemonButton1.setVisible(false);
              }
              else if (ply1.getBackpack().get(0).getHealth() == ply1.getBackpack().get(0).getMaxHealth()) {
                pokemonButton1.setVisible(false);
              }
              JButton pokemonButton2 = newButton(ply1.getBackpack().get(1).getNameBattle(), 285, 200+75, 500, 40);
              pokemonButton2.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(1).getHealth() <= 0) {
                pokemonButton2.setVisible(false);
              }
              else if (ply1.getBackpack().get(1).getHealth() == ply1.getBackpack().get(1).getMaxHealth()) {
                pokemonButton2.setVisible(false);
              }
              JButton pokemonButton3 = newButton(ply1.getBackpack().get(2).getNameBattle(), 285, 200+75+75, 500, 40);
              pokemonButton3.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(2).getHealth() <= 0) {
                pokemonButton3.setVisible(false);
              }
              else if (ply1.getBackpack().get(2).getHealth() == ply1.getBackpack().get(2).getMaxHealth()) {
                pokemonButton3.setVisible(false);
              }
              menuPanel.add(pokemonButton1, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              menuPanel.add(pokemonButton2, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              menuPanel.add(pokemonButton3, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);

              // Dialog text
              JLabel msgLabel = newText("Select a Pokemon", 80, 550, 600, 100);
              msgLabel.setVerticalAlignment(JLabel.TOP);
              menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              animateText(msgLabel);
              pokemonButton1.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(0).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    backButton.setVisible(false);
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(0);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.useHeal(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.useHeal(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
              pokemonButton2.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(1).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(1);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    backButton.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.useHeal(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.useHeal(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
              pokemonButton3.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(2).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    backButton.setVisible(false);
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(2);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.useHeal(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.useHeal(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
            }
          }
        });
        // Revive Button listener
        reviveButton.addMouseListener( new MouseAdapter() {
          public void mouseClicked( MouseEvent evt) {
            if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1){
              healText.setVisible(false);
              healCountText.setVisible(false);
              reviveText.setVisible(true);
              reviveCountText.setVisible(true);
            }
            else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
              healText.setVisible(false);
              healCountText.setVisible(false);
              reviveText.setVisible(false);
              reviveCountText.setVisible(false);
              healButton.setVisible(false);
              reviveButton.setVisible(false);
              menuLabel.setIcon(new ImageIcon("assets/ui/pokemon_selection_battle.png"));
              menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              // All Pokemon Buttons
              JButton pokemonButton1 = newButton(ply1.getBackpack().get(0).getNameBattle(), 285, 200, 500, 40);
              pokemonButton1.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(0).getHealth() > 0) {
                pokemonButton1.setVisible(false);
              }
              JButton pokemonButton2 = newButton(ply1.getBackpack().get(1).getNameBattle(), 285, 200+75, 500, 40);
              pokemonButton2.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(1).getHealth() > 0) {
                pokemonButton2.setVisible(false);
              }
              JButton pokemonButton3 = newButton(ply1.getBackpack().get(2).getNameBattle(), 285, 200+75+75, 500, 40);
              pokemonButton3.setHorizontalAlignment(SwingConstants.LEFT);
              if (ply1.getBackpack().get(2).getHealth() > 0) {
                pokemonButton3.setVisible(false);
              }
              menuPanel.add(pokemonButton1, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              menuPanel.add(pokemonButton2, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              menuPanel.add(pokemonButton3, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              // Dialog text
              JLabel msgLabel = newText("Select a Pokemon", 80, 550, 600, 100);
              msgLabel.setVerticalAlignment(JLabel.TOP);
              menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
              layerPos.add(i);
              animateText(msgLabel);
              pokemonButton1.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(0).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(0);
                    backButton.setVisible(false);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.revive(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.revive(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
              pokemonButton2.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(1).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    backButton.setVisible(false);
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(1);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.revive(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.revive(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
              pokemonButton3.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    msgLabel.setText(ply1.getBackpack().get(2).getName() + " is selected");
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                  }
                  if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Pokemon pokemonUsingHeal = ply1.getBackpack().get(2);
                    backButton.setVisible(false);
                    pokemonButton1.setVisible(false);
                    pokemonButton2.setVisible(false);
                    pokemonButton3.setVisible(false);
                    layerPos.add(i);
                    menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                    menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    String output = "";
                    if (ply1.getName().equals("Player 1")) {
                      output = p1Items.revive(pokemonUsingHeal);
                    }
                    else {
                      output = p2Items.revive(pokemonUsingHeal);
                    }
                    msgLabel.setText(output);
                    menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    animateText(msgLabel);
                    p1PokemonHealth.setText(p1.getHealthBattle());
                    menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                    layerPos.add(i);
                    menuPanel.addMouseListener(new MouseAdapter() {
                      public void mouseClicked(MouseEvent e2) {
                        menuPanel.setVisible(false); 
                        try {
                          con.add(mainBattleMenu(p2, p1, ply2, ply1));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                  }
                }
              });
            }
          }
        });
        
      } 
    });
    // Button Listener for Pokemon Button (Completed with bugs)
    pkmnButton.addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        // Back button
        JButton backButton = backButton();
        backButton.setVisible(true);
        menuPanel.add(backButton, Integer.valueOf(100));
        layerPos.add(i);
        backButton.setVisible(true);
        backButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            try {
              menuPanel.setVisible(false);
              con.add(mainBattleMenu(p1, p2, ply1, ply2));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });

        if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1) {
          // Hidding all other buttons and changing menu
          fightButton.setVisible(false);
          itemButton.setVisible(false);
          pkmnButton.setVisible(false);
          runButton.setVisible(false);
          menuLabel.setIcon(new ImageIcon("assets/ui/pokemon_selection_battle.png"));
          menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);

          // All Pokemon Buttons
          JButton pokemonButton1 = newButton(ply1.getBackpack().get(0).getNameBattle(), 285, 200, 500, 40);
          pokemonButton1.setHorizontalAlignment(SwingConstants.LEFT);
          JButton pokemonButton2 = newButton(ply1.getBackpack().get(1).getNameBattle(), 285, 200+75, 500, 40);
          pokemonButton2.setHorizontalAlignment(SwingConstants.LEFT);
          JButton pokemonButton3 = newButton(ply1.getBackpack().get(2).getNameBattle(), 285, 200+75+75, 500, 40);
          pokemonButton3.setHorizontalAlignment(SwingConstants.LEFT);
          menuPanel.add(pokemonButton1, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          menuPanel.add(pokemonButton2, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          menuPanel.add(pokemonButton3, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          // Dialog text
          JLabel msgLabel = newText("Select a Pokemon", 80, 550, 600, 100);
          msgLabel.setVerticalAlignment(JLabel.TOP);
          menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          animateText(msgLabel);
          if (ply1.getBackpack().get(0).getHealth() <= 0) {
            pokemonButton1.setVisible(false);
          }
          if (ply1.getBackpack().get(1).getHealth() <= 0) {
            pokemonButton2.setVisible(false);
          }
          if (ply1.getBackpack().get(2).getHealth() <= 0) {
            pokemonButton3.setVisible(false);
          }
          if (ply1.getBackpack().get(0).getName().equals(p1.getName())) {
            pokemonButton1.setVisible(false);
          }
          if (ply1.getBackpack().get(1).getName().equals(p1.getName())) {
            pokemonButton2.setVisible(false);
          }
          if (ply1.getBackpack().get(2).getName().equals(p1.getName())) {
            pokemonButton3.setVisible(false);
          }

          pokemonButton1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(0).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                backButton.setVisible(false);
                String newPokemonString = "assets/player/" + pokedex.getPokemonIndex(ply1.getBackpack().get(0)) + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(0).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(0).getName());
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(0).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                animateText(msgLabel);
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(0), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
          pokemonButton2.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(1).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                backButton.setVisible(false);
                String newPokemonString = "assets/player/" + pokedex.getPokemonIndex(ply1.getBackpack().get(1)) + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(1).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(1).getName());
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(1).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                animateText(msgLabel);
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(1), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
          pokemonButton3.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                msgLabel.setText(ply1.getBackpack().get(2).getName() + " is selected");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
              }
              if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                backButton.setVisible(false);
                String newPokemonString = "assets/player/" + pokedex.getPokemonIndex(ply1.getBackpack().get(2)) + ".png";
                p1PokemonLabel.setIcon(new ImageIcon(newPokemonString));
                p1PokemonHealth.setText(ply1.getBackpack().get(2).getHealthBattle());
                p1PokemonName.setText(ply1.getBackpack().get(2).getName());
                menuPanel.setLayer(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                menuPanel.setLayer(p1PokemonName, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                pokemonButton1.setVisible(false);
                pokemonButton2.setVisible(false);
                pokemonButton3.setVisible(false);
                layerPos.add(i);
                menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
                layerPos.add(i);
                msgLabel.setText("Go, " + ply1.getBackpack().get(2).getName() + "!");
                menuPanel.setLayer(msgLabel, Integer.valueOf(layerPos.size()-1));
                animateText(msgLabel);
                menuPanel.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent e2) {
                    menuPanel.setVisible(false); 
                    try {
                      con.add(mainBattleMenu(p2, ply1.getBackpack().get(2), ply2, ply1));
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
              }
            }
          });
        }
      } 
    });
    // Button Listener for Run Button (Completed)
    runButton.addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1) {
          fightButton.setVisible(false);
          itemButton.setVisible(false);
          pkmnButton.setVisible(false);
          runButton.setVisible(false);
          menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
          menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          JLabel msgLabel = newText("You ran away!", 80, 550, 600, 100);
          msgLabel.setVerticalAlignment(JLabel.TOP);
          menuPanel.add(msgLabel, Integer.valueOf(layerPos.size()-1));
          layerPos.add(i);
          animateText(msgLabel);
          menuPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
              frame.dispose();
            }
          });
        }
      } 
    });
    return menuPanel;
  }
  public JLabel newText(String text, int x, int y, int width, int height) {
    JLabel label = new JLabel(text);
    label.setFont(font);
    label.setBounds(x,y,width, height);
    label.setForeground(Color.black);
    return label;
  }

  public JButton newButton(String text, int x, int y, int width, int height) {
    JButton button = new JButton(text);
    button.setFont(font);
    button.setBounds(x,y,width, height);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setVisible(true);
    button.setForeground(Color.black);
    return button;
  }

  public JButton backButton() {
    JButton backButton = new JButton();
    backButton.setBounds(720, 600, 35, 52);
    backButton.setIcon(new ImageIcon("assets/ui/back.png"));
    backButton.setOpaque(false);
    backButton.setContentAreaFilled(false);
    backButton.setBorderPainted(false);
    backButton.setForeground(Color.black);
    return backButton;
  }

  public JLayeredPane winner(String str) {
    JLayeredPane menuPanel = new JLayeredPane();
    menuPanel.setLayout(null);
    menuPanel.setBounds(0, 0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.setBackground(Color.white);
    JLabel menuLabel = new JLabel(new ImageIcon("assets/ui/" + str + ".png"));
    menuLabel.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.add(menuLabel, Integer.valueOf(0));
    return menuPanel;
  }

  public void animateText(JLabel lblMsg) {
    String txt = lblMsg.getText();
    int LblTextlength = txt.length();
    lblMsg.setText("");
    Timer tm = new Timer(30, new ActionListener() {
      int counter = 0;

      @Override
      public void actionPerformed(ActionEvent arg0) {
        counter++;
        if (counter > LblTextlength) {
          ((Timer) arg0.getSource()).stop();
        } 
        else {
          lblMsg.setText(txt.substring(0, counter));
          if (lblMsg.getForeground() == Color.blue) {
            lblMsg.setForeground(Color.red);
          }
          else if (lblMsg.getForeground() == Color.red) {
            lblMsg.setForeground(Color.green);
          }
          else if (lblMsg.getForeground() == Color.green) {
            lblMsg.setForeground(Color.blue);
          }
        }
      }
    });
     
     tm.start();
  }
}

