package minesweeper.main.service;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import minesweeper.main.entities.Field;
import minesweeper.main.service.implementation.FieldListenerServiceImpl;
import minesweeper.main.view.View;

/**
 * This is a service for processing any events/changes requested from the controller
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public interface GameService {

  void createField(int width, int height, int mines, int firstClickX, int firstClickY,
      FieldService service, Field field);

  void checkPlayerAction(int x, int y, MouseEvent event, Field field,
      FieldListenerServiceImpl listenerService, View view, FieldService fieldService);

  void checkAction(ActionEvent event, View view);

  void checkStatus(FieldService fieldService, Field field, View view);
}
