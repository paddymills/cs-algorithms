
# EXTRA CREDIT (10 points): 

# There is a sort #12 in the executable, which uses a data structure to perform the sort.  Based on the the values you can observe, answer the following questions:
# 1) What data structure is being used to support the sort? Be specific and justify your answer.
# 2) Is the sort in-place?
# 3) Is the sort stable? Given this answer how do you think duplicate keys are being handled?

import random
import re
import os
import sys
import sqlite3

from argparse import ArgumentParser
from tabulate import tabulate
from tqdm import tqdm

n_sequence = (
    8,
    10,
    15,
    25,
    50,
    100,
    250,
    500,
    750,
    1_000,
    2_500,
    5_000,
    7_500,
    10_000,
    # 100_000,
    # 1_000_000,
    # 10_000_000
)
num_vals = 1_000
upper_bound = 100_000_000

sort_pattern = re.compile(r"Sort (\d+)")
time_pattern = re.compile(r"The sort took (\d+) ms.")
mem_pattern  = re.compile(r"The sort used (\d+) KB in auxiliary memory.")

def main():
    global num_vals

    parser = ArgumentParser()
    parser.add_argument("-a", "--analysis", action="store_true", help="parse input")
    parser.add_argument("-e", "--extra", action="store_true", help="parse input has extra output")
    parser.add_argument("-n", help="set number of values to generate")
    parser.add_argument("-s", "--sorted", action="store_true", help="generate sorted list of values")
    parser.add_argument("-v", "--values", action="store_true", help="generate input.txt")
    parser.add_argument("-u", "--upload", help="upload results")
    parser.add_argument("--sort-id", help="id of sort (for upload)")
    parser.add_argument("-p", "--plot", action="store_true", help="plot results")
    parser.add_argument("--print", action="store_true", help="plot results")
    args = parser.parse_args()

    if args.print:
        generate_stdout(int(args.n), args.sorted)

    elif args.n:
        generate(int(args.n), args.sorted)

    if args.values:
        for n in n_sequence:
            for tf in (True, False):
                generate(n, sorted_vals=tf)

    if args.analysis:
        analysis(args.extra)

    if args.upload:
        upload(args.sort_id, args.upload)

    if args.plot:
        plot()


def analysis(with_extra=False):
    table = [["Sort", "Time(ms)", "Memory(KB)"]]
    for i in range(11):
        table.append([i+1, "Timed out", "--"])

    current_sort = 0
    for line in sys.stdin:
        line = line.strip()
        if sort_pattern.match(line):
            current_sort = int(sort_pattern.match(line).group(1))
        elif time_pattern.match(line):
            table[current_sort][1] = time_pattern.match(line).group(1)
        elif mem_pattern.match(line):
            table[current_sort][2] = mem_pattern.match(line).group(1)
        

    print(tabulate(table, headers="firstrow"))


def generate(n, sorted_vals=False):
    dispersal = 'sorted' if sorted_vals else 'randomized'
    print(f"generating {n:3,d} {dispersal} values")

    with open(f'input/{n}_{dispersal}.txt', 'w') as f:
        f.write(f"{n}\n")
        nums = [random.randint(1, upper_bound) for _ in range(n)]

        duplicate = random.randint(1, n-1);
        nums[random.randint(1, n-1)] = nums[duplicate]
        nums[random.randint(1, n-1)] = nums[duplicate]

        if sorted_vals:
            nums = sorted(nums)

        for i, num in enumerate(nums):
            f.write(f"{num} {i}\n")

    if sorted_vals is False:
        generate(n, sorted_vals=not sorted_vals)


def generate_stdout(n,sorted_vals):
    print(n)
    nums = [random.randint(1, upper_bound) for _ in range(n)]

    duplicate = random.randint(1, n-1);
    nums[random.randint(1, n-1)] = nums[duplicate]
    nums[random.randint(1, n-1)] = nums[duplicate]

    if sorted_vals:
        nums = sorted(nums)

    for i, num in enumerate(nums):
        print(num, i)


def upload(sort_id, input_file):
    conn = sqlite3.connect("nts.db")
    cursor = conn.cursor()
    
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS telemetry(
            id INTEGER PRIMARY KEY,
            sort INTEGER NOT NULL,
            n INTEGER NOT NULL,
            ordering TEXT NOT NULL,
            runtime INTEGER,
            memory INTEGER
        )
    """)
    conn.commit()


    # parse data
    fn_pattern = re.compile(r"(?:[a-z]+1?/)?(\d+)_([a-z]+)\.txt")
    match = fn_pattern.match(input_file)
    if not match:
        print(input_file, "Not parsed for `n` and `ordering`. Exiting.")
        return
    n, ordering = match.groups()

    runtime = None
    memory = None
    for line in sys.stdin:
        line = line.strip()
        if time_pattern.match(line):
            runtime = int(time_pattern.match(line).group(1))
        elif mem_pattern.match(line):
            memory = int(mem_pattern.match(line).group(1))

            # TODO: parse sorted output

    # upload data
    cursor.execute("""
        INSERT INTO telemetry(sort, n, ordering, runtime, memory)
        VALUES(?, ?, ?, ?, ?)
    """, (sort_id, n, ordering, runtime, memory))
    conn.commit()

    conn.close()


def plot():

    for x in ('randomized', 'sorted'):
        print("********************************")
        print("*{:^30}*".format(x))
        print("********************************")
        runtime = [["Sort", *n_sequence]]
        memory = [["Sort", *n_sequence]]
        for i in range(12):
            runtime.append([i+1, *["Timed out"] * len(n_sequence)])
            memory.append([i+1, *["Timed out"] * len(n_sequence)])

        for i, n in enumerate(n_sequence, start=1):
            with open(f'output/{n}_{x}.txt') as f:
                current_sort = 0
                for line in f.readlines():
                    line = line.strip()
                    if sort_pattern.match(line):
                        current_sort = int(sort_pattern.match(line).group(1))
                    elif time_pattern.match(line):
                        runtime[current_sort][i] = time_pattern.match(line).group(1)
                    elif mem_pattern.match(line):
                        memory[current_sort][i] = mem_pattern.match(line).group(1)

                    # TODO: parse sorted output
            

        print("~~~~~~~~~~~~~~~~~ Runtime ~~~~~~~~~~~~~~~~~")
        print(tabulate(runtime, headers="firstrow"))
        print("~~~~~~~~~~~~~~ Memory Usage ~~~~~~~~~~~~~~~")
        print(tabulate(memory, headers="firstrow"))
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

if __name__ == "__main__":
    main()
