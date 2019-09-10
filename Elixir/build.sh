#!/bin/sh

mix test

echo
read -p "Run Universe? [y/n]" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	mix escript.build
	./life
fi
