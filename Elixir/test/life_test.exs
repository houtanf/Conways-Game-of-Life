defmodule LifeTest do
  use ExUnit.Case
  doctest Life

  test "Canary" do
    assert True
  end

  test "cell with 0 neighbours (dies)" do
    refute Life.is_alive?(true, 0)
  end

  test "live cell with 1 neighbour (dies)" do
    refute Life.is_alive?(true, 1)
  end

  test "live cell with 2 neighbours (lives)" do
    assert Life.is_alive?(true, 2)
  end

  test "live cell with 3 neighbours (lives)" do
    assert Life.is_alive?(true, 3)
  end

  test "live cell with 4 neighbours (dies)" do
    refute Life.is_alive?(true, 4)
  end

  test "dead cell with 4 neighbours (dies)" do
    refute Life.is_alive?(false, 4)
  end

  test "dead cell with 3 neighbours (lives)" do
    assert Life.is_alive?(false, 3)
  end

  test "dead cell with 2 neighbours (dies)" do
    refute Life.is_alive?(false, 2)
  end

  test "dead cell with 1 neighbours (dies)" do
    refute Life.is_alive?(false, 1)
  end

  test "signals in an empty universe" do
    signals = Life.generate_signals([])
  
    assert 0 == length(signals)
  end
  
  test "signals in an universe with one live cell" do
    signals = Life.generate_signals([{2, 3}])

    assert 8 == length(signals)
    assert [{1, 2}, {1, 3}, {1, 4}, {2, 2}, {2, 4}, {3, 2}, {3, 3}, {3, 4}]
      == signals
  end
  
  test "signals in an universe with two live cells" do
    signals = Life.generate_signals([{2, 3}, {10, 12}])

    assert 16 == length(signals)
    assert Enum.member?(signals, {1, 2})
    assert Enum.member?(signals, {10, 11})
  end
  
  test "signals in an universe with two nearby cells" do
    signals = Life.generate_signals([{2, 3}, {2, 5}])

    assert 16 == length(signals)
    assert 2 == length(Enum.filter(signals, fn(e) -> {2, 4} == e end))
  end

  test "count signals when there are no signals" do
    assert %{} == Life.count_signals([])
  end

  test "count signals for one signal" do
    signals = [{1, 2}]

    assert %{{1, 2} => 1} == Life.count_signals(signals)
  end

  test "count signals for two identical signals" do
    signals = [{1, 2}, {1, 2}]

    assert %{{1, 2} => 2} == Life.count_signals(signals)
  end

  test "count signals for three signals with 2 identical" do
    signals = [{3, 2}, {101, 102}, {3, 2}]

    assert %{{3, 2} => 2, {101, 102} => 1} == Life.count_signals(signals)
  end

  test "count signals for six signals with 4 identical" do
    signals = [{3, 2}, {101, 102}, {3, 2}, {1, 2}, {3, 2}, {3, 2}]

    assert %{{3, 2} => 4, {101, 102} => 1, {1, 2} => 1} ==
      Life.count_signals(signals)
  end

  test "next generation of no alive cells" do
    assert [] = Life.next_generation([])
  end

  test "next generation of one alive cell" do
    assert [] = Life.next_generation([{1, 1}])
  end

  test "next generation with block initialization pattern" do
    cells = [{0, 0}, {0, 1}, {1, 0}, {1, 1}]

    assert cells == Life.next_generation(cells)
  end

  test "next generation with horizontal oscillator initialization pattern" do
    cells = [{1, 0}, {1, 1}, {1, 2}]

    assert [{0, 1}, {1, 1}, {2, 1}] == Life.next_generation(cells)
  end

  test "next generation with vertical oscillator initialization pattern" do
    cells = [{1, 1}, {0, 1}, {2, 1}]

    assert [{1, 0}, {1, 1}, {1, 2}] == Life.next_generation(cells)
  end

  test "next generation with basic glider initialization pattern" do
    phase1 = [{0, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}]

    phase2 = [{0, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}]

    phase3 = [{1, 0}, {1, 2}, {2, 1}, {2, 2}, {3, 1}]

    phase4 = [{1, 2}, {2, 0}, {2, 2}, {3, 1}, {3, 2}]

    assert phase2 == Life.next_generation(phase1)

    assert phase3 == Life.next_generation(phase2)

    assert phase4 == Life.next_generation(phase3)

    assert [{1, 1}, {2, 2}, {2, 3}, {3, 1}, {3, 2}] ==
      Life.next_generation(phase4)
  end
end
