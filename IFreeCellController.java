package cs3500.hw03;

import java.util.List;

import cs3500.hw02.IFreeCellModel;


/**
 * This is the interface of the FreeCellController model.
 * It is parametrized over the card type
 */
public interface IFreeCellController<K> {


  /**
   * Starts a new game of FreeCell using the provided model.
   * It also uses the deck, number of cascade piles and num of open
   * piles provided.
   *
   * <p>If shuffle is false the deck is used as is
   * else the deck is shuffled.
   *
   * <p></p>May throw IllegalstateException if the constructor is not
   * initialised properly
   *
   * @param numCascades             number of cascade piles
   * @param numOpens                number of open piles
   * @param deck                    the deck to be dealt
   * @param shuffle                 if true, shuffle the deck else deal the deck as-is
   * @throws IllegalStateException  if the deck is invalid
   */
  void playGame(List<K> deck, IFreeCellModel<K> model, int numCascades,
                int numOpens, boolean shuffle) throws IllegalStateException;
}
