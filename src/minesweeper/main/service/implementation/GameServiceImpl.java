package minesweeper.main.service.implementation;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import minesweeper.main.entities.Field;
import minesweeper.main.entities.FieldCell;
import minesweeper.main.enums.FieldCellState;
import minesweeper.main.enums.FieldCellType;
import minesweeper.main.enums.SkillLevel;
import minesweeper.main.enums.Status;
import minesweeper.main.service.FieldService;
import minesweeper.main.service.GameService;
import minesweeper.main.view.View;

public class GameServiceImpl implements GameService {

  /**
   * This variable is used to determine the first click after which the generation of the playing
   * field begins. Thus, the player does not start the game by clicking on the mine
   */
  private int clickCount = 0;

  /**
   * Field generation method. Its task is to arrange bombs, numbers and empty cells. The first 3
   * parameters are governed by the game difficulty.
   *
   * @param width       - field cell width
   * @param height      - field cell height
   * @param mines       - mines number on the field
   * @param firstClickX @param firstClickY - coordinates of the first mouse click (the field is
   *                    filled with mines and other types after the first click to avoid losing
   *                    after the first click
   */
  public void createField(int width, int height, int mines, int firstClickX, int firstClickY,
      FieldService service, Field field) {
    FieldCell[][] fieldCells = service.fillField(width, height);
    service.setFieldCellState(fieldCells, FieldCellState.CLOSED);
    service.putMinesAtField(fieldCells, mines, firstClickX, firstClickY);
    service.defineOtherCells(fieldCells);
    Field.status = Status.IN_PROGRESS;
    field.setField(fieldCells);
  }

  /**
   * This method is used to check the first click and set the moment of generation of the playing
   * field. Otherwise, it calls the event handling method.
   *
   * @param x,    @param y - click coordinates
   * @param event - passes the event to the field event handler
   */
  public void checkPlayerAction(int x, int y, MouseEvent event, Field field,
      FieldListenerServiceImpl listenerService, View view, FieldService fieldService) {
    if (clickCount == 0) {
      createField(Field.skillLevel.width, Field.skillLevel.height,
          Field.skillLevel.mines, x, y, fieldService, field);
    }
    mouseEventHandler(x, y, event, field.getField(),
        listenerService, view);
    clickCount++;
    isVictory(field);
  }

  /**
   * This method is for processing mouse events for a field. Distributes the event and its
   * parameters into the methods of the event handler service, depending on the button pressed
   *
   * @param x     - click X coordinate
   * @param y     - click Y coordinate
   * @param event - click event
   * @param field - game field
   * @see FieldListenerServiceImpl
   */
  private void mouseEventHandler(int x, int y, MouseEvent event, FieldCell[][] field,
      FieldListenerServiceImpl listenerService, View view) {
    if (event.getButton() == MouseEvent.BUTTON1) {
      listenerService.onLeftClick(x, y, field);
      view.panel.repaint();
    }
    if (event.getButton() == MouseEvent.BUTTON3) {
      listenerService.onRightClick(x, y, field);
      view.panel.repaint();
    }
  }

  /**
   * This method is called after each click to check the status of the game, checking all fields and
   * matching the number of cells marked with a question mark, a flag and closed cells with the
   * number of mines defined for a given game difficulty
   */
  private void isVictory(Field field) {
    int closedFieldCells = 0;
    for (FieldCell[] fieldCells : field.getField()) {
      for (FieldCell fieldCell : fieldCells) {
        if (fieldCell.getFieldCellState() == FieldCellState.CLOSED
            || (fieldCell.getFieldCellState() == FieldCellState.FLAG
            && fieldCell.getFieldCellType() == FieldCellType.MINE)) {
          closedFieldCells++;
        }
      }
    }
    if (closedFieldCells == Field.skillLevel.mines) {
      Field.status = Status.WIN;
    }
  }

  /**
   * This method checks the current status of the game and displays messages in case of loss or win,
   * which provide the player with the opportunity to start the game again. If the player agrees,
   * the click counter is reset and the initialization of the game is called again, otherwise the
   * application c closes with the status 0.
   *
   * @see View#initGame()
   */
  public void checkStatus(FieldService fieldService, Field field, View view) {
    isVictory(field);
    switch (Field.status) {
      case WIN:
        String[] options = new String[]{"Yes", "No"};
        int response = JOptionPane.showOptionDialog(view.panel, "Congratulations! Start again?"
            , "Winner!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if (response == 0) {
          clickCount = 0;
          view.initGame();
        } else {
          System.exit(0);
        }
        return;
      case LOSE:
        String[] options2 = new String[]{"Yes", "No"};
        int responses = JOptionPane.showOptionDialog(view.panel, "Game Over. Start again?"
            , "GameOver", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options2, options2[0]);
        if (responses == 0) {
          clickCount = 0;
          view.initGame();
        } else {
          System.exit(0);
        }
    }
  }

  /**
   * This method processes the following menu events: "New" - starts a new game, "Exit" - closes the
   * game. "Beginner", "Middle", "Skilled" - changes the difficulty of the game {@link
   * minesweeper.main.enums.SkillLevel} After each case, except Exit, the game restarts {@link
   * View#initGame()}
   */
  public void checkAction(ActionEvent event, View view) {
    switch (event.getActionCommand()) {
      case "New":
        view.initGame();
        clickCount = 0;
        return;
      case "Exit":
        view.exitGame();
        clickCount = 0;
      case "Beginner":
        Field.skillLevel = SkillLevel.BEGINNER;
        view.initGame();
        clickCount = 0;
        return;
      case "Middle":
        Field.skillLevel = SkillLevel.MIDDLE;
        view.initGame();
        clickCount = 0;
        return;
      case "Skilled":
        Field.skillLevel = SkillLevel.SKILLED;
        view.initGame();
        clickCount = 0;
    }
  }
}
