defmodule Life do
  
  def is_alive?(alive, number_of_live_neighbors), do:
    alive && number_of_live_neighbors == 2 || number_of_live_neighbors == 3

  def generate_signals(live_cells), do:
    for {x, y} <- live_cells, hx <- (x - 1)..(x + 1), hy <- (y - 1)..(y + 1),
      {hx, hy} != {x, y}, do: {hx, hy}

  def count_signals(signals), do:
    Enum.group_by(signals, &(&1))
    |> Map.new(fn { key, value } -> { key, length(value) } end)

  def next_generation(live_cells) do
    signal_count = generate_signals(live_cells) |> count_signals()
    for {key, val} <- signal_count, is_alive?(key in live_cells, val), do: key
  end
end
