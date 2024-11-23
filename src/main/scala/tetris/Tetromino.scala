package tetris

import tetris.Tetromino.shapes

import scala.util.Random

case class Tetromino(var shape: Array[(Int,Int)], var position: (Int,Int) = (0,0)){
  

  def blocks: Array[(Int, Int)] = {
    shape.map { case (x, y) => (x + position._1, y + position._2) }
  }

  def move(x:Int, y: Int): Unit = {
    position = (position._1+x, position._2+y)
    println(position)
    println(shape.mkString("Array(", ", ", ")"))
  }

  def moveLeft(): Unit =move(-1,0)
  def moveRight():Unit = move(1,0)
  def moveDown():Unit = move(0,1)

  def rotate(): Unit = {
    shape = shape.map{ case (x, y) => (-y, x) }

    println(s"position = $position")
    println(shape.mkString("Array(", ", ", ")"))
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

  val centeredShapes: Array[Array[(Int, Int)]] = shapes.map { shape =>
    val minX = shape.map(_._1).min
    val maxX = shape.map(_._1).max
    val minY = shape.map(_._2).min
    val maxY = shape.map(_._2).max

    val centerX = (minX + maxX)/2
    val centerY = (minY + maxY)/2

    shape.map { case (x, y ) => (x - centerX, y - centerY) }
  }

  def randomTetromino(x: Int, y: Int): Tetromino = {
    val shape = centeredShapes(Random.nextInt(shapes.length))
    //val coordinates = shape.map { case (dx, dy) => (x + dx, y + dy) }      
    new Tetromino(shape, (x, y))
  }

}
