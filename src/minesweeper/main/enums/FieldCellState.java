package minesweeper.main.enums;

import java.awt.Image;

/**
 * Field cell state enum
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */

public enum FieldCellState {
  OPENED,
  CLOSED,
  QUESTION,
  FLAG,
  WRONG_FLAG;

  public Image image;

  public Image getImage() {
    return this.image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}
