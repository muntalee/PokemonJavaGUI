import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MainMenu extends JFrame {
  Container con = this.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  Font font = new Font("PKMN RBYGSC", Font.PLAIN, 28);

  private MainMenu() throws IOException {
    setTitle("Bootleg Pokemon Java Edition");
    setSize(818, 740);
    setResizable(false);
    getContentPane().setLayout(null);
    getContentPane().setBackground(new Color(255,255,255));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JPanel title = new JPanel();
    title.setBounds(0, 0, WIDTH_PANEL, HEIGHT_PANEL);
    title.setBackground(Color.white);
    /* TITLE TEXT */
    // Creating Tile Screen Label
    String path = "assets/ui/main_menu.png";
    File file = new File(path);
    BufferedImage titleImage = ImageIO.read(file);
    JLabel titleLabel = new JLabel(new ImageIcon(titleImage));
    title.add(titleLabel);
    // Add title to container
    con.add(title);
    con.validate();
    setVisible(true);
    titleLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        title.setVisible(false);
        try {
          con.invalidate();
          con.validate();
          PokemonSelectionFrame();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
  }
 
  public void PokemonSelectionFrame() throws IOException {
    JPanel psfWallpaper = new JPanel();
    psfWallpaper.setBackground(Color.white);
    String path = "assets/ui/pokemon_selection.png";
    File file = new File(path);
    BufferedImage pokemonSelection = ImageIO.read(file);
    JLabel psfImage = new JLabel(new ImageIcon(pokemonSelection));
    psfWallpaper.setBounds(-5,0,WIDTH_PANEL,HEIGHT_PANEL);
    psfWallpaper.add(psfImage);
    // setContentPane(new ImagePanel(pokemonSelection));
   // con.add(psfWallpaper);
    Pokedex pokedex = new Pokedex();
    ArrayList<JPanel> pokemonJPanels = pokedex.getPokemonLabels();
    for (int i = 0; i < pokemonJPanels.size(); i++) {
      con.add(pokemonJPanels.get(i));
    }
    con.invalidate();
    con.validate();
    con.setVisible(true);

    Player p1 = new Player("Player 1");
    Player p2 = new Player("Player 2");

    boolean selectPokemon = true;
    int count = 0;

    while (selectPokemon) {
      for (int i = 0; i < pokemonJPanels.size(); i++) {
        selectPokemon = addMouseListenerForPokemons(pokemonJPanels.get(i), p1, i);
        if (!selectPokemon) {
          break;
        }
      }
      break;
    }
    System.out.println(p1.getBackpack().size());
  } 

  public boolean addMouseListenerForPokemons(JPanel p, Player player, int i) {
    boolean[] yes = new boolean[1];
    p.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Pokedex pokedex = new Pokedex();
        p.setVisible(false);
        if (player.getBackpack().size() < 3) {
          player.addPokemon(pokedex.getPokemonByIndex(i));
          System.out.println(player.getBackpack().get(player.getBackpack().size()-1));
          yes[0] = true;
        }
        else {
          yes[0] = false;
        }
      }
    }); 
    return yes[0];
  }


  // Main Method to start code
  public static void main(String[] args) {
    try {
      new MainMenu();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}