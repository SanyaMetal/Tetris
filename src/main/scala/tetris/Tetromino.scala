package tetris

import scala.util.Random

case class Tetromino(var shape: Array[(Int,Int)]){
  var position: (Int,Int) = (0,0)

  def blocks: Array[(Int, Int)] = {
    shape.map { case (x, y) => (x + position._1, y + position._2) }
  }

  def move(x:Int, y: Int): Unit = {
    position = (position._1+x, position._2+y)
  }

  def moveLeft(): Unit ={
    move(-1,0)
  }

  def moveRight():Unit = {
    move(1,0)
  }

  def moveDown():Unit = {
    move(0,1)
  }

  def rotate(): Unit = {
    val centerX = position._1
    val centerY = position._2

    shape = shape.map{
     case(x, y) =>
       val relativeX = x - centerX
       val relativeY = y - centerY
       val rotatedX = -relativeX
       val rotatedY = -relativeY
       (rotatedX + centerX, rotatedY + centerY)
    }
  }




}

object Tetromino{
  val shapes = Array(
    Array((0, 0), (1, 0), (0, 1), (1, 1)), // Квадрат
    Array((0, -1), (0, 0), (0, 1), (0, 2)), // Прямая линия
    Array((0, 0), (1, 0), (1, 1), (2, 1)), // Z-образная(сво)
    Array((0, 0), (-1, 0), (-1, 1), (-2, 1)), // Обратная Z-образная
    Array((0, 0), (1, 0), (2, 0), (1, 1)), // T-образная
    Array((0, 0), (1, 0), (1, 1), (1, 2)), // L-образная
    Array((0, 0), (1, 0), (1, -1), (1, -2)) // Обратная L-образная
  )

  def randomTetromino(x: Int, y: Int): Tetromino = {
    val shape = shapes(Random.nextInt(shapes.length))
    val coordinates = shape.map { case (dx, dy) => (x + dx, y + dy) }
    new Tetromino(coordinates)
  }

}
