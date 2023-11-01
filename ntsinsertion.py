
import random
import sys

n = int(sys.argv[1])
upper_bound = 100_000_000

print(f"generating {n:3,d} semi-sorted values")

with open(f'input/{n}_rev.txt', 'w') as f:
    f.write(f"{n}\n")
    nums = [random.randint(1, upper_bound) for _ in range(n)]
    nums.sort(reverse=True)

    # for _ in range(2000):
    #     index = random.randint(0, len(nums)-1)
    #     nums[index] = random.randint(1, upper_bound)

    for i, num in enumerate(nums):
        f.write(f"{num} {i}\n")
