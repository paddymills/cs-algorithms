import matplotlib.pyplot as plt
import numpy as np
from tqdm import tqdm

with open('tsp_input.txt') as f:
    lines = f.readlines()

xn, yn = list(), list()
for line in tqdm(lines[1:]):
    x, y = [int(x) for x in line.strip().split(' ')]
    xn.append(x)
    yn.append(y)


plt.plot(xn, yn, ",")
plt.show()