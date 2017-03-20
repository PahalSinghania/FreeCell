package cs3500.hw02;

import java.util.List;

/**
 * This is the interface of the Freecell model. It is parameterized over the
 * card type
 */
public interface IFreeCellModel<K> {

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck
   *
   * @return the deck of cards as a list
   */
  List<K> getDeck();

  /**
   * Deal a new game of Freecell with the given deck, with or without shuffling
   * it first. This method first verifies that the deck is valid.
   *
   * <p>It deals the deck among the cascade piles in round-robin fashion. Thus if
   * there are 4 cascade piles, the 1st pile will get cards 0, 4, 8, ..., the
   * 2nd pile will get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10,
   * ... and the 4th pile will get cards 3, 7, 11, ....
   *
   * <p>Depending on the number of cascade piles, they may have a different number
   * of cards.
   *
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param deck            the deck to be dealt
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  void startGame(List<K> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException;

  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param sourceType                the type of the source pile (see {@link PileType})
   * @param sourcePileNumber          the pile number of the given type, starting at 0
   * @param cardIndex                 the index of the card to be moved from the source
   *                                  pile, starting at 0
   * @param destType                  the type of the destination pile (see {@link PileType})
   * @param destPileNumber            the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible
   */
  void move(PileType sourceType, int sourcePileNumber, int cardIndex,
            PileType destType, int destPileNumber)
          throws IllegalArgumentException;

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   *
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in order)
   * O1:[b]o11[n]                        (Cards in open pile 1)
   * O2:[b]o21[n]                        (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n]                        (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n]  (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n]  (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps     (Cards in cascade pile s in order)
   * </pre>
   * where [b] is a single space, [n] is newline. Note that there is no
   * newline on the last line.  If a pile has no cards in it, there should be no
   * space character after the colon and before the newline.
   */
  String getGameState();
}
