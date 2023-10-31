#!/bin/bash

for runId in {1..3}
do
    echo "..$runId"
    timeout 5m ./nameThatSort 0459 $1 "N" < input/$2_sorted.txt | python3 src/nts.py --upload input/$2_sorted.txt --sort-id $1
    timeout 5m ./nameThatSort 0459 $1 "N" < input/$2_randomized.txt | python3 src/nts.py --upload input/$2_randomized.txt --sort-id $1
done
