#!/bin/bash

for id in {3..12}
do
    timeout 5m ./nameThatSort 0459 $id "N" < $1 | python3 src/nts.py --upload $1 --sort-id $id
done
