
if [ $# -eq 2 ]
then
	echo 'You requestd to import ' $1 ' to the collection ' $2
	tr ";" "\t" < $1 | mongoimport -d Entsoe -c $2 --type tsv --headerline
else
	echo 'Imports a CSV file to a collection in the Entsoe DB.
	You must enter 2 arguments,
	1: the csv file
	2: the collection to insert it into'
fi
