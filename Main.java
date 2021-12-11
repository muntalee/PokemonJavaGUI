import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;

public class Main {
  CardLayout cardLayout = new CardLayout();
  JPanel mainPanel = new JPanel(cardLayout);
  JFrame frame = new JFrame();
  Player p1 = new Player("Player 1");
  Player p2 = new Player("Player 2");
  Container con = frame.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  Font font = new Font("PKMN RBYGSC", Font.PLAIN, 28);
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
    if (ex == true) {
      try {
        startGame();
      } catch(IOException e) {
        e.printStackTrace(); 
      }
    }
  }

  public void startGame() throws IOException {
    mainPanel.setBounds(0,0,WIDTH_PANEL, HEIGHT_PANEL);
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
    JPanel selection = pokedex.newSelectionPanel(p1, this);


    titleLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        title.setVisible(false);
        con.add(selection);
      }
    });

    con.revalidate();
    frame.setVisible(true); 
  }

  public void addNewPanel(JPanel p) {
    con.add(p);
    con.revalidate();
  }

}
  