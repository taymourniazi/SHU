import numpy as np
import pandas as pd
import scipy as sp
from scipy import stats
import matplotlib.pyplot as plt
import seaborn.apionly as sns

dataset=pd.read_excel('Designs.xlsx')
print(stats.describe(dataset[['Con1','Con2']]))
#to include all number and string containing column
dataset.describe(include ='all')
#to calculate variance
'''ddof delta degree of freedom, a parameter set to one
so it wont differ'''
np.var(dataset[['Con1','Con2']], ddof=1)

#to calculate std
np.std(dataset[['Con1','Con2']],ddof=1)
#best way to do std dev
dataset[['Con1','Con2']].std()
#just extra (to calculate mode)
stats.mode(dataset[['Con1','Con2']]
