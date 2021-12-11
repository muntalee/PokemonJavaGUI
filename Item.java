import java.util.*;
public class Item {
  private int healValue;
  public Item() { 
    healValue = 5;
  }

  public void useHeal(Pokemon p) {
    Random rand = new Random();
    // 10-15 = (5) + 11
    this.healValue = rand.nextInt(5) + 11;
    if (p.getHealth() + healValue > p.getMaxHealth()) {
      System.out.println(p.getName() + " healed to max health");
      p.setHealth(p.getMaxHealth());
      return;
    }
    System.out.println(p.getName() + " healed up by " + healValue);
    p.addHealth(healValue);
  }

  public void useItem(Pokemon p, String choice) {
    if (choice.toLowerCase().equals("heal")) {
      useHeal(p);
    }
    else if (choice.toLowerCase().equals("revive")) {
      revive(p);
    }
    else {
      System.out.println("Invalid item, sucks man.");
    }
  }

  public void revive(Pokemon p) {
    if (p.getHealth() == 0) {
      if (p.getRevives() < 1) {
        System.out.println(p.getName() + " has been restored!");
        p.reviving();
      }
      else {
        System.out.println("You cannot revive! (Revived too many)");
      }
    }
    else {
      System.out.println("Revive only works if Pokemon is at health 0");
    }
  }

  public boolean checkValidItem(String item) {
    if (item.toLowerCase() == "heal" || item.toLowerCase() == "revive") {
      return false;
    }
    return true;
  }

  public void printItems() {
    System.out.println("Heal (10 - 15 hp)");
    System.out.println("Revive (1 per Pokemon)");
  }

}
