#!/bin/sh

echo -n "Gerando gr�fico... "
NOW=`date +%s`
gnuplot grafico-xor.pl
echo "$((`date +%s` - $NOW)) segundos." 

[ -f saidas.png ] && xv saidas.png
