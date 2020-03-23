package minesweeper.main.service;

import minesweeper.main.entities.FieldCell;

/**
 * This is a service that processes field events interacts with a game field generation and processing
 * service
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public interface FieldListenerService {

  void onLeftClick(int x, int y, FieldCell[][] field);

  void onRightClick(int x, int y, FieldCell[][] field);
}
