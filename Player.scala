import scala.collection.mutable._
import util.control.Breaks._

abstract case class Player() {
  val startingPlace: Int
  val otherPlayer: Player
  val playerName: String
  def changeBoard(index: Int, toMove: Int): Boolean
  var piecesHit = 0
  var ready = false
  def canMove(index: Int, roll: Int): Boolean = {
    !(State.board(index + roll) == otherPlayer)
  }

  def move(moves: ListBuffer[Int]) {
    if (moves.isEmpty) {
      val randInt1 = scala.util.Random.nextInt(5) + 1
      val randInt2 = scala.util.Random.nextInt(5) + 1
      while (!moves.isEmpty) {
        breakable {
          println("Enter an index, then a number to move")
          val index = scala.io.StdIn.readInt
          val toMove = scala.io.StdIn.readInt
          val dest = index + toMove
          if (State.board(dest)._1 == Some(otherPlayer)) {
            if (State.board(dest)._2 == 1) {
              otherPlayer.piecesHit += 1
              State.board(dest) = (None, 0)
            }
            else {
              println("Cannot move there")
              break
            }
          }
          changeBoard(index, toMove)
          moves -= toMove
        }
      }
    }
  }

  def roll: ListBuffer[Int] = {
    val randInt1 = scala.util.Random.nextInt(5) + 1
    val randInt2 = scala.util.Random.nextInt(5) + 1
    val moves = new ListBuffer[Int]
    (moves += randInt1) += randInt2
    if (randInt1 == randInt2) {
      (moves += randInt1) += randInt2
    }
    moves
  }

def enter(moves: ListBuffer[Int]) {
  if (canEnter(moves.apply(0), moves.apply(1))) {
    while (true) {
      println("Select your move")
      val ent = scala.io.StdIn.readInt
      if (!moves.contains(ent)) { println("Move not possible") }
      else {
        moves -= ent
        placeEntryPiece(ent)
        move(moves)
        break
      }
    }
  } else { println("Cannot enter on this roll") }
}

  def placeEntryPiece(move: Int)

  def turn {
    val moves = roll
    if (piecesHit == 0) { move(moves) }
    else { enter(moves) }
  }

  def hasPossibleMoves(x: (Int, Int), iter: Int): Boolean = {
    ((iter < 24) && hasPossibleMoves(x, (iter + 1))) ||
      (State.board(iter)._1 match {
        case Some[Player] => (getType(State.board(iter)._1) == this) && (canMove(iter, x._1) || canMove(iter, x._2))
          // if (s == this) { canMove(iter, x._1) || canMove(iter, x._2) }
          // else { false }
        case None => false
      })
      // State.board(iter) match {
      //   case Some[this] => { canMove(iter, x._1) || canMove(iter, x._2) }
      //   case Some[_] => false
      //   case None => false
      // }
  }

  def canEnter(x: (Int, Int)): Boolean

  def printPossibleMoves(state: State, x: (Int, Int)) {
    var b = 0
    while (b < 24) {
      State.board(b) match {
        case Some(st) =>
          if (st == this) {
            if (canMove(b,x._1)) { println(b, x._1) }
          }
      }
      b = b + 1
    }
    if (x != y) {
      b = 0
      while (b < 24) {
        State.board(b) match {
          case Some(su) =>
            if (su == this) {
              if (canMove(b,x._2)) { println(b, x._2) }
            }
        }
        b = b + 1
      }
    }
  }
}

object Player1 extends Player {
  val startingPlace = 0
  val otherPlayer = Player2
  val playerName = "Player 1"
  def changeBoard(index: Int, toMove: Int): Boolean = {
    if (State.board(index + toMove)._1 == Some(Player2)) {
      false
    }
    val pieceCount = State.board(index + toMove)._2
    State.board(index + toMove) = (Some(Player1), pieceCount + 1)
    true
  }
  def canEnter(x: (Int, Int)): Boolean = {
    a = 0
    (State.board(x._1) != otherPlayer) || (State.board(x._2) != otherPlayer)
  }
  def placeEntryPiece(move: Int) {
    State.board(move) match {
      case (Some(Player1), z) => State.board(move) = (Some(Player1), (z + 1))
      case None => State.board(23 - move) = (Some(Player1), 1)
    }
  }
}

object Player2 extends Player {
  val startingPlace = 23(x)
  val otherPlayer = Player1
  val playerName = "Player 2"
  def changeBoard(index: Int, toMove: Int): Boolean = {
    if (State.board(index - toMove)._1 != Some(Player2)) {
      false
    }
    val pieceCount = State.board(index - toMove)._2
    State.board(index - toMove) = (Some(Player2), pieceCount + 1)
    true
  }
  def canEnter(x: (Int, Int)): Boolean = {
    a = 0
    (State.board(x) != otherPlayer) || (State.board(y) != otherPlayer)
  }
  def placeEntryPiece(move: Int) {
    // if (State.board(23 - move)._1 == Some(this)) {
    //   State.board(23 - move) = (Some(this), (State.board(23 - move)._2 + 1))
    // } else {
    //   State.board(23 - move) = (Some(this), 1)
    // }
    State.board(23 - move) match {
      case (Some(Player2), z) => State.board(23 - move) = (Some(Player2), (z + 1))
      case None => State.board(23 - move) = (Some(Player2), 1)
    }
  }
}

// object AIPlayer extends Player2 { // this won't work
//   val startingPlace = ?
//   val otherPlayer = Player1 // this won't work
//   val playerName = "AI Player"
//   // don't need a changeBoard if it's extending
//   def stateValue()
//
// }
