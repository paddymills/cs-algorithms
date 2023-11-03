#!/bin/sh

echo "rand"
timeout 30s ./nameThatSort 0459 $1 N < input/500000_randomized.txt
echo "rand radix"
timeout 30s ./nameThatSort 0459 $1 N < input/500000_randomized_radix.txt
echo "sorted"
timeout 30s ./nameThatSort 0459 $1 N < input/500000_sorted.txt
echo "sorted radix"
timeout 30s ./nameThatSort 0459 $1 N < input/500000_sorted_radix.txt