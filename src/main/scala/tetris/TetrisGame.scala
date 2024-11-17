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

  private val infoPanel = new JPanel()
  infoPanel.setLayout(new GridLayout(2,1))
  infoPanel.add(scoreLabel)
  infoPanel.add(levelLabel)
  add(infoPanel,BorderLayout.NORTH)

  setTitle("Tetris Game")
  setSize(boardWidth+200, boardHeight)
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setLayout(new BorderLayout())
  setLocationRelativeTo(null)

  private val gamePanel = new JPanel(){
    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      drawBoard(g)
    }
  }


  gamePanel.setPreferredSize(new Dimension(boardWidth, boardHeight))
  add(gamePanel)

  setVisible(true)

  def refresh(): Unit = {
    scoreLabel.setText(s"Score: ${board.score}")
    gamePanel.repaint()
  }

  private def drawBoard(g: Graphics): Unit = {
    for (y <- 0 until board.height; x <- 0 until board.width) {
      if (board.grid(y)(x) != 0) {
        g.setColor(Color.BLUE)
      } else {
        g.setColor(Color.BLACK)
      }
      g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize)
      g.setColor(Color.GRAY)
      g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize)
    }

    if (board.currentTetromino != null) {
      g.setColor(Color.RED)
      for ((x, y) <- board.currentTetromino.blocks) {
        if (x >= 0 && x < board.width && y >= 0 && y < board.height) {
          g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize)
          g.setColor(Color.GRAY)
          g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize)
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
       case _ =>
     }
      gamePanel.repaint()
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
