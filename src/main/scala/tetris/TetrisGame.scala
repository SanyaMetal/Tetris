package tetris

import javax.swing._
import java.awt._
import java.awt.event._


class TetrisGame(board: Board) extends JFrame{
  private val cellSize = 30
  private val boardWidth = board.width * cellSize
  private val boardHeight = board.height * cellSize

  private val scoreLabel = new JLabel(s"Score: ${board.score}")
  private val levelLabel = new JLabel(s"Level: ${board.level}")

  private val infoFont = new Font("Arial", Font.BOLD, 18)
  scoreLabel.setFont(infoFont)
  levelLabel.setFont(infoFont)
  //Шрифт


  private val infoPanel = new JPanel(new GridLayout(2,1)){
    setBackground(Color.BLACK)
    setBorder(BorderFactory.createTitledBorder("Info")) //Рамка
  }
  infoPanel.add(scoreLabel)
  infoPanel.add(levelLabel)
    // Счетчик уровня и очков


  private val gamePanel = new JPanel() {
    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      drawBoard(g)
    }
  }
  gamePanel.setPreferredSize(new Dimension(boardWidth, boardHeight))
  gamePanel.setBackground(Color.LIGHT_GRAY) // Фон игрового поля
  gamePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)) // Рамка игрового поля

  private val nextTetrominoPanel = new JPanel() {
    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      drawNextTetromino(g)
    }
  }

//  nextTetrominoPanel.setPreferredSize(new Dimension(4 * cellSize, 4 * cellSize))
//  nextTetrominoPanel.setBackground(Color.LIGHT_GRAY) // Фон панели следующей фигуры
//  nextTetrominoPanel.setBorder(BorderFactory.createTitledBorder("Next")) // Заголовок рамки


  private val nextTetrominoContainer = new JPanel(new BorderLayout()) {
    setPreferredSize(new Dimension(4 * cellSize + 20, boardHeight))
    setBackground(Color.BLUE)//цвет фона справа
  }
  nextTetrominoPanel.setPreferredSize(new Dimension(4 * cellSize, 4 * cellSize))
  nextTetrominoPanel.setBackground(Color.LIGHT_GRAY)
  nextTetrominoPanel.setBorder(BorderFactory.createTitledBorder("Next"))



  nextTetrominoContainer.add(nextTetrominoPanel, BorderLayout.NORTH)
  nextTetrominoContainer.add(infoPanel, BorderLayout.CENTER)
  add(nextTetrominoContainer, BorderLayout.EAST)

  getContentPane.setBackground(Color.BLUE)// Общий фон


  //add(infoPanel,BorderLayout.NORTH) // Счет и Лвл Сверху
  add(gamePanel, BorderLayout.CENTER)
  //add(nextTetrominoPanel, BorderLayout.EAST)

  //setResizable(false) // Запретить изменение размера
  pack() // Установить размеры окна на основе содержимого

  setVisible(true)

  setTitle("Tetris Game")
  //setSize(boardWidth, boardHeight)
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setLayout(new BorderLayout())
  setLocationRelativeTo(null)

  def refresh(): Unit = {
    scoreLabel.setText(s"Score: ${board.score}")
    levelLabel.setText(s"Level: ${board.level}")
    gamePanel.repaint()
    nextTetrominoPanel.repaint()
  }

  private def drawBoard(g: Graphics): Unit = {
    for (y <- board.invisibleRows until board.totalHeight; x <- 0 until board.width) {
      val visibleY = y - board.invisibleRows
      if (board.grid(y)(x) != 0) {
        g.setColor(Color.BLUE)
      } else {
        g.setColor(Color.BLACK)
      }
      g.fillRect(x * cellSize, visibleY * cellSize, cellSize, cellSize)
      g.setColor(Color.GRAY)
      g.drawRect(x * cellSize, visibleY * cellSize, cellSize, cellSize)
    }

    if (board.currentTetromino != null) {
      g.setColor(Color.RED)
      for ((x, y) <- board.currentTetromino.blocks) {
        val visibleY = y - board.invisibleRows
        if (x >= 0 && x < board.width && visibleY >= 0 && visibleY < board.height) {
          g.fillRect(x * cellSize, visibleY * cellSize, cellSize, cellSize)
          g.setColor(Color.RED)
          g.drawRect(x * cellSize, visibleY * cellSize, cellSize, cellSize)
        }
      }
    }
  }


  addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent): Unit = {
     e.getKeyCode match {
       case KeyEvent.VK_A => board.moveTetrominoLeft()
       case KeyEvent.VK_D => board.moveTetrominoRight()
       case KeyEvent.VK_S => board.moveTetrominoDown()
       case KeyEvent.VK_W => board.rotateTetromino()
       case KeyEvent.VK_P => togglePause()
       case KeyEvent.VK_G => timer.start()
       case _ =>
     }
      //gamePanel.repaint()
      refresh()
    }
  })

  setFocusable(true)

  def restartGame():Unit = {
    board.reset()
    board.spawnTetromino()
    board.score = 0
    board.level = 1
    timer.start()
    refresh()
  }

  def drawNextTetromino(g:Graphics):Unit = {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, nextTetrominoPanel.getWidth, nextTetrominoPanel.getHeight)

    if (board.nextTetromino != null){
      g.setColor(Color.RED)
      for((x, y) <-board.nextTetromino.shape){
        val drawX = (x+2)*cellSize
        val drawY = (y+2)* cellSize
        g.fillRect(drawX,drawY,cellSize,cellSize)
        g.setColor(Color.RED)
        g.drawRect(drawX, drawY, cellSize, cellSize)
      }
    }
  }

  // Панель паузы
  private val pausePanel = new JPanel() {
    setLayout(new GridBagLayout())
    setBackground(new Color(0, 0, 0, 150)) // Полупрозрачный черный фон
  }

  private val pauseLabel = new JLabel("Game Paused")
  pauseLabel.setForeground(Color.WHITE)
  pauseLabel.setFont(new Font("Arial", Font.BOLD, 36))

  // Кнопка для возобновления игры
  private val resumeButton = new JButton("Resume")
  resumeButton.setFont(new Font("Arial", Font.BOLD, 24))
  resumeButton.setBackground(new Color(34, 139, 34)) // Зеленый фон
  resumeButton.setForeground(Color.WHITE) // Белый текст
  resumeButton.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      togglePause()
    }
  })

  // Кнопка для выхода в главное меню
  private val exitButton = new JButton("Exit to Main Menu")
  exitButton.setFont(new Font("Arial", Font.BOLD, 24))
  exitButton.setBackground(new Color(178, 34, 34)) // Красный фон
  exitButton.setForeground(Color.WHITE) // Белый текст
  exitButton.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      // Скрыть окно игры
      setVisible(false)

      // Показать главное меню (например, создаем новое окно меню)
      val mainMenu = new MainMenu() // Предположим, что у вас есть класс MainMenu
      mainMenu.setVisible(true)
    }
  })

  // Добавление компонентов на панель с использованием GridBagConstraints
  val constraints = new GridBagConstraints()
  constraints.gridx = 0
  constraints.gridy = 0
  constraints.insets = new Insets(10, 0, 10, 0) // Отступы между элементами
  pausePanel.add(pauseLabel, constraints)

  constraints.gridy = 1
  pausePanel.add(resumeButton, constraints)

  constraints.gridy = 2
  pausePanel.add(exitButton, constraints)

  // Добавляем панель паузы на игровую панель
  pausePanel.setVisible(false) // Скрыть панель при запуске игры
  gamePanel.setLayout(new BorderLayout())
  gamePanel.add(pausePanel, BorderLayout.CENTER) // Добавляем на игровую панель


  // Функция переключения паузы
  private var isPaused = false

  def togglePause(): Unit = {
    isPaused = !isPaused
    pausePanel.setVisible(isPaused)
    if (isPaused) {
      timer.stop()
    } else {
      timer.start()
    }
  }


  val timer: javax.swing.Timer = new Timer(500, new ActionListener{
   override def actionPerformed(e: ActionEvent): Unit = {
      if(!board.moveTetrominoDown() && board.checkGameOver()){
        timer.stop()
        val result = JOptionPane.showConfirmDialog(
          null,
          "Game Over! Restart?",
          "Game Over",
          JOptionPane.YES_NO_OPTION
        )
        if(result == JOptionPane.YES_OPTION){
          restartGame()
        }
      }
      refresh()
    }
  })
  timer.start()


}
