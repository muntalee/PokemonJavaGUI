import java.util.ArrayList;
import java.util.Random;

public class Pokemon {
	private String name;
	private int maxHealth;
	private int health;
	private String type;
	private int damage;
	private int defense;
	private ArrayList<Attack> attack;
  private int numRevives; 

	public Pokemon(String name, String type, int maxHealth, int damage, int defense) {
		this.name = name;
		this.type = type;
		this.maxHealth = maxHealth;
		health = maxHealth;
		this.damage = damage;
		this.defense = defense;
    this.numRevives = 0;
		attack =  new ArrayList<Attack>();
	}

	public String getName() {
		return name;
	}

  public String getHealthBattle() {
    return health + "/" + maxHealth;
  }

  public void reviving() {
    this.health = maxHealth;
    for (int i = 0; i < attack.size(); i++) {
      attack.get(i).setPp(attack.get(i).getMaxPp());
    }
    numRevives++;
  }

  public int getRevives() {
    return numRevives;
  }

  public int getHealth() {
    return health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void addHealth(int num) {
    this.health += num;
  }

  public void setHealth(int health) {
    this.health = health;
  }

	public Attack getAttack(String name) {
    for (int i = 0; i < attack.size(); i++) {
      if (attack.get(i).getName().toLowerCase().equals(name)) {
				return attack.get(i);
      }
    }
    return null;
	}

	public void addMoveSet(ArrayList<Attack> attack) {
		this.attack = attack;
	}

  public int getDefense() {
    return defense;
  }

  public void printAttacks() {
    for (int i = 0; i < attack.size(); i++) {
      System.out.print(attack.get(i).toString());
    }
  }

  public void attack (Pokemon p, Attack attack) {
    Random rand = new Random();
    int chance = rand.nextInt(101);
    // if chance = 20 and accuracy is 90, then 100 - 90 = 10, 20 >= 10, so do attack
    // if chance = 5 and accuracy is 90, then 100 - 90 = 10, and 5 !>= 10, so don't do attack
    if (chance >= 100 - attack.getAccuracy()) {
      if (attack.getPp() <= 0) {
        System.out.println("You don't have any pp!");
        return;
      }
      double dmgAgainstPokemon = attack.getPower() / ((this.damage/p.getDefense()) + 5);
      double newHealth = Math.ceil((double)p.getHealth() - dmgAgainstPokemon);
      System.out.println(name + " dealt " + dmgAgainstPokemon);
      p.setHealth((int)newHealth);
      if (p.getHealth() <= 0) {
        p.setHealth(0);
      }
      attack.decreasePP();
    }
    else {
      System.out.println(name + "'s attack missed!");
    }
  }

  public String displayHealth() {
    return name + " (" + type + ")\t" + health + "/" + maxHealth + "hp";
  }

	public String toString() {
		return name + "\t(" + type + ")\t" + health + "/" + maxHealth + "hp\tDMG: " + damage + "\tDEF: " + defense; 
	}
}
