import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;

public class Main {
  JFrame frame = new JFrame();
  Player p1 = new Player("Player 1");
  Player p2 = new Player("Player 2");
  Container con = frame.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  final int POKE_OPP_DIM = 240;
  final int POKE_PLA_DIM = 256;
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

  public void startBattle() throws IOException {
    System.out.println(p1);
    System.out.println(p2);
    JLayeredPane mainMenuBattle = mainBattleMenu();
    con.add(mainMenuBattle);
  }

  public JLayeredPane mainBattleMenu() throws IOException {
    // Pokemons
    Pokemon p1Pokemon = p1.getBackpack().get(0);
    Pokemon p2Pokemon = p2.getBackpack().get(0);
    int layerPos = 0;

    // Background setup
    JLayeredPane menuPanel = new JLayeredPane();
    menuPanel.setLayout(null);
    menuPanel.setBounds(0, 0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.setBackground(Color.white);

    // Brackets
    JLabel bracketLabel = new JLabel(new ImageIcon("assets/ui/name_bracket.png"));
    bracketLabel.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.add(bracketLabel, Integer.valueOf(Integer.valueOf(layerPos)));
    layerPos++;

    // Menu
    JLabel menuLabel = new JLabel(new ImageIcon("assets/ui/selection_menu.png"));
    menuLabel.setBounds(0,0, WIDTH_PANEL, HEIGHT_PANEL);
    menuPanel.add(menuLabel, Integer.valueOf(layerPos));
    layerPos++;

    // Player 1 Pokemon
    String p1PokemonFile = "assets/player/" + p1Pokemon.getName().toLowerCase() + ".png";
    JLabel p1PokemonLabel = new JLabel(new ImageIcon(p1PokemonFile));
    p1PokemonLabel.setBounds(27,235, POKE_PLA_DIM, POKE_PLA_DIM);
    menuPanel.add(p1PokemonLabel, Integer.valueOf(layerPos));
    layerPos++;

    // Player 2 Pokemon
    String p2PokemonFile = "assets/opponent/" + p2Pokemon.getName().toLowerCase() + ".png";
    JLabel p2PokemonLabel = new JLabel(new ImageIcon(p2PokemonFile));
    p2PokemonLabel.setBounds(523,2, POKE_OPP_DIM, POKE_OPP_DIM);
    menuPanel.add(p2PokemonLabel, Integer.valueOf(layerPos));
    layerPos++;

    // Player 1 Pokemon Name and Health
    JLabel p1PokemonName = newText(p1Pokemon.getName(), 410, 355, 350, 50);
    JLabel p1PokemonHealth = newText(p1Pokemon.getHealthBattle(), 410, 400, 350, 50);
    menuPanel.add(p1PokemonName, Integer.valueOf(layerPos));
    layerPos++;
    menuPanel.add(p1PokemonHealth, Integer.valueOf(layerPos));
    layerPos++;

    // Player 2 Pokemon Name and Health
    JLabel p2PokemonName = newText(p2Pokemon.getName(), 75, 45, 350, 50);
    JLabel p2PokemonHealth = newText(p2Pokemon.getHealthBattle(), 75, 90, 350, 50);
    menuPanel.add(p2PokemonName, Integer.valueOf(layerPos));
    layerPos++;
    menuPanel.add(p2PokemonHealth, Integer.valueOf(layerPos));
    layerPos++;

    // Buttons
    JButton fightButton = newButton("FIGHT", 360, 510, 180, 40);
    menuPanel.add(fightButton, Integer.valueOf(layerPos));
    layerPos++;
    JButton itemButton = newButton("ITEM", 360, 590, 180, 40);
    menuPanel.add(itemButton, Integer.valueOf(layerPos));
    layerPos++;
    JButton pkmnButton = newButton("PKMN", 580, 510, 180, 40);
    menuPanel.add(pkmnButton, Integer.valueOf(layerPos));
    layerPos++;
    JButton runButton = newButton("RUN", 580, 590, 180, 40);
    menuPanel.add(runButton, Integer.valueOf(layerPos));
    layerPos++;

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
  