package cs3500.hw02;

/**
 *  Represents the Value of a card.
 */
public enum Value {

  // represents all the possible face values of the card
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
  EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

  private final int value;

  /**
   * Constructs a {@code Value} object.
   *
   * @param value     the face value of the card
   */
  Value(int value) {
    this.value = value;
  }

  // returns the value of the card
  int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    if (this.value == 1) {
      return "A";
    }
    if (this.value == 11) {
      return "J";
    }
    if (this.value == 12) {
      return "Q";
    }
    if (this.value == 13) {
      return "K";
    }
    return "" + this.value;
  }
}