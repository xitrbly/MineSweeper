package minesweeper.main.entities;

import minesweeper.main.enums.SkillLevel;
import minesweeper.main.enums.Status;

/**
 * Game field entity
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public class Field {

  /**
   * Game status for checking the current game state
   *
   * @see Status
   */
  public static Status status;

  /**
   * Game skill level entity. "Beginner" is the default value
   *
   * @see SkillLevel
   */
  public static SkillLevel skillLevel = SkillLevel.BEGINNER;

  /**
   * Game field cells
   *
   * @see FieldCell
   */
  private FieldCell[][] fieldCells;

  public FieldCell[][] getField() {
    return fieldCells;
  }

  public void setField(FieldCell[][] fieldCells) {
    this.fieldCells = fieldCells;
  }
}


