package minesweeper.main.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import minesweeper.main.entities.Field;
import minesweeper.main.entities.FieldCell;
import minesweeper.main.enums.FieldCellState;
import minesweeper.main.enums.FieldCellType;
import minesweeper.main.GameController;

/**
 * Game GUI
 *
 * @author Ivan Makarov (xitrbly@gmail.com)
 */
public class View {

  private static final Logger LOGGER = Logger.getLogger("FieldView logger");

  /**
   * Game controller to call GameService methods
   */
  private GameController controller;

  /**
   * Game frame
   */
  private JFrame frame;

  /**
   * Container to display game field
   */
  public JPanel panel;

  /**
   * Game cell size. It uses to draw the cell and coordinate calculation.
   */
  private int iconSize = 25;

  /**
   * Game field storing data about game status, difficulty and field cells coordinate
   */
  private Field field;

  public View(GameController controller, Field field) {
    this.controller = controller;
    this.field = field;
  }

  /**
   * It initializes game GUI, GameController calls it to restart. {@link
   * GameController#GameController}
   */
  public void initGame() {
    if (frame != null) {
      frame.dispose();
    }
    initFrame();
    initMenu();
    initField();
    initPanel();
    initFieldCellType();
    frame.add(panel);
    frame.pack();
    addMouseListener();
  }

  /**
   * Frame initialization, setting parameters
   */
  private void initFrame() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setTitle("MineSweeper");
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
  }

  /**
   * JPanel initialization with paintComponent() method overriding. It redraws after every action
   * with repaint() method.
   */
  private void initPanel() {
    panel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        FieldCell[][] fieldCells = field.getField();
        Arrays.stream(fieldCells)
            .forEach(elements -> Arrays.stream(elements)
                .forEach(element -> {
                  if (element.getFieldCellState() == FieldCellState.CLOSED) {
                    ImageIcon imageIcon = new ImageIcon(FieldCellState.CLOSED.getImage());
                    imageIcon
                        .paintIcon(this, g, element.getX() * iconSize, element.getY() * iconSize);
                  } else if (element.getFieldCellState() == FieldCellState.FLAG) {
                    ImageIcon imageIcon = new ImageIcon(FieldCellState.FLAG.getImage());
                    imageIcon
                        .paintIcon(this, g, element.getX() * iconSize, element.getY() * iconSize);
                  } else if (element.getFieldCellState() == FieldCellState.QUESTION) {
                    ImageIcon imageIcon = new ImageIcon(FieldCellState.QUESTION.getImage());
                    imageIcon
                        .paintIcon(this, g, element.getX() * iconSize, element.getY() * iconSize);
                  } else if (element.getFieldCellState() == FieldCellState.WRONG_FLAG) {
                    ImageIcon imageIcon = new ImageIcon(FieldCellState.WRONG_FLAG.getImage());
                    imageIcon
                        .paintIcon(this, g, element.getX() * iconSize, element.getY() * iconSize);
                  } else {
                    ImageIcon imageIcon = new ImageIcon(element.getFieldCellType().getImage());
                    imageIcon
                        .paintIcon(this, g, element.getX() * iconSize, element.getY() * iconSize);
                  }
                }));
      }
    };
    panel.setPreferredSize(new Dimension(Field.skillLevel.width * iconSize
        , Field.skillLevel.height * iconSize));
  }

  /**
   * Game menu initialization and setting action listener to his elements.
   */
  private void initMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu game = new JMenu("Game");
    JMenuItem newGame = new JMenuItem("New");
    JMenu difficulty = new JMenu("Difficulty");
    JMenuItem beginner = new JMenuItem("Beginner");
    JMenuItem middle = new JMenuItem("Middle");
    JMenuItem skilled = new JMenuItem("Skilled");
    JMenuItem exit = new JMenuItem("Exit");

    game.add(newGame);
    game.add(difficulty);
    game.add(exit);
    difficulty.add(beginner);
    difficulty.add(middle);
    difficulty.add(skilled);
    menuBar.add(game);
    frame.setJMenuBar(menuBar);

    ActionListener listener = event -> controller.checkAction(event);
    newGame.addActionListener(listener);
    beginner.addActionListener(listener);
    middle.addActionListener(listener);
    skilled.addActionListener(listener);
    exit.addActionListener(listener);
  }

  /**
   * Set every field type/state the icon from "images" folder
   */
  private void initFieldCellType() {
    BufferedImage image;
    for (FieldCellType fieldCellType : FieldCellType.values()) {
      try {
        image = ImageIO.read(new File("images/" + fieldCellType.name() + ".png"));
        fieldCellType.setImage(image);
      } catch (IOException e) {
        if (e instanceof FileNotFoundException) {
          LOGGER.warning("Image with name" + fieldCellType.name() + ".png not found");
        }
        e.printStackTrace();
      }
    }
    List<FieldCellState> stateList = Arrays.stream(FieldCellState.values())
        .collect(Collectors.toList());
    stateList.remove(FieldCellState.OPENED);
    for (FieldCellState fieldCellState : stateList) {
      try {
        image = ImageIO.read(new File("images/" + fieldCellState.name() + ".png"));
        fieldCellState.setImage(image);
      } catch (IOException e) {
        if (e instanceof FileNotFoundException) {
          LOGGER.warning("Image with name" + fieldCellState.name() + ".png not found");
        } else if (e instanceof IIOException) {
          LOGGER.warning("Can't read input file " + fieldCellState.name() + ".png");
        }
        e.printStackTrace();
      }
    }
  }

  /**
   * The first direct call to the controller to start the generation of the playing field based on
   * SkillLevel parameters
   *
   * @see minesweeper.main.enums.SkillLevel
   */
  private void initField() {
    controller
        .createField(Field.skillLevel.width, Field.skillLevel.height);
  }

  /**
   * Exit game with 0 status
   */
  public void exitGame() {
    System.exit(0);
  }

  /**
   * Add a mouse event listener for the playing field. The overridden method delegates the execution
   * of event processing to the controller, passing the coordinates of the event and the event
   * itself, then checks the status of the game
   */
  private void addMouseListener() {
    panel.addMouseListener(new MouseListener() {
      @Override
      public void mouseReleased(MouseEvent event) {
        int x = event.getX() / iconSize;
        int y = event.getY() / iconSize;
        controller.checkPlayerAction(x, y, event);
        controller.checkStatus();
      }

      @Override
      public void mouseClicked(MouseEvent e) {
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }
    });
  }
}
