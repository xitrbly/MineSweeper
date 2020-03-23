package minesweeper.main.service.implementation;

import java.util.Arrays;
import java.util.stream.IntStream;
import minesweeper.main.entities.FieldCell;
import minesweeper.main.entities.Field;
import minesweeper.main.enums.FieldCellState;
import minesweeper.main.enums.FieldCellType;
import minesweeper.main.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import minesweeper.main.service.FieldService;

public class FieldServiceImpl implements FieldService {

  private Random random;

  public FieldServiceImpl() {
    this.random = new Random();
  }

  /**
   * This method fills the playing field with empty cells
   *
   * @param width  - game field width
   * @param height - game field height
   */
  public FieldCell[][] fillField(int width, int height) {
    FieldCell[][] fieldCells = new FieldCell[width][height];
    IntStream.range(0, width)
        .forEach(x -> IntStream.range(0, height)
            .forEach(y -> fieldCells[x][y] = new FieldCell(x, y)));
    return fieldCells;
  }

  /**
   * This method fills the playing field with mines
   *
   * @param fieldCells  - playing field cells to be filled with mines
   * @param mines       - mines number on the field
   * @param firstClickX @param firstClickY - field coordinates when the first left mouse click was
   *                    made (the field fills with mines and other types after the first left click
   *                    to avoid losing after the first left click)
   */
  public void putMinesAtField(FieldCell[][] fieldCells, int mines, int firstClickX,
      int firstClickY) {
    int mineCount = 0;
    while (mineCount < mines) {
      FieldCell random = generateRandomFieldCellCoordinate(fieldCells.length, fieldCells[0].length);
      if (fieldCells[random.getX()][random.getY()].getFieldCellType() != FieldCellType.MINE
          && random.getX() != firstClickX
          && random.getY() != firstClickY) {
        fieldCells[random.getX()][random.getY()].setFieldCellType(FieldCellType.MINE);
        mineCount++;
      }
    }
  }

  /**
   * This method generates numerical and empty cells depending on mines coordinates
   *
   * @param fieldCells - game field cells to fill
   */
  public void defineOtherCells(FieldCell[][] fieldCells) {
    List<FieldCell> lookAroundFieldCells;
    FieldCellType[] fieldCellTypes = FieldCellType.values();
    int mineCount;
    for (int i = 0; i < fieldCells.length; i++) {
      for (int j = 0; j < fieldCells[i].length; j++) {
        mineCount = 0;
        if (fieldCells[i][j].getFieldCellType() == FieldCellType.MINE) {
          continue;
        }
        lookAroundFieldCells = lookAroundCell(fieldCells[i][j]);

        for (FieldCell fieldCell : lookAroundFieldCells) {
          if (fieldCells[fieldCell.getX()][fieldCell.getY()].getFieldCellType()
              == FieldCellType.MINE) {
            mineCount++;
          }
        }
        fieldCells[i][j].setFieldCellType(fieldCellTypes[mineCount]);
      }
    }
  }

  /**
   * Method for setting the field cells state (with attached icons) to the entire field.
   *
   * @param fieldCells     - game field cells to fill
   * @param fieldCellState - set all of field cells state to specified one
   */
  public void setFieldCellState(FieldCell[][] fieldCells, FieldCellState fieldCellState) {
    Arrays.stream(fieldCells)
        .forEach(array -> Arrays.stream(array)
            .forEach(element -> element.setFieldCellState(fieldCellState)));
  }

  /**
   * This method generates random coordinates. Used to fill the field cells with mines.
   *
   * @param maxWidth- максимальные значение ширины игрового поля
   * @param maxLength - максимальные значение высоты игрового поля
   */
  public FieldCell generateRandomFieldCellCoordinate(int maxWidth, int maxLength) {
    return new FieldCell(random.nextInt((maxWidth - 1)), random.nextInt(maxLength - 1));
  }

  /**
   * This method used to get cells around any cell
   *
   * @param fieldCell - field cell, the coordinates around which you need to get the coordinates of
   *                  other field cells
   */
  public List<FieldCell> lookAroundCell(FieldCell fieldCell) {
    List<FieldCell> aroundCellsList = new ArrayList<>();
    IntStream.range(fieldCell.getX() - 1, fieldCell.getX() + 2)
        .forEach(x -> IntStream.range(fieldCell.getY() - 1, fieldCell.getY() + 2)
            .forEach(y -> {
              if (x >= 0 && y >= 0 && x <= Field.skillLevel.width - 1
                  && y <= Field.skillLevel.height - 1) {
                aroundCellsList.add(new FieldCell(x, y));
              }
            }));
    return aroundCellsList;
  }

  /**
   * Method for displaying all mines in case of loss (mine explosion)
   */
  public void gameOver(FieldCell[][] field) {
    Field.status = Status.LOSE;
    IntStream.range(0, field.length)
        .forEach(i -> IntStream.range(0, field[0].length)
            .forEach(q -> {
              if (field[i][q].getFieldCellType() == FieldCellType.MINE) {
                field[i][q].setFieldCellState(FieldCellState.OPENED);
                field[i][q].setFieldCellType(FieldCellType.MINE_BLOW);
              }
            }));
  }

  /**
   * This method executes after end of the game. It shows incorrectly placed flags in case of loss.
   */
  public void checkWrongFlags(FieldCell[][] field) {
    IntStream.range(0, field.length)
        .forEach(i -> IntStream.range(0, field[0].length)
            .forEach(q -> {
              if (field[i][q].getFieldCellState() == FieldCellState.FLAG) {
                field[i][q].setFieldCellState(FieldCellState.WRONG_FLAG);
              }
            }));
  }

  public int getSize(FieldCell[][] field) {
    return field.length * field[0].length;
  }
}
