abstract class Piece(val color: Player) {
  var place: Int
}

class Piece2(override val color: Player) extends Piece(color) {
  var place = 23
}
