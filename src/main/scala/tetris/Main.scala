package tetris

object Main {
  def main(args: Array[String]): Unit = {
    val boardWidth = 10
    val boardHeight = 20

    val board = new Board(boardWidth,boardHeight)

    val game = new TetrisGame(board)

    game.setVisible(true)


  }
}
