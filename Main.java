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
    System.out.println(this.p1);
    System.out.println(this.p2);
    startNewBattle = mainBattleMenu(p1.getBackpack().get(0),p2.getBackpack().get(0));
    con.add(startNewBattle);
  }

  public JLayeredPane mainBattleMenu(Pokemon p1,Pokemon p2) throws IOException {
    // Pokemons
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
    String p1PokemonFile = "assets/player/" + p1Pokemon.getName().toLowerCase() + ".png";
    JLabel p1PokemonLabel = new JLabel(new ImageIcon(p1PokemonFile));
    p1PokemonLabel.setBounds(40,150, POKE_PLA_DIM, POKE_PLA_DIM);
    menuPanel.add(p1PokemonLabel, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 2 Pokemon
    String p2PokemonFile = "assets/opponent/" + p2Pokemon.getName().toLowerCase() + ".png";
    JLabel p2PokemonLabel = new JLabel(new ImageIcon(p2PokemonFile));
    p2PokemonLabel.setBounds(523,10, POKE_OPP_DIM, POKE_OPP_DIM);
    menuPanel.add(p2PokemonLabel, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 1 Pokemon Name and Health
    JLabel p1PokemonName = newText(p1Pokemon.getName(), 410, 355, 350, 50);
    JLabel p1PokemonHealth = newText(p1Pokemon.getHealthBattle(), 410, 400, 350, 50);
    menuPanel.add(p1PokemonName, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
    menuPanel.add(p1PokemonHealth, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);

    // Player 2 Pokemon Name and Health
    JLabel p2PokemonName = newText(p2Pokemon.getName(), 75, 45, 350, 50);
    JLabel p2PokemonHealth = newText(p2Pokemon.getHealthBattle(), 75, 90, 350, 50);
    menuPanel.add(p2PokemonName, Integer.valueOf(layerPos.size()-1));
    layerPos.add(i);
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
    // Button Listener for Fight Button
    fightButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
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
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(0));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 40);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1);
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
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(1));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 40);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1);
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
                  menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
                  String attackMessage = p1.attack(p2, p1.getAttack(2));
                  JLabel attkMsgLabel = newText(attackMessage, 80, 550, 600, 40);
                  menuPanel.add(attkMsgLabel, Integer.valueOf(layerPos.size()-1));
                  p2PokemonHealth.setText(p2.getHealthBattle());
                  menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                      try {
                        menuPanel.setVisible(false);
                        JLayeredPane menu = mainBattleMenu(p2, p1);
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
    itemButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        fightButton.setVisible(false);
        itemButton.setVisible(false);
        pkmnButton.setVisible(false);
        runButton.setVisible(false);
        menuLabel.setIcon(new ImageIcon("assets/ui/attack_list.png"));
        menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
      } 
    });
    pkmnButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        fightButton.setVisible(false);
        itemButton.setVisible(false);
        pkmnButton.setVisible(false);
        runButton.setVisible(false);
        menuLabel.setIcon(new ImageIcon("assets/ui/pokemon_selection_battle.png"));
        menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));
      } 
    });
    runButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        fightButton.setVisible(false);
        itemButton.setVisible(false);
        pkmnButton.setVisible(false);
        runButton.setVisible(false);
        menuLabel.setIcon(new ImageIcon("assets/ui/regular_menu.png"));
        menuPanel.setLayer(menuLabel, Integer.valueOf(layerPos.size()-1));

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
}
  