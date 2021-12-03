import java.util.*;

public class Battle {

  private Player player1;
  private Player player2;
  private boolean isPlayer1Turn;

  public Battle(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
    isPlayer1Turn = true;
  }

  private void startNewBattle(Pokemon pL, Pokemon pW) {
    Scanner in = new Scanner(System.in);
    System.out.println();
    System.out.println("Select a new Pokemon: ");
    System.out.println();
    if (isPlayer1Turn) {
      player1.printBackpackBattle();
    }
    else {
      player2.printBackpackBattle();
    }
    System.out.print("> ");
    String whichPokemon = in.next();
    Pokemon newPokemon;
    if (isPlayer1Turn) {
      newPokemon = player1.throwOut(whichPokemon);
    }
    else {
      newPokemon = player2.throwOut(whichPokemon);
    }
    while (newPokemon == null) {
      System.out.println("Invalid! Enter new Pokemon!");
      System.out.print("> ");
      whichPokemon = in.next();
      if (isPlayer1Turn) {
        newPokemon = player1.throwOut(whichPokemon);
      }
      else {
        newPokemon = player2.throwOut(whichPokemon);
      }
    }
    startBattle(pW, newPokemon);
  }

  public void startBattle(Pokemon p1, Pokemon p2) {
    Scanner in = new Scanner(System.in);
    String option;
    System.out.println("Here are the Pokemons: ");
    System.out.println();
    System.out.println(p1.getName() + " and " + p2.getName());
    System.out.println();
    System.out.println(p1.toString());
    System.out.println(p2.toString());
    System.out.println("BATTLE STARTS NOW");
    System.out.println("Enter the option on prompt");
    System.out.println();
    while (true) {
      System.out.println(p1.displayHealth());
      System.out.println(p2.displayHealth());
      if (p1.getHealth() <= 0) {
        System.out.println(p1.getName() + "'s health is 0!");

        System.out.println(p2.getName() + " is the winner!");
        startNewBattle(p1, p2);
        break;
      } else if (p2.getHealth() <= 0) {
        System.out.println(p2.getName() + "'s health is 0!");
        System.out.println(p1.getName() + " is the winner!");
        startNewBattle(p2, p1);
        break;
      }
      if (isPlayer1Turn) {
        System.out.println(p1.getName() + "'s Turn");
      } else {
        System.out.println(p2.getName() + "'s Turn");
      }
      System.out.println("[ FIGHT ] | [ POKEMON] | [ ITEM ] | [ RUN ]");
      System.out.print("> ");
      option = in.next();
      System.out.println();
      if (option.toLowerCase().equals("run")) {
        System.out.println("You ran away!");
        return;
      } else if (option.toLowerCase().equals("fight")) {
        if (isPlayer1Turn) {
          p1.printAttacks();
        } else {
          p2.printAttacks();
        }
        System.out.print("> ");
        String attack = in.next();
        if (isPlayer1Turn) {
          Attack newAttack = p1.getAttack(attack);
          while (newAttack == null) {
            System.out.println("Invalid attack, enter again!");
            System.out.print("> ");
            attack = in.next();
            newAttack = p1.getAttack(attack);
          }
          p1.attack(p2, newAttack);
          isPlayer1Turn = false;
        } else {
          Attack newAttack = p2.getAttack(attack);
          while (newAttack == null) {
            System.out.println("Invalid attack, enter again!");
            System.out.print("> ");
            attack = in.next();
            newAttack = p2.getAttack(attack);
          }
          p2.attack(p1, p2.getAttack(attack));
          isPlayer1Turn = true;
        }
      } else if (option.toLowerCase().equals("pokemon")) {
        System.out.println("Select a Pokemon: ");
        if (this.isPlayer1Turn) {
          player1.printBackpackBattle();
        } else {
          player2.printBackpackBattle();
        }
        System.out.print("> ");
        String selectedPokemon = in.next();
        if (this.isPlayer1Turn) {
          this.isPlayer1Turn = false;
          Pokemon newPokemon = player1.throwOut(selectedPokemon);
          while (newPokemon == null) {
            System.out.println("Invalid Pokemon! Enter new pokemon: ");
            System.out.print("> ");
            selectedPokemon = in.next();
            newPokemon = player1.throwOut(selectedPokemon);
          }
          startBattle(p2, newPokemon);
          return;
        } 
        else {
          this.isPlayer1Turn = true;
          Pokemon newPokemon = player2.throwOut(selectedPokemon);
          while (newPokemon == null) {
            System.out.println("Invalid Pokemon! Enter new pokemon: ");
            System.out.print("> ");
            selectedPokemon = in.next();
            newPokemon = player2.throwOut(selectedPokemon);
          }
          startBattle(p1, newPokemon);
          return;
        }
      }
      else if (option.toLowerCase().equals("item")) {
        Item items = new Item();
        System.out.println("Enter which item to use: ");
        items.printItems();
        System.out.println("> ");
        String selectedItem = in.next();
        while (!items.checkValidItem(selectedItem)) {
          System.out.println("Invalid item! Enter again:");
          System.out.print("> ");
          selectedItem = in.next();
        }
        System.out.println("Enter which Pokemon you want to use the item on");
        System.out.println();
        if (isPlayer1Turn) {
          player1.printBackpackBattle();
        }
        else {
          player2.printBackpackBattle();
        }
        System.out.print("> ");
        String selected = in.next();
        Pokemon selectedPokemon;
        if (isPlayer1Turn) {
          selectedPokemon = player1.throwOut(selected);
        }
        else {
          selectedPokemon = player2.throwOut(selected);
        }
        while (selectedPokemon == null) {
          System.out.println("Invalid Pokemon! Enter again");
          System.out.print("> ");
          selected = in.next();
          if (isPlayer1Turn) {
            selectedPokemon = player1.throwOut(selected);
            isPlayer1Turn = false;
          }
          else {
            selectedPokemon = player2.throwOut(selected);
            isPlayer1Turn = true;
          }
        }
        items.useItem(selectedPokemon, selectedItem);
      }
      System.out.println();
    }
  }
}