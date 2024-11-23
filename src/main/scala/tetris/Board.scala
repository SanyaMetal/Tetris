package tetris

class Board (val width: Int, val height: Int){
  val invisibleRows = 3
  val totalHeight = height + invisibleRows

  val grid = Array.ofDim[Int](totalHeight,width)



  var score:Int = 0
  var level:Int = 1

  var currentTetromino: Tetromino = Tetromino.randomTetromino(width / 2, 0 + invisibleRows)
  var nextTetromino: Tetromino = Tetromino.randomTetromino(width / 2 , invisibleRows)

  def isOccupied(x: Int, y: Int): Boolean ={
    grid(y)(x) !=0
  }

  def canPlaceTetromino(tetromino: Tetromino): Boolean = {
    tetromino.blocks.forall {
      case (x, y) => x >= 0 && x < width && y >= 0 && y < totalHeight && !isOccupied(x, y)
    }
  }

  def placeTetromino(tetromino: Tetromino): Unit = {
    tetromino.blocks.foreach {
      case (x, y) =>
        if (x >= 0 && x < grid(0).length && y >= 0 && y < grid.length) {
          grid(y)(x) = 1
        }
    }
  }

  def canMoveTetromino(dx: Int, dy: Int): Boolean = {
    val newPosition = (currentTetromino.position._1 + dx, currentTetromino.position._2 + dy)
    val newTetromino = currentTetromino.copy()
    newTetromino.position = newPosition

    canPlaceTetromino(newTetromino)
  }

  def moveTetrominoLeft(): Unit ={
    if(canMoveTetromino(-1, 0)) {
      currentTetromino.moveLeft()
    }
  }

  def moveTetrominoRight():Unit = {
    if(canMoveTetromino(1, 0)) {
      currentTetromino.moveRight()
    }
  }

//  def rotateTetromino():Unit = {
//    val newTetromino = currentTetromino.copy()
//    newTetromino.rotate()
//    if (canPlaceTetromino(newTetromino)) {
//      currentTetromino = newTetromino
//    }
//  }

  def rotateTetromino():Unit = {
    val originalShape = currentTetromino.shape
    currentTetromino.rotate()
    if (!canPlaceTetromino(currentTetromino)){
      currentTetromino.shape = originalShape
    }
  }


  def clearFullLines(): Int = {
    val (fullRows, remainingRows) = grid.partition(_.forall(_!=0))
    val clearedCount = fullRows.length

    if (clearedCount > 0){
      score += (clearedCount match {
        case 1 => 100
        case 2 => 300
        case 3 => 700
        case 4 => 1500
        case _ => 0
      })
    }

    val newGrid = Array.fill(clearedCount, width)(0) ++remainingRows
    Array.copy(newGrid, 0, grid, 0, totalHeight)

    println(s"SCORE ====== $score")

    clearedCount
  }

  def spawnTetromino(): Boolean = {

    if (canPlaceTetromino(nextTetromino)){
      currentTetromino = nextTetromino
      nextTetromino = Tetromino.randomTetromino(width / 2, invisibleRows)
      true
    }else{
      false
    }
  }

  def moveTetrominoDown(): Boolean ={
    if(canMoveTetromino(0, 1)){
      currentTetromino.moveDown()
      true
    } else {
      placeTetromino(currentTetromino)
      clearFullLines()
      if(!spawnTetromino()){
        println("Ga1me Over")
        return false
      }
      false
    }
  }

  def checkGameOver(): Boolean = {
    currentTetromino.blocks.exists { case (x, y) => y < 0 }
  }

  def reset():Unit = {
    for(y<- 0 until height; x <- 0 until width ){
      grid(y)(x) = 0
    }
  }








}
