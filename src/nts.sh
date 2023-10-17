#!/bin/bash

for number in {1..11}
do
    echo "Sort $number"
    echo "------------"
    # timeout 10s nameThatSort 0459 $number Y < input.txt
    timeout 10s nameThatSort 0459 $number N < input.txt
    echo "==============================================="
done