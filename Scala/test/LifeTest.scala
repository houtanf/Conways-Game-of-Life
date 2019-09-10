import org.scalatest._
import life._

class LifeTest extends FunSuite {

	test("Canary test") {
		assert(true)
	}

	test ("cell with 0 neighbours (dies)") {
		assert(Life.isAlive(true, 0) === false)
	}

	test ("live cell with 1 neighbour (dies)") {
		assert(Life.isAlive(true, 1) === false)
	}

	test ("live cell with 2 neighbours (lives)") {
		assert(Life.isAlive(true, 2) === true)
	}

	test ("live cell with 3 neighbours (lives)") {
		assert(Life.isAlive(true, 3) === true)
	}

	test ("live cell with 4 neighbours (dies)") {
		assert(Life.isAlive(true, 4) === false)
	}

	test ("dead cell with 4 neighbours (dies)") {
		assert(Life.isAlive(false, 4) === false)
	}

	test ("dead cell with 3 neighbours (lives)") {
		assert(Life.isAlive(false, 3) === true)
	}

	test ("dead cell with 2 neighbours (dies)") {
		assert(Life.isAlive(false, 2) === false)
	}

	test ("dead cell with 1 neighbours (dies)") {
		assert(Life.isAlive(false, 1) === false)
	}

	test ("signals in an empty universe") {
		assert(Life.generate_signals(List()) === List())
	}
  
	test ("signals in an universe with one live cell") {
		val signals = List[(Int, Int)]((2, 3))

		assert(Life.generate_signals(signals) === 
    		List((1, 2), (1, 3), (1, 4), (2, 2), (2, 4), (3, 2), (3, 3), (3, 4)))
	}

	test ("signals in an universe with two live cells") {
		val signals = List[(Int, Int)]((2, 3), (10, 12))

		assert(Life.generate_signals(signals) === 
    		List((1, 2), (1, 3), (1, 4), (2, 2), (2, 4), (3, 2), (3, 3), (3, 4), 
    			(9, 11), (9, 12), (9, 13), (10, 11), (10, 13), (11, 11), (11, 12), (11, 13)))
	}
  
	test ("signals in an universe with two close live cell") {
		val signals = List[(Int, Int)]((2, 3), (2, 5))

	    assert(Life.generate_signals(signals).size === 16)
		assert(Life.generate_signals(signals).contains((2, 4)))
	}

	test ("count signals when there are no signals") {
		assert(Life.count_signals(List()) === Map())
	}

	test ("count signals for one signal") {
		val signals = List[(Int, Int)]((1, 2))

		assert(Life.count_signals(signals) === Map((1, 2) -> 1))
	}

	test ("count signals for two identical signals") {
		val signals = List[(Int, Int)]((1, 2), (1, 2))

		assert(Life.count_signals(signals) === Map((1, 2) -> 2))
	}

	test ("count signals for three signals with 2 identical") {
		val signals = List[(Int, Int)]((3, 2), (101, 102), (3, 2))

		assert(Life.count_signals(signals) === Map((3,2) -> 2, (101,102) -> 1))
	}

	test ("count signals for six signals with 4 identical") {
		val signals = List[(Int, Int)]((3, 2), (101, 102), (3, 2), (1, 2), (3, 2), (3, 2))

		assert(Life.count_signals(signals) === Map((3, 2) -> 4, (101, 102) -> 1, (1, 2) -> 1))
	}

	test ("next generation of no alive cells") {
		assert(Life.next_generation() === List())
	}

	test ("next generation of one alive cell") {
		val cells = List[(Int, Int)]((1, 1))
		assert(Life.next_generation(cells) === List())
	}

	test ("next generation with block initialization pattern") {
		val cells = List[(Int, Int)]((0, 0), (1, 1), (0, 1), (1, 0))
		assert(Life.next_generation(cells) === cells)
	}

	test ("next generation with horizontal oscillator initialization pattern") {
		val first_gen = List[(Int, Int)]((1, 0), (1, 1), (1, 2))
		val next_gen = List[(Int, Int)]((1, 1), (0, 1), (2, 1))
		assert(Life.next_generation(first_gen) === next_gen)
	}

	test ("next generation with vertical oscillator initialization pattern") {
		val first_gen = List[(Int, Int)]((1, 1), (0, 1), (2, 1))
		val next_gen = List[(Int, Int)]((1, 1), (1, 2), (1, 0))
		assert(Life.next_generation(first_gen) === next_gen)
	}

	test ("next generation with basic glider initialization pattern") {
		val first_gen = List[(Int, Int)]((0, 0), (1, 1), (1, 2), (2, 0), (2, 1))
		val second_gen = List[(Int, Int)]((2, 0), (2, 2), (0, 1), (1, 2), (2, 1))
		val third_gen = List[(Int, Int)]((3, 1), (2, 2), (1, 2), (2, 1), (1, 0))
		val fourth_gen = List[(Int, Int)]((3, 1), (2, 0), (3, 2), (2, 2), (1, 2))
		val final_gen = List[(Int, Int)]((3, 1), (1, 1), (3, 2), (2, 2), (2, 3))

		assert(Life.next_generation(first_gen) === second_gen)
		assert(Life.next_generation(second_gen) === third_gen)
		assert(Life.next_generation(third_gen) === fourth_gen)
		assert(Life.next_generation(fourth_gen) === final_gen)
	}
}
