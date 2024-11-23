package tetris

import javax.swing._
import java.awt._
import java.awt.event.ActionEvent

class MainMenu extends JFrame {
  private val cellSize = 30
  private val frameWidth = 10 * cellSize
  private val frameHeight = 20 * cellSize

  // Параметры игрового поля
  private var boardWidthValue = 10
  private var boardHeightValue = 20

  // Установка основного окна
  setTitle("Tetris Game - Main Menu")
  setSize(frameWidth + 200, frameHeight)
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setLayout(new BorderLayout())
  setResizable(false)
  setLocationRelativeTo(null)

  // Панель для визуального стиля
  private val menuPanel = new JPanel() {
    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      drawMenuBackground(g)
    }
  }
  menuPanel.setPreferredSize(new Dimension(frameWidth, frameHeight))
  menuPanel.setLayout(new GridBagLayout())
  menuPanel.setBackground(Color.BLACK)

  // Настройка кнопки "Play"
  private val playButton = new JButton("Play") {
    setFont(new Font("Arial", Font.BOLD, 20))
    setBackground(Color.RED)
    setForeground(Color.WHITE)
    setPreferredSize(new Dimension(150, 60))
  }
  playButton.addActionListener((_: ActionEvent) => {
    dispose() // Закрыть главное меню
    val board = new Board(boardWidthValue, boardHeightValue)
    val game = new TetrisGame(board)
    game.setVisible(true)
  })

  // Поля для Width и Height
  val widthField = new JTextField(boardWidthValue.toString, 3) {
    setHorizontalAlignment(SwingConstants.CENTER)
    setFont(new Font("Arial", Font.BOLD, 20))
    setPreferredSize(new Dimension(80, 60))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
  }
  val heightField = new JTextField(boardHeightValue.toString, 3) {
    setHorizontalAlignment(SwingConstants.CENTER)
    setFont(new Font("Arial", Font.BOLD, 20))
    setPreferredSize(new Dimension(80, 60))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
  }

  // Метки для Width и Height
  val widthLabel = new JLabel("Width") {
    setFont(new Font("Arial", Font.BOLD, 14))
    setForeground(Color.RED)
    setHorizontalAlignment(SwingConstants.CENTER)
  }
  val heightLabel = new JLabel("Height") {
    setFont(new Font("Arial", Font.BOLD, 14))
    setForeground(Color.RED)
    setHorizontalAlignment(SwingConstants.CENTER)
  }

  // Кнопки + и - для Width
  val widthPlusButton = new JButton("+") {
    setPreferredSize(new Dimension(50, 30))
    setFont(new Font("Arial", Font.BOLD, 16))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
    addActionListener((_: ActionEvent) => {
      boardWidthValue += 1
      widthField.setText(boardWidthValue.toString)
    })
  }
  val widthMinusButton = new JButton("-") {
    setPreferredSize(new Dimension(50, 30))
    setFont(new Font("Arial", Font.BOLD, 16))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
    addActionListener((_: ActionEvent) => {
      if (boardWidthValue > 5) {
        boardWidthValue -= 1
        widthField.setText(boardWidthValue.toString)
      }
    })
  }

  // Кнопки + и - для Height
  val heightPlusButton = new JButton("+") {
    setPreferredSize(new Dimension(50, 30))
    setFont(new Font("Arial", Font.BOLD, 16))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
    addActionListener((_: ActionEvent) => {
      boardHeightValue += 1
      heightField.setText(boardHeightValue.toString)
    })
  }
  val heightMinusButton = new JButton("-") {
    setPreferredSize(new Dimension(50, 30))
    setFont(new Font("Arial", Font.BOLD, 16))
    setBackground(Color.BLACK)
    setForeground(Color.RED)
    setBorder(BorderFactory.createLineBorder(Color.RED, 2))
    addActionListener((_: ActionEvent) => {
      if (boardHeightValue > 10) {
        boardHeightValue -= 1
        heightField.setText(boardHeightValue.toString)
      }
    })
  }

  // Панель с настройками
  //val settingsPanel = new JPanel(new GridBagLayout())

  val settingsPanel = new JPanel(new GridBagLayout()) {
    override def isOpaque: Boolean = false // Делает панель прозрачной
  }

  settingsPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3)) // Красная рамка
  val gbc = new GridBagConstraints()
  gbc.insets = new Insets(5, 5, 5, 5)

  // Кнопка Play
  gbc.gridx = 0
  gbc.gridy = 1
  gbc.gridheight = 2
  settingsPanel.add(playButton, gbc)

  // Поле Height
  gbc.gridx = 1
  gbc.gridy = 1
  gbc.gridheight = 1
  settingsPanel.add(heightField, gbc)

  // Кнопки + и - для Height
  gbc.gridx = 2
  gbc.gridy = 1
  settingsPanel.add(heightPlusButton, gbc)
  gbc.gridy = 2
  settingsPanel.add(heightMinusButton, gbc)

  // Метка Height
  gbc.gridx = 1
  gbc.gridy = 2
  gbc.gridwidth = 1
  gbc.anchor = GridBagConstraints.CENTER
  settingsPanel.add(heightLabel, gbc)

  // Поле Width
  gbc.gridx = 3
  gbc.gridy = 1
  gbc.gridwidth = 1
  settingsPanel.add(widthField, gbc)

  // Кнопки + и - для Width
  gbc.gridx = 4
  gbc.gridy = 1
  settingsPanel.add(widthPlusButton, gbc)
  gbc.gridy = 2
  settingsPanel.add(widthMinusButton, gbc)

  // Метка Width
  gbc.gridx = 3
  gbc.gridy = 2
  gbc.anchor = GridBagConstraints.CENTER
  settingsPanel.add(widthLabel, gbc)

  // Центрируем настройки на главной панели
  menuPanel.add(settingsPanel)
  add(menuPanel, BorderLayout.CENTER)

  private def drawMenuBackground(g: Graphics): Unit = {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, menuPanel.getWidth, menuPanel.getHeight)

    // Сетка как в поле
    g.setColor(Color.DARK_GRAY)
    for (x <- 0 until menuPanel.getWidth by cellSize) {
      for (y <- 0 until menuPanel.getHeight by cellSize) {
        g.drawRect(x, y, cellSize, cellSize)
      }
    }

    // Заголовок
    g.setFont(new Font("Arial", Font.BOLD, 36))
    g.setColor(Color.RED)
    val title = "TETRIS"
    val metrics = g.getFontMetrics
    val titleX = (menuPanel.getWidth - metrics.stringWidth(title)) / 2
    g.drawString(title, titleX, 100)
  }
}
