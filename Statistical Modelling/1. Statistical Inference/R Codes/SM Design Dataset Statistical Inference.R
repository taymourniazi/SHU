#reading the Data
dataset = read_excel("Designs.xlsx")
#feeling data
dataset
#feeling data 2
boxplot(dataset$Con1, dataset$Con2)
# Statistics which are useful
library(psych)
describeBy(dataset$Con1)
describeBy(dataset$Con2)
#plotting data
plot(Store~Con1, data=dataset)
#Calculate Mean
mean.Con1=mean(dataset$Con1)
#T test for paired sample
t.test(dataset$Con1, dataset$Con2, mu=0, alt="two.sided", paired = T, conf.level = 0.95)
