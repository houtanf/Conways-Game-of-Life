package life

object Life {
	def isAlive(alive: Boolean, number_of_live_neighbors: Int) =
    	alive && number_of_live_neighbors == 2 || number_of_live_neighbors == 3
                                     
	def generate_signals(live_cells: List[(Int, Int)]): List[(Int, Int)] =
		for ((x, y) <- live_cells; 
				x_coordinate <- (x- 1) to (x + 1);
				y_coordinate <- (y - 1) to (y + 1) if (x_coordinate, y_coordinate) != (x, y)) 
        yield (x_coordinate, y_coordinate);

	def count_signals(signals: List[(Int, Int)]): Map[(Int, Int), Int] =
		signals.groupBy(identity).mapValues(_.size)

	def next_generation(live_cells: List[(Int, Int)] = List()) =
	    count_signals(generate_signals(live_cells))
		    .filter((tuple) => isAlive(live_cells.contains(tuple._1), tuple._2))
		    .map { case (key, value) => (key) }(collection.breakOut): List[(Int, Int)]
}