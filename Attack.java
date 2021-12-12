public class Attack {
  private String name;
  private int power;
  private int accuracy;
  private int pp;
  private int remainingPP;
  public Attack(String name, int pp, int power, int accuracy) {
    this.name = name;
    this.power = power;
    this.accuracy = accuracy;
    this.pp = pp;
    remainingPP = pp;
    this.power = power;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public int getPp() {
    return remainingPP;
  }

  public String getPPBattle() {
    return remainingPP + "/" + pp;
  }

  public int getMaxPp() {
    return pp;
  }

  public void setPp(int remainingPP) {
    this.remainingPP = remainingPP;
  }

  public int getPower() {
    return power;
  }

  public String getName() {
    return name;
  }

  public void changeAcc(int n) {
    accuracy += n;
  }

  public void decreasePP() {
    remainingPP--;
  }

  public String toString() {
    return name + "\t(" + remainingPP + " / " + pp + ")\n";
  }
}
