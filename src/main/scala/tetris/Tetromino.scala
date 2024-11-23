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
    val square = shape.sameElements(shapes(0))
    if(!square) {
      shape = shape.map { case (x, y) => (-y, x) }
    }

    println(s"position = $position")
    println(shape.mkString("Array(", ", ", ")"))
  }



}

object Tetromino{
  val shapes = Array(
    Array((-1, -1), (0, -1), (-1, 0), (0, 0)), // Квадрат (центр (0, 0))
    Array((-2, 0), (-1, 0), (0, 0), (1, 0)), // Прямая линия (центр (0, 0))
    Array((0, 0), (1, 0), (0, 1), (1, -1)), // Z-образная(сво) (центр (0, 0))
    Array((0, 0), (-1, 0), (0, -1), (-1, 1)), // Обратная Z-образная (центр (0, 0))
    Array((0, 0), (-1, 0), (1, 0), (0, -1)), // T-образная (центр (0, 0))
    Array((0, 0), (-1, 0), (1, 0), (1, -1)), // L-образная (центр (1, 0))
    Array((0, 0), (-1, 0), (1, 0), (-1, -1)) // Обратная L-образная (центр (-1, 0))
  )

  //  val centeredShapes: Array[Array[(Int, Int)]] = shapes.map { shape =>
  //    val minX = shape.map(_._1).min
  //    val maxX = shape.map(_._1).max
  //    val minY = shape.map(_._2).min
  //    val maxY = shape.map(_._2).max
  //
  //    val centerX = (minX + maxX)/2
  //    val centerY = (minY + maxY)/2
  //
  //    shape.map { case (x, y ) => (x - centerX , y - centerY) }
  //  }

  def randomTetromino(x: Int, y: Int): Tetromino = {
    println("Shapes before selection: " + shapes.map(_.mkString("[", ", ", "]")).mkString("{", ", ", "}"))

    val shape = shapes(Random.nextInt(shapes.length)).map(identity)
    new Tetromino(shape, (x, y))
  }

}
