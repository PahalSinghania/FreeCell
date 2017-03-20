package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the different types of piles.
 *
 * <p></p>New protected method valid build that checks if a given set of cards is of valid build
 */
public class Pile {
  private PileType type;
  List<Card> pile = new ArrayList<Card>();

  /**
   * Constructs a empty pile of cards of a specific type.
   *
   * @param type marks the type of pile it is
   * @throws IllegalArgumentException if invalid type
   */
  Pile(PileType type) {
    if (!(type == PileType.CASCADE || type == PileType.FOUNDATION
            || type == PileType.OPEN)) {
      throw new IllegalArgumentException();
    }
    this.type = type;
    pile = new ArrayList<Card>();
  }

  /* returns the card that is being tried to move. */
  Card get(int n) throws IllegalArgumentException {
    // checks that list is not empty and the last
    // card is being tried to move
    if (n > pile.size()) {
      throw new IllegalArgumentException("Out of bounds");
    } else if (pile.size() - 1 != n) {
      throw new IllegalArgumentException("Invalid Get: Card not at end");
    } else {
      return pile.get(n);
    }
  }

  /*returns the list of cards as a String. */
  @Override
  public String toString() {
    String out = "";
    for (int i = 0; i < pile.size(); i++) {
      out += pile.get(i).toString();
      if (i != pile.size() - 1) {
        out = out + ", ";
      }
    }
    if (!out.equals("")) {
      out = " " + out;
    }
    return out;
  }

  /*adds a card to the pile if it is valid. */
  Boolean addCard(Card c) {
    Boolean added = true;
    // checks if Open pile is empty and adds
    if (type == PileType.OPEN && pile.isEmpty()) {
      pile.add(c);
    }

    /* checks if foundation pile is empty or of the.
     * same suit and then proceeds to add if move is valid. */
    else if (type == PileType.FOUNDATION) {

      if (pile.isEmpty() && c.getValue() == 1) {
        pile.add(c);
      }
      else if (!pile.isEmpty()) {
        added = false;
        // checking if the card is next in line and if
        // the card matches the suit
        Card crd = pile.get(pile.size() - 1);
        if (crd.getValue() + 1 == c.getValue()
                && crd.getShape().equals(c.getShape())) {
          pile.add(c);
          added = true;
        }
      }
      else {
        added = false;
      }
    }

    /* checks if cascade is empty and adds.
     * also checks if the move is valid and adds. */
    else if (type == PileType.CASCADE) {
      if (pile.isEmpty()) {
        pile.add(c);
      }
      else {
        added = false;
      }
      // checks if the card is next in line
      Card crd = pile.get(pile.size() - 1);
      boolean differentColors = crd.getShape().differentColor(c.getShape());
      if ((crd.getValue() - 1 == c.getValue()) && differentColors) {
        pile.add(c);
        added = true;
      }
    }

    else {
      added = false;
    }
    return added;
  }

  /**
   * new Protected method
   * Check whether the build is valid.
   *
   * @param n   index to start at
   * @return    whether Cards below n form a valid build
   */
  protected Boolean validBuild(int n) {
    Card c;
    Card next;
    boolean valid = false;

    if (n < pile.size()) {
      valid = true;
      for (int i = n; i < pile.size() - 1; i++) {
        c = pile.get(i);
        next = pile.get(i + 1);
        if (!(c.getShape().differentColor(next.getShape()))
                || c.getValue() - 1 != next.getValue()) {
          valid = false;
        }
      }
    }
    return valid;
  }

}
