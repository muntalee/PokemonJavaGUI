import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;

public class StartScreen extends JFrame {
  private Container con = getContentPane();
  StartScreen() throws IOException {
    setTitle("Bootleg Pokemon Java Edition");
    setSize(800, 700);
    setLayout(null);
    getContentPane().setBackground(Color.white);
    JPanel title = new JPanel();
    title.setBounds(0, 0, 800, 700);
    title.setBackground(Color.white);

    /* TITLE TEXT */
    // Creating Tile Screen Label
    String path = "assets/ui/main_menu.png";
    File file = new File(path);
    BufferedImage titleImage = ImageIO.read(file);
    JLabel titleLabel = new JLabel(new ImageIcon(titleImage));
    titleLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Pokedex p = new Pokedex();
        System.out.println(p.getPokemon("squirtle"));
      }
    });
    title.add(titleLabel);
    // Add title to container
    con.validate();
    con.add(title);
    setVisible(true);
  }
  public static void main(String[] args) throws IOException {
    new StartScreen();
  }
}
