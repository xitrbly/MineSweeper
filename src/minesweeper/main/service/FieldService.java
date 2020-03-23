package minesweeper.main.service;

import java.util.List;
import minesweeper.main.entities.FieldCell;
import minesweeper.main.enums.FieldCellState;

/**
 * This is a service responsible for processing processes with game fields
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public interface FieldService {

  FieldCell[][] fillField(int width, int height);

  void putMinesAtField(FieldCell[][] fieldCells, int mines, int firstClickX, int firstClickY);

  void defineOtherCells(FieldCell[][] fieldCells);

  void setFieldCellState(FieldCell[][] fieldCells, FieldCellState fieldCellState);

  FieldCell generateRandomFieldCellCoordinate(int xLength, int yLength);

  List<FieldCell> lookAroundCell(FieldCell fieldCell);

  void gameOver(FieldCell[][] field);

  void checkWrongFlags(FieldCell[][] visibleField);

  int getSize(FieldCell[][] field);
}
