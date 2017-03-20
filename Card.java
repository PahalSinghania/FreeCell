package cs3500.hw02;

import java.util.Objects;

/**
 * Represents a single card in the gme of FreeCell.
 */
public class Card {
  private final Value value;
  private final Suit suit;

  /**
   * Constructs a {@code Card} object.
   *
   * @param value     the value of the card
   * @param suit      the suit of the card
   */
  public Card(Value value, Suit suit) {
    if (value == null || suit == null) {
      throw new IllegalArgumentException("Bad value or suit");
    }

    this.value = value;
    this.suit = suit;
  }

  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Card)) {
      return false;
    }
    Card other = (Card)obj;
    return this.suit == other.suit && this.value == other.value;
  }

  /* returns the value of the card */
  int getValue() {
    return this.value.getValue();
  }

  Suit getShape() {
    return this.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.suit, this.value);
  }
}
