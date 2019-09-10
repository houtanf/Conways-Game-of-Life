package print_world
import life._

object Driver extends App {

	def y_axis(x: Int, y: Int, max_y: Int, live_cells: List[(Int, Int)]): Unit = 
		y match {
			case `max_y` => {
				if (live_cells.contains((x, y))){ println("X") }
				else { println(" ") }
			}
			case _ => {
				if (live_cells.contains((x, y))) { print("X") }
				else { 
					print(" ");
				}
				y_axis(x, y + 1, max_y, live_cells); 
			}
		}

	def x_axis(live_cells: List[(Int, Int)], x: Int, y: Int, max_x: Int, max_y: Int): Unit = 
		x match {
		  	case `max_x` => y_axis(x, y, max_y, live_cells)
		    case _ => {
				y_axis(x, y, max_y, live_cells);
				x_axis(live_cells, x + 1, y, max_x, max_y);
		    }
		}

	def define_borders(cells: List[(Int, Int)]) = 
		(cells.minBy(_._1)._1, cells.minBy(_._2)._2, cells.maxBy(_._1)._1, cells.maxBy(_._2)._2)

	def print_world(live_cells: List[(Int, Int)]) = {
		val (min_x, min_y, max_x, max_y) = define_borders((0,0) :: (10,10) :: live_cells)

		print("\033[2J")
		x_axis(live_cells, min_x, min_y, max_x, max_y)

		Thread.sleep(1000)
		Life.next_generation(live_cells)
	}

	val initial_cells = List[(Int, Int)]((0, 0), (1, 1), (1, 2), (2, 0), (2, 1))
  Stream.from(1).foldLeft(initial_cells) {(acc, _) => print_world(acc)}
}
