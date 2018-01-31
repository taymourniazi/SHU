import numpy as np
import pandas as pd
import stats from scipy

dataset=pd.read_xlsx('Designs.xlsx')
print(stats.describe(dataset['Con1']))

#to include all number and string containing column
dataset.describe(include = 'all')
