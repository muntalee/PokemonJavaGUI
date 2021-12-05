import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class PokemonSelectionMenu extends JFrame {
  Container con = this.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  Font font = new Font("PKMN RBYGSC", Font.PLAIN, 28);
  private Player p1 = new Player("Player 1");
  private Player p2 = new Player("Player 2");
  private boolean isPlayer1Turn;

  public PokemonSelectionMenu(boolean isPlayer1Turn, Player p1, Player p2) {
    this.isPlayer1Turn = isPlayer1Turn;
    this.p1 = p1;
    this.p2 = p2;
    setTitle("Bootleg Pokemon Java Edition");
    setSize(818, 740);
    setResizable(false);
    getContentPane().setLayout(null);
    getContentPane().setBackground(new Color(255,255,255));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    con.validate();
    setVisible(true);
    try {
      if (isPlayer1Turn) {
        PokemonSelectionFrame(p1);
      }
      else {
        PokemonSelectionFrame(p2);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void PokemonSelectionFrame(Player p) throws IOException {
    JPanel psfWallpaper = new JPanel();
    psfWallpaper.setBackground(Color.white);
    String path = "assets/ui/pokemon_selection.png";
    File file = new File(path);
    BufferedImage pokemonSelection = ImageIO.read(file);
    JLabel psfImage = new JLabel(new ImageIcon(pokemonSelection));
    psfWallpaper.setBounds(-5,0,WIDTH_PANEL,HEIGHT_PANEL);
    psfWallpaper.add(psfImage);
    // setContentPane(new ImagePanel(pokemonSelection));
    con.add(psfWallpaper);
    Pokedex pokedex = new Pokedex();
    ArrayList<JPanel> pokemonJPanels = pokedex.getPokemonLabels();
    
    con.invalidate();
    con.validate();
    con.setVisible(true);

    for (int i = 0; i < pokemonJPanels.size(); i++) {
      con.add(pokemonJPanels.get(i));
    }

    for (int i = 0; i < pokemonJPanels.size(); i++) {
      addMouseListenerForPokemons(pokemonJPanels.get(i), p, i);
    }
  } 

  public void addMouseListenerForPokemons(JPanel panel, Player player, int i) {
    panel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Pokedex pokedex = new Pokedex();
        if (player.getBackpack().size() > 2) {
          if (isPlayer1Turn) {
            setVisible(false);
            dispose();
            new PokemonSelectionMenu(false, p1, p2);
          }
          else {
            setVisible(false);
            dispose();
            try {
              new BattleMenu(p1, p2);
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }

        else {
          panel.setVisible(false);
          player.addPokemon(pokedex.getPokemonByIndex(i));
        }
      }
    }); 
  }
}