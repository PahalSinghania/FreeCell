package cs3500.hw04;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;

/**
 * Creates a FreeCellModel based on the user input.
 */
public class FreeCellModelCreator {

  /**
   * Enumerates the different types of games.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * Creates a new FreeCellModel based on user input.
   *
   * @param type    Determines the type of model to be created
   * @return        an IFreeCellModel in accordance with the given type
   */
  public static IFreeCellModel<Card> create(GameType type) {
    if (type == GameType.MULTIMOVE) {
      return new MultiMoveModel();
    }
    else {
      return new FreeCellModel();
    }
  }
}
