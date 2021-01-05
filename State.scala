import scala.Option

object State {
  val board = new Array[(Option[Player], Int)](24)
  var a = 0
  while (a < 24) {
    State.board(a) = (None, 0)
    a = a + 1
  }

  def isOver: Boolean = {
    var i = 0
    while(i < 24) {
      if ((board(i))._2 != 0) {
        false
      }
      i = i + 1
    }
    true
  }

  def renderBoard() {
    var i = 0
    while(i < 24) {
      board(i)._1 match {
        case Some(name) =>
          println(name.playerName, board(i)._2)
        case None => println("0")
      }
      i = i + 1
    }
  }
}
