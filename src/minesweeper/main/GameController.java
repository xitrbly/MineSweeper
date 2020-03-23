package minesweeper.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import minesweeper.main.entities.Field;
import minesweeper.main.view.View;
import minesweeper.main.service.GameService;
import minesweeper.main.service.implementation.FieldListenerServiceImpl;
import minesweeper.main.service.implementation.FieldServiceImpl;
import minesweeper.main.service.implementation.GameServiceImpl;

/**
 * This is controller class responsible for starting the game and initializing the necessary
 * services
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public class GameController {

  /**
   * Game field view
   */
  private View view;

  /**
   * Game field service
   */
  private FieldServiceImpl service;

  /**
   * Game event processing service
   */
  private FieldListenerServiceImpl listenerService;

  /**
   * Game field entity
   */
  private Field field;

  /**
   * This is a service for determining the method of generating a game field, and processing game
   * events, as well as checking the game status
   */
  private GameService gameService;

  public GameController() {
    field = new Field();
    view = new View(this, field);
    service = new FieldServiceImpl();
    gameService = new GameServiceImpl();
    listenerService = new FieldListenerServiceImpl();
    view.initGame();
  }

  public void createField(int x, int y) {
    gameService.createField(x, y, Field.skillLevel.mines, x, y, service, field);
  }

  public void checkAction(ActionEvent event) {
    gameService.checkAction(event, view);
  }

  public void checkPlayerAction(int x, int y, MouseEvent event) {
    gameService.checkPlayerAction(x, y, event, field, listenerService, view, service);
  }

  public void checkStatus() {
    gameService.checkStatus(service, field, view);
  }
}
