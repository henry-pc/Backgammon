import scala.util.Random
import scala.io.StdIn
import scala.collection.mutable._
import util.control.Breaks._

object Game {

  State.board(0) = (Some(Player2), 2)
  State.board(23) = (Some(Player1), 2)
  State.board(5) = (Some(Player1), 5)
  State.board(18) = (Some(Player2), 5)
  State.board(7) = (Some(Player1), 3)
  State.board(16) = (Some(Player2), 3)
  State.board(12) = (Some(Player1), 5)
  State.board(11) = (Some(Player2), 5)


}

object GameMain {
  def main(args: Array[String]) {
    val gameOngoing = true
    val playerArr = new Array[Player](2)
    playerArr(0) = Player1
    playerArr(1) = Player2
    var toMove: Player = playerArr(scala.util.Random.nextInt(1))
    while (true) {
      print("Press Enter to Begin Game")
      val enter = scala.io.StdIn.readLine
      if (enter == "\n") {
        break
      }
    }
    while (true) {
      println("Press Enter to Randomly Determine Starting Order")
      val enter1 = scala.io.StdIn.readLine
      if (enter1 == "\n") {
        break
      }
    }
    while(gameOngoing) {
      while (true) {
        println("1. Display Board")
        println("2. Roll")
        val command = scala.io.StdIn.readLine
        if (command == "1\n") {
          State.renderBoard
        }
        else if (command == "2\n") {
          toMove.turn
          break
        }
        else { println("Invalid command") }
      }
      toMove = toMove.otherPlayer
      if (State.isOver) { break }
    }
    val winner = toMove.otherPlayer
    val winnerString = winner match {
      case Player1 => "Player 1!"
      case Player2 => "Player 2!"
    }
    println("The winner is " + winnerString)
  }
}
