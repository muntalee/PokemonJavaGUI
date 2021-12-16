import java.util.*;
public class Item {
  private int healValue;
  private int healCount;
  private int healMax;
  private boolean canRevive;
  private Player p; 

  public Item(Player p) {
    this.p = p;
    healValue = 5;
    healCount = 0;
    healMax = 3;
    canRevive = true;
  }

  public Player getPlayer() {
    return p; 
  }

  public String getCountHeal() {
    return (3 - healCount) + "/" + healMax;
  }

  public String getReviveStatus() {
    if (canRevive) {
      return "1 revive available";
    }
    return "Cannot revive!";
  }

  public int getHealCountVariable() {
    return healCount;
  }
  
  public boolean canRevive() {
    return canRevive;
  }

  public boolean canHeal() {
    if (healCount > 3) {
      return false;
    }
    return true;
  }

  public String useHeal(Pokemon p) {
    Random rand = new Random();
    // 10-15 = (5) + 11
    this.healValue = rand.nextInt(5) + 11;
    if (healCount > 3) {
      return "You cannot heal! (out of turns)";
    }
    else if (p.getHealth() == p.getMaxHealth()) {
      return "Your health is at max dummy";
    }
    else {
      if (p.getHealth() + healValue > p.getMaxHealth()) {
        p.setHealth(p.getMaxHealth());
        healCount++;
        return p.getName() + " healed to max!";
      }
      p.addHealth(healValue);
      healCount++;
      return p.getName() + " healed up by " + healValue;
    }
  }

  public String revive(Pokemon p) {
    if (canRevive) {
      if (p.getHealth() == 0) {
        p.reviving();
        canRevive = false;
        return p.getName() + " has been restored!";
      }
      else {
        return "Cannot Revive! Not at 0hp";
      }
    }
    return "You cannot heal anymore!";
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
