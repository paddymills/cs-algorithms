#!/bin/bash

for number in {1..11}
do
    echo "Sort $number"
    echo "------------"
    # nameThatSort 0459 $number Y < input.txt
    nameThatSort 0459 $number N < input.txt
    echo "==============================================="
done