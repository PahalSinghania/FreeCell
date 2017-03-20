package cs3500.hw02;

/**
 *  Represents the suit of a card.
 */
public enum Suit {

  // represents the clubs
  CLUBS("♣"),

  // represents the spades
  SPADES("♠"),

  // represents the hearts
  HEARTS("♥"),

  //represents the diamonds
  DIAMONDS("♦");

  private final String shape;

  /**
   * Constructs a {@code Suit} object.
   *
   * @param shape     the suit or shape of the card
   */
  Suit(String shape) {
    this.shape = shape;
  }

  // returns the shape as a string
  @Override
  public String toString() {
    return this.shape;
  }

  /**
   *  Checks if the given suit is of a different color.
   *  */
  public boolean differentColor(Suit other) {
    if (this == CLUBS || this == SPADES) {
      return other == HEARTS || other == DIAMONDS;
    } else {
      return other == CLUBS || other == SPADES;
    }

  }

}

