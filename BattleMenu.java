import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class BattleMenu extends JFrame {
  Container con = this.getContentPane();
  final int WIDTH_PANEL = 800;
  final int HEIGHT_PANEL = 700;
  Font font = new Font("PKMN RBYGSC", Font.PLAIN, 28);
  private Player p1;
  private Player p2;
  public BattleMenu(Player p1, Player p2) throws IOException {
    this.p1 = p1;
    this.p2 = p2;
    setTitle("Bootleg Pokemon Java Edition");
    p1.printBackpack();
    System.out.println();
    p2.printBackpack();
    setSize(818, 740);
    setResizable(false);
    getContentPane().setLayout(null);
    getContentPane().setBackground(new Color(255,255,255));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    con.validate();
    setVisible(true);
    startBattle(p1.getBackpack().get(0),p2.getBackpack().get(1));
  }

  public void startBattle(Pokemon p1, Pokemon p2) throws IOException {
    // Background
    JPanel bgPanel = new JPanel();
    bgPanel.setBackground(Color.white);
    String bgPath = "assets/ui/regular_menu.png";
    File bgFile = new File(bgPath);
    BufferedImage bgImage = ImageIO.read(bgFile);
    JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
    bgPanel.setBounds(-5,0,WIDTH_PANEL,HEIGHT_PANEL);
    bgPanel.add(bgLabel);
    con.add(bgPanel);
    // Player 1 Pokemon
    String p1Path = "assets/player/" + p1.getName().toLowerCase() + ".png";
    File p1File = new File(p1Path);
    BufferedImage p1Image = ImageIO.read(p1File);
    JLabel p1Label = new JLabel(new ImageIcon(p1Image));
    p1Label.setHorizontalAlignment(JLabel.CENTER);
    p1Label.setForeground(Color.black);
    p1Label.setFont(font);
    p1Label.setVisible(true);
    JPanel p1Panel = new JPanel();
    p1Panel.setLayout(new GridBagLayout());
    p1Panel.setBounds(82,150, 256, 256);
    p1Panel.setBackground(Color.white);
    p1Panel.add(p1Label);
    con.add(p1Panel);
    // Player 2 Pokemon
    String p2Path = "assets/opponent/" + p2.getName().toLowerCase() + ".png";
    File p2File = new File(p2Path);
    BufferedImage p2Image = ImageIO.read(p2File);
    JLabel p2Label = new JLabel(new ImageIcon(p2Image));
    p2Label.setHorizontalAlignment(JLabel.CENTER);
    p2Label.setForeground(Color.black);
    p2Label.setFont(font);
    p2Label.setVisible(true);
    JPanel p2Panel = new JPanel();
    p2Panel.setLayout(new GridBagLayout());
    p2Panel.setBounds(412,150, 256, 256);
    p2Panel.setBackground(Color.white);
    p2Panel.add(p2Label);
    con.add(p2Panel);
    con.validate();
  }
}
