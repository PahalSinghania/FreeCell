package cs3500.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.Card;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;

/**
 * Implements the interface of the FreeCell controller.
 *
 * <p> </p>Modified implementation to use scan.next instead of scan.nextLine
 */
public class FreeCellController implements IFreeCellController<Card> {
  Readable rd;
  public Appendable ap;
  private IFreeCellModel model;
  private String move;
  private boolean quit = false;

  // variables to store data for each move
  private PileType source = null;
  private int sourceNumber = -1;
  private int index = -1;
  private PileType dest = null;
  private int destNumber = -1;

  /**
   * Constructs a {@code FreeCellController} object.
   *
   * @param rd takes user input
   * @param ap transmits output
   */
  public FreeCellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException("Null given");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Starts a new game of free cell.
   *
   * @param numCascades number of cascade piles
   * @param numOpens    number of open piles
   * @param deck        the deck to be dealt
   * @param shuffle     if true, shuffle the deck else deal the deck as-is
   */
  private void startGame(List<Card> deck, int numCascades, int numOpens,
                         boolean shuffle) {
    // checks if the model or deck are equal to null
    if (this.model == null || deck == null) {
      throw new IllegalArgumentException("Null given");
    }

    // starts the game and returns if inputs are invalid
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      try {
        ap.append("Could not start game.");
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      quit = true;
    }

  }

  /* Checks if it is a valid pile input */
  private void getPile() {
    boolean valid = true;
    char c = this.move.charAt(0);
    PileType p = null;
    int j = -1;

    if (c == 'C' || c == 'O' || c == 'F') {
      if (move.length() > 1) {
        String rest = move.substring(1);

        for (int i = 0; i < rest.length(); i++) {
          if (!Character.isDigit(rest.charAt(i))) {
            valid = false;
          }
        }
        if (valid) {
          j = Integer.parseInt(rest) - 1;
          p = PileType.CASCADE;
          if (c == 'O') {
            p = PileType.OPEN;
          }
          if (c == 'F') {
            p = PileType.FOUNDATION;
          }
        }

        // allocates pile
        if (source == null && j >= 0) {
          source = p;
          sourceNumber = j;
        } else if (index >= 0) {
          dest = p;
          destNumber = j;
        }
      }
    }
    try {
      if (source == null) {
        ap.append("\nRe-enter Source Pile");
      } else if (index >= 0 && destNumber < 1) {
        ap.append("\nRe-enter Destination Pile");
      }
    } catch (IOException e) {
      // do nothing
    }
  }

  /* Reads card index */
  private void getIndex() {
    boolean valid = true;
    for (int i = 0; i < move.length(); i++) {
      if (!Character.isDigit(move.charAt(i))) {
        valid = false;
      }
    }
    if (valid) {
      index = Integer.parseInt(move) - 1;
    }
    if (index < 0) {
      try {
        ap.append("\nRe-enter Card Index");
      } catch (IOException e) {
        // do nothing
      }
    }
  }

  /* Determines the move based on the given input */
  private void getMove() {
    char c = this.move.charAt(0);
    if (c == 'q' || c == 'Q') {
      try {
        ap.append("\nGame quit prematurely.");
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.quit = true;
      return;
    } else if (source == null) {
      getPile();
    } else if (sourceNumber >= 0 && index < 0) {
      getIndex();
    } else if (index >= 0 && destNumber < 0) {
      getPile();
    }
  }

  /*  Makes the determined move */
  private void makeMove() {
    String error = "";
    try {
      this.model.move(source, sourceNumber, index, dest, destNumber);
    } catch (IllegalArgumentException e) {
      error = "\nInvalid move. Try again.";
      error = error + e.getMessage();
      try {
        ap.append(error);
      } catch (IOException e1) {
        // do nothing
      }
    }
    source = null;
    sourceNumber = -1;
    destNumber = -1;
    dest = null;
    index = -1;
    return;
  }

  @Override
  public void playGame(List<Card> deck, IFreeCellModel<Card> model, int numCascades,
                       int numOpens, boolean shuffle) throws IllegalStateException {
    this.model = model;
    Scanner scan = new Scanner(this.rd);
    // starts the game
    this.startGame(deck, numCascades, numOpens, shuffle);
    if (this.quit) {
      return;
    }

    // implements the game
    while (!this.model.isGameOver()) {
      try {
        ap.append(this.model.getGameState());
      } catch (IOException e) {
        // do nothing
      }

      /**
       * Takes input with scan.next instead of scan.nextLine()
       */
      if (scan.hasNext()) {
        this.move = scan.next();
      }
      this.getMove();
      // quits if prompted to
      if (this.quit) {
        return;
      }
      if (destNumber >= 0) {
        makeMove();
      }
      try {
        ap.append("\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (this.model.isGameOver()) {
      //checks and returns if game is over
      try {
        ap.append(this.model.getGameState());
        ap.append("\nGame over.");
        return;
      } catch (IOException e) {
        // do nothing
      }
    }
  }
}
