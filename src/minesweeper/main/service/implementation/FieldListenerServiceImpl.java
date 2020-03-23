package minesweeper.main.service.implementation;

import minesweeper.main.entities.FieldCell;
import minesweeper.main.enums.FieldCellState;
import minesweeper.main.enums.FieldCellType;

import java.util.List;
import minesweeper.main.service.FieldListenerService;
import minesweeper.main.service.FieldService;

public class FieldListenerServiceImpl implements FieldListenerService {

  private FieldService service;

  public FieldListenerServiceImpl() {
    this.service = new FieldServiceImpl();
  }

  /**
   * This method processes events when the left mouse button is pressed. The check is carried out
   * for all possible types for this field.
   *
   * @param x,    @param y - panel event coordinates
   * @param field - game field
   */
  public void onLeftClick(int x, int y, FieldCell[][] field) {
    if (field[x][y].getFieldCellState() != FieldCellState.FLAG) {
      switch (field[x][y].getFieldCellType()) {
        case EMPTY:
          field[x][y].setFieldCellState(FieldCellState.OPENED);
          openAroundEmptyFieldCell(x, y, field);
          return;
        case MINE:
          field[x][y].setFieldCellState(FieldCellState.OPENED);
          field[x][y].setFieldCellType(FieldCellType.MINE_BLOW);
          service.gameOver(field);
          service.checkWrongFlags(field);
          return;
        case ONE:
        case TWO:
        case THREE:
        case FOUR:
        case FIVE:
        case SIX:
        case SEVEN:
        case EIGHT:
          field[x][y].setFieldCellState(FieldCellState.OPENED);
      }
    }
  }

  /**
   * This method processes events when the right mouse button is clicked. The check is carried out
   * on all possible types of buttons for this field
   *
   * @param x,    @param y - panel event coordinates
   * @param field - game field
   */
  public void onRightClick(int x, int y, FieldCell[][] field) {
    if (field[x][y].getFieldCellState() == FieldCellState.CLOSED) {
      field[x][y].setFieldCellState(FieldCellState.FLAG);
    } else if (field[x][y].getFieldCellState() == FieldCellState.FLAG) {
      field[x][y].setFieldCellState(FieldCellState.QUESTION);
    } else if (field[x][y].getFieldCellState() == FieldCellState.QUESTION) {
      field[x][y].setFieldCellState(FieldCellState.CLOSED);
    }
  }

  /**
   * This method opens empty cells around the selected empty cell.
   *
   * @see FieldListenerServiceImpl#onLeftClick
   */
  private void openAroundEmptyFieldCell(int x, int y, FieldCell[][] field) {
    List<FieldCell> list = service.lookAroundCell(new FieldCell(x, y));
    list.forEach(element -> {
      if (field[element.getX()][element.getY()].getFieldCellType() != FieldCellType.MINE &&
          field[element.getX()][element.getY()].getFieldCellState() == FieldCellState.CLOSED) {
        field[element.getX()][element.getY()].setFieldCellState(FieldCellState.OPENED);
        onLeftClick(element.getX(), element.getY(), field);
      }
    });
  }
}
