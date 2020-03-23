package minesweeper.main.enums;

/**
 * Game skill level (every level has its size and mines count)
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public enum SkillLevel {

  BEGINNER(10, 8, 8),
  MIDDLE(18, 14, 30),
  SKILLED(28, 22, 96);

  public int width;
  public int height;
  public int mines;

  SkillLevel(int width, int height, int mines) {
    this.width = width;
    this.height = height;
    this.mines = mines;
  }
}
