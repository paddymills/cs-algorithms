
import random
import re
import sys

from argparse import ArgumentParser
from tabulate import tabulate

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
    args = parser.parse_args()

    if args.n:
        print(f'setting n to {args.n}')
        num_vals = int(args.n)

    if args.values:
        generate(args.sorted)

    if args.analysis:
        analysis(args.extra)


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


def generate(sorted_vals=False):
    print(f"generating {num_vals} {'sorted' if sorted_vals else 'randomized'} values")

    with open('input.txt', 'w') as f:
        f.write(f"{num_vals}\n")
        nums = [random.randint(1, upper_bound) for _ in range(num_vals)]
        if sorted_vals:
            nums = sorted(nums)

        for i, num in enumerate(nums):
            f.write(f"{num} {i}\n")

if __name__ == "__main__":
    main()
