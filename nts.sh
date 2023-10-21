#!/bin/bash

# rm output/*.txt

showOutput="N"
for sortId in {1..12}
do
    for runId in {1..5}
    do
        for filename in input/*;
        do
            # output=$(echo $filename | sed "s/input/output/g")
            echo "Sort $sortId:$runId"
            timeout 30s ./nameThatSort 0459 $sortId $showOutput < $filename | python3 src/nts.py --upload $filename --sort-id $sortId
        done
    done
done

# timeout 5m ./nameThatSort 0459 2 $showOutput < input/5_sorted.txt
# timeout 5m ./nameThatSort 0459 2 $showOutput < input/5_sorted.txt | python3 src/nts.py --upload input/5_sorted.txt --sort-id 2
