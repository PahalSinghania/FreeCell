package cs3500.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements interface of the FreeCell model.
 *
 * <p></p>New protected method multiMove that helps implements the multi move model.
 * <p></p>changed cascade, foundation, open, size of these piles from private to protected.
 */
public class FreeCellModel implements IFreeCellModel<Card> {
  protected List<Card> deck;
  private boolean started = false;

  protected static int numCascadePiles;
  protected static int numOpenPiles;
  protected static final int numFoundationPiles = 4;

  protected List<Pile> cascade = new ArrayList<Pile>();
  protected List<Pile>  foundation = new ArrayList<Pile>();
  protected List<Pile> open = new ArrayList<Pile>();


  /**
   * Constructs a {@code FreecellModel} object.
   * initialises the number of cascade, foundation and open piles
   * creates the deck
   */
  public FreeCellModel() {
    this.numCascadePiles = 8;
    this.numOpenPiles = 4;

    /** Initialise the deck. */
    this.deck = new ArrayList<Card>();
    this.deck = getDeck();
  }

  /**
   * Constructs a {@code FreecellModel} object .
   * initialises the number of cascade, foundation and open piles
   * creates the deck
   */
  FreeCellModel(List<Pile> cascade,List<Pile> foundation, List<Pile> open) {
    this.cascade = cascade;
    this.foundation = foundation;
    this.open = open;
    this.numCascadePiles = 8;
    this.numOpenPiles = 4;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> temp = new ArrayList<Card>();

    Value[] v = Value.values();
    Suit[] s = Suit.values();
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 13; ++j) {
        temp.add(new Card(v[j], s[i]));
      }
    }
    return temp;
  }

  /* Checks if the given deck has duplicates.*/
  private boolean hasDuplicates(Card current) {
    int count = 0;
    for (int i = 0; i < deck.size(); i++) {
      if (this.deck.get(i).equals(current)) {
        count += 1;
      }
    }
    return count != 1;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles,
                        boolean shuffle) throws IllegalArgumentException {

    /* clear the piles. */
    if (started) {
      open.clear();
      cascade.clear();
      foundation.clear();
    }
    this.deck = deck;
    started = true;
    this.numCascadePiles = numCascadePiles;
    this.numOpenPiles = numOpenPiles;

    /* check if the number of foundation and open piles is valid. */
    if ((numOpenPiles < 1 ) || (numCascadePiles < 4)) {
      started = false;
      throw new IllegalArgumentException("Invalid Piles");
    }

    /* checks if the deck contains 52 unique cards.
     * Compiler for Junit grader crashes. */
    if (deck.size() != 52) {
      started = false;
      throw new IllegalArgumentException("Expected 52");
    }

    /* check for duplicates. */
    for (int i = 0; i < 52; i++) {
      if (hasDuplicates(deck.get(i))) {
        started = false;
        throw new IllegalArgumentException("Invalid Deck");
      }
    }

    /* shuffles the deck if necessary. */
    if (shuffle) {
      Collections.shuffle(this.deck);
    }

    /* initialise all piles. */
    for (int i = 0; i < numCascadePiles; i++) {
      cascade.add(new Pile(PileType.CASCADE));
    }
    for (int i = 0; i < numFoundationPiles; i++) {
      foundation.add(new Pile(PileType.FOUNDATION));
    }
    for (int i = 0; i < numOpenPiles; i++) {
      open.add(new Pile(PileType.OPEN));
    }

    /* initialise the cascade. */
    int pileNumber = 0;
    for (Card c : deck) {
      cascade.get(pileNumber).pile.add(c);
      pileNumber = (pileNumber + 1) % numCascadePiles;
    }
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex,
                   PileType destType, int destPileNumber) throws IllegalArgumentException {
    Card c = null;
    boolean added = false;

    if (sourcePileNumber >= 0 && destPileNumber >= 0
            && cardIndex >= 0) {
      /*  gets the card being tried to move. */
      if (sourceType == PileType.CASCADE && sourcePileNumber < numCascadePiles) {
        c = cascade.get(sourcePileNumber).get(cardIndex);
      } else if (sourceType == PileType.FOUNDATION && sourcePileNumber < numFoundationPiles) {
        c = foundation.get(sourcePileNumber).get(cardIndex);
      } else if (sourceType == PileType.OPEN && sourcePileNumber < numOpenPiles) {
        if (cardIndex == 0) {
          c = open.get(sourcePileNumber).get(cardIndex);
        } else {
          throw new IllegalArgumentException("wrong Card");
        }
      }

      /* adds the card to the destination if the move is valid. */
      if (destType == PileType.CASCADE && destPileNumber < numCascadePiles) {
        added = cascade.get(destPileNumber).addCard(c);
      } else if (destType == PileType.FOUNDATION && destPileNumber < numFoundationPiles) {
        added = foundation.get(destPileNumber).addCard(c);
      } else if (destType == PileType.OPEN && destPileNumber < numOpenPiles) {
        added = open.get(destPileNumber).addCard(c);
      }

      /* removes the card from its prev position if valid move. */
      if (added) {
        if (sourceType == PileType.CASCADE) {
          cascade.get(sourcePileNumber).pile.remove(cardIndex);
        } else if (sourceType == PileType.FOUNDATION) {
          foundation.get(sourcePileNumber).pile.remove(cardIndex);
        } else if (sourceType == PileType.OPEN) {
          open.get(sourcePileNumber).pile.remove(cardIndex);
        }
      }
    }
    if (!added) {
      throw new IllegalArgumentException("Invalid Move");
    }
  }

  /**
   * Helps implement the multi move method.
   * New Protected method
   */
  protected void multiMove(int cardIndex, int source, int dest) {
    boolean added = false;
    boolean empty = true;

    if (source < numCascadePiles && dest < numCascadePiles) {
      List<Card> move = new ArrayList<Card>();
      if (cascade.get(source).validBuild(cardIndex)) {
        // get the list of cards to be moved if it is a valid build

        for (int i = cardIndex; i < cascade.get(source).pile.size(); i++) {
          move.add(cascade.get(source).pile.get(i));
        }

        int n = 1;
        int k = 0;
        for (int i = 0; i < numOpenPiles; i++) {
          if (open.get(i).pile.isEmpty()) {
            n = n + 1;
          }
        }
        for (int i = 0; i < numCascadePiles; i++) {
          if (cascade.get(i).pile.isEmpty()) {
            k = k + 1;
          }
        }
        empty = false;
        // checks if the number of cards being moved is less than (N+1)*2^K
        if (move.size() <= n * Math.pow(2, k)) {
          empty = true;
          // add cards to the destination if it forms a
          // valid build with the destination
          if (move != null) {
            for (Card c : move) {
              added = cascade.get(dest).addCard(c);
            }
          }

          // remove the cards from the source if moved
          if (added) {
            for (int i = 0; i < move.size(); i++) {
              int x = cascade.get(source).pile.size() - 1;
              cascade.get(source).pile.remove(x);
            }
          }
        }
      } else {
        throw new IllegalArgumentException("Invalid Build");
      }
    }
    if (!empty) {
      throw new IllegalArgumentException("Not enough open piles");
    }
    if (!added) {
      throw new IllegalArgumentException("Invalid Move");
    }
  }

  @Override
  public boolean isGameOver() {
    int count = 0;
    /* check if all open piles are empty. */
    for (Pile p : open) {
      count = count + p.pile.size();
    }
    /* check if all cascade piles are empty. */
    for (Pile p : cascade) {
      count = count + p.pile.size();
    }
    return count == 0;
  }

  @Override
  public String getGameState() {
    String output = "";
    if (started) {
      for (int i = 0; i < foundation.size(); i++) {
        output += "F" + Integer.toString(i + 1) + ":";
        output += foundation.get(i).toString() + "\n";
      }
      for (int i = 0; i < open.size(); i++) {
        output += "O" + Integer.toString(i + 1) + ":";
        output += open.get(i).toString() + "\n";
      }
      for (int i = 0; i < cascade.size(); i++) {
        output += "C" + Integer.toString(i + 1) + ":";
        output += cascade.get(i).toString();
        if (i != numCascadePiles - 1) {
          output += "\n";
        }
      }
    }
    return output;
  }
}
