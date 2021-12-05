import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;

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
        setVisible(false);
        dispose();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        new PokemonSelectionMenu(true, p1, p2);
      }
    });
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