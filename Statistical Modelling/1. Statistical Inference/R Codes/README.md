# 

#feeling data
dataset

> dataset
# A tibble: 10 x 3
   Store  Con1  Con2
   <dbl> <dbl> <dbl>
 1  1.00   141   118
 2  2.00   184   167
 3  3.00   132   137
 4  4.00   161   168
 5  5.00   176   175
 6  6.00   196   197
 7  7.00   169   143
 8  8.00   199   169
 9  9.00   150   123
10 10.0    218   197


#feeling data 2
boxplot(dataset$Con1, dataset$Con2)
![Screenshot](Boxplot Designs R.PNG)

library(psych)
describeBy(dataset$Con1)
describeBy(dataset$Con2)

> describeBy(dataset$Con1)
   vars  n  mean    sd median trimmed  mad min max range skew kurtosis   se
X1    1 10 172.6 27.39  172.5     172 34.1 132 218    86 0.07    -1.39 8.66
> describeBy(dataset$Con2)
   vars  n  mean   sd median trimmed   mad min max range skew kurtosis   se
X1    1 10 159.4 28.1  167.5  159.88 40.03 118 197    79 -0.1    -1.54 8.88

#T test for paired sample
t.test(dataset$Con1, dataset$Con2, mu=0, alt="two.sided", paired = T, conf.level = 0.95)

	Paired t-test

data:  dataset$Con1 and dataset$Con2
t = 2.8747, df = 9, p-value = 0.01834
alternative hypothesis: true difference in means is not equal to 0
95 percent confidence interval:
  2.812672 23.587328
sample estimates:
mean of the differences 
                   13.2 
