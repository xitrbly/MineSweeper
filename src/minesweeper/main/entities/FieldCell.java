package minesweeper.main.entities;

import minesweeper.main.enums.FieldCellState;
import minesweeper.main.enums.FieldCellType;

/**
 * Game field cell entity
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public class FieldCell {

  /**
   * Field cell X coordinate
   */
  private int x;

  /**
   * Field cell Y coordinate
   */
  private int y;

  /**
   * Entity to display opened field cell type
   */
  private FieldCellType fieldCellType;

  /**
   * Entity to display closed field cell state
   */
  private FieldCellState fieldCellState;

  public FieldCell(int x, int y) {
    this.x = x;
    this.y = y;
    fieldCellState = FieldCellState.CLOSED;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public FieldCellType getFieldCellType() {
    return fieldCellType;
  }

  public void setFieldCellType(FieldCellType fieldCellType) {
    this.fieldCellType = fieldCellType;
  }

  public FieldCellState getFieldCellState() {
    return fieldCellState;
  }

  public void setFieldCellState(FieldCellState fieldCellState) {
    this.fieldCellState = fieldCellState;
  }
}
