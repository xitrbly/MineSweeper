package minesweeper.main.enums;

import minesweeper.main.view.View;

import java.awt.*;

/**
 * Field cell type enum
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public enum FieldCellType {

  EMPTY,
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  MINE,
  MINE_BLOW;

  /**
   * Every enum is associated with the same name icon.
   *
   * @see View#initFieldCellType()
   * @see /images
   */
  public Image image;

  public Image getImage() {
    return this.image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}
