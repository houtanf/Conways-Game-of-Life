defmodule Driver do

  def y_axis(x, y, max_y, live_cells) when y == max_y, do: if {x, y} in
     live_cells, do: IO.puts("X"), else: IO.puts(" ")

  def y_axis(x, y, max_y, live_cells) do
    if {x, y} in live_cells, do: IO.write("X"), else: IO.write(" ")
      y_axis(x, y + 1, max_y, live_cells)
  end

  def x_axis(live_cells, x, y, max_x, max_y) when x == max_x, do:
    y_axis(x, y, max_y, live_cells)

  def x_axis(live_cells, x, y, max_x, max_y) do
    y_axis(x, y, max_y, live_cells)
    x_axis(live_cells, x + 1, y, max_x, max_y)
  end


  def define_bourders(cells) do
    {min_x, max_x} = Enum.map(cells, fn {x, _} -> x end)
    |> Enum.min_max(fn -> {0, 10} end)

    {min_y, max_y} = Enum.map(cells, fn {_, y} -> y end)
    |> Enum.min_max(fn -> {0, 10} end)

    {min_x, min_y, max_x, max_y}
  end
             

  def print_world(_, live_cells) do
    {min_x, min_y, max_x, max_y} = 
      define_bourders([{0, 0} | [{10, 10} | live_cells]])

    IO.write(IO.ANSI.clear())
    x_axis(live_cells, min_x, min_y, max_x, max_y)

    :timer.sleep(1000)

    Life.next_generation(live_cells)
  end


  def main(_) do
    initial_cells = [{0, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}]

    Stream.iterate(0, &(&1 + 1)) |> Enum.reduce(initial_cells, &(print_world(&1, &2)))
  end

end



