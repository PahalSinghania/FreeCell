package cs3500.hw04;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;

/**
 * Implements the multi move model for FreeCell.
 */
public class MultiMoveModel extends FreeCellModel implements IFreeCellModel<Card> {

  /**
   * Constructs a {@code MultiMoveModel} object .
   * initialises the number of cascade, foundation and open piles
   * creates the deck
   */
  public MultiMoveModel() {
   super();
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) throws IllegalArgumentException {
    if (sourcePileNumber >= 0 && destPileNumber >= 0 && cardIndex >= 0) {
      if (sourceType == PileType.CASCADE && destType == PileType.CASCADE) {
        multiMove(cardIndex, sourcePileNumber, destPileNumber);
      } else {
        super.move(sourceType, sourcePileNumber, cardIndex, destType, destPileNumber);
      }
    }
    else {
      throw new IllegalArgumentException("Invalid Move: ");
    }
  }

}
