sbt test

echo
read -p "Run Universe? [y/n]" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
  echo "Please Wait sbt takes awhile"
  sbt run
fi
