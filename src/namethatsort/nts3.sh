#!/bin/bash

id=10
for i in {1..4}
do
    timeout 5m ./nameThatSort 0459 $id "N" < $1 | python3 src/nts.py --upload $1 --sort-id $id
done
